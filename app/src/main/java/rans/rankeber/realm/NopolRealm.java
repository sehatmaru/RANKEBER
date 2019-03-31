package rans.rankeber.realm;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by sehatmaru on 31/03/2019.
 */

public class NopolRealm extends RealmObject {

    @PrimaryKey
    private String hashId;
    private String nopol;
    private int kategori;

    public NopolRealm() {
    }

    public NopolRealm(String hashId, String nopol, int kategori) {
        this.hashId = hashId;
        this.nopol = nopol;
        this.kategori = kategori;
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
                ", kategori=" + kategori +
                '}';
    }
}
