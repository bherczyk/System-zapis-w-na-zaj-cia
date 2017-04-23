
package edukacja;
import java.io.UnsupportedEncodingException;
import java.security.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class User {
    
    private static String login = "MistrzYoda";
    private static String password = "0c9fd37701646dadb1d1015e4147a3f9";
    
    
    public User() 
    {

    };
    
    public  boolean checked(String login, String password)
    {
        return checkedLogin(login) && checkedPassword(password);
    };
    
    private boolean checkedLogin(String login)
    {
      return(login.equals(User.login));  
    };
    
    private boolean checkedPassword(String checkedPassword)
    {
        String generatedPassword = hashPassowrd(checkedPassword);
        return isCorrect(generatedPassword);
    };
    
    
    private String hashPassowrd(String passwordToHash)
    {
        String generatedPassword =null;
       
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(passwordToHash.getBytes());
            byte[] bytes = md.digest();
           
            StringBuilder sb = new StringBuilder();
            
             for(int i = 0; i < bytes.length; i++)
             {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
             }
       
       generatedPassword = sb.toString();
            
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return generatedPassword;
    };
    
    private boolean isCorrect(String generatedPassword)
    {
        if(password.equals(generatedPassword))
            return true;
        else 
            return false;
    };
}
