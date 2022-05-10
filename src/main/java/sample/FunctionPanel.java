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
	/**Változó hozzáadása */
    public Button addVarButton;
    /**Változók listája */
    public ListView<String> varList;
    /**Új változó neve */
    public TextField varInput;
    /**Az eljáráshoz kapcolódó utasítás sorozat helye. */
    public TextArea commandsTextArea;
    /**A hozzáadás befejezésére a gomb. */
    public Button okButton;
    /**Az eljárás nevének a beviteli mezõje */
    public TextField nameInput;
    /**Ez aváltozó jelzi, hogy éppen szerkesztünk, vagy egy teljesen új eljárást veszünk fel. */
    private boolean editMode=false;
    /**Változókat tároló lista adat rétege*/ 
    ObservableList<String> data=FXCollections.observableArrayList();
    /**
     * Hozzáad egy új változót a listához, amennyiben még nincs a listában az adott elem.
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
     * A kész eljárást elmenti a tárolóba, ha még nincs ilyen nevû eljárás.
     * Ha szerkesztünk egy eljárást akkor elõször kitörli a régit majd hozzáadja az újat.
     * A végén bezárja az ablakot.
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
