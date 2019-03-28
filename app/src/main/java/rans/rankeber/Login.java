package rans.rankeber;

import android.content.Intent;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import io.realm.Realm;
import rans.rankeber.Realm.UserDB;

public class Login extends AppCompatActivity {

    Realm realm;
    EditText username, password;
    CoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Realm.init(this);
        realm = Realm.getDefaultInstance();

        username = (EditText) findViewById(R.id.usernameField);
        password = (EditText) findViewById(R.id.passwordField);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinator);

        Button btnLogin = (Button) findViewById(R.id.lgnBtn);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });
    }

    private void loginUser(){
        String strUser = username.getText().toString();
        String strPass = password.getText().toString();

        if (strUser.equals("user") && strPass.equals("user")){
            realm.beginTransaction();
            UserDB user = realm.createObject(UserDB.class, "1");
            user.setNama("Sehat Maruli Tua Samosir");
            user.setNoHP("082367894080");
            user.setEmail("sehatmaru@gmail.com");
            realm.commitTransaction();

            Intent intent = new Intent(this, Home.class);
            startActivity(intent);
        }else{
            Snackbar.make(coordinatorLayout, "Username/password salah", 3000).show();
        }
    }
}
