/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package donnees.robot;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import configs.Time;
import donnees.Case;
import donnees.NatureTerrain;

/**
 *
 * @author macintosh
 */
public class Roues extends Robot {
    
    public Roues(Case c) {
        super(c);
	//valeurs par défaut
	this.vitesse = 80;
	this.volumeEau = 5000;
    }

    public Roues(Case c, double vitesse) {
	super(c);
	this.vitesse = vitesse;
        this.volumeEau = 5000;
    }
    
    @Override
	public double getVitesse(NatureTerrain nat) {
        return this.vitesse;
    }
    //remplissage complet en 10 minutes
    public void remplirReservoir(){
	// attendre la remplitation
	
        this.volumeEau = 5000;
    }
    
    /**
     *
     * intervention unitaire: 100l en 5s
     * @param vol la volume d'éau a deverser
     */
    
    public void deverserEau(int vol){
	this.volumeEau -= vol;
    }
    
    
    
    @Override
    public boolean isPlaceable(Case position) {
        NatureTerrain nat = position.getNature();
        if (nat == NatureTerrain.TERRAIN_LIBRE || nat == NatureTerrain.HABITAT) {
            return true;
        }
        return false;
    }
    
}
