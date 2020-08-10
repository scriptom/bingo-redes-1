/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bingo.Controllers;

import bingo.comm.Message;
import bingo.game.BingoGame;
import bingo.game.Player;
import bingo.game.cardboard.BingoValue;
import bingo.game.cardboard.Cardboard;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
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
 * @author Cesar
 */
public class PartidaController implements Initializable, Controller {
    private BingoGame bingoGame;
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
    private Stage ventana;

    @FXML
    private Label generatedNumberLabel;

    private ListProperty<Player> playerListProperty;

    private SimpleIntegerProperty generatedNumber = new SimpleIntegerProperty(this, "generatedNumber");

    //Crear Pseudoclass para el css
    private static final String MARCADO = "marcado";

    private static final PseudoClass SELECTED_PSEUDO_CLASS =
            PseudoClass.getPseudoClass("selected");

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        bingoGame = BingoGame.getInstance();
        playerListProperty = new SimpleListProperty<>(bingoGame.getPlayers());
        prepareGame();
        initGameView();
    }

    private void initGameView() {
        initTableView();
//        lblPlayerName.setText(Player.getInstance().getName());
    }

    private void prepareGame() {
        int maxCardboards = bingoGame.getMaxCardboards();
        Cardboard[] cardboards = new Cardboard[maxCardboards];
        GridPane[] panes = new GridPane[]{firstCardboard, secondCardboard};
        for (int i = 0; i < maxCardboards; i++) {
            cardboards[i] = new Cardboard(bingoGame.getBingoChecker());
            fill(panes[i], cardboards[i].getSquares(), i + 1);
        }
        Player.getInstance().setCardboards(cardboards);
        if (maxCardboards == 1) {
            secondCardboardView.setVisible(false);
        }
    }

    private void initTableView() {
        playerTable.itemsProperty().bindBidirectional(playerListProperty);
        TableColumn<Player, String> nameCol = new TableColumn<>("Jugador");
        TableColumn<Player, Integer> numCbCol = new TableColumn<>("# Cartones");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        numCbCol.setCellValueFactory(new PropertyValueFactory<>("numberOfCardboards"));
        //noinspection unchecked
        playerTable.getColumns().setAll(nameCol, numCbCol);
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
                numberButton.setUserData(generateButtonProperties(numberCardboard, position));
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
    private void handleVictoryButtonClick(ActionEvent event) throws Exception {
        Cardboard[] cardboards = Player.getInstance().getCardboards();
        boolean bingo = false;
        //Validacion de si hay bingo
        for (Cardboard c : cardboards) {
            if (c.checkBingo()) {
                System.out.println("BINGO");
                bingo = true;
                break;
            }
        }
        //VALIDAR QUE SE MUESTRE ESTA VISTA SOLO CUANDO SE GANA
        Player player = Player.getInstance();
        player.send(new Message("50",Message.NEXT_NUMBER), player.getWritingSerialPort());
        //bingoGame.setCurrentNumber(50);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/bingo/vistas/Victoria.fxml"));
        Parent root = loader.load();
        VictoriayFalsoController controller = loader.getController();
        controller.setInvoker(this);
        controller.setBingoGame(bingoGame);
        System.out.println("Cambiando Victoria a " + bingo);
        controller.setVictoria(bingo);
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
     * @return Letra la columna en la que se esta iterando
     */
    private String getLetter(int index) {
        switch (index) {
            case 1:
                return "B";
            case 2:
                return "I";
            case 3:
                return "N";
            case 4:
                return "G";
            case 5:
                return "O";
            default:
                return "P";
        }
    }

    /**
     * Genera data personalizada para los botones del numero
     *
     * @param cardIndex Indice del carton al que pertenece el numero
     * @param position  La posicion dentro del carton
     * @return Propiedades a agregar
     */
    private Properties generateButtonProperties(Integer cardIndex, String position) {
        Properties userData = new Properties();
        userData.put("cardboard", cardIndex);
        userData.put("position", position);

        return userData;
    }

    /**
     * Manejador de clicks en botones del carton.
     *
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
                Cardboard cardboard = Player.getInstance().getCardboard(cardboardIndex);
                cardboard.checkIfPresent(position);
                button.pseudoClassStateChanged(SELECTED_PSEUDO_CLASS, true);
                userData.put("checked", true);
            }
        }
    }

    public BingoGame getBingoGame() {
        return bingoGame;
    }

    public void setBingoGame(BingoGame bingoGame) {
        this.bingoGame = bingoGame;
    }

    public Stage getVentana() {
        return ventana;
    }

    public void setVentana(Stage ventana) {
        this.ventana = ventana;
    }
}
