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
    public static AccountManager instance = new AccountManager();
    
    protected AccountManager()
    {
        //nothing yet. add file parsing later with database.
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
    
}
