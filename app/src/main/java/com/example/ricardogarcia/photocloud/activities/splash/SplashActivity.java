package com.example.ricardogarcia.photocloud.activities.splash;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.ricardogarcia.photocloud.R;
import com.example.ricardogarcia.photocloud.activities.login.LoginActivity;

public class SplashActivity extends AppCompatActivity {

    private int REQUEST_PERMISSIONS=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA},REQUEST_PERMISSIONS);
        }else{
            goLogin();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==REQUEST_PERMISSIONS){
            if (grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                goLogin();
            }else{
                Toast.makeText(this, getString(R.string.not_granted_permissions), Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    public void goLogin(){
        Intent intent=new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
