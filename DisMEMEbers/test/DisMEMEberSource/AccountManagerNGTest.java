/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DisMEMEberSource;

import static org.testng.Assert.*;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 *
 * @author hridgeway
 */
public class AccountManagerNGTest {
    
    public AccountManagerNGTest() {
    }

    
    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @BeforeMethod
    public void setUpMethod() throws Exception {
    }

    @AfterMethod
    public void tearDownMethod() throws Exception {
    }
    
    /**
     * Test of add method, of class AccountManager.
     */
    
    //being worked on
    @Test
    public void testGetUser1() //To test if users can be accessed
    {
        AccountManager A = new AccountManager();
        
        assertNotEquals(A.getUser(1000),-1);
        
    }
    
    @Test
    public void testAddUser1() throws Exception //To test adding a user
    {
        AccountManager A = new AccountManager();
        
        Account user = new Account("Hayden","Pass", "yeeyeeboy12@gmail.com");
        int testID = A.add(user);
        assertEquals(A.getUser(testID).username,"Hayden");
        assertEquals(A.getUser(testID).password,"Pass");
        A.Clear();
    }
    
    @Test
    public void testAddUser2() throws Exception //To test adding a user and check if it exists via ID
    {
        AccountManager A = new AccountManager();
        
        Account user = new Account("Hayden", "Pass", "yeeyeeboy12@gmail.com");
        int testID = A.add(user);
        assertEquals(A.getUser(testID + 1).username,"");
        assertEquals(A.getUser(testID + 1).password,"");
        A.Clear();
    }
    
    @Test
    public void testAddUser3() throws Exception //To test adding a user and check if it exists via Username.
    {
        AccountManager A = new AccountManager();
        
        Account user = new Account("Hayden","Pass", "yeeyeeboy12@gmail.com");
        int testID = A.add(user);
        assertEquals(A.getID("Hayden"),testID);
        A.Clear();
    }
    
    @Test
    public void testAddUser4() throws Exception //To test adding a user and check if it exists via Username.
    {
        AccountManager A = new AccountManager();
        
        Account user = new Account("Hayden","Pass", "yeeyeeboy12@gmail.com");
        A.add(user);
        assertEquals(A.getID("Hayde"),-1);
        A.Clear();
    }
    
    
    @Test
    public void testRemoveUser1() throws Exception //To test removing a user by searching ID.
    {
        AccountManager A = new AccountManager();
        Account user = new Account("Hayden","Pass", "yeeyeeboy12@gmail.com");
        int testID = A.add(user);
        assertEquals(A.getID("Hayden"),testID);
        A.delete_by_ID(testID);
        assertEquals(A.getID("Hayden"),-1);
    }
}
