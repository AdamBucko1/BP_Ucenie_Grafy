package sk.stuba.fei.uim.oop;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Ellipse2D;
import java.util.Collections;

public class GraphPlotter extends JPanel implements KeyListener, ActionListener {

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
    int graphOffSet=50;
    double zoom=1;
    boolean startProgram=false;
    MouseControll mouseControll;
    AxisNGridBuilder axisX;
    AxisNGridBuilder axisY;
    DataHandler dataHandler;

    public GraphPlotter()  {
        dataHandler =new DataHandler();
        createFrame(this);
        mouseControll =new MouseControll(this);
        this.addMouseListener(mouseControll);
        this.addMouseMotionListener(mouseControll);
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
        double xListMax=Collections.max(dataHandler.tList);
        double yListMax;
        double listMin=Math.abs(Collections.min(dataHandler.xList));
        double listMax=Math.abs(Collections.max(dataHandler.xList));
        if (Math.abs(Collections.min(dataHandler.yList))<listMin){
            listMin=Math.abs(Collections.min(dataHandler.yList));
        }
        if (Math.abs(Collections.min(dataHandler.zList))<listMin){
            listMin=Math.abs(Collections.min(dataHandler.zList));
        }
        if (Math.abs(Collections.min(dataHandler.mList))<listMin){
            listMin=Math.abs(Collections.min(dataHandler.mList));
        }
        if (Math.abs(Collections.max(dataHandler.yList))>listMax){
            listMax=Math.abs(Collections.max(dataHandler.yList));
        }
        if (Math.abs(Collections.max(dataHandler.zList))>listMax){
            listMax=Math.abs(Collections.max(dataHandler.zList));
        }
        if (Math.abs(Collections.max(dataHandler.mList))>listMax){
            listMax=Math.abs(Collections.max(dataHandler.mList));
        }

        if (listMax>Math.abs(listMin)){
            yListMax=listMax;
        }
        else {
            yListMax=Math.abs(listMin);
        }
        //zistujem scale jednotlivych osi aby sa zmestili na graf
        double graphScaleX= (double)(getWidth()-2*graphOffSet)/(double)(xListMax);
        double graphScaleY =(double)(getHeight()-2*graphOffSet)/(double)(yListMax);

        graphScaleY=graphScaleY/2;
        graphScaleX=graphScaleX/2;

        if  (startProgram==false){
        axisX=new AxisNGridBuilder(graph,yListMax,getWidth(),getHeight(),graphScaleY,true,graphOffSet,zoom, mouseControll,xListMax);
        axisX.setShowGrid(showGrid);
        axisY=new AxisNGridBuilder(graph,xListMax,getWidth(),getHeight(),graphScaleX,false,graphOffSet,zoom, mouseControll,yListMax);
        axisY.setShowGrid(showGrid);
        startProgram=true;
        }
        else {
            axisX.drawAxisScale(graph);
            axisY.drawAxisScale(graph);

        }

        if (showFx==true) {
            graph.setPaint(Color.RED);
            for (int i = 0; i < dataHandler.xList.size(); i++) {
                double drawX = (getWidth() / 2 + (dataHandler.tList.get(i) * graphScaleX) - mouseControll.getCorrectionX()) * mouseControll.getScalingX();
                double drawY = (getHeight() / 2 - (dataHandler.xList.get(i) * graphScaleY) - mouseControll.getCorrectionY()) * mouseControll.getScalingY();
                graph.fill(new Ellipse2D.Double(drawX, drawY, 4, 4));
            }
        }
        if (showFy==true) {
            graph.setPaint(Color.GREEN);
            for (int i = 0; i < dataHandler.yList.size(); i++) {
                double drawX = (getWidth() / 2 + (dataHandler.tList.get(i) * graphScaleX) - mouseControll.getCorrectionX()) * mouseControll.getScalingX();
                double drawY = (getHeight() / 2 - (dataHandler.yList.get(i) * graphScaleY) - mouseControll.getCorrectionY()) * mouseControll.getScalingY();
                graph.fill(new Ellipse2D.Double(drawX, drawY, 4, 4));
            }
        }
        if (showFz==true) {
            graph.setPaint(Color.BLUE);
            for (int i = 0; i < dataHandler.zList.size(); i++) {
                double drawX = (getWidth() / 2 + (dataHandler.tList.get(i) * graphScaleX) - mouseControll.getCorrectionX()) * mouseControll.getScalingX();
                double drawY = (getHeight() / 2 - (dataHandler.zList.get(i) * graphScaleY) - mouseControll.getCorrectionY()) * mouseControll.getScalingY();
                graph.fill(new Ellipse2D.Double(drawX, drawY, 4, 4));
            }
        }
        if (showMx==true) {
            graph.setPaint(Color.ORANGE);
            for (int i = 0; i < dataHandler.mList.size(); i++) {
                double drawX = (getWidth() / 2 + (dataHandler.tList.get(i) * graphScaleX) - mouseControll.getCorrectionX()) * mouseControll.getScalingX();
                double drawY = (getHeight() / 2 - (dataHandler.mList.get(i) * graphScaleY) - mouseControll.getCorrectionY()) * mouseControll.getScalingY();
                graph.fill(new Ellipse2D.Double(drawX, drawY, 4, 4));
            }
        }

        graph.setPaint(Color.GRAY);
        int rectangleCoordX;
        int rectangleCoordY;
        if (mouseControll.getRectStartX()< mouseControll.getRectEndX()){
            rectangleCoordX= mouseControll.getRectStartX();
        }
        else {
            rectangleCoordX= mouseControll.getRectEndX();
        }
        if (mouseControll.getRectStartY()< mouseControll.getRectEndY()){
            rectangleCoordY= mouseControll.getRectStartY();
        }
        else {
            rectangleCoordY= mouseControll.getRectEndY();
        }
        graph.drawRect(rectangleCoordX,rectangleCoordY, Math.abs(mouseControll.getRectEndX()- mouseControll.getRectStartX()),Math.abs(mouseControll.getRectEndY()- mouseControll.getRectStartY()));

    }


    private void createFrame(GraphPlotter graphPlotter){
        //create an instance of JFrame class
        JFrame frame = new JFrame();
        // set size, layout and location for frame.
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setSize(700, 500);
        frame.setLocation(200, 200);

        frame.add("Center", graphPlotter);

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
            mouseControll.setCorrectionX(0);
            mouseControll.setCorrectionY(0);
            mouseControll.setScalingX(1);
            mouseControll.setScalingY(1);
            repaint();

        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(grid)){
            showGrid=!showGrid;
            axisY.setShowGrid(showGrid);
            axisX.setShowGrid(showGrid);
            repaint();
        }
        if (fx.isSelected()){
            showFx=true;
        }
        else {
            showFx=false;
        }
        if (fy.isSelected()){
            showFy=true;
        }
        else {
            showFy=false;
        }
        if (fz.isSelected()){
            showFz=true;
        }
        else {
            showFz=false;
        }
        if (mx.isSelected()){
            showMx=true;
        }
        else {
            showMx=false;
        }
        repaint();

    }
}
