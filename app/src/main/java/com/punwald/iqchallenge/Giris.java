package com.punwald.iqchallenge;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class Giris extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giris);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(8000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                startActivity(new Intent(getApplicationContext(),Anasayfa.class));
            }
        }).start();

    }
}
