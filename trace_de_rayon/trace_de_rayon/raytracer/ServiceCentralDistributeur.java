package raytracer;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
public class ServiceCentralDistributeur implements ServiceCentral {
    private List<ServiceEsclave> esclaves;

    public ServiceCentralDistributeur() {
        esclaves = new ArrayList<>();
    }

    @Override
    public void registerEsclave(ServiceEsclave s) throws RemoteException {
        esclaves.add(s);
        System.out.println("Reçu un esclave!");
    }

    @Override
    public List<ServiceEsclave> getEsclave() throws RemoteException {
        //on scan d'abord pour supprimer les indisponibles
        for (int i = 0; i < this.esclaves.size(); i++) {
            try {
                boolean tmp = this.esclaves.get(i).isAwake();
                //si aucune exception n'est levée, l'esclave est disponible
            } catch (Exception e) {
                //si l'esclave n'est pas éveillé, on le retire de la liste
                esclaves.remove(i);
                i--;
            }
        }
        System.out.println("Un client a demandé les esclaves, nombre d'esclaves disponibles: " + esclaves.size());
        return esclaves;
    }

}
