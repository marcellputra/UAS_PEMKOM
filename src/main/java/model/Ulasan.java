/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.io.Serializable;
import java.time.LocalDate;

/**
 *
 * @author USER
 */
public class Ulasan implements Serializable{

    private static final long serialVersionUID = 1L;

    private int id;
    private String nama;
    private LocalDate tanggalKunjungan;
    private String isiUlasan;
    private int rating;

    public Ulasan() {
    }

    public Ulasan(int id, String nama, LocalDate tanggalKunjungan, String isiUlasan, int rating) {
        this.id = id;
        this.nama = nama;
        this.tanggalKunjungan = tanggalKunjungan;
        this.isiUlasan = isiUlasan;
        this.rating = rating;
    }

    // Getters & Setters
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

    public LocalDate getTanggalKunjungan() {
        return tanggalKunjungan;
    }

    public void setTanggalKunjungan(LocalDate tanggalKunjungan) {
        this.tanggalKunjungan = tanggalKunjungan;
    }

    public String getIsiUlasan() {
        return isiUlasan;
    }

    public void setIsiUlasan(String isiUlasan) {
        this.isiUlasan = isiUlasan;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
