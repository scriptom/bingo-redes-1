/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bingo.Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import bingo.game.Bingo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class HomeController implements Initializable {
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    private void goToLobby(ActionEvent event) throws IOException{
        System.out.println("Lobby");
        FXMLLoader carga = new FXMLLoader(getClass().getResource("/bingo/vistas/MainMenu.fxml"));
        Parent root = (Parent)carga.load();
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/bingo/vistas/MyStyles.css");
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.setTitle("Lobby");
        window.show();
    }

    @FXML
    private void salir(ActionEvent event){
        System.exit(0);
    }
}
