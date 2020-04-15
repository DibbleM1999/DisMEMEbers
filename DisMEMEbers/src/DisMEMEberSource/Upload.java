/**
 *
 * @author Timothy Fowler
 */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns={"/upload"})
@MultipartConfig
public class Upload extends HttpServlet {
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException
    {
        //var name = req.getParameter("name");
        var image = req.getPart("image");
        var imageContent = image.getInputStream();
        
        var bos = new ByteArrayOutputStream();
        byte[] b = new byte[1000];
        while(true)
        {
            int numRead = imageContent.read(b);
            if( numRead <= 0 )
                break;
            bos.write(b,0,numRead);
            if( bos.size() > 100000 ){
                //prevent DoS attack
                resp.sendError(500,"Too much data");
                return;
            }
        }
        
        byte[] fileData = bos.toByteArray();
        //System.out.println("Got data: name="+name+" file="+image.getSubmittedFileName()+" size="+fileData.length );
        System.out.println("Got data: file="+image.getSubmittedFileName()+" size="+fileData.length );
    }
}
