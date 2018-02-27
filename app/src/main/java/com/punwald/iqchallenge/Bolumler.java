package com.punwald.iqchallenge;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class Bolumler extends Activity implements View.OnClickListener{
    Button bolum1,bolum2,bolum3;
    Intent a;
    SharedPreferences shared;
    SharedPreferences.Editor editor;
    boolean sifreBitti,tabloBitti,sekilBitti;
    String bolum=" ";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bolumler);
        a=getIntent();
        if (a.hasExtra("bolum")) {
            bolum = (String) a.getExtras().get("bolum");
        }

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
         /*Reklam*/
        MobileAds.initialize(this, "ca-app-pub-1592029610374280~2237318862");
        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        bolum1= (Button) findViewById(R.id.bolum1);
        bolum2= (Button) findViewById(R.id.bolum2);
        bolum3= (Button) findViewById(R.id.bolum3);
        shared=getPreferences(Context.MODE_PRIVATE);
        editor=shared.edit();


        if (bolum.equals(" ")){

        }else if (bolum.equals("sifre")) {

            editor.putBoolean("sifreBitti", true);
            editor.commit();

        } else if (bolum.equals("tablo")) {

            editor.putBoolean("tabloBitti", true);
            editor.commit();

        } else if (bolum.equals("sekil")) {

            editor.putBoolean("sekilBitti", true);
            editor.commit();

        }


        sifreBitti=shared.getBoolean("sifreBitti",false);
        tabloBitti=shared.getBoolean("tabloBitti",false);
        sekilBitti=shared.getBoolean("sekilBitti",false);

        if(!sifreBitti){
            bolum1.setBackgroundResource(R.drawable.btnsifre);
        }else {
            bolum1.setBackgroundResource(R.drawable.btnsifretm);
        }
        if(!tabloBitti){
            bolum2.setBackgroundResource(R.drawable.btntablo);
        }else {
            bolum2.setBackgroundResource(R.drawable.btntablotm);
        }
        if(!sekilBitti){
            bolum3.setBackgroundResource(R.drawable.btnsekil);
        }else {
            bolum3.setBackgroundResource(R.drawable.btnsekiltm);
        }


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
