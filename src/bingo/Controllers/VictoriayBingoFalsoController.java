package bingo.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;

public class VictoriayBingoFalsoController {

    @FXML
    private void aceptar(javafx.event.ActionEvent event) throws Exception{
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.close();
        Stage ventana = PartidaController.ventana;
        Parent inicial = FXMLLoader.load(getClass().getResource("/bingo/vistas/inicio.fxml"));
        Scene scene = new Scene(inicial,600,400);
        scene.getStylesheets().add("/bingo/vistas/MyStyles.css");
        ventana.setScene(scene);
        ventana.show();
    }
}
