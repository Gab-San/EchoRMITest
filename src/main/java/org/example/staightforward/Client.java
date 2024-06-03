package org.example.staightforward;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Client extends UnicastRemoteObject{
    private final EchoServerInterface server;
    public Client(String registryIp, int registryPort) throws RemoteException, NotBoundException{
        super();
        Registry registry = LocateRegistry.getRegistry(registryIp, registryPort);
        server = (EchoServerInterface) registry.lookup("EchoServer");
    }
    public void send(String msg) throws RemoteException {
        server.echo(msg);
    }

    public static void main(String[] args) {
        String serverIP = null;
        int port = -1;

        String serverHostname = "localhost";
        for(String arg : args){
            if(arg.matches("\\d+.\\d+.\\d+.\\d+")){
                if(serverIP != null)
                    throw new IllegalArgumentException();
                serverIP = arg;
            }
            else if(arg.matches("\\d+")) {
                if(port >= 0)
                    throw new IllegalArgumentException();
                port = Integer.parseInt(arg);
            }
        }

        if(port <= -1){
            System.err.println("Missing server port or RMI/TCP");
            throw new IllegalStateException();
        }
        if(serverIP == null) //if no IP was found among args
            serverIP = serverHostname;

        try{
            Client client = new Client(serverIP, port);
            client.send("Hello");
            client.send("Message from client");
        } catch (NotBoundException | RemoteException e) {
            e.printStackTrace(System.err);
        }

    }
}
