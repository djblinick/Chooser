package com.biu.ap2.winder.chooser;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

/* this activity will be create when the application will be open
 *      this will show nice open screen and wait for 1 sec
 *      while the application start load the data
 */
public class Splash extends ActionBarActivity {
    static Data data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        StartAnimations();


        /*
        //create the class that will handle al the data in this app
        data = new Data(getApplicationContext());

        Thread tLoader = new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    // start load all decision from memory
                    data.loadData();
                    Thread.sleep(1000); //so the splash will be at least 1 sec
                } catch (Exception ex) {
                    //cant sleep for 5 sec or load the data
                }
                Intent i = new Intent(Splash.this, HomePageActivity.class);
                startActivity(i);

            }
        });
        tLoader.start();
        */
    }

    private void StartAnimations() {
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.alpha);
        anim.reset();
        RelativeLayout l=(RelativeLayout) findViewById(R.id.rel_lay);
        l.clearAnimation();
        l.startAnimation(anim);

        anim = AnimationUtils.loadAnimation(this, R.anim.translate);
        anim.reset();
        ImageView iv = (ImageView) findViewById(R.id.openIcon);
        iv.clearAnimation();
        iv.startAnimation(anim);

        //TypeWriter writer = new TypeWriter(this);
        //setContentView(writer);

        Typewriter writer = (Typewriter) findViewById(R.id.title);
        //Add a character every 150ms
        writer.setCharacterDelay(200);
        writer.animateText(getString(R.string.app_name));

        //create the class that will handle al the data in this app
        data = new Data(getApplicationContext());

        Thread tLoader = new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    // start load all decision from memory
                    data.loadData();
                    Thread.sleep(1000); //so the splash will be at least 1 sec
                } catch (Exception ex) {
                    //cant sleep for 5 sec or load the data
                }
                Intent i = new Intent(Splash.this, HomePageActivity.class);
                startActivity(i);

            }
        });
        tLoader.start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //this activity is destroy that mean the user closing the app
        // then we start saving all the data
        data.saveData();
    }



}
