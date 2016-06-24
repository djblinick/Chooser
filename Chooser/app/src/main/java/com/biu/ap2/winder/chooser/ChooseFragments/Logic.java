package com.biu.ap2.winder.chooser.ChooseFragments;

import java.util.ArrayList;
import java.util.List;

/**this class if to swich logic sequnces
 * Created by winder on 1/11/16.
 */
public class Logic {
    // number of choices in this round
    private int numOfChoice;

    //constractor with number of choices
    public Logic(int n) {
        numOfChoice = n;
    }
    //get the declare default chooseFragment
    public chooseFragment getDefault() {
        return new TournamentFragment();
    }

    // set all choose steps to turnaments
    // set n-1 fragment as fix number of steps (math proven)
    public List<chooseFragment> allTurnaments() {
        List<chooseFragment> logic = new ArrayList<>();
        for (int i = 1; i < numOfChoice; i++)
            logic.add(new TournamentFragment());
        return logic;
    }

    // set all choose steps to StandAlone
    // set n fragment as fix number of steps
    public List<chooseFragment> allStandAlone() {
        List<chooseFragment> logic = new ArrayList<>();
        for (int i = 0; i < numOfChoice; i++)
            logic.add(new StandAloneFragment());
        return logic;
    }


    // set all choose steps to rank alone
    // set n fragment as fix number of steps
    public List<chooseFragment> allRankAlone() {
        List<chooseFragment> logic = new ArrayList<>();
        for (int i = 0; i < numOfChoice; i++)
            logic.add(new RankAloneFragment());
        return logic;
    }

    // set first round of steps as stand alone and second round as rank alone
    // set 2*n fragment as max number of steps
    public List<chooseFragment> firstStandAloneThenRankAlone() {
        List<chooseFragment> logic = new ArrayList<>();
        for (int i = 0; i < numOfChoice; i++)
            logic.add(new StandAloneFragment());
        for (int i = 0; i < numOfChoice; i++)
            logic.add(new RankAloneFragment());
        return logic;
    }

    // set all choose steps to N-choose-K
    // set n fragment as fix number of steps
    public List<chooseFragment> allNchooseK() {
        List<chooseFragment> logic = new ArrayList<>();
        for (int i = 0; i < numOfChoice-1; i++)
            logic.add(new NchooseKFragment());
        return logic;
    }


}
