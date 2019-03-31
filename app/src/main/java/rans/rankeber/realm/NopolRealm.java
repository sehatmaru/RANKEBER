package rans.rankeber.realm;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class NopolRealm extends RealmObject {

    @PrimaryKey
    private String hashId;
    private String nopol;
    private String nama;
    private String alamat;
    private int kategori;

    public NopolRealm() {
    }

    public String getHashId() {
        return hashId;
    }

    public void setHashId(String hashId) {
        this.hashId = hashId;
    }

    public String getNopol() {
        return nopol;
    }

    public void setNopol(String nopol) {
        this.nopol = nopol;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public int getKategori() {
        return kategori;
    }

    public void setKategori(int kategori) {
        this.kategori = kategori;
    }

    @Override
    public String toString() {
        return "NopolRealm{" +
                "hashId='" + hashId + '\'' +
                ", nopol='" + nopol + '\'' +
                ", nama='" + nama + '\'' +
                ", alamat='" + alamat + '\'' +
                ", kategori=" + kategori +
                '}';
    }
}
