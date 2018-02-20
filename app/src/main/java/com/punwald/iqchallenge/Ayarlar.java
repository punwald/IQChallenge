package com.punwald.iqchallenge;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Ayarlar extends Activity implements View.OnClickListener{

    Button anasayfa,iletisim,bolumler;
    TextView punwald;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ayarlar);
        anasayfa = (Button) findViewById(R.id.anaSayfa);
        iletisim = (Button) findViewById(R.id.iletisim);
        bolumler = (Button) findViewById(R.id.bolumler);
        punwald = (TextView) findViewById(R.id.punwald);

        Typeface blow = Typeface.createFromAsset(getAssets(),"fonts/coolveticarg.ttf");
        punwald.setTypeface(blow);

        anasayfa.setOnClickListener(this);
        iletisim.setOnClickListener(this);
        bolumler.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.anaSayfa:
                finish();
                startActivity(new Intent(this,Anasayfa.class));
                break;
            case R.id.iletisim:
                String alici[]={"iletisim@punwald.com"};
                Intent a=new Intent(Intent.ACTION_SEND);
                a.putExtra(Intent.EXTRA_EMAIL,alici);
                a.putExtra(Intent.EXTRA_SUBJECT,"IQ Challenge");
                a.setType("plain/text");
                finish();
                startActivity(a);
                break;
            case R.id.bolumler:
                finish();
                startActivity(new Intent(this,Bolumler.class));
                break;
        }
    }
}
