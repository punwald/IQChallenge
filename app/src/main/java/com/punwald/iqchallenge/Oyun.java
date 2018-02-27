package com.punwald.iqchallenge;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;
import java.util.ArrayList;

public class Oyun extends Activity implements View.OnClickListener, RewardedVideoAdListener {
    /*Animasyon*/
    Animation gecisAnimation,yanlisCevapAnimation;

    /*Shared Preferences*/
    SharedPreferences shared;
    SharedPreferences.Editor editor;

    /*View Erişimler*/
    LinearLayout relativeLayout;
    ImageView imageView;
    Button cevapB,tipB,ileriB,geriB;
    EditText cvpGirdi;
    String cevapString;
    TextView bolumText,ipucuText;
    Typeface blow;
    RewardedVideoAd mAd;
    /*View Erişimler Son*/

    ArrayList<Integer> sorular=new ArrayList<>();
    ArrayList<String> cevap=new ArrayList<>();
    ArrayList<String> ipucu=new ArrayList<>();
    String bolum;
    boolean ipucuAcikmi;
    int suan=0,ensonlvl=0;

    @Override
    protected void onPause() {
        super.onPause();
        sharedDuzenle();
    }
    @Override
    protected void onStop() {
        super.onStop();
        sharedDuzenle();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        sharedDuzenle();
    }

    public void sifreSoru() {
        sorular.add(R.drawable.sifre1); cevap.add("265"); ipucu.add("_6_");
        sorular.add(R.drawable.sifre2); cevap.add("636"); ipucu.add("6__");
        sorular.add(R.drawable.sifre3); cevap.add("111"); ipucu.add("__1");
        sorular.add(R.drawable.sifre4); cevap.add("1905"); ipucu.add("1__5");
        sorular.add(R.drawable.sifre5); cevap.add("11935"); ipucu.add("11_3_");
        sorular.add(R.drawable.sifre6); cevap.add("3698"); ipucu.add("_6_8");
        sorular.add(R.drawable.sifre7); cevap.add("1194"); ipucu.add("__94");
    }

    public void tabloSoru() {
        sorular.add(R.drawable.tablosoru1); cevap.add("9"); ipucu.add("İki tarafı topla");
        sorular.add(R.drawable.tablosoru2); cevap.add("8"); ipucu.add("Satırların toplamı");
        sorular.add(R.drawable.tablosoru3); cevap.add("7"); ipucu.add("Spiral");
        sorular.add(R.drawable.tablosoru4); cevap.add("12"); ipucu.add("Köşegen toplamları");
        sorular.add(R.drawable.tablosoru5); cevap.add("3"); ipucu.add("Toplamlar üç katına eşit");
        sorular.add(R.drawable.tablosoru6); cevap.add("5"); ipucu.add("Sudoku");
        sorular.add(R.drawable.tablosoru7); cevap.add("6"); ipucu.add("Bunu bizde çözemedik :D");
    }

    public void sekilSoru() {
        sorular.add(R.drawable.sekil1); cevap.add("3"); ipucu.add("Ters işlem");
        sorular.add(R.drawable.sekil2); cevap.add("11"); ipucu.add("Pascal Üçgeni");
        sorular.add(R.drawable.sekil3); cevap.add("1056"); ipucu.add("Kenar Sayısı");
        sorular.add(R.drawable.sekil4); cevap.add("6"); ipucu.add("Köşe Sayısı");
        sorular.add(R.drawable.sekil5); cevap.add("6"); ipucu.add("Her şekil için bir işlem");
        sorular.add(R.drawable.sekil6); cevap.add("72"); ipucu.add("Topla çarp");
        sorular.add(R.drawable.sekil7); cevap.add("42"); ipucu.add("Rakamlara ayır");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oyun);
        Intent i=getIntent();
        bolum= (String) i.getExtras().get("bolum");
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        /*Reklam*/
        MobileAds.initialize(this, "ca-app-pub-1592029610374280~2237318862");
        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        /*Video Reklam*/
        mAd = MobileAds.getRewardedVideoAdInstance(this);
        mAd.setRewardedVideoAdListener(this);
        loadRewardedVideoAd();

