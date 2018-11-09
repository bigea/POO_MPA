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
import strategie.ChefElementaire;



/**
 * Test Stratégie Elementaire (avec chef pompier élémentaire)
 */

public class TestStrategieElementaire {

    public static void main(String[] args) {
    	/* TEST EVENEMENT */
    	System.out.println("------ TEST STRATEGIE -------");

        try {
        	/* On récupère la carte et les données */
            DonneesSimulation donnees = LecteurDonnees.creeDonnees(args[0]);
            Robot roues = donnees.getRobots()[0];
            Robot drone = donnees.getRobots()[1];
            Robot pattes = donnees.getRobots()[2];
            // Robot chenilles = donnees.getRobots()[3];
            int nbLignes = donnees.getCarte().getNbLignes();
            int nbColonnes = donnees.getCarte().getNbLignes();
            int tailleCases = donnees.getCarte().getTailleCases();
            /* Création d'un simulateur et ajout des évènements */
            Simulateur simulateur = new Simulateur(donnees);

            /* TEST STRATEGIE */
            ChefElementaire chef = new ChefElementaire(simulateur);
            chef.commencerStrategie();

        } catch (FileNotFoundException e) {
            System.out.println("fichier " + args[0] + " inconnu ou illisible");
        } catch (DataFormatException e) {
            System.out.println("\n\t**format du fichier " + args[0] + " invalide: " + e.getMessage());
        }
    }
}
