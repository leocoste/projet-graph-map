package saegraphmap.linkedlist;

/**
 * Cette classe permet de créer des routes
 * @author Jérémie Vernay Léo Coste
 * @version 69.420
 */
public class TRoute {
//ATTRIBUTS
    /**
     * Distance physique entre 2 Tlieu
     */
    private final float distance;

    /**
     * Type de route
     */
    private final char typeRoute;//N (nationnale), D (departementale) ou A (autoroute)

    /**
     * lieu de départ
     */
    private final TLieu lieuRejoint1;

    /**
     * lieu d'arrivé
     */
    private final TLieu lieuRejoint2;

    /**
     * Troute suivant
     */
    private TRoute suivant;

    /**
     * True si la route doit être affiché en plus cour chemin
     */
    private boolean routePluscourChemin = false;
//CONSTRUCTOR

    /**
     * Créer une route et l'initialise avec les valeur en paramettre
     * @param distance
     * @param typeRoute
     * @param lieuRejoint1
     * @param lieuRejoint2
     */
    public TRoute(float distance, char typeRoute, TLieu lieuRejoint1, TLieu lieuRejoint2) {
        this.distance = distance;
        this.typeRoute = typeRoute;
        this.lieuRejoint1 = lieuRejoint1;
        this.lieuRejoint2 = lieuRejoint2;
    }

//GETTER & SETTER

    /**
     * renvoie la distance
     * @return
     */
    public float getDistance() {
        return distance;
    }

    /**
     * Renvoie le type de route
     */
    public char getTypeRoute() {
        return typeRoute;
    }

    /**
     * Renvoie le lieu de départ
     */
    public TLieu getLieuRejoint1() {
        return lieuRejoint1;
    }

    /**
     * revoie le lieu d'arrivé
     * @return
     */
    public TLieu getLieuRejoint2() {
        return lieuRejoint2;
    }

    /**
     * revoie la route suivante de la liste
     * @return
     */
    public TRoute getSuivant() {
        return suivant;
    }

    /**
     * Change le lieu suivant de la route
     * @param suivant
     */
    public void setSuivant(TRoute suivant) {
        this.suivant = suivant;
    }

    /**
     * Renvoie si il sagit du plus cour chemin
     * @return
     */
    public boolean isRoutePluscourChemin() {
        return routePluscourChemin;
    }

    /**
     * change l'état du plus cour chemin
     * @param routePluscourChemin
     */
    public void setRoutePluscourChemin(boolean routePluscourChemin) {
        this.routePluscourChemin = routePluscourChemin;
    }

    @Override
    public String toString() {
        return "TRoute {" +
                "distance=" + distance +
                ", typeRoute=" + typeRoute +
                ", lieuRejoint1=" + lieuRejoint1.getNomLieu() +
                ", lieuRejoint2=" + lieuRejoint2 .getNomLieu()+
                ", suivant=" + suivant +
                '}';
    }
}
