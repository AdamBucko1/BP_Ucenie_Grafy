package sk.stuba.fei.uim.oop;

import lombok.SneakyThrows;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class MyAttemptAtPlotting extends JPanel {


    ArrayList<Integer> xList = new ArrayList<>();
    ArrayList<Integer> yList = new ArrayList<>();
    int graphOffSet=50;

    public MyAttemptAtPlotting()  {

        Path dataFilePath = Paths.get("src/main/java/sk/stuba/fei/uim/oop/DataFile.txt");
        FileWriter writer =null;


        try {
            writer= new FileWriter("src/main/java/sk/stuba/fei/uim/oop/OutfileFile.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }


        try {
            Scanner scanner = new Scanner(dataFilePath);
            //List<Integer> integers = new ArrayList<>();
            while (scanner.hasNext()) {
                if (scanner.hasNextInt()) {
                    xList.add(scanner.nextInt());
                } else {
                    scanner.next();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        //Y=x^2
        for (int i = 0; i < xList.size(); i++) {
            yList.add(xList.get(i)*xList.get(i));
            try {
                writer.write(yList.get(i));
                writer.write(" ");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        createFrame(this);
    }

    protected void paintComponent(Graphics g){
        //create instance of the Graphics to use its methods
        super.paintComponent(g);
        Graphics2D graph = (Graphics2D)g;


        //Sets the value of a single preference for the rendering algorithms.
        graph.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // get width and height
        int width = getWidth();
        int height = getHeight();

        // draw graph
        graph.draw(new Line2D.Double(graphOffSet, height/2,width-graphOffSet, height/2)); //X os
        graph.draw(new Line2D.Double(width/2, graphOffSet, width/2, height-graphOffSet)); //Y os

        //set color for points
        graph.setPaint(Color.RED);


        //0 at height/2 width/2
        // set points to the graph

        double graphScaleX= (double)(width-2*graphOffSet)/(double)(Collections.max(xList)-Collections.min(xList));
        double graphScaleY =(double)(height-2*graphOffSet)/(double)(Collections.max(yList)-Collections.min(yList));
        graphScaleY=graphScaleY/2;
        graph.setPaint(Color.GREEN);
        for (double i=-Collections.max(xList);i<Collections.max(xList);i=i+0.01){
            graph.fill(new Ellipse2D.Double(width/2+i*graphScaleX, height/2-(i*i)*graphScaleY, 1, 1));

        }
        //set color for points
        graph.setPaint(Color.RED);
        for(int i=0; i<xList.size(); i++){
            double drawX = width/2+xList.get(i)*graphScaleX;
            System.out.println(drawX);
            double drawY = height/2-yList.get(i)*graphScaleY;
            System.out.println(drawY);
            graph.fill(new Ellipse2D.Double(drawX, drawY, 4, 4));
        }
    }


    private void createFrame(MyAttemptAtPlotting myAttemptAtPlotting){
        //create an instance of JFrame class
        JFrame frame = new JFrame();
        // set size, layout and location for frame.
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(myAttemptAtPlotting);
        frame.setSize(750, 500);
        frame.setLocation(200, 200);
        frame.setVisible(true);
    }



}
