package com.parianom.utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.parianom.activity.Board;
import com.parianom.activity.MainActivity;

import java.util.HashMap;

public class SessionManagerSplash {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context context;
    int mode = 0;

    private static final String pref_name =  "crudpref";
    private static final String is_login = "isLogin";
    public static final  String kunci_id = "keyid";
    public SessionManagerSplash(Context context) {
        pref = context.getSharedPreferences(pref_name, mode);
        editor = pref.edit();
        this.context = context;
    }
    public void createSession(String id){
        editor.putBoolean(is_login,true);
        editor.putString(kunci_id,id);
        editor.commit();
    }
    public void checkLogin(){
        if(!this.is_login()){
            Intent i = new Intent(context, Board.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
        }else {
            Intent i = new Intent(context, MainActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
        }
    }
    private boolean is_login() {
        return pref.getBoolean(is_login, false);
    }
}
