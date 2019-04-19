package rans.rankeber.dependencies.model;

public class Aturan {

    private String key;
    private String judul;
    private String isi;
    private String kategori;
    private String imageURL;

    public Aturan() {
    }

    public Aturan(String key, String judul, String isi, String kategori, String imageURL) {
        this.key = key;
        this.judul = judul;
        this.isi = isi;
        this.kategori = kategori;
        this.imageURL = imageURL;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
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
        return "Aturan{" +
                "key='" + key + '\'' +
                ", judul='" + judul + '\'' +
                ", isi='" + isi + '\'' +
                ", kategori='" + kategori + '\'' +
                ", imageURL='" + imageURL + '\'' +
                '}';
    }
}
