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
        	/* On récupère la carte et les données */
            // DonneesSimulation donnees = LecteurDonnees.creeDonnees(args[0]);
            /* Création d'un simulateur et ajout des évènements */
            Simulateur simulateur = new Simulateur(args);

            /* TEST STRATEGIE */
            ChefAvance chef = new ChefAvance(simulateur);
            chef.commencerStrategie();
    }
}
