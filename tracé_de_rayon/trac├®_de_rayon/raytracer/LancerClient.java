package raytracer;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.ServerNotActiveException;

public class LancerClient {
    public static void main (String[] args) throws ServerNotActiveException, NotBoundException, RemoteException {
        Client client = new Client();
        //normalement c'est tout ici
    }
}
