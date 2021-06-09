package sistem.Smarta.grandcikarangcity2.rtpintar.warga.ButtonWartga;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.midtrans.sdk.corekit.callback.TransactionFinishedCallback;
import com.midtrans.sdk.corekit.core.MidtransSDK;
import com.midtrans.sdk.corekit.core.TransactionRequest;
import com.midtrans.sdk.corekit.core.UIKitCustomSetting;
import com.midtrans.sdk.corekit.core.themes.CustomColorTheme;
import com.midtrans.sdk.corekit.models.BankType;
import com.midtrans.sdk.corekit.models.CustomerDetails;
import com.midtrans.sdk.corekit.models.ItemDetails;
import com.midtrans.sdk.corekit.models.snap.CreditCard;
import com.midtrans.sdk.corekit.models.snap.TransactionResult;
import com.midtrans.sdk.uikit.SdkUIFlowBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import sistem.Smarta.grandcikarangcity2.Pembayaran;
import sistem.Smarta.grandcikarangcity2.R;
import sistem.Smarta.grandcikarangcity2.model.SdkConfig;

import static sistem.Smarta.grandcikarangcity2.BuildConfig.BASE_URL;
import static sistem.Smarta.grandcikarangcity2.BuildConfig.CLIENT_KEY;

public class Pembayarantagihan extends AppCompatActivity {
TextView one,two,four;
String on,eko,stat,rth;
int alfa;
Button mantapssale;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pembayaran);
        ImageButton imageButton = findViewById(R.id.backbuttwarga);
        one =findViewById(R.id.satu);
        two = findViewById(R.id.dua);
        four = findViewById(R.id.empat);
        mantapssale = findViewById(R.id.bayartagihan);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Intent i = getIntent();
       eko = i.getStringExtra("id");
        getdata(eko);
        mantapssale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (stat!=null){
                    Toast.makeText(getApplicationContext(),"Sudah Melakukan Transaksi \n Harap Selesaikan Transaksi anda \n atau Cek " +
                            "Status Transaksi Anda",Toast.LENGTH_LONG).show();
                }else {
                    initActionButtons();
                }

            }
        });
        initMidtransSdk();

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
                                    Toast.makeText(Pembayarantagihan.this, "Transaksi Sukses ", Toast.LENGTH_LONG).show();
                                    break;
                                case TransactionResult.STATUS_PENDING:
                                    stat="STATUS_PENDING";
                                    datamasuk(result.getResponse().getOrderId());
                                    Toast.makeText(Pembayarantagihan.this, "Transaksi Pending, Segera Selesaikan Pembayaran Anda" , Toast.LENGTH_LONG).show();
                                    break;
                                case TransactionResult.STATUS_FAILED:
                                    datamasuk(result.getResponse().getOrderId());
                                    stat="STATUS_GAGAL";
                                    Toast.makeText(Pembayarantagihan.this, "Transaksi Gagal", Toast.LENGTH_LONG).show();
                                    break;
                            }
                            result.getResponse().getValidationMessages();
                        }else if(result.isTransactionCanceled()){
                            Toast.makeText(Pembayarantagihan.this, "Transaction Failed", Toast.LENGTH_LONG).show();
                        }else{
                            if(result.getStatus().equalsIgnoreCase((TransactionResult.STATUS_INVALID))){
                                Toast.makeText(Pembayarantagihan.this, "Transaction Invalid" + result.getResponse().getTransactionId(), Toast.LENGTH_LONG).show();
                            }else{
                                Toast.makeText(Pembayarantagihan.this, "Something Wrong", Toast.LENGTH_LONG).show();
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
                                    four.setText(stat);
                                    updateiuran(eko);
                                }
                            }else {
                                Toast.makeText(Pembayarantagihan.this, message, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e){
                            e.printStackTrace();
                            Toast.makeText(Pembayarantagihan.this, "Jaringan Bermasalah Mohon Periksa Jaringan Ana", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Pembayarantagihan.this, "Periksa Jaringan Anda" , Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("idorder", orderId);
                params.put("nama_transaksi",on);
                params.put("nominal",String.valueOf(alfa));
                params.put("id_barang",eko);
                params.put("id_rt",rth);
                params.put("status",stat);
                params.put("level_id","1");
                return params;
            }};
        RequestQueue requestQueue = Volley.newRequestQueue(Pembayarantagihan.this);
        requestQueue.add(stringRequest);
    }

    private void updateiuran(String eko) {
        String e = "http://gccestatemanagement.online/public/api/iuranupdate/"+eko;
        StringRequest stringRequest= new StringRequest(Request.Method.PUT, e,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            String message = jsonObject.getString("message");
                            String success  = jsonObject.getString("success");
                            JSONObject data    = jsonObject.getJSONObject("data");

                            if (message.equals("Data berhasil diubah")) {
                                if (success.equals("true")) {
                                    four.setText(stat);
                                }
                            }else {
                                Toast.makeText(Pembayarantagihan.this, message, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e){
                            e.printStackTrace();
                            Toast.makeText(Pembayarantagihan.this, "Jaringan Bermasalah Mohon Periksa Jaringan Ana", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Pembayarantagihan.this, "Periksa Jaringan Anda" , Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("statu",stat);
                params.put("Content-Type","application/x-www-form-urlencoded");
                return params;
            }};
        RequestQueue requestQueue = Volley.newRequestQueue(Pembayarantagihan.this);
        requestQueue.add(stringRequest);

    }

    private void initActionButtons() {
        int i = 0;
        alfa=alfa+i;

        MidtransSDK.getInstance().setTransactionRequest(transactionRequest("101", alfa,on));
        MidtransSDK.getInstance().startPaymentUiFlow(Pembayarantagihan.this );

        UIKitCustomSetting uisetting = new UIKitCustomSetting();
        uisetting.setSkipCustomerDetailsPages(true);
        MidtransSDK.getInstance().setUIKitCustomSetting(uisetting);

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


    private void getdata(String eko) {
        final Dialog dialogok;
        dialogok= new Dialog(Pembayarantagihan.this);
        dialogok.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialogok.setContentView(R.layout.loading);
        dialogok.setCancelable(true);
        dialogok.setCanceledOnTouchOutside(false);
        dialogok.show();
        String UrlLogin="http://gccestatemanagement.online/public/api/wargaspes/"+eko;
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
                                if (message.equals("get data iuran")) {
                                    String id = data.getString("id");
                                    one.setText(data.getString("namaiuran"));
                                    two.setText("Rp. "+data.getString("nominaliuran"));
                                    four.setText(data.getString("statu"));
                                    alfa = Integer.parseInt(data.getString("nominaliuran"));
                                    on = data.getString("namaiuran");
                                    rth = data.getString("id_rt");
                                    dialogok.dismiss();
                                }
                            }else {
                                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e){
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Periksa Koneksi Anda" + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Jaringan Bermasalah Harap Periksa Jaringan Anda" , Toast.LENGTH_SHORT).show();
            }
        }){


        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }
}