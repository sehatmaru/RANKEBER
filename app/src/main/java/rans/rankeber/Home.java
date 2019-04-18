package rans.rankeber;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.realm.Realm;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;
import rans.rankeber.adapter.NopolAdapter;
import rans.rankeber.realm.NopolRealm;
import rans.rankeber.realm.UserDBLog;

public class Home extends AppCompatActivity {

    LinearLayout roda2;
    LinearLayout roda4;
    LinearLayout about;
    LinearLayout admin;
    CardView cardAdmin;
    RecyclerView rcList;
    SearchView searchView;

    List<NopolRealm> listNopol = Collections.EMPTY_LIST;
    Realm realm;
    UserDBLog userDBLog;
    NopolAdapter nopolAdapter;
    ScaleInAnimationAdapter scaleInAnimationAdapter;
    RecyclerView.LayoutManager linearLayoutManager;

    String role = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Realm.init(this);
        realm = Realm.getDefaultInstance();

        checkLogUser();

        roda2 = findViewById(R.id.roda2);
        roda4 = findViewById(R.id.roda4);
        about = findViewById(R.id.about);
        admin = findViewById(R.id.admin);
        rcList = findViewById(R.id.rcList);
        searchView = findViewById(R.id.searchView);
        cardAdmin = findViewById(R.id.cardAdmin);
        linearLayoutManager = new LinearLayoutManager(this);

        roda2.setOnClickListener(view -> {
            Intent i = new Intent(getApplicationContext(), AturanRoda2.class);
            startActivity(i);
        });

        roda4.setOnClickListener(view -> {
            Intent i = new Intent(getApplicationContext(), AturanRoda4.class);
            startActivity(i);
        });

        about.setOnClickListener(view -> {
            Intent i = new Intent(getApplicationContext(), About.class);
            startActivity(i);
        });

        admin.setOnClickListener(view -> {
            Intent i = new Intent(getApplicationContext(), Admin.class);
            startActivity(i);
        });

        if (role.equals("user"))
            hideAdmin();

        populateData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_logout) {
            logoutDialog();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        logoutDialog();
    }

    private void signOut(){
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
        makeToast("Berhasil Logout");
    }

    private void logoutDialog() {
        MaterialDialog dialog = new MaterialDialog.Builder(this)
                .title(R.string.title_logout)
                .content(R.string.content_logout)
                .positiveText(R.string.yes)
                .negativeText(R.string.no)
                .onPositive((dialog1, which) -> {
                    clearLogUser();
                    signOut();
                })
                .onNegative((dialog1, which) -> {

                })
                .build();
        dialog.show();
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

    private void makeToast(String text){
        Toast.makeText(this, text, Toast.LENGTH_LONG).show();
    }

    private void clearLogUser(){
        realm.beginTransaction();
        userDBLog = realm.where(UserDBLog.class).findFirst();

        if (userDBLog != null)
            userDBLog.deleteFromRealm();

        realm.commitTransaction();
    }

    private void populateData(){
        realm.executeTransactionAsync(realm1 -> listNopol = realm1.copyFromRealm(realm1.where(NopolRealm.class).findAll()), () -> {
            if (!listNopol.isEmpty()) {
                nopolAdapter = new NopolAdapter(this, listNopol);
                scaleInAnimationAdapter = new ScaleInAnimationAdapter(nopolAdapter);
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
                List<NopolRealm> filteredList = filter(newText);
                nopolAdapter.animateTo(filteredList);
                rcList.scrollToPosition(0);

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
        Log.e("bk", "" + filteredList.toString());
        return filteredList;
    }

    private void hideAdmin(){
        searchView.setVisibility(View.GONE);
        cardAdmin.setVisibility(View.GONE);
        rcList.setVisibility(View.GONE);
    }
}
