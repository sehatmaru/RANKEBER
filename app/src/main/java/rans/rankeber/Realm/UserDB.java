package rans.rankeber.Realm;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Sehat MT Samosir on 11/25/2018.
 */

public class UserDB extends RealmObject {
    @PrimaryKey
    private String hashId;
    private String nama;
    private String noHP;
    private String email;

    public UserDB() {
    }

    public String getHashId() {
        return hashId;
    }

    public void setHashId(String hashId) {
        this.hashId = hashId;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getNoHP() {
        return noHP;
    }

    public void setNoHP(String noHP) {
        this.noHP = noHP;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "UserDB{" +
                "hashId='" + hashId + '\'' +
                ", nama='" + nama + '\'' +
                ", noHP='" + noHP + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}