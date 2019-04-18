package rans.rankeber.component.nopol;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import io.realm.Realm;
import rans.rankeber.R;
import rans.rankeber.dependencies.enums.Kategori;
import rans.rankeber.dependencies.realm.NopolRealm;

public class UpdateNopol extends AppCompatActivity {

    Realm realm;

    EditText inputNama, inputNopol, inputAlamat;
    Spinner inputKategori;
    Button btnTambah, btnList;

    private static String id = "";

    public static Intent createIntent(Context context, String idNopol) {
        id = idNopol;
        return new Intent(context, UpdateNopol.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_nopol);

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
        NopolRealm nopolRealm = realm.where(NopolRealm.class).equalTo("hashId", id).findFirst();

        inputNama.setText(nopolRealm.getNama());
        inputNopol.setText(nopolRealm.getNopol());
        inputAlamat.setText(nopolRealm.getAlamat());
        inputKategori.setSelection(Integer.valueOf(nopolRealm.getKategori()));

        nopolRealm.setNama(nama);
        nopolRealm.setNopol(nopol);
        nopolRealm.setAlamat(alamat);
        nopolRealm.setKategori(kategori);
        realm.commitTransaction();

        makeToast("Berhasil diubah");

        clearText();
    }

    private void makeToast(String text){
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }
}
