package sistem.Smarta.grandcikarangcity2.rtpintar.warga.ButtonWartga.adpterbutton;

import android.Manifest;
import android.app.Activity;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sistem.Smarta.grandcikarangcity2.R;


public class Adapterhistory  extends RecyclerView.Adapter<Adapterhistory.MyViewHolder> {
    private List<datawarga> datahistoriesl;
    private Context context;
    public Adapterhistory(Context context, ArrayList<datawarga> datahistories) {
        this.datahistoriesl = datahistories;
        this.context = context;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.isihistory,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            datawarga ds= datahistoriesl.get(position);
            holder.satu.setText(ds.getNama_warga());
            holder.dua.setText(ds.getNamapengajuan());
            holder.tiga.setText(ds.getStatus());
            holder.id=ds.getId();
    }

    @Override
    public int getItemCount() {
        return datahistoriesl.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView satu,dua,tiga;
        String id;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            satu =itemView.findViewById(R.id.isi1);
            dua = itemView.findViewById(R.id.isi2);
            tiga = itemView.findViewById(R.id.isi3);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (tiga.getText().toString().equals("terkirim")){
                        getdataspesifik(id);
                    }else {

                    }
                }
            });
        }

    }
    private void getdataspesifik(final String id) {
        String UrlLogin="http://gccestatemanagement.online/public/api/suratspesifik/"+id;
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
                                if (message.equals("get_Pengajuan")) {
                                    String nik = data.getString("nik");
                                    String namapengajuan = data.getString("namapengajuan");
                                    String datepemakai = data.getString("tanggal_pakai");
                                    getktp(namapengajuan,nik,datepemakai,id);
                                }
                            }else {
                                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e){
                            e.printStackTrace();
                            Toast.makeText(context, "Periksa Koneksi Anda" + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Jaringan Bermasalah Harap Periksa Jaringan Anda" , Toast.LENGTH_SHORT).show();
            }
        }){
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);

    }
    private void getktp(final String namaaju , final String nik, final String date, final String id) {
        String UrlLogin="http://gccestatemanagement.online/public/api/wargaktp/"+nik;
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
                                if (message.equals("get data warga")) {
                                    String  nama =data.getString( "nama_lengkap");
                                    String Tempatlahir=data.getString("tempatlahir");
                                    String tgllahir =data.getString("tanggallahir");
                                    String nikah =data.getString("status_pernikahan");
                                    String jk =data.getString("jenis_kelamin");
                                    String kerja = data.getString("id_kerja");
                                    String kk = data.getString("nkk");
                                    String alama = data.getString("alamat");
                                    String agam    = data.getString("agama");
                                    String rt = data.getString("id_rt");
                                    getrt(namaaju,nama,Tempatlahir,tgllahir,nik,nikah,jk,kerja,kk,alama,agam,rt,date,id);
                                }
                            }else {
                                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e){
                            e.printStackTrace();
                            Toast.makeText(context, "Periksa Koneksi Anda" + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Jaringan Bermasalah Harap Periksa Jaringan Anda" , Toast.LENGTH_SHORT).show();
            }
        }){
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }

    private void getrt(final String namaju, final String nama, final String tempatlahir, final String tgllahir, final String nik, final String nikah, final String jk, final String kerja, final String kk, final String alama, final String agam, String rt, final String date,final String id) {
        String UrlLogin="http://gccestatemanagement.online/public/api/getrt2/"+rt;
        final ProgressDialog progressBar = new ProgressDialog(context);
        progressBar.setMessage("Prosess");
        progressBar.show();
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
                                    String name = data.getString("nama_lengkap");
                                    String desa = data.getString("desa").trim();
                                    String kelurahan = data.getString("kelurahan");
                                    String kecamatan = data.getString("kabupaten");
                                    String provinsi = data.getString("provinsi");
                                    String nort = data.getString("rt");
                                    String norw = data.getString("rw");
                                    ActivityCompat.requestPermissions((Activity) context,new String[]{
                                            Manifest.permission.WRITE_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);
                                    createpdf(namaju,nama,tempatlahir,tgllahir,nik,nikah,jk,kerja,kk,alama,agam,
                                            name,desa,kelurahan,kecamatan,provinsi,nort,norw,date);
                                    progressBar.dismiss();
                                    updatedata(id);
                                }
                            }else {
                                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException | ParseException e){
                            e.printStackTrace();
                            Toast.makeText(context, "Periksa Koneksi Anda" + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Jaringan Bermasalah Harap Periksa Jaringan Anda" , Toast.LENGTH_SHORT).show();
            }
        }){
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);

    }

    private void updatedata(String id) {
        String Ur="http://gccestatemanagement.online/public/api/update2/"+id;
        StringRequest volleyMultipartRequest = new StringRequest(Request.Method.PUT, Ur,
                new Response.Listener<String> () {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject result = new JSONObject(response);
                            String message = result.getString("message");
                            String success  = result.getString("success");
                            JSONObject data=result.getJSONObject("data");

                            if (message.equals("Data berhasil diubah")) {
                                if (success.equals("true")) {
                                    String isi = data.getString("id").trim();
                                    notifyDataSetChanged();
                                }
                            }else {
                                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e){
                            e.printStackTrace();
                            Toast.makeText(context, "Periksa Jaringan Anda" + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Mohon Periksa jaringan anda" , Toast.LENGTH_SHORT).show();
            }
        }){

            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("baca_status","keluar");
                params.put("Content-Type","application/x-www-form-urlencoded");
                return params;
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(volleyMultipartRequest);
    }

    private void createpdf(String namaju, String nama, String tempatlahir, String tgllahir, String nik, String nikah, String jk, String kerja, String kk, String alama, String agam, String name, String desa, String kelurahan, String kecamatan, String provinsi, String nort, String norw, String date) throws ParseException {
        int ss;
        PdfDocument pdfDocument  = new PdfDocument();
        Paint title = new Paint();
        Date format;
        Paint isi   = new Paint();
        String rtdesa;

        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(1200,2010,1).create();
        PdfDocument.Page  page = pdfDocument.startPage(pageInfo);
        Canvas canvas = page.getCanvas();
        Typeface plain = ResourcesCompat.getFont(context,R.font.hk);
        Typeface biasa = Typeface.create(plain,Typeface.NORMAL);
        Typeface bold = Typeface.create(plain,Typeface.BOLD);
        title.setTextAlign(Paint.Align.CENTER);
        title.setTextSize(48);
        title.setTypeface(bold);
        title.setColor(Color.BLACK);
        canvas.drawText("SURAT PENGANTAR / KETERANGAN",canvas.getWidth()/2,117,title);
        isi.setStyle(Paint.Style.STROKE);
        isi.setStrokeWidth(4);
        canvas.drawLine(68, 130,1200-68,135,isi);

        isi.setTextAlign(Paint.Align.LEFT);
        isi.setStyle(Paint.Style.FILL);
        isi.setTextSize(24);
        isi.setTypeface(biasa);
        canvas.drawText("Yang bertanda tangan di bawah ini Ketua RT "+nort+" RW "+norw+" ",70,168,isi);
        if (desa.equals("Tidak Ada Pedukuhan")){
            canvas.drawText("Kelurahan "+kelurahan+" dengan ini menerangkan bahwa:",70,260,isi);
        }else {
            rtdesa = desa;
            canvas.drawText("Desa "+rtdesa+" Kelurahan "+kelurahan+" dengan ini menerangkan bahwa:",70,198,isi);
        }
        canvas.drawText("Nama",70,282,isi);
        canvas.drawText(":",346,282,isi);
        canvas.drawText(nama,405,282,isi);
        canvas.drawText("Jenis Kelamin",70,330,isi);
        canvas.drawText(":",346,330,isi);
        canvas.drawText(jk,405,330,isi);
        canvas.drawText("Tempat/Tgl Lahir",70,382,isi);
        canvas.drawText(":",346,382,isi);
        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd");
        format=dateFormat.parse(tgllahir);
        DateFormat outputFormat = new SimpleDateFormat("dd-MMM-yyyy");
        String outputDateStr = outputFormat.format(format);
        canvas.drawText(tempatlahir+" / "+outputDateStr,405,382,isi);
        canvas.drawText("Agama",70,432,isi);
        canvas.drawText(":",346,432,isi);
        canvas.drawText(agam,405,432,isi);
        canvas.drawText("Pekarjaan",70,482,isi);
        canvas.drawText(":",346,482,isi);
        canvas.drawText(kerja,405,482,isi);
        canvas.drawText("Status Pernikahan",70,532,isi);
        canvas.drawText(":",346,532,isi);
        canvas.drawText(nikah,405,532,isi);
        canvas.drawText("No.KTP/No.KK",70,582,isi);
        canvas.drawText(":",346,582,isi);
        canvas.drawText(nik+" / "+kk,405,582,isi);
        canvas.drawText("Alamat",70,632,isi);
        canvas.drawText(":",346,632,isi);
        canvas.drawText(alama,405,632,isi);
        int i = 632;
        int y =632+50;
        int yy = y+30;
        int yo=yy+50;
        int yox= yo+30;
        canvas.drawText("Menerangkan bahwa orang tersebut benar sebagai warga kami yang beralamat / bertempat tinggal *)   "
                ,70,y,isi);
        canvas.drawText("di wilayah kami menurut catatan yang ada."
                ,70,yy,isi);
        canvas.drawText(  "Demikian surat Pengantar / keterangan *) ini di pergunakan untuk :",70,yo,isi);
        int panjang = namaju.length();
        String  firstFourChars = namaju.substring(9,panjang );
        format=dateFormat.parse(date);
        outputFormat = new SimpleDateFormat("dd MMMM yyyy");
        outputDateStr = outputFormat.format(format);
        Calendar cal= Calendar.getInstance();
        SimpleDateFormat month_date = new SimpleDateFormat("dd-MMMM-yyyy");
        String month_name = month_date.format(cal.getTime());
        canvas.drawText(firstFourChars +", Surat ini berlaku sampai dengan tanggal "+outputDateStr,67,yox,isi);
        isi.setTextAlign(Paint.Align.CENTER);
        canvas.drawText(  kecamatan+","+month_name,1200-250,yox+100,isi);
        canvas.drawText(  "Ketua RT "+nort,1200-250,yox+150,isi);
        canvas.drawText(  "( "+name+" )",1200-250,yox+300,isi);
        isi.setTextAlign(Paint.Align.LEFT);
        canvas.drawText(  "Mengetahui ",70,yox+100,isi);
        canvas.drawText(  "Catatan ..*) Coret Yang Tidak Perlu.",70,1910,isi);
        canvas.drawText("Silahkan Datang Ketempat Ketua RT guna mendapatakan Cap RT dan Tanda Tangan.",70,1910+30,isi);


        pdfDocument.finishPage(page);
        File myFile = new File(Environment.getExternalStorageDirectory(), "Download/"+namaju+""+nama+".pdf");
        try {
            pdfDocument.writeTo(new FileOutputStream(myFile));
            Toast.makeText(context, "Surat Pengajuan/Keterangan berhasil Tersimpan ", Toast.LENGTH_LONG).show();
            ss =1;
        }
        catch (Exception e){
            e.printStackTrace();
            ss =2;
            Toast.makeText(context, "PDF Gagal", Toast.LENGTH_LONG).show();
        }
        pdfDocument.close();
        if (ss == 1){
            getpdf();
        }
    }
    private void getpdf() {
        context.startActivity(new Intent(DownloadManager.ACTION_VIEW_DOWNLOADS));
    }

}
