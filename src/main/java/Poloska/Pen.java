package Poloska;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeLineCap;

public class Pen {
    private Color color;
    private boolean penUp;
    private double width;

    public Pen(Color color, boolean penUp, double width) {
        this.color = color;
        this.penUp = penUp;
        this.width = width;
    }
    public void setColor(Color color){
        this.color=color;
    }
    public void setWidth(double width) {
        this.width = width;
    }
    public void setPenUp(boolean penUp) {
        this.penUp = penUp;
    }
    public Color getColor(){
        return color;
    }
    /**
     * Be�ll�tja a rajzol�shoz sz�ks�ges tulajdons�gokat az �ppen aktu�lisra.
     * @param g A rajzt�bla GraphicsContext objektuma.
     * @return	Egy GraphicsContext objektum amin�l bevan �ll�tva minden ami sz�ks�ges a rajzol�shoz.
     */
    public GraphicsContext drawWithPen(GraphicsContext g){
        g.setStroke(color);
        g.setLineWidth(width);
        g.setLineCap(StrokeLineCap.ROUND);
        if(penUp){
            g.setStroke(Color.TRANSPARENT);
        }
        return g;
    }
}
