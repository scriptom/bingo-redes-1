package bingo.Controllers;

import bingo.game.BingoGame;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
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
    private BooleanProperty victoria = new SimpleBooleanProperty(this, "victoria");

    private BingoGame bingoGame;

    private Controller invoker;

    private StringProperty playerName = new SimpleStringProperty(this, "playerName");

    @FXML
    private Label titulo;

    public void initialize() {
        Platform.runLater(() -> {
            titulo.textProperty()
                    .bind(Bindings.createStringBinding(() -> playerName.get() + (victoria.get() ? " ha ganado" : " no tiene bingo"), victoria, playerName));
        });

    }

    private void aceptar(ActionEvent event) throws Exception {
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.close();
        Stage ventana = ((PartidaController) invoker).getVentana();
        Parent inicial = FXMLLoader.load(getClass().getResource("/bingo/vistas/inicio.fxml"));
        Scene scene = new Scene(inicial, 600, 400);
        scene.getStylesheets().add("/bingo/vistas/MyStyles.css");
        ventana.setScene(scene);
        ventana.show();
    }

    private void bingoFalso(ActionEvent event) {
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.close();
    }

    @FXML
    private void elegir(ActionEvent event) throws Exception {
        if (victoria.get()) {
            System.out.println("hola");
            aceptar(event);
        } else {
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

    public void setPlayerName(String name) {
        this.playerName.set(name);
    }
}
