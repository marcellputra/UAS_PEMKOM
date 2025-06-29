/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package ui;

import dao.PengunjungDAO;
import model.Pengunjung;
import i18n.LanguageUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.util.List;

/**
 *
 * @author USER
 */
public class Pengunjungform extends javax.swing.JFrame {

    /**
     * Creates new form
     */
    private final PengunjungDAO dao = new PengunjungDAO();
    private JFrame parent; // optional, bisa null

    public Pengunjungform() {
        this(null);
    }

    public Pengunjungform(JFrame parent) {
        this.parent = parent;
        initComponents();
        updateTexts();
        setLocationRelativeTo(null);
        loadTable();
        setVisible(true);
    }

    private void updateTexts() {
        setTitle(LanguageUtil.get("title.pengunjung"));
//        setTitle(LanguageUtil.get("title.pengunjung"));

        // Ubah header tabel
        tablePengunjung.getColumnModel().getColumn(0).setHeaderValue(LanguageUtil.get("table.pengunjung.id"));
        tablePengunjung.getColumnModel().getColumn(1).setHeaderValue(LanguageUtil.get("table.pengunjung.nama"));
        tablePengunjung.getColumnModel().getColumn(2).setHeaderValue(LanguageUtil.get("table.pengunjung.email"));
        tablePengunjung.getColumnModel().getColumn(3).setHeaderValue(LanguageUtil.get("table.pengunjung.nohp"));
        tablePengunjung.getColumnModel().getColumn(4).setHeaderValue(LanguageUtil.get("table.pengunjung.alamat"));
        tablePengunjung.getColumnModel().getColumn(5).setHeaderValue(LanguageUtil.get("label.jumlah")); // tambahkan ke messages
        btntambah.setText(LanguageUtil.get("button.tambah")); // Sesuaikan dengan nama variabel Anda
        btnubah.setText(LanguageUtil.get("button.ubah"));     // Sesuaikan
        btnhapus.setText(LanguageUtil.get("button.hapus"));   // Sesuaikan
        btnkembali.setText(LanguageUtil.get("button.kembali")); // Sesuaikan

        tablePengunjung.getTableHeader().repaint();
    }

