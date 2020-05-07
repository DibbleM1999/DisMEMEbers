package DisMEMEberSource;

/**
 *
 * @author Kyle and Brice
 */
public class Post {
    static int counter = 0;
    int postID;
    int postersUserID;
    String postText;
    String image;
    String date;
    
    public Post(int userID, String text, String imageLocation, String dateOfPost)
    {
        this.postID = Post.counter++;
        this.postersUserID = userID;
        this.postText = text;
        this.image = imageLocation;
        this.date = dateOfPost;
    }
    public int getUserID()
    {
       return this.postersUserID;
    }
    public int getPostID()
    {
       return this.postID;
    }
    public String getText()
    {
       return this.postText;
    }
    public String getImageLocation()
    {
       return this.image;
    }
    public String getDate()
    {
       return this.date;
    }
}
