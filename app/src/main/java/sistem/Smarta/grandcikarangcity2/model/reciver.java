package sistem.Smarta.grandcikarangcity2.model;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class reciver extends BroadcastReceiver {
List<String>dwi;
    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle extras =intent.getExtras();
        if(extras != null){
          dwi = extras.getStringArrayList("isi");
          for (int i = 0;i<dwi.size();i++){
              getdata(context,dwi.get(i));
          }
        }


    }

    private void getdata(final Context context, String s) {
        String Ur="http://gccestatemanagement.online/public/api/updatelaporsih/"+s;
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
                                    Toast.makeText(context, "sucssess", Toast.LENGTH_SHORT).show();
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
        })
        {

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
}
