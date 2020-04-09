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
    public static Connection connection;
    //public PostManager instance;
    //public static Post instance = new Post();
    public void PostManager(){
        connection = setupTable();
    }
    
    private static final Connection JDBC_URL = DataBase.getInstance();
    
    public static Connection setupTable() 
    {
            try
            {
                connection.createStatement().execute("create table Post(userID int, postID int, text varchar(20), imageLocation varchar(20),date varchar(20)");
                return connection;
            } 
            catch(SQLException e)
            { 
                throw new RuntimeException(e);
            }
    }
    public static void createPost(int userID, int postID,char text,char imageLocation,char date) throws SQLException{
                  //use post class / create post class instead of what is below 
        connection.createStatement().execute(
                "insert into Post values"+
                Post.instance.getUserID()+
                Post.instance.getPostID()+
                Post.instance.getText()+
                Post.instance.getImageLocation()+
                Post.instance.getDate());
       
    } 
}