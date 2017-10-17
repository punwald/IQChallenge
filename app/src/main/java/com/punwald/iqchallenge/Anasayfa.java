package com.punwald.iqchallenge;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class Anasayfa extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anasayfa);
        Intent i=getIntent();
         /*Reklam*/
        MobileAds.initialize(this, "ca-app-pub-1592029610374280~2237318862");
        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        Button oynaBtn= (Button) findViewById(R.id.oyna);
        Button ayarlarBtn= (Button) findViewById(R.id.ayarlar);

        Animation animation= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.oynabutonanim);
        oynaBtn.startAnimation(animation);
        ayarlarBtn.startAnimation(animation);

        oynaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Oyun.class));
            }
        });

        ayarlarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Ayarlar.class));
            }
        });

    }
}
