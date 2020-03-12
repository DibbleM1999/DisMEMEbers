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
@WebServlet(urlPatterns = {"/register"})
public class Register extends HttpServlet {

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

        if (username)
            
        if (AccountManager.instance.addUser(email, username, password)) {
            pw.print("CREATED");
            sess.setAttribute("name", username);
        } 
        else {
            pw.print("DUPLICATE");
        }
    }
}