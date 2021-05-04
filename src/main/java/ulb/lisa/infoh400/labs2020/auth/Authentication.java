/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ulb.lisa.infoh400.labs2020.auth;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import ulb.lisa.infoh400.labs2020.controller.UserJpaController;
import ulb.lisa.infoh400.labs2020.model.User;
import ulb.lisa.infoh400.labs2020.view.LoginWindow;
import ulb.lisa.infoh400.labs2020.view.MainWindow;

/**
 *
 * @author Adrien Foucart
 */
public class Authentication {
    private static final EntityManagerFactory emfac = Persistence.createEntityManagerFactory("infoh400_PU");
    private static final UserJpaController userCtrl = new UserJpaController(emfac);
    private static User loggedInUser = null;
    
    public static boolean hasUser(){
        List<User> users = userCtrl.findUserEntities();
        return users.size() > 0;
    }
    
    public static void createUser(String username, char[] password){
        try {
            String saltedHash = PasswordFactory.getSaltedHash(password);
            
            User user = new User();
            user.setUsername(username);
            user.setPassword(saltedHash);
            userCtrl.create(user);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
            Logger.getLogger(Authentication.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static boolean login(String username, char[] password){
        try {
            User user = userCtrl.findUserByUsername(username);
            if( user == null ) return false;
            
            if( PasswordFactory.check(password, user.getPassword()) ){
                loggedInUser = user;
                user.setLastLogin(new Date());
                userCtrl.edit(user);
                
                return true;
            }
        } catch (InvalidKeySpecException | NoSuchAlgorithmException ex) {
            Logger.getLogger(Authentication.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(Authentication.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return false;
    }
    
    public static User getUser(){
        return loggedInUser;
    }
    
    public static void promptLogin(){
        LoginWindow loginPopup = new LoginWindow();
        loginPopup.setVisible(true);
        loginPopup.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent evt){
                if( loggedInUser != null ){
                    MainWindow.display();
                }
            }
        });
    }
    
}
