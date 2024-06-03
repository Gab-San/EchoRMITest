package org.example.forthandback;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class EchoBackServer implements EchoServerInterface {
    public EchoBackServer(String serverIp, int connectionPort) throws RemoteException{
        System.setProperty("java.rmi.server.hostname", serverIp);
        EchoServerInterface stub = (EchoServerInterface) UnicastRemoteObject.exportObject(this, 0);
        Registry registry = LocateRegistry.createRegistry(connectionPort);
        registry.rebind("EchoServer", stub);
        System.out.println("RMI server waiting for client on port " + connectionPort + "...");
    }

    @Override
    public void echo(String msg, EchoClientInterface echoClient) throws RemoteException {
        System.out.println(msg);
        echoClient.echo(msg);
    }

    public static void main(String[] args) {
        String serverIp = args[0];
        int port = Integer.parseInt(args[1]);
        try{
            new EchoBackServer(serverIp, port);
        } catch (RemoteException e) {
            e.printStackTrace(System.err);
        }
    }
}
