package raytracer;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServiceClient extends Remote {
    public void envoyerImage(Image image, Coordonnees coordonnes) throws RemoteException;
}
