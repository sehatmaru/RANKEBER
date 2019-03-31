package rans.rankeber;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import io.realm.Realm;
import rans.rankeber.realm.AturanRealm;
import rans.rankeber.realm.NopolRealm;
import rans.rankeber.realm.UserDB;
import rans.rankeber.realm.UserDBLog;

public class Splashscreen extends AppCompatActivity {

    private Realm realm;

    private boolean logged;
    private String role = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splashscreen);

        Realm.init(this);
        realm = Realm.getDefaultInstance();

        new Handler().postDelayed(() -> {
            final Intent intent;
            insertUser();
            insertNopol();
            insertAturan();
            checkUser();
            if (logged){
                if (role.equals("user")){
                    intent = new Intent(getApplicationContext(), Home.class);
                    startActivity(intent);
                }
            } else{
                intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
            }

            finish();
        }, 3000); //delay 3 detik
    }

    private void insertUser(){
        realm.beginTransaction();

        UserDB user = realm.where(UserDB.class).equalTo("hashId", "1").findFirst();

        if (user==null){
            UserDB userDB = realm.createObject(UserDB.class, "1");
            userDB.setNama("User");
            userDB.setEmail("user@gmail.com");
            userDB.setNoHP("-");
            userDB.setUsername("user");
            userDB.setPassword("user");
            userDB.setRole("user");

            UserDB userDB1 = realm.createObject(UserDB.class, "2");
            userDB1.setNama("Admin");
            userDB1.setEmail("-");
            userDB1.setNoHP("-");
            userDB1.setUsername("admin");
            userDB1.setPassword("admin");
            userDB1.setRole("admin");

            UserDB userDB3 = realm.createObject(UserDB.class, "3");
            userDB3.setNama("Petugas");
            userDB3.setEmail("-");
            userDB3.setNoHP("-");
            userDB3.setUsername("petugas");
            userDB3.setPassword("petugas");
            userDB3.setRole("petugas");
        }

        realm.commitTransaction();
        finish();
    }

    private void insertNopol(){
        realm.beginTransaction();

        NopolRealm nopolRealm = realm.where(NopolRealm.class).equalTo("hashId", "1").findFirst();

        if (nopolRealm==null){
            NopolRealm nopolRealm1 = realm.createObject(NopolRealm.class, "1");
            nopolRealm1.setNopol("BK 55 JO");
            nopolRealm1.setNama("Sehat Maruli Tua Samosir");
            nopolRealm1.setAlamat("Hinalang");
            nopolRealm1.setKategori(2);

            NopolRealm nopolRealm2 = realm.createObject(NopolRealm.class, "2");
            nopolRealm2.setNopol("BK 216 JD");
            nopolRealm1.setNama("Fredrick Pardosi");
            nopolRealm1.setAlamat("Gatot Subroto, Medan");
            nopolRealm2.setKategori(1);
        }

        realm.commitTransaction();
        finish();
    }

    private void insertAturan(){
        realm.beginTransaction();

        AturanRealm aturan = realm.where(AturanRealm.class).findFirst();

        /*
            1 -> roda 2
            2 -> roda 4
         */
        if (aturan==null){
            AturanRealm aturanmotor1 = realm.createObject(AturanRealm.class, "1");
            aturanmotor1.setJudulAturan("Wajib helm");
            aturanmotor1.setIsiAturan("Pengendara wajib menggunakan helm, apapun yg terjadi, awas aja kalau ga pake helm, pecah kepalamu");
            aturanmotor1.setKategori(1);

            AturanRealm aturanmotor2 = realm.createObject(AturanRealm.class, "2");
            aturanmotor2.setJudulAturan("Wajib spion");
            aturanmotor2.setIsiAturan("Pengendara wajib memasang spion");
            aturanmotor2.setKategori(1);

            AturanRealm aturanmobil1 = realm.createObject(AturanRealm.class, "3");
            aturanmobil1.setJudulAturan("Wajib Lampu");
            aturanmobil1.setIsiAturan("Mobil harus menggunakan lampu dengan SNI");
            aturanmobil1.setKategori(2);

            AturanRealm aturanmobil2 = realm.createObject(AturanRealm.class, "4");
            aturanmobil2.setJudulAturan("Wajib spion");
            aturanmobil2.setIsiAturan("Mobil wajib dipasangi spion");
            aturanmobil2.setKategori(2);
        }

        realm.commitTransaction();
        finish();
    }

    public void checkUser(){
        realm.beginTransaction();
        UserDBLog user = realm.where(UserDBLog.class).findFirst();
        realm.commitTransaction();

        if (user !=null)
            role = user.getRole();

        logged = user != null;
    }
}
