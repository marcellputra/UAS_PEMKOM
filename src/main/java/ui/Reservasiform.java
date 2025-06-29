/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package ui;

import dao.ReservasiDAO;
import model.Reservasi;
import i18n.LanguageUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*; // Import GridLayout
import java.time.LocalDate;
import java.time.format.DateTimeParseException; // Import untuk menangani error parsing tanggal
import java.util.List;

/**
 *
 * @author USER
 */
public class Reservasiform extends javax.swing.JFrame {

    /**
     * Creates new form
     */
    private final ReservasiDAO dao = new ReservasiDAO();
    private JFrame parent; // optional, bisa null

    public Reservasiform() {
        this(null);
    }

    public Reservasiform(JFrame parent) {
        this.parent = parent;
        initComponents(); // Ini menginisialisasi komponen UI dari Form Designer
        setLocationRelativeTo(null);

        // Atur judul frame dan teks tombol menggunakan ResourceBundle
        setTitle(LanguageUtil.get("title.reservasi")); // Pastikan ada di messages.properties
        btntambah.setText(LanguageUtil.get("button.tambah"));
        btnubah.setText(LanguageUtil.get("button.ubah"));
        btnhapus.setText(LanguageUtil.get("button.hapus"));
        btnkembali.setText(LanguageUtil.get("button.kembali"));

        // Update header tabel dengan teks dari ResourceBundle
        DefaultTableModel currentModel = (DefaultTableModel) jTable1.getModel();
        currentModel.setColumnIdentifiers(new Object[]{
            "ID",
            LanguageUtil.get("table.reservasi.nama"), // Assuming "Nama" is for "Tempat"
            LanguageUtil.get("table.reservasi.tanggal_kunjungan"),
            LanguageUtil.get("table.reservasi.jumlah_tiket"),
            LanguageUtil.get("table.reservasi.total_biaya"),
            LanguageUtil.get("table.reservasi.status_pembayaran"),
            LanguageUtil.get("table.reservasi.metode_pembayaran")
        });

        loadTable();
        setVisible(true); // Sebaiknya ini dipanggil oleh pembuat instance Reservasiform
        // jika frame ini dibuka dari frame lain.
        SwingUtilities.updateComponentTreeUI(this);
    }

