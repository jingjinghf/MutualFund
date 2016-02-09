/**
 * @author ChuhuiZhang, Yuheng Li
 * @CreateDate: Jan 19, 2016
 * @LastModify: Jan 20,2016
 */

package bean;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.util.Random;
import org.genericdao.PrimaryKey;

@PrimaryKey("customerId")
public class CustomerBean {
    private int customerId;
    private String customerName;
    private String firstName;
    private String lastName;
    private String hashedPassword = "*";
    private int salt = 0;
    private String addrLine1;
    private String addrLine2;
    private String city;
    private String state;
    private String zip;
    private long cashBalance;
    private long cashAvailable;

    

    public CustomerBean(String customerName, String newPassword) {
        this.customerName = customerName;
        createPassword(newPassword);
    }

    public boolean checkPassword(String password) {
        return hashedPassword.equals(hash(password));
    }
    
    public CustomerBean() {
    };

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public int getSalt() {
        return salt;
    }

    public void setHashedPassword(String x) {
        hashedPassword = x;
    }

    public void createPassword(String s) {
        salt = newSalt();
        hashedPassword = hash(s);
    }

    public void setSalt(int x) {
        salt = x;
    }

    public String getAddrLine1() {
        return addrLine1;
    }

    public void setAddrLine1(String addrLine1) {
        this.addrLine1 = addrLine1;
    }

    public String getAddrLine2() {
        return addrLine2;
    }

    public void setAddrLine2(String addrLine2) {
        this.addrLine2 = addrLine2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    /**
     * get cash balance in long.
     * 
     * @return
     */
    public long getCashBalance() {
        return cashBalance;
    }

    /**
     * DO NOT manully set cash balance, update balance by using CustomerDAO
     * 
     * @param cashBalance
     *            long for database
     */
    public void setCashBalance(long cashBalance) {
        this.cashBalance = cashBalance;
    }

    /**
     * get cash available in long
     * 
     * @return
     */
    public long getCashAvailable() {
        return cashAvailable;
    }

    /**
     * DO NOT manully set cash available, update available by using CustomerDAO
     * 
     * @param cashAvailable
     */
    public void setCashAvailable(long cashAvailable) {
        this.cashAvailable = cashAvailable;
    }

    /**
     * read the double amount of cash balance.
     * 
     * @return
     */
    public double readCashBalance() {
        return (double) cashBalance / 100;
    }

    /**
     * read the double amount of cash available.
     * 
     * @return
     */
    public double readCashAvailable() {
        return (double) cashAvailable / 100;
    }

    /**
     * update cash available by given delta
     * @param amount
     */
    public void writeCashAvailable(double amount) {
        this.cashAvailable = this.cashAvailable + (int)(amount * 100);
    }
    
    public String writeCashBalanceToJSP() {
        String cb;
        DecimalFormat df = new DecimalFormat(",###");
        cb = df.format(this.readCashBalance());
        return cb;
    }
    
    public String writeCashAvailableToJSP() {
        String ca;
        DecimalFormat df = new DecimalFormat(",###");
        ca = df.format(this.readCashAvailable());
        return ca;
    }

    /**
     * update cash balance by given delta, delta could be negtive if money reduce
     * @param amount
     */
    public void writeCashBalance(double amount) {
        this.cashBalance = this.cashBalance + (int)(amount * 100);
        
    }

    private String hash(String clearPassword) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA1");
        } catch (NoSuchAlgorithmException e) {
            throw new AssertionError(
                    "Can't find the SHA1 algorithm in the java.security package");
        }

        String saltString = String.valueOf(salt);

        md.update(saltString.getBytes());
        md.update(clearPassword.getBytes());
        byte[] digestBytes = md.digest();

        // Format the digest as a String
        StringBuffer digestSB = new StringBuffer();
        for (int i = 0; i < digestBytes.length; i++) {
            int lowNibble = digestBytes[i] & 0x0f;
            int highNibble = (digestBytes[i] >> 4) & 0x0f;
            digestSB.append(Integer.toHexString(highNibble));
            digestSB.append(Integer.toHexString(lowNibble));
        }
        String digestStr = digestSB.toString();

        return digestStr;
    }

    private int newSalt() {
        Random random = new Random();
        return random.nextInt(8192) + 1; // salt cannot be zero, except for
                                         // uninitialized password
    }

}
