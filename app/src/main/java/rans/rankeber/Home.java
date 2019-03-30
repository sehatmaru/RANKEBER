package rans.rankeber;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;

public class Home extends AppCompatActivity {

    LinearLayout roda2;
    LinearLayout roda4;
    LinearLayout about;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        makeToast("Welcome to Rankeber");

        roda2 = (LinearLayout) this.findViewById(R.id.roda2);
        roda4 = (LinearLayout) this.findViewById(R.id.roda4);
        about = (LinearLayout) this.findViewById(R.id.about);

        roda2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), AturanRoda2.class);
                startActivity(i);
            }
        });

        roda4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), AturanRoda4.class);
                startActivity(i);
            }
        });

        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), About.class);
                startActivity(i);
            }
        });
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

    private void signOut(){
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
        Toast.makeText(this, "Logout Successfully", Toast.LENGTH_LONG).show();
    }

    private void logoutDialog() {
        MaterialDialog dialog = new MaterialDialog.Builder(this)
                .title(R.string.title_logout)
                .content(R.string.content_logout)
                .positiveText(R.string.yes)
                .negativeText(R.string.no)
                .onPositive((dialog1, which) -> {
                    signOut();
                })
                .onNegative((dialog1, which) -> {

                })
                .build();
        dialog.show();
    }

    private void makeToast(String text){
        Toast.makeText(this, text, Toast.LENGTH_LONG).show();
    }
}
