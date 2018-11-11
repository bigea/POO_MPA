import java.io.FileNotFoundException;
import java.util.zip.DataFormatException;

import data.DonneesSimulation;
import gui2.Simulateur;
import io.LecteurDonnees;
import strategie.ChefAvance;



/**
 * TestStratégieAvance (avec chef pompier avancé)
 */

public class TestStrategieAvancee {

    public static void main(String[] args) {
    	/* TEST EVENEMENT */
    	System.out.println("------ TEST STRATEGIE -------");

        try {
        	/* On récupère la carte et les données */
            DonneesSimulation donnees = LecteurDonnees.creeDonnees(args[0]);
            /* Création d'un simulateur et ajout des évènements */
            Simulateur simulateur = new Simulateur(donnees);

            /* TEST STRATEGIE */
            ChefAvance chef = new ChefAvance(simulateur);
            chef.commencerStrategie();

        } catch (FileNotFoundException e) {
            System.out.println("fichier " + args[0] + " inconnu ou illisible");
        } catch (DataFormatException e) {
            System.out.println("\n\t**format du fichier " + args[0] + " invalide: " + e.getMessage());
        }
    }
}
