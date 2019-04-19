package rans.rankeber.component.aturan;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import rans.rankeber.R;
import rans.rankeber.dependencies.enums.Kategori;
import rans.rankeber.dependencies.model.Aturan;
import rans.rankeber.dependencies.realm.AturanRealm;

public class UpdateAturan extends AppCompatActivity {

    Realm realm;
    AturanRealm aturanRealm;

    EditText judulInput, isiInput;
    Button btnSimpan;
    Spinner kategoriSpinner;
    ProgressDialog progressDialog;

    ArrayAdapter<Kategori> kategoris;

    DatabaseReference databaseReference;
    static String key, imageURL;

    static String DB_PATH = "aturan_db";

    public static Intent createIntent(Context context, String keys) {
        key = keys;
        return new Intent(context, UpdateAturan.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_aturan);

        Realm.init(this);
        realm = Realm.getDefaultInstance();

        databaseReference = FirebaseDatabase.getInstance().getReference(DB_PATH);

        judulInput = findViewById(R.id.inputJudul);
        isiInput = findViewById(R.id.inputIsi);
        kategoriSpinner = findViewById(R.id.inputKategori);
        btnSimpan = findViewById(R.id.btnSimpan);
        progressDialog = new ProgressDialog(this);

        getRealmData();
        setData();
        setSpinner();

        btnSimpan.setOnClickListener(view -> UploadImageFileToFirebaseStorage());
    }

    private void setSpinner(){
        kategoris = new ArrayAdapter<>(this, R.layout.spinner_item, Kategori.values());
        kategoriSpinner.setAdapter(kategoris);
    }

    private void setData(){
        judulInput.setText(aturanRealm.getJudul());
        isiInput.setText(aturanRealm.getIsi());
    }

    private void clearText(){
        judulInput.setText("");
        isiInput.setText("");
        kategoriSpinner.setSelection(0);
    }

    public void UploadImageFileToFirebaseStorage() {
        String judull = judulInput.getText().toString();
        String isii = isiInput.getText().toString();
        int kategorii = kategoriSpinner.getSelectedItemPosition();

        if (!judull.isEmpty() && !isii.isEmpty() && kategorii != 0) {

            progressDialog.setTitle("Updating data");
            progressDialog.show();

            progressDialog.dismiss();

            makeToast("Data updated successfully");

            @SuppressWarnings("VisibleForTests")
            Aturan aturan = new Aturan(key, judull, isii, String.valueOf(kategorii), imageURL);

            databaseReference.child(key).setValue(aturan);

            clearText();
            toAturan();
        }
        else {
            makeToast("Harap isi semua data");
        }
    }

    private void makeToast(String text){
        Toast.makeText(this, text, Toast.LENGTH_LONG).show();
    }

    private void getRealmData(){
        realm.beginTransaction();
        aturanRealm = realm.where(AturanRealm.class).equalTo("key", key).findFirst();
        imageURL = aturanRealm.getImageURL();
        realm.commitTransaction();
    }

    private void toAturan(){
        finish();
        Intent intent = new Intent(this, CreateAturan.class);
        startActivity(intent);
    }
}
