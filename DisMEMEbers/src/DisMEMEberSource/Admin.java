/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DisMEMEberSource;

/**
 *
 * @author ebrown
 */
public class Admin extends Account{
    public Admin(String user, String password, String em)
    {
         super(user, password, em);
    }
    
    // Return the ID of the account that has been banned, otherwise return -1.
    public int Ban(int ID)
    {
        return -1;
    }
}
