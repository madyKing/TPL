package simu;

import java.util.ArrayList;
import java.util.List;

import donnees.Incendie;
import donnees.robot.Robot;

/**
 *
 * Dans cette classe on met en place la strat�gie �volu�e, qui est une solution plus �labor�e au probl�me
 *
 */
public class StrategieEvoluee extends ChefPompier {

    /**
     * constructeur de classe
     * @param simul
     */
    public StrategieEvoluee(Simulateur simul) {
	super(simul);
    }

    /**
     * M�thode de gestion autonome avec la version �labor�e
     */

    public void deploier(){
	List<Incendie> incendies = this.donnees.getIncendies();
	List<Robot> robots = this.donnees.getRobots();
	List<Long> tabTemps = new ArrayList<Long>() ;
	int choix = 0;

	for(int i = 0; i < incendies.size(); i++) { /* on parcout la liste des incendies */
	    for (int j =0; j < robots.size(); j++) {/* on parcourt la liste des robots */

		/* on teste pour chaque sa disponibilit� */
		if(robots.get(j).estDisponible()) {
		    tabTemps.add(j,robots.get(j).tempsPCC(robots.get(j).getPosition(), incendies.get(j).getCase(), this.simul.getData().getCarte()));
		}

		long min = tabTemps.get(0);

		for (int k =0; k < tabTemps.size(); k++){
		    if (tabTemps.get(k) < min)
			min = tabTemps.get(k);
		    choix = k;
		}
	    }
	    if (incendies.get(i).getIntensite() != 0)
		robots.get(choix).EteindreFeu(incendies.get(i),this.getDonnees().getCarte(),this.simul);
	}
    }
}
