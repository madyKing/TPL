/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package donnees.robot;

import configs.Time;
import donnees.Case;
import donnees.NatureTerrain;

/**
 *
 * @author macintosh
 */
public class Pattes extends Robot {
    
    public Pattes(Case c) {
        super(c);
	//valeurs par défaut
	this.vitesse = 30;      //Vitesse à base: 30 km/h
	this.volumeEau = 1000000; // volume infini
    }
    
    @Override
    public double getVitesse(NatureTerrain nat) {
	if (nat == NatureTerrain.ROCHE) {
            return 10;                              //Vitesse sur roche: 10 km/h
        }
        return this.vitesse;
    }
    /**
     *
     */
    //reservoir infini
    public void remplirReservoir() {
	this.volumeEau = 1000000;
    }
    //intervention unitaire: 10l en 1s
    public void deverserEau(int vol){
	
    }
    
    @Override
	public boolean isPlaceable(Case position) {
        NatureTerrain nat = position.getNature();
        if (nat == NatureTerrain.EAU) {
            return false;
        }
        return true;
    }
    
}
