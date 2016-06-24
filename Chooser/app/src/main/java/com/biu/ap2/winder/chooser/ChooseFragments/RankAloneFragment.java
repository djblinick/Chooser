package com.biu.ap2.winder.chooser.ChooseFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.biu.ap2.winder.chooser.Choice;
import com.biu.ap2.winder.chooser.R;

import java.util.Iterator;
/*
* this class is single choice where user can rank the choice
*           between 3 option
 */
public class RankAloneFragment extends chooseFragment {
    // rank for each button
    public final int button1 = 1;
    public final int button2 = 2;
    public final int button3 = 3;

    Choice choice;



    public RankAloneFragment() {this.name = "Rank Alone";}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rank_alone, container, false);

        // to avoid crash-app
        if (map == null || map.size() < 1) return view;
        ImageView img = (ImageView) view.findViewById(R.id.ra_img);

        // get the right choice from the map
        Iterator<Choice> choiceIterator = super.getIterInPlace();
        if (!choiceIterator.hasNext()) return view;
        choice = choiceIterator.next();

        img.setImageBitmap(choice.getFirstPic());
        // set 3 button for ranking
        Button b1 = (Button) view.findViewById(R.id.ra_scr_1);
        Button b2 = (Button) view.findViewById(R.id.ra_scr_2);
        Button b3 = (Button) view.findViewById(R.id.ra_scr_3);
        // increment because we choose this one at any case
        // what is not sure is the new rank
        ha.advance++;

        // if one of the button has been click on set the choice ranking accordinaly
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonClick(button1);
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonClick(button2);
            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonClick(button3);
            }
        });

        return view;
    }

    private void onButtonClick(int newRank) {
        map.put(choice, newRank);
        if( ha.advance == map.size())
            ha.CheckWinner(map);
        else ha.choose(map);
    }


}
