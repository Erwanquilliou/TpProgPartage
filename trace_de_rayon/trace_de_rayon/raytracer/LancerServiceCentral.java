package raytracer;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.ServerNotActiveException;
import java.rmi.server.UnicastRemoteObject;

public class LancerServiceCentral {
    public static void main(String[] args) throws RemoteException, ServerNotActiveException, NotBoundException {
        ServiceCentralDistributeur serviceCentralDistributeur = new ServiceCentralDistributeur();
        ServiceCentral serviceCentral = (ServiceCentral) UnicastRemoteObject.exportObject(serviceCentralDistributeur, 0);

        Registry registry = LocateRegistry.createRegistry(1099);
        registry.rebind("serviceCentral", serviceCentral);
    }
}
