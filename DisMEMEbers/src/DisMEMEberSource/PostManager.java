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
    //public PostManager instance;
    //public static Post instance = new Post();
    public void PostManager(){
        connection = getConnection();
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
            var stmt = connection.prepareStatement(
                    "create table posts ("
                            + "uid integer foreign key, "
                            + "pid integer primary key generated always as identity, "
                            + "caption varchar(180), "
                            + "imglocation varchar(52), "
                            + "date varchar(10), "
                            + ")");
        } catch(Exception e){
               throw new RuntimeException(e);
        }
    }
    public static void createPost(int userID, int postID,char text,char imageLocation,char date) throws SQLException{
                  //use post class / create post class instead of what is below 
        connection.createStatement().execute(
                "insert into post values"+
                Post.instance.getUserID()+
                Post.instance.getPostID()+
                Post.instance.getText()+
                Post.instance.getImageLocation()+
                Post.instance.getDate());
       
    } 
}