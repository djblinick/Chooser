package com.biu.ap2.winder.chooser;

import android.graphics.Bitmap;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**this class represent decision from user
 * it's include name and list of choices
 * Created by winder on 11/17/15.
 */
public class Decision {
    private String name;
    View.OnClickListener listener;
    private List<Choice> cl = new ArrayList<>();

    private final static String delimiter = "##";


    public Decision(String n, View.OnClickListener l) {
        name = n;
        listener = l;
    }
    public Decision(String n) {
        name = n;
        listener = null;
    }
    //this metod is to reconstruct choice from string
    // the boolean is to distiguish between constructor
    public Decision(String s, boolean b) {
        //break string into parts with given delimiter
        // extract all data from the string
        String[] a = s.split("\\#\\#");
        name = a[0];
        // get all choices
        List<Choice> l = new ArrayList<>();
        for (int i = 1; i < a.length; i++) {
            Choice c = new Choice(a[i]);
            l.add(c);
        }
        cl = l;
    }
    // getter and setter
    public View.OnClickListener getListener() {
        return this.listener;
    }
    public void setLisener(View.OnClickListener l) {
        this.listener = l;
    }
    public void addChoice(Choice c) {this.cl.add(c);}
    public boolean removeChoice(Choice c) {
        if (cl.contains(c)) {
            this.cl.remove(c); return true;
        } else return false;
    }
    public String getName() {
        return this.name;
    }
    public List<Choice> getCl() {return cl;}
    public void setCl(List<Choice> c) {cl =c;}

    //flaten the decision to string in order to save it
    @Override
    public String toString() {
        String s = this.name + delimiter;
        for (Choice c: cl) {
            s += c.toString() + delimiter;
        }
        return s;
    }

}
