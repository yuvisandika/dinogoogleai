package com.example.sig_hotel_jambi;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;

public class AdminLokasiTambah extends AppCompatActivity {

    private static final int REQUEST_CODE_IMAGE = 101;
    private ImageView imageadd;
    private EditText txtnama, txtalamat, txtnomor, txtlatitude, txtlongitude;
    private EditText txttipe1, txttipe2, txttipe3;
    private EditText txtharga1, txtharga2, txtharga3;

    private Button btnupload;

    Uri imageUri;
    boolean isImageAdded;

    float Rating = 0;

    DatabaseReference ref;
    StorageReference sref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_lokasi_tambah);

        txtnama = findViewById(R.id.txtnama); //nama
        txtalamat = findViewById(R.id.txtalamat); //alamat
        txtnomor = findViewById(R.id.txtnomor); //nomor
        // tipe dan harga
        txttipe1 = findViewById(R.id.txttipe1);
        txttipe2 = findViewById(R.id.txttipe2);
        txttipe3 = findViewById(R.id.txttipe3);
        txtharga1 = findViewById(R.id.txtharga1);
        txtharga2 = findViewById(R.id.txtharga2);
        txtharga3 = findViewById(R.id.txtharga3);

        txtlatitude = findViewById(R.id.txttitik1);  //keordinat lat
        txtlongitude = findViewById(R.id.txttitik2); //keordinat long

        ref = FirebaseDatabase.getInstance().getReference().child("Lokasi");
        sref = FirebaseStorage.getInstance().getReference().child("Image_Lokasi");

        imageadd = findViewById(R.id.imgadd); //gambar
        imageadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inten = new Intent();
                inten.setType("image/*");
                inten.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(inten, REQUEST_CODE_IMAGE);

            }
        });

        //upload
        btnupload = findViewById(R.id.btnsave);
        btnupload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String nama = txtnama.getText().toString();
                final String alamat = txtalamat.getText().toString();
                final String nomor = txtnomor.getText().toString();

                final String tipe1 = txttipe1.getText().toString();
                final String tipe2 = txttipe2.getText().toString();
                final String tipe3 = txttipe3.getText().toString();

                final String harga1 = txtharga1.getText().toString();
                final String harga2 = txtharga2.getText().toString();
                final String harga3 = txtharga3.getText().toString();

                final String latitude = txtlatitude.getText().toString();
                final String longitude = txtlongitude.getText().toString();


                if (TextUtils.isEmpty(txtnama.getText().toString())) {
                    Toast.makeText(AdminLokasiTambah.this, "Masukan Nama Tempat", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(txtalamat.getText().toString())) {
                    Toast.makeText(AdminLokasiTambah.this, "Masukan Alamat", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(txtnomor.getText().toString())) {
                    Toast.makeText(AdminLokasiTambah.this, "Masukan Nomor Telpon", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(txtlatitude.getText().toString())) {
                    Toast.makeText(AdminLokasiTambah.this, "Masukan Titik Latitude", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(txtlongitude.getText().toString())) {
                    Toast.makeText(AdminLokasiTambah.this, "Masukan Titik Longitude", Toast.LENGTH_SHORT).show();


                    //komplit
                } else if (isImageAdded != false && nama != null && alamat != null && nomor != null
                        && tipe1 != null && tipe2 != null && tipe3 != null
                        && harga1 != null && harga2 != null && harga3 != null
                        && latitude != null && longitude != null) {

                    upload(nama, alamat, nomor,
                            tipe1, tipe2, tipe3,
                            harga1,  harga2, harga3,
                            latitude, longitude);

                } else {
                    Toast.makeText(AdminLokasiTambah.this, "Mohon Masukan Gambar", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void upload(final String nama, final String alamat, final String nomor,
                        final String tipe1, final String tipe2, final String tipe3,
                        final String harga1, final String harga2, final String harga3,
                        final String latitude, final String longitude) {

        final String key = ref.push().getKey();
        //progress dialog
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Data Sedang Di Upload . . . . ");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        sref.child(key + ".jpg").putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                sref.child(key + ".jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        HashMap hashMap = new HashMap();
                        hashMap.put("Nama", nama);
                        hashMap.put("Alamat", alamat);
                        hashMap.put("Nomor", nomor);

                        hashMap.put("Tipe1", tipe1);
                        hashMap.put("Tipe2", tipe2);
                        hashMap.put("Tipe3", tipe3);

                        hashMap.put("Harga1", harga1);
                        hashMap.put("Harga2", harga2);
                        hashMap.put("Harga3", harga3);

                        hashMap.put("Latitude", "-"+latitude);
                        hashMap.put("Longitude", longitude);
                        hashMap.put("ImageUrl", uri.toString());


                        hashMap.put("Rating",""+Rating);

                        ref.child(key).setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                progressDialog.dismiss();
                                startActivity(new Intent(getApplicationContext(), AdminLokasiList.class));
                                Toast.makeText(AdminLokasiTambah.this, "Sukses", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_IMAGE && data != null) {
            imageUri = data.getData();

            isImageAdded = true;
            imageadd.setImageURI(imageUri);
        }
    }
}