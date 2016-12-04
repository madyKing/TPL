package donnees.robot;

import configs.*;
import donnees.Carte;
import donnees.Case;
import donnees.Direction;
import donnees.Incendie;
import donnees.NatureTerrain;
import graphe.Graphe;
import java.util.*;
import simu.EvenementDeplacerElementaire;
import simu.Simulateur;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author macintosh
 */

abstract public class Robot {
    protected Case position;
    protected int volumeEau;
    protected double vitesse;
    protected boolean disponible;

    public Robot(Case c){
	this.position = c;
	disponible = true;
    }

    public int getVolumeEau() {
        return this.volumeEau;
    }

    public void setVolumeEau(int vol) {
	this.volumeEau = vol;
    }


    public Case getPosition() {
	return this.position;
    }


    public boolean estDisponible() {
	return this.disponible;
    }
    public void setDisponible(boolean etat) {
        this.disponible = etat;
    }




    /**
     * Se placer dans un case
     * @param position nouveau case
     */
    public void setPosition(Case position) {
        if (isPlaceable(position)) {
            this.position = position;
        }
    }

    /**
     * Calculer le temps pour déplacer depuis case c1 jusqu'à case c2 sur
     * la carte c
     * @param c1
     * @param c2
     * @param c
     * @return le temps pour déplacer
     */
    public long tempsDeplacement(Case c1, Case c2, Carte c) {
       	String nom = this.getClass().getSimpleName();

       	switch(nom) {
	case "Drone":
	    return (long)(c.getTailleCases()/(this.vitesse*(36/10)));
	case "Roues":
	    if(this.isPlaceable(c2))
		return (long)(c.getTailleCases()/(this.vitesse*(36/10)));
	    return 100000;
	case "Chenilles":
	    if(this.isPlaceable(c2)) {
		return (long)( (c.getTailleCases()/2) * ( 1/(this.getVitesse(c1.getNature())*(36/10)) + 1/(this.getVitesse(c2.getNature())*(36/10)) ));
	    }
	    return 100000;
	case "Pattes":
	    if(this.isPlaceable(c2)) {
		return (long)( (c.getTailleCases()/2) * ( 1/(this.getVitesse(c1.getNature())*(36/10)) + 1/(this.getVitesse(c2.getNature())*(36/10)) ));
	    }
	    return 1000000;
	default:
	    return 100000;
       	}
    }

    /**
     * calcul du temps pour déverser de l'eau sur un incendie
     * @param volumeEau
     * @return temps calculé
     */
    public long tempsDeversement(int volumeEau) {
	String nom = this.getClass().getSimpleName();

	switch (nom) {
	case "Drone":
	    return (long)(30*Time.second);
	case "Roues":
	    return (long)(5*Time.second*volumeEau/100);
	case "Chenilles":
	    return (long)(8*Time.second*volumeEau/100);
	case "Pattes":
	    return (long)(1*Time.second*volumeEau/10);
	default:
	    return 0;
	}
    }

