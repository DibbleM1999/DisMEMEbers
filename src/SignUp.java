/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author DeBbb
 */

import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = {"/register"})
public class Register extends HttpServlet {
    
    class Account{
        String email;
        String username;
        String password;
        Account(String e, String u, String p){
            this.email = e;
            this.password = p;
            this.username = u;
        }
    }

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
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

        //check if username is already in use
        //if so print out pw.print("USERNAMEUSED"); return;
       
        //check if email is in use
        //if so pw.print("DUPLICATE"); return;
        
        
        //make a user here and send it to account manager    
        AccountManager.instance.addUser(new Account(email, username, password));
        pw.print("CREATED");
        sess.setAttribute("name", username);
        
    }
}