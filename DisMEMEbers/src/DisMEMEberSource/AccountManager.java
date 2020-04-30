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
        if(AccountManager.getInstance().containsUser(newuser.username))
            return -1;
        
        Userlist.add(newuser);
        
        var stmt = DataBase.getInstance().prepareStatement("insert into users "
                + "(username, password, email, avatar, isAdmin) "
                + "values (?, ?, ?, ?, ?)");
        stmt.setString(1, newuser.username);
        stmt.setString(2, newuser.password);
        stmt.setString(3, newuser.email);
        stmt.setBytes(4, new byte[]{3, 1, 4, 1, 5, 9});
        stmt.setInt(5, newuser.isAdmin);
        stmt.executeUpdate();
            
        return AccountManager.getInstance().getID(newuser.username);
    }
    
    public String getUser(int ID) throws Exception
    {   
        var stmt = DataBase.getInstance().prepareStatement("select username from users where uid=?");
        stmt.setInt(1, ID);
        var result = stmt.executeQuery();
        
        if(result.next())
        {
            String user = result.getString(1);
            return user;
        }
        
        else
            return null;
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
        if(result.next())
        {
            int uid = result.getInt(1);
            return uid;
        }
        
        else
            return -1;
    }
    
    public boolean containsUser(String user)throws Exception
    {
        return AccountManager.getInstance().getID(user) > -1;
    }
    
    public int getIsAdmin(int UID) throws Exception
    {
        var stmt = DataBase.getInstance().prepareStatement("select isAdmin from users where uid=?");
        stmt.setInt(1, UID);
        var result = stmt.executeQuery();
        if(result.next())
        {
        int admin = result.getInt(1);
        return admin;
        }
        
        else
            return -1;
    }
    
    public byte[] getAvatar(int ID) throws Exception
    {
        var stmt = DataBase.getInstance().prepareStatement("select avatar from users where uid=?");
        stmt.setInt(1, ID);
        var result = stmt.executeQuery();
        if(result.next())
        {
            byte[] avatar = result.getBytes(1);
            return avatar;
        }
        
        else
            return null;
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
        if(result.next())
        {
            String email = result.getString(1);
            return email;
        }
        
        else
            return null;
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
        if(result.next())
        {
            String password = result.getString(1);
            return password;
        }
        
        else
            return null;
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
    
   public void printTable() throws Exception
   {
       var stmt = DataBase.getInstance().prepareStatement("select * from users");
       var request = stmt.executeQuery();
       var requestMetaData = request.getMetaData();
       
       int NumColumns = requestMetaData.getColumnCount();
       
       System.out.println("----- TABLE PRINT -----");
       
       while(request.next())
       {
           for(int i = 1; i <= NumColumns; i++)
           {
               System.out.print(request.getString(i) + " ");
           }
           
           System.out.println();
       }
       
        System.out.println("----- END PRINT -----");
   }
    
    public void Clear() throws Exception //KILL EVERYONE
    {
        Userlist.clear();
        var stmt = DataBase.getInstance().prepareStatement("truncate table users");
        stmt.executeUpdate();
    }
    
}
