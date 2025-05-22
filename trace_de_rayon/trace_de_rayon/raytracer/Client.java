package raytracer;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.ServerNotActiveException;
import java.util.ArrayList;
import java.util.List;

public class Client implements ServiceClient{
    List<ServiceEsclave> esclaves;
    List<Coordonnees> coordonnees;
    Disp disp;

    public void envoyerImage(Image image, Coordonnees co){
        synchronized (this.coordonnees) {
            //on va chercher la coordonnée dans le tableau
            for (Coordonnees c : this.coordonnees) {
                if (c.equals(co)) {
                    c.setEtat(2);
                    break;
                }
            }
        }
        //on ajoute l'image à la liste des images
        this.disp.setImage(image, co.getX(), co.getY());
    }
    public Client() throws RemoteException, ServerNotActiveException, NotBoundException {
        String ip = "127.0.0.1";

        Registry registry = LocateRegistry.getRegistry(ip, 1099);
        ServiceCentral serviceCentral = (ServiceCentral) registry.lookup("serviceCentral");

        //on s'ouvre aussi en service pour l'envoyer au esclave pour qu'il nous envoie les images

        this.esclaves = serviceCentral.getEsclave();

        //valeur pour faire une image
        String fichier_description="simple.txt";
        int largeur = 512, hauteur = 512;
        this.disp = new Disp("Raytracer", largeur, hauteur);
        Scene scene = new Scene(fichier_description, largeur, hauteur);

        //on doit découper l'image
        //On stock la length et la height max de chaque bloc image (on peut en avoir des plus petits, sur la fin par exemple)
        int decoupageSize = 20;

        //on a besoin du tableau qui va stocker les bouts d'image (les x et y)
        //un objet coordonnée qui va stocker les coordonnées de chaque pixel
        //on va aussi prendre en compte l'état dans l'objet coordonnée (0 libre, 1 en cours, 2 terminé)
        this.coordonnees = new ArrayList<>();

        //Coordonnees(int x, int y, int length, int height)
        for (int i = 0; i < largeur; i += decoupageSize) {
            for (int j = 0; j < hauteur; j += decoupageSize) {
                int length = Math.min(decoupageSize, largeur - i);
                int height = Math.min(decoupageSize, hauteur - j);
                Coordonnees coordonnee = new Coordonnees(i, j, length, height);
                this.coordonnees.add(coordonnee);
            }
        }

        //pour chaque esclave, on lance un thread (ContacteurEsclave) qui va boucler pour calculer les images
        for (ServiceEsclave esclave : this.esclaves) {
            ContacteurEsclave ce = new ContacteurEsclave(esclave, coordonnees, scene, this);
            ce.start();
            //c'est tout ils s'arretent tout seul quand tout fini ou service esclave HS
        }

        //on display notre image et on va recevoir les images au fur et à mesure, normalement
    }
}