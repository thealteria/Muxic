package com.example.user.muxic;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by Aman on 9/8/2016.
 */
public class SplashScreen extends Activity {

    @Override
protected void onCreate(Bundle savedInstanceState) {
    // TODO Auto-generated method stub
    super.onCreate(savedInstanceState);
    setContentView(R.layout.splash);

    Thread timerThread = new Thread(){
        public void run(){
            try{
                sleep(2000);
            }catch(InterruptedException e){
                e.printStackTrace();
            }finally{
                Intent intent = new Intent(SplashScreen.this,OnBoarding.class);
                startActivity(intent);
            }
        }
    };
    timerThread.start();
}

        @Override
        protected void onPause() {
            // TODO Auto-generated method stub
            super.onPause();
            finish();
        }

}