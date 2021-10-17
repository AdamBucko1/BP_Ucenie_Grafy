package sk.stuba.fei.uim.oop;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class DataHandler {
    public ArrayList<Double> xList = new ArrayList<>();
    public ArrayList<Double> yList = new ArrayList<>();
    public ArrayList<Double> zList = new ArrayList<>();
    public ArrayList<Double> mList = new ArrayList<>();
    public ArrayList<Double> tList = new ArrayList<>();
    public ArrayList<String> variableList = new ArrayList<>();

    public DataHandler() {
        createArrayLists();
    }

    public void createArrayLists() {
        Path datafile = Paths.get("src/main/java/sk/stuba/fei/uim/oop/DataSheet");
        Scanner scan = null;
        try {
            scan = new Scanner(datafile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        scan.useDelimiter("\\Z");
        String content = scan.next();

        String[] DataLists = content.split("\n");
        String[] variables = DataLists[0].split(" ");
        String[] tsArray = DataLists[1].split(" ");
        String[] fxArray = DataLists[2].split(" ");
        String[] fyArray = DataLists[3].split(" ");
        String[] fzArray = DataLists[4].split(" ");
        String[] mxArray = DataLists[5].split(" ");
        for (int i = 0; true; i++) {
            if (i> fxArray.length&&i> fyArray.length&&i> fzArray.length&&i> mxArray.length&&i> tsArray.length){
                break;
            }
            if (i < fxArray.length) {
                xList.add(Double.valueOf(fxArray[i]));
            }
            if (i < fyArray.length) {
                yList.add(Double.valueOf(fyArray[i]));
            }
            if (i < fzArray.length) {
                zList.add(Double.valueOf(fzArray[i]));
            }
            if (i < mxArray.length) {
                mList.add(Double.valueOf(mxArray[i]));
            }
            if (i < tsArray.length) {
                tList.add(Double.valueOf(tsArray[i]));
            }
            if (i<variables.length){
                variableList.add(variables[i]);
            }
        }
      }
    }

