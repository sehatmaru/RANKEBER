package rans.rankeber.component;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import io.realm.Realm;
import rans.rankeber.R;
import rans.rankeber.dependencies.realm.UserDB;

public class Register extends AppCompatActivity {

    EditText nama, noHP, email, username, password;
    Button btnRegister;

    Realm realm;
    UserDB userDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Realm.init(this);
        realm = Realm.getDefaultInstance();

        nama = findViewById(R.id.input_nama);
        noHP = findViewById(R.id.input_noHP);
        email = findViewById(R.id.input_email);
        username = findViewById(R.id.input_username);
        password = findViewById(R.id.input_password);
        btnRegister = findViewById(R.id.registBtn);

        btnRegister.setOnClickListener(view -> {
            insertUser();

            makeToast("Berhasil mendaftar");

            Intent intent = new Intent(getApplicationContext(), Login.class);
            startActivity(intent);
        });
    }

    private void insertUser(){
        realm.beginTransaction();

        userDB = realm.createObject(UserDB.class, generateHashId());
        userDB.setNama(nama.getText().toString());
        userDB.setEmail(email.getText().toString());
        userDB.setNoHP(noHP.getText().toString());
        userDB.setUsername(username.getText().toString());
        userDB.setPassword(password.getText().toString());
        userDB.setRole("user");

        Log.e("userDB", "" + userDB.toString());

        realm.commitTransaction();
        finish();
    }

    protected String generateHashId() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 10) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        @SuppressLint("SimpleDateFormat") String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        return timeStamp + "" + salt.toString();
    }

    private void makeToast(String text){
        Toast.makeText(this, text, Toast.LENGTH_LONG).show();
    }
}
