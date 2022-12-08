
package saegraphmap.linkedlist;



import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;


/**
 * Cette classe permet de créer et intérroger la liste de points
 * @author Jérémie Vernay Léo Coste
 * @version 69.420
 */
public class TListe {

//ATTRIBUTS
    /**
     * Cette variable stocke le premier Tlieu de la liste
     */
    private TLieu liste;


//CONSTRUCTOR
    /**
     * Crée la liste chainé {@link TListe} grace à un fichier csv dont le chemin est indiqué au constructeur
     * @param nom_Fichier
     */
    public TListe(String nom_Fichier) {
        //Ouverture du fichier et insertion ligne par ligne dans un array de string
        try {

            ArrayList<String> listPts = new ArrayList<>();
            try {
                BufferedReader buffer_csv = new BufferedReader(new FileReader(nom_Fichier));
                while (buffer_csv.ready()) {
                    listPts.add(buffer_csv.readLine());
                }
                buffer_csv.close();
            } catch (FileNotFoundException ex) {
                System.out.println("Erreur fichier non trouver: " + ex);
            } catch (IOException ex) {
                System.out.println("Erreur : " + ex);
            }

            //Création de tout les Tlieu dans la Tliste
            int index = 0;
            for (String ligne : listPts) {
                String[] lieu = ligne.split(":", 2);
                this.ajoutLieu(new TLieu(lieu[0].split(",")[1], lieu[0].split(",")[0].charAt(0), null));
                listPts.set(index, lieu[1]);
                index++;
            }

            //Ajout des route pour chaque Tlieu
            TLieu celluleLieu = this.liste;
            for (String ligne : listPts) {
                String[] routes = ligne.split(";");
                System.out.println(ligne);
                for (String route : routes) {
                    char typeRoute = route.split("::")[0].split(",")[0].toCharArray()[0];
                    float longueurRoute = Integer.parseInt(route.split("::")[0].split(",")[1]);
                    String destination = route.split("::")[1].split(",")[1];
                    celluleLieu.ajoutRoute(new TRoute(longueurRoute, typeRoute, celluleLieu, chercheLieu(destination)));
                }
                celluleLieu = celluleLieu.getSuivant();
            }
        }
        catch (Exception e){
            JOptionPane.showMessageDialog(null, "Erreur lors du chargement du fichier", "Érreur",JOptionPane.ERROR_MESSAGE);
        }
    }
//GETTER & SETTER

    /**
     * Renvoie le premier élément de la liste
     * @return
     */
    public TLieu getListe() {
        return liste;
    }

//METHODE PUBLIC

    /**
     * Permet de chercher un Tlieu en fonction de sa String nom.
     * Elle renvoie null si aucun Tlieu n'est trouvé
     * @param lieu
     * @return
     */
    public TLieu chercheLieu(String lieu) {
        TLieu elliste = this.liste;
        int i = 0;
        while (elliste != null) {
            if (elliste.getNomLieu().equals(lieu)) {
                return elliste;
            } else {
                elliste = elliste.getSuivant();
            }
        }
        return null;
    }

    /**
     * Cette fonction permet de renvoyer la liste des route entre 2 lieu
     * Celle-ci ne renvoi que les route les plus courte car elle n'est utiliser que pour l'algo de plus cour chemin
     * @param nomlieu1
     * @param nomlieu2
     * @return
     */
    public ArrayList<TRoute> chercheRoutes(String nomlieu1, String nomlieu2) {
        float distance = Float.MAX_VALUE;
        TLieu lieu1 = chercheLieu(nomlieu1);
        TLieu lieu2 = chercheLieu(nomlieu2);
        ArrayList<TRoute> routes = new ArrayList<TRoute>();
        for(TRoute route = lieu1.getTetelisteroutes(); route!=null ; route = route.getSuivant()){
            if(route.getLieuRejoint2() == lieu2) {
                if(route.getDistance() <  distance){
                    routes.add(route);
                    distance = route.getDistance();
                }
                else if(route.getDistance() ==  distance){
                    routes.add(route);
                }
            }
        }
        for(TRoute route = lieu2.getTetelisteroutes(); route!=null ; route = route.getSuivant()){
            if(route.getLieuRejoint2() == lieu1) {
                if(route.getDistance() <  distance){
                    routes.add(route);
                    distance = route.getDistance();
                }
                else if(route.getDistance() ==  distance){
                    routes.add(route);
                }
            }
        }
        return routes;
    }

    /**
     * Renvoie le nombre de ville de la TListe
     * @return
     */
    public int compterVilles() {
        TLieu elliste = this.liste;
        int nb = 0;
        while (elliste != null) {
            if (elliste.getType() == 'V') {
                nb++;
            }
            elliste = elliste.getSuivant();
        }
        return nb;
    }

