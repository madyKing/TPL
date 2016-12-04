package simu;

import java.util.*;

import donnees.*;
import donnees.robot.*;

/**
 *
 * Dans cette classe on met en place la strat�gie �l�mentaire, qui est une solution brute au probl�me
 *
 */
public class StrategieElementaire extends ChefPompier {

    /**
     * Constructeur de la classe StrategieElementaire
     * @param simul
     */
    public StrategieElementaire(Simulateur simul) {
	super(simul);
    }

    /**
     * M�thode de gestion autonome avec la vision simple
	 */

    public void deploier() {
	boolean a;
	List<Incendie> incendies = this.donnees.getIncendies();
	List<Robot> robots = this.donnees.getRobots();
	//List<Evenement> events = new ArrayList<Evenement>();


	for(int i = 0; i < incendies.size(); i++) { /* on parcout la liste des incendies */
      if (incendies.get(i).getIntensite() > 0){
      for (int j = 0; j < robots.size(); j++) {/* on parcourt la liste des robots
						       /* on teste pour chaque sa disponibilit� et sa capacit� � �teindre l'incendie,
						       * si bon, on agit
						       * sinon on continue */
		if(robots.get(j).estDisponible()){
		//    System.out.println("Intervention r�ussie!");
		    a = robots.get(j).EteindreFeu(incendies.get(i),this.getDonnees().getCarte(),this.simul );
		}
		
	    }
    }
	}

    }
}
