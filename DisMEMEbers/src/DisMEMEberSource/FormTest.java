
import java.io.IOException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet(urlPatterns={"/formtest"})
@MultipartConfig
public class FormTest extends HttpServlet {
    public void doPost(HttpServletRequest req, 
    HttpServletResponse resp) throws IOException{
        var name = req.getParameter("name");
        var pobox = req.getParameter("pobox");
        var state = req.getParameter("state");
        System.out.println("{Server got name="+name+" / pobox="+pobox+" / state="+state+"}");
        resp.setContentType("text/plain");
        var pw = resp.getWriter();
        pw.print("OK!");
    }
}
