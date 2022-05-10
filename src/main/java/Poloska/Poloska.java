package Poloska;

import javafx.scene.canvas.Canvas;
import javafx.scene.image.PixelWriter;
import javafx.scene.paint.Color;

public class Poloska {
	/** A poloska X pozíciója*/
    private double x;
    /** A poloska Y pozíciója*/
    private double y;
    /** A poloska forgása szögben a 0 fok az jobbra néz.*/
    private double rotation;
    /** A rajzfelület amire jelenleg a Poloska rajzol.*/
    private Canvas currentCanvas;
    /** A toll amivel jelenleg  rajzol a poloska. Ez tartalmazza a színt, vastagságot és hogy felvan-e éppen emelve.*/
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
     * Visszaállítja a rajzfelületet az eredeti állapotába.
     * Minden eddigi rajzot kitöröl.
     * Poloska a kéernyõ közepére kerül, és felfele néz.
     */
    public void resetCanvas(){
        currentCanvas.getGraphicsContext2D().clearRect(0,0,currentCanvas.getWidth(),currentCanvas.getHeight());
        x=currentCanvas.getWidth()/2;
        y=currentCanvas.getHeight()/2;
        rotation=90;
    }
    /**
     * A poloska a megadott forgási irányba megy a megadott egységet.
     * @param value Az elõre haladás értéke.
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
     * @param value	A forgás mennyisége, ha negatív akkor jobbra, ha pozitív akkor ballra fordul.
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
