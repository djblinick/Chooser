package com.biu.ap2.winder.chooser;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by t-dablin on 7/6/2016.
 */
public class WinnerActivity extends ActionBarActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.winner_popup);

        TextView winner_text = (TextView) findViewById(R.id.winner_name_textview);
        String winner_string = getIntent().getExtras().getString("filename");
        winner_text.setText(winner_string);

        ImageView winner_image = (ImageView) findViewById(R.id.winner_image);
        try {
            Bitmap bitmap = BitmapFactory.decodeStream(getApplicationContext()
                    .openFileInput((getIntent().getExtras().getString("filename"))));
            winner_image.setImageBitmap(bitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
