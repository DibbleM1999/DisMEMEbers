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

/**
 *
 * @author Ezekiel and Dan
 */
@WebServlet(urlPatterns={"/Login"})
public class Login extends HttpServlet
{
    //needs to get data from account manager
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException{
        resp.setContentType("text/plain");
        var pw = resp.getWriter();
        var iUser = req.getParameter("user");
        var iPass = req.getParameter("pass");
        for (int i = 0; i < Account.next_UID; i++){
            var user = "Account.class.getUsername();";
            var pass = "Account.class.getPassword();";
            if( iUser.equals(user) || iPass.equals(pass)){
                var sess = req.getSession();
                pw.printf("Logged in "+user);
            } 
            else {
                pw.printf("Invalid User/pass");
        }
        }
    }

}
