package rans.rankeber;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import io.realm.Realm;
import rans.rankeber.realm.AturanRealm;
import rans.rankeber.realm.UserDBLog;

public class DetailAturan extends AppCompatActivity {

    TextView judul, isi;
    ImageView gbr;

    AturanRealm aturanRealm;
    Realm realm;

    private static String id;

    public static Intent createIntent(Context context, String idProduk) {
        id = idProduk;
        return new Intent(context, DetailAturan.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_aturan);

        Realm.init(this);
        realm = Realm.getDefaultInstance();

        judul = (TextView) findViewById(R.id.judul);
        isi = (TextView) findViewById(R.id.isi);
        gbr = (ImageView) findViewById(R.id.gbr);

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
        aturanRealm = realm.where(AturanRealm.class).equalTo("hashId", id).findFirst();

        if (aturanRealm != null){
            judul.setText(aturanRealm.getJudulAturan());
            isi.setText(aturanRealm.getIsiAturan());
        }
        realm.commitTransaction();
    }

    private void makeToast(String text){
        Toast.makeText(this, text, Toast.LENGTH_LONG).show();
    }
}
