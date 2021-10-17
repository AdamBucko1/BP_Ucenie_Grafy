package sk.stuba.fei.uim.oop;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseControll extends MouseAdapter {

    private int startX=0;
    private int startY=0;

    private int endX=0;
    private int endY=0;
    private GraphPlotter graphPlotter;

    private double scalingX=1;
    private double scalingY=1;
    private int correctionX=0;
    private int correctionY=0;
    private int rectStartX=0;
    private int rectStartY=0;
    private int rectEndX=0;
    private int rectEndY=0;


    public MouseControll(GraphPlotter graphPlotter) {
        this.graphPlotter = graphPlotter;


    }

    @Override
    public void mousePressed(MouseEvent e) {
        startX=e.getX();
        startY=e.getY();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        endX = e.getX();
        endY = e.getY();
        rectStartX=startX;
        rectStartY=startY;
        rectEndX=endX;
        rectEndY=endY;
        graphPlotter.repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (endX<startX){
            correctionX=(int)(correctionX+(double)endX/scalingX);
        }
        else {
            correctionX=(int)(correctionX+(double)startX/scalingX);
        }
        if (endY<startY){
            correctionY=(int)(correctionY+(double)endY/scalingY);
        }
        else {
            correctionY=(int)(correctionY+(double)startY/scalingY);
        }
        scalingY =scalingY*(double) graphPlotter.getHeight()/(double)Math.abs(endY-startY);
        scalingX =scalingX*(double) graphPlotter.getWidth()/(double)Math.abs(endX-startX);
        rectStartX=0;
        rectStartY=0;
        rectEndX=0;
        rectEndY=0;
        graphPlotter.repaint();
        graphPlotter.setFocusable(true);
        graphPlotter.requestFocusInWindow();

    }
    public double getScalingX() {
        return scalingX;
    }

    public double getScalingY() {
        return scalingY;
    }

    public int getCorrectionX() {
        return correctionX;
    }

    public int getCorrectionY() {
        return correctionY;
    }
    public int getRectStartX() {
        return rectStartX;
    }

    public int getRectStartY() {
        return rectStartY;
    }

    public int getRectEndX() {
        return rectEndX;
    }

    public int getRectEndY() {
        return rectEndY;
    }

    public void setScalingX(double scalingX) {
        this.scalingX = scalingX;
    }

    public void setScalingY(double scalingY) {
        this.scalingY = scalingY;
    }

    public void setCorrectionX(int correctionX) {
        this.correctionX = correctionX;
    }

    public void setCorrectionY(int correctionY) {
        this.correctionY = correctionY;
    }
}