    /**
     * Calculer le temps pour remplir d'eau des robot
     * @return le temps remplissage
     */
    public long tempsRemplissage() {
	String nom = this.getClass().getSimpleName();

	switch (nom) {
	case "Drone":
	    return (long)(30*Time.minute);
	case "Roues":
	    return (long)(10*Time.minute);
	case "Chenilles":
	    return (long)(5*Time.minute);
	case "Pattes":
	    return 0;
	default:
	    return 0;
	}
    }
    /**
     * Vérifier si ce robot peu éteindre le feu ou pas
     * Si oui, il va à la position de feu et déverser de l'eau
     * @param incendie
     * @param carte
     * @param simu
     * @return true si ce robot peut éteindre "l'incendie" sur "carte"
     */
    public boolean EteindreFeu(Incendie incendie, Carte carte, Simulateur simu) {
	Graphe graphe = carte.construireGraphe(this);
	boolean retour = false;
	List<Case> pcc = graphe.plusCourtChemin(this.position, incendie.getCase());
	this.disponible = false;
	for (int i = 2; i <= pcc.size(); i++) {
	    simu.ajouteDeplacementCase(this, pcc.get(pcc.size() - i));
	}
	int eauADeverser = 0;
	if (incendie.getIntensite() >= this.volumeEau) {
	    eauADeverser = this.volumeEau;
	    retour = true;
	} else {
	    eauADeverser = incendie.getIntensite();
	}
	simu.ajouteDeversement(this, eauADeverser, incendie);

	if (retour || !this.getClass().getSimpleName().equals("Pattes")) {
	    Case casePourRemplir = null;
	    long temps = 100000;

	    if (this.getClass().getSimpleName().equals("Drone")) {
		for (int i = 0; i < carte.getNbLignes(); i++) {
		    for (int j = 0; j < carte.getNbColonnes(); j++) {
			if (carte.getCase(i, j).getNature() == NatureTerrain.EAU) {
			    long tempsPCC = tempsPCC(incendie.getCase(), carte.getCase(i, j), carte);
			    if (temps >= tempsPCC) {
				temps = tempsPCC;
				casePourRemplir = carte.getCase(i, j);
			    }
			}
		    }
		}
	    } else {
		for (int i = 0; i < carte.getNbLignes(); i++) {
		    for (int j = 0; j < carte.getNbColonnes(); j++) {
			Case caseTemps = carte.getCase(i, j);
			if (caseTemps.getNature() != NatureTerrain.EAU){
                            if (carte.voisinExiste(caseTemps, Direction.NORD)) {
                                if (carte.getVoisin(caseTemps, Direction.NORD).getNature() == NatureTerrain.EAU) {
                                    long tempsPCC = tempsPCC(incendie.getCase(), caseTemps, carte);
                                    if (temps >= tempsPCC) {
                                        temps = tempsPCC;
                                        casePourRemplir = caseTemps;
                                    }
                                }
                            }

                            if (carte.voisinExiste(caseTemps, Direction.EST)) {
                                if (carte.getVoisin(caseTemps, Direction.EST).getNature() == NatureTerrain.EAU) {
                                    long tempsPCC = tempsPCC(incendie.getCase(), caseTemps, carte);
                                    if (temps >= tempsPCC) {
                                        temps = tempsPCC;
                                        casePourRemplir = caseTemps;
                                    }
                                }
                            }

                            if (carte.voisinExiste(caseTemps, Direction.OUEST)) {
                                if (carte.getVoisin(caseTemps, Direction.OUEST).getNature() == NatureTerrain.EAU) {
                                    long tempsPCC = tempsPCC(incendie.getCase(), caseTemps, carte);
                                    if (temps >= tempsPCC) {
                                        temps = tempsPCC;
                                        casePourRemplir = caseTemps;
                                    }
                                }
                            }

                            if (carte.voisinExiste(caseTemps, Direction.SUD)) {
                                if (carte.getVoisin(caseTemps, Direction.SUD).getNature() == NatureTerrain.EAU) {
                                    long tempsPCC = tempsPCC(incendie.getCase(), caseTemps, carte);
                                    if (temps >= tempsPCC) {
                                        temps = tempsPCC;
                                        casePourRemplir = caseTemps;
                                    }
                                }
                            }
			}
		    }
		}
		}
	    //System.out.println(this.getClass().getSimpleName());

	    //System.out.println(casePourRemplir.getLigne() + ", " + casePourRemplir.getColonne());

	    if (casePourRemplir != null) {
		List<Case> pccR = graphe.plusCourtChemin(incendie.getCase(), casePourRemplir);
		//System.out.println(pccR);
		for (int i = 2; i <= pccR.size(); i++) {
		    simu.ajouteDeplacementCase(this, pccR.get(pccR.size() - i));
		}
		simu.ajouteRemplissage(this,casePourRemplir);

	    } else {
		return false;
	    }
	}
	simu.ajouteLibererRobot(this);

	return true;
    }

    /**
     * calcul du temps pour arriver à la case du plus court chemin
     * @return long temps pour le PCC
     */
    public long tempsPCC(Case depart, Case destination, Carte carte) {
	long total = 0;

	Graphe graphe = carte.construireGraphe(this);
	List<Case> pcc = graphe.plusCourtChemin(depart, destination);

	for(int i = 0; i < pcc.size() - 1; i++) {/* on parcourt toute la liste du pcc jusqu'à l'avant dernier indice */
	    total += tempsDeplacement(pcc.get(i), pcc.get(i+1), carte);
	}
	return total;
    }


    /**
     * Verifier si le robot peut se placer sur ce case
     * @param position case à vérifier
     * @return true si positionnable, fasle si non
     */
    abstract public boolean isPlaceable(Case position);

    abstract double getVitesse(NatureTerrain nat);

    abstract public void deverserEau(int vol);

    abstract public void remplirReservoir();

}
