package bingo.game;

import java.awt.event.ActionEvent;
import java.io.IOException;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.*;


public class Bingo extends Application{
    /* Aqui comienzan las funciones implementadas en dev, por ahora estan comentadas para
   crear las vistas
    public static void main(String[] args) {
        Cardboard cardboard = new Cardboard();
        for (BingoValue row: cardboard.valueRow(0)) cardboard.checkIfPresent(row);
//        int row = 6;
//        for (BingoValue[] col: cardboard.getSquares().values()) cardboard.checkIfPresent(col[row--]);
        Victory victory = new LinearVictory();
        System.out.println("\ncardboard = \n" + cardboard);
        System.out.println("hasBingo = " + victory.hasBingo(cardboard));
    }
     */

    @Override
    public void start (Stage primaryStage) throws Exception{
        Parent inicial = FXMLLoader.load(getClass().getResource("/bingo/vistas/inicio.fxml"));
        Scene scene = new Scene(inicial,600,400);
        primaryStage.setTitle("Bienvenido al Bingo!");
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        scene.getStylesheets().add("/bingo/vistas/MyStyles.css");
        primaryStage.show();
    }

    @FXML
    public void button(ActionEvent event) throws IOException {
        System.out.println("Button");
    }

    public static void main(String[] args) {
        launch(args);
    }
}