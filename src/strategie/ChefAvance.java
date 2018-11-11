package strategie;

import chemin.Chemin;
import data.Case;
import data.Incendie;
import data.robot.Robot;
import gui2.Simulateur;

/**
 * Classe ChefAvance
 */

public class ChefAvance extends Chef {

    protected static final long TEMPS = 2147483647;

    /*********************************************
    *
    * METHODES DE BASE
    */

    public ChefAvance(Simulateur sim) {
    	super(sim);
    }

	/*********************************************
    *
    * METHODES DE STRATEGIE
    */

    /* Méthode de stratégie
     * 		pour chaque robot, on prend l'incendie non affecté le plus proche
     */
    public void strategie() {
    	long dateMin;
    	/* pour tous les incendies non affectés*/
        if (this.incendies.size()>0) {
        	while(this.resteIncendiePasAffecte() && this.resteRobotPasAffecte()) {
            	long dateCourante = this.getSimulateur().getDateSimulation();
            	for(int i=0; i<this.getNbRobots();i++) {
            		Robot rbt = this.getRobots()[i];
            		Incendie incPlusProche = null;
            		dateMin = INFINI;
            		if(rbt.estDisponible(dateCourante)) {
                		for(int j=0; j<this.getIncendies().size();j++) {
                			Incendie inc = this.getIncendies().get(j);
                			Case dest = inc.getPosition();
                			if(this.affectations.get(inc) == null && rbt.possibleDeplacement(dest)) {
                				Chemin chemin = rbt.plusCourt(dest, dateCourante, carte);
                				int ind = chemin.getNbCase();
                                long dateFin;
                                if (ind>0) {
                                    dateFin = chemin.getDates().get(ind-1);
                                } else {
                                    dateFin = dateCourante;
                                }
                				/* On garde seulement le temps minimum */
                				if(dateFin < dateMin) {
                					dateMin = dateFin;
                					incPlusProche = inc;
                				}
                			}
                		}
            		}
            		if(incPlusProche != null) {
            			/* on affecte l'incendie le plus proche au robot */
                		this.affectations.put(incPlusProche, rbt);
                		/* on lui demande d'intervenir */
                        rbt.ordreIntervention(this.sim, incPlusProche);
            		}

            	}
            }
        } else {
            System.out.println("Tous les incendies sont éteints en " + this.sim.getDateSimulation() + " secondes ! Bravo ! ");
        }
    }
}
