package sk.stuba.fei.uim.oop;

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
        graph.draw(new Line2D.Double(graphOffSet, height/2,width-graphOffSet, height/2)); //X axis
        graph.draw(new Line2D.Double(width/2, graphOffSet, width/2, height-graphOffSet)); //Y axis


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
        graph.setPaint(Color.GREEN);
        drawAxisScale(graph,yListMax,width,height,graphScaleY,true);
        drawAxisScale(graph,xListMax,width,height,graphScaleX,false);

        for (double i=-xListMax;i<xListMax;i=i+0.01){
            graph.fill(new Ellipse2D.Double(width/2+i*graphScaleX, height/2-(i*i)*graphScaleY, 1, 1));

        }
        // set points to the graph
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

    private void drawAxisScale(Graphics2D graph, double endOfAxis, double width, double height, double axisScale, boolean isY){
        int scaleIncreaser=10;
        double[] listOfScale =new double[10];
        fillListOfScale(listOfScale);

        while (true){
            if (endOfAxis < 0.01*scaleIncreaser) {
                setAxisUpToScale(listOfScale,scaleIncreaser);
                break;

            }

       scaleIncreaser=scaleIncreaser+10;
        }
        graph.setPaint(Color.BLACK);
        for (int i = 0; i< listOfScale.length; i++){
            if (listOfScale[i]> endOfAxis){
                if (isY==true){
                graph.draw(new Line2D.Double(width/2-2, height/2- endOfAxis * axisScale,width/2+2, height/2- endOfAxis * axisScale));
                graph.draw(new Line2D.Double(width/2-2, height/2+ endOfAxis * axisScale,width/2+2, height/2+ endOfAxis * axisScale));

                    for (int j=1;j<=9;j++){
                        graph.draw(new Line2D.Double(width / 2 - 1, height / 2 - (endOfAxis-(listOfScale[0]/10)*j) * axisScale, width / 2 + 1, height / 2 - (endOfAxis-(listOfScale[0]/10)*j) * axisScale));
                        graph.draw(new Line2D.Double(width / 2 - 1, height / 2 + (endOfAxis-(listOfScale[0]/10)*j) * axisScale, width / 2 + 1, height / 2 + (endOfAxis-(listOfScale[0]/10)*j) * axisScale));

                    }
                }
                else {
                    graph.draw(new Line2D.Double(width/2- endOfAxis * axisScale, height/2-2,width/2- endOfAxis * axisScale, height/2+2));
                    graph.draw(new Line2D.Double(width/2+ endOfAxis * axisScale, height/2-2,width/2+ endOfAxis * axisScale, height/2+2));

                    for (int jj=1;jj<=9;jj++){
                        graph.draw(new Line2D.Double( width / 2 -((endOfAxis-(listOfScale[0]/10)*jj) * axisScale), height/2-1, width/2 - (endOfAxis-(listOfScale[0]/10)*jj) * axisScale, height / 2 + 1));
                        graph.draw(new Line2D.Double( width / 2 +((endOfAxis-(listOfScale[0]/10)*jj) * axisScale), height/2-1, width/2 + (endOfAxis-(listOfScale[0]/10)*jj) * axisScale, height / 2 + 1));

                    }
                }
                break;
            }
            else {
                if (isY==true) {
                    graph.draw(new Line2D.Double(width / 2 - 2, height / 2 - listOfScale[i] * axisScale, width / 2 + 2, height / 2 - listOfScale[i] * axisScale));
                    graph.draw(new Line2D.Double(width / 2 - 2, height / 2 + listOfScale[i] * axisScale, width / 2 + 2, height / 2 + listOfScale[i] * axisScale));
                    for (int ii=1;ii<=9;ii++){
                        graph.draw(new Line2D.Double(width / 2 - 1, height / 2 - (listOfScale[i]-(listOfScale[0]/10)*ii) * axisScale, width / 2 + 1, height / 2 - (listOfScale[i]-(listOfScale[0]/10)*ii) * axisScale));
                        graph.draw(new Line2D.Double(width / 2 - 1, height / 2 + (listOfScale[i]-(listOfScale[0]/10)*ii) * axisScale, width / 2 + 1, height / 2 + (listOfScale[i]-(listOfScale[0]/10)*ii) * axisScale));

                    }
                }
                else {
                    graph.draw(new Line2D.Double(width/2- listOfScale[i]* axisScale, height/2-2,width/2- listOfScale[i]* axisScale, height/2+2));
                    graph.draw(new Line2D.Double(width/2+ listOfScale[i]* axisScale, height/2-2,width/2+ listOfScale[i]* axisScale, height/2+2));
                    for (int iii=1;iii<=9;iii++){
                        graph.draw(new Line2D.Double( width / 2 -((listOfScale[i]-(listOfScale[0]/10)*iii) * axisScale), height/2-1, width/2 - (listOfScale[i]-(listOfScale[0]/10)*iii) * axisScale, height / 2 + 1));
                        graph.draw(new Line2D.Double( width / 2 +((listOfScale[i]-(listOfScale[0]/10)*iii) * axisScale), height/2-1, width/2 + (listOfScale[i]-(listOfScale[0]/10)*iii) * axisScale, height / 2 + 1));

                    }

                }
            }
        }



    }


    private void createFrame(MyAttemptAtPlotting myAttemptAtPlotting){
        //create an instance of JFrame class
        JFrame frame = new JFrame();
        // set size, layout and location for frame.
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(myAttemptAtPlotting);
        frame.setSize(1500, 1000);
        frame.setLocation(200, 200);
        frame.setVisible(true);
    }
    private void fillListOfScale(double[] listOfYScale){
        for (int i=0;i<listOfYScale.length;){
        listOfYScale[i]=0.001*++i;
        }

    }
    private void setAxisUpToScale(double[] listOfYScale, int scaleIncreaser){
        for (int i=0;i<listOfYScale.length;i++){
            listOfYScale[i]=listOfYScale[i]*scaleIncreaser;
        }

    }
}
