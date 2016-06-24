package com.biu.ap2.winder.chooser;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


public class DilemmaActivity extends ActionBarActivity {
    private static int ObjsCnt = 0;
    private ObjFragment of;
    private String Dname;
    final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dilemma);

        //to get your dilemma
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            Dname = getIntent().getExtras().getString("view");
        }

        TextView name = (TextView) findViewById(R.id.dilemmatitle);
        name.setText(Dname);


        ImageButton add = (ImageButton) findViewById(R.id.add_object_button);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                // set title
                alertDialogBuilder.setTitle("Enter New Choice");
                final EditText Cname = new EditText(context);
                Cname.setHint("Name");
                final EditText Cdata = new EditText(context);
                Cdata.setHint("Extra Data");
                LinearLayout ll=new LinearLayout(getApplicationContext());
                ll.setOrientation(LinearLayout.VERTICAL);
                ll.addView(Cname);
                ll.addView(Cdata);
                alertDialogBuilder.setView(ll);


                // set dialog message
                alertDialogBuilder.setCancelable(false)
                        .setPositiveButton("Create", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                String name = Cname.getText().toString();
                                String data = Cdata.getText().toString();
                                //Choice c = new Choice(ObjsCnt++, name);
                                Choice c = new Choice(ObjsCnt++, name, data);
                                of.addDecision(c);
                                //Data.addChoice(Dname,c); no need because save data like this when close

                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();
                // show it
                alertDialog.show();

            }

        });


        Button helpMeDecide = (Button) findViewById(R.id.help_me_decided_button);
        helpMeDecide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), HelperActivity.class);
                i.putExtra("view", Dname);
                startActivity(i);
            }
        });

        of = new ObjFragment();
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        ft.add(R.id.ObjectsFragment, of);
        ft.commit();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        Bitmap bm = null;
        Activity activity = this;
        if (resultCode == activity.RESULT_OK) {
            Uri selectedImage = imageReturnedIntent.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = activity.getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            if (cursor.moveToFirst()) {
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String filePath = cursor.getString(columnIndex);
                bm = BitmapFactory.decodeFile(filePath);
            }
            cursor.close();
        }


        // insert the Bitmap into the requested choice
        Choice c = null;
        Decision d = Data.getD(Dname);
        for (Choice ch: d.getCl()) {
            if (ch.getId() == requestCode) c = ch;
        }
        if (c == null) return;
        //c.addPicture(bm);
        //Bitmap croppedBmp = Bitmap.createBitmap(bm, 0, 0, 30, 30);
        Data.addPicToChoice(Dname, c.getName(), bm);



        Intent intent = new Intent(this, ReloadService.class);
        startService(intent);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_dilemma, menu);
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

    @Override
    public void onBackPressed() {
        if (this.getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        } else
            super.onBackPressed();
    }
}
