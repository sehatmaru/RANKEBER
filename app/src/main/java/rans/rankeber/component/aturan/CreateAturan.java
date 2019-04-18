package rans.rankeber.component.aturan;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;

import io.realm.Realm;
import rans.rankeber.R;
import rans.rankeber.dependencies.enums.Kategori;
import rans.rankeber.dependencies.model.Aturan;

public class CreateAturan extends AppCompatActivity {
    Realm realm;

    EditText judulInput, isiInput;
    Button btnGambar, btnTambah, btnList;
    Spinner kategoriSpinner;
    ImageView imageView;
    ProgressDialog progressDialog;

    Uri FilePathUri;
    int Image_Request_Code = 7;

    StorageReference storageReference;
    DatabaseReference databaseReference;

    String STORAGE_PATH = "image_upload/";
    public static String DB_PATH = "aturan_db";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_aturan);

        Realm.init(this);
        realm = Realm.getDefaultInstance();

        storageReference = FirebaseStorage.getInstance().getReference();

        databaseReference = FirebaseDatabase.getInstance().getReference(DB_PATH);

        judulInput = findViewById(R.id.inputJudul);
        isiInput = findViewById(R.id.inputIsi);
        kategoriSpinner = findViewById(R.id.inputKategori);
        imageView = findViewById(R.id.imageView);
        btnGambar = findViewById(R.id.btnGbr);
        btnTambah = findViewById(R.id.btnTambah);
        btnList = findViewById(R.id.btnList);
        progressDialog = new ProgressDialog(this);

        setSpinner();

        btnGambar.setOnClickListener(view -> {
            Intent intent = new Intent();

            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Please Select Image"), Image_Request_Code);
        });

        btnTambah.setOnClickListener(view -> UploadImageFileToFirebaseStorage());

        btnList.setOnClickListener(view -> {
            Intent intent = new Intent(this, ListAturan.class);
            startActivity(intent);
        });
    }

    private void setSpinner(){
        ArrayAdapter<Kategori> kategoris = new ArrayAdapter<>(this, R.layout.spinner_item, Kategori.values());
        kategoriSpinner.setAdapter(kategoris);
    }

    private void clearText(){
        judulInput.setText("");
        isiInput.setText("");
        kategoriSpinner.setSelection(0);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Image_Request_Code && resultCode == RESULT_OK && data != null && data.getData() != null) {
            FilePathUri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), FilePathUri);
                imageView.setImageBitmap(bitmap);
                btnGambar.setText("Image Selected");
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Creating Method to get the selected image file Extension from File Path URI.
    public String GetFileExtension(Uri uri) {

        ContentResolver contentResolver = getContentResolver();

        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();

        // Returning the file Extension.
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri)) ;

    }

    public void UploadImageFileToFirebaseStorage() {
        String judull = judulInput.getText().toString();
        String isii = isiInput.getText().toString();
        int kategorii = kategoriSpinner.getSelectedItemPosition();

        if (FilePathUri != null && !judull.isEmpty() && !isii.isEmpty() && kategorii != 0) {

            progressDialog.setTitle("Image is Uploading...");
            progressDialog.show();

            StorageReference storageReference2nd = storageReference.child(STORAGE_PATH + System.currentTimeMillis() + "." + GetFileExtension(FilePathUri));

            storageReference2nd.putFile(FilePathUri)
                    .addOnSuccessListener(taskSnapshot -> {

                        String juduls = judulInput.getText().toString().trim();
                        String isis = isiInput.getText().toString().trim();
                        String kategoris = String.valueOf(kategoriSpinner.getSelectedItemPosition());

                        progressDialog.dismiss();

                        makeToast("Image Uploaded Successfully");

                        @SuppressWarnings("VisibleForTests")
                        Aturan aturan = new Aturan(juduls, isis, kategoris, taskSnapshot.getDownloadUrl().toString());

                        String ImageUploadId = databaseReference.push().getKey();

                        databaseReference.child(ImageUploadId).setValue(aturan);

                        clearText();
                    })
                    .addOnFailureListener(exception -> {
                        progressDialog.dismiss();
                        Toast.makeText(this, exception.getMessage(), Toast.LENGTH_LONG).show();
                    })

                    .addOnProgressListener(taskSnapshot -> {
                        progressDialog.setTitle("Image is Uploading...");
                    });
        }
        else {
            makeToast("Please Select Image or Add Image Name");
        }
    }

    private void makeToast(String text){
        Toast.makeText(this, text, Toast.LENGTH_LONG).show();
    }
}
