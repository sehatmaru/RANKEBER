package rans.rankeber.component;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import rans.rankeber.R;
import rans.rankeber.component.aturan.CreateAturan;
import rans.rankeber.component.aturan.UpdateAturan;
import rans.rankeber.component.nopol.CreateNopol;
import rans.rankeber.component.nopol.UpdateNopol;

public class Admin extends AppCompatActivity {

    Button btnAturan, btnNopol;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        btnAturan = findViewById(R.id.btnAturan);
        btnNopol = findViewById(R.id.btnNopol);

        btnAturan.setOnClickListener(view -> {
            Intent intent = new Intent(this, CreateAturan.class);
            startActivity(intent);
        });

        btnNopol.setOnClickListener(view -> {
            Intent intent = new Intent(this, CreateNopol.class);
            startActivity(intent);
        });
    }
}
