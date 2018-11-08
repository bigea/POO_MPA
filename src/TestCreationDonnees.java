
import io.LecteurDonnees;
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
        	//Affichage des données
        	System.out.println(donnees);
        } catch (FileNotFoundException e) {
            System.out.println("fichier " + args[0] + " inconnu ou illisible");
        } catch (DataFormatException e) {
            System.out.println("\n\t**format du fichier " + args[0] + " invalide: " + e.getMessage());
        }
    }
}
