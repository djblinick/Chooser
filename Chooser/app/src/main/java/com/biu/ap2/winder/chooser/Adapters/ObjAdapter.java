package com.biu.ap2.winder.chooser.Adapters;

import android.app.Activity;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.biu.ap2.winder.chooser.Choice;
import com.biu.ap2.winder.chooser.Data;
import com.biu.ap2.winder.chooser.ImagesFragment;
import com.biu.ap2.winder.chooser.R;

import java.util.List;

/**
 * Created by winder on 11/20/15.
 */
public class ObjAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<Choice> objectList;
    private BaseAdapter baseAdapter;
    private String decistionName ;


    public ObjAdapter(Activity activity, List<Choice> ol, String name) {
        this.activity = activity;
        this.objectList = ol;
        baseAdapter = this;
        decistionName = name;
    }
    // those override methods as adaptor
    @Override
    public int getCount() {
        return objectList.size();
    }

    @Override
    public Object getItem(int location) {
        return objectList.get(location);
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
            convertView = inflater.inflate(R.layout.list_item_obj, null);

	Choice choice = objectList.get(position);

        //attach field to variables
        TextView name = (TextView) convertView.findViewById(R.id.name);
        TextView data = (TextView) convertView.findViewById(R.id.data);
        LinearLayout layout = (LinearLayout) convertView.findViewById(R.id.obj);
        ImageView examplePic = (ImageView) convertView.findViewById(R.id.examplePic);
        ImageButton delete = (ImageButton) convertView.findViewById(R.id.delete);
        ImageButton edit = (ImageButton) convertView.findViewById(R.id.edit);

	// set delete butten
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = activity.getApplicationContext();
                String strTag = (String) v.getTag();
                Toast.makeText(context, strTag, Toast.LENGTH_SHORT).show();
		// find the choice to delete
                Choice ChioceToDelete = null;
                for (Choice choice: objectList) {
                    if (choice.getName().equals(strTag)) ChioceToDelete = choice;
                }
                if (ChioceToDelete==null) return;
                objectList.remove(ChioceToDelete);
                baseAdapter.notifyDataSetChanged();
                Data.removeChoice(decistionName, ChioceToDelete);

            }
        });
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strTapg = (String) v.getTag();
                Choice edit = null;
		// find the choice to edit
                for (Choice choice: objectList) {
                    if (choice.getName().equals(strTapg)) edit = choice;
                }
                if (edit==null) return;
		// start intent whom choose picture from album
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                activity.startActivityForResult(intent, edit.getId());

            }
        });
        // set the example pic if there is
        Bitmap bitmap = choice.getFirstPic();
        if (bitmap !=null) {
	    //set scaled picture
            int height = examplePic.getHeight();
            int width = examplePic.getWidth();
            Bitmap scaledBitMap = Bitmap.createScaledBitmap(bitmap, 50,50, true);
            examplePic.setImageBitmap(scaledBitMap);
        }



	// attach date to fields
        name.setText(choice.getName());
        if (choice.getData() != null) data.setText(choice.getData());
        delete.setTag(choice.getName());
        edit.setTag(choice.getName());
        layout.setTag(choice.getName());


	// if user click on choice layout he will see the picture in big (over all screen)
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = activity.getFragmentManager();
		
		// find choice to show picture of
                String strTags = (String) v.getTag();
                Choice show = null;
                for (Choice choice: objectList) {
                    if (choice.getName().equals(strTags)) show = choice;
                }
                if (show==null) return;
                if (show.getNumOfPic() < 1) return;

		// create imageFragment and insert the picture list to it
                ImagesFragment imageFragment = new ImagesFragment();
                imageFragment.setBitmapList(show.getPicList());
                FragmentTransaction ft = fm.beginTransaction();
                ft.add(R.id.ImageFragment, imageFragment);
                ft.addToBackStack("images");
                ft.commit();
            }
        });
        return convertView;
    }




}
