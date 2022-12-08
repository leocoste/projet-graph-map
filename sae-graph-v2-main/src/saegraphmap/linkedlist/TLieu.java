package saegraphmap.linkedlist;

import saegraphmap.window.RoundJToggleButton;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Cette classe permet de créer et intérroger la liste de points
 * @author Jérémie Vernay Léo Coste
 * @version 69.420
 */
public class TLieu {
//ATTRIBUTS

    /**
     * Correspond au nom du lieu
     */
    public String nomLieu;

    /**
     * Correspond au type de lieu
     */
    private final char type; //V (ville), R (restaurant) ou L (lieu de loisir)

    /**
     * Indique le lieu suivant de la liste
     */
    private TLieu suivant;

    /**
     * Début de la liste de route
     */
    private TRoute tetelisteroutes;

    /**
     * Varriable comptenant les caractéristique de l'affichage du TLieu
     */
    private RoundJToggleButton rJTogBtn;

    /**
     * Ces variable sont les vecteur en float utiliser pour la génération du graph
     */
    private double fx = 0, fy=0;

    /**
     * Ces variable sont les coordonné en float utiliser pour l'affichage du graph
     */
    private double x ,y;

//CONSTRUCTOR

    /**
     * Ce Constructeur permet de créer un Tlieu avec certenescaractéristtiques
     * @param nomLieu
     * @param type
     * @param tetelisteroutes
     */
    public TLieu(String nomLieu, char type, TRoute tetelisteroutes) {
        this.nomLieu = nomLieu;
        this.type = type;
        this.tetelisteroutes = tetelisteroutes;
        this.x = (int) Math.floor(Math.random()*(1000+1)+0);
        this.y = (int) Math.floor(Math.random()*(1000+1)+0);
        this.rJTogBtn= new RoundJToggleButton((int)this.x, (int)this.y, this.type);
    }

    /**
     * Ce constructeur créer un TLieu par défaut
     */
    public TLieu() {
        this.nomLieu = null;
        this.type = 'z';
        this.suivant = null;
        this.tetelisteroutes = null;
    }

//GETTER & SETTER

    /**
     * renvoie le nom du pts
     * @return
     */
    public String getNomLieu() {
        return nomLieu;
    }

    /**
     * Renvoie le type du pts
     * @return
     */
    public char getType() {
        return type;
    }

    /**
     * renvoie le Tlieu suivant de la liste
     * @return
     */
    public TLieu getSuivant() {
        return suivant;
    }

    /**
     * Renvoie la liste de route du Tlieu
     * @return
     */
    public TRoute getTetelisteroutes() {
        return tetelisteroutes;
    }

    /**
     * Permet d'indiquer le lieu suivant au Tlieu
     * @param suivant
     */
    public void setSuivant(TLieu suivant) {
        this.suivant = suivant;
    }


    /**
     * renvoie les coordonné graphique du pts
     * @return
     */
    public double getX() {
        return this.x;
    }

    /**
     * renvoie les coordonné graphique du pts
     * @return
     */
    public double getY() {
        return this.y;
    }

    /**
     * Set les coordonné graphique et réel du pts
     * @return
     */
    public void setX(double x) {
        this.x = x;
        this.rJTogBtn.setX((int) x);
    }

    /**
     * Set les coordonné graphique et réel du pts
     * @return
     */
    public void setY(double y) {
        this.y = y;
        this.rJTogBtn.setY((int) y);
    }

    /**
     * Renvoie l'objet d'affichage du pts
     * @return
     */
    public RoundJToggleButton getrJTogBtn() {
        return rJTogBtn;
    }

    /**
     * Renvoie la valeur du vecteur du pts
     * @return
     */
    public double getFx() {
        return fx;
    }

    /**
     * Renvoie la valeur du vecteur du pts
     * @return
     */
    public double getFy() {
        return fy;
    }

    /**
     * Modifie la valeur du vecteur du pts
     * @param fxt
     */
    public void setFx(double fxt) {
        this.fx = fxt;
    }

    /**
     * Modifie la valeur du vecteur du pts
     * @param fyt
     */
    public void setFy(double fyt) {
        this.fy = fyt;
    }
//METHODE PUBLIC
    /**
     * Renvoie la liste des Tlieu à 1 distance du pts
     * @return
     */
    public ArrayList<TLieu> unDistance() {
        ArrayList<TLieu> listUnDistance = new ArrayList<>();
        for(TRoute route = this.getTetelisteroutes(); route != null; route= route.getSuivant()){
            listUnDistance.add(route.getLieuRejoint2());
        }
        return listUnDistance;
    }

    /**
     * Ajoute une route à la liste dse route
     * @param routeAjoute
     */
    public void ajoutRoute(TRoute routeAjoute){
        TRoute celluleRoute = this.tetelisteroutes;
        if (celluleRoute == null){
            this.tetelisteroutes = routeAjoute;
        }
        else{
            while (celluleRoute.getSuivant() != null){
                celluleRoute = celluleRoute.getSuivant();
            }
            celluleRoute.setSuivant(routeAjoute);
        }

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TLieu)) return false;
        TLieu tlieu = (TLieu) o;
        return type == tlieu.type && Objects.equals(nomLieu, tlieu.nomLieu);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nomLieu, type);
    }
}



