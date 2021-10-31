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
    int counter=0;

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

        String[] DataLists = content.split(" ");
        for (int ii=0;ii<DataLists.length;ii++){

        }

        for (int i = 0; i< DataLists.length; i++) {

            if (counter==0) {
                tList.add(Double.valueOf(DataLists[i]));
            }
            if (counter==1) {
                xList.add(Double.valueOf(DataLists[i]));
            }
            if (counter==2) {
                yList.add(Double.valueOf(DataLists[i]));
            }
            if (counter==3) {
                zList.add(Double.valueOf(DataLists[i]));
            }
            if (counter==4) {
                mList.add(Double.valueOf(DataLists[i]));
                counter=-1;
            }
            counter++;
        }
      }
    }

