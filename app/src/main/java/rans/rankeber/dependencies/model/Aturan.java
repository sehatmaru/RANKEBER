package rans.rankeber.dependencies.model;

public class Aturan {

    private String judul;
    private String isi;
    private String kategori;
    private String imageURL;

    public Aturan() {
    }

    public Aturan(String judul, String isi, String kategori, String imageURL) {
        this.judul = judul;
        this.isi = isi;
        this.kategori = kategori;
        this.imageURL = imageURL;
    }

    public String getJudul() {
        return judul;
    }

    public String getIsi() {
        return isi;
    }

    public String getKategori() {
        return kategori;
    }

    public String getImageURL() {
        return imageURL;
    }

    @Override
    public String toString() {
        return "Aturan{" +
                "judul='" + judul + '\'' +
                ", isi='" + isi + '\'' +
                ", kategori='" + kategori + '\'' +
                ", imageURL='" + imageURL + '\'' +
                '}';
    }
}
