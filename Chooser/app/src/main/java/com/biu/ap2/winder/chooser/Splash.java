package com.biu.ap2.winder.chooser;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

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
