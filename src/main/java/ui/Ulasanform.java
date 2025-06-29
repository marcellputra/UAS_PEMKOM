/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package ui;

import dao.UlasanDAO;
import i18n.LanguageUtil;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import model.Ulasan;
import utils.ThreadUtil;

/**
 *
 * @author USER
 */
public class Ulasanform extends javax.swing.JFrame {

    private UlasanDAO dao = new UlasanDAO();
    private JFrame parentFrame;

    /**
     * Creates new form UlasanFrame
     */
    public Ulasanform() {
        this(null); // Memanggil konstruktor dengan parent null jika tidak ada parent awal
    }

    public Ulasanform(JFrame parent) {
        this.parentFrame = parent; // Set parentFrame di konstruktor
        initComponents(); // Dihasilkan oleh NetBeans Form Designer
        setLocationRelativeTo(null);

        setupLanguageComboBox(); // Panggil metode setup ComboBox bahasa
        applyI18n(); // Metode untuk menerapkan teks i18n

    }

    // Metode untuk mengatur JComboBox bahasa (sudah ada)
    private void setupLanguageComboBox() {
        cbBahasa.removeAllItems();
        cbBahasa.addItem("Indonesia");
        cbBahasa.addItem("English");

        // Hapus ActionListeners lama untuk menghindari duplikasi
        for (ActionListener al : cbBahasa.getActionListeners()) {
            cbBahasa.removeActionListener(al);
        }

        Locale currentLocale = LanguageUtil.getCurrentLocale();
        if (currentLocale.getLanguage().equals("in")) {
            cbBahasa.setSelectedItem("Indonesia");
        } else if (currentLocale.getLanguage().equals("en")) {
            cbBahasa.setSelectedItem("English");
        } else {
            cbBahasa.setSelectedItem("Indonesia");
            LanguageUtil.setCurrentLocale(new Locale("in", "ID"));
        }

        cbBahasa.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedLang = (String) cbBahasa.getSelectedItem();
                if (selectedLang != null) {
                    if (selectedLang.equals("Indonesia")) {
                        LanguageUtil.setCurrentLocale(new Locale("in", "ID"));
                    } else if (selectedLang.equals("English")) {
                        LanguageUtil.setCurrentLocale(new Locale("en", "US"));
                    }
                    applyI18n();
                }
            }
        });
    }

    private void applyI18n() {
        setTitle(LanguageUtil.get("title.ulasan_form"));
        lblNama.setText(LanguageUtil.get("label.ulasan.nama"));
        lblTanggal.setText(LanguageUtil.get("label.ulasan.tanggal_kunjungan_format")); // Pastikan teks ini memberi tahu format
        lblIsiUlasan.setText(LanguageUtil.get("label.ulasan.isi_ulasan"));
        lblRating.setText(LanguageUtil.get("label.ulasan.rating"));

        btnKirim.setText(LanguageUtil.get("button.kirim_ulasan"));
        btnKeluar.setText(LanguageUtil.get("button.keluar"));

        lblBahasa.setText(LanguageUtil.get("label.bahasa"));

        SwingUtilities.updateComponentTreeUI(this);
    }

    private void kirimUlasan() {
        // Validasi input tetap di EDT (UI thread)
        String nama = tfNama.getText().trim();
        String isi = taIsiUlasan.getText().trim();
        String tanggalText = tfTanggal.getText().trim();

        if (nama.isEmpty() || isi.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    LanguageUtil.get("error.ulasan.namaIsiWajib"),
                    LanguageUtil.get("title.error"),
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        LocalDate tanggal;
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            tanggal = LocalDate.parse(tanggalText, formatter);
        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(this,
                    LanguageUtil.get("error.ulasan.tanggalTidakValid") + "\n" + e.getMessage(),
                    LanguageUtil.get("title.error"),
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        int rating;
        try {
            rating = Integer.parseInt(cbRating.getSelectedItem().toString());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    LanguageUtil.get("error.ulasan.ratingTidakValid"),
                    LanguageUtil.get("title.error"),
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Jalankan insert di thread baru
        ThreadUtil.runAsync(() -> {
            try {
                int newId = dao.getMaxId() + 1;
                Ulasan ulasan = new Ulasan(newId, nama, tanggal, isi, rating);
                dao.insert(ulasan);

                // Serialisasi objek ke file
                String filename = "ulasan_" + newId + ".ser";
                utils.SerializationUtil.serializeUlasan(ulasan, filename);

                SwingUtilities.invokeLater(() -> {
                    JOptionPane.showMessageDialog(this,
                            LanguageUtil.get("info.ulasan.kirimBerhasil"),
                            LanguageUtil.get("title.informasi"),
                            JOptionPane.INFORMATION_MESSAGE);
                    clearForm();
                });
            } catch (Exception ex) {
                SwingUtilities.invokeLater(() -> {
                    JOptionPane.showMessageDialog(this,
                            LanguageUtil.get("error.ulasan.kirimGagal") + "\n" + ex.getMessage(),
                            LanguageUtil.get("title.error"),
                            JOptionPane.ERROR_MESSAGE);
                });
                ex.printStackTrace();
            }
        });
    }

    private void clearForm() {
        tfNama.setText("");
        tfTanggal.setText("");
        taIsiUlasan.setText("");
        cbRating.setSelectedIndex(0); // Set kembali ke item pertama (misal, rating 1)
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
        jLabel1 = new javax.swing.JLabel();
        lblNama = new javax.swing.JLabel();
        lblTanggal = new javax.swing.JLabel();
        lblIsiUlasan = new javax.swing.JLabel();
        lblRating = new javax.swing.JLabel();
        tfNama = new javax.swing.JTextField();
        tfTanggal = new javax.swing.JTextField();
        cbRating = new javax.swing.JComboBox<>();
        btnKirim = new javax.swing.JButton();
        btnKeluar = new javax.swing.JButton();
        lblBahasa = new javax.swing.JLabel();
        cbBahasa = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        taIsiUlasan = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(0, 255, 255));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel1.setText("ULASAN PENGUNJUNG");

        lblNama.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblNama.setText("Nama");

        lblTanggal.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblTanggal.setText("Tanggal Kunjungan");

        lblIsiUlasan.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblIsiUlasan.setText("Isi Ulasan");

        lblRating.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblRating.setText("Rating");

        cbRating.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5" }));

        btnKirim.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnKirim.setText("Kirim");
        btnKirim.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKirimActionPerformed(evt);
            }
        });

        btnKeluar.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnKeluar.setText("Keluar");
        btnKeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKeluarActionPerformed(evt);
            }
        });

        lblBahasa.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblBahasa.setText("Bahasa");

        cbBahasa.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Indonesia", "English" }));

        taIsiUlasan.setColumns(20);
        taIsiUlasan.setRows(5);
        jScrollPane1.setViewportView(taIsiUlasan);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(199, 199, 199)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblIsiUlasan)
                    .addComponent(lblRating)
                    .addComponent(lblTanggal)
                    .addComponent(lblBahasa)
                    .addComponent(lblNama))
                .addGap(107, 107, 107)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnKirim)
                        .addGap(28, 28, 28)
                        .addComponent(btnKeluar))
                    .addComponent(tfTanggal)
                    .addComponent(tfNama)
                    .addComponent(cbRating, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cbBahasa, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 239, Short.MAX_VALUE))
                .addGap(29, 190, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 58, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblBahasa)
                    .addComponent(cbBahasa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tfNama, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblNama))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tfTanggal, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTanggal))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(lblIsiUlasan)))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbRating, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblRating))
                .addGap(36, 36, 36)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnKeluar)
                    .addComponent(btnKirim))
                .addGap(26, 26, 26))
        );

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnKirimActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKirimActionPerformed
        kirimUlasan();
    }//GEN-LAST:event_btnKirimActionPerformed

    private void btnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKeluarActionPerformed
        this.dispose(); // Tutup frame ulasan saat ini
        if (parentFrame != null) {
            SwingUtilities.invokeLater(() -> parentFrame.setVisible(true)); // Tampilkan frame parent
        }
    }//GEN-LAST:event_btnKeluarActionPerformed

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
            java.util.logging.Logger.getLogger(Ulasanform.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Ulasanform.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Ulasanform.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Ulasanform.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Ulasanform().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnKeluar;
    private javax.swing.JButton btnKirim;
    private javax.swing.JComboBox<String> cbBahasa;
    private javax.swing.JComboBox<String> cbRating;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblBahasa;
    private javax.swing.JLabel lblIsiUlasan;
    private javax.swing.JLabel lblNama;
    private javax.swing.JLabel lblRating;
    private javax.swing.JLabel lblTanggal;
    private javax.swing.JTextArea taIsiUlasan;
    private javax.swing.JTextField tfNama;
    private javax.swing.JTextField tfTanggal;
    // End of variables declaration//GEN-END:variables
}
