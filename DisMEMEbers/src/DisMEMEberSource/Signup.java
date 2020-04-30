/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DisMEMEberSource;

/**
 *
 * @author DeBbb
 */

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet(urlPatterns = {"/signup"})
public class Signup extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            resp.setContentType("text/plain");
            var pw = resp.getWriter();
            var email = req.getParameter("email");
            var password = req.getParameter("password");
            var username = req.getParameter("username");
            var sess = req.getSession();
            
            if (sess.getAttribute("name") != null || email == null || password == null || username == null || email.isEmpty() || password.isEmpty() || username.isEmpty()) {
                pw.print("ERROR");
                return;
            }
            
            String[] emailsplit = email.split("@");
            
            if (emailsplit.length != 2) {
                pw.print("BADEMAIL");
                return;
            }
            
            
            if (emailsplit[0].isEmpty() || emailsplit[1].isEmpty()) {
                pw.print("BADEMAIL");
                return;
            }
            
            if (AccountManager.instance.name_check(username)){  // check function name   AccountManager.name_check(username)
                pw.print("USERINUSE");
                return;
            }
            
            
            if (AccountManager.instance.email_check(email)){    // check function name   AccountManager.email_check(email)
                pw.print("EMAILINUSE");
                return;
            }
            
            AccountManager.instance.add(new Account(username, password, email));
            //AccountManager.add(new Account(username, password, email));
            pw.print("CREATED");
            sess.setAttribute("name", username);
        } catch (Exception ex) {
            Logger.getLogger(Signup.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
