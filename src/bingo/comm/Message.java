package bingo.comm;

import com.fazecast.jSerialComm.SerialPort;

import java.io.*;
import java.util.Arrays;

/**
 * Clase de un mensaje a enviar por puerto serial.
 * {BEGIN_BYTES + TIPO_MENSAJE + CONTENIDO_MENSAJE + PUERTO_ORIGEN + END_BYTES}
 *  El PUERTO_ORIGEN  es un string que indica el nombre del puerto serial que envió la información. Cada vez que
 *  alguien recibe un paquete, debe verificar si el remitente es el receptor, para saber si el anillo se terminó de cerrar.
 *  Las comunicaciones de los emisores debe ser <b>estrictamente</b> por el puerto de escritura (Es decir, la comunicación debe ser en 1 solo sentido)
 */
public class Message implements Serializable {
    private static ByteArrayOutputStream bos;
    private static ObjectOutputStream os;
    private static ByteArrayInputStream in;
    private static ObjectInputStream is;

    /* Constantes de Tipo de mensaje */

    public static String translateMessageType(byte messageType) {
        switch (messageType) {
            case HAS_BINGO: return "HAS_BINGO";
            case CHAT: return "CHAT";
            case JOIN: return "JOIN";
            case NEXT_NUMBER: return "NEXT_NUMBER";
            case HOST_PLAYER: return "HOST_PLAYER";
            case CURRENT_PLAYERS: return "CURRENT_PLAYERS";
            case FULL_BINGO: return "FULL_BINGO";
            case TWO_CARDBOARDS: return "TWO_CARDBOARDS";
        }
        return "";
    }

    /**
     * Es un canto de bingo acertado.
     * El contenido de este mensaje es el nombre del jugador quién tuvo bingo acertado
     */
    public static final byte HAS_BINGO = 0x01;

    /**
     * Es un mensaje de chat que viene (Sin uso)
     * El contenido de este mensaje es lo escrito por el remitente
     */
    public static final byte CHAT = 0x02;

    /**
     * Mensaje de solicitud de entrada a la partida
     * El contenido de este mensaje es el nombre del jugador ingresa a la partida.
     */
    public static final byte JOIN = 0x03;

    /**
     * Próximo número de bingo
     * El contenido debe ser el próximo número del bingo
     */
    public static final byte NEXT_NUMBER = 0x04;

    /**
     * Indica quién es el host de la partida
     * El contenido debe ser el nombre del host de la partida
     */
    public static final byte HOST_PLAYER = 0x05;

    /**
     * Nombres de todos los jugadores de la partida
     * El contenido de este mensaje es un String[] que de los jugadores que están actualmente en la partida
     */
    public static final byte CURRENT_PLAYERS = 0x06;

    /**
     * Indica si se van a utilizar 2 cartones en la partida.
     * El contenido debe ser un booleano, si está TRUE, el receptor debe configurar su instancia de partida
     * para 2 cartones. En caso FALSE, debe configurar para 1 sola
     */
    public static final byte TWO_CARDBOARDS = 0x07;

    /**
     * Indica el tipo de bingo que se va a utilizar.
     * El contenido es un booleano, que si está TRUE, el receptor debe configurar su instancia de partida
     * para un bingo de cartón completo. En caso FALSE, debe configurar para Bingo lineal.
     */
    public static final byte FULL_BINGO = 0x08;


    /**
     * Delimitador de inicio de mensaje
     */
    public static final byte[] BEGIN_BYTES = "B1".getBytes();

    /**
     * Delimitador de final de mensaje
     */
    public static final byte[] END_BYTES = "O74".getBytes();

    /**
     * Tipo de mensaje a enviar
     */
    private byte messageType;

    /**
     * Contenido del mensaje
     */
    private Serializable contents;

    /**
     * Nombre (a nivel de sistema) del puerto serial del remitente
     */
    private String senderSerialPortName;


    /**
     * Constructor de mensajes básico
     * @param senderSerialPortName Remitente del mensaje
     * @param messageType Tipo del mensaje
     * @param contents Contenido del mensaje
     */
    public Message(String senderSerialPortName, byte messageType, Serializable contents) {
        this.messageType = messageType;
        this.contents = contents;
        this.senderSerialPortName = senderSerialPortName;
    }

    /**
     * Constructor para mensajes vacíos
     * @param senderSerialPortName Remitente del mensaje
     * @param messageType Tipo del mensaje
     */
    public Message(String senderSerialPortName, byte messageType) {
        this(senderSerialPortName, messageType, null);
    }

    public Serializable getContents() {
        return contents;
    }

    public void setContents(Serializable contents) {
        this.contents = contents;
    }

    public byte getMessageType() {
        return messageType;
    }

    public void setMessageType(byte messageType) {
        this.messageType = messageType;
    }

    public String getSenderSerialPortName() {
        return senderSerialPortName;
    }

    public void setSenderSerialPortName(String senderSerialPortName) {
        this.senderSerialPortName = senderSerialPortName;
    }

    public static byte[] serialize(Message message) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream(512);
        ObjectOutputStream os = new ObjectOutputStream(out);
        os.writeObject(message);
        os.flush();
        return out.toByteArray();
    }

    public static Message deserialize(byte[] data) throws IOException, ClassNotFoundException {
        // Nos saltamos la entrada "B1" y la salida "O74"
        byte[] messageBytes = Arrays.copyOfRange(data, 2, data.length - 3);
        ByteArrayInputStream in = new ByteArrayInputStream(messageBytes);
        ObjectInputStream is = new ObjectInputStream(in);
        Message message = (Message) is.readObject();
        in.reset();
        return message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Message message = (Message) o;

        if (messageType != message.messageType) return false;
        return senderSerialPortName.equals(message.senderSerialPortName);
    }

    @Override
    public int hashCode() {
        int result = messageType;
        result = 31 * result + senderSerialPortName.hashCode();
        return result;
    }
}
