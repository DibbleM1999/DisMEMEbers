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
    protected String email;
    protected int UID = -1;
    protected byte[] avatar = null;
    
    public Account(String user, String pass, String em)
    {
        this.username = user;
        this.password = pass;
        this.email = em;
        
        if(this.UID == -1 || this.UID < next_UID)
        {
            this.UID = next_UID;
            next_UID++;
        }
    }
    
    public int getUID()
    {
       return this.UID;
    }
    
    public String getUsername()
    {
        return this.username;
    }
    
    public String getEmail()
    {
        return this.email;
    }
    
    public String getPassword()
    {
        return this.password;
    }
    
    public byte[] getAvatar()
    {
        if(this.avatar != null)
            return avatar;
        
        return null;
    }
    
    public boolean setAvatar(byte[] img)
    {
        this.avatar = img;
        
        return this.avatar != null;
    }
    
}
