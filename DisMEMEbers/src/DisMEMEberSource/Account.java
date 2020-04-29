/*

 * To change this license header, choose License Headers in Project Properties.

 * To change this template file, choose Tools | Templates

 * and open the template in the editor.

 */
package DisMEMEberSource;

import java.util.ArrayList;



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

    protected boolean banned = false;
    
    protected float ban_time = 0;

    protected byte[] avatar = null;
    
    protected ArrayList<String> History = new ArrayList<String>(); // A basic history for testing functionality.
    

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

    

    public boolean isBanned()

    {

        return this.banned;

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
    
    public void resetUID() //AccountManager testing
    {
        this.next_UID = 1000;
    }
    
    public int nextid()
    {
        return this.next_UID;
    }
    
    public void add_history(String loc) //Adds a basic string of the meme they were on. Should be replaced with more abstract stuff later
    {
        this.History.add(loc);
    }
    
    //You know, if someone needs to hide they been on them naughy memes
    
    public void delete_history() //Delete all
    {
        this.History.clear();
    }
    
    public void delete_history_at_name(String loc) //Delete via name
    {
        for(int i=0; i<this.History.size(); i++ )
        {
            if(this.History.get(i) == loc)
            {
                this.History.remove(i);
                break;
            }
        }
    }
    
    public void delete_history_at_i(int index) //Delete via index
    {
        this.History.remove(index);
    }
    
    public ArrayList<String> get_history()
    {
        return this.History;
    }
    
    public String get_history_at_i(int index)
    {
        return this.History.get(index);
    }
    
    public void sendreport(String reason, int postID, byte[] img, PostManager P)
    {
        Report sendinfo = new Report(reason,postID,img);
        P.add_report(sendinfo);
    }
    
}
