package events;

import data.Case;
import data.robot.Robot;
import gui2.Simulateur;

/**
 * Classe DeplacementUnitaire
 */

public class DeplacementUnitaire extends Evenement{
    /**
	 * Classe DeplacementUnitaire :
	 * 		hérite du modèle Evenement et effectue le déplacement du robot
	 * 		Jusqu'à une case voisine
	 */
	
    private Case destination;
    
    /*********************************************
     *
     * METHODES DE BASE
     */

     /* Constructeur */
     public DeplacementUnitaire(long date, Simulateur sim, Robot rbt, Case dest) {
         super(date, sim, rbt);
         this.setDestination(dest);
     }

     @Override
     public String toString() {
         String chaine = new String();
         chaine += "DeplacementUnitaire (" + this.getDate() + ", " + this.getRobot().getNature() + ")";
         return chaine;
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
         this.getRobot().setPosition(this.getDestination());
     }
}
