package sk.stuba.fei.uim.oop;

import sk.stuba.fei.uim.oop.utility.KeyboardInput;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class MyAttemptAtPlotting extends JPanel {


    ArrayList<Integer> xList = new ArrayList<>();
    ArrayList<Integer> yList = new ArrayList<>();
    int graphOffSet=50;
    double zoom=1.5;

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
        //WRITE DOESNT WORK YET
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

        // draw graph lines
        //calculates scale ratio between points and grap size
        double xListMax;
        if (Collections.max(xList)<Math.abs(Collections.min(xList))){
            xListMax=Math.abs(Collections.min(xList));
        }
        else {
            xListMax=Collections.max(xList);
        }
        double yListMax;
        if (Collections.max(yList)<Math.abs(Collections.min(yList))){
            yListMax=Math.abs(Collections.min(yList));
        }
        else {
            yListMax=Collections.max(yList);
        }

        double graphScaleX= (double)(width-2*graphOffSet)/(double)(xListMax);
        double graphScaleY =(double)(height-2*graphOffSet)/(double)(yListMax);

        graphScaleY=graphScaleY/2;
        graphScaleX=graphScaleX/2;

        new DrawAxis(graph,yListMax,width,height,graphScaleY,true,graphOffSet,zoom);
        new DrawAxis(graph,xListMax,width,height,graphScaleX,false,graphOffSet,zoom);

        for (double i=-xListMax;i<xListMax;i=i+xListMax/10000){
            graph.fill(new Ellipse2D.Double(width/2+((i*graphScaleX)*zoom), height/2-(((i*i)*graphScaleY)*zoom), 1, 1));

        }
        graph.setPaint(Color.RED);
        for(int i=0; i<xList.size(); i++){
            double drawX = width/2+(xList.get(i)*graphScaleX*zoom);
            System.out.println(drawX);
            double drawY = height/2-(yList.get(i)*graphScaleY*zoom);
            System.out.println(drawY);
            graph.fill(new Ellipse2D.Double(drawX, drawY, 4, 4));
        }
    }

    private void createFrame(MyAttemptAtPlotting myAttemptAtPlotting){
        //create an instance of JFrame class
        JFrame frame = new JFrame();
        // set size, layout and location for frame.
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.add("Center",myAttemptAtPlotting);
        frame.setSize(700, 500);
        frame.setLocation(200, 200);
        frame.setVisible(true);
    }
}
