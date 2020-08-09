/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bingo.Controllers;

import bingo.game.BingoGame;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HomeController implements Initializable, Controller {

    @Override
    public void initialize(URL location, ResourceBundle resources) {}

    @FXML
    private void goToLobby(ActionEvent event) throws IOException{
        System.out.println("Lobby");
        FXMLLoader carga = new FXMLLoader(getClass().getResource("/bingo/vistas/MainMenu.fxml"));
        Parent root = (Parent)carga.load();
        // Objeto de la partida a pasar a través de los controladores
        BingoGame game = BingoGame.getInstance();
        game.setHostInstance(true);
        MainMenuController controller = carga.getController();
        controller.setBingoGame(game);
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/bingo/vistas/MyStyles.css");
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.setTitle("Lobby");
        window.show();
    }
    @FXML
    private void unirse(ActionEvent event) throws IOException {
        FXMLLoader carga = new FXMLLoader(getClass().getResource("/bingo/vistas/SelecPort.fxml"));
        Parent root = (Parent)carga.load();
        SelecPortController controller = carga.getController();
        BingoGame game = BingoGame.getInstance();
        game.setHostInstance(false);
        controller.setBingoGame(game);
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/bingo/vistas/MyStyles.css");
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.setTitle("PORTS");
        window.show();
    }

    @FXML
    private void salir(ActionEvent event){
        System.exit(0);
    }
}
