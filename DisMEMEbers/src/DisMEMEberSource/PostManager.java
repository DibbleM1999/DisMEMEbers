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
/**
 *
 * @author Brice, Kyle, and Dion
 */

public class PostManager {
    public static Connection connection;
    public static PostManager instance;

    private static Post Post(String userID, String text, String image, String date) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    //public static Post instance = new Post();
    public void PostManager(){
        instance.connection = getConnection();
        setupTable();
    }
    public static PostManager getIntance(){
        if(instance == null){
            instance = new PostManager();
        }
        return instance;
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
    
    public static Post readPost(int postID){
        try{
            String text = "",image = "",date = "";
            int userID = 0;
            var stmt = instance.connection.prepareStatement("select imagelocation from posts where pid=?");
             stmt.setInt(1, postID);
             var result = stmt.executeQuery();
             if(result.next()){
                image = result.getString(1);
             }
             var stmt1 = instance.connection.prepareStatement("select caption from posts where pid=?");
             stmt1.setInt(1, postID);
             var result1 = stmt.executeQuery();
             if(result1.next()){
                 text = result.getString(1);
             }
              var stmt2 = instance.connection.prepareStatement("select date from posts where pid=?");
             stmt2.setInt(1, postID);
             var result2 = stmt.executeQuery();
             if(result2.next()){
                 date = result.getString(1);
             }
              var stmt3 = instance.connection.prepareStatement("select uid from posts where pid=?");
             stmt3.setInt(1, postID);
             var result3 = stmt.executeQuery();
             if(result3.next()){
                 userID = result.getInt(1);
             }else{
                 
             }
             return new Post(userID,text,image,date);
             
             
            //char image = stmt.executeQuery();
            //int userID, char text, char imageLocation, char dateOfPost
            //Post new_post();
        }
       catch(Exception e){
            throw new RuntimeException(e);
}
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
   }
    
    public static void deletePost( int postID ) {
        try{
            var stmt = instance.connection.prepareStatement("delete from posts where pid=?",postID);
            stmt.executeUpdate(); 
        }
        catch(Exception e){
            throw new RuntimeException(e);
        }
    }
}