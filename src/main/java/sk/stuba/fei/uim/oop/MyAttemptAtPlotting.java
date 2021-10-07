package sk.stuba.fei.uim.oop;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class MyAttemptAtPlotting extends JPanel {


    ArrayList<Integer> xList = new ArrayList<>();
    ArrayList<Integer> yList = new ArrayList<>();
    int[] Xcords = {0, 1, 5, 10, 15, 25, 28, 32, 50, 80, 85, -1, -5, -10, -15, -25, -28, -32, -50, -80, -85,};
    int graphOffSet=50;

    public MyAttemptAtPlotting() {
        for (int i = 0; i < Xcords.length; i++) {
            xList.add(Xcords[i]);
        }
        //Y=x^2
        for (int i = 0; i < xList.size(); i++) {
            yList.add(xList.get(i)*xList.get(i));
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

        //find value of x and scale to plot points
       // double x = (double)(width-2*marg)/(cord.length-1);
       // double scale = (double)(height-2*marg)/getMax();

        //set color for points
        graph.setPaint(Color.RED);


        //0 at height/2 width/2
        // set points to the graph

        double graphScaleX= (double)(width-2*graphOffSet)/(double)(Collections.max(xList)-Collections.min(xList));
        graphScaleX=graphScaleX/2;
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
