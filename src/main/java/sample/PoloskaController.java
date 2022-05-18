package sample;


import Poloska.Poloska;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import InputTranslator.*;
import javafx.scene.layout.Pane;
import Commands.*;
import javafx.scene.transform.Rotate;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import variables.FunctionStorage;
import variables.VariableStorage;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

public class PoloskaController implements Initializable {
	/**Az utas�t�sok bemeneti mez�je */
    public TextField cmdInput;
    /**Az utas�t�sok kimeneti mez�je */
    public TextArea cmdOutput;
    /**A rajzfel�let, amire a poloska rajzol. */
    public Canvas drawingBoard;
    /**A t�rol� ami a Poloska k�p�t �s a rajzt�bl�t egybe foglalja */
    public Pane content;
    /**A poloska jelenlegi �llapot�t megjelen�t� k�p*/
    public ImageView imagePoloska;
    /**A poloska ami mint rajzol� eszk�z funkcion�l*/
    private Poloska p;
    private int pos=-1;
    /**F�jl v�laszt� ablak */
    private FileChooser fileChooser=new FileChooser();
    /**Az el�z� utas�t�s sorok list�ja, ezekre visszatudunk l�pni. */
    List<String> prevCommands=new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        drawingBoard.widthProperty().bind(content.widthProperty());
        drawingBoard.heightProperty().bind(content.heightProperty());
        p=new Poloska(600,300,drawingBoard);
        imagePoloska.setImage(new Image("/images/premiumboi4.png",24,24,true,false));
        imagePoloska.setVisible(false);
        cmdInput.setEditable(false);
        cmdOutput.setEditable(false);
    }
    /**
     * A k�l�nb�z� billenty� bemenetek lereag�l�sa.
     * 
     * Enter: A bemenetet �tvezeti az adott ford�t�/sz�r� r�tegeken. El�sz�r a Tokenizer Tokenekre bontja, azt�n a Parser Command-ok� alak�tja �ket.
     * A kapott Commandokat pedig az execute() met�dusok seg�ts�g�vel lefuttatjuk.
     * A v�g�n pedig a feldolgozott parancsot a kimenetre ki�rjuk.
     * 
     * fel/le: A m�r lefutatott parancsok k�z�tt tudunk v�logatni.
     * @param keyEvent A bej�v� billenyt� lenyom�s �lltal genr�lt esem�ny.
     */
    public void InputKeyPressed(KeyEvent keyEvent) {
        if(keyEvent.getCode()== KeyCode.ENTER){
            try {

                Parser parser = new Parser();
                ArrayList<Command> cmds = parser.parse((new Tokenizer(cmdInput.getText())).getTokens());
                Iterator<Command> it = cmds.iterator();
                while (it.hasNext()) {
                    Command c = it.next();
                    c.execute(p);
                }
                cmdOutput.appendText(cmdInput.getText()+"\n");
            }catch(Exception e){
               cmdOutput.appendText("Error: "+cmdInput.getText()+" | "+e.getMessage()+"\n");
                for(StackTraceElement ste: e.getStackTrace()){
                    System.out.println(ste);
                }
                VariableStorage.getInstance().clear();
            }
            //Rotation
            rotateImage();

            prevCommands.add(0,cmdInput.getText());
            pos=-1;
            cmdInput.setText("");
        }
        if(keyEvent.getCode()==KeyCode.DOWN && pos+1<prevCommands.size()){
            pos++;
            cmdInput.setText(prevCommands.get(pos));

        }
        if(keyEvent.getCode()==KeyCode.UP && pos>0){
        		pos--;
                cmdInput.setText(prevCommands.get(pos));
	    }
	}
    /**
     * Megynitja azt az ablakot ahol az elj�r�sokat menedzselhetj�k.
     * @param actionEvent	akci�
     * @throws IOException	ha nem siker�l bet�lteni az adott fxml f�jlt.
     */
    public void openClicked(ActionEvent actionEvent) throws IOException{
        Stage newStage=new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FunctionList.fxml"));
        Parent root = loader.load();
        newStage.setTitle("Add Function");
        Scene scene=new Scene(root,300,500);
        newStage.setScene(scene);
        newStage.getIcons().add(new Image("/images/premiumboi.png"));
        newStage.initModality(Modality.NONE);
        newStage.initOwner(drawingBoard.getScene().getWindow());

        newStage.show();
    }
    /**
     * Megnyit egy f�jlv�laszt� ablakot, ahol egy json f�jlt tudunk elmenteni, ebbe az elj�r�sok jelenlegi �llapotukban ker�lnek bele.
     * @param actionEvent
     * @throws IOException
     */
    public void ExportFunctions() throws IOException{
        fileChooser.getExtensionFilters().clear();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON files (*.json)","*.json"));
        File selected=fileChooser.showSaveDialog(content.getScene().getWindow());
        FunctionStorage.getInstance().Save(selected.getAbsolutePath());
    }

    /**
     * Elmenti a rajzfel�letet egy .png k�pf�jlba.
     * @param actionEvent
     */
    public void saveCanvas() {
        fileChooser.getExtensionFilters().clear();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PNG files (*.png)","*.png"));
        File selected=fileChooser.showSaveDialog(content.getScene().getWindow());
        WritableImage wim = new WritableImage((int)drawingBoard.getWidth(), (int)drawingBoard.getHeight());
        drawingBoard.snapshot(null,wim);
        try {
            ImageIO.write(SwingFXUtils.fromFXImage(wim, null), "png", selected);
        } catch (Exception s) {
            s.printStackTrace();
        }
    }
    /**
     * Egy felugr� ablak megjelenik a programr�l n�h�ny inform�ci�val.
     * @param actionEvent
     */
    public void aboutClicked() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About");
        alert.setHeaderText("About this program");
        alert.setContentText("Sz�cs �d�m - Programozas alapjai 3 HF \"Magic Poloska\" \n(BME 2020)");

        alert.showAndWait();
    }
    /**
     * Egy �j rajz k�sz�t�s�t kezdi. A k�pet megjelen�ti �s elforgatja, a rajzfel�letet alaphelyzetbe �ll�tja.
     * @param actionEvent
     */
    public void newDrawing() {
        imagePoloska.setVisible(true);
        p.resetCanvas();
        rotateImage();
        cmdInput.setEditable(true);
    }
    /**
     * A polosk�t tartalmaz� k�pet hozz�igaz�tja a poloska val�di fordul�s�hoz.
     * A forg�s k�z�ppontj�t a k�p k�z�ppontj�ra �ll�tja.
     */
    private void rotateImage() {
        imagePoloska.getTransforms().clear();
        imagePoloska.setX(p.getX()-12);
        imagePoloska.setY(p.getY()-12);
        Rotate rotation = new Rotate(-p.getRotation());
        rotation.setPivotX(p.getX());
        rotation.setPivotY(p.getY());
        imagePoloska.getTransforms().add(rotation);
    }
    /**Bez�r�dik a program */
    public void exitClicked() {
        Platform.exit();
    }
}
