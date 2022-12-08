/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package saegraphmap.window;

import saegraphmap.linkedlist.TLieu;
import saegraphmap.linkedlist.TListe;
import saegraphmap.linkedlist.TRoute;
import saegraphmap.window.listener.GraphPanelListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Cette class permet de générer un panel affichant les pts
 * l'algo de FruchtermanReingold permetant d'obtenire un affichage coérant des pts
 * ce panel permet de selectioné des pts et générer des event.
 * @author Jérémie
 */
public class GraphPanel extends javax.swing.JPanel implements MouseListener,MouseMotionListener {
//CONSTRUCTOR
    /**
     * Genere un panel vide
     */
    public GraphPanel() {
        initComponents();
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
    }
    /**
     * genère un panel avec la liste de pts
     * il utilise la fonction de génération de graph pour obtenir le bon affichage
     */
    public GraphPanel(TListe listPts) {
        this.listPts = listPts;
        initComponents();
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        generationGraph();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setLayout(null);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables



//METHODE PUBLIC
    /**
     * Cette fonction utilise l'algo de FruchtermanReingold pour déplacer les pts qui sont initialisé à des position aléatoire
     * des force d'attraction et de répulsion sont appliqué au points plusieurs foit pour les déplacer
     * demo:
     * https://www1.pub.informatik.uni-wuerzburg.de/demos/forceDirected.html
     */
    public void generationGraph(){
        //initialisation de valeur pour les calculs
        double nbIterrationMax=1000 , iterration = 1, maxForce = 1000, force;
        double temp = 1;
        double minX=Integer.MAX_VALUE , minY=Integer.MAX_VALUE, maxX=Integer.MIN_VALUE , maxY = Integer.MIN_VALUE;
        TLieu lieu;
        TLieu lieu2;
        TRoute route;
        double epsilon = 0.1;
        //tant que le nombre d'itération max n'as pas été atteint et que la force maximal est suffisante les points sont déplacer
        while (iterration < nbIterrationMax && maxForce > epsilon){
            lieu = listPts.getListe();
            maxForce = 0;
            iterration++;
            //Pour tout les points
            while (lieu != null){
                lieu.setFx(0);
                lieu.setFy(0);
                lieu2 = listPts.getListe();
                //Ajoute la somme des forces d'attraction par rapport à tout les autre points
                while (lieu2 != null){
                    if(lieu2 != lieu){
                        lieu.setFx( lieu.getFx() +  (forceRep(lieu, lieu2) * normX(lieu2, lieu)));
                        lieu.setFy( lieu.getFy() +  (forceRep(lieu, lieu2) * normY(lieu2, lieu)));
                    }
                    lieu2 = lieu2.getSuivant();
                }
                lieu = lieu.getSuivant();
            }
            lieu = listPts.getListe();
            //Pour tout les points
            while (lieu != null){
                route = lieu.getTetelisteroutes();
                //Ajoute la somme des force de répulsion avec les pts au quel il est relié
                while (route != null){
                    lieu.setFx( lieu.getFx() +  (forceAttr(route.getLieuRejoint1(), route.getLieuRejoint2()) * normX(route.getLieuRejoint1(), route.getLieuRejoint2())));
                    lieu.setFy( lieu.getFy() +  (forceAttr(route.getLieuRejoint1(), route.getLieuRejoint2()) * normY(route.getLieuRejoint1(), route.getLieuRejoint2())));

                    route = route.getSuivant();
                }
                lieu = lieu.getSuivant();
            }
            lieu = listPts.getListe();
            //Applique la somme des deux force à chaque points
            while (lieu != null){
                force =  vecLength(lieu.getFx(), lieu.getFy());

                if (force > 0){
                    lieu.setFx(applyTemp(lieu.getFx(), force, temp));
                    lieu.setFy(applyTemp(lieu.getFy(), force, temp));
                }

                force = vecLength(lieu.getFx(), lieu.getFy());
                maxForce = Math.max(maxForce, force);
                lieu.setX(lieu.getX() + lieu.getFx());
                lieu.setY(lieu.getY() + lieu.getFy());
                lieu = lieu.getSuivant();
            }
            temp = cool(temp);
            this.repaint();

        }
        //Déplace tout les points pour réduire la taille du graph
        lieu =listPts.getListe();
        while(lieu !=null){
            if     (lieu.getX()<minX) minX = lieu.getX();
            else if(lieu.getX()>maxX) maxX = lieu.getX();
            if     (lieu.getY()<minY) minY = lieu.getY();
            else if(lieu.getY()>maxY) maxY = lieu.getY();
            lieu = lieu.getSuivant();
        }
        lieu =listPts.getListe();
        while(lieu !=null){
            lieu.setX(lieu.getX() - minX + 50 );
            lieu.setY(lieu.getY() - minY + 50 );
            lieu = lieu.getSuivant();
        }
        this.setPreferredSize(new Dimension((int) (maxX-minX+100), (int) (maxY-minY+100)));
    }

    /**
     * Cette fonction calcul la force de répulsion entre 2 Tlieu
     * @param lieu1
     * @param lieu2
     * @return
     */
    private double forceRep(TLieu lieu1, TLieu lieu2){
        return (Math.pow(this.longueurVisee,2) / Math.max(this.dist(lieu1, lieu2),0.01));
    }

    /**
     * Cette fonction calcul la force d'attraction entre 2 Tlieu
     * @param lieu1
     * @param lieu2
     * @return
     */
    private double forceAttr(TLieu lieu1, TLieu lieu2){
        return  Math.pow(this.dist(lieu1, lieu2), 2) /  longueurVisee;
    }

    /**
     * Cette fonction calcule la distance entre deux Tlieu
     * @param lieu1
     * @param lieu2
     * @return
     */
    private double dist(TLieu lieu1, TLieu lieu2){
        return vecLength(lieu2.getX() - lieu1.getX(), lieu2.getY() - lieu1.getY());
    }

    /**
     * Cette fonction calcule la disancce entre la souris et le Tlieu
     * @param m
     * @param lieu2
     * @return
     */
    private double dist(MouseEvent m, TLieu lieu2){
        return vecLength(lieu2.getX() - m.getX(), lieu2.getY() - m.getY());
    }

    /**
     * Calcule la longueur d'un vecteur
     * @param x
     * @param y
     * @return
     */
    private double vecLength(double x , double y){
        double ssum = Math.pow(x,2) + Math.pow(y,2);
        return Math.sqrt(ssum);
    }

    /**
     * Pondération des force en X
     * @param lieu1
     * @param lieu2
     * @return
     */
    private double normX(TLieu lieu1, TLieu lieu2){
        if(dist(lieu1, lieu2) == 0){
            return 0;
        }
        return (lieu2.getX() - lieu1.getX()) / this.dist(lieu1, lieu2);
    }

    /**
     * Pondération des force en Y
     * @param lieu1
     * @param lieu2
     * @return
     */
    private double normY(TLieu lieu1, TLieu lieu2){
        if(dist(lieu1, lieu2) == 0){
            return 0;
        }
        return (lieu2.getY() - lieu1.getY()) / this.dist(lieu1, lieu2);
    }

    /**
     * Pondération de la force en fonction du nb d'utiration
     */
    private double applyTemp(double dir, double force, double temp){
        return ( dir /  force * Math.min(force, temp * this.longueurVisee * 2));
    }

    /**
     * réduction du multiplicateur de force
     * @param temp
     * @return
     */
    private double cool(double temp){
        double delta = 0.975;
        return delta * temp;
    }

    /**
     * Affichage des routes et des points sur le graph
     * la couleur change en fonction du type de route et de points
     * @param g
     */
    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        //active l'antialiazing
        g2d.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING, java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
        //efface l'image précédent
        g2d.setColor(Color.white);
        g2d.fillRect(0,0,this.getWidth(),this.getHeight());
        //Si la liste des pts n'est pas null il vas afficher les route et les pts
        if(listPts!=null){
            //parcour de la liste de pts
            for(TLieu lieu = listPts.getListe(); lieu != null; lieu =lieu.getSuivant()){
                //parcour de la liste de route
                for(TRoute route = lieu.getTetelisteroutes(); route != null; route = route.getSuivant()){
                    //j'affiche les route en premier pour que les points s'affiche au dessus des route

                    //en fonction du type de route, de si les deux point relier seront afficher et de si la route est un plus cour chemin
                    //l'affiche de la route seras modifié(visible, couleur)
                    if(route.isRoutePluscourChemin()){
                        g2d.setColor(Color.magenta);
                        g2d.setStroke(new BasicStroke(3));
                        g2d.drawLine((int)route.getLieuRejoint1().getX(), (int)route.getLieuRejoint1().getY(), (int)route.getLieuRejoint2().getX(), (int)route.getLieuRejoint2().getY());
                        g2d.setStroke(new BasicStroke(1));
                    }
                    else if(route.getTypeRoute() == 'A' && afficheAutoroute && route.getLieuRejoint1().getrJTogBtn().isVisible() && route.getLieuRejoint2().getrJTogBtn().isVisible()) {
                        g2d.setColor(Color.blue);
                        g2d.drawLine((int)route.getLieuRejoint1().getX(), (int)route.getLieuRejoint1().getY(), (int)route.getLieuRejoint2().getX(), (int)route.getLieuRejoint2().getY());
                    }
                    else if(route.getTypeRoute() == 'N' && afficheNationale && route.getLieuRejoint1().getrJTogBtn().isVisible() && route.getLieuRejoint2().getrJTogBtn().isVisible()){
                        g2d.setColor(Color.red);
                        g2d.drawLine((int)route.getLieuRejoint1().getX(), (int)route.getLieuRejoint1().getY(), (int)route.getLieuRejoint2().getX(), (int)route.getLieuRejoint2().getY());
                    }
                    else if(route.getTypeRoute() == 'D' && afficheDepartemental && route.getLieuRejoint1().getrJTogBtn().isVisible() && route.getLieuRejoint2().getrJTogBtn().isVisible()){
                        g2d.setColor(Color.green);
                        g2d.drawLine((int)route.getLieuRejoint1().getX(), (int)route.getLieuRejoint1().getY(), (int)route.getLieuRejoint2().getX(), (int)route.getLieuRejoint2().getY());
                    }
                }
            }
            //parcour la liste des lieux
            for(TLieu lieu = listPts.getListe(); lieu != null; lieu = lieu.getSuivant()){
                //si le pts est dans un état de selection (1,2) alors on déssine d'abord un cercle pour montrer qu'il est selectionné
                //affiche le pts en fonction de la visibilité et de sont type
                if(lieu.getrJTogBtn().isVisible()){
                    if(lieu.getrJTogBtn().isSelected() == 1){
                        g2d.setColor(Color.BLACK);
                        g2d.fillOval(lieu.getrJTogBtn().getX() - lieu.getrJTogBtn().getTaillePts()/2 -2 ,lieu.getrJTogBtn().getY() -lieu.getrJTogBtn().getTaillePts()/2 -2 ,lieu.getrJTogBtn().getTaillePts()+4,lieu.getrJTogBtn().getTaillePts()+4);
                    }
                    if(lieu.getrJTogBtn().isSelected() == 2){
                        g2d.setColor(new Color(94, 3, 252));
                        g2d.fillOval(lieu.getrJTogBtn().getX() - lieu.getrJTogBtn().getTaillePts()/2 -2 ,lieu.getrJTogBtn().getY() -lieu.getrJTogBtn().getTaillePts()/2 -2 ,lieu.getrJTogBtn().getTaillePts()+4,lieu.getrJTogBtn().getTaillePts()+4);
                    }
                    g2d.setColor(lieu.getrJTogBtn().getCouleurPts());
                    g2d.fillOval(lieu.getrJTogBtn().getX() - lieu.getrJTogBtn().getTaillePts()/2  ,lieu.getrJTogBtn().getY() -lieu.getrJTogBtn().getTaillePts()/2 ,lieu.getrJTogBtn().getTaillePts(),lieu.getrJTogBtn().getTaillePts());
                }
            }
        }
    }

