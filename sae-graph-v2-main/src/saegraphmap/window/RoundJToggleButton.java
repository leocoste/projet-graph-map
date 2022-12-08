package saegraphmap.window;

import java.awt.*;

/**
 * Cette de définir et modifier les caractéristique d'affichage des Lieu
 * @author Jérémie Vernay Léo Coste
 * @version 69.420
 */
public class RoundJToggleButton {
//ATTRIBUTS

    /**
     * Correspond au coordonné d'affichage des pts
     */
    private int x,y;

    /**
     * Taille des pts
     */
    private final int taillePts = 24;

    /**
     * Différent état du btn (0: non selectionné, 1:selectionné, 2:selection secondaire)
     */
    private int etatBtn = 0;

    /**
     * Couleur du pts
     */
    private Color couleurPts;

    /**
     * Visibilité du pts
     */
    private boolean visible = true;

//CONSTRUCTOR

    /**
     * Créer un RoundJToggleButton avec les paramettre du constructeur
     * @param x
     * @param y
     * @param typeVille
     */
    public RoundJToggleButton(int x, int y, char typeVille) {
        this.x = x;
        this.y = y;
        switch (typeVille) {
            case 'V' : this.couleurPts = new Color(92, 92, 92);break;
            case 'R' : this.couleurPts = new Color(73, 38, 24);break;
            case 'L' : this.couleurPts = new Color(230, 168, 0);break;
        }
    }


//GETTER & SETTER

    /**
     * Renvoie l'état de visibilité
     * @return
     */
    public boolean isVisible() {
        return visible;
    }

    /**
     * change la visibilité
     * @param visible
     */
    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    /**
     * renvoie le x du points
     * @return
     */
    public int getX() {
        return x;
    }

    /**
     * renvoie le y du points
     * @return
     */
    public int getY() {
        return y;
    }

    /**
     * renvoie la taille du pts
     * @return
     */
    public int getTaillePts() {
        return taillePts;
    }

    /**
     * renvoie la couleur du pts
     * @return
     */
    public Color getCouleurPts() {
        return couleurPts;
    }

    /**
     * Renvoie l'état de selection du pts
     * @return
     */
    public int isSelected() {
        return etatBtn;
    }

    /**
     * Change la valeur de x
     * @param x
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * change la valeur de y
     * @param y
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * change l'état du btn
     * @param etatBtn
     */
    public void setEtatBtn(int etatBtn) {
        this.etatBtn = etatBtn;
    }
}
