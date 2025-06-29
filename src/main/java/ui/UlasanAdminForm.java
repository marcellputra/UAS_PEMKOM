/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package ui;

import dao.UlasanDAO;
import model.Ulasan;
import i18n.LanguageUtil; // Pastikan ini diimpor

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author USER
 */
public class UlasanAdminForm extends javax.swing.JFrame {

    private JTable table;
    private DefaultTableModel model;
    private UlasanDAO dao = new UlasanDAO();
    private JFrame parentFrame;

    /**
     * Creates new form UlasanAdminFrame
     */
    public UlasanAdminForm() {
        this(null);
    }

    public UlasanAdminForm(JFrame parent) {
        this.parentFrame = parent;
        initComponents();
        setLocationRelativeTo(null);

        applyI18n();
        loadTable();
    }

    private void applyI18n() {
        setTitle(LanguageUtil.get("title.ulasan_admin"));

        btnHapus.setText(LanguageUtil.get("button.hapus"));
        btnKembali.setText(LanguageUtil.get("button.kembali"));
        btnSaveFile.setText(LanguageUtil.get("button.Simpan"));

        String[] headers = new String[]{
            LanguageUtil.get("header.ulasan.id"),
            LanguageUtil.get("header.ulasan.nama"),
            LanguageUtil.get("header.ulasan.tanggal_kunjungan"),
            LanguageUtil.get("header.ulasan.isi_ulasan"),
            LanguageUtil.get("header.ulasan.rating")
        };

        if (model == null) {
            model = (DefaultTableModel) tableulasan.getModel();
        }
        model.setColumnIdentifiers(headers);

        SwingUtilities.updateComponentTreeUI(this);
    }

    private void loadTable() {
        try {
            List<Ulasan> list = dao.getAll();
            model.setRowCount(0);
            for (Ulasan u : list) {
                model.addRow(new Object[]{
                    u.getId(),
                    u.getNama(),
                    u.getTanggalKunjungan(),
                    u.getIsiUlasan(),
                    u.getRating()
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    LanguageUtil.get("error.loadUlasanGagal"),
                    LanguageUtil.get("title.error"), JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void hapusUlasan() {
        int selected = tableulasan.getSelectedRow();
        if (selected >= 0) {
            int confirm = JOptionPane.showConfirmDialog(this,
                    LanguageUtil.get("confirm.ulasan.hapus"),
                    LanguageUtil.get("confirm.ulasan.title"),
                    JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    int ulasanId = (Integer) model.getValueAt(selected, 0);

                    dao.deleteById(ulasanId);

                    loadTable();
                    JOptionPane.showMessageDialog(this,
                            LanguageUtil.get("info.ulasan.hapusBerhasil"),
                            LanguageUtil.get("title.informasi"), JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this,
                            LanguageUtil.get("error.ulasan.hapusGagal") + "\n" + ex.getMessage(),
                            LanguageUtil.get("title.error"), JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }
            }
        } else {
            JOptionPane.showMessageDialog(this,
                    LanguageUtil.get("error.ulasan.pilihDulu"),
                    LanguageUtil.get("title.informasi"),
                    JOptionPane.WARNING_MESSAGE);
        }
    }


    private void saveUlasanToFile(Ulasan ulasan) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Simpan File Ulasan");
        fileChooser.setSelectedFile(new File("ulasan_" + ulasan.getId() + ".ser"));

        int userSelection = fileChooser.showSaveDialog(this);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            if (!fileToSave.getName().toLowerCase().endsWith(".ser")) {
                fileToSave = new File(fileToSave.getParentFile(), fileToSave.getName() + ".ser");
            }
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileToSave))) {
                oos.writeObject(ulasan);
                JOptionPane.showMessageDialog(this, "File berhasil disimpan:\n" + fileToSave.getAbsolutePath());
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Gagal menyimpan file:\n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableulasan = new javax.swing.JTable();
        btnHapus = new javax.swing.JButton();
        btnKembali = new javax.swing.JButton();
        btnSaveFile = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        tableulasan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "ID", "Nama", "Tanggal Kunjungan", "Isi Ulasan", "Rating"
            }
        ));
        jScrollPane1.setViewportView(tableulasan);

        btnHapus.setText("Hapus");
        btnHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapusActionPerformed(evt);
            }
        });

        btnKembali.setText("Kembali");
        btnKembali.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKembaliActionPerformed(evt);
            }
        });

        btnSaveFile.setText("Simpan");
        btnSaveFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveFileActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(210, 210, 210)
                .addComponent(btnHapus)
                .addGap(18, 18, 18)
                .addComponent(btnKembali)
                .addGap(18, 18, 18)
                .addComponent(btnSaveFile)
                .addContainerGap(218, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 321, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnHapus)
                    .addComponent(btnKembali)
                    .addComponent(btnSaveFile))
                .addGap(0, 18, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusActionPerformed
        hapusUlasan();
    }//GEN-LAST:event_btnHapusActionPerformed

    private void btnKembaliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKembaliActionPerformed
        dispose();
        if (parentFrame != null) {
            parentFrame.setVisible(true);
        }
    }//GEN-LAST:event_btnKembaliActionPerformed

    private void btnSaveFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveFileActionPerformed
        int selectedRow = tableulasan.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Pilih data ulasan dari tabel terlebih dahulu.", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int id = (int) tableulasan.getValueAt(selectedRow, 0);
        String nama = (String) tableulasan.getValueAt(selectedRow, 1);
        LocalDate tanggal = (LocalDate) tableulasan.getValueAt(selectedRow, 2);
        String isi = (String) tableulasan.getValueAt(selectedRow, 3);
        int rating = (int) tableulasan.getValueAt(selectedRow, 4);

        Ulasan ulasan = new Ulasan(id, nama, tanggal, isi, rating);
        saveUlasanToFile(ulasan);
    }//GEN-LAST:event_btnSaveFileActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(UlasanAdminForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(UlasanAdminForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(UlasanAdminForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(UlasanAdminForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new UlasanAdminForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnHapus;
    private javax.swing.JButton btnKembali;
    private javax.swing.JButton btnSaveFile;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tableulasan;
    // End of variables declaration//GEN-END:variables
}
