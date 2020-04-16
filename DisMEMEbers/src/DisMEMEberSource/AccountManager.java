package DisMEMEberSource;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.File;
import java.util.ArrayList;
//import java.util.List;
/**
 *
 * @author hridgeway
 */

/*
- Update Functionality.
*/

public class AccountManager {
    //being worked on
    protected int num_acc; //Account count
    protected ArrayList<Account> Userlist = new ArrayList<>(); //list of users. Holds ints until a user class is made to fill the list.
    private static AccountManager getInstance;
    
    protected AccountManager() 
    {
        // Weird Recursion bug causing stack overflow.
        try
        {
            if( !new File("disMEMEber_db.sql").exists() )
            {
                AccountManager.createDatabase();
            }
        }
        catch(Exception e)
        {
            throw new RuntimeException(e);
        }
    }
    
    public static AccountManager getInstance()
    {
        if (getInstance == null)
            getInstance = new AccountManager();
        return getInstance;
    }
    
    static void createDatabase() throws Exception
    {
        var stmt = DataBase.getInstance().prepareStatement(
                "create table users ("
              + "uid integer primary key generated always as identity, "
              + "username varchar(32), "
              + "password varchar(32), "
              + "email varchar(52), "
              + "avatar blob, "
              + "isAdmin int"
              + ")");
        stmt.executeUpdate();
    }
    
    public int add(Account newuser) throws Exception//finish when user class is made 
    {
        Userlist.add(newuser);
        
        var stmt = DataBase.getInstance().prepareStatement("insert into users "
                + "(username, password, email, avatar, isAdmin) "
                + "values (?, ?, ?, ?)");
        stmt.setString(1, newuser.username);
        stmt.setString(2, newuser.password);
        stmt.setString(3, newuser.email);
        stmt.setBytes(4, new byte[]{3, 1, 4, 1, 5, 9});
        stmt.setInt(5, newuser.isAdmin);
        stmt.executeUpdate();
            
        return newuser.getUID();
    }
    
    public Account getUser(int ID) throws Exception//will return user once created
    {
        for(int i = 0; i < Userlist.size(); i++)
        {
            if(Userlist.get(i).UID == ID)
                return Userlist.get(i);
        }
        
        var stmt = DataBase.getInstance().prepareStatement("select username from users where uid=?");
        stmt.setInt(1, ID);
        var result = stmt.executeQuery();
        String user = result.getString(1);
        System.out.println(user);
        
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
        var stmt = DataBase.getInstance().prepareStatement("select uid from users where username=? and password IS NOT NULL");
        stmt.setString(1, user);
        var result = stmt.executeQuery();
        int uid = result.getInt(1);
        System.out.println(uid);
        return uid;
    }
    
    public int getIsAdmin(int UID) throws Exception
    {
        var stmt = DataBase.getInstance().prepareStatement("select isAdmin from users where uid=?");
        stmt.setInt(1, UID);
        var result = stmt.executeQuery();
        int admin = result.getInt(1);
        return admin;
    }
    
    public byte[] getAvatar(int ID) throws Exception
    {
        var stmt = DataBase.getInstance().prepareStatement("select avatar from users where uid=?");
        stmt.setInt(1, ID);
        var result = stmt.executeQuery();
        byte[] avatar = result.getBytes(1);
        return avatar;
    }
    
    public void setAvatar(int ID, byte[] data) throws Exception
    {
        var stmt = DataBase.getInstance().prepareStatement("update users set avatar=? where uid=?");
        stmt.setBytes(1, data);
        stmt.setInt(2, ID);
        stmt.executeUpdate();
    }

    public String getEmail(int ID) throws Exception
    {
        var stmt = DataBase.getInstance().prepareStatement("select email from users where uid=?");
        stmt.setInt(1, ID);
        var result = stmt.executeQuery();
        String email = result.getString(1);
        return email;
    }
    
    public void setEmail(int ID, String data) throws Exception
    {
        var stmt = DataBase.getInstance().prepareStatement("update users set email=? where uid=?");
        stmt.setString(1, data);
        stmt.setInt(2, ID);
        stmt.executeUpdate();
    }
    
    public  String getPassword(int ID) throws Exception
    {
        var stmt = DataBase.getInstance().prepareStatement("select password from users where uid=?");
        stmt.setInt(1, ID);
        var result = stmt.executeQuery();
        String password = result.getString(1);
        return password;
    }
    
    public void setPassword(int ID, String data) throws Exception
    {
        var stmt = DataBase.getInstance().prepareStatement("update users set password=? where uid=?");
        stmt.setString(1, data);
        stmt.setInt(2, ID);
        stmt.executeUpdate();
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
        var stmt = DataBase.getInstance().prepareStatement("delete from users where uid=?");
        stmt.setInt(1, ID);
        stmt.executeUpdate();
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
    
    public void Clear() //KILL EVERYONE
    {
        Userlist.get(0).resetUID();
        Userlist.clear();
    }
    
}
