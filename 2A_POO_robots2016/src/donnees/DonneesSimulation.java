/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package donnees;

import donnees.robot.Robot;
import java.util.List;

/**
 *
 * @author macintosh
 */

public class DonneesSimulation {
    private Carte carte;
    private List<Incendie> incendies;
    private List<Robot> robots;
    
    /**
     * Contruire les donnees pour la simulation
     * @param carte
     * @param incendies
     * @param robots 
     */
    public DonneesSimulation(Carte carte, List<Incendie> incendies, List<Robot> robots){
	this.carte = carte;
	this.incendies = incendies;
	this.robots = robots;
    }
    
    /**
     * Accesseurs
     */
    public Carte getCarte() { 
        return this.carte;
    }
    
    public Incendie getIncendie(int numero) { 
        return this.incendies.get(numero);
    }
    
    public Robot getRobot(int numero) {
        return this.robots.get(numero);
    }
    
    public List<Incendie> getIncendies(){
    	return this.incendies;
    }
    
    public List<Robot> getRobots(){
    	return this.robots;
    }
    
    /**
     * Mutateurs
     */
    public void setCarte(Carte carte) {
        this.carte = carte;
    }

    public void setIncendies(List<Incendie> incendies) {
        this.incendies = incendies;
    }

    public void setRobots(List<Robot> robots) {
        this.robots = robots;
    }
    
    public void addIncendie(Incendie incendie) {
        this.incendies.add(incendie);
    }

    public void addRobot(Robot robot) {
        this.robots.add(robot);
    }

    public boolean removeRobot(Robot robot) {
        return this.robots.remove(robot);
    }

    public boolean removeIncendie(Incendie incendie) {
        return this.incendies.remove(incendie);
    }
}
