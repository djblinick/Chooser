package com.biu.ap2.winder.chooser.ChooseFragments;

import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.biu.ap2.winder.chooser.Choice;
import com.biu.ap2.winder.chooser.Adapters.ChoiceAdapter;
import com.biu.ap2.winder.chooser.R;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class NchooseKFragment extends chooseFragment {
    public static int N_OPTION = 3;
    public static int K_CHOOSE = 2;
    private static int extra = 50;

    ChoiceAdapter choiceAdapter;
    List<Choice> choiceList;
    ListView listView;

    // Constructor.
    public NchooseKFragment() {
	this.name = Integer.toString(N_OPTION) + "-choose-" + Integer.toString(K_CHOOSE);
        //this.name = "N-choose-K";
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nchoose_k, container, false);

        listView = (ListView) view.findViewById(R.id.choiceList);

        // Fill the list with N options.
        choiceList = new ArrayList<>();
        Iterator<Choice> chioceIterator = super.getIterInPlace();
        for (int i = 0; i < N_OPTION; i++) {
            if (chioceIterator.hasNext())
                choiceList.add(chioceIterator.next());
        }
        // If there are only K options, automatically choose the K options and move onward.
        if (choiceList.size() < K_CHOOSE) {
            ha.choose(map);
        }

        // Create and set the adapter.
        choiceAdapter = new ChoiceAdapter(getActivity(), choiceList);
        listView.setAdapter(choiceAdapter);
        choiceAdapter.notifyDataSetChanged();

        // Save option when there is a long click on the item.
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
                choiceAdapter.toggleSelection(position);
            }
        });

        // When user clicks the done button:
        Button done = (Button) view.findViewById(R.id.doneChooseK);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SparseBooleanArray choosenChoicesArray = choiceAdapter.getSelectedIds();
                int size = choosenChoicesArray.size();
                // Check that the user used K options.
                if (size > K_CHOOSE) {
                    String message = "Please choose only " + K_CHOOSE;
                    Toast.makeText(ha.getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                } else if (size < K_CHOOSE) {
                    String message = "Please choose " + (K_CHOOSE - size) + " more";
                    Toast.makeText(ha.getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                } else {
                    // If the user chose exactly K.
                    for (int i = 0; i < N_OPTION; i++) {
                        Choice choice = choiceList.get(i);
                        if (choosenChoicesArray.get(i)) {
                            int newRank = map.get(choice) + extra;
                            map.put(choice, newRank);
                        } else {
                            map.remove(choice);
                        }
                    }
                    // increment as number of selected and move to next step
                    ha.advance += K_CHOOSE;
                    ha.choose(map);
                }
            }
        });
        return view;
    }
}
