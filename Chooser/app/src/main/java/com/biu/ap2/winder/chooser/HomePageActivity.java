package com.biu.ap2.winder.chooser;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;


public class HomePageActivity extends ActionBarActivity {
    //for the decisions list display
    DecisionsFragment df;
    final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        ImageButton del = (ImageButton) findViewById(R.id.deleteMemory);
        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "deleting memory", Toast.LENGTH_SHORT).show();
                Splash.data.deleteAllData();
                //Toast.makeText(getApplicationContext(), "saving memory", Toast.LENGTH_SHORT).show();
                //Splash.data.saveData();
            }
        });


        ImageButton add = (ImageButton) findViewById(R.id.add_decision_button);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                // set title
                alertDialogBuilder.setTitle("Enter new Name");
                final EditText text = new EditText(context);

                // set dialog message
                alertDialogBuilder.setView(text).setCancelable(false)
                        .setPositiveButton("Create",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                final String name = text.getText().toString();
                                //Toast.makeText(getApplicationContext(), name, Toast.LENGTH_SHORT).show();
                                Decision d = new Decision(name);

                                Data.decisionList.add(d);

                                 Intent i = new Intent(context, DilemmaActivity.class);
                                 i.putExtra("view", name);
                                 startActivity(i);
                            }
                        })
                        .setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                dialog.cancel();
                            }
                        });
                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();
                // show it
                alertDialog.show();


            }

        });


        df = new DecisionsFragment();
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.DecisionsFragment, df);
        ft.commit();

    }












    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
