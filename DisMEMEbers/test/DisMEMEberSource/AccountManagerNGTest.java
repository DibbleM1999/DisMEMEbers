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

    /*
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
    */
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
    
}
