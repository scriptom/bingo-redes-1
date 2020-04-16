/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bingo.Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 *
 * @author Cesar
 */
public class PartidaController implements Initializable {
    @FXML
    private GridPane carton1;
    @FXML
    private Pane secondCardboard;
    static int numberOfCardboards = 1;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("se inicia");
        if (PartidaController.numberOfCardboards == 1){
            secondCardboard.setVisible(false);
        }
        this.llenar();
    }
    public void llenar() {
        int value = 0;
        for (int i = 0; i<5; i++) {
             for (int j = 0; j<5; j++) {
                 if (value == 12) {
                     value++;
                     continue;
                 }
                Label numberLabel = new Label();
                numberLabel.setText(Integer.toString(value));
                carton1.add(numberLabel, j, i, 1, 1);
                value++;
             }
        }
    }
}
