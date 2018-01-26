package com.punwald.iqchallenge;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

public class Bolumler extends Activity implements View.OnClickListener{
    Button bolum1,bolum2,bolum3;
    Intent a;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bolumler);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
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
            a=new Intent(this,ilkBolumOyun.class);
            startActivity(a);
        }else if(v.getId()==bolum2.getId()){
            a=new Intent(this,ikinciBolumOyun.class);
            startActivity(a);
        }else if(v.getId()==bolum3.getId()){
            a=new Intent(this,UcuncuBolumOyun.class);
            startActivity(a);
        }
    }
}
