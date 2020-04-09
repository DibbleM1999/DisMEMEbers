package DisMEMEberSource;

/**
 *
 * @author Kyle and Brice
 */
public class Post {
    static int counter = 0;
    int postID;
    int postersUserID;
    char postText;
    char image;
    char date;
    
    public static Post instance;
    
    public Post(int userID, char text, char imageLocation, char dateOfPost)
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
    public char getText()
    {
       return this.postText;
    }
    public char getImageLocation()
    {
       return this.image;
    }
    public char getDate()
    {
       return this.date;
    }
}
