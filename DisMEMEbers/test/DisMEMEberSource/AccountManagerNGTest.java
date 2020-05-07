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
        Account user = new Account("Ethan","TheBrunester22!", "comingthroughtheback@gmail.com");
        AccountManager.getInstance().add(user);
    }

    @AfterMethod
    public void tearDownMethod() throws Exception {
        AccountManager.getInstance().printTable();
        AccountManager.getInstance().Clear();
    }
    
    /**
     * Test of add method, of class AccountManager.
     */
    
    //being worked on
    
    @Test
    public void testAddUser1() throws Exception //To test adding a user
    {    
        Account user = new Account("Hayden", "Pass", "yeeyeeboy12@gmail.com");
        AccountManager.getInstance().add(new Account("Jack", "Pass", "boy12@gmail.com"));
        int testID = AccountManager.getInstance().add(user);
        
        assertEquals(AccountManager.getInstance().getUser(testID), "Hayden");
        assertEquals(AccountManager.getInstance().getPassword(testID), "Pass");
        
        testID = AccountManager.getInstance().getID("Jack");
        assertEquals(AccountManager.getInstance().getUser(testID), "Jack");
        assertEquals(AccountManager.getInstance().getPassword(testID), "Pass");
    }
    
//    @Test
//    public void testAddUser2() throws Exception //To test adding a user and check if it exists via ID
//    {
//        AccountManager A = new AccountManager();
//        
//        Account user = new Account("Hayden", "Pass", "yeeyeeboy12@gmail.com");
//        int testID = A.add(user);
//        assertEquals(A.getUser(testID + 1).username,"");
//        assertEquals(A.getUser(testID + 1).password,"");
//        A.Clear();
//    }
//    
//    @Test
//    public void testAddUser3() throws Exception //To test adding a user and check if it exists via Username.
//    {
//        AccountManager A = new AccountManager();
//        
//        Account user = new Account("Hayden","Pass", "yeeyeeboy12@gmail.com");
//        int testID = A.add(user);
//        assertEquals(A.getID("Hayden"),testID);
//        A.Clear();
//    }
//    
//    @Test
//    public void testAddUser4() throws Exception //To test adding a user and check if it exists via Username.
//    {
//        AccountManager A = new AccountManager();
//        
//        Account user = new Account("Hayden","Pass", "yeeyeeboy12@gmail.com");
//        A.add(user);
//        assertEquals(A.getID("Hayde"),-1);
//        A.Clear();
//    }
//    
//    @Test
//    public void printTable() throws Exception
//    {
//        AccountManager.getInstance().printTable();
//    }
    
    @Test
    public void testRemoveUser1() throws Exception //To test removing a user by searching ID.
    {
        Account user = new Account("Hayden","Pass", "yeeyeeboy12@gmail.com");
        AccountManager.getInstance().add(user);
        int TestID = AccountManager.getInstance().getID("Hayden");
        AccountManager.getInstance().printTable();
        AccountManager.getInstance().delete_by_ID(TestID);
        assertEquals(AccountManager.getInstance().getID("Hayden"), -1);
    }
    
    @Test
    public void updateEmail() throws Exception
    {
        int ID = AccountManager.getInstance().getID("Ethan");
        AccountManager.getInstance().setEmail(ID, "OGyeeyee@hotmail.com");
        assertNotEquals(AccountManager.getInstance().getEmail(ID), "comingthroughtheback@gmail.com");
    }
    
}
