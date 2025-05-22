package raytracer;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServiceEsclave extends Remote{
    public boolean isAwake() throws RemoteException;
    public Image computeImage(int x, int y, int length, int height, Scene scene) throws RemoteException;
}
