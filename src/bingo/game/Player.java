package bingo.game;

import bingo.game.cardboard.Cardboard;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.UUID;

public class Player {
    /**
     * Nombre del usuario
     */
    private SimpleStringProperty name;

    /**
     * Numero de cartones
     */
    private SimpleIntegerProperty numberOfCardboards;

    /**
     * Instancias de cartones
     */
    private Cardboard[] cardboards;

    /**
     * Instancia del jugador
     */
    private static Player instance;

    /**
     * Identificador único
     */
    private String uuid;

    /**
     * Constructor privado
     */
    private Player(String uuid) {
        this.uuid = uuid;
        name = new SimpleStringProperty(this, "name");
        numberOfCardboards = new SimpleIntegerProperty(this, "numberOfCardboards");
    }

    /**
     * Obtiene la instancia del singleton
     * @return Instancia unica del jugador
     */
    public static Player getInstance() {
        if (null == instance) {
            instance = new Player(UUID.randomUUID().toString());
        }

        return instance;
    }

    public String getName() {
        return name.get();
    }

    public int getNumberOfCardboards() {
        return numberOfCardboards.get();
    }

    public Cardboard[] getCardboards() {
        return cardboards;
    }

    public String getUuid() {
        return uuid;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public final StringProperty nameProperty() {
        return name;
    }

    public void setNumberOfCardboards(int numberOfCardboards) {
        this.numberOfCardboards.set(numberOfCardboards);
    }

    public IntegerProperty numberOfCardboardsProperty() {
        return numberOfCardboards;
    }

    public void setCardboards(Cardboard[] cardboards) {
        this.cardboards = cardboards;
    }

    public Cardboard getCardboard(int index) {
        if (index > numberOfCardboards.get()) {
            return null;
        }
        return cardboards[index];
    }
}
