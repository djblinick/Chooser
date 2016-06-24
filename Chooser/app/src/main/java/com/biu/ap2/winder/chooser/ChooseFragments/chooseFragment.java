package com.biu.ap2.winder.chooser.ChooseFragments;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.biu.ap2.winder.chooser.Choice;
import com.biu.ap2.winder.chooser.HelperActivity;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public abstract class chooseFragment extends Fragment {
    public String name;
    protected Map<Choice, Integer> map;
    protected HelperActivity ha;

    public chooseFragment() {
        // Required empty public constructor
    }

    @Override
    abstract public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState);

    //set map and activity as member of this class
    private void setMap(Map<Choice, Integer> map) {this.map = map;}
    private void setActivity(HelperActivity act) {this.ha = act;}
    public void preSet(HelperActivity act, Map<Choice, Integer> map) {
        setActivity(act);
        setMap(map);
    }

    //get the iterator that can go over all the choice
    // we also move the iterator to the right place according the advance we
    // made which is save in the helper activity
    protected Iterator<Choice> getIterInPlace() {
        if (ha.advance >= map.size()) ha.advance = 0;
        Set<Choice> chioceSet = map.keySet();
        Iterator<Choice> choiceIterator = chioceSet.iterator();
        for (int i = 0; i < ha.advance; i++) choiceIterator.next();
        return choiceIterator;
    }

    //get the iterator that can go over all the choice
    protected Iterator<Choice> getIterInFirstPlace() {
        if (ha.advance >= map.size()) ha.advance = 0;
        Set<Choice> chioceSet = map.keySet();
        return chioceSet.iterator();
    }

    //retrive the first choice from given map
    public static Choice getFirst(Map<Choice, Integer> map) {
        Set<Choice> chioceSet = map.keySet();
        Iterator<Choice> choiceIterator = chioceSet.iterator();
        Choice c = choiceIterator.next();
        return c;
    }

    // get the maximum number from collection of integers
    private static int getMax(Collection<Integer> collectionInteger) {
        if (collectionInteger == null || collectionInteger.isEmpty()) return 0;
        Iterator<Integer> iter = collectionInteger.iterator();
        int max = iter.next();
        while (iter.hasNext()) {
            int current = iter.next();
            if (current > max) max = current;
        }
        return max;
    }

    //get a map of choices and rank and return new map with only the element whom
    //  had the max rank - that map is continue to next stage
    public static Map<Choice, Integer> reduceNonMaximum(Map<Choice, Integer> map) {
        Collection<Integer> allValues = map.values();
        int max = getMax(allValues);
        Map<Choice, Integer> newMap = new HashMap<>();

        Set<Choice> choiceSet = map.keySet();
        Iterator<Choice> choiceIterator = choiceSet.iterator();
        while (choiceIterator.hasNext()) {
            Choice current = choiceIterator.next();
            int currentScore = map.get(current);
            if (currentScore == max) newMap.put(current, currentScore);
        }
        return newMap;
    }

}
