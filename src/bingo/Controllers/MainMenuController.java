/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bingo.Controllers;

import bingo.game.BingoGame;
import bingo.game.checker.BingoChecker;
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
public class MainMenuController implements Initializable, Controller {
    private BingoChecker bingoChecker;
    private BingoGame bingoGame;
    private int maxCardboards;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        bingoGame = BingoGame.getInstance();
    }

    @FXML
    private void goToPuertos(ActionEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/bingo/vistas/Selecport.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/bingo/vistas/MyStyles.css");
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        // Se supone que ya tenemos un bingo seleccionado... no?
        bingoGame.setBingoChecker(bingoChecker);
        bingoGame.setMaxCardboards(maxCardboards);
        // Pasamos de vuelta el inicio del bingo
        SelecPortController controller = loader.getController();
        controller.setBingoGame(bingoGame);
        window.setScene(scene);
        window.setTitle("Jugando Bingo!");
        window.show();
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
                bingoChecker = new LineChecker();
                break;
            case "fullBingo":
                System.out.println("Bingo Full Cartonn");
                bingoChecker = new FullChecker();
                break;
        }
    }

    public void handleCardboardSelection(ActionEvent event) {
        Button button = (Button) event.getSource();
        maxCardboards = Integer.parseInt(((String) button.getUserData()));
    }

    public void setBingoGame(BingoGame bingoGame) {
        this.bingoGame = bingoGame;
    }
}
