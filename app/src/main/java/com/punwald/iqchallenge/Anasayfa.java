package com.punwald.iqchallenge;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class Anasayfa extends Activity implements View.OnClickListener{
    Button oynaBtn,ayarlarBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anasayfa);
        Intent i=getIntent();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
         /*Reklam*/
        MobileAds.initialize(this, "ca-app-pub-1592029610374280~2237318862");
        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        oynaBtn= (Button) findViewById(R.id.oyna);
        ayarlarBtn= (Button) findViewById(R.id.ayarlar);

        Animation animation= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.oynabutonanim);
        oynaBtn.startAnimation(animation);
        ayarlarBtn.startAnimation(animation);

        oynaBtn.setOnClickListener(this);
        ayarlarBtn.setOnClickListener(this);

    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK ) {
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        if (v.getId()==oynaBtn.getId()){
            startActivity(new Intent(this,Bolumler.class));
        }else if(v.getId()==ayarlarBtn.getId()){
            startActivity(new Intent(this,Ayarlar.class));
        }
    }
}
