package sistem.Smarta.grandcikarangcity2.model;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.HashMap;

import sistem.Smarta.grandcikarangcity2.MainActivity;
import sistem.Smarta.grandcikarangcity2.rt.Login;

public class SessionManager {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    public Context context;

    private static final String PREF_NAME = "LOGIN";
    private static final String LOGIN = "IS_LOGIN";
    public static final String ID = "ID";

    @SuppressLint("CommitPrefEdits")
    public SessionManager(Context context) {
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
            Intent i = new Intent(context, Login.class);
            context.startActivity(i);
            ((MainActivity) context).finish();
        }

    }

    public HashMap<String, String> getUserDetail(){
        HashMap<String, String> user = new HashMap<>();
        user.put(ID, sharedPreferences.getString(ID, null));
        return user;
    }

    public void logout(){
       SharedPreferences sahre = context.getSharedPreferences("share", Context.MODE_PRIVATE);
        SharedPreferences blodd = context.getSharedPreferences("blood",Context.MODE_PRIVATE);
        SharedPreferences.Editor oneshare = sahre.edit();
        SharedPreferences.Editor bloodshare=blodd.edit();
        oneshare.clear();
        oneshare.commit();
        oneshare.apply();
        bloodshare.clear();
        bloodshare.commit();
        bloodshare.apply();
        editor.clear();
        editor.commit();
        editor.apply();
        Intent i = new Intent(context, Login.class);
        context.startActivity(i);
        ((MainActivity) context).finish();
    }

}


