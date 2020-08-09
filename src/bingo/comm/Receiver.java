package bingo.comm;

import com.fazecast.jSerialComm.SerialPortMessageListener;

public interface Receiver extends CommunicationSubject, SerialPortMessageListener {
}
