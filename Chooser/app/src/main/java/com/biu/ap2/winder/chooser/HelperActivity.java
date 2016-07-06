package com.biu.ap2.winder.chooser;

import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.biu.ap2.winder.chooser.Adapters.AdvanceBarAdapter;
import com.biu.ap2.winder.chooser.ChooseFragments.Logic;
import com.biu.ap2.winder.chooser.ChooseFragments.chooseFragment;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
* this activity will be frame for all the helper stage - it will
*   include choose fragment to handle all kind of choose method
*   it also have advance bar at top so the user can follow his steps
 */
public class HelperActivity extends ActionBarActivity {
    public int stepsCnt = 0;
    public int advance = 0;
    AdvanceBarAdapter advancebaradapter;
    ListView liststeps;
    TextView fragmenttitle;
    List<Integer> steps;
    public Map<Choice, Integer> map = new HashMap<>();
    private List<chooseFragment> cfl;
    private Logic logic;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_helper);

        String Dilemmaname = "null";
        //get the name of dilemma
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            Dilemmaname = getIntent().getExtras().getString("view");
        }
        TextView name = (TextView) findViewById(R.id.helper_name);
        name.setText("helping for " + Dilemmaname);
        fragmenttitle = (TextView) findViewById(R.id.fragment_title);

        //get the choice for this dilemma
        Decision d = Data.getD(Dilemmaname);
        //TODO create temp decision for debugging
        //Decision d = createFakeDecision();


        //add all choice of this decision to map with grade 0
        if (d != null) {
            List<Choice> cl = d.getCl();
            for (Choice c:cl) {
                map.put(c,0);
            }
        }
        logic = new Logic(map.size());

        //cfl = logic.allTurnaments();
        //cfl = logic.allStandAlone();
        //cfl = logic.allRankAlone();
       // cfl = logic.firstStandAloneThenRankAlone();
        cfl = logic.allNchooseK();

        switch (getIntent().getExtras().getString("selected_radio_button")) {
            case "N-Choose-K":
                cfl = logic.allNchooseK();
                break;
            case "Tournament Style":
                cfl = logic.allTurnaments();
                break;
            case "Rank Alone":
                cfl = logic.allRankAlone();
                break;
            case "Stand Alone":
                cfl = logic.allStandAlone();
                break;
        }



        liststeps = (ListView) findViewById(R.id.lstSteps);
        liststeps.setEmptyView(findViewById(R.id.empty));

        //set the number of steps
        int size = cfl.size();
        this.fillSteps(size);

        advancebaradapter = new AdvanceBarAdapter(this, steps);
        liststeps.setAdapter(advancebaradapter);
        advancebaradapter.notifyDataSetChanged();


        // set up the fragment with the first choise
        FragmentTransaction ft = getFragmentManager().beginTransaction();

        chooseFragment firstFragment = cfl.get(stepsCnt);
        firstFragment.preSet(this, map);

        ft.add(R.id.HelperFragment, firstFragment, firstFragment.name);
        ft.addToBackStack(null);
        ft.commit();
    }

    private void fillSteps(int n) {
        if (steps == null) steps = new ArrayList<>();
            else steps.clear();
        for (int i = 1; i <= n; i++) {
            steps.add(i);
        }

    }

    // update the Advance bar so user can know how much he done
    private void updateAdvanceBar() {
        // if done all steps from now on we dont know how many steps left
        if (stepsCnt+1 == steps.size()) {
            steps.clear();
            advancebaradapter.notifyDataSetChanged();
        }
        // move to default  - tournament fragments
        if (stepsCnt+1 >= steps.size()) {
            String remain = Integer.toString(map.size());
            String s = "second round - moving to tournament - remain " + remain + " choices";
            fragmenttitle.setText(s);
            stepsCnt++;
            return;

        }
        //advancing the current bar (for now, *10, in the future ?)
        int iter = steps.get(stepsCnt);
        steps.remove(stepsCnt);
        steps.add(stepsCnt, iter*10);
        advancebaradapter.notifyDataSetChanged();
        stepsCnt++;
    }


    public void CheckWinner(Map<Choice, Integer> map) {
        if (map == null || map.isEmpty()) return;
        if (map.size() == 1) {
            Choice c = chooseFragment.getFirst(map);
            int score = map.get(c);
            haveWinner(c, score);
            return;
        }
        Map<Choice, Integer> newMap = chooseFragment.reduceNonMaximum(map);
        this.advance = -1;
        choose(newMap);
    }

    //private PopupWindow popupWindow;
    //private LayoutInflater layoutInflater;

    private void haveWinner(Choice c, int score) {
        String s = "choose " + c.getName() + "!" + "\n" + "Score: " + Integer.valueOf(score);
        Intent i = new Intent(getApplicationContext(), WinnerActivity.class);
        String fileName = s;//no .png or .jpg needed
        try {
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            c.getFirstPic().compress(Bitmap.CompressFormat.JPEG, 100, bytes);
            FileOutputStream fo = openFileOutput(fileName, Context.MODE_PRIVATE);
            fo.write(bytes.toByteArray());
            // remember close file output
            fo.close();
        } catch (Exception e) {
            e.printStackTrace();
            fileName = null;
        }
        i.putExtra("filename", s);
        startActivity(i);
        
        //TODO move the Dilemma Activity
    }

    public void choose(Map<Choice, Integer> map) {
        if (map.size() == 1) {
            CheckWinner(map);
            return;
        }
        this.map = map;
        updateAdvanceBar();

        //take new fragment - according to logic
        // if there is not more get default
        chooseFragment nextFragment;
        if (this.advance == -1) {
            nextFragment = logic.getDefault();
            this.advance++;
        } else if (cfl.size() <= stepsCnt) nextFragment = logic.getDefault();
            else nextFragment = cfl.get(stepsCnt);
        nextFragment.preSet(this, map);

        // replace and insert the new chooseFragment
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.HelperFragment, nextFragment, nextFragment.name);
        transaction.addToBackStack(null);
        transaction.commit();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_helper, menu);
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

    private Decision createFakeDecision() {
        Decision d = new Decision("testing logic stage");

        Choice c1 = new Choice(1, "ch1", "none");
        c1.addPicture(BitmapFactory.decodeResource(getApplicationContext().getResources(),R.drawable.test1));
        Choice c2 = new Choice(2, "ch2", "none");
        c2.addPicture(BitmapFactory.decodeResource(getApplicationContext().getResources(),R.drawable.test2));
        Choice c3 = new Choice(3, "ch3", "none");
        c3.addPicture(BitmapFactory.decodeResource(getApplicationContext().getResources(),R.drawable.test3));
        Choice c4 = new Choice(4, "ch4", "none");
        c4.addPicture(BitmapFactory.decodeResource(getApplicationContext().getResources(),R.drawable.test4));

        d.addChoice(c1);
        d.addChoice(c2);
        d.addChoice(c3);
        d.addChoice(c4);
        return d;
    }
}
