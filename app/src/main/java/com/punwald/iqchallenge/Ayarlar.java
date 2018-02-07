package com.punwald.iqchallenge;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class Ayarlar extends Activity implements View.OnClickListener{

    Button anasayfa,iletisim,bolumler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ayarlar);
        anasayfa= (Button) findViewById(R.id.anaSayfa);
        iletisim= (Button) findViewById(R.id.iletisim);
        bolumler= (Button) findViewById(R.id.bolumler);

        anasayfa.setOnClickListener(this);
        iletisim.setOnClickListener(this);
        bolumler.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){


            case R.id.anaSayfa:
                startActivity(new Intent(this,Anasayfa.class));
                break;

            case R.id.iletisim:
                String alici[]={"punwald@gmail.com"};
                Intent a=new Intent(Intent.ACTION_SEND);
                a.putExtra(Intent.EXTRA_EMAIL,alici);
                a.putExtra(Intent.EXTRA_SUBJECT,"IQ Challenge");
                a.setType("plain/text");
                startActivity(a);
                break;

            case R.id.bolumler:
                startActivity(new Intent(this,Bolumler.class));
                break;

        }
    }
}
