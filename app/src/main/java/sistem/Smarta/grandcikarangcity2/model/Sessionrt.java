package sistem.Smarta.grandcikarangcity2.model;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.HashMap;

import sistem.Smarta.grandcikarangcity2.MainActivity;
import sistem.Smarta.grandcikarangcity2.rt.Login;
import sistem.Smarta.grandcikarangcity2.rt.MainHomeRt;
import sistem.Smarta.grandcikarangcity2.rtpintar.Registerrtpintar_RT;
import sistem.Smarta.grandcikarangcity2.rtpintar.Rtpintarklik;

public class Sessionrt {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    public Context context;

    private static final String PREF_NAME = "Warga";
    private static final String LOGIN = "IS_LOGIN";
    public static final String ID = "ID";

    @SuppressLint("CommitPrefEdits")
    public Sessionrt(Context context) {
        this.context = context;
        int PRIVATE_MODE = 0;
        sharedPreferences = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = sharedPreferences.edit();
    }

    public void createSession(String id){
        editor.putBoolean(LOGIN, true);
        editor.putString(ID, id);
        editor.apply();
    }

    private boolean isLoggin(){
        return sharedPreferences.getBoolean(LOGIN, false);
    }

    public void checkLogin(){
        if (!this.isLoggin()){
            Intent i = new Intent(context, Registerrtpintar_RT.class);
            context.startActivity(i);
            ((MainHomeRt) context).finish();
        }

    }

    public HashMap<String, String> getUserDetail(){
        HashMap<String, String> user = new HashMap<>();
        user.put(ID, sharedPreferences.getString(ID, null));
        return user;
    }

    public void logout(){
        editor.clear();
        editor.commit();
        Intent i = new Intent(context,Registerrtpintar_RT.class);
        context.startActivity(i);
        ((MainHomeRt) context).finish();
    }

}



