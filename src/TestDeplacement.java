import java.io.FileNotFoundException;
import java.util.zip.DataFormatException;

import data.Case;
import data.DonneesSimulation;
import data.robot.Robot;
import events.DeplacementUnitaire;
import gui2.Simulateur;
import io.LecteurDonnees;



/**
 * TestDeplacement
 */

public class TestDeplacement {


	/**
	 * TestDeplacement
	 * 		- vérifie que l'évènement déplacement unitaire fonctionne
	 */

    public static void main(String[] args) {
    	System.out.println("------ TEST EVENEMENT DEPLACEMENT -------");
        try {
            DonneesSimulation donnees = LecteurDonnees.creeDonnees(args[0]);
            Simulateur simulateur = new Simulateur(donnees);
            Robot rbt = donnees.getRobots()[0];
            Case dest = donnees.getCarte().getCase(1, 1);
            System.out.println(rbt.getPosition());
            simulateur.ajouteEvenement(new DeplacementUnitaire(0, simulateur, rbt, dest));
            simulateur.incrementeDate();
            System.out.println(rbt.getPosition());
        } catch (FileNotFoundException e) {
            System.out.println("fichier " + args[0] + " inconnu ou illisible");
        } catch (DataFormatException e) {
            System.out.println("\n\t**format du fichier " + args[0] + " invalide: " + e.getMessage());
        }
    }
}
