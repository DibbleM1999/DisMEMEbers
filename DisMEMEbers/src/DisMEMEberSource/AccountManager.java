package DisMEMEberSource;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.ArrayList;
//import java.util.List;
/**
 *
 * @author hridgeway
 */

/*
CRUD REVIEW
We Have
- Creation of an account
- Deletion of an account by UID

We Need
- Reading Functionality.
- Update Functionality.
*/

public class AccountManager {
    //being worked on
    protected int num_acc; //Account count
    protected ArrayList<Account> Userlist = new ArrayList<>(); //list of users. Holds ints until a user class is made to fill the list.
    public static AccountManager instance = new AccountManager();
    
    protected AccountManager()
    {
        //nothing yet. add file parsing later with database.
    }
    
    void createDatabase() throws Exception
    {
        try(var conn = java.sql.DriverManager.getConnection("jdbc:derby:disMEMEber_db.sql;create=true"))
        {
            var stmt = conn.prepareStatement(
                    "create table users ("
                  + "uid integer primary key generated always as identity, "
                  + "username varchar(32), "
                  + "password varchar(32), "
                  + "email varchar(52), "
                  + "avatar blob"
                  + ")");
            stmt.executeUpdate();
            
            
            stmt = conn.prepareStatement(
                    "create table follow ("
                  + "fuid integer references users(uid), "
                  + "feduid integer references users(uid), "
                  + ")");
            stmt.executeUpdate();
        }
        catch(Exception e)
        {
            throw(e);
        }
    }
    
    public int add(Account newuser) throws Exception//finish when user class is made
    {
        Userlist.add(newuser);
        try (var conn = java.sql.DriverManager.getConnection("jdbc:derby:disMEMEber_db.sql"))
        {
            var stmt = conn.prepareStatement("insert into users "
                    + "(username, password, email, avatar) "
                    + "values (?, ?, ?, ?)");
            stmt.setString(1, newuser.username);
            stmt.setString(2, newuser.password);
            stmt.setString(3, newuser.email);
            stmt.setBytes(4, new byte[]{3, 1, 4, 1, 5, 9});
            stmt.executeUpdate();
        }
        catch(Exception e)
        {
            throw(e);
        }
        return newuser.getUID();
    }
    
    public Account getUser(int ID) //will return user once created
    {
        for(int i = 0; i < Userlist.size(); i++)
        {
            if(Userlist.get(i).UID == ID)
                return Userlist.get(i);
        }
        
        Account fail = new Account("","",""); //Blank account to show there is no account of the given ID.
        return fail;
    }
    
    public int getID(String user) throws Exception//Will return the ID of the given user. -1 if none.
    {
        //for(int i = 0; i < Userlist.size(); i++)
        //{
        //    if(Userlist.get(i).getUsername() == user)
        //        return Userlist.get(i).getUID();
        //}
        try(var conn = java.sql.DriverManager.getConnection("jdbc:derby:disMEMEber_db.sql"))
        {
            var stmt = conn.prepareStatement("select uid from users where username=? and password IS NOT NULL");
            stmt.setString(1, user);
            var result = stmt.executeQuery();
            int uid = result.getInt(1);
            System.out.println(uid);
            return uid;
        }
        catch(Exception e)
        {
            throw(e);
        }
    }
    

    public void delete_by_ID(int ID) throws Exception
    {
        //for(int i = 0; i < Userlist.size(); i++)
        //{
        //    if(Userlist.get(i).getUID() == ID)
        //    {
        //        Userlist.remove(i);
        //        System.out.print("Deleted");
        //        break;
        //    }   
        //}
        try(var conn = java.sql.DriverManager.getConnection("jdbc:derby:disMEMEber_db.sql"))
        {
            var stmt = conn.prepareStatement("delete from users where uid=?");
            stmt.setInt(1, ID);
            stmt.executeUpdate();
        }
        catch(Exception e)
        {
            throw(e);
        }
    }
    
    public boolean name_check(String username)
    {
        for(int i=0; i<Userlist.size();i++)
        {
            if(Userlist.get(i).getUsername() == null ? username == null : Userlist.get(i).getUsername().equals(username))
            {
                return true;
            }
        }
        return false;
    }
    
    public boolean email_check(String mail)
    {
        for(int i=0; i<Userlist.size();i++)
        {
            if(Userlist.get(i).getEmail() == null ? mail == null : Userlist.get(i).getEmail().equals(mail))
            {
                return true;
            }
        }
        return false;
    }
    
    public boolean name_check(String username)
    {
        for(int i=0; i<Userlist.size();i++)
        {
            if(Userlist.get(i).getUsername() == username)
            {
                return true;
            }
        }
        return false;
    }
    
    public boolean email_check(String mail)
    {
        for(int i=0; i<Userlist.size();i++)
        {
            if(Userlist.get(i).getEmail() == mail)
            {
                return true;
            }
        }
        return false;
    }
    
    public void Clear() //KILL EVERYONE
    {
        Userlist.get(0).resetUID();
        Userlist.clear();
    }
    
    
    public String userRelationship(String user, String other, boolean following) throws Exception //Will return the String of the followed user. -1 if none.
    {                                               // True for following, false for unfollowing
  
        try(var conn = java.sql.DriverManager.getConnection("jdbc:derby:disMEMEber_db.sql"))
        {
            if (following)
            {
                var stmt = conn.prepareStatement("select fuid from follow where fuid=? and feduid=?");
                stmt.setString(1, user);
                stmt.setString(2, other);
                var result = stmt.executeQuery();
                if (result != null)
                {
                    return "Already following";
                }
                else
                {
            
                    stmt = conn.prepareStatement("insert into follow "
                            + "(fuid, feduid) "
                            + "values (?, ?)");
                    stmt.setString(1, user);
                    stmt.setString(2, other);
                    stmt.executeUpdate();
                    return other;
                }
            }
            else
            {
                var stmt = conn.prepareStatement("select fuid from follow where fuid=? and feduid=?");
                stmt.setString(1, user);
                stmt.setString(2, other);
                var result = stmt.executeQuery();
                if (result != null)
                {
                    return "Not already following";
                }
                else
                {
            
                    stmt = conn.prepareStatement("delete from follow where fuid=? and feduid=?");
                    stmt.setString(1, user);
                    stmt.setString(2, other);
                    stmt.executeUpdate();
                    return other;
                }
            }
        }
        catch(Exception e)
        {
            throw(e);
        }
    }
    
    public ArrayList<String> seeWhosFollowing(String user) throws Exception // Returns who's following the user
    {
        
        try(var conn = java.sql.DriverManager.getConnection("jdbc:derby:disMEMEber_db.sql"))
        {
                var stmt = conn.prepareStatement("select fuid from follow where feduid=?");
                stmt.setString(1, user);
                var result = stmt.executeQuery();
                return (ArrayList<String>) result;
        }
        catch(Exception e)
        {
            throw(e);
        }
    }
    
    public ArrayList<String> seeWhoTheyFollowing(String user) throws Exception  // Returns who user is following
    {
        
        try(var conn = java.sql.DriverManager.getConnection("jdbc:derby:disMEMEber_db.sql"))
        {
                var stmt = conn.prepareStatement("select feduid from follow where fuid=?");
                stmt.setString(1, user);
                var result = stmt.executeQuery();
                return (ArrayList<String>) result;
        }
        catch(Exception e)
        {
            throw(e);
        }
    }
}
