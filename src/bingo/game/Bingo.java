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
        /*
        Cardboard cardboard = new Cardboard(new LineChecker());

        //Cardboard cardboard = new Cardboard(new FullChecker());
        
        //Para mostrar por pantalla
        Scanner input = new Scanner(System.in);
        boolean keepPlaying = true;
        List<Integer> blacklist = new ArrayList<>();
        while(keepPlaying){
            String print = cardboard.render();

            System.out.println(print);
            int number = 1;
            while(true){
                Random random = new Random(new Date().getTime());
                number = random.nextInt(75)+1;
                if (!blacklist.contains(number)) {
                        blacklist.add(number);
                        break;
                }
            }
            System.out.println("El siguiente numero es: "+number);
            System.out.println("Marcar casilla: ");
            String position = input.next();
            cardboard.checkIfPresent(position);
            if(cardboard.checkBingo(position)){
                keepPlaying = false;
            }
        
        }
        System.out.println("");
        System.out.println("");
        
        String print = cardboard.render();
        System.out.println(print);
        
        System.out.println("BINGO MANAOOO, SI SIRVE ESTA VAINAAA");
        //for (BingoValue row: cardboard.valueRow(0)) cardboard.checkIfPresent(row);
//        int row = 6;
//        for (BingoValue[] col: cardboard.getSquares().values()) cardboard.checkIfPresent(col[row--]);
        // Victory victory = new LinearVictory();
        // System.out.println("\ncardboard = \n" + cardboard);
        // System.out.println("hasBingo = " + victory.hasBingo(cardboard));

         */
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader carga = new FXMLLoader(getClass().getResource("/bingo/vistas/inicio.fxml"));
        Parent root = carga.load();
        Scene scene = new Scene(root,600,400);
        scene.getStylesheets().add("/bingo/vistas/MyStyles.css");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.setTitle("Bingo!");
        primaryStage.show();
    }
}
