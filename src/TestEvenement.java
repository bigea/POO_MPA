import java.io.FileNotFoundException;
import java.util.zip.DataFormatException;

import data.Case;
import data.DonneesSimulation;
import data.robot.Robot;
import events.Evenement;
import events.EvenementDeplacementUnitaire;
import events.EvenementMessage;
import gui2.Simulateur;
import io.LecteurDonnees;


/**
 * Test des évènements (organisation des évènements)
 */

public class TestEvenement {

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

        /* TESTS SUR UNE CARTE */
        /* Création d'une carte et de ses attributs */
        if (args.length < 1) {
            System.out.println("Syntaxe: java TestCreationDonnees <nomDeFichier>");
            System.exit(1);
        }

        try {
        	/* On récupère la carte et les données */
            DonneesSimulation donnees = LecteurDonnees.creeDonnees(args[0]);
            Robot drone = donnees.getRobots()[0];
            Robot roues = donnees.getRobots()[1];
            Robot pattes = donnees.getRobots()[2];
            int nbLignes = donnees.getCarte().getNbLignes();
            int nbColonnes = donnees.getCarte().getNbLignes();
            int tailleCases = donnees.getCarte().getTailleCases();
            /* Création d'un simulateur et ajout des évènements */
            Simulateur simulateur2 = new Simulateur(1, nbLignes, nbColonnes, tailleCases);
//            Case dest = donnees.getCarte().getCase(3, 4);
//            Evenement event = new EvenementDeplacementUnitaire(62,simulateur2,drone,dest);
//            simulateur2.ajouteEvenement(event);
//            dest = donnees.getCarte().getCase(6, 6);
//            event = new EvenementDeplacementUnitaire(20,simulateur2,roues,dest);
//            simulateur2.ajouteEvenement(event);
//            dest = donnees.getCarte().getCase(5, 7);
//            event = new EvenementDeplacementUnitaire(45,simulateur2,pattes,dest);
//            simulateur2.ajouteEvenement(event);
//            simulateur2.incrementeDate();
//            System.out.println(donnees);
//            simulateur2.incrementeDate();
//            System.out.println(donnees);
        } catch (FileNotFoundException e) {
            System.out.println("fichier " + args[0] + " inconnu ou illisible");
        } catch (DataFormatException e) {
            System.out.println("\n\t**format du fichier " + args[0] + " invalide: " + e.getMessage());
        }
    }
}
