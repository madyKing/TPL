package graphe;

import java.util.*;
import donnees.Case;

/**
 *
 * Classe représentent un graphe composé d'une liste de points
 *
 */

public class Graphe {
    private Point[][] points;


    /**
     * constructeur d'un graphe avec une matrice de points
     */
    public Graphe(Point[][] points) {
	this.points = points;
    }

    //Accesseurs

    public Point[][] getPoints() {
	return this.points;
    }

    public Point getPoint(int colonne, int ligne) {
	return this.points[colonne][ligne];
    }

    /**
     * Calcul du plus court chemin entre une case départ et une case destination par l'algorithme de Dijkstra
     * 		1) On initialise les marques des Points à l'infini ("") et à 0 pour la racine
     * 		2) On prend la racine et on effectue les corrections de marque sur les Points voisins
     * 		3) On repère le Point à la marque la moins élevée et non définitive et on met cette marque en définitif
     * 		4) On stocke l'arrête qui correspond à la "fin" du plus court chemin pour aller au Point du 3)
     * 		5) On recommence au 2) en remplacant la racine par le Point du 3)
     */

    public List<Case> plusCourtChemin(Case depart, Case destination) {

	if (destination == null) {
	    return null;
	}

	Point pointDestination = this.getPoint(destination.getLigne(), destination.getColonne());
	if (pointDestination.getDestinations().size() == 0)
	    return null;

	this.points = this.clonePoints(this.points);
	Point point = this.getPoint(depart.getLigne(), depart.getColonne());
	point.setMarque(0); /* La racine est mise à marque nulle */
	point.setDefinitif(1); /* On rend cette marque définitive*/

	List<Point> s = new ArrayList<Point>(); /* s est la liste des Points déja étudiés */
	s.add(point);

	List<Arrete> a = new ArrayList<Arrete>(); /* a est la liste des arrêtes sauvegardées par l'algorithme */

	while (s.size() < points.length * points[0].length
	      && !this.getPoint(destination.getLigne(), destination.getColonne()).getDefinitif()) { /* Tant qu'on
												       a pas étudié tous les points ou alors tant qu'on a pas étudié le Point lié à la destination */
	    //System.out.println("(" + point.getCase().getLigne() + ", " + point.getCase().getColonne() + ")");
	    List<Arrete> liens = point.getDestinations();

	    if (liens.size() > 0) { /* Si le point d'étude n'a pas d'arrête*/
		for(int i = 0; i < liens.size(); i++) { /* On parcourt toutes les arrêtes du point en cours d'étude */

		    Arrete lien = liens.get(i);
		    Point arrivee = lien.getArrivee();
		    int ligneArrivee = arrivee.getCase().getLigne();
		    int colonneArrivee = arrivee.getCase().getColonne();
		    int marqueArrivee = this.getPoint(ligneArrivee, colonneArrivee).getMarque();
		    int marqueDepart = point.getMarque();

		    if (marqueArrivee > marqueDepart + lien.getPoids()) { /* On fait la correction si nécessaire */
			this.getPoint(ligneArrivee, colonneArrivee).setMarque(marqueDepart + lien.getPoids());
		    }
		}
	    }

	    int marqueMin = 100000;
	    int ligneProchainPoint = 0, colonneProchainPoint = 0;

	    for (int i = 0; i < points.length; i++) { /* On parcourt tous les Points */
		for (int j = 0; j < points[0].length; j++) {
		    point = this.getPoint(i,j);

		    if (!point.getDefinitif()) { /* On ne s'intérésse qu'au Points de marque non définitive */
			if (point.getMarque() < marqueMin) { /* On stocke le Point dont la marque est la plus petite */
			    marqueMin = point.getMarque();
			    ligneProchainPoint = i;
			    colonneProchainPoint = j;
			}
		    }
		}
	    }


	    this.getPoint(ligneProchainPoint, colonneProchainPoint).setDefinitif(1); /* On rend définitive la marque du Point gardé pour la suite
											de l'algo */

	    for (int k = 0; k < s.size(); k++) { /* On parcourt les points déja traités */
		point = s.get(k);
		liens = point.getDestinations();
		for(int l = 0; l < liens.size(); l++) { /* On parcourt les arrêtes dont le Point gardé est sommet d'arrivée */
		    Arrete arrete = liens.get(l);
		    Case caseArrivee = arrete.getArrivee().getCase();
		    Point prochainPoint = this.getPoint(ligneProchainPoint, colonneProchainPoint);
		    Case caseProchainPoint = prochainPoint.getCase();
		    int marqueProchainPoint = prochainPoint.getMarque();
		    int marquePoint = point.getMarque();

		    if (caseArrivee.equals(caseProchainPoint) && (marqueProchainPoint == marquePoint + arrete.getPoids())){ /* On garde l'arrêt qui a
															       mené à ce Point est qui est lié au plus court chemin pour y aller */
			a.add(arrete);
			break;
		    }
		}
	    }

	    point = this.getPoint(ligneProchainPoint, colonneProchainPoint); /* On met à jour le Point en cours d'étude pour la prochaine boucle */
	    s.add(point); /* On ajoute ce Point aux points étudiés */
		}

	List<Case> sortie = new ArrayList<Case>();
	Case caseTemp = destination;

	while (!depart.equals(caseTemp)) { /* Tant qu'on n'est pas arrivé jusqu'au début du chemin */

	    for (int i = a.size() - 1; i >= 0; i--) { /* On parcourt a */
		Arrete arrete = a.get(i);
		Point pointD = arrete.getDepart();
		Point pointA = arrete.getArrivee();
		//System.out.println(caseTemp.getLigne() + ", " + caseTemp.getColonne());
		//System.out.println(pointD.getCase().getLigne() + ", " + pointD.getCase().getColonne());
		//System.out.println(pointA.getCase().getLigne() + ", " + pointA.getCase().getColonne());
		//System.out.println("----------------------");
		if (pointA.getCase().equals(caseTemp)) { /* Si la case d'arrivée est la case associée à la case caseTemp */

		    caseTemp = pointD.getCase();
		    sortie.add(pointA.getCase()); /* On ajoute à la liste finale les Points qu'on doit parcourir */

		    if (depart.equals(caseTemp)) {
			sortie.add(arrete.getDepart().getCase()); /* On ajoute à la liste finale le Point de départ si on est au
								     début du chemin */
		    }

		    a.remove(i);
		    break;
		}
	    }
	}
	// afficherPCC(sortie); /* On affiche cette liste de Case qui correspond au chemin le plus court (à l'envers) */
	return sortie;
    }

