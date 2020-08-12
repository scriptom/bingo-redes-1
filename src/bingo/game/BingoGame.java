package bingo.game;

import bingo.Controllers.PartidaController;
import bingo.game.checker.BingoChecker;
import javafx.application.Platform;
import javafx.beans.Observable;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class BingoGame {
    private ObservableList<Player> players = FXCollections.observableArrayList();
    private Player hostPlayer;
    private BingoChecker bingoChecker;
    private int maxCardboards;
    private static BingoGame instance;
    private BingoNumberGenerator generator;
    private IntegerProperty currentNumber = new SimpleIntegerProperty(this, "currentNumber");
    private boolean hostInstance;
    private SimpleStringProperty winnerName = new SimpleStringProperty(this, "winnerName");


    private BingoGame() {
        this.generator = new BingoNumberGenerator();
    }

    public static BingoGame getInstance() {
        return instance != null ? instance : (instance = new BingoGame());
    }

    public static void setInstance(BingoGame bingoGame) {
        instance = bingoGame;
    }

    public void addPlayer(Player player) {
        if (null == player.getGame()) {
            player.setGame(this);
        }
        players.add(player);
    }

    public ObservableList<Player> getPlayers() {
        return players;
    }

    public BingoChecker getBingoChecker() {
        return bingoChecker;
    }

    public void setBingoChecker(BingoChecker bingoChecker) {
        this.bingoChecker = bingoChecker;
    }

    public Player getHostPlayer() {
        return hostPlayer;
    }

    public void setHostPlayer(Player hostPlayer) {
        this.hostPlayer = hostPlayer;
    }

    public int getMaxCardboards() {
        return maxCardboards;
    }

    public void setMaxCardboards(int maxCardboards) {
        this.maxCardboards = maxCardboards;
    }

    public void setPlayers(List<Player> players) {
        this.players.setAll(players);
    }

    public int getCurrentNumber() {
        return currentNumber.get();
    }

    public IntegerProperty currentNumberProperty() {
        return this.currentNumber;
    }

    public void generateNewNumber() {
        currentNumber.set(generator.getNumberToPlay());
    }

    public void setCurrentNumber(int number) {
        Platform.runLater(() -> currentNumber.set(number));

    }

    public boolean isHostInstance() {
        return hostInstance;
    }

    public void setHostInstance(boolean hostInstance) {
        this.hostInstance = hostInstance;
    }

    public String getWinnerName() {
        return winnerName.get();
    }

    public void setWinnerName(String winnerName) {
        this.winnerName.set(winnerName);
    }

    public StringProperty winnerNameProperty() {
        return winnerName;
    }

    public boolean isUnplayable() {
        return
                getPlayers() == null ||
                getBingoChecker() == null ||
                getMaxCardboards() == 0 ||
                getHostPlayer() == null;
    }
}
