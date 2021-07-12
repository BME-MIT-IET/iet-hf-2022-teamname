package Poloska;

import javafx.scene.canvas.Canvas;
import javafx.scene.image.PixelWriter;
import javafx.scene.paint.Color;

public class Poloska {
	/** A poloska X poz�ci�ja*/
    private double x;
    /** A poloska Y poz�ci�ja*/
    private double y;
    /** A poloska forg�sa sz�gben a 0 fok az jobbra n�z.*/
    private double rotation;
    /** A rajzfel�let amire jelenleg a Poloska rajzol.*/
    private Canvas currentCanvas;
    /** A toll amivel jelenleg  rajzol a poloska. Ez tartalmazza a sz�nt, vastags�got �s hogy felvan-e �ppen emelve.*/
    private Pen pen;
    public Poloska(int x, int y, Canvas canvas) {
        this.x = x;
        this.y = y;
        this.currentCanvas=canvas;
        rotation = 90;
        pen=new Pen(Color.BLACK,false,1);

    }
    public Pen getPen() {
        return pen;
    }
    public Canvas getCanvas(){
        return currentCanvas;
    }
    /**
     * Vissza�ll�tja a rajzfel�letet az eredeti �llapot�ba.
     * Minden eddigi rajzot kit�r�l.
     * Poloska a k�erny� k�zep�re ker�l, �s felfele n�z.
     */
    public void resetCanvas(){
        currentCanvas.getGraphicsContext2D().clearRect(0,0,currentCanvas.getWidth(),currentCanvas.getHeight());
        x=currentCanvas.getWidth()/2;
        y=currentCanvas.getHeight()/2;
        rotation=90;
    }
    /**
     * A poloska a megadott forg�si ir�nyba megy a megadott egys�get.
     * @param value Az el�re halad�s �rt�ke.
     */
    public void move(double value){
        double x2= x+value*Math.cos(Math.toRadians(rotation));
        double y2= y-value*Math.sin(Math.toRadians(rotation));
        pen.drawWithPen(currentCanvas.getGraphicsContext2D()).strokeLine(x,y,x2,y2);
        x=x2;
        y=y2;
    }
    /**
     * A megadott fokkal elfordul a poloska.
     * @param value	A forg�s mennyis�ge, ha negat�v akkor jobbra, ha pozit�v akkor ballra fordul.
     */
    public void rotate(double value){
        rotation+=value;
    }
    public double getX(){
        return x;
    }
    public double getY(){
        return  y;
    }
    public double getRotation(){
        return rotation;
    }
}
