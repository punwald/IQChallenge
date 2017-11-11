package com.punwald.iqchallenge;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class Ayarlar extends AppCompatActivity {

    Button iletisimB;
    SeekBar sesB;
    AudioManager sesYöneticisi;
    TextView sesText,punwaldText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ayarlar);
        Intent intent=getIntent();
         /*Reklam*/
        MobileAds.initialize(this, "ca-app-pub-1592029610374280~2237318862");
        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        sesB= (SeekBar) findViewById(R.id.ses);
        iletisimB= (Button) findViewById(R.id.iletisim);
        sesText= (TextView) findViewById(R.id.sesText);
        punwaldText= (TextView) findViewById(R.id.textView);
        punwaldText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "http://www.punwald.com";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        TextView ıq= (TextView) findViewById(R.id.ıq);
        Typeface tp=Typeface.createFromAsset(getAssets(),"fonts/blowbrush.otf");
        ıq.setTypeface(tp);

        iletisimB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] alici={"punwald@gmail.com"};
                Intent email=new Intent(Intent.ACTION_SEND);
                email.setData(Uri.parse("mailto:"));
                email.setType("text/plain");
                email.putExtra(Intent.EXTRA_EMAIL,alici);

                try {
                    startActivity(Intent.createChooser(email,"E-mail Uygulaması Seçiniz!"));
                    finish();
                }catch (ActivityNotFoundException e){
                    Toast.makeText(getApplicationContext(),"E-mail gönderecek uygulama bulunamadı!",Toast.LENGTH_LONG).show();
                }

            }
        });

        sesYöneticisi= (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        sesAyarla(sesB,AudioManager.STREAM_MUSIC);
    }

    private void sesAyarla(SeekBar bar,final int stream){

        bar.setMax(sesYöneticisi.getStreamMaxVolume(stream));
        bar.setProgress(sesYöneticisi.getStreamVolume(stream));

        bar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                sesYöneticisi.setStreamVolume(stream,progress,R.raw.backsound);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


    }
}

