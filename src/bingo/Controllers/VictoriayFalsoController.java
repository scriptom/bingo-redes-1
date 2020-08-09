package bingo.Controllers;

import bingo.game.BingoGame;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

public class VictoriayFalsoController {
    //Un flag para saber si la victoria es valida o no
    @FXML
    private BooleanProperty victoria = new SimpleBooleanProperty(this, "victoria", false);

    private BingoGame bingoGame;

    private Controller invoker;

    @FXML
    private Label titulo;

    public void initialize() {
        victoria.addListener((observable, oldValue, newValue) -> titulo.setText(newValue ? "Juanito tiene bingo!" : "Juanito tiene bingo falso"));
    }
    private void aceptar(ActionEvent event) throws Exception {
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.close();
        Stage ventana = ((PartidaController)invoker).getVentana();
        Parent inicial = FXMLLoader.load(getClass().getResource("/bingo/vistas/inicio.fxml"));
        Scene scene = new Scene(inicial,600,400);
        scene.getStylesheets().add("/bingo/vistas/MyStyles.css");
        ventana.setScene(scene);
        ventana.show();
    }

    private void bingoFalso(ActionEvent event){
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.close();
    }
    @FXML
    private void elegir(ActionEvent event) throws Exception{
        if (victoria.get()){
            System.out.println("hola");
            aceptar(event);
        }
        else{
            bingoFalso(event);
        }
    }

    public Controller getInvoker() {
        return invoker;
    }

    public void setInvoker(Controller invoker) {
        this.invoker = invoker;
    }

    public BingoGame getBingoGame() {
        return bingoGame;
    }

    public void setBingoGame(BingoGame bingoGame) {
        this.bingoGame = bingoGame;
    }

    public void setVictoria(boolean victoria) {
        this.victoria.set(victoria);
    }
}
