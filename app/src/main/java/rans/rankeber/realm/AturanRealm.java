package rans.rankeber.realm;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class AturanRealm extends RealmObject {

    @PrimaryKey
    private String hashId;
    private String judulAturan;
    private String isiAturan;
    private int kategori;

    public AturanRealm() {
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

    @Override
    public String toString() {
        return "AturanRealm{" +
                "hashId='" + hashId + '\'' +
                ", judulAturan='" + judulAturan + '\'' +
                ", isiAturan='" + isiAturan + '\'' +
                ", kategori=" + kategori +
                '}';
    }
}
