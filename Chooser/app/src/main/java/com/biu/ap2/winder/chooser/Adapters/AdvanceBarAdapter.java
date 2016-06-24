package com.biu.ap2.winder.chooser.Adapters;

        import android.app.Activity;
        import android.content.Context;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.BaseAdapter;
        import android.widget.TextView;

        import com.biu.ap2.winder.chooser.R;

        import java.util.List;

/**
 * Created by winder on 11/17/15.
 */
public class AdvanceBarAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    //list of friends to show
    private List<Integer> steps;


    public AdvanceBarAdapter(Activity activity, List<Integer> listOfStep) {
        this.activity = activity;
        this.steps = listOfStep;
    }

    // those override methods as adaptor
    @Override
    public int getCount() {
        return steps.size();
    }

    @Override
    public Object getItem(int location) {
        return steps.get(location);
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
            convertView = inflater.inflate(R.layout.list_item_advance, null);

        //attach field to variables
        TextView stepNumber = (TextView) convertView.findViewById(R.id.stepNumber);

        //get all data from friend and set the view accordinly
        int num = steps.get(position);
        String text = String.valueOf(num);
        stepNumber.setText(text);

        return convertView;
    }




}