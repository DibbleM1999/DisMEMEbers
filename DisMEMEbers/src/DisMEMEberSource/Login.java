/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DisMEMEberSource;

import java.util.ArrayList;

/**
 *
 * @author Ezekiel and Dan
 */
public class Login {
    static ArrayList<Account> Accounts = new ArrayList<Account>();
    
    public Login(String username, String password)
    {
        
        Account NewAccount = new Account(username, password);
        Accounts.add(NewAccount);
        
        
    }
    
    
}
