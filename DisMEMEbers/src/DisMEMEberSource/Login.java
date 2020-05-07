/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DisMEMEberSource;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ezekiel and Dan
 */
@WebServlet(urlPatterns={"/Login"})
public class Login extends HttpServlet
{
    
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException{
        resp.setContentType("text/plain");
        var pw = resp.getWriter();
        var iUser = req.getParameter("user");
        var iPass = req.getParameter("pass");
        
        try {
            var result = AccountManager.getInstance().verifyUser(iUser, iPass);
            
            if(result >= 0)
            {
                var sess = req.getSession();
                pw.printf("Logged in as " + AccountManager.getInstance().getUser(result));
            }
            
            else
                pw.print("Invalid username or password.");
            
        } catch (Exception ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
            
    }

}
