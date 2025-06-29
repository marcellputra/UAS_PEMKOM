/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author USER
 */
public class Pengunjung {

    private int id;
    private String nama;
    private String email;
    private String noHp;
    private String alamat;
    private int jumlahPengunjung;

    public Pengunjung() {
    }

    public Pengunjung(int id, String nama, String email, String noHp, String alamat, int jumlahPengunjung) {
        this.id = id;
        this.nama = nama;
        this.email = email;
        this.noHp = noHp;
        this.alamat = alamat;
        this.jumlahPengunjung = jumlahPengunjung;
    }

    // Getter & Setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNoHp() {
        return noHp;
    }

    public void setNoHp(String noHp) {
        this.noHp = noHp;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public int getJumlahPengunjung() {
        return jumlahPengunjung;
    }

    public void setJumlahPengunjung(int jumlahPengunjung) {
        this.jumlahPengunjung = jumlahPengunjung;
    }

    @Override
    public String toString() {
        return nama;
    }
}
