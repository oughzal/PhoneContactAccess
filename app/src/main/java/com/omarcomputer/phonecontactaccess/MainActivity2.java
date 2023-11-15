package com.omarcomputer.phonecontactaccess;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import com.omarcomputer.phonecontactaccess.databinding.ActivityMain2Binding;
import com.omarcomputer.phonecontactaccess.databinding.ActivityMainBinding;

public class MainActivity2 extends AppCompatActivity {
    ActivityMain2Binding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMain2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.btnGetState.setOnClickListener(v -> {
            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnectedOrConnecting()) {
                //TODO : exécuter une tâche qui necéssaite Internet
                binding.txtConneted.setText("Connected : Yes");
            } else {
                binding.txtConneted.setText("Connected : No");
            }
            if (networkInfo != null && networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                binding.txtConnetedWifi.setText("Connected : Yes");
            } else {
                binding.txtConnetedWifi.setText("Connected : No");
            }
            if (networkInfo != null && networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                binding.txtConneted4G.setText("Connected : Yes");
            } else {
                binding.txtConneted4G.setText("Connected : No");
            }
        });
    }
}