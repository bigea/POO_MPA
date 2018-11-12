import java.io.FileNotFoundException;
import java.util.zip.DataFormatException;

import data.DonneesSimulation;
import gui2.Simulateur;
import io.LecteurDonnees;
import strategie.ChefElementaire;



/**
 * Test Stratégie Elementaire (avec chef pompier élémentaire)
 */

public class TestStrategieElementaire {

    public static void main(String[] args) {
    	System.out.println("------ TEST STRATEGIE -------");
        try {
            DonneesSimulation donnees = LecteurDonnees.creeDonnees(args[0]);
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
