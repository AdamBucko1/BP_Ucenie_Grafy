package sk.stuba.fei.uim.oop;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseMovement extends MouseAdapter {

    private int startX=0;
    private int startY=0;

    private int endX=0;
    private int endY=0;
    private MyAttemptAtPlotting myAttemptAtPlotting;

    private double scalingX=1;
    private double scalingY=1;
    private int correctionX=0;
    private int correctionY=0;
    private int rectStartX=0;
    private int rectStartY=0;
    private int rectEndX=0;
    private int rectEndY=0;


    public MouseMovement(MyAttemptAtPlotting myAttemptAtPlotting) {
        this.myAttemptAtPlotting=myAttemptAtPlotting;

    }

    @Override
    public void mousePressed(MouseEvent e) {
        startX=e.getX();
        startY=e.getY();
        System.out.println(startX);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        endX = e.getX();
        endY = e.getY();
        System.out.println(endX);
        rectStartX=startX;
        rectStartY=startY;
        rectEndX=endX;
        rectEndY=endY;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
      /*  if (myAttemptAtPlotting.getHeight()/Math.abs(endY-startY)<myAttemptAtPlotting.getWidth()/Math.abs(endX-startX)){
            scalingY=myAttemptAtPlotting.getHeight()/Math.abs(endY-startY);
            scalingX=myAttemptAtPlotting.getHeight()/Math.abs(endY-startY);
        }
        else {
            scalingY=myAttemptAtPlotting.getWidth()/Math.abs(endX-startX);
            scalingX=myAttemptAtPlotting.getWidth()/Math.abs(endX-startX);
        }*/
        scalingY = myAttemptAtPlotting.getHeight()/Math.abs(endY-startY);
        scalingX = myAttemptAtPlotting.getWidth()/Math.abs(endX-startX);
        if (endX<startX){
        correctionX=endX;
        }
        else {
            correctionX=startX;
        }
        if (endY<startY){
            correctionY=endY;
        }
        else {
            correctionY=startY;
        }
        rectStartX=0;
        rectStartY=0;
        rectEndX=0;
        rectEndY=0;
        myAttemptAtPlotting.repaint();

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
}