    /**
     * Renvoie le nombre de restorant de la TListe
     * @return
     */
    public int compterRestaurants() {
        TLieu elliste = this.liste;
        int nb = 0;
        while (elliste != null) {
            if (elliste.getType() == 'R') {
                nb++;
            }
            elliste = elliste.getSuivant();
        }
        return nb;
    }

    /**
     * Renvoie le nombre de loisir de la TListe
     * @return
     */
    public int compterLoisir() {
        TLieu elliste = this.liste;
        int nb = 0;
        while (elliste != null) {
            if (elliste.getType() == 'L') {
                nb++;
            }
            elliste = elliste.getSuivant();
        }
        return nb;
    }

    /**
     * Renvoie le nombre de départemental de la Tliste
     * @return
     */
    public int compterDepartementales() {
        TLieu elliste = this.liste;
        int nb = 0;
        while (elliste != null) {
            TRoute ellisteroute = elliste.getTetelisteroutes();
            while (ellisteroute != null) {
                if (ellisteroute.getTypeRoute() == 'D') {
                    nb++;
                }
                ellisteroute = ellisteroute.getSuivant();
            }
            elliste = elliste.getSuivant();
        }
        return nb / 2;
    }

    /**
     * Renvoie le nombre de nationale dans la Tliste
     * @return
     */
    public int compterNationales() {
        TLieu elliste = this.liste;
        int nb = 0;
        while (elliste != null) {
            TRoute ellisteroute = elliste.getTetelisteroutes();
            while (ellisteroute != null) {
                if (ellisteroute.getTypeRoute() == 'N') {
                    nb++;
                }
                ellisteroute = ellisteroute.getSuivant();
            }
            elliste = elliste.getSuivant();
        }
        return nb / 2;
    }

    /**
     * Renvoie le nombre d'Autoroute de la Tliste
     * @return
     */
    public int compterAutoroute() {
        TLieu elliste = this.liste;
        int nb = 0;
        while (elliste != null) {
            TRoute ellisteroute = elliste.getTetelisteroutes();
            while (ellisteroute != null) {
                if (ellisteroute.getTypeRoute() == 'A') {
                    nb++;
                }
                ellisteroute = ellisteroute.getSuivant();
            }
            elliste = elliste.getSuivant();
        }
        return nb / 2;
    }

    /**
     * Renvoit la liste des Tlieu étant à 1-distance du Tlieu en parametre
     * Un filtre est appliqué en fonction du type de ville indiqué en paramettre
     * @param lieu1d
     * @param typecherche
     * @return
     */
    public ArrayList<TLieu> unDistance(TLieu lieu1d, char typecherche) {
        ArrayList UnDistance = new ArrayList();
        for (TRoute route = lieu1d.getTetelisteroutes() ; route != null; route = route.getSuivant()){
            if(route.getLieuRejoint2().getType() == typecherche)
                UnDistance.add(route.getLieuRejoint2());
        }
        return UnDistance;
    }


    /**
     * Renvoit la liste des Tlieu étant à 2-distance du Tlieu en parametre
     * Un filtre est appliqué en fonction du type de ville indiqué en paramettre
     * @param lieu2d
     * @param typecherche
     * @return
     */
    public ArrayList deuxDistance(TLieu lieu2d, char typecherche) {
        ArrayList deuxDistance = new ArrayList();
        for (TRoute route = lieu2d.getTetelisteroutes() ; route != null; route = route.getSuivant()){
            for (TRoute route2 = route.getLieuRejoint2().getTetelisteroutes() ; route2 != null; route2 = route2.getSuivant()){
                if(!deuxDistance.contains(route2.getLieuRejoint2()) && route2.getLieuRejoint2()!= lieu2d && route2.getLieuRejoint2().getType() == typecherche){
                    deuxDistance.add(route2.getLieuRejoint2());
            }
            }
        }
        return deuxDistance;
    }

    /**
     * Renvoit toute la liste des Tlieu étant à 2-distance du Tlieu en parametre
     * @param lieu2d
     * @return
     */
    public ArrayList deuxDistance(TLieu lieu2d) {
        ArrayList deuxDistance = new ArrayList();
        for (TRoute route = lieu2d.getTetelisteroutes() ; route != null; route = route.getSuivant()){
            for (TRoute route2 = route.getLieuRejoint2().getTetelisteroutes() ; route2 != null; route2 = route2.getSuivant()){
                if(!deuxDistance.contains(route2.getLieuRejoint2())){
                    deuxDistance.add(route2.getLieuRejoint2());
                }
            }
        }
        return deuxDistance;
    }

    /**
     * Cette fonction permet de savoir si deux Tlieu sont à 2 distance de l'un de l'autre
     * @param lieu1
     * @param lieu2
     * @return
     */
    public boolean isDeuxDistance(TLieu lieu1, TLieu lieu2) {
        for(TRoute route = lieu1.getTetelisteroutes() ; route!=null ; route = route.getSuivant()){
            for(TRoute route2 = lieu2.getTetelisteroutes() ; route2!=null ; route2 = route2.getSuivant()){
                if(route.getLieuRejoint2() == route2.getLieuRejoint2()) return true;
            }
        }
        return false;
    }

