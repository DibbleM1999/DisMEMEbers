/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DisMEMEberSource;
import static DisMEMEberSource.DataBase.JDBC_URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

//Hayden and Alex
import java.util.ArrayList;
import DisMEMEberSource.Report;

/**
 *
 * @author Brice, Kyle, and Dion
 */

public class PostManager {
    public static Connection connection;
    public static PostManager instance;
    //public static Post instance = new Post();
    public void PostManager(){
        instance.connection = getConnection();
        setupTable();
    }
    
    //private static final Connection JDBC_URL = DataBase.getInstance();
    private static Connection getConnection(){
        if( connection == null){
           try{connection = DataBase.getInstance();}
           catch(Exception e){
               throw new RuntimeException(e);
           }
       }
       return connection;
    }
    
    public static void setupTable() 
    {
        try {
            var stmt = instance.connection.prepareStatement(
                    "create table posts ("
                            + "uid integer foreign key, "
                            + "pid integer primary key generated always as identity, "
                            + "caption varchar(180), "
                            + "imglocation varchar(52), "
                            + "date varchar(10), "
                            + ")");
            stmt.executeUpdate();
        } catch(Exception e){
               throw new RuntimeException(e);
        }
    }
    
    public static void createPost(Post new_post){
                  //use post class / create post class instead of what is below 
        try {
            var stmt = instance.connection.prepareStatement(
                "insert into post values"+
                new_post.getUserID()+
                new_post.getPostID()+
                new_post.getText()+
                new_post.getImageLocation()+
                new_post.getDate());
            stmt.executeUpdate();
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }
    
//    public static void readPost(int postID){
//        // problems/blocker do we as a class want to alow updates to a post 
//        // how do we save off the information given by a select statement 
//        // dion needs new audio to comuicate better
//        // how to pull information out of tables
//        try{
//            var stmt = instance.connection.prepareStatement("select imagelocation from posts where pid=?",postID);
//            char image = stmt.executeQuery();
//            int userID, char text, char imageLocation, char dateOfPost
//            Post new_post();
//        }
//        catch(Exception e){
//            throw new RuntimeException(e);
//        }
//    }
    
    public static void deletePost( int postID ) {
        try{
            var stmt = instance.connection.prepareStatement("delete from posts where pid=?",postID);
            stmt.executeUpdate(); 
        }
        catch(Exception e){
            throw new RuntimeException(e);
        }
    }
    
    
    //Hayden and alex custom things we needed
    
    protected ArrayList<Report> reports;
    
    public void add_report(Report r)
    {
        //Could add limit to number of reports at one time. Maybe 1000
        reports.add(r);
    }
    
    public Report getReport()
    {
        Report temp;
        if(this.reports == null)
        {
            temp = new Report("None",-1,null);
            return temp;
        }
        
        temp = this.reports.get(0);
        this.reports.remove(0);
        
        return temp;
        
    }
}
