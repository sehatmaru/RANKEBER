package rans.rankeber.dependencies.realm;

import io.realm.RealmObject;

public class AturanRealm extends RealmObject {

    private String judul;
    private String isi;
    private String kategori;
    private String imageURL;

    public AturanRealm() {
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getIsi() {
        return isi;
    }

    public void setIsi(String isi) {
        this.isi = isi;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    @Override
    public String toString() {
        return "AturanRealm{" +
                "judul='" + judul + '\'' +
                ", isi='" + isi + '\'' +
                ", kategori='" + kategori + '\'' +
                ", imageURL='" + imageURL + '\'' +
                '}';
    }
}
