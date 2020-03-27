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
    
    public void delete_history_by_name(String name,AccountManager A)
    {
       this.get_Account_by_name(name, A).delete_history();
    }
    
    public void delete_history_by_ID(int ID, AccountManager A)
    {
        this.get_Account_by_ID(ID, A).delete_history();
    }
    
    public void delete_user(int ID, AccountManager A)
    {
        A.delete_by_ID(ID);
    }
    
    //add Deletion of specific items via the specific delete methods in Account...Later.
   
}
