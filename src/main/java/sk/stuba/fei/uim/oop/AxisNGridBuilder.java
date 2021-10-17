package sk.stuba.fei.uim.oop;

import lombok.Setter;

import java.awt.*;
import java.awt.geom.Line2D;

public class AxisNGridBuilder {
    Graphics2D graph;
    double endOfAxis;
    double theOtherAxis;
    double width;
    double height;
    double axisScale;
    boolean isY;
    int graphOffSet;
    double zoom;
    @Setter
    boolean showGrid;
    MouseControll mouseControll;

    public AxisNGridBuilder(Graphics2D graph, double endOfAxis, double width, double height, double axisScale, boolean isY, int graphOffSet, double zoom, MouseControll mouseControll, double theOtherAxis) {
        this.graph = graph;
        this.endOfAxis = endOfAxis;
        this.width = width;
        this.height = height;
        this.axisScale = axisScale;
        this.isY = isY;
        this.graphOffSet= graphOffSet;
        this.zoom=zoom;
        this.theOtherAxis=theOtherAxis;
        this.mouseControll = mouseControll;
        drawAxisScale(graph);
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
     public void drawAxisScale(Graphics2D graph){
        this.graph=graph;

        //vykreslenie "holej" x osi
        if (isY){
            graph.setPaint(Color.BLACK);
            graph.draw(new Line2D.Double((graphOffSet- mouseControll.getCorrectionX())*(mouseControll.getScalingX()),
                (height/2- mouseControll.getCorrectionY())* mouseControll.getScalingY(),
                (width-graphOffSet- mouseControll.getCorrectionX())*(mouseControll.getScalingX()),
                (height/2- mouseControll.getCorrectionY())* mouseControll.getScalingY()));} //X axis

        //vykreslenie "holej" y osi
        else {
            graph.setPaint(Color.BLACK);
            graph.draw(new Line2D.Double((width/2- mouseControll.getCorrectionX())* mouseControll.getScalingX(),
                (graphOffSet- mouseControll.getCorrectionY())* mouseControll.getScalingY(),
                (width/2- mouseControll.getCorrectionX())* mouseControll.getScalingX(),
                (height-graphOffSet- mouseControll.getCorrectionY())* mouseControll.getScalingY()));} //Y axis

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

        graph.drawString("0",
                 (int)((width/2+3- mouseControll.getCorrectionX())* mouseControll.getScalingX()),
                 (int)((height/2+15- mouseControll.getCorrectionY())* mouseControll.getScalingY()));


         if (showGrid) {
            graph.setPaint(Color.GRAY);

            for (int i = 0; i < listOfScale.length; i++) {

                if (listOfScale[i] < endOfAxis + listOfScale[1]) {

                    if (isY) {
                        graph.draw(new Line2D.Double(0,
                                ( height / 2 - ((listOfScale[i])) * axisScale- mouseControll.getCorrectionY())* mouseControll.getScalingY(),
                                width,
                                ( height / 2 - (listOfScale[i]) * axisScale- mouseControll.getCorrectionY())* mouseControll.getScalingY()));

                        graph.draw(new Line2D.Double(0,
                                ( height / 2 + ((listOfScale[i])) * axisScale- mouseControll.getCorrectionY())* mouseControll.getScalingY(),
                                width,
                                ( height / 2 + (listOfScale[i]) * axisScale- mouseControll.getCorrectionY())* mouseControll.getScalingY()));
                    }

                    else {
                        graph.draw(new Line2D.Double(( width / 2 + ((listOfScale[i])) * axisScale- mouseControll.getCorrectionX())* mouseControll.getScalingX(),
                                0,
                                ( width / 2 + ((listOfScale[i])) * axisScale- mouseControll.getCorrectionX())* mouseControll.getScalingX(),
                                height));

                        graph.draw(new Line2D.Double(( width / 2 - ((listOfScale[i])) * axisScale- mouseControll.getCorrectionX())* mouseControll.getScalingX(),
                                0,
                                ( width / 2 - ((listOfScale[i])) * axisScale- mouseControll.getCorrectionX())* mouseControll.getScalingX(),
                                height));
                    }
                }
                if (isY) {
                    graph.draw(new Line2D.Double((mouseControll.getCorrectionX()) * (mouseControll.getScalingX()),
                            (height / 2 - mouseControll.getCorrectionY()) * mouseControll.getScalingY(),
                            (width - mouseControll.getCorrectionX()) * (mouseControll.getScalingX()),
                            (height / 2 - mouseControll.getCorrectionY()) * mouseControll.getScalingY()));
                }

                else {
                    graph.draw(new Line2D.Double((width / 2 - mouseControll.getCorrectionX()) * mouseControll.getScalingX(),
                            (-mouseControll.getCorrectionY()) * mouseControll.getScalingY(),
                            (width / 2 - mouseControll.getCorrectionX()) * mouseControll.getScalingX(),
                            (height - mouseControll.getCorrectionY()) * mouseControll.getScalingY()));
                }
            }
        }
        graph.setPaint(Color.BLACK);


        for (int i = 0; i< listOfScale.length; i++){

            if (listOfScale[i]> endOfAxis){

                ////Algoritmus pre grafické osadenie koncovej hodnoty na osi Y +9 medzičiar.
                if (isY==true){

                    graph.draw(new Line2D.Double((width/2-2- mouseControll.getCorrectionX())* mouseControll.getScalingX(),
                            (height/2- (endOfAxis * axisScale)- mouseControll.getCorrectionY())* mouseControll.getScalingY(),
                            (width/2+2- mouseControll.getCorrectionX())* mouseControll.getScalingX(),
                            (height/2-(endOfAxis * axisScale)- mouseControll.getCorrectionY())* mouseControll.getScalingY()));
                    graph.drawString(Double.toString( endOfAxis),
                            (int) (((width / 2 )- mouseControll.getCorrectionX())* mouseControll.getScalingX()-5),
                            Math.round(((height / 2 - endOfAxis*axisScale- mouseControll.getCorrectionY())* mouseControll.getScalingY()-5)));

                    graph.draw(new Line2D.Double((width/2-2- mouseControll.getCorrectionX())* mouseControll.getScalingX(),
                            (height/2+ (endOfAxis * axisScale)- mouseControll.getCorrectionY())* mouseControll.getScalingY(),
                            (width/2+2- mouseControll.getCorrectionX())* mouseControll.getScalingX(),
                            (height/2+(endOfAxis * axisScale)- mouseControll.getCorrectionY())* mouseControll.getScalingY()));
                    graph.drawString(Double.toString(-endOfAxis), (int) (((width / 2 )- mouseControll.getCorrectionX())* mouseControll.getScalingX()-5), Math.round(((height / 2 + endOfAxis*axisScale- mouseControll.getCorrectionY())* mouseControll.getScalingY()+10)));


                    for (int j=1;j<=9;j++){

                        if (listOfScale[i-1]+(listOfScale[0]/10)*j<endOfAxis) {

                            graph.draw(new Line2D.Double((width / 2 - 1 - mouseControll.getCorrectionX()) * mouseControll.getScalingX(),
                                    (height / 2 - (listOfScale[i-1]+(listOfScale[0]/10)*j) * axisScale - mouseControll.getCorrectionY()) * mouseControll.getScalingY(),
                                    (width / 2 + 1 - mouseControll.getCorrectionX()) * mouseControll.getScalingX(),
                                    (height / 2 - (listOfScale[i-1]+(listOfScale[0]/10)*j) * axisScale - mouseControll.getCorrectionY()) * mouseControll.getScalingY()));

                            graph.draw(new Line2D.Double((width / 2 - 1 - mouseControll.getCorrectionX()) * mouseControll.getScalingX(),
                                    (height / 2 + (listOfScale[i-1]+(listOfScale[0]/10)*j) * axisScale - mouseControll.getCorrectionY()) * mouseControll.getScalingY(),
                                    (width / 2 + 1 - mouseControll.getCorrectionX()) * mouseControll.getScalingX(),
                                    (height / 2 + (listOfScale[i-1]+(listOfScale[0]/10)*j) * axisScale - mouseControll.getCorrectionY()) * mouseControll.getScalingY()));
                        }
                    }
                }

                ////Algoritmus pre grafické osadenie koncovej hodnoty na osi X+ 9 medzičiar.
                else {

                    graph.draw(new Line2D.Double((width/2+ endOfAxis * axisScale- mouseControll.getCorrectionX())* mouseControll.getScalingX(),
                            (height/2-2- mouseControll.getCorrectionY())* mouseControll.getScalingY(),
                            (width/2+ endOfAxis * axisScale- mouseControll.getCorrectionX())* mouseControll.getScalingX(),
                            (height/2+2- mouseControll.getCorrectionY())* mouseControll.getScalingY()));
                    graph.drawString(Double.toString(endOfAxis), Math.round((width / 2 + (endOfAxis* axisScale- mouseControll.getCorrectionX()))* mouseControll.getScalingX()-3),(int) ((height/2- mouseControll.getCorrectionY())* mouseControll.getScalingY()+25));

                    graph.draw(new Line2D.Double((width/2- endOfAxis * axisScale- mouseControll.getCorrectionX())* mouseControll.getScalingX(),
                            (height/2-2- mouseControll.getCorrectionY())* mouseControll.getScalingY(),
                            (width/2- endOfAxis * axisScale- mouseControll.getCorrectionX())* mouseControll.getScalingX(),
                            (height/2+2- mouseControll.getCorrectionY())* mouseControll.getScalingY()));
                    graph.drawString(Double.toString(-endOfAxis), Math.round((width/2- endOfAxis * axisScale- mouseControll.getCorrectionX())* mouseControll.getScalingX()-3),(int) ((height/2- mouseControll.getCorrectionY())* mouseControll.getScalingY()+25));


                    for (int jj=1;jj<=9;jj++){

                        if (listOfScale[i-1]+(listOfScale[0]/10)*jj<endOfAxis) {

                            graph.draw(new Line2D.Double((width / 2 + (listOfScale[i-1]+(listOfScale[0]/10)*jj) * axisScale - mouseControll.getCorrectionX()) * mouseControll.getScalingX(),
                                    (height / 2 - 1 - mouseControll.getCorrectionY()) * mouseControll.getScalingY(),
                                    (width / 2 + (listOfScale[i-1]+(listOfScale[0]/10)*jj) * axisScale - mouseControll.getCorrectionX()) * mouseControll.getScalingX(),
                                    (height / 2 + 1 - mouseControll.getCorrectionY()) * mouseControll.getScalingY()));

                            graph.draw(new Line2D.Double((width / 2 - (listOfScale[i-1]+(listOfScale[0]/10)*jj) * axisScale - mouseControll.getCorrectionX()) * mouseControll.getScalingX(),
                                    (height / 2 - 1 - mouseControll.getCorrectionY()) * mouseControll.getScalingY(),
                                    (width / 2 - (listOfScale[i-1]+(listOfScale[0]/10)*jj) * axisScale - mouseControll.getCorrectionX()) * mouseControll.getScalingX(),
                                    (height / 2 + 1 - mouseControll.getCorrectionY()) * mouseControll.getScalingY()));
                              }
                        }
                }
                break;
            }
            else {

                //Algoritmus pre grafické osadenie mierky na os Y aj s medzičiarami medzi číselne vyjadrenými bodmi mierky.
                if (isY==true) {

                    graph.draw(new Line2D.Double((width / 2 - 2- mouseControll.getCorrectionX())* mouseControll.getScalingX(),
                            ( height / 2 - ((listOfScale[i])) * axisScale- mouseControll.getCorrectionY())* mouseControll.getScalingY(),
                            (width / 2 + 2- mouseControll.getCorrectionX())* mouseControll.getScalingX(),
                            ( height / 2 - (listOfScale[i]) * axisScale- mouseControll.getCorrectionY())* mouseControll.getScalingY()));
                    graph.drawString(Double.toString(listOfScale[i]),
                            (int) ((width / 2 - mouseControll.getCorrectionX())* mouseControll.getScalingX()+2),
                            Math.round(( height / 2 - ((listOfScale[i])) * axisScale- mouseControll.getCorrectionY())* mouseControll.getScalingY()));

                    graph.draw(new Line2D.Double((width / 2 - 2- mouseControll.getCorrectionX())* mouseControll.getScalingX(),
                            ( height / 2 + ((listOfScale[i])) * axisScale- mouseControll.getCorrectionY())* mouseControll.getScalingY(),
                            (width / 2 + 2- mouseControll.getCorrectionX())* mouseControll.getScalingX(),
                            ( height / 2 + (listOfScale[i]) * axisScale- mouseControll.getCorrectionY())* mouseControll.getScalingY()));
                    graph.drawString(Double.toString(-listOfScale[i]),
                            (int) ((width / 2 - mouseControll.getCorrectionX())* mouseControll.getScalingX()+2),
                            (int)(( height / 2 + ((listOfScale[i])) * axisScale- mouseControll.getCorrectionY())* mouseControll.getScalingY()));


                    for (int ii=1;ii<=9;ii++){

                        graph.draw(new Line2D.Double((width / 2 - 1- mouseControll.getCorrectionX())* mouseControll.getScalingX(),
                                ( height / 2 - ((listOfScale[i]-(listOfScale[0]/10)*ii)) * axisScale- mouseControll.getCorrectionY())* mouseControll.getScalingY(),
                                (width / 2 + 1- mouseControll.getCorrectionX())* mouseControll.getScalingX(),
                                ( height / 2 - (listOfScale[i]-(listOfScale[0]/10)*ii) * axisScale- mouseControll.getCorrectionY())* mouseControll.getScalingY()));

                        graph.draw(new Line2D.Double((width / 2 - 1- mouseControll.getCorrectionX())* mouseControll.getScalingX(),
                                ( height / 2 + ((listOfScale[i]-(listOfScale[0]/10)*ii)) * axisScale- mouseControll.getCorrectionY())* mouseControll.getScalingY(),
                                (width / 2 + 1- mouseControll.getCorrectionX())* mouseControll.getScalingX(),
                                ( height / 2 + (listOfScale[i]-(listOfScale[0]/10)*ii) * axisScale- mouseControll.getCorrectionY())* mouseControll.getScalingY()));
                    }
                }

                //Algoritmus pre grafické osadenie mierky na os X aj s medzičiarami medzi číselne vyjadrenými bodmi mierky.
                else {

                    graph.draw(new Line2D.Double(( width / 2 + ((listOfScale[i])) * axisScale- mouseControll.getCorrectionX())* mouseControll.getScalingX(),
                            (height/2+2- mouseControll.getCorrectionY())* mouseControll.getScalingY(),
                            ( width / 2 + ((listOfScale[i])) * axisScale- mouseControll.getCorrectionX())* mouseControll.getScalingX(),
                            (height/2-2- mouseControll.getCorrectionY())* mouseControll.getScalingY()));
                    graph.drawString(Double.toString(listOfScale[i]),
                            Math.round((width / 2 + (listOfScale[i]* axisScale- mouseControll.getCorrectionX()))* mouseControll.getScalingX()-3),
                            (int) ((height/2- mouseControll.getCorrectionY())* mouseControll.getScalingY()+15));

                    graph.draw(new Line2D.Double(( width / 2 - ((listOfScale[i])) * axisScale- mouseControll.getCorrectionX())* mouseControll.getScalingX(),
                            (height/2+2- mouseControll.getCorrectionY())* mouseControll.getScalingY(),
                            ( width / 2 - ((listOfScale[i])) * axisScale- mouseControll.getCorrectionX())* mouseControll.getScalingX(),
                            (height/2-2- mouseControll.getCorrectionY())* mouseControll.getScalingY()));
                    graph.drawString(Double.toString(-listOfScale[i]),
                            Math.round(( width / 2 - ((listOfScale[i])) * axisScale- mouseControll.getCorrectionX())* mouseControll.getScalingX()-3),
                            (int) ((height/2- mouseControll.getCorrectionY())* mouseControll.getScalingY()+15));


                    for (int iii=1;iii<=9;iii++){

                        graph.draw(new Line2D.Double(( width / 2 - ((listOfScale[i]-(listOfScale[0]/10)*iii)) * axisScale- mouseControll.getCorrectionX())* mouseControll.getScalingX(),
                                (height / 2 - 1- mouseControll.getCorrectionY())* mouseControll.getScalingY(),
                                ( width / 2 - ((listOfScale[i]-(listOfScale[0]/10)*iii)) * axisScale- mouseControll.getCorrectionX())* mouseControll.getScalingX(),
                                (height / 2 + 1- mouseControll.getCorrectionY())* mouseControll.getScalingY()));

                        graph.draw(new Line2D.Double(( width / 2 +((listOfScale[i]-(listOfScale[0]/10)*iii) * axisScale- mouseControll.getCorrectionX()))* mouseControll.getScalingX(),
                                (height/2-1- mouseControll.getCorrectionY())* mouseControll.getScalingY(),
                                ( width / 2 +((listOfScale[i]-(listOfScale[0]/10)*iii) * axisScale- mouseControll.getCorrectionX()))* mouseControll.getScalingX(),
                                (height/2+1- mouseControll.getCorrectionY())* mouseControll.getScalingY()));

                    }

                }
            }
        }


    }
}
