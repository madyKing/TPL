package simu;


import donnees.Case;
import donnees.robot.Robot;

/**
 * Evénement déplacer sur chaque case dans un chemin
 *
 */
public class EvenementDeplacerElementaire extends Evenement {
    private Case destination;

    public EvenementDeplacerElementaire(long date, Robot r, Case c) {
	super(date,r);
	this.destination = c;
    }

    public void execute() {
	super.getRobot().setPosition(this.destination);
	//System.out.print("(" + super.getRobot().getPosition().getLigne() + ", " + super.getRobot().getPosition().getColonne() + ") -> ");
    }

    @Override
    public String toString() {
        return "(" + this.destination.getLigne() + ", " + this.destination.getColonne() + ") -> ";

    }
}
