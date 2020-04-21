/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DisMEMEberSource;

/**
 *
 * @author Owner
 */
public class Report {
    
    protected String reason;
    protected int postID;
    protected byte[] img;
    
    public Report(String reason, int postID, byte[] img)
    {
        this.reason = reason;
        this.postID = postID;
        this.img = img;
        
    }
    
    public String getReason()
    {
        return this.reason;
    }
    
    public int getpostID()
    {
        return this.postID;
    }
    
    public byte[] getImg()
    {
        return this.img;
    }
}
