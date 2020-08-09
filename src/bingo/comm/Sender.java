package bingo.comm;

import com.fazecast.jSerialComm.SerialPort;

public interface Sender extends CommunicationSubject {
    public int send(Message message, SerialPort serialPort);
}
