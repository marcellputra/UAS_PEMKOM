/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import dao.AdminDAO;
import crypto.HashUtil;
import model.Admin;
import i18n.LanguageUtil;
import utils.ThreadUtil;

import javax.swing.*;
import java.util.Locale;
import ui.Login;

/**
 *
 * @author USER
 */
public class Main {

    public static void main(String[] args) {
        try {
            // Letakkan sebelum *semua* GUI dibuat
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println("Gagal mengatur Look and Feel.");
            e.printStackTrace(); // Penting untuk melihat error jika L&F gagal diatur
        }

        // Set locale
        Locale selectedLocale = new Locale("id", "ID");
        LanguageUtil.setLocale(selectedLocale);

//        // Tambah admin baru (sekali saja)
//        try {
//            AdminDAO adminDAO = new AdminDAO();
//            Admin adminBaru = new Admin();
//            adminBaru.setUsername("admin");
//            adminBaru.setPassword(HashUtil.hashBCrypt("admin123"));
//            adminDAO.insert(adminBaru);
//            System.out.println("Admin baru berhasil ditambahkan.");
//
//            // Verifikasi isi koleksi admin
//            adminDAO.getAll().forEach(admin -> {
//                System.out.println("Admin terdaftar: " + admin.getUsername());
//            });
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        SwingUtilities.invokeLater(() -> {
            new Login();  // pastikan import ui.Login;
        });

        ThreadUtil.runAsync(() -> {
            System.out.println("Aplikasi dimulai dengan bahasa: " + LanguageUtil.getCurrentLocale());
        });
    }
}
