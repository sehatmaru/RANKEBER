package rans.rankeber.component.nopol;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import io.realm.Realm;
import rans.rankeber.R;
import rans.rankeber.dependencies.enums.Kategori;
import rans.rankeber.dependencies.realm.NopolRealm;

public class CreateNopol extends AppCompatActivity {

    Realm realm;

    EditText inputNama, inputNopol, inputAlamat;
    Spinner inputKategori;
    Button btnTambah, btnList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_nopol);

        Realm.init(this);
        realm = Realm.getDefaultInstance();

        inputNama = findViewById(R.id.inputNama);
        inputNopol = findViewById(R.id.inputNopol);
        inputAlamat = findViewById(R.id.inputAlamat);
        inputKategori = findViewById(R.id.inputKategori);
        btnTambah = findViewById(R.id.btnTambah);
        btnList = findViewById(R.id.btnList);

        setSpinner();

        btnTambah.setOnClickListener(view -> insertNopol());
        btnList.setOnClickListener(view -> {
            Intent intent = new Intent(this, ListNopol.class);
            startActivity(intent);
        });
    }

    private void setSpinner(){
        ArrayAdapter<Kategori> kategoris = new ArrayAdapter<>(this, R.layout.spinner_item, Kategori.values());
        inputKategori.setAdapter(kategoris);
    }

    private void clearText(){
        inputNama.setText("");
        inputNopol.setText("");
        inputAlamat.setText("");
        inputKategori.setSelection(0);
    }

    private void insertNopol(){
        String nama = inputNama.getText().toString();
        String nopol = inputNopol.getText().toString();
        String alamat = inputAlamat.getText().toString();
        String kategori = String.valueOf(inputKategori.getSelectedItemPosition());

        realm.beginTransaction();
        NopolRealm nopolRealm = realm.createObject(NopolRealm.class, getSaltString());
        nopolRealm.setNama(nama);
        nopolRealm.setNopol(nopol);
        nopolRealm.setAlamat(alamat);
        nopolRealm.setKategori(kategori);
        realm.commitTransaction();

        makeToast("Berhasil ditambah");

        clearText();
    }

    protected String getSaltString() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 10) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        return timeStamp + "" + salt.toString();
    }

    private void makeToast(String text){
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }
}
