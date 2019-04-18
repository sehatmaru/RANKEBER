package rans.rankeber.component.nopol;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;
import rans.rankeber.R;
import rans.rankeber.component.Login;
import rans.rankeber.component.adapter.NopolAdapter;
import rans.rankeber.dependencies.realm.NopolRealm;
import rans.rankeber.dependencies.realm.UserDBLog;

public class ListNopol extends AppCompatActivity implements NopolAdapter.OnClickNopolListener {

    Realm realm;

    SearchView searchView;
    RecyclerView recyclerView;

    NopolAdapter adapter;
    ScaleInAnimationAdapter scaleInAnimationAdapter;

    List<NopolRealm> listNopol = new ArrayList<>();

    AlertDialog b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_nopol);

        Realm.init(this);
        realm = Realm.getDefaultInstance();

        checkLogUser();

        searchView = findViewById(R.id.simpleSearchView);
        recyclerView = findViewById(R.id.rcList);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        populateData();
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

    private void setSearchFunction() {
        searchView.setOnQueryTextListener(new android.support.v7.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                List<NopolRealm> filteredList = filter(newText);
                adapter.animateTo(filteredList);
                recyclerView.scrollToPosition(0);

                return true;
            }
        });
    }

    private List<NopolRealm> filter(String query) {
        query = query.toLowerCase();
        final List<NopolRealm> filteredList = new ArrayList<>();
        for (NopolRealm konten : realm.where(NopolRealm.class).findAll()) {
            final String text = konten.getNopol().toLowerCase();
            if (text.contains(query)) {
                filteredList.add(konten);
            }
        }
        return filteredList;
    }

    private void populateData(){
        realm.executeTransactionAsync(realm1 -> listNopol = realm1.copyFromRealm(realm1.where(NopolRealm.class).findAll()), () -> {
            if (!listNopol.isEmpty()) {
                adapter = new NopolAdapter(this, listNopol, this);
                scaleInAnimationAdapter = new ScaleInAnimationAdapter(adapter);
                recyclerView.setAdapter(scaleInAnimationAdapter);
                setSearchFunction();
            }
        });
    }

    @Override
    public void OnClickNopol(String idNopol) {
        createDialog(idNopol);
    }

    private void createDialog(String idNopol){
        realm.beginTransaction();
        NopolRealm nopol = realm.where(NopolRealm.class).equalTo("hashId", idNopol).findFirst();
        realm.commitTransaction();

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.dialog_window, null);
        dialogBuilder.setView(dialogView);

        final Button btnUpdate = dialogView.findViewById(R.id.btnUpdate);
        final Button btnDelete = dialogView.findViewById(R.id.btnDelete);

        btnUpdate.setOnClickListener(v -> {
            b.dismiss();
            startActivity(UpdateNopol.createIntent(this, idNopol));
        });

        btnDelete.setOnClickListener(v -> {
            deleteNopol(idNopol);
            makeToast("Berhasil dihapus");

            b.dismiss();
            populateData();
        });

        b = dialogBuilder.create();
        b.show();
    }

    private void deleteNopol(String idNopol){
        realm.beginTransaction();
        realm.where(NopolRealm.class).equalTo("hashId", idNopol).findFirst().deleteFromRealm();
        realm.commitTransaction();
    }
}