    private void loadTable() {
        try {
            DefaultTableModel model = (DefaultTableModel) tablePengunjung.getModel();
            model.setRowCount(0);
            List<Pengunjung> list = dao.getAll();
            for (Pengunjung p : list) {
                model.addRow(new Object[]{
                    p.getId(),
                    p.getNama(),
                    p.getEmail(),
                    p.getNoHp(),
                    p.getAlamat(),
                    p.getJumlahPengunjung()
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    LanguageUtil.get("error.loadDataGagal"),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void tambahPengunjung() {
        Pengunjung p = showInputDialog(null);
        if (p != null) {
            try {
                int maxId = dao.getAll().stream()
                        .mapToInt(Pengunjung::getId)
                        .max().orElse(0);
                p.setId(maxId + 1);
                dao.insert(p);
                loadTable();
                JOptionPane.showMessageDialog(this, LanguageUtil.get("info.tambahDataBerhasil"));
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, LanguageUtil.get("error.tambahDataGagal"));
            }
        }
    }

    private void ubahPengunjung() {
        int selected = tablePengunjung.getSelectedRow();
        if (selected >= 0) {
            // Ambil ID dari baris yang dipilih untuk keamanan
            int pengunjungId = (int) tablePengunjung.getValueAt(selected, 0);
            Pengunjung lama = dao.findById(pengunjungId); // Asumsi dao.findById() ada

            if (lama == null) {
                JOptionPane.showMessageDialog(this, LanguageUtil.get("error.dataTidakDitemukan"),
                        LanguageUtil.get("title.error"), JOptionPane.ERROR_MESSAGE); // Pastikan ini juga di i18n
                return;
            }

            Pengunjung baru = showInputDialog(lama);
            if (baru != null) {
                try {
                    // Pastikan ID tidak berubah saat update
                    baru.setId(lama.getId());
                    dao.update(baru); // Update objek 'baru' yang sudah punya ID lama
                    loadTable();
                    // PESAN SUKSES UBAH DATA: Gunakan kunci I18n yang benar
                    JOptionPane.showMessageDialog(this, LanguageUtil.get("info.ubahDataBerhasil"),
                            LanguageUtil.get("title.informasi"), JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception e) {
                    // PESAN GAGAL UBAH DATA: Gunakan kunci I18n yang benar
                    JOptionPane.showMessageDialog(this, LanguageUtil.get("error.ubahDataGagal") + "\n" + e.getMessage(),
                            LanguageUtil.get("title.error"), JOptionPane.ERROR_MESSAGE);
                    e.printStackTrace(); // Penting untuk debugging
                }
            }
        } else {
            // PESAN JIKA BELUM PILIH DATA: Gunakan kunci I18n yang benar
            JOptionPane.showMessageDialog(this, LanguageUtil.get("info.pilihDataTerlebihDahulu"),
                    LanguageUtil.get("title.peringatan"), JOptionPane.WARNING_MESSAGE);
        }
    }

    private void hapusPengunjung() {
        int selected = tablePengunjung.getSelectedRow();
        if (selected >= 0) {
            // Ambil ID dari baris yang dipilih
            int pengunjungId = (int) tablePengunjung.getValueAt(selected, 0);
            Pengunjung p = dao.findById(pengunjungId); // Asumsi dao.findById() ada

            if (p == null) {
                JOptionPane.showMessageDialog(this, LanguageUtil.get("error.dataTidakDitemukan"),
                        LanguageUtil.get("title.error"), JOptionPane.ERROR_MESSAGE);
                return;
            }

            // PESAN KONFIRMASI HAPUS: Gunakan kunci I18n yang benar
            int confirm = JOptionPane.showConfirmDialog(this, LanguageUtil.get("confirm.hapusData"),
                    LanguageUtil.get("title.konfirmasi"), JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    dao.delete(p);
                    loadTable();
                    // PESAN SUKSES HAPUS DATA: Gunakan kunci I18n yang benar
                    JOptionPane.showMessageDialog(this, LanguageUtil.get("info.hapusDataBerhasil"),
                            LanguageUtil.get("title.informasi"), JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception e) {
                    // PESAN GAGAL HAPUS DATA: Gunakan kunci I18n yang benar
                    JOptionPane.showMessageDialog(this, LanguageUtil.get("error.hapusDataGagal") + "\n" + e.getMessage(),
                            LanguageUtil.get("title.error"), JOptionPane.ERROR_MESSAGE);
                    e.printStackTrace(); // Penting untuk debugging
                }
            }
        } else {
            // PESAN JIKA BELUM PILIH DATA: Gunakan kunci I18n yang benar
            JOptionPane.showMessageDialog(this, LanguageUtil.get("info.pilihDataTerlebihDahulu"),
                    LanguageUtil.get("title.peringatan"), JOptionPane.WARNING_MESSAGE);
        }
    }

    private Pengunjung showInputDialog(Pengunjung existing) {
        JTextField tfNama = new JTextField(existing != null ? existing.getNama() : "");
        JTextField tfEmail = new JTextField(existing != null ? existing.getEmail() : "");
        JTextField tfNoHp = new JTextField(existing != null ? existing.getNoHp() : "");
        JTextField tfAlamat = new JTextField(existing != null ? existing.getAlamat() : "");
        JTextField tfJumlah = new JTextField(existing != null ? String.valueOf(existing.getJumlahPengunjung()) : "");

        Object[] input = {
            LanguageUtil.get("label.nama"), tfNama,
            LanguageUtil.get("label.email"), tfEmail,
            LanguageUtil.get("label.nohp"), tfNoHp,
            LanguageUtil.get("label.alamat"), tfAlamat,
            LanguageUtil.get("label.jumlah"), tfJumlah // gunakan label.jumlah
        };

        int result = JOptionPane.showConfirmDialog(this, input,
                existing == null
                        ? LanguageUtil.get("dialog.tambahPengunjung.title")
                        : LanguageUtil.get("dialog.ubahPengunjung.title"),
                JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                Pengunjung p = new Pengunjung();
                if (existing != null) {
                    p.setId(existing.getId());
                }
                p.setNama(tfNama.getText().trim());
                p.setEmail(tfEmail.getText().trim());
                p.setNoHp(tfNoHp.getText().trim());
                p.setAlamat(tfAlamat.getText().trim());
                p.setJumlahPengunjung(Integer.parseInt(tfJumlah.getText().trim()));
                return p;
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, LanguageUtil.get("Input tidak valid!"));
            }
        }
        return null;
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
        tablePengunjung = new javax.swing.JTable();
        btntambah = new javax.swing.JButton();
        btnubah = new javax.swing.JButton();
        btnhapus = new javax.swing.JButton();
        btnkembali = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        tablePengunjung.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Nama", "Email", "No. Hp", "Alamat", "Jumlah Pengunjung"
            }
        ));
        jScrollPane1.setViewportView(tablePengunjung);

        btntambah.setText("Tambah");
        btntambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btntambahActionPerformed(evt);
            }
        });

        btnubah.setText("Ubah");
        btnubah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnubahActionPerformed(evt);
            }
        });

        btnhapus.setText("Hapus");
        btnhapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnhapusActionPerformed(evt);
            }
        });

        btnkembali.setText("Kembali");
        btnkembali.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnkembaliActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 748, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(206, 206, 206)
                .addComponent(btntambah)
                .addGap(18, 18, 18)
                .addComponent(btnubah)
                .addGap(18, 18, 18)
                .addComponent(btnhapus)
                .addGap(18, 18, 18)
                .addComponent(btnkembali)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 351, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btntambah)
                    .addComponent(btnubah)
                    .addComponent(btnhapus)
                    .addComponent(btnkembali)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 17, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btntambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btntambahActionPerformed
        tambahPengunjung();
    }//GEN-LAST:event_btntambahActionPerformed

    private void btnubahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnubahActionPerformed
        ubahPengunjung();
    }//GEN-LAST:event_btnubahActionPerformed

    private void btnhapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnhapusActionPerformed
        hapusPengunjung();
    }//GEN-LAST:event_btnhapusActionPerformed

    private void btnkembaliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnkembaliActionPerformed
        // Logika untuk kembali ke frame parent
        this.dispose(); // Tutup frame Pengunjungform saat ini
        if (parent != null) {
            SwingUtilities.invokeLater(() -> parent.setVisible(true)); // Tampilkan frame parent
        }
    }//GEN-LAST:event_btnkembaliActionPerformed

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
            java.util.logging.Logger.getLogger(Pengunjungform.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Pengunjungform.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Pengunjungform.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Pengunjungform.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Pengunjungform().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnhapus;
    private javax.swing.JButton btnkembali;
    private javax.swing.JButton btntambah;
    private javax.swing.JButton btnubah;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tablePengunjung;
    // End of variables declaration//GEN-END:variables
}
