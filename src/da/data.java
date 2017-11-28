/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package da;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Rara ^___^
 */
public class data {
    private String kodebarang,namabarang,kategori,packages,harga;

    public data(String kodebarang, String namabarang, String kategori, String packages, String harga) {
        this.kodebarang = kodebarang;
        this.namabarang = namabarang;
        this.kategori = kategori;
        this.packages = packages;
        this.harga = harga;
    }

    /**
     * @return the kodebarang
     */
    public String getKodebarang() {
        return kodebarang;
    }

    /**
     * @param kodebarang the kodebarang to set
     */
    public void setKodebarang(String kodebarang) {
        this.kodebarang = kodebarang;
    }

    /**
     * @return the namabarang
     */
    public String getNamabarang() {
        return namabarang;
    }

    /**
     * @param namabarang the namabarang to set
     */
    public void setNamabarang(String namabarang) {
        this.namabarang = namabarang;
    }

    /**
     * @return the kategori
     */
    public String getKategori() {
        return kategori;
    }

    /**
     * @param kategori the kategori to set
     */
    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    /**
     * @return the packages
     */
    public String getPackages() {
        return packages;
    }

    /**
     * @param packages the packages to set
     */
    public void setPackages(String packages) {
        this.packages = packages;
    }

    /**
     * @return the harga
     */
    public String getHarga() {
        return harga;
    }

    /**
     * @param harga the harga to set
     */
    public void setHarga(String harga) {
        this.harga = harga;
    }
    
}
