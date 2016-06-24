package com.biu.ap2.winder.chooser;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

/**this class will handle all the method that word on app data
 * mainly load and save
 * this class will be decleare as static when the app starting
 * Created by winder on 12/2/15.
 */
public class Data {
    static List<Decision> decisionList = new ArrayList<>();
    static Context c;

    public Data(Context appContext) {c = appContext;}

    // file name for the decision list
    private final static String DecisionFileName = "Decisions.txt";

    // load all data
    // will be called from splash activity
    public static boolean loadData() {
        decisionList = loadDecision(DecisionFileName);
        // get all choices
        for (Decision decision: decisionList) {
            loadChoices(decision);
        }
        return true;
    }
    // delete all data in the app memory
    public boolean deleteAllData() {
        deleteAllFileWithPrefix(DecisionFileName);
        for (Decision decision: decisionList) {
            deleteAllFileWithPrefix(decision.getName());
        }
        return true;
    }

    // load all choices of given decision
    public static boolean loadChoices(Decision decision) {
        String decisionName = decision.getName();
        List<Choice> choiceList = decision.getCl();
        for (Choice choice: choiceList) {
            //for every choice in this decision;
            String choiceName = choice.getName();
            int picNum = choice.getNumOfPic();
            List<Bitmap> bitMapList = new ArrayList<>();
            // load all picture and add them to bitMapList
            for (int i = 1; i <= picNum; i++) {
                String path = decisionName + "@" + choiceName + "@" + Integer.toString(i);
                Bitmap bitmap = loadBitmap(path);
                bitMapList.add(bitmap);
            }
            // set all picture for this choice in it
            choice.setPl(bitMapList);
        }
        return true;
    }

    //save all decision from memory
    public boolean saveData() {
        saveDecision(decisionList, DecisionFileName);
        return true;
    }

    // add choice to decision according given name
    public static boolean addChoice(String decisionName, Choice choice) {
        Decision decision = getD(decisionName);
        if (decision == null) return false;
        decision.addChoice(choice);
        return true;
    }

    // inner class for save and load data
    public static class MyThread implements Runnable {
        Decision d;
        Choice c;
        Bitmap bm;
        boolean b;  // save = True, load = false
        public MyThread(Decision d1, Choice c1, Bitmap bm1, boolean b1) {
            d = d1; c = c1; bm = bm1; b = b1;
        }
        public void run() {
            if (b) saveChPic(d, c, bm);
            //else loadChPic(d, c, bm);
        }
    }
    // add picture to choice
    // args: decision name, choice name and the picture as bit-map
    public static boolean addPicToChoice(String decision, String choiseaame, Bitmap bm) {
        //find the right decision and choice
        Decision d = getD(decision);
        if (d == null) return false;
        List<Choice> cl = d.getCl();
        for (Choice c: cl) {
            if (c.getName().equals(choiseaame)) {
                //add the pictur and save it
                c.addPicture(bm);
                Runnable r = new MyThread(d, c,bm, true);
                new Thread(r).start();
                return true;
            }
        }
        return false;
    }
    //this method will be called from independent theard to save time
    private static void saveChPic(Decision d, Choice c, Bitmap bm) {
        int num = c.getNumOfPic();
        String path = d.getName() + "@" + c.getName() + "@" + Integer.toString(num);
        saveBitmap(bm, path);
    }

    // delete choice from decision and delete it from memory
    public static boolean removeChoice(String decision, Choice c) {
        Decision d = getD(decision);
        if (d == null) return false;
        deleteAllFileWithPrefix(d.getName() + "@" + c.getName());
        return (d.removeChoice(c));
    }

    // return the decision class by given name
    // if such name doesn't exist return null
    public static Decision getD(String name) {
        for (Decision d: decisionList) {
            if (d.getName().equals(name)) return d;
        }
        return null;
    }

    // deleting all file with given prefix
    private static void deleteAllFileWithPrefix(String prefix) {
        String[] allFiles = c.fileList();
        for (String s : allFiles) {
            if (s.startsWith(prefix))
                c.deleteFile(s);
        }
    }
    // load all decision from given file name
    // return list decisions instance
    private static List<Decision> loadDecision(String file) {
        File filename = new File(c.getFilesDir(), file);
        List<String> sl = null;
        try {
            if (!filename.exists()) {filename.createNewFile();}
            ObjectInputStream oips =
                    new ObjectInputStream(new FileInputStream(filename));
            try {
                // first get all the decisions as string
                sl = (List<String>) oips.readObject();
            } catch (ClassNotFoundException e) {e.printStackTrace();}
            if (oips != null) { // Exception might have happened at constructor
                oips.close();
            }
        } catch (Exception e) {e.printStackTrace();}
        if (sl == null) return new ArrayList<>();
        // convert all string to decision instance
        List<Decision> dl = new ArrayList<>();
        for (String s : sl) {dl.add(new Decision(s, true));}
        return dl;
    }

    // save all decision
    // args: list of decision to save and file name
    private void saveDecision(List<Decision> dl, String file) {
        // first' convert all decision to string
        List<String> sl = new ArrayList<>();
        for (Decision d : dl) {
            String s = d.toString();
            sl.add(s);
        }
        // open the file by given name
        File filename = new File(c.getFilesDir(), file);
        try {
            ObjectOutputStream oops = new ObjectOutputStream(
                    new BufferedOutputStream(
                            new FileOutputStream(filename)));
            // save the string list
            oops.writeObject(sl);
            if (oops != null) { // Exception might have happened at constructor
                oops.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //save picture as bit-map in given file
    // commpress the picture before saving it
    private static boolean saveBitmap(Bitmap image, String file) {
        try {
            FileOutputStream fos = c.openFileOutput(file, Context.MODE_PRIVATE);
            image.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.close();
            return true;
        } catch (Exception e) {return false;}
    }

    // load the picture from memory to bit-map type
    private static Bitmap loadBitmap(String file){
        Bitmap b = null;
        FileInputStream fis;
        try {
            fis = c.openFileInput(file);
            b = BitmapFactory.decodeStream(fis);
            fis.close();
        }
        catch (IOException e) {e.printStackTrace();}
        return b;
    }

}
