package com.punwald.iqchallenge;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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
    RelativeLayout relativeLayout;
    ImageView imageView;
    Button cevapB,tipB,ileriB,geriB,reklam;
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
    int suan=0,tipHakki,ensonlvl=0;

    @Override
    protected void onPause() {
        super.onPause();
    }


    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void sifreSoru(){
        sorular.add(R.drawable.sifre1);
        sorular.add(R.drawable.sifre2);
        sorular.add(R.drawable.sifre3);
        sorular.add(R.drawable.sifre4);
        sorular.add(R.drawable.sifre5);
        sorular.add(R.drawable.sifre6);
        sorular.add(R.drawable.sifre7);
        cevap.add("265");
        cevap.add("636");
        cevap.add("111");
        cevap.add("1905");
        cevap.add("11935");
        cevap.add("3698");
        cevap.add("1194");

    }
    public void tabloSoru(){
        sorular.add(R.drawable.tablosoru1);
        sorular.add(R.drawable.tablosoru2);
        sorular.add(R.drawable.tablosoru3);
        sorular.add(R.drawable.tablosoru4);
        sorular.add(R.drawable.tablosoru5);
        sorular.add(R.drawable.tablosoru6);
        sorular.add(R.drawable.tablosoru7);
        cevap.add("9");
        cevap.add("8");
        cevap.add("7");
        cevap.add("12");
        cevap.add("3");
        cevap.add("5");
        cevap.add("6");
        ipucu.add("İki tarafı topla");
        ipucu.add("Satırların toplamı");
        ipucu.add("Spiral");
        ipucu.add("Köşegen toplamları");
        ipucu.add("Toplamlar üç katına eşit");
        ipucu.add("Sudoku");
        ipucu.add("Bunu bizde çözemedik :D");
    }
    public void sekilSoru(){
        sorular.add(R.drawable.sekil1);
        sorular.add(R.drawable.sekil2);
        sorular.add(R.drawable.sekil3);
        sorular.add(R.drawable.sekil4);
        sorular.add(R.drawable.sekil4);
        sorular.add(R.drawable.sekil5);
        sorular.add(R.drawable.sekil7);
        cevap.add("3");
        cevap.add("11");
        cevap.add("1056");
        cevap.add("6");
        cevap.add("6");
        cevap.add("72");
        cevap.add("42");
        ipucu.add("Ters işlem");
        ipucu.add("Pascal Üçgeni");
        ipucu.add("Kenar Sayısı");
        ipucu.add("Köşe Sayısı");
        ipucu.add("Her şekil için bir işlem");
        ipucu.add("Topla çarp");
        ipucu.add("Rakamlara ayır");
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

        tipHakki=shared.getInt("ipucuHakki",3);

        /*SharedPreferences Son*/

        /*Sorular*/
        if(bolum.equals("sifre")){
            sifreSoru();
            suan=shared.getInt("sifreSuan",0);
            ensonlvl=shared.getInt("ensonlvlSifre",0);
            ipucuAcikmi=shared.getBoolean("ipucuSifre",false);
        }else if(bolum.equals("tablo")){
            tabloSoru();
            suan=shared.getInt("tabloSuan",0);
            ensonlvl=shared.getInt("ensonlvlTablo",0);
            ipucuAcikmi=shared.getBoolean("ipucuTablo",false);
        }else if(bolum.equals("sekil")){
            sekilSoru();
            suan=shared.getInt("sekilSuan",0);
            ensonlvl=shared.getInt("ensonlvlSekil",0);
            ipucuAcikmi=shared.getBoolean("ipucuSekil",false);
        }
        /*Sorular Son*/

        if (ensonlvl>0){
            suan=ensonlvl;
        }

        imageView.setBackgroundResource(sorular.get(suan));
        bolumText.setText("#"+(suan+1)+"/7");
        tipB.setText(tipHakki+" ");
        kontrol();


    }

    private void initialize() {

        gecisAnimation = AnimationUtils.loadAnimation(this,R.anim.sorugecisanim);
        yanlisCevapAnimation=AnimationUtils.loadAnimation(this,R.anim.yanliscevapanim);
        relativeLayout= (RelativeLayout) findViewById(R.id.layout);
        imageView= (ImageView) findViewById(R.id.imageview);
        cevapB= (Button) findViewById(R.id.cevapButon);
        tipB= (Button) findViewById(R.id.ipucuButon);
        cvpGirdi= (EditText) findViewById(R.id.cevap);
        ileriB= (Button) findViewById(R.id.ileri);
        geriB= (Button) findViewById(R.id.geri);
        bolumText= (TextView) findViewById(R.id.bolumText);
        ipucuText= (TextView) findViewById(R.id.ipucuText);
        reklam= (Button) findViewById(R.id.reklam);
        cevapB.setOnClickListener(this);
        tipB.setOnClickListener(this);
        ileriB.setOnClickListener(this);
        geriB.setOnClickListener(this);
        reklam.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId()==cevapB.getId()){
            cevapString=cvpGirdi.getText().toString().trim();
            if(!cvpGirdi.toString().isEmpty()){
                if(cevapString.equals(cevap.get(suan))) {
                    if (suan<(sorular.size()-1)){

                        suan++;
                        if(!(ensonlvl>=suan)){
                            ensonlvl=suan;
                        }
                        if(suan%5==0){
                            tipHakki++;
                        }
                        //kacinciTip=suan;
                        imageView.startAnimation(gecisAnimation);
                        imageView.setBackgroundResource(sorular.get(suan));
                        bolumText.setText("#" + (suan+1)+"/7");
                        cvpGirdi.setText("");
                        cvpGirdi.setHint("Cevap");
                        ipucuText.setText("***İpucu***");
                        tipB.setText(tipHakki+"");
                        ipucuAcikmi=false;
                        kontrol();
                    }/*Kontrol İf*/else{
                        Toast.makeText(this,"Bölüm Bitti",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(this,Bolumler.class));
                    }
                }else {
                    relativeLayout.startAnimation(yanlisCevapAnimation);
                    Toast.makeText(this,"Yanlış Cevap",Toast.LENGTH_SHORT).show();
                }

            }
        }//İpucu
        else if(v.getId()==tipB.getId()){
            if (ipucuAcikmi){
                ipucuText.setText(ipucu.get(suan));
            }else {
                if (tipHakki > 0) {
                    tipHakki--;
                    ipucuText.setText(ipucu.get(suan));
                    tipB.setText(tipHakki + "");
                    ipucuAcikmi = true;
                } else {
                    Toast.makeText(this, "İpucu Hakkınız Yok", Toast.LENGTH_LONG).show();
                }
            }
        }//İpucu
        else if(v.getId()==ileriB.getId()){
            if(suan<ensonlvl){
                suan++;
                imageView.setBackgroundResource(sorular.get(suan));
                bolumText.setText("#"+(suan+1)+"/7");
                cvpGirdi.setText("");
                cvpGirdi.setHint("Cevap");;
                ipucuText.setText("***İpucu***");
                tipB.setText(tipHakki+"");
                kontrol();
            }else{
                Toast.makeText(this,"Kilitli",Toast.LENGTH_LONG).show();
            }
        }//İleri Buton
        else if(v.getId()==geriB.getId()){
            if(suan>0){
                suan--;
                imageView.setBackgroundResource(sorular.get(suan));
                bolumText.setText("#"+(suan+1)+"/7");
                cvpGirdi.setText("");
                cvpGirdi.setHint("Cevap");
                ipucuText.setText("***İpucu***");
                tipB.setText(tipHakki+"");
                kontrol();
            }
        }
        else if(v.getId()==reklam.getId()){
            if(mAd.isLoaded()) {
                geriSayim();
                mAd.show();
                loadRewardedVideoAd();
            }else{
                Toast.makeText(this,"Hata Oluştu!",Toast.LENGTH_SHORT).show();
                loadRewardedVideoAd();
            }
        }
        /*SharedPreferences düzenleme*/
        if(bolum.equals("sifre")){
            editor.putInt("sifreSuan",suan);
            editor.putInt("ensonlvlSifre",ensonlvl);
            editor.putBoolean("ipucuSifre",ipucuAcikmi);
        }else if(bolum.equals("tablo")){
            editor.putInt("tabloSuan",suan);
            editor.putInt("ensonlvlTablo",ensonlvl);
            editor.putBoolean("ipucuTablo",ipucuAcikmi);
        }else if(bolum.equals("sekil")){
            editor.putInt("sekilSuan",suan);
            editor.putInt("ensonlvlSekil",ensonlvl);
            editor.putBoolean("ipucuSekil",ipucuAcikmi);
        }

        editor.putInt("ipucuHakki",tipHakki);
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
        tipHakki++;
        tipB.setText(tipHakki + "");
    }

    @Override
    public void onRewardedVideoAdFailedToLoad(int errorCode) {
        loadRewardedVideoAd();
    }

    @Override
    public void onRewardedVideoAdLoaded() {
    }

    @Override
    public void onRewardedVideoAdOpened() {
    }

    @Override
    public void onRewardedVideoStarted() {
    }

    public void geriSayim() {
        new CountDownTimer(180000, 1000) {
            public void onTick(long millisUntilFinished) {
                // Kalan süreyi saniye cinsine çevirip ekran alanına yazıyoruz.
                reklam.setText(""+(millisUntilFinished/1000));
            }

            public void onFinish() {
                // Süre tamamlandığını bildiriyoruz.
                reklam.setText("+1");
                loadRewardedVideoAd();
            }
        }.start();
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK ) {

            startActivity(new Intent(this,Ayarlar.class));
        }
        return true;
    }

    public void kontrol(){
        if(suan==0){
            geriB.setVisibility(Button.INVISIBLE);
        }else if(suan==6){
            ileriB.setVisibility(Button.INVISIBLE);
        }else{
            ileriB.setVisibility(Button.VISIBLE);
            geriB.setVisibility(Button.VISIBLE);
        }
    }
}

