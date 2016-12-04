/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simu;

import donnees.Carte;
import donnees.Incendie;
import donnees.robot.Robot;

/**
 * Evénement: le robot est rendu disponible quand il a fini une tache
 */
public class EvenementLibererRobot extends Evenement {
    
    
    public EvenementLibererRobot(long date, Robot r) {
	super(date,r);
    }
    
    @Override
    public void execute() {
        super.getRobot().setDisponible(true);
    }
}