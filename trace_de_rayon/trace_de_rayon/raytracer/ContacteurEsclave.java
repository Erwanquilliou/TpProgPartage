package raytracer;

import java.util.List;

public class ContacteurEsclave extends Thread {
    ServiceEsclave esclave;
    List<Coordonnees> points;
    Scene scene;
    Client client;

    public ContacteurEsclave(ServiceEsclave esclave, List<Coordonnees> points, Scene scene, Client client) {
        this.esclave = esclave;
        this.points = points;
        this.scene = scene;
        this.client = client;
    }

    public void run() {
        boolean test = true;
        //on s'arrete que si on a un RemoteException, et on reverse l'état du points(à 0, libre)
        //ou si tout les points sont à 2, terminé!
        while(test) {
            //on doit trouver un objet qui est en etat 0 ou voir si tout est à 2
            Coordonnees coordonnee = null;
            boolean toutFini = true;
            synchronized (this.points){
                for (Coordonnees c : this.points) {
                    if (c.getEtat() == 0) {
                        toutFini = false;
                        coordonnee = c;
                        break;
                    }
                    if(c.getEtat() == 1) {
                        toutFini = false;
                    }
                }
            }
            if(toutFini) {
                test = false;
                //fin de service :)
                break;
            }
            //on a un point à traiter
            //on le met à 1, en cours
            synchronized (this.points) {
                coordonnee.setEtat(1);
            }
            //on va faire le calcul de l'image, on verifie try catch le remoteexception aussi
            //computeImage(int x, int y, int length, int height, Scene scene)
            try{
                Image image = this.esclave.computeImage(coordonnee.getX(), coordonnee.getY(), coordonnee.getLength(), coordonnee.getHeight(), this.scene);
                //on a l'image, on l'envoie au client
                this.client.envoyerImage(image, coordonnee);
            } catch (Exception e) {
                //on a un remote exception, on remet le point à 0
                synchronized (this.points) {
                    coordonnee.setEtat(0);
                }
                //on coupe le thread, esclave HS :(
                test = false;
                break;
            }
            //on a fini la boucle, le client mettra 2 automatiquement au point,
            //on relance et on recherche... tout marche bien :)
        }
    }
}
