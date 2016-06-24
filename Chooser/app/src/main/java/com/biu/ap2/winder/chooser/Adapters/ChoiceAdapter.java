package com.biu.ap2.winder.chooser.Adapters;

import android.app.Activity;
import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;


import com.biu.ap2.winder.chooser.Choice;
import com.biu.ap2.winder.chooser.R;

import java.util.List;

/**
 * Created by winder on 3/10/16.
 */
public class ChoiceAdapter  extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<Choice> choiceList;
    private SparseBooleanArray mSelectedItemsIds;



    // constructor
    public ChoiceAdapter(Activity activity, List<Choice> dl) {
        this.activity = activity;
        this.choiceList = dl;
        mSelectedItemsIds = new SparseBooleanArray();
    }
    // those override methods as adaptor
    @Override
    public int getCount() {
        return choiceList.size();
    }

    @Override
    public Object getItem(int location) {
        return choiceList.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.list_item_choice, null);

        //get current choice and set the first picture as the option to choose
        Choice choice = choiceList.get(position);
        ImageView picture = (ImageView) convertView.findViewById(R.id.pic);
        picture.setImageBitmap(choice.getFirstPic());

        return convertView;
    }


    //when click on item this method will be called
    public void toggleSelection(int position) {
        selectView(position, !mSelectedItemsIds.get(position));
    }

    //swich selection item - if already selected delete him from the SelectedItemList
    // else, insert to that list
    private void selectView(int position, boolean value) {
        if (value) {
            mSelectedItemsIds.put(position, value);
            //TODO also change color
        }
        else {
            mSelectedItemsIds.delete(position);
            //TODO also change color
        }
        notifyDataSetChanged();
    }

    //getter for list of selected items
    public SparseBooleanArray getSelectedIds() {
        return mSelectedItemsIds;
    }

    public void clearSelection() {
        mSelectedItemsIds.clear();
    }


}