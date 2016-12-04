package graphe;

/**
 * 
 * Cette classe repr�sente l'arr�te du graphe qui lie deux points et son poids
 *
 */

public class Arrete {
	private Point depart;
	private Point arrivee;
	private int poids;
	
	/**
         * Contrure une arrete à basé de point départ, point arrivée et le poids
         * @param depart
         * @param arrivee
         * @param poids 
         */
	public Arrete(Point depart, Point arrivee, int poids) {
		this.depart = depart;
		this.arrivee = arrivee;
		this.poids= poids;
	}
        
	//Accesseurs	
	public Point getDepart() {
		return this.depart;
	}
	public Point getArrivee() {
		return this.arrivee;
	}
	public int getPoids() {
		return this.poids;
	}
}
