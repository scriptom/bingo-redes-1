package bingo.game;

import bingo.comm.Message;
import bingo.comm.Receiver;
import bingo.comm.Sender;
import bingo.game.cardboard.Cardboard;
import bingo.game.checker.FullChecker;
import bingo.game.checker.LineChecker;
import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortEvent;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.io.IOException;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

import static bingo.comm.Message.*;

public class Player implements Sender, Receiver, Serializable {
    /**
     * Nombre del usuario
     */
    private StringProperty name;

    /**
     * Instancias de cartones
     */
    private Cardboard[] cardboards;

    /**
     * Puerto serial de comunicación (escritura)
     */
    private SerialPort writingSerialPort;

    /**
     * Puerto serial de comunicación (lectura)
     */
    private SerialPort readingSerialPort;

    /**
     * Instancia de esta aplicación en particular
     */
    private static Player instance;

    /**
     * Instancia de la partida
     */
    private BingoGame game;

    /**
     * Lista de mensajes pendientes por mandar.
     */
    private Queue<Message> pendingMessages;


    public Player(String name) {
        this.name = new SimpleStringProperty(this, "name", name);
        pendingMessages = new LinkedList<>();
    }

    public Player() {
        this(null);
    }

    public static Player getInstance() {
        if (null == instance) {
            instance = new Player();
        }
        return instance;
    }

    private void maybeOpenPort(SerialPort port) {
        if (!port.isOpen()) {
            port.openPort();
        }
    }

    public void disconnectAllPorts() {
        if (readingSerialPort != null && readingSerialPort.isOpen()) {
            readingSerialPort.closePort();
        }
        if (writingSerialPort != null && writingSerialPort.isOpen()) {
            writingSerialPort.closePort();
        }
    }

    public String getName() {
        return name.get();
    }


