package com.biu.ap2.winder.chooser;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by winder on 11/17/15.
 */
public class DecisionAdaptor extends BaseAdapter{
    private Activity activity;
    private LayoutInflater inflater;
    //list of friends to show
    private List<Decision> decisions;
    private BaseAdapter BA;


    public DecisionAdaptor(Activity activity, List<Decision> dl) {
        this.activity = activity;
        this.decisions = dl;
        BA = this;
    }
    // those override methods as adaptor
    @Override
    public int getCount() {
        return decisions.size();
    }

    @Override
    public Object getItem(int location) {
        return decisions.get(location);
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
            convertView = inflater.inflate(R.layout.list_item_decision, null);

        //attach field to variables
        TextView name = (TextView) convertView.findViewById(R.id.name);
        LinearLayout layout = (LinearLayout) convertView.findViewById(R.id.decision);

        //get all data from friend and set the view accordinly
        Decision d = decisions.get(position);
        ImageView examplePic = (ImageView) convertView.findViewById(R.id.examplePic);


        ImageButton dele = (ImageButton) convertView.findViewById(R.id.delete);
        dele.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context c = activity.getApplicationContext();
                //TextView n = (TextView) v.findViewById(R.id.decision_to_delete);
                String s = (String) v.getTag();
                //Toast.makeText(c, s, Toast.LENGTH_SHORT).show();
                Decision del = null;
                for (Decision d: decisions) {
                    if (d.getName().equals(s)) del = d;
                }
                if (del==null) return;
                decisions.remove(del);
                Data.decisionList.remove(del);
                Toast.makeText(c, del.getName(), Toast.LENGTH_SHORT).show();
                BA.notifyDataSetChanged();
            }
        });
        // TODO insert a random pic into imgProfile
        //examplePic.setImageBitmap(decodedByte);

        name.setText(d.getName());
        dele.setTag(d.getName());
        layout.setOnClickListener(d.getListener());

        return convertView;
    }


}
