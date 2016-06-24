package com.biu.ap2.winder.chooser;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;

public class ReloadService extends IntentService {
        public static final String DONE = "chooser.Services.ReloadService.DONE";

        public ReloadService() {
            super(ReloadService.class.getName());
        }

        public ReloadService(String name) {
            super(name);
        }

        @Override
        protected void onHandleIntent(Intent intent) {
            /*
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            */
            Intent i = new Intent(DONE);
            this.sendBroadcast(i);
        }

}
