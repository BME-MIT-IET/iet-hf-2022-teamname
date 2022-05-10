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
	/**Az utasítások bemeneti mezõje */
    public TextField cmdInput;
    /**Az utasítások kimeneti mezõje */
    public TextArea cmdOutput;
    /**A rajzfelület, amire a poloska rajzol. */
    public Canvas drawingBoard;
    /**A tároló ami a Poloska képét és a rajztáblát egybe foglalja */
    public Pane content;
    /**A poloska jelenlegi állapotát megjelenítõ kép*/
    public ImageView imagePoloska;
    /**A poloska ami mint rajzoló eszköz funkcionál*/
    private Poloska p;
    private int pos=-1;
    /**Fájl választó ablak */
    private FileChooser fileChooser=new FileChooser();
    /**Az elõzõ utasítás sorok listája, ezekre visszatudunk lépni. */
    List<String> prevCommands=new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        drawingBoard.widthProperty().bind(content.widthProperty());
        drawingBoard.heightProperty().bind(content.heightProperty());
        p=new Poloska(600,300,drawingBoard);
        imagePoloska.setImage(new Image("images/premiumboi4.png",24,24,true,false));
        imagePoloska.setVisible(false);
        cmdInput.setEditable(false);
        cmdOutput.setEditable(false);
    }
    /**
     * A különbözõ billentyû bemenetek lereagálása.
     * 
     * Enter: A bemenetet átvezeti az adott fordító/szûrõ rétegeken. Elõször a Tokenizer Tokenekre bontja, aztán a Parser Command-oká alakítja õket.
     * A kapott Commandokat pedig az execute() metódusok segítségével lefuttatjuk.
     * A végén pedig a feldolgozott parancsot a kimenetre kiírjuk.
     * 
     * fel/le: A már lefutatott parancsok között tudunk válogatni.
     * @param keyEvent A bejövõ billenytû lenyomás álltal genrált esemény.
     */
    public void InputKeyPressed(KeyEvent keyEvent) {
        if(keyEvent.getCode()== KeyCode.ENTER){
            try {

                Parser parser = new Parser();
                ArrayList<Command> cmds = parser.parse((new Tokenizer(cmdInput.getText())).getTokens());
                Iterator<Command> it = cmds.iterator();
                while (it.hasNext()) {
                    Command c = (Command) it.next();
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
        if(keyEvent.getCode()==KeyCode.DOWN){
        	if(pos+1<prevCommands.size()) {
            pos++;
            cmdInput.setText(prevCommands.get(pos));
        	}

        }
        if(keyEvent.getCode()==KeyCode.UP){
        	if(pos>0) {
        		pos--;
                cmdInput.setText(prevCommands.get(pos));
        	}
        
    }
}
    /**
     * Megynitja azt az ablakot ahol az eljárásokat menedzselhetjük.
     * @param actionEvent	akció
     * @throws IOException	ha nem sikerül betölteni az adott fxml fájlt.
     */
    public void openClicked(ActionEvent actionEvent) throws IOException{
        Stage newStage=new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FunctionList.fxml"));
        Parent root = loader.load();
        newStage.setTitle("Add Function");
        Scene scene=new Scene(root,300,500);
        newStage.setScene(scene);
        newStage.getIcons().add(new Image("images/premiumboi.png"));
        newStage.initModality(Modality.NONE);
        newStage.initOwner(drawingBoard.getScene().getWindow());

        newStage.show();
    }
    /**
     * Megnyit egy fájlválasztó ablakot, ahol egy json fájlt tudunk elmenteni, ebbe az eljárások jelenlegi állapotukban kerülnek bele.
     * @param actionEvent
     * @throws IOException
     */
    public void ExportFunctions(ActionEvent actionEvent) throws IOException{
        fileChooser.getExtensionFilters().clear();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON files (*.json)","*.json"));
        File selected=fileChooser.showSaveDialog(content.getScene().getWindow());
        FunctionStorage.getInstance().Save(selected.getAbsolutePath());
    }

    /**
     * Elmenti a rajzfelületet egy .png képfájlba.
     * @param actionEvent
     */
    public void saveCanvas(ActionEvent actionEvent) {
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
     * Egy felugró ablak megjelenik a programról néhány információval.
     * @param actionEvent
     */
    public void aboutClicked(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About");
        alert.setHeaderText("About this program");
        alert.setContentText("Szûcs Ádám - Programozas alapjai 3 HF \"Magic Poloska\" \n(BME 2020)");

        alert.showAndWait();
    }
    /**
     * Egy új rajz készítését kezdi. A képet megjeleníti és elforgatja, a rajzfelületet alaphelyzetbe állítja.
     * @param actionEvent
     */
    public void newDrawing(ActionEvent actionEvent) {
        imagePoloska.setVisible(true);
        p.resetCanvas();
        rotateImage();
        cmdInput.setEditable(true);
    }
    /**
     * A poloskát tartalmazó képet hozzáigazítja a poloska valódi fordulásához.
     * A forgás középpontját a kép középpontjára állítja.
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
    /**Bezáródik a program */
    public void exitClicked(ActionEvent actionEvent) {
        Platform.exit();
    }
}
