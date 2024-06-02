package org.example;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class EchoServer implements EchoServerInterface {
    public EchoServer(String serverIp, int connectionPort) throws RemoteException{
        System.setProperty("java.rmi.server.hostname", serverIp);
        EchoServerInterface stub = (EchoServerInterface) UnicastRemoteObject.exportObject(this, 0);
        Registry registry = LocateRegistry.createRegistry(connectionPort);
        registry.rebind("EchoServer", stub);
        System.out.println("RMI server waiting for client on port " + connectionPort + "...");
    }

    @Override
    public void echo(String msg) throws RemoteException {
        System.out.println(msg);
    }

    public static void main(String[] args) {
        String serverIp = args[0];
        int port = Integer.parseInt(args[1]);
        try{
            new EchoServer(serverIp, port);
        } catch (RemoteException e) {
            e.printStackTrace(System.err);
        }
    }
}
