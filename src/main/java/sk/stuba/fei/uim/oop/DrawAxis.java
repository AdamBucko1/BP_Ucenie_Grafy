package sk.stuba.fei.uim.oop;

import java.awt.*;
import java.awt.geom.Line2D;

public class DrawAxis {
    Graphics2D graph;
    double endOfAxis;
    double width;
    double height;
    double axisScale;
    boolean isY;
    int graphOffSet;
    double zoom;
    MouseMovement mouseMovement;

    public DrawAxis(Graphics2D graph, double endOfAxis, double width, double height, double axisScale, boolean isY, int graphOffSet, double zoom, MouseMovement mouseMovement) {
        this.graph = graph;
        this.endOfAxis = endOfAxis;
        this.width = width;
        this.height = height;
        this.axisScale = axisScale;
        this.isY = isY;
        this.graphOffSet= graphOffSet;
        this.zoom=zoom;
        this.mouseMovement=mouseMovement;
        drawAxisScale();
    }
    //toto je základná podoba mierky ktorá sa neskôr upravý na požadovanú veľkosť, je volaná z metódy drawAxisScale
    private void fillListOfScale(double[] listOfYScale){
        for (int i=0;i<listOfYScale.length;){
            listOfYScale[i]=0.001*++i;
        }

    }
    //Vynásobíme mierku premenou scaleIncreaser (ktorá sa vypočíta v metóde drawAxisScale) aby naša mierka korešpondovala s vykresleným grafom
    private void setAxisUpToScale(double[] listOfYScale, long scaleIncreaser){
        for (int i=0;i<listOfYScale.length;i++){
            listOfYScale[i]=listOfYScale[i]*scaleIncreaser;
        }

    }
//((getWidth()/2+(i*graphScaleX)-mouseMovement.getCorrectionX())*(mouseMovement.getScalingX()), (getHeight()/2-(i*i)*graphScaleY-mouseMovement.getCorrectionY())*mouseMovement.getScalingY(), 1, 1));
    public void drawAxisScale(){
        //vykreslenie "holej" x osi
        if (isY){
            graph.setPaint(Color.BLACK);
            System.out.println("AAAAAAAA"+mouseMovement.getCorrectionY());
        graph.draw(new Line2D.Double((graphOffSet-mouseMovement.getCorrectionX())*(mouseMovement.getScalingX()), (height/2-mouseMovement.getCorrectionY())*mouseMovement.getScalingY(),(width-graphOffSet-mouseMovement.getCorrectionX())*(mouseMovement.getScalingX()), (height/2-mouseMovement.getCorrectionY())*mouseMovement.getScalingY()));} //X axis

        //vykreslenie "holej" y osi
        else {
            graph.setPaint(Color.BLACK);
        graph.draw(new Line2D.Double((width/2-mouseMovement.getCorrectionX())*mouseMovement.getScalingX(), (graphOffSet-mouseMovement.getCorrectionY())*mouseMovement.getScalingY(), (width/2-mouseMovement.getCorrectionX())*mouseMovement.getScalingX(), (height-graphOffSet-mouseMovement.getCorrectionY())*mouseMovement.getScalingY()));} //Y axis

        //ideme vypočítať scaleIncreaser
        long scaleIncreaser=10;
        double[] listOfScale =new double[10];
        fillListOfScale(listOfScale);

        while (true){
            if (endOfAxis < 0.01*scaleIncreaser) {
                //keď sme vypočítali scaleIncreaser môžme mierku nastaviť do požadovanej veľkosti
                setAxisUpToScale(listOfScale,scaleIncreaser);
                break;

            }

            if (endOfAxis/(0.1*scaleIncreaser)>1){
                scaleIncreaser=scaleIncreaser*10;
            }
            else {
                scaleIncreaser=scaleIncreaser+scaleIncreaser;}
//((getWidth()/2+(i*graphScaleX)-mouseMovement.getCorrectionX())*(mouseMovement.getScalingX()), (getHeight()/2-(i*i)*graphScaleY-mouseMovement.getCorrectionY())*mouseMovement.getScalingY(), 1, 1));
        }
        //osadíme 0 do grafu
        graph.setPaint(Color.BLACK);
        graph.drawString("0",(int)((width/2+3-mouseMovement.getCorrectionX())*mouseMovement.getScalingX()),(int)((height/2+15-mouseMovement.getCorrectionY())*mouseMovement.getScalingY())

        );
        for (int i = 0; i< listOfScale.length; i++){
            if (listOfScale[i]> endOfAxis){
                ////Algoritmus pre grafické osadenie koncovej hodnoty na osi Y +9 medzičiar.
                if (isY==true){
                    graph.draw(new Line2D.Double((width/2-2-mouseMovement.getCorrectionX())*mouseMovement.getScalingX(), (height/2- (endOfAxis * axisScale)-mouseMovement.getCorrectionY())*mouseMovement.getScalingY(),(width/2+2-mouseMovement.getCorrectionX())*mouseMovement.getScalingX(), (height/2-(endOfAxis * axisScale)-mouseMovement.getCorrectionY())*mouseMovement.getScalingY()));
                    graph.drawString(Double.toString( endOfAxis), (int) (((width / 2 )-mouseMovement.getCorrectionX())*mouseMovement.getScalingX()-5), Math.round(((height / 2 - endOfAxis*axisScale-mouseMovement.getCorrectionY())*mouseMovement.getScalingY()-5)));

                    graph.draw(new Line2D.Double((width/2-2-mouseMovement.getCorrectionX())*mouseMovement.getScalingX(), (height/2+ (endOfAxis * axisScale)-mouseMovement.getCorrectionY())*mouseMovement.getScalingY(),(width/2+2-mouseMovement.getCorrectionX())*mouseMovement.getScalingX(), (height/2+(endOfAxis * axisScale)-mouseMovement.getCorrectionY())*mouseMovement.getScalingY()));
                    graph.drawString(Double.toString(-endOfAxis), (int) (((width / 2 )-mouseMovement.getCorrectionX())*mouseMovement.getScalingX()-5), Math.round(((height / 2 + endOfAxis*axisScale-mouseMovement.getCorrectionY())*mouseMovement.getScalingY()+10)));

                    for (int j=1;j<=9;j++){
                        if (listOfScale[i-1]+(listOfScale[0]/10)*j<endOfAxis) {
                            graph.draw(new Line2D.Double((width / 2 - 1 - mouseMovement.getCorrectionX()) * mouseMovement.getScalingX(), (height / 2 - (listOfScale[i-1]+(listOfScale[0]/10)*j) * axisScale - mouseMovement.getCorrectionY()) * mouseMovement.getScalingY(), (width / 2 + 1 - mouseMovement.getCorrectionX()) * mouseMovement.getScalingX(), (height / 2 - (listOfScale[i-1]+(listOfScale[0]/10)*j) * axisScale - mouseMovement.getCorrectionY()) * mouseMovement.getScalingY()));
                            graph.draw(new Line2D.Double((width / 2 - 1 - mouseMovement.getCorrectionX()) * mouseMovement.getScalingX(), (height / 2 + (listOfScale[i-1]+(listOfScale[0]/10)*j) * axisScale - mouseMovement.getCorrectionY()) * mouseMovement.getScalingY(), (width / 2 + 1 - mouseMovement.getCorrectionX()) * mouseMovement.getScalingX(), (height / 2 + (listOfScale[i-1]+(listOfScale[0]/10)*j) * axisScale - mouseMovement.getCorrectionY()) * mouseMovement.getScalingY()));
                        }
                    }
                }

                ////Algoritmus pre grafické osadenie koncovej hodnoty na osi X+ 9 medzičiar.
                else {
                    graph.draw(new Line2D.Double((width/2+ endOfAxis * axisScale-mouseMovement.getCorrectionX())*mouseMovement.getScalingX(), (height/2-2-mouseMovement.getCorrectionY())*mouseMovement.getScalingY(),(width/2+ endOfAxis * axisScale-mouseMovement.getCorrectionX())*mouseMovement.getScalingX(), (height/2+2-mouseMovement.getCorrectionY())*mouseMovement.getScalingY()));
                    graph.drawString(Double.toString(endOfAxis), Math.round((width / 2 + (endOfAxis* axisScale-mouseMovement.getCorrectionX()))*mouseMovement.getScalingX()-3),(int) ((height/2-mouseMovement.getCorrectionY())*mouseMovement.getScalingY()+25));
                    graph.draw(new Line2D.Double((width/2- endOfAxis * axisScale-mouseMovement.getCorrectionX())*mouseMovement.getScalingX(), (height/2-2-mouseMovement.getCorrectionY())*mouseMovement.getScalingY(),(width/2- endOfAxis * axisScale-mouseMovement.getCorrectionX())*mouseMovement.getScalingX(), (height/2+2-mouseMovement.getCorrectionY())*mouseMovement.getScalingY()));
                    graph.drawString(Double.toString(-endOfAxis), Math.round((width/2- endOfAxis * axisScale-mouseMovement.getCorrectionX())*mouseMovement.getScalingX()-3),(int) ((height/2-mouseMovement.getCorrectionY())*mouseMovement.getScalingY()+25));

                    for (int jj=1;jj<=9;jj++){
                        if (listOfScale[i-1]+(listOfScale[0]/10)*jj<endOfAxis) {
                            graph.draw(new Line2D.Double((width / 2 + (listOfScale[i-1]+(listOfScale[0]/10)*jj) * axisScale - mouseMovement.getCorrectionX()) * mouseMovement.getScalingX(), (height / 2 - 1 - mouseMovement.getCorrectionY()) * mouseMovement.getScalingY(), (width / 2 + (listOfScale[i-1]+(listOfScale[0]/10)*jj) * axisScale - mouseMovement.getCorrectionX()) * mouseMovement.getScalingX(), (height / 2 + 1 - mouseMovement.getCorrectionY()) * mouseMovement.getScalingY()));
                            graph.draw(new Line2D.Double((width / 2 - (listOfScale[i-1]+(listOfScale[0]/10)*jj) * axisScale - mouseMovement.getCorrectionX()) * mouseMovement.getScalingX(), (height / 2 - 1 - mouseMovement.getCorrectionY()) * mouseMovement.getScalingY(), (width / 2 - (listOfScale[i-1]+(listOfScale[0]/10)*jj) * axisScale - mouseMovement.getCorrectionX()) * mouseMovement.getScalingX(), (height / 2 + 1 - mouseMovement.getCorrectionY()) * mouseMovement.getScalingY()));
                        }
                        }
                }
                break;
            }
            else {
                //Algoritmus pre grafické osadenie mierky na os Y aj s medzičiarami medzi číselne vyjadrenými bodmi mierky.
                if (isY==true) {
                    //graph.draw(new Line2D.Double(width / 2 - 1-mouseMovement.getCorrectionX(),( height / 2 + (endOfAxis-(listOfScale[0]/10)*j) * axisScale-mouseMovement.getCorrectionY())*mouseMovement.getScalingY(), width / 2 + 1-mouseMovement.getCorrectionX(), (height / 2 + (endOfAxis-(listOfScale[0]/10)*j) * axisScale-mouseMovement.getCorrectionY())*mouseMovement.getScalingY()));
                   //ath.round((width / 2 + (listOfScale[i]* axisScale-mouseMovement.getCorrectionX()))*mouseMovement.getScalingX()-3)

                    graph.draw(new Line2D.Double((width / 2 - 2-mouseMovement.getCorrectionX())*mouseMovement.getScalingX(), ( height / 2 - ((listOfScale[i])) * axisScale-mouseMovement.getCorrectionY())*mouseMovement.getScalingY(), (width / 2 + 2-mouseMovement.getCorrectionX())*mouseMovement.getScalingX(), ( height / 2 - (listOfScale[i]) * axisScale-mouseMovement.getCorrectionY())*mouseMovement.getScalingY()));
                    graph.drawString(Double.toString(listOfScale[i]),(int) ((width / 2 -mouseMovement.getCorrectionX())*mouseMovement.getScalingX()+2), Math.round(( height / 2 - ((listOfScale[i])) * axisScale-mouseMovement.getCorrectionY())*mouseMovement.getScalingY()));


                    graph.draw(new Line2D.Double((width / 2 - 2-mouseMovement.getCorrectionX())*mouseMovement.getScalingX(), ( height / 2 + ((listOfScale[i])) * axisScale-mouseMovement.getCorrectionY())*mouseMovement.getScalingY(), (width / 2 + 2-mouseMovement.getCorrectionX())*mouseMovement.getScalingX(), ( height / 2 + (listOfScale[i]) * axisScale-mouseMovement.getCorrectionY())*mouseMovement.getScalingY()));
                    graph.drawString(Double.toString(-listOfScale[i]), (int) ((width / 2 -mouseMovement.getCorrectionX())*mouseMovement.getScalingX()+2), (int)(( height / 2 + ((listOfScale[i])) * axisScale-mouseMovement.getCorrectionY())*mouseMovement.getScalingY()));
                    for (int ii=1;ii<=9;ii++){
                        graph.draw(new Line2D.Double((width / 2 - 1-mouseMovement.getCorrectionX())*mouseMovement.getScalingX(), ( height / 2 - ((listOfScale[i]-(listOfScale[0]/10)*ii)) * axisScale-mouseMovement.getCorrectionY())*mouseMovement.getScalingY(), (width / 2 + 1-mouseMovement.getCorrectionX())*mouseMovement.getScalingX(), ( height / 2 - (listOfScale[i]-(listOfScale[0]/10)*ii) * axisScale-mouseMovement.getCorrectionY())*mouseMovement.getScalingY()));
                       // graph.draw(new Line2D.Double(width / 2 - 1, height / 2 - (listOfScale[i]-(listOfScale[0]/10)*ii) * axisScale*zoom, width / 2 + 1, height / 2 - (listOfScale[i]-(listOfScale[0]/10)*ii) * axisScale*zoom));
                     //   graph.draw(new Line2D.Double(width / 2 - 1, height / 2 + (listOfScale[i]-(listOfScale[0]/10)*ii) * axisScale*zoom, width / 2 + 1, height / 2 + (listOfScale[i]-(listOfScale[0]/10)*ii) * axisScale*zoom));
                        graph.draw(new Line2D.Double((width / 2 - 1-mouseMovement.getCorrectionX())*mouseMovement.getScalingX(), ( height / 2 + ((listOfScale[i]-(listOfScale[0]/10)*ii)) * axisScale-mouseMovement.getCorrectionY())*mouseMovement.getScalingY(), (width / 2 + 1-mouseMovement.getCorrectionX())*mouseMovement.getScalingX(), ( height / 2 + (listOfScale[i]-(listOfScale[0]/10)*ii) * axisScale-mouseMovement.getCorrectionY())*mouseMovement.getScalingY()));
                    }
                }
                //Algoritmus pre grafické osadenie mierky na os X aj s medzičiarami medzi číselne vyjadrenými bodmi mierky.
                else {
                    graph.draw(new Line2D.Double(( width / 2 + ((listOfScale[i])) * axisScale-mouseMovement.getCorrectionX())*mouseMovement.getScalingX(), (height/2+2-mouseMovement.getCorrectionY())*mouseMovement.getScalingY(),( width / 2 + ((listOfScale[i])) * axisScale-mouseMovement.getCorrectionX())*mouseMovement.getScalingX(), (height/2-2-mouseMovement.getCorrectionY())*mouseMovement.getScalingY()));
                    graph.drawString(Double.toString(listOfScale[i]), Math.round((width / 2 + (listOfScale[i]* axisScale-mouseMovement.getCorrectionX()))*mouseMovement.getScalingX()-3),(int) ((height/2-mouseMovement.getCorrectionY())*mouseMovement.getScalingY()+15));

                    graph.draw(new Line2D.Double(( width / 2 - ((listOfScale[i])) * axisScale-mouseMovement.getCorrectionX())*mouseMovement.getScalingX(), (height/2+2-mouseMovement.getCorrectionY())*mouseMovement.getScalingY(),( width / 2 - ((listOfScale[i])) * axisScale-mouseMovement.getCorrectionX())*mouseMovement.getScalingX(), (height/2-2-mouseMovement.getCorrectionY())*mouseMovement.getScalingY()));
                    graph.drawString(Double.toString(-listOfScale[i]), Math.round(( width / 2 - ((listOfScale[i])) * axisScale-mouseMovement.getCorrectionX())*mouseMovement.getScalingX()-3),(int) ((height/2-mouseMovement.getCorrectionY())*mouseMovement.getScalingY()+15));
                    for (int iii=1;iii<=9;iii++){
                        //graph.draw(new Line2D.Double((width / 2 - 1-mouseMovement.getCorrectionX())*mouseMovement.getScalingX(), ( height / 2 - ((listOfScale[i]-(listOfScale[0]/10)*ii)) * axisScale-mouseMovement.getCorrectionY())*mouseMovement.getScalingY(), (width / 2 + 1-mouseMovement.getCorrectionX())*mouseMovement.getScalingX(), ( height / 2 - (listOfScale[i]-(listOfScale[0]/10)*ii) * axisScale-mouseMovement.getCorrectionY())*mouseMovement.getScalingY()));
                        graph.draw(new Line2D.Double(( width / 2 - ((listOfScale[i]-(listOfScale[0]/10)*iii)) * axisScale-mouseMovement.getCorrectionX())*mouseMovement.getScalingX(), (height / 2 - 1-mouseMovement.getCorrectionY())*mouseMovement.getScalingY(), ( width / 2 - ((listOfScale[i]-(listOfScale[0]/10)*iii)) * axisScale-mouseMovement.getCorrectionX())*mouseMovement.getScalingX(), (height / 2 + 1-mouseMovement.getCorrectionY())*mouseMovement.getScalingY()));
                        graph.draw(new Line2D.Double(( width / 2 +((listOfScale[i]-(listOfScale[0]/10)*iii) * axisScale-mouseMovement.getCorrectionX()))*mouseMovement.getScalingX(), (height/2-1-mouseMovement.getCorrectionY())*mouseMovement.getScalingY(), ( width / 2 +((listOfScale[i]-(listOfScale[0]/10)*iii) * axisScale-mouseMovement.getCorrectionX()))*mouseMovement.getScalingX(), (height/2+1-mouseMovement.getCorrectionY())*mouseMovement.getScalingY()));

                    }

                }
            }
        }

    }
}
