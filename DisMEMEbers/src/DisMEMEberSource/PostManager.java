/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DisMEMEberSource;
import java.sql.Connection;
import java.sql.SQLException;
/**
 *
 * @author Brice, Kyle, and Dion
 */

public class PostManager {
    //public PostManager instance;
    //public static Post instance = new Post();
    public void PostManager(){
        setupTable();
    }
    
    private static final Connection JDBC_URL = DataBase.getInstance();
    
    public static void setupTable() 
    {
            var stmt = JDBC_URL.prepareStatement(
            "create table posts ("
              + "uid integer foreign key, "
              + "pid integer primary key generated always as identity, "
              + "caption varchar(180), "
              + "imglocation varchar(52), "
              + "date varchar(10), "
              + ")");
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