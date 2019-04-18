package rans.rankeber.component;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import rans.rankeber.R;
import rans.rankeber.component.aturan.CreateAturan;
import rans.rankeber.component.aturan.UpdateAturan;
import rans.rankeber.dependencies.model.Aturan;
import rans.rankeber.dependencies.realm.AturanRealm;
import rans.rankeber.dependencies.realm.NopolRealm;
import rans.rankeber.dependencies.realm.UserDB;
import rans.rankeber.dependencies.realm.UserDBLog;

public class Splashscreen extends AppCompatActivity {

    private Realm realm;

    private boolean logged;
    private String role = "";

    DatabaseReference databaseReference;

    List<Aturan> list = new ArrayList<>();
    List<AturanRealm> listRealm = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splashscreen);

        Realm.init(this);
        realm = Realm.getDefaultInstance();

        databaseReference = FirebaseDatabase.getInstance().getReference(CreateAturan.DB_PATH);
        deleteAturanRealm();
        populateData();

        new Handler().postDelayed(() -> {
            final Intent intent;
            insertUser();
            insertNopol();
//            insertAturan();
            checkUser();
            if (logged){
                if (role != null){
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
            nopolRealm1.setKategori("2");

            NopolRealm nopolRealm2 = realm.createObject(NopolRealm.class, "2");
            nopolRealm2.setNopol("BK 216 JD");
            nopolRealm2.setNama("Fredrick Pardosi");
            nopolRealm2.setAlamat("Gatot Subroto, Medan");
            nopolRealm2.setKategori("1");
        }

        realm.commitTransaction();
        finish();
    }

    private void insertRealm(List<Aturan> aturan){
        for (int i=0; i<aturan.size(); i++){
            realm.beginTransaction();
            AturanRealm aturanRealm = realm.createObject(AturanRealm.class);
            aturanRealm.setJudul(aturan.get(i).getJudul());
            aturanRealm.setKategori(aturan.get(i).getKategori());
            aturanRealm.setIsi(aturan.get(i).getIsi());
            aturanRealm.setImageURL(aturan.get(i).getImageURL());

            listRealm.add(aturanRealm);

            realm.commitTransaction();
        }
        Log.e("realm ", "" + listRealm.size());
    }

    private void populateData(){
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                for (DataSnapshot postSnapshot : snapshot.getChildren()) {

                    Aturan aturan = postSnapshot.getValue(Aturan.class);

                    list.add(aturan);
                }

                insertRealm(list);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                // Hiding the progress dialog.

            }
        });
    }

    public void checkUser(){
        realm.beginTransaction();
        UserDBLog user = realm.where(UserDBLog.class).findFirst();
        realm.commitTransaction();

        if (user !=null)
            role = user.getRole();

        logged = user != null;
    }


    private void deleteAturanRealm(){
        realm.beginTransaction();
        realm.where(AturanRealm.class).findAll().deleteAllFromRealm();
        realm.commitTransaction();
    }
}
