/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DisMEMEberSource;

import java.util.ArrayList;
/**
 *
 * @author Owner
 */
public class Admin extends Account 
{
    protected Report reporton = null;
    
    protected Admin(String user, String pass, String em)
    {
       super(user, pass, em);
    }
    
    
    public Account get_Account_by_name(String name, AccountManager A)
    {
        return A.instance.getUser(A.instance.getID(name));
    }
    
    public Account get_Account_by_ID(int ID, AccountManager A)
    {
        return A.instance.getUser(ID);
    }
    
    public ArrayList<String> get_history_by_name(String name, AccountManager A)
    {
        return this.get_Account_by_name(name, A).get_history();
    }
    
    public ArrayList<String> get_history_by_Id(int ID, AccountManager A)
    {
        return this.get_Account_by_ID(ID, A).get_history();
    }
    
    public void delete_history_by_name(String name,AccountManager A) //Might be a problem to have because of similar names
    {
       this.get_Account_by_name(name, A).delete_history();
    }
    
    public void delete_history_by_ID(int ID, AccountManager A)
    {
        this.get_Account_by_ID(ID, A).delete_history();
    }
    
    public void delete_user(int ID, AccountManager A) //Add the ability to store deleted accounts for a certain period of time for possible recovery.
    {
        A.delete_by_ID(ID);
    }
    
    public void delete_avatar_by_ID(int ID, AccountManager A)
    {
        this.get_Account_by_ID(ID,A).setAvatar(null);
    }
    
    public void ban_by_ID( int ID, AccountManager A, float time) //Time will most likely be in minutes or hours.
    {
        this.get_Account_by_ID(ID, A).banned = true;
        this.get_Account_by_ID(ID, A).ban_time = time; //-1 for permaban
    }
    
    public void getreport(PostManager P)
    {
        if(this.reporton == null)
        {
            this.reporton = P.getReport();
        }
        else
        {
            return;
            //tell admin to finish report
        }
        
    }
    
    public void reportPositive(PostManager P)
    {
        //This is when the post breaks any rules
        
            //more code to impliment later:
            
        if(this.reporton != null)
        {
            P.deletePost(this.reporton.getpostID());
        
            this.reporton = null;
        }
        else
        {
            return;
            //send back a problem message
        }
            
            //bans can be done alsewhere.
        
    }
    
    public void reportNegative()
    {
        //This is when the post breaks no rules
        this.reporton = null;
    }
}
