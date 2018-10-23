import data.Carte;
import data.Case;
import data.enumerate.Direction;
import data.enumerate.NatureTerrain;
import data.robot.Drone;
import data.robot.Robot;
import events.Evenement;
import events.EvenementDeplacementDirection;
import events.EvenementDeplacementUnitaire;
import events.EvenementMessage;
import gui2.Simulateur;


/**
 * Test du simulateur (organisation des évènements)
 */

public class TestSimulateur {

    public static void main(String[] args) {
    	/* TEST EVENEMENT */
    	System.out.println("------ TEST EVENEMENTS -------");
    	System.out.println("---- Test EvenementMessage");
    	Evenement message = new EvenementMessage(1 , null, " [ PING ] " );
    	try {
    		message.execute();
    	} catch (Exception e) {
    		System.out.println("Evènement impossible");
    	}


    	/* TEST SIMULATEUR */
    	System.out.println("------ TEST SIMULATEUR -------");
    	System.out.println("------ Test EvenementMessage -------");
    	/* Création d'un simulateur et ajout des évènements */
        Simulateur simulateur1 = new Simulateur(2, 8, 6, 20);
        for(int i = 2 ; i <= 10 ; i += 2) {
        	simulateur1.ajouteEvenement(new EvenementMessage(i , simulateur1, " [ PING ] " ));
        }
        for(int i = 3 ; i <= 9 ; i += 3) {
        	simulateur1.ajouteEvenement(new EvenementMessage(i , simulateur1, " [ PONG ] " ));
        }
        /* on exécute le simulateur (sur 60 secondes)*/
        try{
        	simulateur1.incrementeDate();
        } catch (Exception e) {
    		System.out.println("Evènement impossible");
        }

        /* Création d'une carte et de ses attributs */
        Carte carte = new Carte(3,3,10);
        Case cas11 = new Case(0,0,NatureTerrain.FORET);
        Case cas12 = new Case(0,1,NatureTerrain.FORET);
        Case cas13 = new Case(0,2,NatureTerrain.EAU);
        Case cas21 = new Case(1,0,NatureTerrain.ROCHE);
        Case cas22 = new Case(1,1,NatureTerrain.EAU);
        Case cas23 = new Case(1,2,NatureTerrain.HABITAT);
        Case cas31 = new Case(2,0,NatureTerrain.HABITAT);
        Case cas32 = new Case(2,1,NatureTerrain.FORET);
        Case cas33 = new Case(2,2,NatureTerrain.HABITAT);
        carte.setCase(cas11);
        carte.setCase(cas12);
        carte.setCase(cas13);
        carte.setCase(cas21);
        carte.setCase(cas22);
        carte.setCase(cas23);
        carte.setCase(cas31);
        carte.setCase(cas32);
        carte.setCase(cas33);
        Robot drone = new Drone(cas11,carte);
        System.out.println(drone);
        /* Création d'un simulateur et ajout des évènements */
        Simulateur simulateur2 = new Simulateur(2, 800, 600, 20);
        Evenement event = new EvenementDeplacementUnitaire(3,simulateur2,drone,cas21);
        simulateur2.ajouteEvenement(event);
        event = new EvenementDeplacementUnitaire(5,simulateur2,drone,cas22);
        simulateur2.ajouteEvenement(event);
        event = new EvenementDeplacementDirection(7,simulateur2,drone,Direction.SUD);
        simulateur2.ajouteEvenement(event);
        simulateur2.incrementeDate();
        System.out.println(drone);

    }
}
