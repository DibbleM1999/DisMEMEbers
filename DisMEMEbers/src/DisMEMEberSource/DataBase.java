/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DisMEMEberSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 *
 * @author nickb & dionc
 */
public class DataBase {
    private static Connection conn;
    public static final String JDBC_URL = "jdbc:derby:disMEMEber_db.sql;create=true";
    
    static Connection getInstance(){
       if( conn == null){
           try{conn = DriverManager.getConnection(JDBC_URL);}
           catch(Exception e){
               throw new RuntimeException(e);
           }
       }
       return conn;
    }
}
