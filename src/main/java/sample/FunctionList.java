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
	/**Az elj�r�sokat tartalmaz� lista */
    public ListView<Function> functionList;
    /**Az �j elj�r�sok felv�tel��rt felel�s gomb. */
    public Button addNew;
    /**A f�lban l�v� elj�r�sok felv�tel��rt felel�s gomb. */
    public Button importFunc;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        functionList.setSelectionModel(new NoSelectionModel<>());
        functionList.setItems(FunctionStorage.getInstance().getList());
        functionList.setFocusTraversable(false);
        functionList.setStyle("-fx-control-inner-background-alt: -fx-control-inner-background ;");
        functionList.setCellFactory((Callback<ListView<Function>, ListCell<Function>>) functionListView -> new FunctionCellController());

    }
    /**
     * Megnyit egy �j ablakot ahol lehet�s�g�n van egy �j elj�r�s felv�tel�re.
     * @param actionEvent	A gener�l� event.
     * @throws IOException	Amennyiben nem tudjuk megnyitni a k�v�nt fxml-t
     */
    public void addNewFunc(ActionEvent actionEvent) throws IOException {
        Stage stage=new Stage();
        FXMLLoader loader =new FXMLLoader(getClass().getResource("/functionPanel.fxml"));
        Parent root = (Parent)loader.load();
        stage.setTitle("Magic Poloska");
        stage.setScene(new Scene(root, 600, 300));
        stage.getIcons().add(new Image("/images/premiumboi.png"));
        stage.show();
    }
    /**
     * Megnyit egy ablakot ahol lehet�s�g�nk van kiv�lasztani egy .json f�jlt amib�l beolvassuk a benne l�v� elj�r�sokat. 
     * @param actionEvent
     * @throws IOException
     */
    public void importFunctions() throws IOException{
        FileChooser fileChooser=new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON files (*.json)","*.json"));
        File selectedFile =fileChooser.showOpenDialog(addNew.getScene().getWindow());
        FunctionStorage.getInstance().Load(selectedFile.getAbsolutePath());
    }
}
