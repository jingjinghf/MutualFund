/**
 * @author Chuhui Zhang
 * Date: Jan 18, 2016
 */

package bean;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import org.genericdao.PrimaryKey;

@PrimaryKey("userName")
public class EmployeeBean {
    private String  userName = null;
    private String firstName = null;
    private String lastName = null;
    private String  hashedPassword = "*";
    private int     salt           = 0;

    public boolean checkPassword(String password) {
        return hashedPassword.equals(hash(password));
    }

    public String  getHashedPassword() { return hashedPassword; }
    public String  getUserName()       { return userName;       }
    public String  getFirstName()       { return firstName;       }
    public String  getLastName()       { return lastName;       }
    public int     getSalt()           { return salt;           }

    public void createPassword(String x) { salt = newSalt(); hashedPassword = hash(x); }
    public void setHashedPassword(String x) {hashedPassword = x;}
    public void setUserName(String s)       { userName = s;       }
    public void setFirstName(String s)       { firstName = s;       }
    public void setLastName(String s)       { lastName = s;       }
    public void setSalt(int x)              { salt = x;           }

    public String toString() {
        return "User("+getUserName()+")";
    }

    private String hash(String clearPassword) {
        MessageDigest md = null;
        try {
          md = MessageDigest.getInstance("SHA1");
        } catch (NoSuchAlgorithmException e) {
          throw new AssertionError("Can't find the SHA1 algorithm in the java.security package");
        }

        String saltString = String.valueOf(salt);
        
        md.update(saltString.getBytes());
        md.update(clearPassword.getBytes());
        byte[] digestBytes = md.digest();

        // Format the digest as a String
        StringBuffer digestSB = new StringBuffer();
        for (int i=0; i<digestBytes.length; i++) {
          int lowNibble = digestBytes[i] & 0x0f;
          int highNibble = (digestBytes[i]>>4) & 0x0f;
          digestSB.append(Integer.toHexString(highNibble));
          digestSB.append(Integer.toHexString(lowNibble));
        }
        String digestStr = digestSB.toString();

        return digestStr;
    }

    private int newSalt() {
        Random random = new Random();
        return random.nextInt(8192)+1;  // salt cannot be zero, except for uninitialized password
    }
}
