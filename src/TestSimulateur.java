
import data.Carte;
import data.Case;
import data.Incendie;
import data.enumerate.NatureTerrain;
import data.robot.Drone;
import data.robot.Robot;
import events.Evenement;
import events.EvenementDeplacement;
import events.EvenementIntervention;
import events.EvenementMessage;
import events.EvenementRemplissage;

/**
 * Test du simulateur (organisation des évènements)
 */

public class TestSimulateur {

    public static void main(String[] args) {
    	/* TEST EVENEMENT */
    	System.out.println("------ TEST EVENEMENTS -------");
    	System.out.println("---- Test EvenementMessage");
    	Evenement message = new EvenementMessage(1 , null, " [ PING ] " );
    	message.execute();
    	
    	System.out.println("---- Test EvenementDeplacement : drone 2 3 ROCHE => 5 6 FORET");
    	Carte carte = new Carte(8,8,2);
    	Case pos = new Case(2,3,NatureTerrain.ROCHE);
    	carte.setCase(pos);
    	Robot rbt = new Drone(pos, carte);
    	Case dest = new Case(5,6,NatureTerrain.FORET);
    	carte.setCase(dest);
    	Evenement dpltc = new EvenementDeplacement(3, null, rbt, dest);
    	dpltc.execute();
    	System.out.println(rbt);
    	
    	System.out.println("---- Test EvenementIntervention et EvenementRemplissage");
    	Incendie inc = new Incendie(pos, 10500);
    	Evenement inter = new EvenementIntervention(5, null, rbt, inc);
    	inter.execute();
    	Evenement remplir = new EvenementRemplissage(6, null, rbt);
    	remplir.execute();
    	Evenement inter2 = new EvenementIntervention(7, null, rbt, inc);
    	inter2.execute();
    	
    	/* TEST SIMULATEUR */
//    	/* Création d'un simulateur et ajout des évènements */
//        Simulateur simulateur1 = new Simulateur(2);
//        for(int i = 2 ; i <= 10 ; i += 2) {
//        	simulateur1.ajouteEvenement(new EvenementMessage(i , " [ PING ] " ));
//        }
//        for(int i = 3 ; i <= 9 ; i += 3) {
//        	simulateur1.ajouteEvenement(new EvenementMessage(i , " [ PONG ] " ));
//        }
//        /* on exécute le simulateur */
//        for(int i = 2 ; i <= 10 ; i ++) {
//        	simulateur1.incrementeDate();
//        }
    }
}

