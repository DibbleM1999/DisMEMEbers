/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DisMEMEberSource;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 *
 * @author Kyle Johnson & Brice Kade
 */
@WebServlet(urlPatterns={"/logout"})
public class Logout extends HttpServlet
{
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException
    {
        req.getSession().removeAttribute("user");
        
        //Display text on logout
        resp.setContentType("text/plain");
        var pw = resp.getWriter();
        pw.printf("Logged out");
    }
}