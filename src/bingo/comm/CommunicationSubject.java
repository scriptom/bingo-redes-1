package bingo.comm;

import com.fazecast.jSerialComm.SerialPort;

public interface CommunicationSubject {
    public void setWritingSerialPort(SerialPort serialPort);
    public SerialPort getWritingSerialPort();
    public void setReadingSerialPort(SerialPort serialPort);
    public SerialPort getReadingSerialPort();
}
