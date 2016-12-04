/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package donnees.robot;

import configs.Time;
import donnees.Case;
import donnees.NatureTerrain;
import donnees.robot.Robot;

/**
 *
 * @author macintosh
 */
public class Drone extends Robot {
    
    public Drone(Case c) {
        super(c);
	//valeurs par défaut
	this.vitesse = 100; // Vitess par défault de 100 km/h
	this.volumeEau = 10000;
    }
    
    public Drone(Case c, double vitesse) {
	super(c);
	this.vitesse = (vitesse > 150) ? 150 : vitesse; // Vitesse sans dépasser 150 km/h
        this.volumeEau = 10000;
    }
    
    @Override
    public double getVitesse(NatureTerrain nat) {
	return this.vitesse;
    }
    
    //remplissage complet en 30 minutes
    public void remplirReservoir() {
	this.volumeEau = 10000;
    }
    
    //intervention unitaire: vidage en 30s
    public void deverserEau(int vol) {
	this.volumeEau = 0;
    }
    
    @Override
    public boolean isPlaceable(Case position) {
        return true;
    }
}
