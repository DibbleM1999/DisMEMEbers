package DisMEMEberSource;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.ArrayList;
//import java.util.List;
/**
 *
 * @author hridgeway
 */
public class AccountManager {
    //being worked on
    protected int num_acc; //Account count
    protected ArrayList<Integer> Userlist = new ArrayList<Integer>(); //list of users. Holds ints until a user class is made to fill the list.
    
    protected AccountManager()
    {
        for(int i = 0; i < 5; i++)
        {
            Userlist.add(num_acc);
            num_acc++;
        }
    }
    
    public void add(int val)//finish when user class is made
    {
        
    }
    
    public void remove(int index)//Same as add but removing
    {
        try
        {
            int i = Userlist.get(index);
        }catch(ArrayIndexOutOfBoundsException e){System.out.println(e);}
         System.out.println("Index is less than Zero or greater than size of array");
    }
    
    public int getUser(int ID) //will return user once created
    {
        return this.Userlist.get(0);
    }
    
}
