package simu;

import donnees.Carte;
import donnees.Direction;
import donnees.robot.Robot;

/**
 * Evénement un robot se rend sur une case selon la direction
 */
public class EvenementDeplacerSurDirection extends Evenement {
    
    private Direction direction;
    private Carte carte;
    
    public EvenementDeplacerSurDirection(long date, Robot r, Direction dir, Carte carte) {
	super(date,r);
	this.direction = dir;
	this.carte = carte;
    }
    
    public void execute() {
	if (carte.voisinExiste(super.getRobot().getPosition(), this.direction)) {
	    super.getRobot().setPosition(carte.getVoisin(super.getRobot().getPosition(), this.direction));
	}
        else {
            System.out.println("Erreur: Case non atteignable!");
        }
    }
}
