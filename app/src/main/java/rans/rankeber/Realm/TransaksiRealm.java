package rans.rankeber.Realm;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class TransaksiRealm extends RealmObject {

    @PrimaryKey
    private String hashId;

    private String hashUser;
    private String hashProduk;
    private String penerima;
    private String alamat;
    private int jumlah;
    private int total;
    private String kurir;
    private String note;
    private String tanggal;

    public TransaksiRealm() {
    }

    public String getHashId() {
        return hashId;
    }

    public void setHashId(String hashId) {
        this.hashId = hashId;
    }

    public String getHashUser() {
        return hashUser;
    }

    public void setHashUser(String hashUser) {
        this.hashUser = hashUser;
    }

    public String getHashProduk() {
        return hashProduk;
    }

    public void setHashProduk(String hashProduk) {
        this.hashProduk = hashProduk;
    }

    public String getPenerima() {
        return penerima;
    }

    public void setPenerima(String penerima) {
        this.penerima = penerima;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public int getJumlah() {
        return jumlah;
    }

    public void setJumlah(int jumlah) {
        this.jumlah = jumlah;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getKurir() {
        return kurir;
    }

    public void setKurir(String kurir) {
        this.kurir = kurir;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    @Override
    public String toString() {
        return "TransaksiRealm{" +
                "hashId='" + hashId + '\'' +
                ", hashUser='" + hashUser + '\'' +
                ", hashProduk='" + hashProduk + '\'' +
                ", penerima='" + penerima + '\'' +
                ", alamat='" + alamat + '\'' +
                ", jumlah='" + jumlah + '\'' +
                ", total='" + total + '\'' +
                ", kurir='" + kurir + '\'' +
                ", note='" + note + '\'' +
                ", tanggal='" + tanggal + '\'' +
                '}';
    }
}