    private void loadTable() {
        try {
            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
            model.setRowCount(0); // Clear existing rows
            List<Reservasi> list = dao.getAll();
            for (Reservasi r : list) {
                model.addRow(new Object[]{
                    r.getId(),
                    r.getTempat(), // Asumsi ini nama
                    r.getTanggal().toString(),
                    r.getJumlahOrang(),
                    r.getTotalBiaya(),
                    r.getStatusPembayaran(),
                    r.getMetodePembayaran()
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    LanguageUtil.get("error.loadReservasiGagal"),
                    "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace(); // Penting untuk melihat detail error di konsol
        }
    }

    private void tambahReservasi() {
        Reservasi r = showInputDialog(null);
        if (r != null) {
            try {
                // Mendapatkan ID tertinggi dari database (lebih aman)
                // Pastikan ReservasiDAO Anda memiliki metode getMaxId()
                int newId = dao.getMaxId() + 1;
                r.setId(newId);

                dao.insert(r);
                loadTable();
                JOptionPane.showMessageDialog(this, LanguageUtil.get("info.tambahDataBerhasil"));
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, LanguageUtil.get("error.tambahDataGagal") + "\n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
    }

    private void ubahReservasi() {
        int selected = jTable1.getSelectedRow();
        if (selected >= 0) {
            // Ambil ID dari tabel, bukan dari dao.getAll().get(selected)
            int idToEdit = (int) jTable1.getValueAt(selected, 0); // Asumsi ID ada di kolom pertama (indeks 0)
            Reservasi lama = dao.findById(idToEdit); // Ambil objek lengkap berdasarkan ID

            if (lama == null) {
                JOptionPane.showMessageDialog(this, "Data reservasi tidak ditemukan di database!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Reservasi baru = showInputDialog(lama);
            if (baru != null) {
                try {
                    // Pastikan ID tetap sama saat update
                    baru.setId(lama.getId());
                    dao.update(baru); // Update di database
                    loadTable(); // Refresh tabel
                    JOptionPane.showMessageDialog(this, LanguageUtil.get("info.ubahDataBerhasil"));
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, LanguageUtil.get("error.ubahDataGagal") + "\n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    e.printStackTrace();
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, LanguageUtil.get("info.pilihDataTerlebihDahulu"));
        }
    }

    private void hapusReservasi() {
        int selected = jTable1.getSelectedRow();
        if (selected >= 0) {
            // Ambil ID dari tabel
            int idToDelete = (int) jTable1.getValueAt(selected, 0); // Asumsi ID ada di kolom pertama (indeks 0)
            Reservasi r = dao.findById(idToDelete); // Ambil objek lengkap berdasarkan ID

            if (r == null) {
                JOptionPane.showMessageDialog(this, "Data reservasi tidak ditemukan di database!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(this,
                    LanguageUtil.get("confirm.hapusData"),
                    LanguageUtil.get("title.konfirmasi"), JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    dao.delete(r); // Hapus berdasarkan objek yang ditemukan
                    loadTable();
                    JOptionPane.showMessageDialog(this, LanguageUtil.get("info.hapusDataBerhasil"));
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, LanguageUtil.get("error.hapusDataGagal") + "\n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    e.printStackTrace();
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, LanguageUtil.get("info.pilihDataTerlebihDahulu"));
        }
    }

    private Reservasi showInputDialog(Reservasi existing) {
        JTextField tfNama = new JTextField(existing != null ? existing.getTempat() : "");
        JTextField tfTanggal = new JTextField(existing != null ? existing.getTanggal().toString() : "");
        JTextField tfJumlahTiket = new JTextField(existing != null ? String.valueOf(existing.getJumlahOrang()) : "");
        JTextField tfTotalBiaya = new JTextField(existing != null ? String.valueOf(existing.getTotalBiaya()) : "");

        // ComboBox untuk Status Pembayaran
        String[] statusOptions = {"Pending", "Paid", "Cancelled"};
        JComboBox<String> cbStatus = new JComboBox<>(statusOptions);
        if (existing != null && existing.getStatusPembayaran() != null) {
            cbStatus.setSelectedItem(existing.getStatusPembayaran());
        }

        // ComboBox untuk Metode Pembayaran
        String[] metodeOptions = {"Cash", "Credit Card", "Bank Transfer", "E-Wallet"};
        JComboBox<String> cbMetode = new JComboBox<>(metodeOptions);
        if (existing != null && existing.getMetodePembayaran() != null) {
            cbMetode.setSelectedItem(existing.getMetodePembayaran());
        }

        JPanel panel = new JPanel(new GridLayout(0, 2, 5, 5));
        panel.add(new JLabel(LanguageUtil.get("label.nama_pemesan"))); // Menggunakan i18n
        panel.add(tfNama);
        panel.add(new JLabel(LanguageUtil.get("label.tanggal_kunjungan_format"))); // Menggunakan i18n
        panel.add(tfTanggal);
        panel.add(new JLabel(LanguageUtil.get("label.jumlah_tiket"))); // Menggunakan i18n
        panel.add(tfJumlahTiket);
        panel.add(new JLabel(LanguageUtil.get("label.total_biaya"))); // Menggunakan i18n
        panel.add(tfTotalBiaya);
        panel.add(new JLabel(LanguageUtil.get("label.status_pembayaran"))); // Menggunakan i18n
        panel.add(cbStatus);
        panel.add(new JLabel(LanguageUtil.get("label.metode_pembayaran"))); // Menggunakan i18n
        panel.add(cbMetode);

        int result = JOptionPane.showConfirmDialog(this, panel,
                existing == null ? LanguageUtil.get("title.tambahReservasi") : LanguageUtil.get("title.ubahReservasi"),
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            try {
                // Validasi input
                if (tfNama.getText().trim().isEmpty() || tfTanggal.getText().trim().isEmpty()
                        || tfJumlahTiket.getText().trim().isEmpty() || tfTotalBiaya.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, LanguageUtil.get("error.semuaKolomHarusDiisi"), "Input Error", JOptionPane.ERROR_MESSAGE);
                    return null;
                }

                LocalDate tanggal = LocalDate.parse(tfTanggal.getText().trim());
                int jumlahTiket = Integer.parseInt(tfJumlahTiket.getText().trim());
                double totalBiaya = Double.parseDouble(tfTotalBiaya.getText().trim());

                if (jumlahTiket <= 0) {
                    JOptionPane.showMessageDialog(this, LanguageUtil.get("error.jumlahTiketInvalid"), "Input Error", JOptionPane.ERROR_MESSAGE);
                    return null;
                }
                if (totalBiaya <= 0) {
                    JOptionPane.showMessageDialog(this, LanguageUtil.get("error.totalBiayaInvalid"), "Input Error", JOptionPane.ERROR_MESSAGE);
                    return null;
                }

                Reservasi r = new Reservasi();
                if (existing != null) {
                    r.setId(existing.getId()); // Pertahankan ID saat mengubah
                }
                r.setTempat(tfNama.getText().trim());
                r.setTanggal(tanggal);
                r.setJumlahOrang(jumlahTiket);
                r.setTotalBiaya(totalBiaya);
                r.setStatusPembayaran((String) cbStatus.getSelectedItem()); // Ambil dari ComboBox
                r.setMetodePembayaran((String) cbMetode.getSelectedItem()); // Ambil dari ComboBox
                return r;
            } catch (DateTimeParseException e) {
                JOptionPane.showMessageDialog(this, LanguageUtil.get("error.tanggalTidakValid"), "Input Error", JOptionPane.ERROR_MESSAGE);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, LanguageUtil.get("error.jumlahBiayaNumerik"), "Input Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, LanguageUtil.get("error.inputTidakValid") + "\n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
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
        jTable1 = new javax.swing.JTable();
        btntambah = new javax.swing.JButton();
        btnubah = new javax.swing.JButton();
        btnhapus = new javax.swing.JButton();
        btnkembali = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Nama", "Tanggal Kunjungan", "Jumlah Tiket", "Total Biaya", "Status Pembayaran", "Metode Pembayaran"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

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
            .addComponent(jScrollPane1)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(222, 222, 222)
                .addComponent(btntambah)
                .addGap(32, 32, 32)
                .addComponent(btnubah)
                .addGap(35, 35, 35)
                .addComponent(btnhapus)
                .addGap(33, 33, 33)
                .addComponent(btnkembali)
                .addContainerGap(200, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 367, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btntambah)
                    .addComponent(btnubah)
                    .addComponent(btnhapus)
                    .addComponent(btnkembali))
                .addGap(0, 26, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btntambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btntambahActionPerformed
        tambahReservasi();
    }//GEN-LAST:event_btntambahActionPerformed

    private void btnubahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnubahActionPerformed
        ubahReservasi();
    }//GEN-LAST:event_btnubahActionPerformed

    private void btnhapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnhapusActionPerformed
        hapusReservasi();
    }//GEN-LAST:event_btnhapusActionPerformed

    private void btnkembaliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnkembaliActionPerformed
        // Logika untuk kembali ke frame parent
        this.dispose(); // Tutup frame Reservasiform saat ini
        if (parent != null) {
            SwingUtilities.invokeLater(() -> parent.setVisible(true)); // Tampilkan frame parent di EDT
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
            java.util.logging.Logger.getLogger(Reservasiform.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Reservasiform.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Reservasiform.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Reservasiform.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Reservasiform().setVisible(true);
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
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
