package raytracer;

public class Coordonnees {
    private int x;
    private int y;
    private int etat;
    private int length;
    private int height;

    public Coordonnees(int x, int y, int length, int height) {
        this.x = x;
        this.y = y;
        this.etat = 0; // 0 = libre, 1 = en cours, 2 = terminé
        this.length = length;
        this.height = height;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getEtat() {
        return etat;
    }

    public void setEtat(int etat) {
        this.etat = etat;
    }

    public int getLength() {
        return length;
    }

    public int getHeight() {
        return height;
    }
}