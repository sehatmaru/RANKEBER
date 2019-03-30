package rans.rankeber;


import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
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

import com.joanzapata.iconify.widget.IconTextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.realm.Realm;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;
import rans.rankeber.adapter.AturanAdapter;
import rans.rankeber.realm.AturanRealm;
import rans.rankeber.utils.Konstanta;

public class AturanRoda2 extends AppCompatActivity implements AturanAdapter.OnClickAturanListener{

    Realm realm;
    AturanAdapter aturanAdapter;
    RecyclerView.LayoutManager linearLayoutManager;
    RecyclerView rcList;
    SearchView searchView;
    IconTextView spinner;
    CoordinatorLayout coordinatorLayout;

    private List<AturanRealm> listAturan = Collections.EMPTY_LIST;
    private ScaleInAnimationAdapter scaleInAnimationAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aturanroda2);

        Realm.init(this);
        realm = Realm.getDefaultInstance();

        rcList = (RecyclerView) findViewById(R.id.rcList);
        searchView = (SearchView) findViewById(R.id.simpleSearchView);
        linearLayoutManager = new LinearLayoutManager(this);
        spinner = (IconTextView) findViewById(R.id.spinnerIcon);

        spinner.setVisibility(View.VISIBLE);
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
            listAturan = realm1.copyFromRealm(realm1.where(AturanRealm.class).equalTo("kategori", 1).findAll());
        }, () -> {
            if (!listAturan.isEmpty()) {
                aturanAdapter = new AturanAdapter(this, listAturan, this);
                scaleInAnimationAdapter = new ScaleInAnimationAdapter(aturanAdapter);
                rcList.setAdapter(scaleInAnimationAdapter);
                rcList.setLayoutManager(linearLayoutManager);
                rcList.setVisibility(View.VISIBLE);
                updateLayout(Konstanta.LAYOUT_SUCCESS);
                setSearchFunction();
            }else {
                updateLayout(Konstanta.LAYOUT_EMPTY);
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
        for (AturanRealm konten : realm.where(AturanRealm.class).equalTo("kategori", 1).findAll()) {
            final String text = konten.getJudulAturan().toLowerCase();
            if (text.contains(query)) {
                filteredList.add(konten);
            }
        }
        return filteredList;
    }

    private void updateLayout(String status) {
        switch (status) {
            case Konstanta.LAYOUT_SUCCESS:
                spinner.setVisibility(View.GONE);
                rcList.setVisibility(View.VISIBLE);
                break;
            case Konstanta.LAYOUT_EMPTY:
                createSnackbar(Konstanta.LAYOUT_EMPTY).show();
                spinner.setText("{fa-info 200%}  No data found");
                break;
            case Konstanta.LAYOUT_ERROR:
                createSnackbar(Konstanta.LAYOUT_ERROR).show();
                spinner.setText("{fa-info 200%} Error");
                break;
            case Konstanta.LAYOUT_LOADING:
                rcList.setVisibility(View.GONE);
                spinner.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }
    }

    private Snackbar createSnackbar(String message) {
        return Snackbar.make(coordinatorLayout, message, Snackbar.LENGTH_INDEFINITE);
    }
}
