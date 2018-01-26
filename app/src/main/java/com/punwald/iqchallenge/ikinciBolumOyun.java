package com.punwald.iqchallenge;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.media.MediaPlayer;
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

public class ikinciBolumOyun extends Activity implements View.OnClickListener, RewardedVideoAdListener {
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

    int[] sorular =new int[7];
    String[] cevap={"9","8","7","12","3","5","6"};
    String[] ipucu={"1.cevap","2.cevap","3.cevap","4.cevap","5.cevap","6.cevap","7.cevap"};

    int suan=0,tipHakki=3,ensonlvl=0,kacinciTip=0;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ikinci_bolum_oyun);
        Intent i=getIntent();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        relativeLayout= (RelativeLayout) findViewById(R.id.layout);
        /*Reklam*/
        MobileAds.initialize(this, "ca-app-pub-1592029610374280~2237318862");
        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        /*Video Reklam*/
        mAd = MobileAds.getRewardedVideoAdInstance(this);
        mAd.setRewardedVideoAdListener(this);
        loadRewardedVideoAd();

        /*Animasyon*/
        gecisAnimation = AnimationUtils.loadAnimation(this,R.anim.sorugecisanim);
        yanlisCevapAnimation=AnimationUtils.loadAnimation(this,R.anim.yanliscevapanim);


        imageView= (ImageView) findViewById(R.id.imageview);
        cevapB= (Button) findViewById(R.id.cevapButon);
        tipB= (Button) findViewById(R.id.ipucuButon);
        cvpGirdi= (EditText) findViewById(R.id.cevap);
        ileriB= (Button) findViewById(R.id.ileri);
        geriB= (Button) findViewById(R.id.geri);
        bolumText= (TextView) findViewById(R.id.bolumText);
        ipucuText= (TextView) findViewById(R.id.ipucuText);
        reklam= (Button) findViewById(R.id.reklam);

        blow=Typeface.createFromAsset(getAssets(),"fonts/blowbrush.otf");
        bolumText.setTypeface(blow);
        /*Sorular*/
        sorular[0]=R.drawable.tablosoru1;
        sorular[1]=R.drawable.tablosoru2;
        sorular[2]=R.drawable.tablosoru3;
        sorular[3]=R.drawable.tablosoru4;
        sorular[4]=R.drawable.tablosoru5;
        sorular[5]=R.drawable.tablosoru6;
        sorular[6]=R.drawable.tablosoru7;
        /*Sorular Son*/

        /*SharedPreferences*/

        shared=getPreferences(Context.MODE_PRIVATE);
        editor=shared.edit();

        suan=shared.getInt("suanLvl",0);
        tipHakki=shared.getInt("ipucuHakki",3);
        ensonlvl=shared.getInt("ensonlvl",0);

        /*SharedPreferences Son*/

        cevapB.setOnClickListener(this);
        tipB.setOnClickListener(this);
        ileriB.setOnClickListener(this);
        geriB.setOnClickListener(this);
        reklam.setOnClickListener(this);
        if (ensonlvl>0){
            suan=ensonlvl;
        }

        imageView.setBackgroundResource(sorular[suan]);
        bolumText.setText("#"+(suan+1));
        tipB.setText(tipHakki+" ");



    }

    @Override
    public void onClick(View v) {
        if (v.getId()==cevapB.getId()){
            cevapString=cvpGirdi.getText().toString();
            if(!cvpGirdi.toString().isEmpty()){
                if(cevapString.equals(cevap[suan])) {
                    if (suan<(sorular.length-1)){

                        suan++;
                        if(!(ensonlvl>=suan)){
                            ensonlvl=suan;
                        }
                        if(suan%5==0){
                            tipHakki++;
                        }
                        kacinciTip=suan;
                        imageView.startAnimation(gecisAnimation);
                        imageView.setBackgroundResource(sorular[suan]);
                        bolumText.setText("#" + (suan+1));
                        cvpGirdi.setText("");
                        cvpGirdi.setHint("Cevap");
                        ipucuText.setText("***İpucu***");
                        tipB.setText(tipHakki+"");

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
            if(tipHakki>0){
                if(kacinciTip==suan) {
                    kacinciTip++;
                    tipHakki--;
                    ipucuText.setText(ipucu[suan]);
                    tipB.setText(tipHakki + "");
                }
            }else{
                Toast.makeText(this,"İpucu Hakkınız Yok",Toast.LENGTH_LONG).show();
            }
        }//İpucu
        else if(v.getId()==ileriB.getId()){
            if(suan<ensonlvl){
                suan++;
                imageView.setBackgroundResource(sorular[suan]);
                bolumText.setText("#"+(suan+1));
                cvpGirdi.setText("");
                cvpGirdi.setHint("Cevap");;
                ipucuText.setText("***İpucu***");
                tipB.setText(tipHakki+"");
            }else{
                Toast.makeText(this,"Kilitli",Toast.LENGTH_LONG).show();
            }
        }//İleri Buton
        else if(v.getId()==geriB.getId()){
            if(suan>0){
                suan--;
                imageView.setBackgroundResource(sorular[suan]);
                bolumText.setText("#"+(suan+1));
                cvpGirdi.setText("");
                cvpGirdi.setHint("Cevap");
                ipucuText.setText("***İpucu***");
                tipB.setText(tipHakki+"");
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
        editor.putInt("suanLvl",suan);
        editor.putInt("ipucuHakki",tipHakki);
        editor.putInt("ensonlvl",ensonlvl);
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

}

