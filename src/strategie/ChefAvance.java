package strategie;

import chemin.Chemin;
import data.Case;
import data.Incendie;
import data.robot.Robot;
import gui2.Simulateur;

/**
 * Classe ChefAvance
 *    Pemet de définir la stratégie globale plus avancée
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
     * 		tant qu'il reste des incendies et des robots pas affectés, on fait la stratégie unitaire
     */
     public void strategie() {
     	long dateMin;
     	Robot rbtPlusProche;
     	/* pour tous les incendies non affectés*/
     	// while(this.resteIncendiePasAffecte()) {
            //  le principe d'attendre entre chaque boucle (sinon boucle infini)
         	// 	Il faudra mettre le for qui suit dedans
         	// 	this.attend(dateCourante);
            long dateCourante = this.getSimulateur().getDateSimulation();
            for(Incendie inc: this.getIncendies()){
                /* si l'incendie n'est pas affecté */
                if(this.affectations.get(inc) == null) {
                    /* pour trouver le robot le plus proche */
                    dateMin = INFINI;
                    rbtPlusProche = null;
                    /* position de l'incendie */
                    Case dest = inc.getPosition();
                    /* On propose à chacun des robots cet incendie */
                    for(int j=0; j<this.getNbRobots();j++) {
                        Robot rbt = this.getRobots()[j];
                        /* Si le robot peut s'en occuper */
                        if(rbt.estDisponible(dateCourante) && rbt.possibleDeplacement(dest)) {
                            /* Calcul du plus court chemin et on récupère la date de fin */
                            Chemin chemin = rbt.plusCourt(dest, dateCourante, carte);
                            int ind = chemin.getNbCase();
                            long dateFin = chemin.getDates().get(ind-1);
                            /* On garde seulement le temps minimum */
                            if(dateFin < dateMin) {
                                dateMin = dateFin;
                                rbtPlusProche = rbt;
                            }
                        }
                    }
                    /* Si on a un robot le plus proche */
                    if(rbtPlusProche != null) {
                        /* on affecte l'incendie au robot le plus proche */
                        this.affectations.put(inc, rbtPlusProche);
                        /* on lui demande d'intervenir */
                         rbtPlusProche.ordreIntervention(this.sim, inc);
                         /* A AJOUTER : le robot doit vérifier que l'incendie n'a pas déjà été eteint une fois arrivé sur place */
                    }
                }
            }
        // }
    }
}