    public Cardboard[] getCardboards() {
        return cardboards;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public final StringProperty nameProperty() {
        return name;
    }

    public void setCardboards(Cardboard[] cardboards) {
        this.cardboards = cardboards;
    }

    public Cardboard getCardboard(int index) {
        if (index > cardboards.length) {
            return null;
        }
        return cardboards[index];
    }

    @Override
    public int send(Message message, SerialPort serialPort) {
        if (!serialPort.getDCD()) {
            // Mientras no tengamos nadie a quién recibir, Añadimos mensajes a la cola
            pendingMessages.add(message);
        } else {
            // Ya podemos enviar porque tenemos receptor
            if (!pendingMessages.isEmpty()) {
                Message pending;
                // primero mandamos todos los mensajes pendientes
                while ((pending = pendingMessages.remove()) != null) {
                    send(pending, serialPort);
                }
            }
        }

        // Mandamos el mensaje
        byte[] messageBytes = new byte[0];
        try {
            messageBytes = serialize(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Empezamos a mandar el mensaje
        serialPort.writeBytes(BEGIN_BYTES, 2);
        int bytesWritten = serialPort.writeBytes(messageBytes, messageBytes.length);
        System.out.println("bytesWritten = " + bytesWritten);
        serialPort.writeBytes(END_BYTES, 3);
        return bytesWritten;
    }

    @Override
    public void setWritingSerialPort(SerialPort serialPort) {
        maybeOpenPort(serialPort);
        System.out.println("Asignado puerto escritura");
        this.writingSerialPort = serialPort;
    }

    @Override
    public SerialPort getWritingSerialPort() {
        return writingSerialPort;
    }

    @Override
    public void setReadingSerialPort(SerialPort serialPort) {
        maybeOpenPort(serialPort);
        serialPort.addDataListener(this);
        System.out.println("Asignado puerto lectura");
        this.readingSerialPort = serialPort;
    }

    @Override
    public SerialPort getReadingSerialPort() {
        return readingSerialPort;
    }

    @Override
    public byte[] getMessageDelimiter() {
        return END_BYTES;
    }

    @Override
    public boolean delimiterIndicatesEndOfMessage() {
        return true;
    }

    @Override
    public int getListeningEvents() {
        return SerialPort.LISTENING_EVENT_DATA_RECEIVED;
    }

    @Override
    public void serialEvent(SerialPortEvent serialPortEvent) {
        if (serialPortEvent.getEventType() == getListeningEvents()) {
            byte[] data = serialPortEvent.getReceivedData();
            try {
                Message message = deserialize(data);
                // Verificamos si completamos la vuelta del anillo. Realmente no nos interesa más de aquí
                if (!ringComplete(message)) {
                    System.out.println("Message.translateMessageType(message.getMessageType()) = " + translateMessageType(message.getMessageType()));
                    System.out.println("message.getSenderSerialPortName() = " + message.getSenderSerialPortName());
                    switch (message.getMessageType()) {
                        case JOIN:
                            if (null != game && game.isHostInstance()) {
                                // El host añade a la partida al nuevo jugador!
                                String name = (String) message.getContents();
                                Player newPlayer = new Player(name);
                                game.addPlayer(newPlayer);
                                // En caso de que alguien solicite la información de la partida, se la damos por partes
                                // Primero, el numeron de cartones
                                Message cardboardNumberMessage = new Message(writingSerialPort.toString(), TWO_CARDBOARDS, game.getMaxCardboards() == 2);
                                // Tipo de bingo
                                Message bingoCheckerMessage = new Message(writingSerialPort.toString(), FULL_BINGO, game.getBingoChecker() instanceof FullChecker);
                                // Luego le informamos de quién es el host
                                Message hostMessage = new Message(writingSerialPort.toString(), HOST_PLAYER, game.getHostPlayer().getName());
                                // Le damos la lista completa de jugadores
                                String joined = namesHelper();
                                Message playersMessage = new Message(writingSerialPort.toString(), CURRENT_PLAYERS, joined);
                                // Ahora mandamos todos los datos
                                for (Message m : new Message[]{cardboardNumberMessage, bingoCheckerMessage, hostMessage, playersMessage}) {
                                    send(m, writingSerialPort);
                                }
                            } else {
                                retransmitMessage(message);
                            }
                            break;
                        case NEXT_NUMBER:
                            game.setCurrentNumber((int) message.getContents());
                            retransmitMessage(message);
                            break;
                        case HAS_BINGO:
                            // Ya ganó. TODO: Crear trigger para esto.
                            String winnerName = (String) message.getContents();
                            break;
                        case CURRENT_PLAYERS:
                            if (null == game) {
                                game = BingoGame.getInstance();
                            }
                            String contents = (String) message.getContents();
                            String[] names = contents.split("\\$");
                            List<Player> playerList = Arrays.stream(names)
                                    .map(Player::new)
                                    .collect(Collectors.toList());
                            int originalSize = game.getPlayers().size();
                            game.setPlayers(playerList);
                            int newSize = game.getPlayers().size();
                            System.out.println("originalSize = " + originalSize);
                            System.out.println("newSize = " + newSize);
                            System.out.println("playerList = " + playerList);
                            retransmitMessage(message);
                            break;
                        case FULL_BINGO:
                            if (null == game) {
                                game = BingoGame.getInstance();
                            }
                            // Ajustamos la config para usar FullChecker o LineChecker
                            boolean isFullBingo = (boolean) message.getContents();
                            if (null == game.getBingoChecker()) {
                                game.setBingoChecker(isFullBingo ? new FullChecker() : new LineChecker());
                            }
                            retransmitMessage(message);
                            break;
                        case TWO_CARDBOARDS:
                            if (null == game) {
                                game = BingoGame.getInstance();
                            }
                            // Ajustamos la config para manejar 2 cartones o uno
                            boolean isTwoCardboards = (boolean) message.getContents();
                            if (0 == game.getMaxCardboards()) {
                                game.setMaxCardboards(isTwoCardboards ? 2 : 1);
                            }
                            retransmitMessage(message);
                            break;
                        case HOST_PLAYER:
                            if (null == game) {
                                game = BingoGame.getInstance();
                            }
                            // Creamos una instancia de Player y la asignamos como host
                            String hostName = (String) message.getContents();
                            if (null == game.getHostPlayer()) {
                                game.setHostPlayer(new Player(hostName));
                            }
                            retransmitMessage(message);
                            break;
                        default:
                            System.out.println("message.getMessageType() = " + message.getMessageType());
                    }

                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public BingoGame getGame() {
        return game;
    }

    public void setGame(BingoGame game) {
        this.game = game;
    }

    /**
     * Pregunta si hay algún juego de bingo
     */
    public void joinExistingGame() {
        Message message = new Message(writingSerialPort.toString(), JOIN, name.get());
        System.out.println("Solicitando entrar a partida...");
        send(message, writingSerialPort);
        while (!readingSerialPort.getDCD() || !writingSerialPort.getDCD()) {
            System.out.println("No hay carrier en lectura/escritura. Seguiré esperando...");
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Retransmitimos un mensaje en nuestro
     *
     * @param message Mensaje a retransmitir
     */
    private void retransmitMessage(Message message) {
        System.out.println("Retransmisión.");
        send(message, writingSerialPort);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return Objects.equals(name, player.name) &&
                Arrays.equals(cardboards, player.cardboards) &&
                Objects.equals(writingSerialPort, player.writingSerialPort) &&
                Objects.equals(readingSerialPort, player.readingSerialPort);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(name, writingSerialPort, readingSerialPort);
        result = 31 * result + Arrays.hashCode(cardboards);
        return result;
    }

    /**
     * Determina si el anillo de transmisión ha sido concretado. Esto lo hace comprobando que el puerto de
     * origen del mensaje sea igual a nuestro puerto de escritura asignado
     *
     * @param message El mensaje para validar
     * @return Si el anillo fue concretado. Eso se hace verificando que el emisor sea igual al receptor
     */
    private boolean ringComplete(Message message) {
        return message.getSenderSerialPortName().equals(writingSerialPort.toString());
    }

    /**
     * Convierte una lista (List) de jugadores (Player) a un string con los nombres delimitados por "$"
     * Ej: ["TOMAS","CESAR","DANIEL","ROBERT"] => "TOMAS$CESAR$DANIEL$ROBERT"
     *
     * Esto es necesario porque no se puede enviar un Object[] serializado y luego hacerle casting a String[] de nuevo
     * @return
     */
    private String namesHelper() {
        String[] names = new String[game.getPlayers().size()];
        for (int i = 0; i < names.length; i++) {
            names[i] = game.getPlayers().get(i).getName();
        }
        return String.join("$", names);
    }

    @Override
    public String toString() {
        return getName();
    }
}