    /**
     * Modifie si les Auto-routes sont affiché ou non
     * @param afficheAutoroute
     */
    public void setAfficheAutoroute(boolean afficheAutoroute) {
        this.afficheAutoroute = afficheAutoroute;
    }

    /**
     * Modifie si les nationales sont affiché ou non
     * @param afficheNationale
     */
    public void setAfficheNationale(boolean afficheNationale) {
        this.afficheNationale = afficheNationale;
    }

    /**
     * Modifie si les Départemental sont affiché ou non
     * @param afficheDepartemental
     */
    public void setAfficheDepartemental(boolean afficheDepartemental) {
        this.afficheDepartemental = afficheDepartemental;
    }

    /**
     * permet d'ajouter une liste de pts au graph
     * @param liste
     */
    public void ajoutListePts(TListe liste){
        this.listPts = liste;
        generationGraph();
    }

    /**
     * Détecte si la souris clique sur une route ou un points
     * Génere un event lorsque qu'on clique sur le points
     * Change l'état de selection des points
     * @param mouseEvent
     */
    @Override
   public void mouseClicked(MouseEvent mouseEvent) {
        double d1, d2, lineLen;
        boolean stop = false;
        TLieu lieu = listPts.getListe();
        TLieu lieuChangeEtatBtn = listPts.getListe();
        ArrayList<TLieu> lieuEvent = null;
        boolean ptsSelected =false;
        //parcour la liste des points et détecte si il y a une colision
        while (lieu != null){
            if(Math.sqrt(Math.pow(lieu.getrJTogBtn().getX()-mouseEvent.getX(),2) +Math.pow(lieu.getrJTogBtn().getY()-mouseEvent.getY(),2)) < (float) lieu.getrJTogBtn().getTaillePts()/2 && lieu.getrJTogBtn().isVisible()){
                ptsSelected = true;
                //change l'état du points si il était selection ou shift selectionné
                if(mouseEvent.isShiftDown()){
                    if(lieu.getrJTogBtn().isSelected() != 2) lieu.getrJTogBtn().setEtatBtn(2);
                    else lieu.getrJTogBtn().setEtatBtn(0);
                    while (lieuChangeEtatBtn != null){
                        if(!(lieuChangeEtatBtn.equals(lieu) || lieuChangeEtatBtn.getrJTogBtn().isSelected() == 1)) lieuChangeEtatBtn.getrJTogBtn().setEtatBtn(0);
                        lieuChangeEtatBtn = lieuChangeEtatBtn.getSuivant();
                    }
                }
                else {
                    if(lieu.getrJTogBtn().isSelected() != 1) lieu.getrJTogBtn().setEtatBtn(1);
                    else lieu.getrJTogBtn().setEtatBtn(0);
                    while (lieuChangeEtatBtn != null){
                        if(!(lieuChangeEtatBtn.equals(lieu) || lieuChangeEtatBtn.getrJTogBtn().isSelected() == 2)) lieuChangeEtatBtn.getrJTogBtn().setEtatBtn(0);
                        lieuChangeEtatBtn = lieuChangeEtatBtn.getSuivant();
                    }
                }

                this.repaint();
                lieuChangeEtatBtn = listPts.getListe();
                lieuEvent = new ArrayList<>(Arrays.asList(null,null));
                //ajoute dans un array de taille des les points selectionné
                while (lieuChangeEtatBtn != null)
                {
                    if (lieuChangeEtatBtn.getrJTogBtn().isSelected() == 1){
                        lieuEvent.set(0,lieuChangeEtatBtn);
                    } else if (lieuChangeEtatBtn.getrJTogBtn().isSelected() == 2) {
                        lieuEvent.set(1,lieuChangeEtatBtn);
                    }
                    lieuChangeEtatBtn = lieuChangeEtatBtn.getSuivant();
                }
                break;
            }

            lieu = lieu.getSuivant();
        }
        //si un pts à été selectionné au click alors on déclanche l'event qui renvoit la liste des points selectionné
        if(ptsSelected) {
            for (GraphPanelListener listener : listeners) {
                listener.lieuSelectedChanged(lieuEvent);
            }
        }
        //sinon on vérifie si on a clicqué sur une route et dans ce cas là on affiche les info de la route
        else {
            for (lieu = listPts.getListe(); lieu != null && !stop; lieu = lieu.getSuivant()) {
                for (TRoute route = lieu.getTetelisteroutes(); route != null; route = route.getSuivant()) {
                    d1 = dist(mouseEvent, route.getLieuRejoint1());
                    d2 = dist(mouseEvent, route.getLieuRejoint2());
                    lineLen = dist(route.getLieuRejoint1(), route.getLieuRejoint2());
                    if (d1 + d2 >= lineLen - 0.3 && d1 + d2 <= lineLen + 0.1) {
                        JOptionPane.showMessageDialog(this, "Lieu rejoint 1 : " + route.getLieuRejoint1().getNomLieu() + "\n" + "Lieu rejoint 2 : " + route.getLieuRejoint2().getNomLieu() + "\n" + "Distance : " + route.getDistance() + " Type de route : " + route.getTypeRoute());
                        stop = true;
                    }
                }
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseDragged(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent) {
        if(listPts!=null){
            TLieu lieu = listPts.getListe();
            while (lieu != null){
                if(Math.sqrt(Math.pow(lieu.getrJTogBtn().getX()-mouseEvent.getX(),2) +Math.pow(lieu.getrJTogBtn().getY()-mouseEvent.getY(),2)) < (float) lieu.getrJTogBtn().getTaillePts()/2 && lieu.getrJTogBtn().isVisible()){
                    this.setToolTipText(lieu.getNomLieu());
                    return;
                }
                lieu = lieu.getSuivant();
            }
            this.setToolTipText(null);
        }
    }

    /**
     * ajoute les listener
     * @param listener
     */
    public void addGraphPanelListener(GraphPanelListener listener){
        this.listeners.add(listener);
    }

    //ATTRIBUTS
    /**
     * correspond à la longueur visée entre les points
     */
    private final double longueurVisee = 50;
    /**
     * liste des pts pour la génération et l'affichage du graph
     */
    private TListe listPts;

    /**
     * correspond à l'affichage des différents type de route
     */
    private boolean afficheAutoroute = true, afficheNationale =true, afficheDepartemental = true;


    /**
     * la liste des listener
     */
    private List<GraphPanelListener> listeners = new ArrayList<>();

}