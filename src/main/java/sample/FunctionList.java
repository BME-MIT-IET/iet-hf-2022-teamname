package sample;

import Function.Function;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;

import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.fxml.Initializable;
import variables.FunctionStorage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class FunctionList implements Initializable {
	/**Az eljárásokat tartalmazó lista */
    public ListView<Function> functionList;
    /**Az új eljárások felvételéért felelõs gomb. */
    public Button addNew;
    /**A fálban lévõ eljárások felvételéért felelõs gomb. */
    public Button importFunc;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        functionList.setSelectionModel(new NoSelectionModel<>());
        functionList.setItems(FunctionStorage.getInstance().getList());
        functionList.setFocusTraversable(false);
        functionList.setStyle("-fx-control-inner-background-alt: -fx-control-inner-background ;");
        functionList.setCellFactory((Callback<ListView<Function>, ListCell<Function>>) functionListView -> new FunctionCellController(this));

    }
    /**
     * Megnyit egy új ablakot ahol lehetõségün van egy új eljárás felvételére.
     * @param actionEvent	A generáló event.
     * @throws IOException	Amennyiben nem tudjuk megnyitni a kívánt fxml-t
     */
    public void addNewFunc(ActionEvent actionEvent) throws IOException {
        Stage stage=new Stage();
        FXMLLoader loader =new FXMLLoader(getClass().getResource("functionPanel.fxml"));
        Parent root = (Parent)loader.load();
        stage.setTitle("Magic Poloska");
        stage.setScene(new Scene(root, 600, 300));
        stage.getIcons().add(new Image("images/premiumboi.png"));
        stage.show();
    }
    /**
     * Megnyit egy ablakot ahol lehetõségünk van kiválasztani egy .json fájlt amibõl beolvassuk a benne lévõ eljárásokat. 
     * @param actionEvent
     * @throws IOException
     */
    public void importFunctions(ActionEvent actionEvent) throws IOException{
        FileChooser fileChooser=new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON files (*.json)","*.json"));
        File selectedFile =fileChooser.showOpenDialog(addNew.getScene().getWindow());
        FunctionStorage.getInstance().Load(selectedFile.getAbsolutePath());
    }
}
