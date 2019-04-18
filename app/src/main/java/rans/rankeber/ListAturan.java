package rans.rankeber;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;
import rans.rankeber.adapter.AturanAdapter;
import rans.rankeber.model.Aturan;
import rans.rankeber.realm.AturanRealm;
import rans.rankeber.realm.UserDBLog;

public class ListAturan extends AppCompatActivity implements AturanAdapter.OnClickAturanListener {

    Realm realm;

    SearchView searchView;
    RecyclerView recyclerView;
    AturanAdapter adapter;
    ScaleInAnimationAdapter scaleInAnimationAdapter;

    DatabaseReference databaseReference;
    ProgressDialog progressDialog;

    List<AturanRealm> listAturan = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_aturan);

        Realm.init(this);
        realm = Realm.getDefaultInstance();
        checkLogUser();

        recyclerView = findViewById(R.id.rcList);
        searchView = findViewById(R.id.simpleSearchView);
        progressDialog = new ProgressDialog(ListAturan.this);

        recyclerView.setLayoutManager(new LinearLayoutManager(ListAturan.this));

        progressDialog.setMessage("Loading Images From Firebase.");

        databaseReference = FirebaseDatabase.getInstance().getReference(Admin.DB_PATH);

        populateData();
    }

    @Override
    public void OnClickAturan(String imageURL) {
        startActivity(DetailAturan.createIntent(getApplicationContext(), imageURL));
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
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                List<AturanRealm> filteredList = filter(newText);
                adapter.animateTo(filteredList);
                recyclerView.scrollToPosition(0);

                return true;
            }
        });
    }

    private List<AturanRealm> filter(String query) {
        query = query.toLowerCase();
        final List<AturanRealm> filteredList = new ArrayList<>();
        for (AturanRealm konten : realm.where(AturanRealm.class).findAll()) {
            final String text = konten.getJudul().toLowerCase();
            if (text.contains(query)) {
                filteredList.add(konten);
            }
        }
        return filteredList;
    }

    private void populateData(){
        realm.executeTransactionAsync(realm1 -> listAturan = realm1.copyFromRealm(realm1.where(AturanRealm.class).findAll()), () -> {
            if (!listAturan.isEmpty()) {
                adapter = new AturanAdapter(this, listAturan, this);
                scaleInAnimationAdapter = new ScaleInAnimationAdapter(adapter);
                recyclerView.setAdapter(scaleInAnimationAdapter);
                setSearchFunction();
            }
        });
    }
}
