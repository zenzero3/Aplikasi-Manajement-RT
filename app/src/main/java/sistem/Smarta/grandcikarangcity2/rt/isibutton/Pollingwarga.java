package sistem.Smarta.grandcikarangcity2.rt.isibutton;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import sistem.Smarta.grandcikarangcity2.R;
import sistem.Smarta.grandcikarangcity2.model.AppHelper;
import sistem.Smarta.grandcikarangcity2.model.VolleyMultipartRequest;
import sistem.Smarta.grandcikarangcity2.model.VolleySingleton;
import sistem.Smarta.grandcikarangcity2.rt.Login;
import sistem.Smarta.grandcikarangcity2.rt.formregister1;

public class Pollingwarga extends AppCompatActivity {
    TextView lala;
    String progessone,progesstwo,progressthree;
    Bitmap ekoa;
    Uri dwi;
    int i;
    EditText namapolling,des;
    private ImageView img,tambah;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pollingwarga);
        namapolling = findViewById(R.id.namepolling);
        des = findViewById(R.id.deskrippolling);
        ImageButton imageButton = findViewById(R.id.backbuttwarga);
        lala = findViewById(R.id.lampiran);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Button bagi = findViewById(R.id.bagi);
        tambah = findViewById(R.id.tambahoke);
        Button list = findViewById(R.id.bagisatu);
        final Spinner satu = findViewById(R.id.kategorisatu);
        final Spinner dua  = findViewById(R.id.kategoridua);
        final Spinner tiga = findViewById(R.id.kategoritiga);
        final TextView agama = findViewById(R.id.pollingagama);

        ArrayAdapter<CharSequence> adapters =  ArrayAdapter.createFromResource(getApplicationContext(),R.array.pollingwarga1,R.layout.itemoke);
        adapters.setDropDownViewResource(R.layout.itemoke);
        satu.setAdapter(adapters);
        satu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
              progessone = satu.getSelectedItem().toString();
              if (progessone.equals("Agama")){
                  agama.setVisibility(View.VISIBLE);
                  tiga.setVisibility(View.VISIBLE);
                  ArrayAdapter<CharSequence> ada = ArrayAdapter.createFromResource(getApplicationContext(),R.array.Agama,R.layout.itemoke);
                  ada.setDropDownViewResource(R.layout.itemoke);
                  tiga.setAdapter(ada);
                  tiga.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                      @Override
                      public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                          progressthree= tiga.getSelectedItem().toString();
                      }

                      @Override
                      public void onNothingSelected(AdapterView<?> parent) {

                      }
                  });
              }else {
                  agama.setVisibility(View.GONE);
                  tiga.setVisibility(View.GONE);
              }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        ArrayAdapter<CharSequence> adapter =  ArrayAdapter.createFromResource(getApplicationContext(),R.array.Pollingpenerima,R.layout.itemoke);
        adapter.setDropDownViewResource(R.layout.itemoke);
        dua.setAdapter(adapter);
        dua.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                progesstwo = dua.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });




        bagi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String aku =namapolling.getText().toString();
                String dua = des.getText().toString();
                if (aku.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Nama Informasi Tidak Boleh Kosong",Toast.LENGTH_LONG).show();
                    namapolling.setError("Nama Informasi Kosong");
                }else if (dua.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Deskripsi Tidak boleh Kosong",Toast.LENGTH_LONG).show();
                    des.setError("Deskripsi Kosong");
                }else if (progessone.equals("Pilih Kategori Poling")){
                    Toast.makeText(getApplicationContext(),"Pilih Kategori Informasi",Toast.LENGTH_LONG).show();
                }else if (progesstwo.equals("Pilih Penerima Poling Berdasarkan Jenis Kelamin")){
                    Toast.makeText(getApplicationContext(),"Pilih Penerima Poling",Toast.LENGTH_LONG).show();
                }else if (progessone.equals("Agama")){
                    if (progesstwo.equals("Pilih Penerima Poling Berdasarkan Jenis Kelamin")){
                        Toast.makeText(getApplicationContext(),"Pilih Penerima Informasi",Toast.LENGTH_LONG).show();
                    } else {
                    postdataagam();
                    }
                }else {
                    progressthree="Semua Agama";
                    postdata();
                }
            }
        });

        list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Pollingwarga.this,List_pollingrt.class);
                startActivity(intent);
            }
        });
   tambah.setOnClickListener(new View.OnClickListener() {
       @Override
       public void onClick(View v) {

           final BottomSheetDialog dialog = new BottomSheetDialog(Pollingwarga.this, R.style.CustomBottomSheetDialogTheme);
           dialog.setContentView(R.layout.bottomsheetone);
           final LinearLayout foto = dialog.findViewById(R.id.kamera);
           final  TextView back   = dialog.findViewById(R.id.cancel);
           final LinearLayout galeri = dialog.findViewById(R.id.ambilhp);
           back.setText("Lampirkan Gambar / Foto");
           dialog.setCanceledOnTouchOutside(false);
           Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
           dialog.show();
           back.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   dialog.dismiss();
               }
           });

           galeri.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   i=1;
                   Intent intent = new   Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                   startActivityForResult(intent, 1);
                   dialog.dismiss();
               }
           });
           foto.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   i=2;
                   Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                   startActivityForResult(intent, 2);

                   dialog.dismiss();
               }
           });


       }
   });


    }

    private void postdataagam() {
        SharedPreferences sharedPreferences = getSharedPreferences("blood", Context.MODE_PRIVATE);
        String isiid=sharedPreferences.getString("ide","empty");
        getrtdata(isiid);
    }

    private void postdata() {
        SharedPreferences sharedPreferences = getSharedPreferences("blood", Context.MODE_PRIVATE);
        String isiid=sharedPreferences.getString("ide","empty");
        getrtdata(isiid);

    }

    private void getrtdata(String isiid) {
        String UrlLogin="http://gccestatemanagement.online/public/api/getrt/"+isiid;
        StringRequest stringRequest= new StringRequest(Request.Method.GET, UrlLogin,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            String message = jsonObject.getString("message");
                            String status  = jsonObject.getString("success");
                            JSONObject data=jsonObject.getJSONObject("data");

                            if (status.equals("true")) {
                                if (message.equals("get rt")) {
                                    String isi = data.getString("id").trim();
                                    String idesa= data.getString("desa").trim();
                                    postdatamain(isi);

                                }
                            }else {
                                Toast.makeText(Pollingwarga.this, message, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e){
                            e.printStackTrace();
                            Toast.makeText(Pollingwarga.this, "Periksa Koneksi Anda" + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Pollingwarga.this, "Jaringan Bermasalah Harap Periksa Jaringan Anda" , Toast.LENGTH_SHORT).show();
            }
        }){
        };
        RequestQueue requestQueue = Volley.newRequestQueue(Pollingwarga.this);
        requestQueue.add(stringRequest);

    }

    private void postdatamain(final String isi) {
        String Ur="http://gccestatemanagement.online/public/api/polling";
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, Ur,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        String resultResponse = new String(response.data);
                        try{
                            JSONObject result = new JSONObject(resultResponse);
                            String message = result.getString("message");
                            String success  = result.getString("success");

                            if (message.equals("Polling Created")) {
                                if (success.equals("true")) {
                                    Toast.makeText(getApplicationContext(),"Berhasil Membagikan Poling",Toast.LENGTH_LONG).show();
                                    finish();
                                }
                            }else {

                            }
                        } catch (JSONException e){
                            e.printStackTrace();
                            Toast.makeText(Pollingwarga.this, "Periksa Jaringan Anda" + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Pollingwarga.this, "Periksa Jaringan Anda" , Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                        params.put("id_rt",isi);
                        params.put("nama_polling",namapolling.getText().toString());
                        params.put("kategori_satu",progessone);
                        params.put("kategori_dua",progesstwo);
                        params.put("kategori_tiga",progressthree);
                        params.put("deskripsi",des.getText().toString());

                return params;
            }
            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                params.put("image",new DataPart("default", AppHelper.getFileDataFromDrawable(getBaseContext(), tambah.getDrawable()), "image/jpeg"));
                return params;
            }

        };
        VolleySingleton.getInstance(getBaseContext()).addToRequestQueue(volleyMultipartRequest);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data!=null){
        if(resultCode != RESULT_CANCELED){
            if (requestCode == 2) {
                Bitmap photo = (Bitmap) Objects.requireNonNull(data.getExtras()).get("data");
                if (photo!=null){
                ekoa=photo;
                tambah.setImageBitmap(ekoa);
                lala.setVisibility(View.GONE);
                }else {
                    lala.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(),"Batal Ambil Gambar dari Kamera",Toast.LENGTH_LONG).show();
                }

            }
            if (requestCode == 1)
            {
                Uri selectedImage = data.getData();
                if (selectedImage!=null){
                dwi=selectedImage;
                tambah.setImageURI(dwi);
                lala.setVisibility(View.GONE);
            }
                else {
                    lala.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(),"Batal Ambil Gambar dari Galerry",Toast.LENGTH_LONG).show();
                }
            }
        }
        }
        else {
            Toast.makeText(getApplicationContext(),"Batal Ambil Gambar ",Toast.LENGTH_LONG).show();
            lala.setVisibility(View.GONE);
        }
    }

}