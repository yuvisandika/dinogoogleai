package com.example.sig_hotel_jambi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class AdminLokasiList extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    FirebaseRecyclerOptions<AdapterLokasi> options;
    FirebaseRecyclerAdapter<AdapterLokasi,ViewHolderLokasi> adapter;

    FloatingActionButton ft_tambah, ft_hapus, ft_edit;

    DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_lokasi_list);

        ref= FirebaseDatabase.getInstance().getReference().child("Lokasi");

        //recyclerview--------
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setHasFixedSize(true);

        ft_tambah = findViewById(R.id.ft_tambah);
        ft_tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminLokasiList.this, AdminLokasiTambah.class);
                startActivity(intent);
            }
        });

        ft_edit = findViewById(R.id.ft_edit);
        ft_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditData();
            }
        });

        ft_hapus = findViewById(R.id.ft_hapus);
        ft_hapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AdminLokasiList.this, "Pilih Data Yang Ingin Dihapus", Toast.LENGTH_SHORT).show();
                HapusData();
            }
        });

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
                        Intent intent = new Intent(AdminLokasiList.this,AdminLokasiView.class);
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

    private void EditData() {


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
                        Intent intent = new Intent(AdminLokasiList.this,AdminLokasiView.class);
                        intent.putExtra("LokasiKey",getRef(position).getKey());
                        startActivity(intent);
                    }
                });

                holder.row_edit.setVisibility(View.VISIBLE);
                holder.row_edit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final DialogPlus dialogPlus = DialogPlus.newDialog(holder.imageView.getContext())
                                .setContentHolder(new ViewHolder(R.layout.activity_admin_lokasi_edit))
                                .setExpanded(true,1500)
                                .create();

                        dialogPlus.show();
                        View myview=dialogPlus.getHolderView();
                        //----------------------------------------------------
                        ImageView img = myview.findViewById(R.id.imgadd);
                        final EditText nama = myview.findViewById(R.id.txtnama);
                        final EditText alamat = myview.findViewById(R.id.txtalamat);
                        final EditText nomor = myview.findViewById(R.id.txtnomor);

                        final EditText tipe1 = myview.findViewById(R.id.txttipe1);
                        final EditText tipe2 = myview.findViewById(R.id.txttipe2);
                        final EditText tipe3 = myview.findViewById(R.id.txttipe3);

                        final EditText harga1 = myview.findViewById(R.id.txtharga1);
                        final EditText harga2 = myview.findViewById(R.id.txtharga2);
                        final EditText harga3 = myview.findViewById(R.id.txtharga3);

                        final EditText lat = myview.findViewById(R.id.txttitik1);
                        final EditText lon = myview.findViewById(R.id.txttitik2);

                        Button update = myview.findViewById(R.id.btnupdate);

                        nama.setText(model.getNama());
                        alamat.setText(model.getAlamat());
                        nomor.setText(model.getNomor());

                        tipe1.setText(model.getTipe1());
                        tipe2.setText(model.getTipe2());
                        tipe3.setText(model.getTipe3());

                        harga1.setText(model.getHarga1());
                        harga2.setText(model.getHarga2());
                        harga3.setText(model.getHarga3());

                        lat.setText(model.getLatitude());
                        lon.setText(model.getLongitude());

                        dialogPlus.show();
                        //refrence database
                        update.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Map<String,Object> map = new HashMap<>();
                                map.put("Nama",nama.getText().toString());
                                map.put("Alamat",alamat.getText().toString());
                                map.put("Nomor",nomor.getText().toString());

                                map.put("Tipe1",tipe1.getText().toString());
                                map.put("Tipe2",tipe2.getText().toString());
                                map.put("Tipe3",tipe3.getText().toString());

                                map.put("Harga1",harga1.getText().toString());
                                map.put("Harga2",harga2.getText().toString());
                                map.put("Harga3",harga3.getText().toString());

                                map.put("Latitude",lat.getText().toString());
                                map.put("Longitude",lon.getText().toString());

                                FirebaseDatabase.getInstance().getReference().child("Lokasi")
                                        .child(getRef(position).getKey()).updateChildren(map)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                dialogPlus.dismiss();
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                dialogPlus.dismiss();
                                            }
                                        });
                            }
                        });
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

    private void HapusData() {

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
                        Intent intent = new Intent(AdminLokasiList.this,AdminLokasiView.class);
                        intent.putExtra("LokasiKey",getRef(position).getKey());
                        startActivity(intent);
                    }
                });

                holder.row_hapus.setVisibility(View.VISIBLE);
                holder.row_hapus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AlertDialog.Builder builder=new AlertDialog.Builder(holder.imageView.getContext());
                        builder.setTitle("Delet");
                        builder.setMessage("Delete....");


                        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                FirebaseDatabase.getInstance().getReference().child("Lokasi").child(getRef(position).getKey()).removeValue();
                                FirebaseStorage.getInstance().getReference().child("Image_Lokasi").child(getRef(position).getKey()).delete();

                                Intent intent = new Intent(AdminLokasiList.this, AdminLokasiList.class);
                                Toast.makeText(AdminLokasiList.this, "Data Berhasi Dihapus", Toast.LENGTH_SHORT).show();
                                startActivity(intent);
                                finish();
                            }
                        });
                        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });
                        builder.show();

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


    @Override
    public void onBackPressed() {

        Intent intent = new Intent(AdminLokasiList.this,AdminHome.class);
        startActivity(intent
        );
    }
}