/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simu;

import donnees.Carte;
import donnees.Case;
import donnees.Direction;
import static donnees.NatureTerrain.EAU;
import donnees.robot.Robot;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Evénement: un robot rempli l'eau
 */
public class EvenementRemplir extends Evenement {
    private Carte carte;
    private Case caseEau;
    public EvenementRemplir(long date, Robot r, Carte carte,Case c) {
	super(date,r);
	this.carte = carte;
        this.caseEau = c;
    }

    @Override
    public void execute() {
        Case nord = this.carte.getVoisin(caseEau, Direction.NORD);
        Case est = this.carte.getVoisin(caseEau, Direction.EST);
        Case sud = this.carte.getVoisin(caseEau, Direction.SUD);
        Case ouest = this.carte.getVoisin(caseEau, Direction.OUEST);
        Robot rob = super.getRobot();

        if (((nord.getNature() == EAU || est.getNature() == EAU
	      || sud.getNature() == EAU || ouest.getNature() == EAU)
	     && (!rob.getClass().getSimpleName().equals("Drone")))
            || ((rob.getClass().getSimpleName().equals("Drone"))
		&& (rob.getPosition().getNature() == EAU))) {
            rob.remplirReservoir();
        } else {
            try {
                throw new Exception("Je ne peut pas remplir ici");
            } catch (Exception ex) {
                Logger.getLogger(EvenementRemplir.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
