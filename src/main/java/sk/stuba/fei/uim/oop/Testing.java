package sk.stuba.fei.uim.oop;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class Testing {
    public static void main(String[] args)
    {
        ArrayList<Double> xList = new ArrayList<>();
        ArrayList<Double> yList = new ArrayList<>();
        ArrayList<Double> zList = new ArrayList<>();
        ArrayList<Double> mList = new ArrayList<>();
        ArrayList<Double> tList = new ArrayList<>();

        Path datafile = Paths.get("src/main/java/sk/stuba/fei/uim/oop/DataTest");
        Scanner scan = null;
        try {
            scan = new Scanner(datafile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        scan.useDelimiter("\\Z");
        String content = scan.next();
        System.out.println(content);

        String[] DataLists = content.split("\n");
        System.out.println();
        String[] variables = DataLists[0].split(" ");
        String[] fxList = DataLists[1].split(" ");
        String[] fyList = DataLists[2].split(" ");
        String[] fzList = DataLists[3].split(" ");
        String[] mxList = DataLists[4].split(" ");
        for (int i=0;true;i++){
            if (i<fxList.length){
                xList.add(Double.valueOf(fxList[i]));
            }
            if (i<fyList.length){
                yList.add(Double.valueOf(fyList[i]));
            }
            if (i<fzList.length){
                zList.add(Double.valueOf(fzList[i]));
            }


        }

    }
    }

