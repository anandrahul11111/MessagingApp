package com.example.dx.messageapp;

import android.app.Service;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by DX on 04-Feb-18.
 */

public class Connection {
    Context context;

    public Connection(Context context) {
        this.context = context;
    }
    public boolean isConnected(){
        ConnectivityManager connectivityManager=(ConnectivityManager)context.getSystemService(Service.CONNECTIVITY_SERVICE);
        if(connectivityManager !=null){
            NetworkInfo info=connectivityManager.getActiveNetworkInfo();
            if (info !=null){
                if (info.getState()== NetworkInfo.State.CONNECTED){
                    return true;
                }
            }
        }
        return false;
    }
}
