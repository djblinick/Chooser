package com.biu.ap2.winder.chooser.ChooseFragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.biu.ap2.winder.chooser.Choice;
import com.biu.ap2.winder.chooser.OnSwipeTouchListener;
import com.biu.ap2.winder.chooser.R;

import java.util.Iterator;
/*
* this class is single choice where user can sweep rignt or left
*       for keep or discharge
 */
public class StandAloneFragment extends chooseFragment {
    // number of point when win StandAlone 
    public static int extra = 100;
    Choice choice;

    public StandAloneFragment() {
        this.name = "Stand Alone";
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_stand_alone, container, false);

        if (map == null || map.size() < 1) return view;
        ImageView img = (ImageView) view.findViewById(R.id.sa_img);

        // get the current choice
        Iterator<Choice> choiceIterator = super.getIterInPlace();
        if (!choiceIterator.hasNext()) return view;
        choice = choiceIterator.next();
        // set the picture from that choice
        Bitmap choiceImageBitmap = choice.getFirstPic();
        //Bitmap choiceImageBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.test1);
        img.setImageBitmap(choiceImageBitmap);
        //img.setImageResource(R.drawable.test1);

        // listener for right or left swift
        img.setOnTouchListener(new OnSwipeTouchListener(getActivity()) {
            @Override
            public void onSwipeLeft() {
                // set new rank
                int newRank = map.get(choice) + extra;
                map.put(choice, newRank);
                // increment because we continue with this option
                ha.advance++;
                ha.choose(map);
            }
            @Override
            public void onSwipeRight() {
                // delete choice and continue
                map.remove(choice);
                ha.choose(map);
            }
        });
        return view;
    }
}
