package rans.rankeber.component;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.Toast;

import io.realm.Realm;
import rans.rankeber.R;
import rans.rankeber.dependencies.realm.UserDBLog;

public class About extends AppCompatActivity {

    Button logoutBtn;

    Realm realm;

    UserDBLog userDBLog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        Realm.init(this);
        realm = Realm.getDefaultInstance();

        checkLogUser();

        logoutBtn = findViewById(R.id.logoutBtn);
        logoutBtn.setOnClickListener(view -> {
            clearLogUser();

            Intent intent = new Intent(getApplicationContext(), Login.class);
            startActivity(intent);
        });
    }

    private void checkLogUser(){
        realm.beginTransaction();
        UserDBLog userDBLog = realm.where(UserDBLog.class).findFirst();

        if (userDBLog == null){
            makeToast("Anda harus login terlebih dahulu");
            Intent intent = new Intent(getApplicationContext(), Login.class);
            startActivity(intent);
        }

        realm.commitTransaction();
    }

    private void clearLogUser(){
        realm.beginTransaction();
        userDBLog = realm.where(UserDBLog.class).findFirst();

        if (userDBLog != null)
            userDBLog.deleteFromRealm();

        realm.commitTransaction();
    }

    private void makeToast(String text){
        Toast.makeText(this, text, Toast.LENGTH_LONG).show();
    }
}