        initialize();

        blow=Typeface.createFromAsset(getAssets(),"fonts/blowbrush.otf");
        bolumText.setTypeface(blow);

        /*SharedPreferences*/
        shared=getPreferences(Context.MODE_PRIVATE);
        editor=shared.edit();

        /*SharedPreferences Son*/

        /*Sorular*/
        if(bolum.equals("sifre")) {
            sifreSoru();
            suan=shared.getInt("sifreSuan",0);
            ensonlvl=shared.getInt("ensonlvlSifre",0);
            ipucuAcikmi=shared.getBoolean("ipucuSifre",false);
        }else if(bolum.equals("tablo")) {
            tabloSoru();
            suan=shared.getInt("tabloSuan",0);
            ensonlvl=shared.getInt("ensonlvlTablo",0);
            ipucuAcikmi=shared.getBoolean("ipucuTablo",false);
        }else if(bolum.equals("sekil")) {
            sekilSoru();
            suan=shared.getInt("sekilSuan",0);
            ensonlvl=shared.getInt("ensonlvlSekil",0);
            ipucuAcikmi=shared.getBoolean("ipucuSekil",false);
        }
        /*Sorular Son*/

        if (ensonlvl>0) {
            suan=ensonlvl;
        }

        imageView.setBackgroundResource(sorular.get(suan));
        bolumText.setText((suan+1) + "/" + sorular.size());
        kontrol();
    }

    private void initialize() {
        gecisAnimation = AnimationUtils.loadAnimation(this,R.anim.sorugecisanim);
        yanlisCevapAnimation=AnimationUtils.loadAnimation(this,R.anim.yanliscevapanim);
        relativeLayout= (LinearLayout) findViewById(R.id.layout);
        imageView= (ImageView) findViewById(R.id.imageview);
        cevapB= (Button) findViewById(R.id.cevapButon);
        tipB= (Button) findViewById(R.id.ipucuButon);
        cvpGirdi= (EditText) findViewById(R.id.cevap);

        cvpGirdi.setOnKeyListener(new View.OnKeyListener(){
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    switch (keyCode) {
                        case KeyEvent.KEYCODE_ENTER:
                            Cevapla();
                            //cvpGirdi.setFocusable(false);
                            //cvpGirdi.setFocusableInTouchMode(true);
                            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(cvpGirdi.getWindowToken(), 0);
                            return true;
                        default:
                            break;
                    }
                }
                return false;
            }
        });

        ileriB= (Button) findViewById(R.id.ileri);
        geriB= (Button) findViewById(R.id.geri);
        bolumText= (TextView) findViewById(R.id.bolumText);
        ipucuText= (TextView) findViewById(R.id.ipucuText);
        cevapB.setOnClickListener(this);
        tipB.setOnClickListener(this);
        ileriB.setOnClickListener(this);
        geriB.setOnClickListener(this);
    }
    public void Cevapla(){
        cevapString=cvpGirdi.getText().toString().trim();
        if(!cvpGirdi.toString().isEmpty()){
            if(cevapString.equals(cevap.get(suan))) {
                if (suan<(sorular.size()-1)){
                    suan++;
                    if(!(ensonlvl>=suan)){
                        ensonlvl=suan;
                    }
                    //kacinciTip=suan;
                    imageView.startAnimation(gecisAnimation);
                    imageView.setBackgroundResource(sorular.get(suan));
                    bolumText.setText((suan+1) + "/" + sorular.size());
                    cvpGirdi.setText("");
                    cvpGirdi.setHint("Cevap");
                    ipucuText.setText("***İpucu***");
                    ipucuAcikmi=false;
                    kontrol();
                }/*Kontrol İf*/else{
                    Toast.makeText(this,"Bölüm Bitti",Toast.LENGTH_SHORT).show();
                    Intent a=new Intent(this,Bolumler.class);
                    a.putExtra("bolum",bolum);
                    startActivity(a);
                }
            }else {
                relativeLayout.startAnimation(yanlisCevapAnimation);
                Toast.makeText(this,"Yanlış Cevap",Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId()==cevapB.getId()){
            Cevapla();
        }//İpucu
        else if(v.getId()==tipB.getId()) {
            if (ipucuAcikmi){
                ipucuText.setText(ipucu.get(suan));
            }else {
                if(mAd.isLoaded()) {
                    mAd.show();
                    loadRewardedVideoAd();
                }else{
                    Toast.makeText(this,"Video Yükleniyor...",Toast.LENGTH_SHORT).show();
                    loadRewardedVideoAd();
                }

            }
        }//İpucu
        else if(v.getId()==ileriB.getId()) {
            if(suan<ensonlvl){
                suan++;
                imageView.setBackgroundResource(sorular.get(suan));
                bolumText.setText((suan+1) + "/" + sorular.size());
                cvpGirdi.setText("");
                cvpGirdi.setHint("Cevap");;
                ipucuText.setText("***İpucu***");
                kontrol();
            }else{
                Toast.makeText(this,"Kilitli",Toast.LENGTH_LONG).show();
            }
        }//İleri Buton
        else if(v.getId()==geriB.getId()) {
            if(suan>0){
                suan--;
                imageView.setBackgroundResource(sorular.get(suan));
                bolumText.setText((suan+1) + "/" + sorular.size());
                cvpGirdi.setText("");
                cvpGirdi.setHint("Cevap");
                ipucuText.setText("***İpucu***");
                kontrol();
            }
        }
        sharedDuzenle();

    }
    public void sharedDuzenle(){
        /*SharedPreferences düzenleme*/
        if(bolum.equals("sifre")) {
            editor.putInt("sifreSuan",suan);
            editor.putInt("ensonlvlSifre",ensonlvl);
            editor.putBoolean("ipucuSifre",ipucuAcikmi);
        }else if(bolum.equals("tablo")) {
            editor.putInt("tabloSuan",suan);
            editor.putInt("ensonlvlTablo",ensonlvl);
            editor.putBoolean("ipucuTablo",ipucuAcikmi);
        }else if(bolum.equals("sekil")) {
            editor.putInt("sekilSuan",suan);
            editor.putInt("ensonlvlSekil",ensonlvl);
            editor.putBoolean("ipucuSekil",ipucuAcikmi);
        }

        editor.commit();
        /*SharedPreferences düzenleme son*/
    }
    private void loadRewardedVideoAd() {
        mAd.loadAd("ca-app-pub-1592029610374280/3265483599",new AdRequest.Builder().build());
    }

    @Override
    public void onRewardedVideoAdLeftApplication() {
        Toast.makeText(this, "Left!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRewardedVideoAdClosed() {
        loadRewardedVideoAd();
    }

    @Override
    public void onRewarded(RewardItem reward) {
        Toast.makeText(this,"İpucu Kazandınız!",Toast.LENGTH_SHORT).show();
        ipucuText.setText(ipucu.get(suan));
        ipucuAcikmi = true;
    }

    @Override
    public void onRewardedVideoAdFailedToLoad(int errorCode) {
        loadRewardedVideoAd();
    }

    @Override
    public void onRewardedVideoAdLoaded() {}
    @Override
    public void onRewardedVideoAdOpened() {}
    @Override
    public void onRewardedVideoStarted() {}

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK ){
            startActivity(new Intent(this,Ayarlar.class));
        }
        if(keyCode == KeyEvent.KEYCODE_ENTER){
            Cevapla();
        }
        return true;
    }

    public void kontrol(){
        if(suan==0){
            geriB.setVisibility(Button.INVISIBLE);
        }else if(suan==sorular.size() - 1){
            ileriB.setVisibility(Button.INVISIBLE);
        }else{
            ileriB.setVisibility(Button.VISIBLE);
            geriB.setVisibility(Button.VISIBLE);
        }
    }
}
