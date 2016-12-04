package simu;

import donnees.DonneesSimulation;

/**
 * Le chef d'équipe de robots pompiers qui décider les actions des robots
 *
 */
public abstract class ChefPompier {
    protected Simulateur simul;
    protected DonneesSimulation donnees; //carte + liste incendies + listes robots
    
    /**
     * Constructeur ChefPompier
     * @param  donnees: les donn�es du probl�me (Carte, Robots, Incendies)
     */
    public ChefPompier(Simulateur simul) {
	this.simul = simul;
	this.donnees = simul.getData();
    }
    
    /**
     * Modificateurs
     * @param donnees
     */
    public void setDonnees(DonneesSimulation donnees) {
	this.donnees = donnees;
    }
    
    public DonneesSimulation getDonnees() {
	return this.donnees;
    }
    
    abstract public void deploier();
}
