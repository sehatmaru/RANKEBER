package rans.rankeber;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import java.util.List;

import io.realm.Realm;
import rans.rankeber.Realm.AturanRealm;
import rans.rankeber.Realm.UserDB;

public class Splashscreen extends AppCompatActivity {

    Realm realm;

    List<AturanRealm> aturanList;

    Boolean logged, inserted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splashscreen);

        Realm.init(this);
        realm = Realm.getDefaultInstance();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                final Intent intent;
                insertUser();
                checkUser();
                if (logged){
                    intent = new Intent(getApplicationContext(), Home.class);
                    startActivity(intent);
                } else{
                    intent = new Intent(getApplicationContext(), Login.class);
                    startActivity(intent);
                }

                finish();
            }
        }, 3000); //delay 3 detik
    }

    private void insertUser(){
        realm.beginTransaction();

        UserDB userDB = realm.createObject(UserDB.class, "1");
        userDB.setNama("User");
        userDB.setEmail("user@gmail.com");
        userDB.setNoHP("-");
        userDB.setUsername("user");
        userDB.setPassword("user");

        realm.commitTransaction();
        finish();
    }

    public boolean checkUser(){
        realm.beginTransaction();
        UserDB user = realm.where(UserDB.class).findFirst();
        realm.commitTransaction();

        logged = user != null;

        return logged;
    }

//    public void checkAturan(){
//        realm.beginTransaction();
//        aturanList = realm.where(AturanRealm.class).findAll();
//        realm.commitTransaction();
//
//        if (aturanList.size() == 0)
//            insertData();
//    }
}
