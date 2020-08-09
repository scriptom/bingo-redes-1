package bingo.game;

import bingo.game.cardboard.Cardboard;
import bingo.game.checker.LineChecker;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.*;

import static javafx.application.Application.launch;

public class Bingo extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader carga = new FXMLLoader(getClass().getResource("/bingo/vistas/Inicio.fxml"));
        Parent root = carga.load();
        Scene scene = new Scene(root,600,400);
        scene.getStylesheets().add("/bingo/vistas/MyStyles.css");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.setTitle("Bingo!");
        primaryStage.show();
    }
}
