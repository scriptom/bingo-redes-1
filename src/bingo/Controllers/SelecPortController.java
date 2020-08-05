package bingo.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import com.fazecast.jSerialComm.*;

import java.awt.*;
import java.net.URL;
import java.util.Observable;
import java.util.ResourceBundle;


public class SelecPortController implements Initializable {
    @FXML
    private static ListView<String> puertos;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    public void cargar(ActionEvent event){
        ObservableList<String> hola = FXCollections.observableArrayList("hola","Como");
        puertos.setItems(hola);
    }

}
