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
public class AccountManager {
    //being worked on
    protected int num_acc; //Account count
    protected ArrayList<Account> Userlist = new ArrayList<Account>(); //list of users. Holds ints until a user class is made to fill the list.
    
    protected AccountManager()
    {
        //nothing yet. add file parsing later
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
        }
        catch(Exception e)
        {
            throw(e);
        }
    }
    
    public int add(Account newuser)//finish when user class is made
    {
        Userlist.add(newuser);
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
    
    public int getID(String user)//Will return the ID of the given user. -1 if none.
    {
        for(int i = 0; i < Userlist.size(); i++)
        {
            if(Userlist.get(i).getUsername() == user)
                return Userlist.get(i).getUID();
        }
        
       return -1;
    }
    
    public void delete_by_ID(int ID)
    {
        for(int i = 0; i < Userlist.size(); i++)
        {
            if(Userlist.get(i).getUID() == ID)
            {
                Userlist.remove(i);
                System.out.print("Deleted");
                break;
            }   
        }
    }
    
    public void Clear() //KILL EVERYONE
    {
        Userlist.get(0).resetUID();
        Userlist.clear();
    }
    
}
