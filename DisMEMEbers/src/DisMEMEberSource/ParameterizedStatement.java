
import java.util.Iterator;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ParameterizedStatement {

    private java.sql.PreparedStatement stmt;

    public class Results implements Iterable<Map<String, Value>> {

        private final String[] names;
        private final int[] types;
        private final java.sql.ResultSet R;
        private boolean alreadyIterated=false;
        public Results(java.sql.ResultSet R) throws SQLException {
            this.R = R;
            java.sql.ResultSetMetaData meta = R.getMetaData();
            names = new String[meta.getColumnCount()];
            types = new int[meta.getColumnCount()];
            for (int i = 0; i < meta.getColumnCount(); ++i) {
                names[i] = meta.getColumnName(i + 1);
                types[i] = meta.getColumnType(i + 1);
            }
        }

        public ArrayList<Map<String,Value>> getAll(){
            var r = new ArrayList<Map<String,Value>> ();
            for(var x : this ){
                r.add(x);
            }
            return r;
        }
        class ResultsIterator implements Iterator<Map<String, Value>> {

            //states:
            //  0=R.next() needs to be called before we can do anything
            //  1=R.next() was called and data is awaiting next()
            //  2=no more data
            int state = 0;

            public ResultsIterator() {
            }

            @Override
            public Map<String, Value> next() {
                if (state == 0) {
                    fetch();
                }

                if (state == 1) {
                    try {
                        var rv = new CaseInsensitiveMap<Value>();
                        //Value[] rv = new Value[names.length];

                        for (int i = 1; i <= names.length; ++i) {
                            String name = names[i - 1];
                            switch (types[i - 1]) {
                                case Types.BLOB:
                                    rv.put(name, new BlobValue(R.getBytes(i)));
                                    break;
                                case Types.BOOLEAN:
                                    rv.put(name, new BooleanValue(R.getBoolean(i)));
                                    break;
                                case Types.DOUBLE:
                                case Types.FLOAT:
                                case Types.REAL:
                                    rv.put(name, new DoubleValue(R.getDouble(i)));
                                    break;
                                case Types.BIGINT:
                                    rv.put(name, new LongValue(R.getInt(i)));
                                    break;
                                case Types.INTEGER:
                                case Types.SMALLINT:
                                    rv.put(name, new IntValue(R.getInt(i)));
                                    break;
                                case Types.VARCHAR:
                                    rv.put(name, new StringValue(R.getString(i)));
                                    break;
                                default:
                                    rv.put(name, new ObjectValue(R.getObject(i)));
                                    break;
                            }
                            if (R.wasNull()) {
                                rv.put(name, null);
                            }
                        }
                        state = 0;
                        return rv;
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    throw new RuntimeException("No more data!");
                }
            }

            @Override
            public boolean hasNext() {
                if (state == 0) {
                    fetch();
                }
                if (state == 1) {
                    return true;
                } else if (state == 2) {
                    return false;
                } else {
                    throw new RuntimeException("?");
                }
            }

            private void fetch() {
                boolean b;
                try {
                    b = R.next();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                if (b) {
                    state = 1;
                } else {
                    state = 2;
                }
            }
        }

        @Override
        public Iterator<Map<String, Value>> iterator() {
            if( alreadyIterated )
                throw new RuntimeException("You cannot iterate over the results more than once");
            alreadyIterated = true;
            return new ResultsIterator();
        }
    }

    public ParameterizedStatement(java.sql.Connection conn, String sql, Object... params) {
        try{
            stmt = conn.prepareStatement(sql);
            for (int i = 1; i <= params.length; ++i) {
                stmt.setObject(i, params[i - 1]);
            }
        } catch( SQLException e){
            throw new RuntimeException(e);
        }
    }

    static Results executeOneQuery(java.sql.Connection conn, String sql, Object... params ){
        try {
            return new ParameterizedStatement(conn, sql, params).executeQuery();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    static int executeOneUpdate(java.sql.Connection conn, String sql, Object... params ){
        try {
            return new ParameterizedStatement(conn, sql, params).executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
    
    public Results executeQuery() throws SQLException {
        return new Results(stmt.executeQuery());
    }

    public int executeUpdate() throws SQLException {
        return stmt.executeUpdate();
    }

    class CaseInsensitiveMap<V> implements Map<String, V> {

        private final HashMap<Object, V> M = new HashMap<>();
        private final Object never = new Object();  //never in the map

        private Object normalize(Object o) {
            if (!(o instanceof String)) {
                return never;
            }
            return ((String) o).toUpperCase();
        }

        @Override
        public int size() {
            return M.size();
        }

        @Override
        public boolean isEmpty() {
            return M.isEmpty();
        }

        @Override
        public boolean containsKey(Object o) {
            return M.containsKey(normalize(o));
        }

        @Override
        public boolean containsValue(Object o) {
            return M.containsValue(o);
        }

        @Override
        public V get(Object o) {
            return M.get(normalize(o));
        }

        @Override
        public V put(String k, V v) {
            return M.put(normalize(k), v);
        }

        @Override
        public V remove(Object o) {
            return M.remove(normalize(o));
        }

        @Override
        public void putAll(Map<? extends String, ? extends V> map) {
            for (var k : map.keySet()) {
                this.put(k, map.get(k));
            }
        }

        @Override
        public void clear() {
            M.clear();
        }

        @Override
        public Set<String> keySet() {
            Set<String> S = new HashSet<>();
            for (var k : M.keySet()) {
                S.add((String) k);
            }
            return S;
        }

        @Override
        public Collection<V> values() {
            return M.values();
        }

        @Override
        public Set<Entry<String, V>> entrySet() {
            Set<Entry<String,V>> r = new HashSet<>();
            for (var v : M.entrySet()) {
                r.add(new MyEntry((String) v.getKey(), v.getValue()));
            }
            return r;
        }

        class MyEntry implements java.util.Map.Entry {

            String key;
            V value;

            MyEntry(String k, V value) {
                this.key = k;
                this.value = value;
            }

            @Override
            public Object getKey() {
                return key;
            }

            @Override
            public Object getValue() {
                return value;
            }

            @Override
            public Object setValue(Object v) {
                return put(key, (V) v);
            }
        }
    }

    /**
     * A single value returned from an SQL database query. Use instanceof to aid
     * in determining which type to convert to. Example: Value v = ...; if( v
     * instanceof StringValue ){ String s = v.asString(); }
     *
     */
    public abstract class Value {

        /**
         * Return this value as a string. Throws RuntimeException if the value
         * can't be represented as a string.
         *
         * @return The value.
         */
        public String asString() {
            throw new RuntimeException("Cannot represent " + this.getClass().getName() + " as a String");
        }

        /**
         * Return this value as an object. Throws RuntimeException if the value
         * can't be represented as an object.
         *
         * @return The value.
         */
        public Object asObject() {
            throw new RuntimeException("Cannot represent " + this.getClass().getName() + " as an Object");
        }

        /**
         * Return this value as a string. Throws RuntimeException if the value
         * can't be represented as a string.
         *
         * @return The value.
         */
        public boolean asBoolean() {
            throw new RuntimeException("Cannot represent " + this.getClass().getName() + " as a boolean");
        }

        /**
         * Return this value as a double. Throws RuntimeException if the value
         * can't be represented as a double.
         *
         * @return The value.
         */
        public double asDouble() {
            throw new RuntimeException("Cannot represent " + this.getClass().getName() + " as a double");
        }

        /**
         * Return this value as an integer. Throws RuntimeException if the value
         * can't be represented as an integer.
         *
         * @return The value.
         */
        public int asInt() {
            throw new RuntimeException("Cannot represent " + this.getClass().getName() + " as an integer value");
        }

        /**
         * Return this value as a long. Throws RuntimeException if the value
         * can't be represented as a long.
         *
         * @return The value.
         */
        public long asLong() {
            return this.asInt();
        }

        /**
         * Return this value as a blob (an array of bytes). Throws
         * RuntimeException if the value can't be represented as a blob.
         *
         * @return The value.
         */
        public byte[] asBlob() {
            throw new RuntimeException("Cannot represent " + this.getClass().getName() + " as a blob");
        }

        /**
         * Convert this object to a string. Unlike asString, this function
         * always succeeds, although the returned string may not be particularly
         * useful.
         *
         * @return The string.
         */
        @Override
        public abstract String toString();
    }

    /**
     * A string value. Cannot be converted to a numeric or blob type.
     */
    public class StringValue extends Value {

        private final String data;

        StringValue(String s) {
            data = s;
        }

        @Override
        public String asString() {
            return data;
        }

        @Override
        public String toString() {
            return data;
        }
    }

    /**
     * A numeric value (integer or floating point). @see Value
     */
    public abstract class NumericValue extends Value {
    }

    public class BooleanValue extends Value {

        private final boolean d;

        public BooleanValue(boolean d) {
            this.d = d;
        }

        @Override
        public double asDouble() {
            return d ? 1.0 : 0.0;
        }

        @Override
        public int asInt() {
            return d ? 1 : 0;
        }

        @Override
        public String asString() {
            return "" + d;
        }

        @Override
        public String toString() {
            return "" + d;
        }
    }

    public class ObjectValue extends Value {

        private final Object d;

        public ObjectValue(Object d) {
            this.d = d;
        }

        @Override
        public Object asObject() {
            return d;
        }

        @Override
        public String toString() {
            return "" + d;
        }

    }

    /**
     * A floating point value. Can be converted to string or numeric type;
     * cannot be converted to blob type.
     */
    public class DoubleValue extends NumericValue {

        private final double d;

        public DoubleValue(double d) {
            this.d = d;
        }

        @Override
        public double asDouble() {
            return d;
        }

        @Override
        public int asInt() {
            return (int) d;
        }

        @Override
        public long asLong() {
            return (long) d;
        }

        @Override
        public String asString() {
            return "" + d;
        }

        @Override
        public String toString() {
            return "" + d;
        }
    }

    /**
     * An integer value. Can be converted to string or numeric type; cannot be
     * converted to blob type.
     */
    public class IntValue extends NumericValue {

        private final int d;

        public IntValue(int d) {
            this.d = d;
        }

        @Override
        public double asDouble() {
            return d;
        }

        @Override
        public int asInt() {
            return (int) d;
        }

        @Override
        public long asLong() {
            return (long) d;
        }

        @Override
        public String asString() {
            return "" + d;
        }

        @Override
        public String toString() {
            return "" + d;
        }

    }

    /**
     * A long integer value. Can be converted to string or numeric type; cannot
     * be converted to blob type.
     */
    public class LongValue extends NumericValue {

        private final long d;

        public LongValue(long d) {
            this.d = d;
        }

        @Override
        public double asDouble() {
            return d;
        }

        @Override
        public int asInt() {
            return (int) d;
        }

        @Override
        public long asLong() {
            return (long) d;
        }

        @Override
        public String asString() {
            return "" + d;
        }

        @Override
        public String toString() {
            return "" + d;
        }

    }

    /**
     * A blob (byte array) value. Cannot be converted to string using asString
     * (toString gives the size of the blob but not its contents). Cannot be
     * represented as a numeric type.
     */
    public class BlobValue extends Value {

        private final byte[] d;

        public BlobValue(byte[] d) {
            this.d = d;
        }

        @Override
        public String toString() {
            return "[Blob of " + d.length + " bytes]";
        }

        @Override
        public byte[] asBlob() {
            return d;
        }
    }

}
