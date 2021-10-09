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

    public void drawAxisScale(){
        //vykreslenie "holej" x osi
        if (isY){
            graph.setPaint(Color.BLACK);
        graph.draw(new Line2D.Double(graphOffSet-(width*mouseMovement.getScalingX()-width)-mouseMovement.getCorrectionX(), height/2,width-graphOffSet+(width* mouseMovement.getScalingX()-width)-mouseMovement.getCorrectionX(), height/2));} //X axis

        //vykreslenie "holej" y osi
        else {
            graph.setPaint(Color.BLACK);
        graph.draw(new Line2D.Double(width/2, graphOffSet-(height*zoom-height), width/2, height-graphOffSet+(height*zoom-height)));} //Y axis

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

        }
        //osadíme 0 do grafu
        graph.setPaint(Color.BLACK);
        graph.drawString("0",(int)width/2+3,(int)height/2+15

        );
        for (int i = 0; i< listOfScale.length; i++){
            if (listOfScale[i]> endOfAxis){
                ////Algoritmus pre grafické osadenie koncovej hodnoty na osi Y +9 medzičiar.
                if (isY==true){
                    graph.draw(new Line2D.Double(width/2-2, height/2- (endOfAxis * axisScale*zoom),width/2+2, height/2- (endOfAxis * axisScale*zoom)));
                    graph.drawString(Double.toString(endOfAxis), (int) (width / 2 + 2), Math.round((height / 2 - (endOfAxis* axisScale*zoom))));

                    graph.draw(new Line2D.Double(width/2-2, height/2+ (endOfAxis * axisScale*zoom),width/2+2, height/2+ (endOfAxis * axisScale*zoom)));
                    graph.drawString(Double.toString(-endOfAxis), (int) (width / 2 + 2), Math.round((height / 2 + (endOfAxis* axisScale*zoom))));

                    for (int j=1;j<=9;j++){
                        graph.draw(new Line2D.Double(width / 2 - 1, height / 2 - (endOfAxis-(listOfScale[0]/10)*j) * axisScale*zoom, width / 2 + 1, height / 2 - (endOfAxis-(listOfScale[0]/10)*j) * axisScale*zoom));
                        graph.draw(new Line2D.Double(width / 2 - 1, height / 2 + (endOfAxis-(listOfScale[0]/10)*j) * axisScale*zoom, width / 2 + 1, height / 2 + (endOfAxis-(listOfScale[0]/10)*j) * axisScale*zoom));

                    }
                }

                ////Algoritmus pre grafické osadenie koncovej hodnoty na osi X+ 9 medzičiar.
                else {
                    graph.draw(new Line2D.Double(width/2- endOfAxis * axisScale*zoom, height/2-2,width/2- endOfAxis * axisScale*zoom, height/2+2));
                    graph.drawString(Double.toString(endOfAxis), Math.round((width / 2 + (endOfAxis* axisScale*zoom))-3),(int) height/2+15);
                    graph.draw(new Line2D.Double(width/2+ endOfAxis * axisScale*zoom, height/2-2,width/2+ endOfAxis * axisScale*zoom, height/2+2));
                    graph.drawString(Double.toString(-endOfAxis), Math.round((width / 2 - (endOfAxis* axisScale*zoom))-3),(int) height/2+15);

                    for (int jj=1;jj<=9;jj++){
                        graph.draw(new Line2D.Double( width / 2 -((endOfAxis-(listOfScale[0]/10)*jj) * axisScale*zoom), height/2-1, width/2 - (endOfAxis-(listOfScale[0]/10)*jj) * axisScale*zoom, height / 2 + 1));
                        graph.draw(new Line2D.Double( width / 2 +((endOfAxis-(listOfScale[0]/10)*jj) * axisScale*zoom), height/2-1, width/2 + (endOfAxis-(listOfScale[0]/10)*jj) * axisScale*zoom, height / 2 + 1));

                    }
                }
                break;
            }

            else {
                //Algoritmus pre grafické osadenie mierky na os Y aj s medzičiarami medzi číselne vyjadrenými bodmi mierky.
                if (isY==true) {
                    graph.draw(new Line2D.Double(width / 2 - 2, height / 2 - listOfScale[i] * axisScale*zoom, width / 2 + 2, height / 2 - listOfScale[i] * axisScale*zoom));
                    graph.drawString(Double.toString(listOfScale[i]), (int) (width / 2 + 2), Math.round((height / 2 - (listOfScale[i]* axisScale*zoom))));
                    graph.draw(new Line2D.Double(width / 2 - 2, height / 2 + listOfScale[i] * axisScale*zoom, width / 2 + 2, height / 2 + listOfScale[i] * axisScale*zoom));
                    graph.drawString(Double.toString(-listOfScale[i]), (int) (width / 2 + 2), Math.round((height / 2 + (listOfScale[i]* axisScale*zoom))));
                    for (int ii=1;ii<=9;ii++){
                        graph.draw(new Line2D.Double(width / 2 - 1, height / 2 - (listOfScale[i]-(listOfScale[0]/10)*ii) * axisScale*zoom, width / 2 + 1, height / 2 - (listOfScale[i]-(listOfScale[0]/10)*ii) * axisScale*zoom));
                        graph.draw(new Line2D.Double(width / 2 - 1, height / 2 + (listOfScale[i]-(listOfScale[0]/10)*ii) * axisScale*zoom, width / 2 + 1, height / 2 + (listOfScale[i]-(listOfScale[0]/10)*ii) * axisScale*zoom));

                    }
                }
                //Algoritmus pre grafické osadenie mierky na os X aj s medzičiarami medzi číselne vyjadrenými bodmi mierky.
                else {
                    graph.draw(new Line2D.Double(width/2- listOfScale[i]* axisScale*zoom, height/2-2,width/2- listOfScale[i]* axisScale*zoom, height/2+2));
                    graph.drawString(Double.toString(listOfScale[i]), Math.round((width / 2 + (listOfScale[i]* axisScale*zoom))-3),(int) height/2+15);
                    graph.draw(new Line2D.Double(width/2+ listOfScale[i]* axisScale*zoom, height/2-2,width/2+ listOfScale[i]* axisScale*zoom, height/2+2));
                    graph.drawString(Double.toString(-listOfScale[i]), Math.round((width / 2 - (listOfScale[i]* axisScale*zoom))-3),(int) height/2+15);
                    for (int iii=1;iii<=9;iii++){
                        graph.draw(new Line2D.Double( width / 2 -((listOfScale[i]-(listOfScale[0]/10)*iii) * axisScale*zoom), height/2-1, width/2 - (listOfScale[i]-(listOfScale[0]/10)*iii) * axisScale*zoom, height / 2 + 1));
                        graph.draw(new Line2D.Double( width / 2 +((listOfScale[i]-(listOfScale[0]/10)*iii) * axisScale*zoom), height/2-1, width/2 + (listOfScale[i]-(listOfScale[0]/10)*iii) * axisScale*zoom, height / 2 + 1));

                    }

                }
            }
        }

    }
}
