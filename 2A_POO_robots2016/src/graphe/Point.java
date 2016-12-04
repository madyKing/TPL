package graphe;

import donnees.Case;
import java.util.*;

/**
 * 
 * cette classe repr�sente un point du graphe � l'aide d'une case et de ses liens avec les autres points du graphe
 * elle contient aussi deux attributs pour nous permettre de calculer le plus court chemin
 *
 */

public class Point {
	private Case casePoint;
	private List<Arrete> destinations ;
	private int marque ;
	private int definitif;
	
	/**
	 * les diff�rents contructeurs utiles pour la suite
	 */
	
	public Point(Case caseP) {
		this.casePoint = caseP;
		this.marque = 100000; // au d�part infini
		this.definitif = 0;
		this.destinations = new ArrayList<Arrete>();
	}
	/**
         * Contruire une point depuis sa position et les liens associés
         * @param caseP
         * @param liens 
         */
	public Point(Case caseP, List<Arrete> liens) {
		this.casePoint = caseP;
		this.marque = 100000; // au d�part infini
		this.definitif = 0;
		this.destinations = liens;
	}
	
	public Point(Point p) {
		this(p.casePoint, p.destinations);
	}
        
	/**
	 * Modificateurs
	 */
	public int getMarque(){
		return this.marque;
	}
	
	public boolean getDefinitif(){
		return !(this.definitif == 0);
	}
	
	public Case getCase() {
		return this.casePoint;
	}
	
	public List<Arrete> getDestinations(){
		return this.destinations;
	}
	
	public void setMarque(int m){
		this.marque = m;
	}
	
	public void setDefinitif(int d){
		this.definitif = d;
	}

	public void addArrete(Arrete arrete){
		this.destinations.add(arrete);
	}
}























