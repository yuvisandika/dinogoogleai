package com.example.sig_hotel_jambi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class UserLokasiList extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    FirebaseRecyclerOptions<AdapterLokasi> options;
    FirebaseRecyclerAdapter<AdapterLokasi,ViewHolderLokasi> adapter;

    DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_lokasi_list);

        ref= FirebaseDatabase.getInstance().getReference().child("Lokasi");

        //recyclerview--------
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setHasFixedSize(true);

        LoadData();
    }

    private void LoadData() {

        options=new FirebaseRecyclerOptions.Builder<AdapterLokasi>().setQuery(ref,AdapterLokasi.class).build();
        adapter=new FirebaseRecyclerAdapter<AdapterLokasi, ViewHolderLokasi>(options) {

            @Override
            protected void onBindViewHolder(@NonNull final ViewHolderLokasi holder, final int position, @NonNull final AdapterLokasi model) {
                holder.txtView1.setText(model.getNama());
                holder.txtView2.setText(model.getAlamat());
                Picasso.get().load(model.getImageUrl()).into(holder.imageView);


                //holder view (row.xml & MyViewHolder)
                holder.v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(UserLokasiList.this,UserLokasiView.class);
                        intent.putExtra("LokasiKey",getRef(position).getKey());
                        startActivity(intent);
                    }
                });
            }
            @NonNull
            @Override
            public ViewHolderLokasi onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.row,parent,false);
                return new ViewHolderLokasi(v);
            }
        };
        adapter.startListening();
        recyclerView.setAdapter(adapter);
    }

}