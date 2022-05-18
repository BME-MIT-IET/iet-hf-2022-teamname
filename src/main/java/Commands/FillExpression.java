package Commands;
import Exception.InputErrorException;
import Poloska.Poloska;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class FillExpression implements Command{

    private void colorPixel(int x, int y, Poloska p, Color col){

        PixelWriter pixelWriter=p.getPen().drawWithPen(p.getCanvas().getGraphicsContext2D()).getPixelWriter();
        WritableImage wim = new WritableImage((int)p.getCanvas().getWidth(), (int)p.getCanvas().getHeight());
        p.getCanvas().snapshot(null,wim);
        PixelReader pixelReader=wim.getPixelReader();
        pixelWriter.setColor(x,y,col);


   /*   if(pixelReader.getColor(x,y).getBlue()>200 &&pixelReader.getColor(x,y).getRed()>200&&pixelReader.getColor(x,y).getBlue()>200){

        }
  */

    }
    @Override
    public void execute(Poloska p) throws InputErrorException {
        colorPixel((int)p.getX(),(int)p.getY(),p,p.getPen().getColor());
    }
}