    /**
     * On clone les points pour pouvoir réutiliser un même graphe sans le reconstuire pour un même robot.
     */
    private Point[][] clonePoints(Point[][] points) {
	Point[][] newPoint = new Point[points.length][points[0].length];
	for (int i=0;i<points.length ;i++ ) {
	    for (int j=0;j<points[0].length ; j++) {
		newPoint[i][j]=new Point(points[i][j]);
	    }
	}
	return newPoint;
    }

    /**
     * Affiche une liste de cases
     */
    public static void afficherPCC(List<Case> liste) {
	for(int i = 0; i < liste.size(); i++){
	    Case caseDepart = liste.get(i);
	    int ligne_depart = caseDepart.getLigne();
	    int colonne_depart = caseDepart.getColonne();
	//    System.out.print("("+ligne_depart+","+colonne_depart+") -> ");
	}
    }

    /**
     * Affiche un Graphe en listant ses points ainsi que les arrêtes dont ils sont le sommet de départ
     */
    public void afficherGraphe() {
	for(int i=0; i<this.points.length; i++){ /* On parcourt tout le Graphe */
	    for(int j = 0; j<this.points[0].length; j++){

		Point point = this.points[i][j];
	//	System.out.println("Point : ("+point.getCase().getColonne()+","+point.getCase().getLigne()+") de liens :");

		if(point.getDestinations().size() > 0) { /* Si il n'a pas de liens, ou autrement dit si on ne s'intérésse pas à ce Point */

		    for(int k =0; k<point.getDestinations().size(); k++){ /* On parcourt toutes les arrêtes associées */

			Point arrivee = point.getDestinations().get(k).getArrivee();
			Case caseArrivee = arrivee.getCase();
		//	System.out.println("("+point.getCase().getColonne()+","+point.getCase().getLigne()+") -> ("+caseArrivee.getColonne()+","+caseArrivee.getLigne()+")");
		    }
		}
		//System.out.println();
	    }
	}
    }
}
