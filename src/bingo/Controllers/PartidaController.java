/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bingo.Controllers;

import bingo.game.Player;
import bingo.game.cardboard.BingoValue;
import bingo.game.cardboard.Cardboard;
import bingo.game.checker.BingoChecker;
import bingo.game.checker.LineChecker;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.css.PseudoClass;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.net.URL;
import java.util.*;

/**
 *
 * @author Cesar
 */
public class PartidaController implements Initializable {
    @FXML
    private Label lblPlayerName;
    @FXML
    private TableView<Player> playerTable;
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

    private SimpleIntegerProperty generatedNumber = new SimpleIntegerProperty(this, "generatedNumber");

    //Crear Pseudoclass para el css
    private static final String MARCADO = "marcado";

    private static final PseudoClass SELECTED_PSEUDO_CLASS =
            PseudoClass.getPseudoClass("selected");

    public static BingoChecker bingoType;

    /**
     * Mapa de jugadores
     * TODO: Estudiar como popularlo
     */
    private ObservableList<Player> players;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        prepareGame();
        joinPlayer(Player.getInstance());
        initGameView();
    }

    private void initGameView() {
        initTableView();
        lblPlayerName.setText(Player.getInstance().getName());
    }

    private void prepareGame() {
        // Cada vez que se inicie una partida, victoria se pondra en falso para que funcione correctamente el bingo
        VictoriayFalsoController.victoria = false;
        int numberOfCardboards = Player.getInstance().getNumberOfCardboards();
        Cardboard[] cardboards = new Cardboard[numberOfCardboards];
        GridPane[] panes = new GridPane[] { firstCardboard, secondCardboard };
        for (int i = 0; i < numberOfCardboards; i++) {
            cardboards[i] = new Cardboard(bingoType);
            fill(panes[i], cardboards[i].getSquares(), i + 1);
        }
        Player.getInstance().setCardboards(cardboards);
        if (numberOfCardboards == 1){
            secondCardboardView.setVisible(false);
        }
    }

    private void initTableView() {
        playerTable.setItems(players);
        TableColumn<Player, String> nameCol = new TableColumn<>("Jugador");
        TableColumn<Player, Integer> numCbCol = new TableColumn<>("# Cartones");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        numCbCol.setCellValueFactory(new PropertyValueFactory<>("numberOfCardboards"));
        //noinspection unchecked
        playerTable.getColumns().setAll(nameCol, numCbCol);
    }

    /**
     * Mete a un jugador en la partida
     * @param player Jugador a meter
     */
    @SuppressWarnings("WeakerAccess")
    public void joinPlayer(Player player) {
        if (null == players) {
            players = FXCollections.observableArrayList();
        }
        players.add(player);
    }

    private void fill(GridPane cardboard, LinkedHashMap<String, BingoValue> squares, int numberCardboard) {
        generatedNumberLabel.setText(Integer.toString(43));
        int value = 0;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (value == 12) {
                    value++;
                    continue;
                }

                Button numberButton = new Button();
                String letter = getLetter(j + 1);
                String position = letter + (i + 1);
                int number = squares.get(position).getNumber();

                //Se toma el numero de la casilla creada en la clase Cardboard
                numberButton.setText(Integer.toString(number));
                //Se establece
                numberButton.setId(numberCardboard + position);
                // Propiedades personalizadas para facilitar la busqueda de informacion del boton
                numberButton.setUserData(generateButtonProperites(numberCardboard, position));
                //Agrega el CSS del boton
                numberButton.getStyleClass().add(MARCADO);
                numberButton.setOnAction(this::handleButtonClick);
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
    private void handleVictoryButtonClick(ActionEvent event) throws Exception{

            //Se obtienen los cartones del player
            Cardboard[] cardboards = Player.getInstance().getCardboards();
            boolean bingo = false;
            //Validacion de si hay bingo
            for (Cardboard c : cardboards) {
                if(c.checkBingo()){
                    bingo = true;
                    break;
                }
            }
            if (bingo){
                VictoriayFalsoController.victoria= true;
            }

            Parent root = FXMLLoader.load(getClass().getResource("/bingo/vistas/Victoria.fxml"));
            Scene scene = new Scene(root, 400, 200);
            scene.getStylesheets().add("/bingo/vistas/MyStyles.css");
            Stage alerta = new Stage(StageStyle.TRANSPARENT);
            alerta.setScene(scene);
            scene.setFill(Color.TRANSPARENT);
            alerta.initStyle(StageStyle.UNDECORATED);
            alerta.setResizable(false);
            alerta.show();
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

    /**
     * Genera data personalizada para los botones del numero
     * @param cardIndex Indice del carton al que pertenece el numero
     * @param position  La posicion dentro del carton
     * @return Propiedades a agregar
     */
    private Properties generateButtonProperites(Integer cardIndex, String position) {
        Properties userData = new Properties();
        userData.put("cardboard", cardIndex);
        userData.put("position", position);

        return userData;
    }

    /**
     * Manejador de clicks en botones del carton.
     * @param event Objeto del evento
     */
    private void handleButtonClick(ActionEvent event) {
        Button button = (Button) event.getSource();
        Properties userData = ((Properties) button.getUserData());
        System.out.println("Clicked");
        if (null != userData) {
            // Solamente procesamos si no ha sido marcado
            if (null == userData.get("checked")) {
                System.out.println("Checking");
                int cardboardIndex = ((Integer) userData.get("cardboard")) - 1;
                String position = (String) userData.get("position");
                /*
                ES NECESARIO VALIDAR SI EL NUMERO QUE SALIO ES EL MISMO QUE TIENE EL CUADRO PARA PODER MARCARLO
                 */
                Player.getInstance().getCardboard(cardboardIndex).checkIfPresent(position);
                button.pseudoClassStateChanged(SELECTED_PSEUDO_CLASS, true);
                userData.put("checked", true);
            }
        }
    }
}
