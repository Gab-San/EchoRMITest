package org.example.staightforward;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface EchoServerInterface extends Remote {
    void echo(String msg) throws RemoteException;
}
