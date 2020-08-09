package bingo.Controllers;

import bingo.game.BingoGame;
import com.fazecast.jSerialComm.SerialPort;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import bingo.game.Player;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class SelecPortController implements Initializable {
    private BingoGame bingoGame;
    @FXML
    private TableView<SerialPort> puertosTable;
    @FXML
    private TableColumn<SerialPort, String> puertos;  //el primer parametro de la columna corresponderia a la clase del puerto, el segundo permanece como string ya que es el tipo de dato que muestra la celda
    @FXML
    private TableColumn<SerialPort, String> disponibilidad; // Determina si un puerto está en uso o no
    private ObservableList<SerialPort> portList;       //la lista deberia ser de objetos puerto
    private SerialPort writingSerialPort;
    private SerialPort readingSerialPort;

    @FXML
    private TextField playerName;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fillPortsList(); //se rellena el array de puertos
        initTableView(); //se inicializa la tabla con los puertos
    }

    private void initTableView() {
        puertos.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getSystemPortName())); //se debe cargar el puerto como valor de la celda y se pasa como parametro su propiedad nombre o lo que se vaya a mostrar OJO tambien cambia el metodo para el get ya que este es solo con string
        disponibilidad.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().isOpen() ? "NO" : "SI"));
        puertosTable.setItems(portList); //se asigna cada puerto para las celdas, ya que la llamada de arriba solo setea a nivel de interfaz

        this.puertosTable.setOnMouseClicked((event) -> {    //se configura la funcionalidad para seleccionar con click
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                if (readingSerialPort == null) {
                    readingSerialPort = this.puertosTable.getSelectionModel().getSelectedItem(); //en ves de cast a string es a puerto para obtener el puerto seleccionado
                } else {
                    writingSerialPort = this.puertosTable.getSelectionModel().getSelectedItem(); //en ves de cast a string es a puerto para obtener el puerto seleccionado
                }
            }
            System.out.println("lectura: " + readingSerialPort);
            System.out.println("escritura: " + writingSerialPort);
        });
    }

    @FXML
    private void goBack(ActionEvent event) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/bingo/vistas/MainMenu.fxml"));
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/bingo/vistas/MyStyles.css");
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.setTitle("Bingo!");
        window.show();
    }

    public void fillPortsList() { //se rellena la lista con los puertos
        portList = FXCollections.observableArrayList(SerialPort.getCommPorts());
    }

    @FXML
    private void goToPartida(ActionEvent event) throws IOException { //se va a la vista del juego
        String hostPlayerName = playerName.getText();
        Player player = Player.getInstance();
        player.setName(hostPlayerName);
        player.setWritingSerialPort(writingSerialPort);
        player.setReadingSerialPort(readingSerialPort);
        if (!isNewGame()) {
            player.joinExistingGame();
            bingoGame = player.getGame();
        } else {
            bingoGame.setHostPlayer(player);
            bingoGame.addPlayer(player);
        }
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/bingo/vistas/Partida.fxml"));
        Parent root = loader.load();
        PartidaController controller = loader.getController();
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/bingo/vistas/MyStyles.css");
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.setTitle("Jugando Bingo!");
        window.show();
        controller.setVentana(window);
    }

    public void setBingoGame(BingoGame bingoGame) {
        this.bingoGame = bingoGame;
    }

    private boolean isNewGame() {
        return bingoGame.isHostInstance();
    }
}
