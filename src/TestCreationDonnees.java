
import io.LecteurDonnees;
import gui2.*;

import java.io.FileNotFoundException;
import java.util.zip.DataFormatException;

import data.DonneesSimulation;

/**
 * Test de la création des données à partir du fichier
 */

public class TestCreationDonnees {

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Syntaxe: java TestCreationDonnees <nomDeFichier>");
            System.exit(1);
        }

        try {
        	DonneesSimulation donnees = LecteurDonnees.creeDonnees(args[0]);
        	
        	//System.out.println("coucou");
        	//sim.gererDonnees(donnees);
        	System.out.println(donnees);
        	Simulateur simulateur1 = new Simulateur(2, 8, 6, 20);
        	simulateur1.gererDonnees(donnees);;
        } catch (FileNotFoundException e) {
            System.out.println("fichier " + args[0] + " inconnu ou illisible");
        } catch (DataFormatException e) {
            System.out.println("\n\t**format du fichier " + args[0] + " invalide: " + e.getMessage());
        }
    }
}

