package sample;

import Function.Function;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import variables.FunctionStorage;

import java.net.URL;
import java.util.ResourceBundle;

public class FunctionPanel implements Initializable {
	/**V�ltoz� hozz�ad�sa */
    public Button addVarButton;
    /**V�ltoz�k list�ja */
    public ListView<String> varList;
    /**�j v�ltoz� neve */
    public TextField varInput;
    /**Az elj�r�shoz kapcol�d� utas�t�s sorozat helye. */
    public TextArea commandsTextArea;
    /**A hozz�ad�s befejez�s�re a gomb. */
    public Button okButton;
    /**Az elj�r�s nev�nek a beviteli mez�je */
    public TextField nameInput;
    /**Ez av�ltoz� jelzi, hogy �ppen szerkeszt�nk, vagy egy teljesen �j elj�r�st vesz�nk fel. */
    private boolean editMode=false;
    /**V�ltoz�kat t�rol� lista adat r�tege*/ 
    ObservableList<String> data=FXCollections.observableArrayList();
    /**
     * Hozz�ad egy �j v�ltoz�t a list�hoz, amennyiben m�g nincs a list�ban az adott elem.
     * @param actionEvent
     */
    public void addVar(ActionEvent actionEvent) {
        if(!data.contains(varInput.getText()))
            data.add(varInput.getText());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ContextMenu listMenu=new ContextMenu();
        MenuItem del=new MenuItem("Delete");
        del.setOnAction((event)->data.remove(varList.getSelectionModel().getSelectedItem()));
        listMenu.getItems().add(del);
        varList.setContextMenu(listMenu);
        varList.setItems(data);
    }
    public void setEditMode(boolean edit){
        editMode=edit;
    }
    /**
     * A k�sz elj�r�st elmenti a t�rol�ba, ha m�g nincs ilyen nev� elj�r�s.
     * Ha szerkeszt�nk egy elj�r�st akkor el�sz�r kit�rli a r�git majd hozz�adja az �jat.
     * A v�g�n bez�rja az ablakot.
     * @param actionEvent
     */
    public void saveFunction(ActionEvent actionEvent) {
        FunctionStorage str=FunctionStorage.getInstance();
        Function f=new Function(nameInput.getText(),data,commandsTextArea.getText());
        if(editMode){
            str.RemoveFunctionByName(f.name);
        }
       if(!str.HasFunction(f.name)){
           str.AddFunction(f);
       }

        Stage stage =(Stage)okButton.getScene().getWindow();
        stage.close();
    }
}
