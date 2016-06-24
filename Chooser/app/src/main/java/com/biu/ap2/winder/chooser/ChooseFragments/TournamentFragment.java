package com.biu.ap2.winder.chooser.ChooseFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.biu.ap2.winder.chooser.Choice;
import com.biu.ap2.winder.chooser.R;

import java.util.Iterator;

public class TournamentFragment extends chooseFragment {
    // number of point when choice win Tournament 
    public static int extra = 100;
    Choice choice1;
    Choice choice2;


    public TournamentFragment() {
        this.name = "Tournament";
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tournament, container, false);

        if (map == null || map.size() < 2) return view;

        // set the 2 image as butten
        ImageButton img1= (ImageButton)view.findViewById(R.id.firstPic);
        ImageButton img2= (ImageButton)view.findViewById(R.id.secondPic);

        // get the 2 current choices
        Iterator<Choice> cohiceIterator = super.getIterInPlace();
        if (!cohiceIterator.hasNext()) return view;
        choice1 = cohiceIterator.next();
        if (cohiceIterator.hasNext()) {
            choice2 = cohiceIterator.next();
        } else {
            //get back to start and get first choice
            cohiceIterator = super.getIterInFirstPlace();
            choice2 = cohiceIterator.next();
            // if choice1 == choice2 mean we only have on option
            if (choice1 == choice2) {ha.choose(map); return view;}
        }

        // set the picture from selected choices
        img1.setImageBitmap(choice1.getFirstPic());
        img2.setImageBitmap(choice2.getFirstPic());

        // set what happend when click upon
        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // set new rank for choice1
                int newRank = map.get(choice1) + extra;
                map.put(choice1, newRank);
                // remove what loss
                map.remove(choice2);
                // increment advance because we choose one option
                ha.advance++;
                ha.choose(map);
            }
        });
        img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // set new rank for choice2
                int newRank = map.get(choice2) + extra;
                map.put(choice2, newRank);
                // remove what loss
                map.remove(choice1);
                // increment advance because we choose one option
                ha.advance++;
                ha.choose(map);
            }
        });
        return view;
    }
}
