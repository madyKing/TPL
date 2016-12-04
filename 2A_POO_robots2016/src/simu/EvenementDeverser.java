/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simu;

import donnees.Carte;
import donnees.Case;
import donnees.Direction;
import donnees.Incendie;
import donnees.robot.Robot;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Evénement: un robot déverse de l'eau sur un incendie
 */
public class EvenementDeverser extends Evenement {
    
    private Carte carte;
    private Incendie incendieAEteindre;
    private int volumeEau;
    
    public EvenementDeverser(long date, Robot r, Carte carte, Incendie i,int v) {
	super(date,r);
	this.carte = carte;
        this.incendieAEteindre = i;
        this.volumeEau = v;
    }
    
    @Override
    public void execute() {
	super.getRobot().deverserEau(volumeEau);
	incendieAEteindre.setIntensite(incendieAEteindre.getIntensite()-volumeEau);
	
    }    
}
