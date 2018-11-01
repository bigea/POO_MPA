package events;

import data.Case;
import data.robot.Robot;
import gui2.Simulateur;

/**
 * Classe EvenementDeplacementCase
 */

public class DeplacementUnitaire extends Evenement{
    /**
	 * Classe EvenementDeplacementCase :
	 * 		hérite du modèle Evenement et effectue le déplacement du robot
	 * 		Jusqu'à une case quelconque
	 */
    private Case destination;
    /*********************************************
     *
     * METHODES DE BASE
     */

     /* Constructeur */
     public DeplacementUnitaire(long date, Simulateur sim, Robot rbt, long duree, Case dest) {
         super(date, sim, rbt, duree);
         this.setDestination(dest);
     }
     /* Mutateurs */
     public void setDestination(Case dest) {
         this.destination = dest;
     }

     /* Accesseur */
     public Case getDestination() {
         return this.destination;
     }
     /*********************************************
      *
      * EXECUTION
      */

     /* Exécution de l'évènement */
     public void execute() {
         /* Le robot va gérer le déplacement vers la case */
        //  this.getRobot().deplacementCase(this.getCase(), this.getSimulateur());
         this.getRobot().setPosition(this.getDestination());
     }
}
