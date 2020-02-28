/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DisMEMEberSource;

/**
 *
 * @author Ethan Brown
 */
public class Account {
    static int next_UID = 1000;
    
    protected String username;
    protected String password;
    protected int UID = -1;
    protected byte[] avatar = null;
    
    public Account(String user, String pass)
    {
        this.username = user;
        this.password = pass;
        
        if(this.UID == -1 || this.UID < next_UID)
        {
            this.UID = next_UID;
            next_UID++;
        }
    }
    
}
