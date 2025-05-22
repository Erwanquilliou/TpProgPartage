package raytracer;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
public interface ServiceCentral extends Remote {
    public void registerEsclave(ServiceEsclave s) throws RemoteException;
    public List<ServiceEsclave> getEsclave() throws RemoteException;
}
