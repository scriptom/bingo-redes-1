package bingo.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import java.net.URL;
import java.util.ResourceBundle;

public class VictoriayFalsoController {
    //Un flag para saber si la victoria es valida o no
    @FXML
    static boolean victoria = false;

    @FXML
    private Label titulo;

    public void initialize() {
        if (victoria){
            titulo.setText("Juanito Gano");
        }
        else {
            titulo.setText("Juanito ha cantado \nbingo falso");
        }
    }
    private void aceptar(ActionEvent event) throws Exception{
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.close();
        Stage ventana = PartidaController.ventana;
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
        if (victoria){
            System.out.println("hola");
            aceptar(event);
        }
        else{
            bingoFalso(event);
        }
    }
}
