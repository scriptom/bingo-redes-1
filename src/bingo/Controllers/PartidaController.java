/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bingo.Controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author Cesar
 */
public class PartidaController implements Initializable {
    @FXML
    private GridPane firstCardboard;
    @FXML
    private GridPane secondCardboard;
    @FXML
    private Pane secondCardboardView;
    @FXML
    static Stage ventana;
    
    @FXML
    private Label generatedNumberLabel;

    static int numberOfCardboards = 1;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("se inicia");
        generatedNumberLabel.setStyle("-fx-font-size: 40");
        generatedNumberLabel.setAlignment(Pos.TOP_CENTER);
        if (PartidaController.numberOfCardboards == 1){
            this.llenar(firstCardboard);
            secondCardboardView.setVisible(false);
            return;
        }
        this.llenar(firstCardboard);
        this.llenar(secondCardboard);
    }
    public void llenar(GridPane cardboard) {
        generatedNumberLabel.setText(Integer.toString(43));
        int value = 0;
        for (int i = 0; i<5; i++) {
             for (int j = 0; j<5; j++) {
                 if (value == 12) {
                     value++;
                     continue;
                 }
                Button numberButton = new Button();
                numberButton.setText(" "+Integer.toString(value));
                numberButton.setId(Integer.toString(value));
                numberButton.setStyle("-fx-background-color: transparent;");
                numberButton.setOnAction(new EventHandler<ActionEvent>() {
                    @Override public void handle(ActionEvent e) {
                        System.out.println("button pressed = "+numberButton.idProperty().getValue());
                        //aqui se verifica si la marca en el carton es valida
                        if (true) { // y si lo es, se envia el feedback
                            numberButton.setStyle("-fx-background-color: green;");
                        }
                    }
                });
                cardboard.add(numberButton, j, i, 1, 1);
                value++;
             }
        }
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

    @FXML
    private void victoria(ActionEvent event) throws Exception{

            Parent root = FXMLLoader.load(getClass().getResource("/bingo/vistas/Victoria.fxml"));
            Scene scene = new Scene(root, 400, 200);
            scene.getStylesheets().add("/bingo/vistas/MyStyles.css");
            Stage alerta = new Stage(StageStyle.TRANSPARENT);
            alerta.setScene(scene);
            scene.setFill(Color.TRANSPARENT);
            alerta.initStyle(StageStyle.UNDECORATED);
            alerta.setResizable(false);
            alerta.show();
        /* Hay que meter la validacion del carton en este punto
           if (true){
                VictoriayFalsoController.victoria= true;
            }
            */
    }
}
