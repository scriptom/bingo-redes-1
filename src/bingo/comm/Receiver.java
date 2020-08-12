package bingo.comm;

import com.fazecast.jSerialComm.SerialPortMessageListener;
import com.fazecast.jSerialComm.SerialPortMessageListenerWithExceptions;

public interface Receiver extends CommunicationSubject, SerialPortMessageListenerWithExceptions {
}
