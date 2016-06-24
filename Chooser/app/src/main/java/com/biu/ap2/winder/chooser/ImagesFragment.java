package com.biu.ap2.winder.chooser;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class ImagesFragment extends Fragment {

    ImageView imgMain;
    int counter;
    List<Bitmap> pl;

    public ImagesFragment() {
        counter = 0;
        pl = new ArrayList<>();
    }

    public void setBitmapList(List<Bitmap> l) {pl = l;}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_images, container, false);

        imgMain = (ImageView) view.findViewById(R.id.images_imgMain);
        imgMain.setOnTouchListener(new OnSwipeTouchListener(getActivity()) {
            @Override
            public void onSwipeLeft() {
                counter++;
                setImage();
            }

            @Override
            public void onSwipeRight() {
                counter--;

                if (counter == 0)
                    counter = pl.size();

                setImage();
            }
        });
        setImage();
        return view;
    }

    private void setImage() {
        int current = counter % pl.size();
        Bitmap bm = pl.get(current);
        imgMain.setImageBitmap(bm);

    }


}