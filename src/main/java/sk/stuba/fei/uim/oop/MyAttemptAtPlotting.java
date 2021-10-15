package sk.stuba.fei.uim.oop;

import sk.stuba.fei.uim.oop.utility.KeyboardInput;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class MyAttemptAtPlotting extends JPanel implements KeyListener, ActionListener {

    JCheckBox fx;
    JCheckBox fy;
    JCheckBox fz;
    JCheckBox mx;
    JButton grid;
    boolean showGrid=false;
    boolean showFx=false;
    boolean showFy=false;
    boolean showFz=false;
    boolean showMx=false;
    /*ArrayList<Double> xList = new ArrayList<>();
    ArrayList<Double> yList = new ArrayList<>();
    ArrayList<Integer> zList = new ArrayList<>();
    ArrayList<Integer> mxList = new ArrayList<>();
    ArrayList<Integer> tList = new ArrayList<>();*/


    int graphOffSet=50;
    double zoom=1;
    boolean startProgram=false;
    MouseMovement mouseMovement;
    DrawAxis axisX;
    DrawAxis axisY;
    Testing testing;

    public MyAttemptAtPlotting()  {
        testing=new Testing();

       /* Path dataFilePath = Paths.get("src/main/java/sk/stuba/fei/uim/oop/DataFile.txt");
        try {
            Scanner scanner = new Scanner(dataFilePath);
            //List<Integer> integers = new ArrayList<>();
            while (scanner.hasNext()) {
                if (scanner.hasNextInt()) {
                    xList.add(scanner.nextDouble());
                } else {
                    scanner.next();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }*/


        //Y=x^2
        //WRITE DOESNT WORK YET
       /* for (int i = 0; i < xList.size(); i++) {
            yList.add(xList.get(i)*xList.get(i));
        }*/
        createFrame(this);
        mouseMovement=new MouseMovement(this);
        this.addMouseListener(mouseMovement);
        this.addMouseMotionListener(mouseMovement);
        this.addKeyListener(this);
    }

    protected void paintComponent(Graphics g){
        //create instance of the Graphics to use its methods
        super.paintComponent(g);
        Graphics2D graph = (Graphics2D)g;


        //Sets the value of a single preference for the rendering algorithms.
        graph.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // draw graph lines
        //calculates scale ratio between points and grap size
        double xListMax=Collections.max(testing.tList);
        /*System.out.println(testing.tList);
        if (Collections.max(testing.tList)<Math.abs(Collections.min(testing.tList))){
            xListMax=Math.abs(Collections.min(testing.xList));
        }
        else {
            xListMax=Collections.max(testing.xList);
        }*/
        double yListMax;
        double listMin=Math.abs(Collections.min(testing.xList));
        double listMax=Math.abs(Collections.max(testing.xList));
        if (Math.abs(Collections.min(testing.yList))<listMin){
            listMin=Math.abs(Collections.min(testing.yList));
        }
        if (Math.abs(Collections.min(testing.zList))<listMin){
            listMin=Math.abs(Collections.min(testing.zList));
        }
        if (Math.abs(Collections.min(testing.mList))<listMin){
            listMin=Math.abs(Collections.min(testing.mList));
        }
        if (Math.abs(Collections.max(testing.yList))>listMax){
            listMax=Math.abs(Collections.max(testing.yList));
        }
        if (Math.abs(Collections.max(testing.zList))>listMax){
            listMax=Math.abs(Collections.max(testing.zList));
        }
        if (Math.abs(Collections.max(testing.mList))>listMax){
            listMax=Math.abs(Collections.max(testing.mList));
        }

        if (listMax>Math.abs(listMin)){
            yListMax=listMax;
        }
        else {
            yListMax=Math.abs(listMin);
        }



       /* if (Collections.max(testing.yList)<Math.abs(Collections.min(testing.yList))){
            yListMax=Math.abs(Collections.min(testing.yList));
        }
        else {
            yListMax=Collections.max(testing.yList);
        }*/


        //zistujem scale jednotlivych osi aby sa zmestili na graf
        double graphScaleX= (double)(getWidth()-2*graphOffSet)/(double)(xListMax);
        double graphScaleY =(double)(getHeight()-2*graphOffSet)/(double)(yListMax);

        graphScaleY=graphScaleY/2;
        graphScaleX=graphScaleX/2;

        if  (startProgram==false){
        axisX=new DrawAxis(graph,yListMax,getWidth(),getHeight(),graphScaleY,true,graphOffSet,zoom,mouseMovement,xListMax);
        axisX.setShowGrid(showGrid);
        axisY=new DrawAxis(graph,xListMax,getWidth(),getHeight(),graphScaleX,false,graphOffSet,zoom,mouseMovement,yListMax);
        axisY.setShowGrid(showGrid);
        startProgram=true;
        }
        else {
            axisX.drawAxisScale(graph);
            axisY.drawAxisScale(graph);

        }
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        /*for (double i=-xListMax;i<xListMax;i=i+xListMax/10000){
            //Stred osi X.... ziska poziciu na osi X  (i), vynasoby scalom aby bod pasoval na graf, posunie bod o hodnotu ziskanu pri zoomovani... a zvacsi ho na pozadovanu zoomnutu hodnotu
            graph.fill(new Ellipse2D.Double((getWidth()/2+(i*graphScaleX)-mouseMovement.getCorrectionX())*(mouseMovement.getScalingX()), (getHeight()/2-(i*i)*graphScaleY-mouseMovement.getCorrectionY())*mouseMovement.getScalingY(), 1, 1));
                                                          //REALNY BOD                  //POsun                     //Scaling                                    //REALNY BOD                  //POsun                     //Scaling
        }*/
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


        graph.setPaint(Color.RED);
        for(int i=0; i<testing.xList.size(); i++){
            double drawX = (getWidth()/2+(testing.tList.get(i)*graphScaleX)-mouseMovement.getCorrectionX())*mouseMovement.getScalingX();
            double drawY = (getHeight()/2-(testing.xList.get(i)*graphScaleY)-mouseMovement.getCorrectionY())*mouseMovement.getScalingY();
            graph.fill(new Ellipse2D.Double(drawX, drawY, 4, 4));
        }
        graph.setPaint(Color.GREEN);
        for(int i=0; i<testing.yList.size(); i++){
            double drawX = (getWidth()/2+(testing.tList.get(i)*graphScaleX)-mouseMovement.getCorrectionX())*mouseMovement.getScalingX();
            double drawY = (getHeight()/2-(testing.yList.get(i)*graphScaleY)-mouseMovement.getCorrectionY())*mouseMovement.getScalingY();
            graph.fill(new Ellipse2D.Double(drawX, drawY, 4, 4));
        }
        graph.setPaint(Color.BLUE);
        for(int i=0; i<testing.zList.size(); i++){
            double drawX = (getWidth()/2+(testing.tList.get(i)*graphScaleX)-mouseMovement.getCorrectionX())*mouseMovement.getScalingX();
            double drawY = (getHeight()/2-(testing.zList.get(i)*graphScaleY)-mouseMovement.getCorrectionY())*mouseMovement.getScalingY();
            graph.fill(new Ellipse2D.Double(drawX, drawY, 4, 4));
        }
        graph.setPaint(Color.ORANGE);
            for(int i=0; i<testing.mList.size(); i++){
            double drawX = (getWidth()/2+(testing.tList.get(i)*graphScaleX)-mouseMovement.getCorrectionX())*mouseMovement.getScalingX();
            double drawY = (getHeight()/2-(testing.mList.get(i)*graphScaleY)-mouseMovement.getCorrectionY())*mouseMovement.getScalingY();
            graph.fill(new Ellipse2D.Double(drawX, drawY, 4, 4));
        }


        graph.setPaint(Color.GRAY);
        int rectangleCoordX;
        int rectangleCoordY;
        if (mouseMovement.getRectStartX()<mouseMovement.getRectEndX()){
            rectangleCoordX=mouseMovement.getRectStartX();
        }
        else {
            rectangleCoordX=mouseMovement.getRectEndX();
        }
        if (mouseMovement.getRectStartY()<mouseMovement.getRectEndY()){
            rectangleCoordY=mouseMovement.getRectStartY();
        }
        else {
            rectangleCoordY=mouseMovement.getRectEndY();
        }
        graph.drawRect(rectangleCoordX,rectangleCoordY, Math.abs(mouseMovement.getRectEndX()-mouseMovement.getRectStartX()),Math.abs(mouseMovement.getRectEndY()-mouseMovement.getRectStartY()));





        //   repaint();
    }


    private void createFrame(MyAttemptAtPlotting myAttemptAtPlotting){
        //create an instance of JFrame class
        JFrame frame = new JFrame();
        // set size, layout and location for frame.
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setSize(700, 500);
        frame.setLocation(200, 200);

        frame.add("Center",myAttemptAtPlotting);

        JPanel controlPanel= new JPanel();
        controlPanel.setSize(700,100);
        grid = new JButton("GRID");
        grid.setFocusable(false);
        controlPanel.setLayout(new GridLayout(1,4));
        controlPanel.add(grid);

        fx= new JCheckBox("Fx[N]");
        fx.setFocusable(false);
        controlPanel.add(fx);
        fx.addActionListener(this);

        fy= new JCheckBox("Fy[N]");
        fy.setFocusable(false);
        controlPanel.add(fy);
        fy.addActionListener(this);

        fz= new JCheckBox("Fz[N]");
        fz.setFocusable(false);
        controlPanel.add(fz);
        fz.addActionListener(this);

        mx= new JCheckBox("Mx[N]");
        mx.setFocusable(false);
        controlPanel.add(mx);
        mx.addActionListener(this);

        grid.addActionListener(this);
        frame.add("North",controlPanel);
        frame.setVisible(true);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode()==KeyEvent.VK_ESCAPE){
            mouseMovement.setCorrectionX(0);
            mouseMovement.setCorrectionY(0);
            mouseMovement.setScalingX(1);
            mouseMovement.setScalingY(1);
            repaint();

        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(grid)){
            System.out.println("clicked >|");
            showGrid=!showGrid;
            axisY.setShowGrid(showGrid);
            axisX.setShowGrid(showGrid);
            repaint();
        }
        if (fx.isSelected()){
            System.out.println("fx   IS ON");
            showFx=true;
        }
        else {
            System.out.println("fx   IS OFF");
            showFx=false;
        }
        if (fy.isSelected()){
            System.out.println("fy   IS ON");
            showFy=true;
        }
        else {
            System.out.println("fy   IS OFF");
            showFy=false;
        }
        if (fz.isSelected()){
            System.out.println("fz   IS ON");
            showFz=true;
        }
        else {
            System.out.println("fz   IS OFF");
            showFz=false;
        }
        if (mx.isSelected()){
            System.out.println("mx   IS ON");
            showMx=true;
        }
        else {
            System.out.println("mx   IS OFF");
            showMx=false;
        }

    }
}
