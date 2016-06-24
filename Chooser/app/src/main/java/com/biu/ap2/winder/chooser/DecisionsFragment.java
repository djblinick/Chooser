package com.biu.ap2.winder.chooser;

import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import java.util.List;

public class DecisionsFragment extends Fragment {

    DecisionAdaptor decisionAdapter;
    ListView listdecision;
    List<Decision> decisions;

    public DecisionsFragment() {}

    public List<Decision> getDecisionsList() {
        return this.decisions;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_decisions, container, false);
        listdecision = (ListView) view.findViewById(R.id.lstDecisions);

        // getting the decision list from Splash where it was loaded
        decisions = Data.decisionList;

        // need to move it to the service
        for (Decision d:decisions) {
            this.setListener(d);
        }

        //insert all friend to adatper so we could show them
        listdecision.setEmptyView(view.findViewById(R.id.empty));
        decisionAdapter = new DecisionAdaptor(getActivity(), decisions);
        listdecision.setAdapter(decisionAdapter);
        decisionAdapter.notifyDataSetChanged();

        return view;
    }

    private void setListener(Decision d) {
        d.setLisener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), DilemmaActivity.class);
                TextView n = (TextView) v.findViewById(R.id.name);
                String s = n.getText().toString();
                i.putExtra("view", s);
                startActivity(i);
            }
        });
    }

    @Override
    public void onResume(){
        super.onResume();
        for (Decision d:decisions) {
            this.setListener(d);
        }
        decisionAdapter.notifyDataSetChanged();
        // put your code here...
    }

}
