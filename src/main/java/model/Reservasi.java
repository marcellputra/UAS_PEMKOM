/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.time.LocalDate;

/**
 *
 * @author USER
 */
public class Reservasi {

    private int id;
    private String tempat; // Nama pengunjung
    private LocalDate tanggal;
    private int jumlahOrang;
    private double totalBiaya;
    private String statusPembayaran;
    private String metodePembayaran;

    public Reservasi() {
    }

    public Reservasi(int id, String tempat, LocalDate tanggal, int jumlahOrang,
            double totalBiaya, String statusPembayaran, String metodePembayaran) {
        this.id = id;
        this.tempat = tempat;
        this.tanggal = tanggal;
        this.jumlahOrang = jumlahOrang;
        this.totalBiaya = totalBiaya;
        this.statusPembayaran = statusPembayaran;
        this.metodePembayaran = metodePembayaran;
    }

    // Getter & Setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTempat() {
        return tempat;
    }

    public void setTempat(String tempat) {
        this.tempat = tempat;
    }

    public LocalDate getTanggal() {
        return tanggal;
    }

    public void setTanggal(LocalDate tanggal) {
        this.tanggal = tanggal;
    }

    public int getJumlahOrang() {
        return jumlahOrang;
    }

    public void setJumlahOrang(int jumlahOrang) {
        this.jumlahOrang = jumlahOrang;
    }

    public double getTotalBiaya() {
        return totalBiaya;
    }

    public void setTotalBiaya(double totalBiaya) {
        this.totalBiaya = totalBiaya;
    }

    public String getStatusPembayaran() {
        return statusPembayaran;
    }

    public void setStatusPembayaran(String statusPembayaran) {
        this.statusPembayaran = statusPembayaran;
    }

    public String getMetodePembayaran() {
        return metodePembayaran;
    }

    public void setMetodePembayaran(String metodePembayaran) {
        this.metodePembayaran = metodePembayaran;
    }
}
