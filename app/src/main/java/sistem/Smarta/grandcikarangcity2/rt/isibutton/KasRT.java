package sistem.Smarta.grandcikarangcity2.rt.isibutton;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.midtrans.sdk.corekit.callback.TransactionFinishedCallback;
import com.midtrans.sdk.corekit.core.MidtransSDK;
import com.midtrans.sdk.corekit.core.TransactionRequest;
import com.midtrans.sdk.corekit.core.UIKitCustomSetting;
import com.midtrans.sdk.corekit.core.themes.CustomColorTheme;
import com.midtrans.sdk.corekit.models.ItemDetails;
import com.midtrans.sdk.corekit.models.snap.CreditCard;
import com.midtrans.sdk.corekit.models.snap.TransactionResult;
import com.midtrans.sdk.uikit.SdkUIFlowBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import sistem.Smarta.grandcikarangcity2.R;
import sistem.Smarta.grandcikarangcity2.model.AdapterTransaksi;
import sistem.Smarta.grandcikarangcity2.model.Adapteriuran;
import sistem.Smarta.grandcikarangcity2.model.Transaksi;
import sistem.Smarta.grandcikarangcity2.model.iurannamadesa;
import sistem.Smarta.grandcikarangcity2.rtpintar.warga.ButtonWartga.Pembayarantagihan;

import static sistem.Smarta.grandcikarangcity2.BuildConfig.BASE_URL;
import static sistem.Smarta.grandcikarangcity2.BuildConfig.CLIENT_KEY;
import static sistem.Smarta.grandcikarangcity2.rtpintar.warga.ButtonWartga.Pembayarantagihan.transactionRequest;

