package raytracer;

import java.rmi.RemoteException;

public class ServiceEsclaveTravailleur implements ServiceEsclave {
    public ServiceEsclaveTravailleur() {
    }

    @Override
    public boolean isAwake() throws RemoteException {
        return true;
    }

    @Override
    public Image computeImage(int x, int y, int length, int height, Scene scene) throws RemoteException {
        return scene.compute(x, y, length, height);
    }
}
