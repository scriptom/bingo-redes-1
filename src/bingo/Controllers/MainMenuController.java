/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bingo.Controllers;

import bingo.game.Player;
import bingo.game.checker.FullChecker;
import bingo.game.checker.LineChecker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 *
 * @author Cesar
 */
public class MainMenuController implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    @FXML
    private void goToPuertos(ActionEvent event) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("/bingo/vistas/Selecport.fxml"));
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/bingo/vistas/MyStyles.css");
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.setTitle("Jugando Bingo!");
        window.show();
        PartidaController.ventana = window;
    }

    @FXML
    private void goBack(ActionEvent event) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/bingo/vistas/Inicio.fxml"));
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/bingo/vistas/MyStyles.css");
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.setTitle("Bingo!");
        window.show();
    }

    @FXML
    private void selectBingo(ActionEvent event){
        String option = ((Button)(event.getSource())).getId();
        System.out.println(option);
        /*
        AQUI SE DEBE ASIGNAR EL VALOR DE LA OPCION A CUAL SEA LA CLASE DE LA CONFIGURACION
         */
        switch (option){
            case "lineBingo":
                System.out.println("Bingo en linea");
                PartidaController.bingoType = new LineChecker();
                break;
            case "fullBingo":
                System.out.println("Bingo Full Cartonn");
                PartidaController.bingoType = new FullChecker();
                break;
            default:
                PartidaController.bingoType = new LineChecker();
        }
    }

    public void handleCardboardSelection(ActionEvent event) {
        Button button = (Button) event.getSource();
        int numberOfCardboards = Integer.parseInt(((String) button.getUserData()));
        Player.getInstance().setNumberOfCardboards(numberOfCardboards);
    }
}
