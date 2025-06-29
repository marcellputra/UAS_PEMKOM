/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tools;

import crypto.HashUtil;

/**
 *
 * @author USER
 */
public class GenerateAdminHash {

    public static void main(String[] args) {
        String username = "admin";
        String plainPassword = "admin123";

        System.out.println("Username     : " + username);
        System.out.println("Plain Password: " + plainPassword);

        String hashed = HashUtil.hashBCrypt(plainPassword);
        System.out.println("Hashed Password: " + hashed);
    }
}
