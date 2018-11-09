import java.io.FileNotFoundException;
import java.util.List;
import java.util.zip.DataFormatException;

import data.Case;
import data.DonneesSimulation;
import data.Incendie;
import data.enumerate.Direction;
import data.robot.Robot;
import events.Evenement;
import events.EvenementMessage;
import gui2.Simulateur;
import io.LecteurDonnees;
import data.enumerate.NatureTerrain;



/**
 * Test des évènements (organisation des évènements)
 */

public class TestEvenementBis {

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
            Robot roues = donnees.getRobots()[0];
            Robot drone = donnees.getRobots()[1];
            Robot pattes = donnees.getRobots()[2];
            Robot chenilles = donnees.getRobots()[3];
            System.out.println("tempsRemplissage de Chenilles : " + drone.getTempsRemplissageComplet());

            /*On modifie la position initiale du drone*/
            Case case_initiale = donnees.getCarte().getCase(1, 1);
            chenilles.setPosition(case_initiale);
            int nbLignes = donnees.getCarte().getNbLignes();
            int nbColonnes = donnees.getCarte().getNbLignes();
            int tailleCases = donnees.getCarte().getTailleCases();
            /* Création d'un simulateur et ajout des évènements */
            Simulateur simulateur2 = new Simulateur(0, donnees);

            /* TEST EVENEMENT DEPLACEMENT UNITAIRE */

            System.out.println(pattes.getVitesse(NatureTerrain.TERRAIN_LIBRE));
            Case dest = donnees.getCarte().getCase(16, 16);
            chenilles.deplacementCase(dest, simulateur2);

            // for (int i=0; i<50; i++) {
            //     try {
            //       Thread.sleep(500);
            //     } catch(InterruptedException ex) {
            //       Thread.currentThread().interrupt();
            //     }
            //     simulateur2.next();
            // }
            //
            // simulateur2.restart();

            /* TEST INTERVENTION */
            // Incendie incendie = donnees.getIncendies().get(6);
            // System.out.println("incendie : " + incendie);
            // chenilles.ordreIntervention(simulateur2, incendie);
            //
            // // chenilles.ordreRemplissage(simulateur2);
            //
            // incendie = donnees.getIncendies().get(7);
            // chenilles.ordreIntervention(simulateur2, incendie);

            // for (int i=0; i<25; i++) {
            //     simulateur2.incrementeDate();
            // }
            // chenilles.ordreRemplissage(simulateur2);
            // dest = donnees.getCarte().getCase(6, 6);
            // roues.deplacementCase(dest, simulateur2, 20);

            // dest = donnees.getCarte().getCase(5, 7);
            // pattes.deplacementCase(dest, simulateur2, 45);

           // simulateur2.incrementeDate();
           // //
           // simulateur2.incrementeDate();

            /* TEST DEPLACEMENT CASE
             * 		OK pour les déplacements case quand le chemin est fixé et donné par l'utilisateur
             * 		Testé quand on ne fait pas appel au plus cours chemin, qui lui marche pas
             * 		26/10
             */

//            /* TEST DEPLACEMENT DIRECTION */

        } catch (FileNotFoundException e) {
            System.out.println("fichier " + args[0] + " inconnu ou illisible");
        } catch (DataFormatException e) {
            System.out.println("\n\t**format du fichier " + args[0] + " invalide: " + e.getMessage());
        }
    }
}
