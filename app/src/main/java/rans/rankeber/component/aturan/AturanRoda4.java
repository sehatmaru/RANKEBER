package rans.rankeber.component.aturan;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.realm.Realm;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;
import rans.rankeber.R;
import rans.rankeber.component.Login;
import rans.rankeber.component.adapter.AturanAdapter;
import rans.rankeber.dependencies.realm.AturanRealm;
import rans.rankeber.dependencies.realm.UserDBLog;

public class AturanRoda4 extends AppCompatActivity implements AturanAdapter.OnClickAturanListener{

    Realm realm;
    AturanAdapter aturanAdapter;
    RecyclerView.LayoutManager linearLayoutManager;
    RecyclerView rcList;
    SearchView searchView;

    private List<AturanRealm> listAturan = Collections.EMPTY_LIST;
    private ScaleInAnimationAdapter scaleInAnimationAdapter;

    public static String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aturan_roda_4);

        Realm.init(this);
        realm = Realm.getDefaultInstance();

        checkLogUser();

        rcList = findViewById(R.id.rcList);
        searchView = findViewById(R.id.simpleSearchView);
        linearLayoutManager = new LinearLayoutManager(this);

        populateData();
    }

    @Override
    public void OnClickAturan(String idAturan, String imageURL) {
        startActivity(DetailAturan.createIntent(this, idAturan, imageURL));
    }

    private void populateData(){
        realm.executeTransactionAsync(realm1 -> listAturan = realm1.copyFromRealm(realm1.where(AturanRealm.class).equalTo("kategori", "2").findAll()), () -> {
            if (!listAturan.isEmpty()) {
                aturanAdapter = new AturanAdapter(this, listAturan, this);
                scaleInAnimationAdapter = new ScaleInAnimationAdapter(aturanAdapter);
                rcList.setAdapter(scaleInAnimationAdapter);
                rcList.setLayoutManager(linearLayoutManager);
                setSearchFunction();
            }
        });
    }

    private void setSearchFunction() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                List<AturanRealm> filteredList = filter(newText);
                aturanAdapter.animateTo(filteredList);
                rcList.scrollToPosition(0);

                return true;
            }
        });
    }

    private List<AturanRealm> filter(String query) {
        query = query.toLowerCase();
        final List<AturanRealm> filteredList = new ArrayList<>();
        for (AturanRealm konten : realm.where(AturanRealm.class).equalTo("kategori", "2").findAll()) {
            final String text = konten.getJudul().toLowerCase();
            if (text.contains(query)) {
                filteredList.add(konten);
            }
        }
        return filteredList;
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

    private void makeToast(String text){
        Toast.makeText(this, text, Toast.LENGTH_LONG).show();
    }
}
