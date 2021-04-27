package com.example.sig_hotel_jambi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class AdminLokasiView extends AppCompatActivity {


    ImageView imageView;

    TextView nama, alamat,nomor;
    TextView tipe1, tipe2, tipe3;
    TextView harga1, harga2, harga3;

    DatabaseReference ref,DataRef;
    StorageReference StorageRef;

    //rating
    TextView getRating;
    Button btnsubmit;
    RatingBar rate;
    float myRating = 0;

    String nomorcell;
    //maps metod
    Uri gmmIntentUri;
    String latitude;
    String longitude;

    Intent mapIntent;
    String goolgeMap = "com.google.android.apps.maps";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_lokasi_view);

        imageView = findViewById(R.id.img);

        nama = findViewById(R.id.nama);
        alamat = findViewById(R.id.alamat);
        nomor = findViewById(R.id.nomor);

        tipe1 = findViewById(R.id.tipe1);
        tipe2 = findViewById(R.id.tipe2);
        tipe3 = findViewById(R.id.tipe3);

        harga1 = findViewById(R.id.harga1);
        harga2 = findViewById(R.id.harga2);
        harga3 = findViewById(R.id.harga3);

        //rating
        getRating = findViewById(R.id.txtrate);
        btnsubmit = findViewById(R.id.btnsubmit);
        rate = findViewById(R.id.rateBar);



        final String LokasiKey = getIntent().getStringExtra("LokasiKey");
        ref = FirebaseDatabase.getInstance().getReference().child("Lokasi");
        DataRef = FirebaseDatabase.getInstance().getReference().child("Lokasi").child(LokasiKey);
        StorageRef = FirebaseStorage.getInstance().getReference().child("Image").child(LokasiKey);

        ref.child(LokasiKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {

                    String ImageUrl = dataSnapshot.child("ImageUrl").getValue().toString();

                    String Nama = dataSnapshot.child("Nama").getValue().toString();
                    String Alamat = dataSnapshot.child("Alamat").getValue().toString();
                    String Nomor = dataSnapshot.child("Nomor").getValue().toString();

                    String Tipe1 = dataSnapshot.child("Tipe1").getValue().toString();
                    String Tipe2 = dataSnapshot.child("Tipe2").getValue().toString();
                    String Tipe3 = dataSnapshot.child("Tipe3").getValue().toString();

                    String Harga1 = dataSnapshot.child("Harga1").getValue().toString();
                    String Harga2 = dataSnapshot.child("Harga2").getValue().toString();
                    String Harga3 = dataSnapshot.child("Harga3").getValue().toString();


                    String Rating = dataSnapshot.child("Rating").getValue().toString();


                    Picasso.get().load(ImageUrl).into(imageView);
                    nama.setText(Nama);
                    alamat.setText(Alamat);
                    nomor.setText(Nomor);

                    tipe1.setText(Tipe1);
                    tipe2.setText(Tipe2);
                    tipe3.setText(Tipe3);

                    harga1.setText(Harga1);
                    harga2.setText(Harga2);
                    harga3.setText(Harga3);

                    getRating.setText(Rating);

                    nomorcell = dataSnapshot.child("Nomor").getValue().toString();
                    latitude = dataSnapshot.child("Latitude").getValue().toString();
                    longitude = dataSnapshot.child("Longitude").getValue().toString();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        rate.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {

                getRating.setText("" + rating);

                myRating = ratingBar.getRating();

            }
        });

        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View vi) {


                HashMap map = new HashMap();
                map.put("Rating",""+myRating);



                DataRef.updateChildren(map).addOnSuccessListener(new OnSuccessListener() {
                    @Override
                    public void onSuccess(Object o) {

                    }
                });
                Toast.makeText(AdminLokasiView.this, "Berhasil Memberi Rating!", Toast.LENGTH_LONG).show();
            }
        });


    }

    public void onNav(View view) {
        if (view.getId() == R.id.btn_nav) {
            gmmIntentUri = Uri.parse("google.navigation:q=" + latitude + "," + longitude);
            mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            mapIntent.setPackage(goolgeMap);
            if (mapIntent.resolveActivity(getPackageManager()) != null) {
                startActivity(mapIntent);
            } else {
                Toast.makeText(AdminLokasiView.this, "Google Maps Belum Terinstall", Toast.LENGTH_SHORT).show();
            }
        }
    }
    public void onCall (View view){
        String number = nomorcell;
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:"+number));
        startActivity(intent);

    }
}
