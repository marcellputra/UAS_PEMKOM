/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package crypto;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author USER
 */
public class HashUtil {

    // SHA-256 (Simple Hashing)
    public static String hashSHA256(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = md.digest(input.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hashBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Hashing Error: " + e.getMessage());
        }
    }

    // BCrypt (Salted Hashing – Recommended for passwords)
    public static String hashBCrypt(String input) {
        return BCrypt.hashpw(input, BCrypt.gensalt());
    }

    public static boolean verifyBCrypt(String plainText, String hashed) {
        return BCrypt.checkpw(plainText, hashed);
    }
}
