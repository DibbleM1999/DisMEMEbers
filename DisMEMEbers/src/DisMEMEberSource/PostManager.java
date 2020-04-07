/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DisMEMEberSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
/**
 *
 * @author Brice
 */

public class PostManager {
    public static Connection connection;
    public PostManager instance;
    public void PostManager(){
        connection = main();
    }
    
    public static final String JDBC_URL = "jdbc:derby:zadb;create=true";
    public static Connection main() {
       try{
            Connection connection = DriverManager.getConnection(JDBC_URL);
            connection.createStatement().execute("create table Post(userID int, postID int, text varchar(20), imageLocation varchar(20),date varchar(20)");
            return connection;
      } catch(Exception e){ 
           throw new RuntimeException(e);
    }
    }
    public static void createPost(int userID, int postID,char text,char imageLocation,char date) throws SQLException{
                  //use post class / create post class instead of what is below 
        connection.createStatement().execute("insert into Post values"+userID+postID+text+imageLocation+date);
       
    } 
}
