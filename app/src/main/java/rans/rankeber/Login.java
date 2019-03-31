package rans.rankeber;

import android.content.Intent;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;

import io.realm.Realm;
import rans.rankeber.realm.UserDB;
import rans.rankeber.realm.UserDBLog;

public class Login extends AppCompatActivity {

    Realm realm;
    EditText username, password;
    CoordinatorLayout coordinatorLayout;
    Button btnLogin, btnRegister;

    UserDB userDB;

    boolean users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Realm.init(this);
        realm = Realm.getDefaultInstance();

        username = (EditText) findViewById(R.id.usernameField);
        password = (EditText) findViewById(R.id.passwordField);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinator);
        btnLogin = (Button) findViewById(R.id.lgnBtn);
        btnRegister = (Button) findViewById(R.id.registBtn);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Register.class);
                startActivity(intent);
            }
        });
    }


    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), Splashscreen.class);
        startActivity(intent);
    }

    private void loginUser(){
        String strUser = username.getText().toString();
        String strPass = password.getText().toString();

        realm.beginTransaction();
        userDB= realm.where(UserDB.class).equalTo("username", strUser).equalTo("password", strPass).findFirst();

        if (userDB!=null){
            UserDBLog user = realm.createObject(UserDBLog.class, "1");
            user.setNama(userDB.getNama());
            user.setNoHP(userDB.getNoHP());
            user.setEmail(userDB.getEmail());
            user.setUsername(userDB.getUsername());
            user.setPassword(userDB.getPassword());
            user.setRole(userDB.getRole());

            Intent intent = new Intent(this, Home.class);
            startActivity(intent);
        } else{
            Snackbar.make(coordinatorLayout, "Username/password salah", 3000).show();
        }

        realm.commitTransaction();
    }
}
