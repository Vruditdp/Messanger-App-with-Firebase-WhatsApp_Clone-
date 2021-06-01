package com.example.whatsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class WelcomeActivity extends AppCompatActivity {

    Animation topAnim, bottomAnim;
    ImageView image;
    TextView logo,slogan;
    private Handler mHandler;
    private Runnable mRunnable;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_welcom);

        topAnim= AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottomAnim= AnimationUtils.loadAnimation(this,R.anim.bottom_animation);

        image=findViewById(R.id.imageView);
        logo=findViewById(R.id.textView);
        slogan=findViewById(R.id.textView2);

//        anima ani = new anima();
//        ani.ani1();

        image.setAnimation(topAnim);
        logo.setAnimation(bottomAnim);
        slogan.setAnimation(bottomAnim);


        //finish();

        mRunnable=new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                finish();
            }
        };

//        image.setAnimation(bottomAnim);
//        logo.setAnimation(topAnim);
//        slogan.setAnimation(topAnim);

        mHandler=new Handler();
        mHandler.postDelayed(mRunnable,2800);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mHandler!=null && mRunnable!=null)
        mHandler.removeCallbacks(mRunnable);

    }


}
//class anima extends AppCompatActivity{
//    void ani1(){
//        Animation topAnim, bottomAnim;
//        ImageView image;
//        TextView logo,slogan;
//
//        topAnim= AnimationUtils.loadAnimation(this,R.anim.top_animation);
//        bottomAnim= AnimationUtils.loadAnimation(this,R.anim.bottom_animation);
//
//        image=findViewById(R.id.imageView);
//        logo=findViewById(R.id.textView);
//        slogan=findViewById(R.id.textView2);
//        image.setAnimation(topAnim);
//        logo.setAnimation(bottomAnim);
//        slogan.setAnimation(bottomAnim);
//
//    }
//}