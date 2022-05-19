package sample;

import Function.Function;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import variables.FunctionStorage;

import java.io.IOException;

public class FunctionCellController extends ListCell<Function> {
	/**Az elj�r�s nev�t tartalmaz� label. */
    Label name=new Label();
    /**Az elj�r�s param�tereit tartalmaz� label. */
    Label params=new Label();
    /**A labeleket tartalmaz� t�rol�. */
    private VBox labels=new VBox(-6);
    /**Az elj�r�s t�rl�s�re haszn�lt gomb. */
    Button btnDel=new Button("\uD83D\uDDD1");
    /**Az elj�r�s szerkeszt�s�re haszn�lt gomb. */
    Button btnEdit=new Button("\u270f");
    /**A gy�k�r t�rol� */
    private BorderPane root=new BorderPane();
    /**A gombokat tartalmaz� t�rol�. */
    private HBox btns=new HBox(4);
    /**
     * Be�ll�tja az adott gomb param�tereit a k�v�ntra.
     * 
     * @param btn	A gomb aminek �ll�tjuk az �rt�keit.
     * @param color	A sz�n amire �tv�lt ha felette van a kurzor.
     */
    private void setUpButton(Button btn, String color){
        btn.setPadding(new Insets(0,0,0,0));
        btn.setAlignment(Pos.CENTER);
        btn.setPrefSize(30,30);
        btn.setStyle("-fx-font-size: 20;-fx-background-color: darkgrey;");
        btn.setOnMouseEntered(e->btn.setStyle("-fx-font-size: 15;-fx-background-color:" +color+ ";"));
        btn.setOnMouseExited(e->btn.setStyle("-fx-font-size: 20;-fx-background-color: darkgray;"));
    }
    public FunctionCellController(){
        name.setStyle("-fx-font-weight: bold;-fx-font-size: 15pt;");
        setUpButton(btnDel,"#a83246");
        setUpButton(btnEdit,"#32a8a2");
        btns.getChildren().add(btnEdit);
        btns.getChildren().add(btnDel);
        btns.setAlignment(Pos.CENTER_RIGHT);
        labels.getChildren().add(name);
        labels.getChildren().add(params);
        root.setCenter(labels);
        root.setRight(btns);
        labels.setAlignment(Pos.TOP_LEFT);
        BorderPane.setMargin(btns, new Insets(5,10,5,5));
        BorderPane.setMargin(labels, new Insets(2,10,5,5));
        root.setStyle("-fx-background-color: lightgrey; -fx-background-radius: 5");
        btnDel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                FunctionStorage str=FunctionStorage.getInstance();
                str.removeFunction(getItem());

            }
        });

        btnEdit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try{
                    Stage stage=new Stage();
                    FXMLLoader loader =new FXMLLoader(getClass().getResource("/functionPanel.fxml"));
                    Parent root = (Parent)loader.load();
                    FunctionPanel controller = loader.getController();
                    controller.nameInput.setText(getItem().name);
                    controller.okButton.setText("Save Changes");
                    controller.nameInput.setEditable(false);
                    controller.commandsTextArea.setText(getItem().commandStr);
                    controller.varList.getItems().addAll(getItem().variables);
                    controller.setEditMode(true);
                    stage.setTitle("Magic Poloska");
                    stage.setScene(new Scene(root, 600, 300));
                    stage.getIcons().add(new Image("/images/premiumboi.png"));
                    stage.show();
                }catch(IOException e){
                    e.printStackTrace();
                }
            }
        });
        setPrefWidth(USE_PREF_SIZE);
    }
    /**
     * Be�ll�tja a labeleket az aktu�lis elj�r�s �rt�keire.
     */
    @Override
    protected void updateItem(Function function, boolean empty) {
        super.updateItem(function, empty);
        if(empty|| function==null){
            setGraphic(null);
        } else{
          name.setText(function.name+"\n");
          params.setText(function.variables.toString());
          setGraphic(root);
        }
    }
}
