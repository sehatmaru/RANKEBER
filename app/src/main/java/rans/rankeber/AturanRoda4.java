package rans.rankeber;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.realm.Realm;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;
import rans.rankeber.adapter.AturanAdapter;
import rans.rankeber.realm.AturanRealm;

public class AturanRoda4 extends AppCompatActivity implements AturanAdapter.OnClickAturanListener{

    Realm realm;
    AturanAdapter aturanAdapter;
    RecyclerView.LayoutManager linearLayoutManager;
    RecyclerView rcList;
    SearchView searchView;

    private List<AturanRealm> listAturan = Collections.EMPTY_LIST;
    private ScaleInAnimationAdapter scaleInAnimationAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aturanroda4);

        Realm.init(this);
        realm = Realm.getDefaultInstance();

        rcList = (RecyclerView) findViewById(R.id.rcList);
        searchView = (SearchView) findViewById(R.id.simpleSearchView);
        linearLayoutManager = new LinearLayoutManager(this);

        populateData();
    }

    @Override
    public void OnClickAturan(String idAturan) {
        createDialog(idAturan);
    }

    private void createDialog(String idAturan){
        realm.beginTransaction();
        AturanRealm aturan = realm.where(AturanRealm.class).equalTo("hashId", idAturan).findFirst();
        realm.commitTransaction();

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.dialog_aturan, null);
        dialogBuilder.setView(dialogView);

        final TextView txtJudul = dialogView.findViewById(R.id.judul);
        final TextView txtIsi = dialogView.findViewById(R.id.isi);
        final Button btnTutup = dialogView.findViewById(R.id.tutupBtn);

        txtJudul.setText(aturan.getJudulAturan());
        txtIsi.setText(aturan.getIsiAturan());

        btnTutup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        final AlertDialog b = dialogBuilder.create();
        b.show();
    }

    private void populateData(){
        realm.executeTransactionAsync(realm1 -> {
            listAturan = realm1.copyFromRealm(realm1.where(AturanRealm.class).equalTo("kategori", 2).findAll());
        }, () -> {
            if (!listAturan.isEmpty()) {
                aturanAdapter = new AturanAdapter(this, listAturan, this);
                scaleInAnimationAdapter = new ScaleInAnimationAdapter(aturanAdapter);
                rcList.setAdapter(scaleInAnimationAdapter);
                rcList.setLayoutManager(linearLayoutManager);
                rcList.setVisibility(View.VISIBLE);
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
        for (AturanRealm konten : realm.where(AturanRealm.class).equalTo("kategori", 2).findAll()) {
            final String text = konten.getJudulAturan().toLowerCase();
            if (text.contains(query)) {
                filteredList.add(konten);
            }
        }
        return filteredList;
    }
}
