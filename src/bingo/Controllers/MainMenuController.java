/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bingo.Controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;

import java.io.IOException;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Cesar
 */
public class MainMenuController implements Initializable {
    @FXML
    private Button oneCardboard = new Button();
    @FXML
    private Button twoCardboards = new Button();
    
    /**
     * Initializes the controller class.
     */
    public void setNumberOfCardboards(int value) {
        PartidaController.numberOfCardboards = value;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        oneCardboard.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                System.out.println("1 carton");
                PartidaController.numberOfCardboards = 1;
            }
         });
        
        twoCardboards.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                System.out.println("2 cartones");
                PartidaController.numberOfCardboards = 2;
            }
         });
    }
    
    @FXML
    private void goToPartida(ActionEvent event) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("/bingo/vistas/Partida.fxml"));
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/bingo/vistas/MyStyles.css");
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.setTitle("Jugando Bingo!");
        window.show();
        PartidaController.ventana = window;
    }

    @FXML
    private void salir(ActionEvent event) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/bingo/vistas/inicio.fxml"));
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/bingo/vistas/MyStyles.css");
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.setTitle("Bingo!");
        window.show();
    }
}
