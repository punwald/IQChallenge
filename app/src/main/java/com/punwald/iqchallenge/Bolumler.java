package com.punwald.iqchallenge;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class Bolumler extends Activity implements View.OnClickListener{
    Button bolum1,bolum2,bolum3;
    Intent a;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bolumler);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
         /*Reklam*/
        MobileAds.initialize(this, "ca-app-pub-1592029610374280~2237318862");
        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        bolum1= (Button) findViewById(R.id.bolum1);
        bolum2= (Button) findViewById(R.id.bolum2);
        bolum3= (Button) findViewById(R.id.bolum3);

        bolum1.setOnClickListener(this);
        bolum2.setOnClickListener(this);
        bolum3.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v.getId()==bolum1.getId()){
            a=new Intent(this,Oyun.class);
            a.putExtra("bolum","sifre");
            startActivity(a);
        }else if(v.getId()==bolum2.getId()){
            a=new Intent(this,Oyun.class);
            a.putExtra("bolum","tablo");
            startActivity(a);
        }else if(v.getId()==bolum3.getId()){
            a=new Intent(this,Oyun.class);
            a.putExtra("bolum","sekil");
            startActivity(a);
        }
    }
}
