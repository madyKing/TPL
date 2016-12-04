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
public class Chenilles extends Robot {
    
    public Chenilles(Case c) {
        super(c);
	//valeurs par défaut
	this.vitesse = 60;
	this.volumeEau = 2000;
    }
    public Chenilles (Case c, double vitesse){
	super(c);
	this.vitesse = (vitesse > 80) ? 80 : vitesse; // sans dépasser 80 km/h
        this.volumeEau = 2000;
    }
    
    @Override
    public double getVitesse(NatureTerrain nat) {
	if (nat == NatureTerrain.FORET) {
            return this.vitesse / 2;
        }
        return this.vitesse;
    }

    //remplissage complet en 5 minutes
    public void remplirReservoir(){
	this.volumeEau = 2000;
    }
    
    //intervention unitaire: 100l en 8s
    public void deverserEau(int vol){
	this.volumeEau -= vol;
    }
    
    @Override
    public boolean isPlaceable(Case position) {
        NatureTerrain nat = position.getNature();
        if (nat == NatureTerrain.EAU || nat == NatureTerrain.ROCHE) {
            return false;
        }
        return true;
    }
    
}
