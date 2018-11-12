import java.io.FileNotFoundException;
import java.util.zip.DataFormatException;

import data.DonneesSimulation;
import events.EvenementMessage;
import gui2.Simulateur;
import io.LecteurDonnees;



/**
 * TestSimulateur
 */

public class TestSimulateur {
	/**
	 * TestSimulateur
	 * 		- vérifie que les évènements ont bien lieu au bon moment quand le simulateur incrémente sa date
	 */

    public static void main(String[] args) {
    	System.out.println("------ TEST SIMULATEUR -------");
        try {
            DonneesSimulation donnees = LecteurDonnees.creeDonnees(args[0]);
            Simulateur simulateur = new Simulateur(donnees);
            for(int i = 2 ; i <= 10 ; i += 2) {
            	simulateur.ajouteEvenement(new EvenementMessage(i , simulateur, " [ PING ] " ));
            }
            for(int i = 3 ; i <= 9 ; i += 3) {
            	simulateur.ajouteEvenement(new EvenementMessage(i , simulateur, " [ PONG ] " ));
            }
            try{
            	simulateur.incrementeDate();
            } catch (Exception e) {
        		System.out.println("Evènement impossible");
            }
        } catch (FileNotFoundException e) {
            System.out.println("fichier " + args[0] + " inconnu ou illisible");
        } catch (DataFormatException e) {
            System.out.println("\n\t**format du fichier " + args[0] + " invalide: " + e.getMessage());
        }
    }
}
