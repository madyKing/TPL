/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package donnees;

import donnees.robot.Robot;
import graphe.Arrete;
import graphe.Graphe;
import graphe.Point;
import java.util.*;

/**
 *
 * @author macintosh
 */
public class Carte {
    private int tailleCases;
    private Case[][] cases;

    public Carte(int nbLignes, int nbColonnes, int tailleCases) {
	this.cases = new Case[nbLignes][nbColonnes];
	this.tailleCases = tailleCases;
    }
    public int getNbLignes() {
	return this.cases.length;
    }
    public int getNbColonnes() {
	return this.cases[0].length;
    }
    public int getTailleCases() {
	return this.tailleCases;
    }
    public Case getCase(int lig, int col) {
	return this.cases[lig][col];
    }

    public void setCase(int lig, int col, Case src) {
        this.cases[lig][col] = src;
    }
    
    /**
     * Vérifier s'il y a une case à côté de 'src' sur direction 'dir'
     * @param src 
     * @param dir direction à vérifier
     * @return 
     */
    public boolean voisinExiste(Case src, Direction dir) {
	switch (dir) {
	case NORD:
	    if (src.getLigne()  == 0) 
		return false;
	    return true;
	case OUEST:
	    if (src.getColonne() == 0) 
		return false;
	    return true;
	case EST:
	    if (src.getColonne() == this.cases[0].length - 1) 
		return false;
	    return true;
	case SUD:
	    if(src.getLigne() == this.cases.length - 1) return false;
	    return true;
        default:
            return false;
	}
    }

    /**
     * Prendre le case voisin de 'src' sur direction 'dir' s'il existe
     * @param src
     * @param dir
     * @return 
     */
    public Case getVoisin(Case src, Direction dir){
	
	if (!voisinExiste(src,dir)) 
	    return null;

	switch (dir) {
	case NORD:
	    return getCase(src.getLigne() - 1, src.getColonne());
	case OUEST:
	    return getCase(src.getLigne(), src.getColonne() - 1);	 
	case EST:
	    return getCase(src.getLigne(), src.getColonne() + 1);
	case SUD:
	    return getCase(src.getLigne() + 1, src.getColonne());
        default:
            return null;
        }
    }
    
    /**
     * Construction des différents graphes contenant les possibilités de déplacement des différents robots
     * Ils permettront de calculer le plus court chemin pour aller d'une case départ à une case destination
     * @param robot qui a besoin de graphe
     * @return graphe pour calculer le plus court chemin
     */
    
    public Graphe construireGraphe(Robot robot) {
    	Point[][] matrice = new Point[this.getNbLignes()][this.getNbColonnes()]; // on considÃ¨re toutes les cases au dÃ©part
    	Point p1 ;
    	Point p2;
    	
    	for(int i = 0; i < this.cases.length; i++){ /* On parcourt toute la carte */
			for(int j = 0; j < this.cases[0].length; j++){
				int poids = 1; // Le poids est de 1 pour toutes les arrÃªtes sauf cas spÃ©cifiques de certains robots car d = v*poids
				                                
                                Case caseTraitee = this.cases[i][j];
				List<Arrete> liens = new ArrayList<Arrete>();
				Arrete arrete ;

                                p1 = new Point(this.cases[i][j]) ;
				int ligneP1 = p1.getCase().getLigne();
				//ce test n'est pas utile pour le robot drone, nÃ©anmoins on le met pour plus de clartÃ©
				if(robot.isPlaceable(caseTraitee)) { /* si la caseTraitee est accessible par le robot */
					
					if(robot.getClass().getSimpleName().equals("Chenilles")  && (caseTraitee.getNature() == NatureTerrain.FORET)) 
                                            poids = 2; /* car v = v/2, donc Ã§a ira 2 fois moins vite */
					if(robot.getClass().getSimpleName().equals("Pattes") && (caseTraitee.getNature() == NatureTerrain.ROCHE)) 
                                            poids = 3; /* pareil Ã§a ira 3 fois moins vite */
					
					/* On traite les quatre cas dans les quatre directions pour chaque point sauf les extrÃ©mitÃ©s */
					
					if(j > 0) { /* Si on n'est pas sur la premiÃ¨re colonne de la ligne i */
						p2 = new Point(this.cases[i][j - 1]); // la case prÃ©cÃ©dente
						arrete = new Arrete(p1, p2, poids);
						if (robot.isPlaceable(p2.getCase())) 
                                                    liens.add(arrete); /* Si le robot peut accÃ©der Ã  cette case alors on 
							ajoute l'arrÃªte associÃ©e aux liens du point */
					}
                                        
					if (j < this.cases[0].length - 1){ /* Si on n'est pas sur la derniÃ¨re colonne de la ligne i */
						p2 = new Point(this.cases[i][j + 1]);
						arrete = new Arrete(p1, p2, poids);
						if (robot.isPlaceable(p2.getCase())) 
                                                    liens.add(arrete);
					}
					if ( i > 0) { /* Si on n'est pas sur la premiÃ¨re ligne */
						p2 = new Point(this.cases[i - 1][j]);
						arrete = new Arrete(p1, p2, poids);
						if(robot.isPlaceable(p2.getCase())) 
                                                    liens.add(arrete);
					}

					if (i < this.cases.length - 1){ /* Si on n'est pas sur la derniÃ¨re ligne */
						p2 = new Point(this.cases[i + 1][j]);
						arrete = new Arrete(p1, p2, poids);
						if(robot.isPlaceable(p2.getCase())) 
                                                    liens.add(arrete);
					}
						
				}
				else {
					poids = 100000;
					
					if(j > 0) { /* Si on n'est pas sur la premiÃ¨re colonne de la ligne i */
						p2 = new Point(this.cases[i][j - 1]); // la case prÃ©cÃ©dente
						arrete = new Arrete(p1, p2, poids);
						if (robot.isPlaceable(p2.getCase())) 
                                                    liens.add(arrete); /* Si le robot peut accÃ©der Ã  cette case alors on 
							ajoute l'arrÃªte associÃ©e aux liens du point */
					}
                                        
					if (j < this.cases[0].length - 1){ /* Si on n'est pas sur la derniÃ¨re colonne de la ligne i */
						p2 = new Point(this.cases[i][j + 1]);
						arrete = new Arrete(p1, p2, poids);
						if (robot.isPlaceable(p2.getCase())) 
                                                    liens.add(arrete);
					}
					if ( i > 0) { /* Si on n'est pas sur la premiÃ¨re ligne */
						p2 = new Point(this.cases[i - 1][j]);
						arrete = new Arrete(p1, p2, poids);
						if(robot.isPlaceable(p2.getCase())) 
                                                    liens.add(arrete);
					}

					if (i < this.cases.length - 1){ /* Si on n'est pas sur la derniÃ¨re ligne */
						p2 = new Point(this.cases[i + 1][j]);
						arrete = new Arrete(p1, p2, poids);
						if(robot.isPlaceable(p2.getCase())) 
                                                    liens.add(arrete);
					}
				}
                                
				Point point = new Point(caseTraitee, liens); /* On crÃ©e le point avec ses liens */
				matrice[i][j] = point;
			}
					
		}
        
		Graphe graphe = new Graphe(matrice);
		return graphe;	
    }
       
}

