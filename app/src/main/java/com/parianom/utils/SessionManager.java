package com.parianom.utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.parianom.R;
import com.parianom.activity.Chat;
import com.parianom.activity.MainActivity;
import com.parianom.activity.Masuk;

import java.util.HashMap;

public class SessionManager {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context context;
    int mode = 0;

    private static final String pref_name =  "crudprefUser";
    private static final String is_login = "isLogin";
    public static final  String kunci_id_user = "keyidUser";
    public SessionManager(Context context) {
        pref = context.getSharedPreferences(pref_name, mode);
        editor = pref.edit();
        this.context = context;
    }
    public void createSession(String id){
        editor.putBoolean(is_login,true);
        editor.putString(kunci_id_user,id);
        editor.commit();
    }

    public Integer checkLogin(){
        int a;
        if(!this.is_login()){
            a = 0;
        }else {
            a = 1;
        }
        return a;
    }
    private boolean is_login() {
        return pref.getBoolean(is_login, false);
    }
    public void logout(){
        editor.clear();
        editor.commit();
        Intent i = new Intent(context, MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }
    public HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<String, String>();
        user.put(pref_name, pref.getString(pref_name, null));
        user.put(kunci_id_user, pref.getString(kunci_id_user, null));
        return user;
    }
}
