package rans.rankeber.component.aturan;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import io.realm.Realm;
import rans.rankeber.R;
import rans.rankeber.component.Login;
import rans.rankeber.component.Splashscreen;
import rans.rankeber.dependencies.realm.AturanRealm;
import rans.rankeber.dependencies.realm.UserDBLog;

public class DetailAturan extends AppCompatActivity {

    TextView judul, isi;
    ImageView gbr;
    Button btnUpdate, btnDelete;
    LinearLayout btnBottom;

    DatabaseReference databaseReference;
    StorageReference storageReference;

    AturanRealm aturanRealm;
    Realm realm;

    String role = "";
    public static String key;
    private static String imageURL;

    public static Intent createIntent(Context context, String keys, String image) {
        key = keys;
        imageURL = image;
        return new Intent(context, DetailAturan.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_aturan);

        Realm.init(this);
        realm = Realm.getDefaultInstance();

        checkLogUser();

        storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(imageURL);
        databaseReference = FirebaseDatabase.getInstance().getReference(CreateAturan.DB_PATH);

        judul = findViewById(R.id.judul);
        isi = findViewById(R.id.isi);
        gbr = findViewById(R.id.gbr);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);
        btnBottom = findViewById(R.id.btnBottom);

        btnDelete.setOnClickListener(view -> deleteData(key));
        btnUpdate.setOnClickListener(view -> {
            finish();
            startActivity(UpdateAturan.createIntent(this, key));
        });

        if (role.equals("user")) toUser();

        checkLogUser();
        fillData();
    }

    private void checkLogUser(){
        realm.beginTransaction();
        UserDBLog userDBLog = realm.where(UserDBLog.class).findFirst();

        if (userDBLog == null){
            makeToast("Anda harus login terlebih dahulu");
            Intent intent = new Intent(getApplicationContext(), Login.class);
            startActivity(intent);
        } else{
            role = userDBLog.getRole();
        }

        realm.commitTransaction();
    }

    private void fillData(){
        realm.beginTransaction();
        aturanRealm = realm.where(AturanRealm.class).equalTo("key", key).findFirst();

        if (aturanRealm != null){
            judul.setText(aturanRealm.getJudul());
            isi.setText(aturanRealm.getIsi());
            Glide.with(this).load(aturanRealm.getImageURL()).into(gbr);
        }
        realm.commitTransaction();
    }

    private void makeToast(String text){
        Toast.makeText(this, text, Toast.LENGTH_LONG).show();
    }

    private void deleteData(String key){
        storageReference.delete().addOnSuccessListener(aVoid -> {
            databaseReference.child(key).removeValue();

            Intent intent = new Intent(this, CreateAturan.class);
            startActivity(intent);
        }).addOnFailureListener(exception -> {

        });
    }

    private void toUser(){
        btnBottom.setVisibility(View.GONE);
    }
}
