package raytracer;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.ServerNotActiveException;
import java.rmi.server.UnicastRemoteObject;

public class LancerServiceEsclave {
    public static void main(String[] args) throws RemoteException, ServerNotActiveException, NotBoundException {
        ServiceEsclaveTravailleur serviceEsclave = new ServiceEsclaveTravailleur();
        ServiceEsclave s = (ServiceEsclave) UnicastRemoteObject.exportObject(serviceEsclave, 0);

        String ip = "127.0.0.1";

        Registry registry = LocateRegistry.getRegistry(ip, 1099);
        ServiceCentral serviceCentral = (ServiceCentral) registry.lookup("serviceCentral");

        serviceCentral.registerEsclave(s);
        //on a fini de s'enregistrer
    }
}