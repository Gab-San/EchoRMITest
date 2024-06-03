package org.example.forthandback;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface EchoServerInterface extends Remote {
    void echo(String msg, EchoClientInterface echoClient) throws RemoteException;
}
