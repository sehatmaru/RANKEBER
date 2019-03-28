package rans.rankeber.Realm;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by sehatmaru on 13/03/2019.
 */

public class AturanRealm extends RealmObject {

    @PrimaryKey
    private String hashId;
    private String judulAturan;
    private String isiAturan;
    private int kategori;

    public AturanRealm() {
    }

    public AturanRealm(String hashId, String judulAturan, String isiAturan, int kategori) {
        this.hashId = hashId;
        this.judulAturan = judulAturan;
        this.isiAturan = isiAturan;
        this.kategori = kategori;
    }

    public String getHashId() {
        return hashId;
    }

    public void setHashId(String hashId) {
        this.hashId = hashId;
    }

    public String getJudulAturan() {
        return judulAturan;
    }

    public void setJudulAturan(String judulAturan) {
        this.judulAturan = judulAturan;
    }

    public String getIsiAturan() {
        return isiAturan;
    }

    public void setIsiAturan(String isiAturan) {
        this.isiAturan = isiAturan;
    }

    public int getKategori() {
        return kategori;
    }

    public void setKategori(int kategori) {
        this.kategori = kategori;
    }
}
