package rans.rankeber.component.aturan;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import io.realm.Realm;
import rans.rankeber.R;
import rans.rankeber.component.Login;
import rans.rankeber.dependencies.realm.AturanRealm;
import rans.rankeber.dependencies.realm.UserDBLog;

public class DetailAturan extends AppCompatActivity {

    TextView judul, isi;
    ImageView gbr;
    Button btnUpdate, btnDelete;

    AturanRealm aturanRealm;
    Realm realm;

    private static String image;

    public static Intent createIntent(Context context, String imageURL) {
        image = imageURL;
        return new Intent(context, DetailAturan.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_aturan);

        Realm.init(this);
        realm = Realm.getDefaultInstance();

        judul = findViewById(R.id.judul);
        isi = findViewById(R.id.isi);
        gbr = findViewById(R.id.gbr);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);

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
        }
        realm.commitTransaction();
    }

    private void fillData(){
        realm.beginTransaction();
        aturanRealm = realm.where(AturanRealm.class).equalTo("imageURL", image).findFirst();

        if (aturanRealm != null){
            judul.setText(aturanRealm.getJudul());
            isi.setText(aturanRealm.getIsi());
            Glide.with(this).load(image).into(gbr);
        }
        realm.commitTransaction();
    }

    private void makeToast(String text){
        Toast.makeText(this, text, Toast.LENGTH_LONG).show();
    }
}