public class KasRT extends AppCompatActivity {
    private Dialog customDialog;
    SharedPreferences sahres;
    String stat;
    String nominal,namakas;
    String idrt;
    TextView kosong,rp;
    LinearLayout ok;
    List<Integer>wee;
    RecyclerView rek;
    RecyclerView.Adapter as;
    private Handler handler;
    RecyclerView.LayoutManager layoutManager;
    private List<Transaksi> transaksis;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kasrt);
        ImageButton imageButton = findViewById(R.id.backbuttwarga);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ok = findViewById(R.id.ip);
        rek = findViewById(R.id.re);
        layoutManager = new LinearLayoutManager(this);
        Button tambah = findViewById(R.id.tambah);
        rp = findViewById(R.id.rp);
        kosong = findViewById(R.id.kosong);
        sahres = getSharedPreferences("blood", Context.MODE_PRIVATE);
        String isiid=sahres.getString("ide","empty");
        getdata(isiid);
        tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initViewComponents();
            }
        });
        initMidtransSdk();
        gettranssaksi();
        this.handler = new Handler();
        this.handler.postDelayed(m_Runnable,5000);
    }
    private final Runnable m_Runnable = new Runnable()
    {
        public void run()

        {
            gettranssaksi();

            handler.postDelayed(m_Runnable, 10000);
        }

    };

    private void getdata(String isiid) {
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
                                    idrt =isi;
                                    gettranssaksi();
                                }
                            }else {
                                Toast.makeText(KasRT.this, message, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e){
                            e.printStackTrace();
                            Toast.makeText(KasRT.this, "Periksa Koneksi Anda" + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(KasRT.this, "Jaringan Bermasalah Harap Periksa Jaringan Anda" , Toast.LENGTH_SHORT).show();
            }
        }){
        };
        RequestQueue requestQueue = Volley.newRequestQueue(KasRT.this);
        requestQueue.add(stringRequest);
    }

    private void initViewComponents() {
        customDialog = new Dialog(KasRT.this);
        customDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        customDialog.setContentView(R.layout.alertkastambah);
        customDialog.setCancelable(true);
        customDialog.setCanceledOnTouchOutside(false);
        customDialog.show();
        Button save = customDialog.findViewById(R.id.button4);
        final EditText name = customDialog.findViewById(R.id.name);
        final EditText ee = customDialog.findViewById(R.id.nominalkas);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nominal = ee.getText().toString();
                int dwi = Integer.parseInt(nominal);
                namakas = name.getText().toString();
                if (namakas.equals("")){
                    Toast.makeText(getApplicationContext(),"Nominal Tidak Boleh Kosong",Toast.LENGTH_LONG).show();
                }else  if (nominal.equals("")){
                    Toast.makeText(getApplicationContext(),"Nama Dana Tidak Boleh Kosong",Toast.LENGTH_LONG).show();
                }else {
                    if (nominal.equals(0)){
                        Toast.makeText(getApplicationContext(),"Nominal Tidak boleh kosong",Toast.LENGTH_LONG).show();
                    }else if (dwi<=4000){
                        Toast.makeText(getApplicationContext(),"Minimal Pendanaan Rp 50000 ",Toast.LENGTH_LONG).show();
                    }else {
                        savedata();
                    }

                }

                customDialog.dismiss();
            }
        });
    }

    private void savedata() {
        int i = 0;

        MidtransSDK.getInstance().setTransactionRequest(transactionRequest("201", Integer.parseInt(nominal),namakas));
        MidtransSDK.getInstance().startPaymentUiFlow(KasRT.this );

        UIKitCustomSetting uisetting = new UIKitCustomSetting();
        uisetting.setSkipCustomerDetailsPages(true);
        MidtransSDK.getInstance().setUIKitCustomSetting(uisetting);

    }
    private void initMidtransSdk() {
        SdkUIFlowBuilder.init()
                .setClientKey(CLIENT_KEY) // client_key is mandatory
                .setContext(this) // context is mandatory
                .setTransactionFinishedCallback(new TransactionFinishedCallback() {
                    @Override
                    public void onTransactionFinished(TransactionResult result) {
                        if(result.getResponse() != null){
                            switch (result.getStatus()){
                                case TransactionResult.STATUS_SUCCESS:
                                    stat="SUCCESS";
                                    datamasuk(result.getResponse().getOrderId());
                                    Toast.makeText(KasRT.this, "Transaksi Sukses ", Toast.LENGTH_LONG).show();
                                    break;
                                case TransactionResult.STATUS_PENDING:
                                    stat="PENDING";
                                    datamasuk(result.getResponse().getOrderId());
                                    Toast.makeText(KasRT.this, "Transaksi Pending, Segera Selesaikan Pembayaran Anda" , Toast.LENGTH_LONG).show();
                                    break;
                                case TransactionResult.STATUS_FAILED:
                                    datamasuk(result.getResponse().getOrderId());
                                    stat="GAGAL";
                                    Toast.makeText(KasRT.this, "Transaksi Gagal", Toast.LENGTH_LONG).show();
                                    break;
                            }
                            result.getResponse().getValidationMessages();
                        }else if(result.isTransactionCanceled()){
                            Toast.makeText(KasRT.this, "Transaksi Batal", Toast.LENGTH_LONG).show();
                        }else{
                            if(result.getStatus().equalsIgnoreCase((TransactionResult.STATUS_INVALID))){
                                Toast.makeText(KasRT.this, "Kesalahan Transaksi" + result.getResponse().getTransactionId(), Toast.LENGTH_LONG).show();
                            }else{
                                Toast.makeText(KasRT.this, "Periksa Jaringan Anda", Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                })
                .setMerchantBaseUrl(BASE_URL)
                .enableLog(true)
                .setColorTheme(new CustomColorTheme("#FFE51255", "#B61548", "#FFE51255"))
                .buildSDK();
    }

    private void datamasuk(final String orderId) {
        final String idbarang="TRN "+namakas+"TN"+nominal;
        String e = "http://gccestatemanagement.online/public/api/transaksi";
        StringRequest stringRequest= new StringRequest(Request.Method.POST, e,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            String message = jsonObject.getString("message");
                            String success  = jsonObject.getString("success");
                            JSONObject data    = jsonObject.getJSONObject("data");

                            if (message.equals("Post Created")) {
                                if (success.equals("true")) {
                                        gettranssaksi();
                                }
                            }else {
                                Toast.makeText(KasRT.this, message, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e){
                            e.printStackTrace();
                            Toast.makeText(KasRT.this, "Jaringan Bermasalah Mohon Periksa Jaringan Ana", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(KasRT.this, "Periksa Jaringan Anda" , Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("idorder", orderId);
                params.put("nama_transaksi",namakas);
                params.put("nominal",nominal);
                params.put("id_barang",idbarang);
                params.put("id_rt",idrt);
                params.put("status",stat);
                params.put("level_id","2");
                return params;
            }};
        RequestQueue requestQueue = Volley.newRequestQueue(KasRT.this);
        requestQueue.add(stringRequest);

    }

    private void gettranssaksi() {
        transaksis = new ArrayList<>();
        String UrlLogin="http://gccestatemanagement.online/public/api/transaksiss/"+idrt;
        StringRequest stringRequest= new StringRequest(Request.Method.GET, UrlLogin,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            String message = jsonObject.getString("message");
                            String status  = jsonObject.getString("success");
                          JSONArray data=jsonObject.getJSONArray("data");

                            if (status.equals("true")) {
                                if (message.equals("get_Pengajuan")) {
                                    int ek = data.length();
                                    if (ek==0){
                                        kosong.setVisibility(View.VISIBLE);
                                        ok.setVisibility(View.GONE);
                                    }else {
                                        kosong.setVisibility(View.GONE);
                                        ok.setVisibility(View.VISIBLE);
                                        for (int i=0;i<data.length();i++)
                                        {    Transaksi transaksiss= new Transaksi();
                                            JSONObject he = data.getJSONObject(i);
                                            transaksiss.setId(he.getString("id"));
                                            transaksiss.setIdorder(he.getString("idorder"));
                                            transaksiss.setNamatransaksi(he.getString("nama_transaksi"));
                                            transaksiss.setNominal(he.getString("nominal"));
                                            transaksiss.setStatus(he.getString("status"));
                                            transaksiss.setTanggal(he.getString("created_at"));
                                            transaksis.add(transaksiss);
                                        }
                                        rek.setLayoutManager(layoutManager);
                                        as= new AdapterTransaksi(KasRT.this,(ArrayList<Transaksi>)transaksis);
                                        rek.setAdapter(as);
                                        gettranssaksitotal();

                                    }
                                }
                            }else {
                                Toast.makeText(KasRT.this, message, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e){
                            e.printStackTrace();
                            Toast.makeText(KasRT.this, "Periksa Koneksi Anda" + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(KasRT.this, "Jaringan Bermasalah Harap Periksa Jaringan Anda" , Toast.LENGTH_SHORT).show();
            }
        }){
        };
        RequestQueue requestQueue = Volley.newRequestQueue(KasRT.this);
        requestQueue.add(stringRequest);

    }

    private void gettranssaksitotal() {
        wee = new ArrayList<>();
        String UrlLogin="http://gccestatemanagement.online/public/api/transaksiss2/"+idrt;
        StringRequest stringRequest= new StringRequest(Request.Method.GET, UrlLogin,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            String message = jsonObject.getString("message");
                            String status  = jsonObject.getString("success");
                            JSONArray data=jsonObject.getJSONArray("data");

                            if (status.equals("true")) {
                                if (message.equals("get_Pengajuan")) {
                                    int ek = data.length();
                                    if (ek==0){

                                    }else {
                                        kosong.setVisibility(View.GONE);
                                        ok.setVisibility(View.VISIBLE);
                                        for (int i=0;i<data.length();i++)
                                        {
                                            JSONObject he = data.getJSONObject(i);
                                            wee.add(Integer.valueOf(he.getString("nominal")));
                                        }
                                        int o= wee.size();
                                        if (o==0){
                                            rp.setText("Rp 0");
                                        }
                                        double sum = 0;
                                        for(int i = 0; i < wee.size(); i++){
                                            sum=sum+ wee.get(i);
                                        }
                                        Locale localeID = new Locale("in ", "ID");
                                        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
                                        rp.setText(formatRupiah.format((double)sum));

                                    }
                                }
                            }else {
                                Toast.makeText(KasRT.this, message, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e){
                            e.printStackTrace();
                            Toast.makeText(KasRT.this, "Periksa Koneksi Anda" + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(KasRT.this, "Jaringan Bermasalah Harap Periksa Jaringan Anda" , Toast.LENGTH_SHORT).show();
            }
        }){
        };
        RequestQueue requestQueue = Volley.newRequestQueue(KasRT.this);
        requestQueue.add(stringRequest);
    }

    public static TransactionRequest transactionRequest(String id, int amount, String on){
        String produk =on;
        TransactionRequest request =  new TransactionRequest(System.currentTimeMillis() + " " , amount );
        ItemDetails details = new ItemDetails(id, amount, 1, produk);

        ArrayList<ItemDetails> itemDetails = new ArrayList<>();
        itemDetails.add(details);
        request.setItemDetails(itemDetails);
        CreditCard creditCard = new CreditCard();
        creditCard.setSaveCard(false);
        details.getId();
        request.setCreditCard(creditCard);
        return request;
    }

}