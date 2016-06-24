package com.biu.ap2.winder.chooser;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import android.os.Bundle;
import android.app.Fragment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.biu.ap2.winder.chooser.Adapters.ObjAdapter;

import java.util.ArrayList;
import java.util.List;


public class ObjFragment extends Fragment {


    ObjAdapter objAdapter;
    ListView lstobj;
    List<Choice> objs;
    SwipeRefreshLayout swipeLayout;


    public ObjFragment() {

    }

    public List<Choice> getDecisionsList() {
        return this.objs;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_obj, container, false);
        lstobj = (ListView) view.findViewById(R.id.lstObjs);

        String name = null;
        Bundle extras = getActivity().getIntent().getExtras();
        if (extras != null) {
            name = getActivity().getIntent().getExtras().getString("view");
        }
        objs = null;
        for (Decision d: Data.decisionList) {
            if (d.getName().equals(name))
                objs = d.getCl();
        }
        // TODO check if need to get the decisions list from memory

        // need to move it to the service
        if (objs != null)
            for (Choice o : objs) {
                this.setListener(o);
            }

        lstobj.setEmptyView(view.findViewById(R.id.empty));
        //insert all friend to adatper so we could show them
        objAdapter = new ObjAdapter(getActivity(), objs,name);
        lstobj.setAdapter(objAdapter);
        objAdapter.notifyDataSetChanged();


        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ReloadService.DONE);
        getActivity().registerReceiver(reloadDone, intentFilter);

        return view;
        /*
        //swipe part
        swipeLayout = (SwipeRefreshLayout) view.findViewById(R.id.feed_swipeLayout);
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Intent intent = new Intent(getActivity(), ReloadService.class);
                getActivity().startService(intent);
            }
        });
        swipeLayout.setColorScheme(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ReloadService.DONE);
        getActivity().registerReceiver(reloadDone, intentFilter);
        */



    }

/*
    private BroadcastReceiver reloadDone = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            swipeLayout.setRefreshing(false);
            Toast.makeText(getActivity(), "Reload is done", Toast.LENGTH_SHORT).show();
        }
    };

*/

    public void addDecision(Choice o) {
        if (objs == null) {
            objs = new ArrayList<>();
        }
        objs.add(o);
        if (objAdapter != null) {
            objAdapter.notifyDataSetChanged();
        }

    }

    private void setListener(Choice o) {
        o.setLisener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO see all pictue and rank
            }
        });

    }

    private BroadcastReceiver reloadDone = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            String name = null;
            Bundle extras = getActivity().getIntent().getExtras();
            if (extras != null) {
                name = getActivity().getIntent().getExtras().getString("view");
            }

            for (Decision d: Data.decisionList) {
                if (d.getName().equals(name))
                    objs = d.getCl();
            }
            objAdapter.notifyDataSetChanged();
        }
    };


}
