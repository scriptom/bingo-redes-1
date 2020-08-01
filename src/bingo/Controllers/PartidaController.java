/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bingo.Controllers;

import java.net.URL;
import java.util.LinkedHashMap;
import java.util.ResourceBundle;

import bingo.game.BingoNumber;
import bingo.game.cardboard.BingoValue;
import bingo.game.cardboard.Cardboard;
import bingo.game.checker.LineChecker;
import javafx.css.PseudoClass;
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

    public static Cardboard cardboard;
    public static Cardboard cardboard2;
    public static int currentPlayingNumber = BingoNumber.getNumberToPlay();

    //Crear Pseudoclass para el css
    private static final String MARCADO = "marcado";

    private static final PseudoClass SELECTED_PSEUDO_CLASS =
            PseudoClass.getPseudoClass("selected");


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Cada vez que se inicie una partida, victoria se pondra en falso para que funcione correctamente el bingo
        VictoriayFalsoController.victoria = false;
        this.cardboard = new Cardboard(new LineChecker());
        generatedNumberLabel.setStyle("-fx-font-size: 40");
        generatedNumberLabel.setAlignment(Pos.TOP_CENTER);
        this.llenar(firstCardboard, cardboard.getSquares(), 1);
        if (PartidaController.numberOfCardboards == 1){
            secondCardboardView.setVisible(false);
            return;
        }
        this.cardboard2 = new Cardboard(new LineChecker());
        this.llenar(secondCardboard, cardboard2.getSquares(), 2);
    }

    public void llenar(GridPane cardboard, LinkedHashMap<String, BingoValue> squares, int numberCardboard) {
        generatedNumberLabel.setText(Integer.toString(currentPlayingNumber));
        int value = 0;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (value == 12) {
                    value++;
                    continue;
                }
                Button numberButton = new Button();
                //Se toma el numero de la casilla creada en la clase Cardboard
                numberButton.setText( Integer.toString( squares.get( getLetter(j+1)+Integer.toString(i+1) ).getNumber()) );
                //Se establece
                numberButton.setId(Integer.toString(numberCardboard) + getLetter(j+1) + Integer.toString(i+1));
                //Agrega el CSS del boton
                numberButton.getStyleClass().add(MARCADO);
                numberButton.setOnAction(new EventHandler<ActionEvent>() {

                    @Override public void handle(ActionEvent e) {
                        System.out.println("button pressed = "+numberButton.idProperty().getValue());
                        String position = numberButton.getId();
                        System.out.println("Posicion a marcar: "+position);
                        String number = numberButton.getId().substring(0,1);
                        String bingoNumber = numberButton.getId().substring(1,3);
                        System.out.println("numero marcado ="+squares.get(bingoNumber).getNumber());
                        if (squares.get(bingoNumber).getNumber() == currentPlayingNumber) { // VALIDACION SI ES EL NUMERO QUE SE GENERA EN LA PARTIDA
                            System.out.println("Carton a marcar: " + number);
                            if((number.equals("1")) && (cardboard != null)){
                                //Se marca la casilla en la logica
                                PartidaController.cardboard.checkIfPresent(position);
                                //Se valida el bingo
                                if(PartidaController.cardboard.checkBingo(position)){
                                    VictoriayFalsoController.victoria = true;
                                }
                            }
                            if((number.equals("2")) && (cardboard2 != null)){
                                //Se marca la casilla en la logica
                                PartidaController.cardboard2.checkIfPresent(position);
                                //Se valida el bingo
                                if(PartidaController.cardboard2.checkBingo(position)){
                                    VictoriayFalsoController.victoria = true;
                                }
                            }
                            numberButton.pseudoClassStateChanged(SELECTED_PSEUDO_CLASS,true);
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
        Parent root = FXMLLoader.load(getClass().getResource("/bingo/vistas/Inicio.fxml"));
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


    /**
    * @param index numero de la columna en la que se encuentra la iteracion
    * @return  Letra la columna en la que se esta iterando
     */
    private String getLetter(int index){
        switch (index){
            case 1: return "B";
            case 2: return "I";
            case 3: return "N";
            case 4: return "G";
            case 5: return "O";
            default: return "P";
        }
    }
}
