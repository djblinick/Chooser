package com.biu.ap2.winder.chooser;

import android.graphics.Bitmap;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**this class for one choice in decision
 * Created by winder on 11/20/15.
 */
public class Choice {
    //all member are private
    private int id;
    private int numOfPic;
    private String name = null;
    private String data = null;
    private List<Bitmap> pictureList = new ArrayList<>();
    View.OnClickListener listener;

    // delimiter for save the choice as string (serializable)
    private final static String delimiter = "$";
    public Choice(int i, String n) {
        id = i;
        name = n;
    }

    public Choice(int i, String n, String d) {
        id = i;
        name = n;
        data = d;
    }
    //this method is to reconstruct choice from string
    public Choice(String str) {
        //break string into parts with given delimiter
        // extract all data from the string
        String[] splitArray = str.split("\\$");
        id = Integer.parseInt(splitArray[0]);
        name = splitArray[1];
        data = splitArray[2];
        numOfPic = Integer.parseInt(splitArray[3]);

    }

    //getter and setter
    public int getId() {return id;}
    public String getName() {return name;}
    public String getData() {return data;}
    public int getNumOfPic() {return numOfPic;}
    public void setLisener(View.OnClickListener l) {
        this.listener = l;
    }
    public void setPl(List<Bitmap> pl) {pictureList=pl;numOfPic = pictureList.size();}
    public void addPicture(Bitmap picture) {pictureList.add(picture); numOfPic = pictureList.size();}
    public List<Bitmap> getPicList() {return pictureList;}

    // return first picture of this choice
    public Bitmap getFirstPic() {
        if (pictureList.size() > 0) return pictureList.get(0);
        return null;
    }

    //flaten the choice to string in order to save it
    @Override
    public String toString() {
        String str = Integer.toString(this.id);
        str += delimiter;
        str += this.name + delimiter + this.data + delimiter;
        str+= Integer.toString(numOfPic) + delimiter;
        return str;
    }
}
