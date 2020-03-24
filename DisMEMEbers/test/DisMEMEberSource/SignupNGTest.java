/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DisMEMEberSource;


import java.io.UnsupportedEncodingException;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.URL;
import java.net.URLEncoder;
import static org.testng.Assert.*;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class SignupNGTest {

    public SignupNGTest() {
    }

    static CookieManager cookieManager = new CookieManager();

    @BeforeClass
    public static void startServer() {
        Main.startOrStopJetty(true);
        CookieHandler.setDefault(cookieManager);
    }

    @AfterClass
    public static void stopServer() {
        Main.startOrStopJetty(false);
    }

    @BeforeMethod
    public void clearCookies() {
        cookieManager.getCookieStore().removeAll();
    }

    @BeforeMethod
    public void clearAccounts() {
        Utils.fetch("/srv/clearaccounts");
    }

    @Test
    public void testSignup() throws UnsupportedEncodingException {
        String s = Utils.fetch("/srv/register"
                + "?email=" + URLEncoder.encode("bob@example.com", "UTF-8")
                + "&password=" + URLEncoder.encode("s3cr3t", "UTF-8")
        );
        assertTrue(s.equals("CREATED"));
    }

    @Test
    public void testSignup2() throws UnsupportedEncodingException {
        String s = Utils.fetch("/srv/register"
                + "?email=" + URLEncoder.encode("bob@example.com", "UTF-8")
                + "&password=" + URLEncoder.encode("s3cr3t", "UTF-8")
        );
        assertTrue(s.contains("CREATED"));
        cookieManager.getCookieStore().removeAll();
        s = Utils.fetch("/srv/register"
                + "?email=" + URLEncoder.encode("bob@example.com", "UTF-8")
                + "&password=" + URLEncoder.encode("xyz", "UTF-8")
        );
        assertTrue(s.equals("DUPLICATE"));
    }

    @Test
    public void testSignupNoEmail() throws UnsupportedEncodingException {
        String s = Utils.fetch("/srv/register"
                + "?password=" + URLEncoder.encode("s3cr3t", "UTF-8")
        );
        assertTrue(s.equals("ERROR"));
    }

    @Test
    public void testSignupNoPASSWORd() throws UnsupportedEncodingException {
        String s = Utils.fetch("/srv/register"
                + "?email=" + URLEncoder.encode("bob@example.com", "UTF-8")
        );
        assertTrue(s.equals("ERROR"));
    }

    @Test
    public void testSignupNothingAtAll() throws UnsupportedEncodingException {
        String s = Utils.fetch("/srv/register");
        assertTrue(s.equals("ERROR"));
    }

    @Test
    public void testSignupAndLogin() throws UnsupportedEncodingException {
        String s = Utils.fetch("/srv/register"
                + "?email=" + URLEncoder.encode("bob@example.com", "UTF-8")
                + "&password=" + URLEncoder.encode("s3cr3t", "UTF-8")
        );
        assertTrue(s.equals("CREATED"));
        s = Utils.fetch("/srv/who");
        assertTrue(s.contains("You are bob@example.com"));
    }

    @Test
    public void testSignupAndLogin2() throws UnsupportedEncodingException {
        String s = Utils.fetch("/srv/register"
                + "?email=" + URLEncoder.encode("bob@example.com", "UTF-8")
                + "&password=" + URLEncoder.encode("s3cr3t", "UTF-8")
        );
        assertTrue(s.equals("CREATED"));
        cookieManager.getCookieStore().removeAll();
        s = Utils.fetch("/srv/who");
        assertTrue(s.contains("Don't know who you are"));
        s = Utils.fetch("/srv/register"
                + "?email=" + URLEncoder.encode("bob@example.com", "UTF-8")
                + "&password=" + URLEncoder.encode("s3cr3t", "UTF-8")
        );
        assertTrue(s.equals("DUPLICATE"));
        s = Utils.fetch("/srv/who");
        assertTrue(s.contains("Don't know who you are"));
    }

    @Test
    public void testSignupAndLogin3() throws UnsupportedEncodingException {
        String s = Utils.fetch("/srv/register"
                + "?email=" + URLEncoder.encode("bob@example.com", "UTF-8")
                + "&password=" + URLEncoder.encode("s3cr3t", "UTF-8")
        );
        assertTrue(s.equals("CREATED"));
        s = Utils.fetch("/srv/who");
        assertTrue(s.contains("You are bob@example.com"));

        s = Utils.fetch("/srv/register"
                + "?email=" + URLEncoder.encode("alice@example.com", "UTF-8")
                + "&password=" + URLEncoder.encode("xyz", "UTF-8")
        );
        assertTrue(s.equals("ERROR"));
        s = Utils.fetch("/srv/who");
        assertTrue(s.contains("You are bob@example.com"));

    }

    @Test
    public void testSignupAndLogin4() throws UnsupportedEncodingException {
        String s = Utils.fetch("/srv/register"
                + "?email=" + URLEncoder.encode("bob@example.com", "UTF-8")
                + "&password=" + URLEncoder.encode("s3cr3t", "UTF-8")
        );
        assertTrue(s.equals("CREATED"));
        s = Utils.fetch("/srv/who");
        assertTrue(s.contains("You are bob@example.com"));

        cookieManager.getCookieStore().removeAll();
        s = Utils.fetch("/srv/register"
                + "?email=" + URLEncoder.encode("alice@example.com", "UTF-8")
                + "&password=" + URLEncoder.encode("xyz", "UTF-8")
        );
        assertTrue(s.equals("CREATED"));
        s = Utils.fetch("/srv/who");
        assertTrue(s.contains("You are alice@example.com"));

    }
    
    
     @Test
    public void testBadEmail() throws UnsupportedEncodingException {
        String s = Utils.fetch("/srv/register"
                + "?email=" + URLEncoder.encode("bob@", "UTF-8")
                + "&password=" + URLEncoder.encode("s3cr3t", "UTF-8")
        );
        assertTrue(s.equals("BADEMAIL"));
        s = Utils.fetch("/srv/who");
        assertTrue(s.contains("Don't know who you are"));
    }
    
    
     @Test
    public void testBadEmail2() throws UnsupportedEncodingException {
        String s = Utils.fetch("/srv/register"
                + "?email=" + URLEncoder.encode("@bob", "UTF-8")
                + "&password=" + URLEncoder.encode("s3cr3t", "UTF-8")
        );
        assertTrue(s.equals("BADEMAIL"));
        s = Utils.fetch("/srv/who");
        assertTrue(s.contains("Don't know who you are"));
    }
    
    
     @Test
    public void testBadEmail3() throws UnsupportedEncodingException {
        String s = Utils.fetch("/srv/register"
                + "?email=" + URLEncoder.encode("bob@bob@bob@bob", "UTF-8")
                + "&password=" + URLEncoder.encode("s3cr3t", "UTF-8")
        );
        assertTrue(s.equals("BADEMAIL"));
        s = Utils.fetch("/srv/who");
        assertTrue(s.contains("Don't know who you are"));
    }
    
    
     @Test
    public void testBadEmail4() throws UnsupportedEncodingException {
        String s = Utils.fetch("/srv/register"
                + "?email=" + URLEncoder.encode("bobbobarebobawhambamboom", "UTF-8")
                + "&password=" + URLEncoder.encode("s3cr3t", "UTF-8")
        );
        assertTrue(s.equals("BADEMAIL"));
        s = Utils.fetch("/srv/who");
        assertTrue(s.contains("Don't know who you are"));
    }
    
    
     @Test
    public void testBadEmail5() throws UnsupportedEncodingException {
        String s = Utils.fetch("/srv/register"
                + "?email=" + URLEncoder.encode("@", "UTF-8")
                + "&password=" + URLEncoder.encode("s3cr3t", "UTF-8")
        );
        assertTrue(s.equals("BADEMAIL"));
        s = Utils.fetch("/srv/who");
        assertTrue(s.contains("Don't know who you are"));
    }
    
        @Test
    public void testBadEmail6() throws UnsupportedEncodingException {
        String s = Utils.fetch("/srv/register"
                + "?email=" + URLEncoder.encode("@@@", "UTF-8")
                + "&password=" + URLEncoder.encode("s3cr3t", "UTF-8")
        );
        assertTrue(s.equals("BADEMAIL"));
        s = Utils.fetch("/srv/who");
        assertTrue(s.contains("Don't know who you are"));
    }
    
}