    /**
     * Cette fonction permet de savoir si la quelle des deux ville à le plus de loisir à moin de deux distance
     * @param lieu1
     * @param lieu2
     * @return
     */
    public String plusCulturelle(TLieu lieu1, TLieu lieu2) {
        Set<TLieu> lieuSet = new LinkedHashSet<>(deuxDistance(lieu1, 'L'));
        lieuSet.addAll(unDistance(lieu1, 'L'));
        int nblieu1 = lieuSet.size();

        lieuSet = new LinkedHashSet<>(deuxDistance(lieu2, 'L'));
        lieuSet.addAll(unDistance(lieu2, 'L'));
        int nblieu2 = lieuSet.size();

        if (nblieu1 == nblieu2) return(lieu1.getNomLieu() + " et " + lieu2.getNomLieu() + " sont autant culturelles");
        else if (nblieu1 > nblieu2) return(lieu1.getNomLieu() + " est plus culturelle que " + lieu2.getNomLieu());
        else return(lieu1.getNomLieu() + " est moins culturelle que " + lieu2.getNomLieu());
    }

    /**
     * Cette fonction permet de savoir si la quelle des deux ville à le plus de TLieu à moin de deux distance
     * @param lieu1
     * @param lieu2
     * @return
     */
    public String plusOuverte(TLieu lieu1, TLieu lieu2) {
        Set<TLieu> lieuSet = new LinkedHashSet<>(deuxDistance(lieu1));
        lieuSet.addAll(lieu1.unDistance());
        int nblieu1 = lieuSet.size();

        lieuSet = new LinkedHashSet<>(deuxDistance(lieu2));
        lieuSet.addAll(lieu2.unDistance());
        int nblieu2 = lieuSet.size();
        if (nblieu1 == nblieu2) return(lieu1.getNomLieu() + " et " + lieu2.getNomLieu() + " sont autant ouvertes");
        else if (nblieu1 > nblieu2) return(lieu1.getNomLieu() + " est plus ouvertes que " + lieu2.getNomLieu());
        else return(lieu1.getNomLieu() + " est moins ouvertes que " + lieu2.getNomLieu());
    }

    /**
     * Cette fonction permet de savoir si la quelle des deux ville à le plus de resto à moin de deux distance
     * @param lieu1
     * @param lieu2
     * @return
     */
    public String plusGastronomique(TLieu lieu1, TLieu lieu2) {
        Set<TLieu> lieuSet = new LinkedHashSet<>(deuxDistance(lieu1, 'R'));
        lieuSet.addAll(unDistance(lieu1, 'R'));
        int nblieu1 = lieuSet.size();

        lieuSet = new LinkedHashSet<>(deuxDistance(lieu2, 'R'));
        lieuSet.addAll(unDistance(lieu2, 'R'));
        int nblieu2 = lieuSet.size();
        if (nblieu1 == nblieu2) return(lieu1.getNomLieu() + " et " + lieu2.getNomLieu() + " sont autant gastronomiques");
        else if (nblieu1 > nblieu2) return(lieu1.getNomLieu() + " est plus gastronomiques que " + lieu2.getNomLieu());
        else return(lieu1.getNomLieu() + " est moins gastronomiques que " + lieu2.getNomLieu());
    }

    /**
     * Cette fonction ajoute un Tlieu à la fin de la Tliste
     * @param lieuAjoute
     */
    public void ajoutLieu(TLieu lieuAjoute) {
        TLieu celluleLieu = this.liste;
        if (celluleLieu == null) {
            this.liste = lieuAjoute;
        } else {
            while (celluleLieu.getSuivant() != null) {
                celluleLieu = celluleLieu.getSuivant();
            }
            celluleLieu.setSuivant(lieuAjoute);
        }
    }

    /**
     * Cette fonction permet de changer la visibilité de tout les Tlieu sur la map en fonction du type de Lieu
     * @param c
     * @param visible
     */
    public void changeLieuVisibility(char c, boolean visible){
        for(TLieu lieu = this.getListe(); lieu != null ; lieu = lieu.getSuivant()){
            if(lieu.getType() == c || c=='A' ) lieu.getrJTogBtn().setVisible(visible);
        }
    }

    /**
     * Renvoie le nombre de Tlieu dans la liste
     * @return
     */
    public int compterLieu(){
        return compterLoisir() + compterRestaurants() + compterVilles();
    }

    /**
     * Renvoie le nombre de route unique dans tout les Tlieu
     * @return
     */
    public int compterRoute(){return  compterAutoroute() + compterDepartementales() + compterNationales();}
}
