package sistem.Smarta.grandcikarangcity2.rtpintar.warga.ButtonWartga.stepper;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.stepstone.stepper.BlockingStep;
import com.stepstone.stepper.Step;
import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import sistem.Smarta.grandcikarangcity2.Adapter;
import sistem.Smarta.grandcikarangcity2.R;
import sistem.Smarta.grandcikarangcity2.model.AppHelper;
import sistem.Smarta.grandcikarangcity2.model.VolleyMultipartRequest;
import sistem.Smarta.grandcikarangcity2.model.VolleySingleton;
import sistem.Smarta.grandcikarangcity2.model.laporanwarga;

import static android.app.Activity.RESULT_CANCELED;


public class fragmenttwo extends Fragment implements Step, BlockingStep {
    String user,rt,nik;
    int j = 0;
    Bitmap ekoa,dwi;
   TextView des,nama,lokasi;
    String judu,deskrip,lokal,longitu,latitu,address;
    ArrayList<Bitmap>imagearray;
    SharedPreferences maslaha;
    ArrayList<Bitmap>image;
    SwipeRefreshLayout one;
    RecyclerView mrecycleview;
    RecyclerView.LayoutManager mLayout;
    RecyclerView.Adapter adapter;
    ProgressDialog progressBar;
    int i = 0;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragmentttwolaporanwarga, container, false);
        maslaha = getActivity().getSharedPreferences("laporanwarga", Context.MODE_PRIVATE);
        imagearray = new ArrayList<Bitmap>();
    progressBar = new ProgressDialog(requireContext());
        ImageButton tambah = v.findViewById(R.id.imageButton2);
        mrecycleview = v.findViewById(R.id.getimage);
        image = new ArrayList<>();
        one = v.findViewById(R.id.reload);
        des = v.findViewById(R.id.detail);
        nama = v.findViewById(R.id.fasil);
        lokasi =v.findViewById(R.id.peru);
        chek();
        onResume();
        tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final BottomSheetDialog dialog = new BottomSheetDialog(requireContext(), R.style.CustomBottomSheetDialogTheme);
                dialog.setContentView(R.layout.bottomsheetone);
                final LinearLayout foto = dialog.findViewById(R.id.kamera);
                final  TextView back   = dialog.findViewById(R.id.cancel);
                final LinearLayout galeri = dialog.findViewById(R.id.ambilhp);
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
        return v;
    }
    public void onResume() {
        super.onResume();
        chek();
    }
    private void chek() {
        SharedPreferences preferences = this.getActivity().getSharedPreferences("loss", Context.MODE_PRIVATE);
        SharedPreferences preference = this.getActivity().getSharedPreferences("Kepala", Context.MODE_PRIVATE);
        judu =maslaha.getString("nama_info",null);
        deskrip=maslaha.getString("detail",null);
        lokal=maslaha.getString("lokasiok",null);
        address=maslaha.getString("lokasi",null);
        longitu = maslaha.getString("longitude", null);
        latitu = maslaha.getString("latitude",null);
        user = preferences.getString("fulluname","Empty");
        rt   = preference.getString("id_rt",null);
        nik  =preference.getString("nik",null);
        des.setText(judu);
        nama.setText(deskrip);
        lokasi.setText(lokal);
    }

    @Override
    public void onNextClicked(StepperLayout.OnNextClickedCallback callback) {

    }

    @Override
    public void onCompleteClicked(final StepperLayout.OnCompleteClickedCallback callback) {
        if (imagearray.isEmpty()){
            Toast.makeText(requireContext(),"Harap Masukan Gambar",Toast.LENGTH_LONG).show();
        }else {
            senddata(callback);
        }
    }

    private void senddata(final StepperLayout.OnCompleteClickedCallback callback) {
        String UrlLogin="http://gccestatemanagement.online/public/api/laporwe";
        StringRequest stringRequest= new StringRequest(Request.Method.POST, UrlLogin,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            String message = jsonObject.getString("message");
                            String status  = jsonObject.getString("success");
                            JSONObject data    = jsonObject.getJSONObject("data");

                            if (message.equals("Post Created")) {
                                if (status.equals("true")) {
                                    int j = 0;
                                    String id =data.getString("id").trim();
                                    for (int i = 0;i< imagearray.size();i++){
                                        Bitmap as = imagearray.get(i);
                                        uploadimage(id,as,callback);
                                        j=j+i;
                                    }
                                    close(callback);

                                }
                            }else {
                                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e){
                            e.printStackTrace();
                            Toast.makeText(requireContext(), "Laporan Gagal Terkirim " + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(requireContext(), "Periksa Jaringan Anda" , Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("nik",nik);
                params.put("id_rt",rt);
                params.put("nama_pelapor",user);
                params.put("namalaporan",judu);
                params.put("longituded",longitu);
                params.put("latituded",latitu);
                params.put("lokasiinputuser",lokal);
                params.put("deskripsi",deskrip);
                params.put("gps",address);
                return params;
            }};
        RequestQueue requestQueue = Volley.newRequestQueue(requireContext());
        requestQueue.add(stringRequest);


    }

    private void close(final StepperLayout.OnCompleteClickedCallback callback) {
        final BottomSheetDialog dialog = new BottomSheetDialog(requireContext(), R.style.CustomBottomSheetDialogTheme);
        dialog.setContentView(R.layout.bottomsheetdua);
        Button selesai = dialog.findViewById(R.id.selesai);
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        assert selesai != null;
        selesai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor= maslaha.edit();
                editor.clear();
                editor.commit();
                editor.apply();
                callback.complete();
                dialog.dismiss();
                getActivity().finish();
            }
        });
    }

    private void uploadimage(final String id, Bitmap as, final StepperLayout.OnCompleteClickedCallback callback) {
        final Drawable d = new BitmapDrawable(getResources(), as);
        progressBar.setMessage("Prosess");
        progressBar.show();
        String UrlLogin="http://gccestatemanagement.online/public/api/imagewarga";
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, UrlLogin,

                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        String resultResponse = new String(response.data);
                        try{
                            JSONObject result = new JSONObject(resultResponse);
                            String message = result.getString("message");
                            String success  = result.getString("success");
                            if (message.equals("upload Laporan")) {
                                if (success.equals("true")) {
                                    progressBar.dismiss();
                                    j= imagearray.size();
                                    callback.complete();
                                }
                            }else {
                                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e){
                            e.printStackTrace();
                            Toast.makeText(requireContext(), "Periksa Jaringan Anda" + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(requireContext(), "Username Atau Password Salah" , Toast.LENGTH_SHORT).show();
            }
        }){

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("id_laporan",id);
                return params;
            }


            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                params.put("image",new DataPart("default", AppHelper.getFileDataFromDrawable(getActivity().getBaseContext(),d), "image/jpeg"));
                return params;
            }

        };
        VolleySingleton.getInstance(getActivity().getBaseContext()).addToRequestQueue(volleyMultipartRequest);

    }

    @Override
    public void onBackClicked(StepperLayout.OnBackClickedCallback callback) {
        callback.goToPrevStep();
    }


    @Override
    public VerificationError verifyStep() {
        return null;
    }

    @Override
    public void onSelected() {
        chek();

    }

    @Override
    public void onError( VerificationError error) {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode != RESULT_CANCELED){
            if (data!=null){
            if (requestCode == 2) {
                Bitmap photo = (Bitmap) Objects.requireNonNull(data.getExtras()).get("data");
                if (photo!=null){
                ekoa=photo;
                image.add(ekoa);
                imagearray.add(ekoa);
                mrecycleview.setVisibility(View.VISIBLE );
                mrecycleview.setHasFixedSize(true);
                mLayout = new LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false);
                adapter= new Adapter(image);
                mrecycleview.setLayoutManager(mLayout);
                mrecycleview.setAdapter(adapter);
            }
                else {
                    Toast.makeText(requireContext(),"Batal Ambil Gambar dari Kamera",Toast.LENGTH_LONG).show();
            }
            }
            else if (requestCode == 1)
            {
                Uri selectedImage = data.getData();
                if (selectedImage!=null){
                try {
                    InputStream inputStream = getActivity().getContentResolver().openInputStream(selectedImage);
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    dwi=bitmap;
                    image.add(dwi);
                    imagearray.add(dwi);
                    mLayout = new LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false);
                    mrecycleview.setVisibility(View.VISIBLE );
                    mrecycleview.setHasFixedSize(true);
                    adapter= new Adapter(image);
                    mrecycleview.setLayoutManager(mLayout);
                    mrecycleview.setAdapter(adapter);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }else {
                    Toast.makeText(requireContext(),"Batal Ambil Gambar dari Kamera",Toast.LENGTH_LONG).show();
                }


            }
        }
        }
    }
}
