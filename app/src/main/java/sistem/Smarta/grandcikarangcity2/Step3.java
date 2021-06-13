package sistem.Smarta.grandcikarangcity2;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


import sistem.Smarta.grandcikarangcity2.model.AppHelper;
import sistem.Smarta.grandcikarangcity2.model.VolleyMultipartRequest;
import sistem.Smarta.grandcikarangcity2.model.VolleySingleton;
import sistem.Smarta.grandcikarangcity2.rt.Login;

import static android.app.Activity.RESULT_CANCELED;

public class Step3 extends Fragment implements Step, BlockingStep {
    String user;
    int j = 0;
    Bitmap ekoa,dwi;
    private int internal= 1;
    private int STORAGE_PERMISSION_CODE = 1;
    TextView des,fas,lok,blo,per,nona;
    private int camera = 1;
    String maslaah,falislitas,lokasi, perum, blok,norumah;
    ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
    ArrayList<Bitmap>imagearray;
        ArrayList<Bitmap>image;
        RecyclerView mrecycleview;
        RecyclerView.LayoutManager mLayout;
        RecyclerView.Adapter adapter;
        int i = 0;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.step3, container, false);
        chek();
        imagearray = new ArrayList<Bitmap>();
        ImageButton tambah = v.findViewById(R.id.imageButton2);
       mrecycleview = v.findViewById(R.id.getimage);
        image = new ArrayList<>();
        des =v.findViewById(R.id.detail);
        fas =v.findViewById(R.id.fasil);
        lok =v.findViewById(R.id.lokasi);
        blo =v.findViewById(R.id.bli);
        per =v.findViewById(R.id.peru);
        nona=v.findViewById(R.id.nomer);
        lok.setText(lokasi);
        des.setText(maslaah);
        fas.setText(falislitas);
        blo.setText(blok);
        per.setText(perum);
        nona.setText(norumah);


        tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getcamera();
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
                        Intent intent = new   Intent(Intent.ACTION_PICK,
                                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(intent , 1);


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

    private void chek() {
        SharedPreferences preferences = this.getActivity().getSharedPreferences("blood", Context.MODE_PRIVATE);
        SharedPreferences maslaha = this.getActivity().getSharedPreferences("laporan", Context.MODE_PRIVATE);
        user = preferences.getString("ide","Empty");
        maslaah= maslaha.getString("deskripsi","Empty");
        falislitas= maslaha.getString("fasilitas","Empty");
        perum =maslaha.getString("perumaha","Empty");
        blok =maslaha.getString("blok","Empty");
        norumah =maslaha.getString("norumah","Empty");
        lokasi =maslaha.getString("lokasi","empty");
        if (lokasi=="empty"){
            reload();
        }
    }

    private void reload() {
        SharedPreferences isi = this.getActivity().getSharedPreferences("isi", Context.MODE_PRIVATE);
        lokasi =isi.getString("lokasi","empty");
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode != RESULT_CANCELED){
            if (requestCode == 2) {
                Bitmap photo = (Bitmap) Objects.requireNonNull(data.getExtras()).get("data");
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
            if (requestCode == 1)
            { InputStream is = null;
                Uri selectedImage = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(requireContext().getContentResolver(), selectedImage);
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

            }
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == STORAGE_PERMISSION_CODE && requestCode == camera) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getdata();
            } else {
                new AlertDialog.Builder(requireContext())
                        .setTitle("Warning Permission Needed")
                        .setMessage("Membutuhkan perizinan aplikasi")
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ActivityCompat.requestPermissions(requireActivity(),
                                        new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, internal);
                                ActivityCompat.requestPermissions(requireActivity(),
                                        new String[] {Manifest.permission.CAMERA}, camera);
                                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                Uri uri = Uri.fromParts("package",getActivity().getPackageName(),null);
                                intent.setData(uri);
                                startActivity(intent);
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .create().show();
            }
        } else {

        }
    }

    private void getdata() {

    }

    @Nullable
    @Override
    public VerificationError verifyStep() {
        return null;
    }

    @Override
    public void onSelected() {
        chek();

    }

    @Override
    public void onError(@NonNull VerificationError error) {

    }

    @Override
    public void onNextClicked(StepperLayout.OnNextClickedCallback callback) {

    }

    @Override
    public void onCompleteClicked(final StepperLayout.OnCompleteClickedCallback callback) {
       if (imagearray.isEmpty()){
           Toast.makeText(requireContext(),"Harap Masukan Gambar",Toast.LENGTH_LONG).show();
       }else {
           senddata();

           final BottomSheetDialog dialog = new BottomSheetDialog(requireContext(), R.style.CustomBottomSheetDialogTheme);
           dialog.setContentView(R.layout.bottomsheetdua);
           Button selesai = dialog.findViewById(R.id.selesai);
           dialog.setCanceledOnTouchOutside(false);
           Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
           dialog.show();
           assert selesai != null;
           selesai.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   callback.complete();
                   requireActivity().finish();
                   dialog.dismiss();
               }
           });
       }
    }

    private void senddata() {
        SharedPreferences dapat = getActivity().getSharedPreferences("laporan", Context.MODE_PRIVATE);
        final String lat,lang;
        lat=dapat.getString("lat","Empty");
        lang = dapat.getString("long","Empty");
        String UrlLogin="http://gccestatemanagement.online/public/api/laporankerusakan";
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
                                    String id =data.getString("id").trim();
                                    for (int i = 0;i< imagearray.size();i++){
                                        Bitmap as = imagearray.get(i);
                                        Log.d("isi",as.toString());
                                        uploadimage(id,as);
                                    }
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
                params.put("nama_masalah",des.getText().toString());
                params.put("jenis_fasilitas",fas.getText().toString());
                params.put("lokasi",lok.getText().toString());
                params.put("noblok",blo.getText().toString());
                params.put("norumah",nona.getText().toString());
                params.put("id_user",user);
                params.put("longitude",lang);
                params.put("latitude",lat);
                return params;
            }};
        RequestQueue requestQueue = Volley.newRequestQueue(requireContext());
        requestQueue.add(stringRequest);
    }

    private void uploadimage(final String id, Bitmap as) {
        final Drawable d = new BitmapDrawable(getResources(), as);
        final ProgressDialog progressBar = new ProgressDialog(requireContext());
        progressBar.setMessage("Prosess");
        progressBar.show();
        String UrlLogin="http://gccestatemanagement.online/public/api/rusak";
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

    private void getcamera() {
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA)== PackageManager.PERMISSION_GRANTED){
                getpermissionstorage();
            }
            else {
                requestcamera();
            }

        }else {
            getpermissionstorage();
        }
    }
    private void requestcamera() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(),
                Manifest.permission.CAMERA)) {
            new AlertDialog.Builder(requireContext())
                    .setTitle("Permission needed")
                    .setMessage("Aplikasi Membutuhkan perizinan Camera")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            getpermissionstorage();
                            ActivityCompat.requestPermissions(requireActivity(),
                                    new String[] {Manifest.permission.CAMERA}, camera);
                        }
                    })
                    .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create().show();
        } else {
            ActivityCompat.requestPermissions(requireActivity(),
                    new String[] {Manifest.permission.CAMERA}, camera);
        }
    }
    private void getpermissionstorage() {
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            if (ContextCompat.checkSelfPermission(requireContext(),Manifest.permission.READ_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED){
                getinternalstorage();
            }
            else {
                requestExternal();
            }
        }else {
            getinternalstorage();
        }
    }
    private void requestExternal() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(),
                Manifest.permission.READ_EXTERNAL_STORAGE)) {
            new AlertDialog.Builder(requireContext())
                    .setTitle("Permission needed")
                    .setMessage("Aplikasi Membutuhkan perizinan Galerry")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            getinternalstorage();
                            ActivityCompat.requestPermissions(requireActivity(),
                                    new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
                        }
                    })
                    .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create().show();
        } else {
            ActivityCompat.requestPermissions(requireActivity(),
                    new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
        }
    }
    private void getinternalstorage() {
        if (ContextCompat.checkSelfPermission(requireContext(),Manifest.permission.WRITE_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED){
            getdata();
        }
        else {
            requestinternal();
        }

    }


    private void requestinternal() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            new AlertDialog.Builder(requireContext())
                    .setTitle("Permission needed")
                    .setMessage("Aplikasi Membutuhkan perizinan internal")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(requireActivity(),
                                    new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, internal);
                            dialog.dismiss();
                        }
                    })
                    .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create().show();
        } else {
            ActivityCompat.requestPermissions(requireActivity(),
                    new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, internal);
        }
    }
}
