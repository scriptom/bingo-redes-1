package bingo.Controllers;

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
import java.util.ResourceBundle;


public class SelecPortController implements Initializable {
    @FXML
    private TableView<SerialPort> puertosTable;
    @FXML
    private TableColumn<SerialPort, String> puertos;  //el primer parametro de la columna corresponderia a la clase del puerto, el segundo permanece como string ya que es el tipo de dato que muestra la celda
    private ObservableList<SerialPort> portList;       //la lista deberia ser de objetos puerto
    private SerialPort selectedPort[];     //en vez de string seria objeto del puerto

    @FXML
    private TextField playerName;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fillPortsList(); //se rellena el array de puertos
        initTableView(); //se inicializa la tabla con los puertos
        selectedPort =  new SerialPort[2];
        createPlayer(playerName.getText());
    }

    private void initTableView() {
        puertos.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getSystemPortName())); //se debe cargar el puerto como valor de la celda y se pasa como parametro su propiedad nombre o lo que se vaya a mostrar OJO tambien cambia el metodo para el get ya que este es solo con string
        puertosTable.setItems(portList); //se asigna cada puerto para las celdas, ya que la llamada de arriba solo setea a nivel de interfaz

        this.puertosTable.setOnMouseClicked((event) -> {    //se configura la funcionalidad para seleccionar con click
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                if (selectedPort.length == 0) {
                    selectedPort[0] = (SerialPort) this.puertosTable.getSelectionModel().getSelectedItem(); //en ves de cast a string es a puerto para obtener el puerto seleccionado
                    if (selectedPort != null) { //se selecciona el puerto satisfactoriamente
                        System.out.println("Seleccionado puerto " + selectedPort); //aqui dentro se realiza todo lo necesario para asignar el puerto, y se va a la partida a travez del boton en la interfaz
                    }
                }

                else {
                    selectedPort[1] = (SerialPort) this.puertosTable.getSelectionModel().getSelectedItem(); //en ves de cast a string es a puerto para obtener el puerto seleccionado
                    if (selectedPort != null) { //se selecciona el puerto satisfactoriamente
                        System.out.println("Seleccionado puerto " + selectedPort); //aqui dentro se realiza todo lo necesario para asignar el puerto, y se va a la partida a travez del boton en la interfaz
                    }
                }
            }

        });
    }

    public void cargar(ActionEvent event){

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
        createPlayer(playerName.getText());
        Parent root = FXMLLoader.load(getClass().getResource("/bingo/vistas/Partida.fxml"));
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/bingo/vistas/MyStyles.css");
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.setTitle("Jugando Bingo!");
        window.show();
        PartidaController.ventana = window;
    }

    private void createPlayer(String name) {
        Player.getInstance().setName(name);
    }

}
