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

public class MainNGTest {

    public MainNGTest() {
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
        fetch("/srv/clearaccounts");
    }

    static String fetch(String... allurls) {
        try {
            String str = null;
            byte[] returnedData = new byte[]{0};  //dummy
            for (String oneurl : allurls) {
                var url = new URL("http://localhost:2020" + oneurl);
                var conn = url.openConnection();
                conn.connect();
                var istr = conn.getInputStream();
                returnedData = istr.readAllBytes();
            }
            return new String(returnedData, 0, returnedData.length);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testSignup() throws UnsupportedEncodingException {
        String s = fetch("/srv/register"
                + "?email=" + URLEncoder.encode("bob@example.com", "UTF-8")
                + "&password=" + URLEncoder.encode("s3cr3t", "UTF-8")
        );
        assertTrue(s.equals("CREATED"));
    }

    @Test
    public void testSignup2() throws UnsupportedEncodingException {
        String s = fetch("/srv/register"
                + "?email=" + URLEncoder.encode("bob@example.com", "UTF-8")
                + "&password=" + URLEncoder.encode("s3cr3t", "UTF-8")
        );
        assertTrue(s.contains("CREATED"));
        cookieManager.getCookieStore().removeAll();
        s = fetch("/srv/register"
                + "?email=" + URLEncoder.encode("bob@example.com", "UTF-8")
                + "&password=" + URLEncoder.encode("xyz", "UTF-8")
        );
        assertTrue(s.equals("DUPLICATE"));
    }

    @Test
    public void testSignupNoEmail() throws UnsupportedEncodingException {
        String s = fetch("/srv/register"
                + "?password=" + URLEncoder.encode("s3cr3t", "UTF-8")
        );
        assertTrue(s.equals("ERROR"));
    }

    @Test
    public void testSignupNoPASSWORd() throws UnsupportedEncodingException {
        String s = fetch("/srv/register"
                + "?email=" + URLEncoder.encode("bob@example.com", "UTF-8")
        );
        assertTrue(s.equals("ERROR"));
    }

    @Test
    public void testSignupNothingAtAll() throws UnsupportedEncodingException {
        String s = fetch("/srv/register");
        assertTrue(s.equals("ERROR"));
    }

    
}









