package io;


import java.io.*;
import java.util.*;
import java.util.zip.DataFormatException;

import data.Carte;
import data.Case;
import data.DonneesSimulation;
import data.Incendie;
import data.enumerate.NatureTerrain;
import data.robot.Chenilles;
import data.robot.Drone;
import data.robot.Pattes;
import data.robot.Robot;
import data.robot.Roues;



/**
 * Lecteur de cartes au format spectifié dans le sujet.
 * Les données sur les cases, robots puis incendies sont lues dans le fichier,
 * puis simplement affichées.
 * A noter: pas de vérification sémantique sur les valeurs numériques lues.
 *
 * IMPORTANT:
 *
 * Cette classe ne fait que LIRE les infos et les afficher.
 * A vous de modifier ou d'ajouter des méthodes, inspirées de celles présentes
 * (ou non), qui CREENT les objets au moment adéquat pour construire une
 * instance de la classe DonneesSimulation à partir d'un fichier.
 *
 * Vous pouvez par exemple ajouter une méthode qui crée et retourne un objet
 * contenant toutes les données lues:
 *    public static DonneesSimulation creeDonnees(String fichierDonnees);
 * Et faire des méthode creeCase(), creeRobot(), ... qui lisent les données,
 * créent les objets adéquats et les ajoutent ds l'instance de
 * DonneesSimulation.
 */
public class LecteurDonnees {

    /**
     * Lit et affiche le contenu d'un fichier de donnees (cases,
     * robots et incendies).
     * Ceci est méthode de classe; utilisation:
     * LecteurDonnees.lire(fichierDonnees)
     * @param fichierDonnees nom du fichier à lire
     */
    public static void lire(String fichierDonnees)
        throws FileNotFoundException, DataFormatException {
        System.out.println("\n == Lecture du fichier" + fichierDonnees);
        LecteurDonnees lecteur = new LecteurDonnees(fichierDonnees);
        lecteur.lireCarte();
        lecteur.lireIncendies();
        lecteur.lireRobots();
        scanner.close();
        System.out.println("\n == Lecture terminee");
    }

    /**
     * Lit et crée un objet DonneesSimulation avec les donnéees du fichier (cases,
     * robots et incendies).
     * Ceci est méthode de classe; utilisation:
     * LecteurDonnees.creeDonnees(fichierDonnees)
     * @param fichierDonnees nom du fichier à lire
     */
    public static DonneesSimulation creeDonnees(String fichierDonnees)
    	throws FileNotFoundException, DataFormatException {
    		// Nouveau objet LecteurDonnees
            LecteurDonnees lecteur = new LecteurDonnees(fichierDonnees);
            // Nouvel objet carte à partir du fichier
            Carte carte = lecteur.creeCarte();
            // On récupère le nombre d'incendies
            int nbIncendies = lecteur.nbIncendies();
            // Création des objets incendie dans un tableau
            Incendie[] incendies = lecteur.creeIncendies(nbIncendies, carte);
            // On récupère le nombre de robots
            int nbRobots = lecteur.nbRobots();
            //Création des objets robots dans un tableau
            Robot[] robots = lecteur.creeRobots(nbRobots, carte);
            // On va compter le nombre de case d'eau et mettre chaque case eau dans une list de cases
            int nbCaseEau = 0;
            List<Case> eaux = new ArrayList<Case>();
            for (int i=0; i<carte.getNbLignes(); i++){
              for (int j=0; j<carte.getNbColonnes(); j++){
                Case currentCase = carte.getCase(i,j);
                if (currentCase.getNature() == NatureTerrain.EAU) {
                  nbCaseEau += 1;
                  eaux.add(currentCase);
                }
              }
            }
            // Nouvel objet DonneesSimulation (avec tableaux vides)
            DonneesSimulation donnees = new DonneesSimulation(carte, nbIncendies, nbRobots, nbCaseEau);
            // On remplie le tableau des incendies de donnees
            for(int i = 0; i < nbIncendies; i++) {
            	donnees.addIncendie(incendies[i]);
            }
            //On remplie le tableau des robots de donnees
            for(int i = 0; i < nbRobots; i++) {
            	donnees.addRobot(robots[i], i);
            }
            //On remplie le tableau des cases d'eau de donnees
            for(int i = 0; i < nbCaseEau; i++) {
            	donnees.addEau(eaux.get(i), i);
            }
            scanner.close();
            return donnees;
    }


    // Tout le reste de la classe est prive!

	private static Scanner scanner;

    /**
     * Constructeur prive; impossible d'instancier la classe depuis l'exterieur
     * @param fichierDonnees nom du fichier a lire
     */
    private LecteurDonnees(String fichierDonnees)
        throws FileNotFoundException {
        scanner = new Scanner(new File(fichierDonnees));
        scanner.useLocale(Locale.US);
    }


    /*******
     * LECTURE ET AFFICHAGE DES OBJETS (classes créées auparavant par les enseignants)
     */

    /**
     * Lit et affiche les donnees de la carte.
     * @throws ExceptionFormatDonnees
     */
    private void lireCarte() throws DataFormatException {
        ignorerCommentaires();
        try {
            int nbLignes = scanner.nextInt();
            int nbColonnes = scanner.nextInt();
            int tailleCases = scanner.nextInt();	// en m
            System.out.println("Carte " + nbLignes + "x" + nbColonnes
                    + "; taille des cases = " + tailleCases);

            for (int lig = 0; lig < nbLignes; lig++) {
                for (int col = 0; col < nbColonnes; col++) {
                    lireCase(lig, col);
                }
            }

        } catch (NoSuchElementException e) {
            throw new DataFormatException("Format invalide. "
                    + "Attendu: nbLignes nbColonnes tailleCases");
        }
    }

    /**
     * Lit et affiche les donnees d'une case.
     */
    private void lireCase(int lig, int col) throws DataFormatException {
        ignorerCommentaires();
        System.out.print("Case (" + lig + "," + col + "): ");
        String chaineNature = new String();
        try {
            chaineNature = scanner.next();
            verifieLigneTerminee();
            System.out.print("nature = " + chaineNature);

        } catch (NoSuchElementException e) {
            throw new DataFormatException("format de case invalide. "
                    + "Attendu: nature altitude [valeur_specifique]");
        }

        System.out.println();
    }

	/**
     * Lit et affiche les donnees des incendies.
     */
    private void lireIncendies() throws DataFormatException {
        ignorerCommentaires();
        try {
            int nbIncendies = scanner.nextInt();
            System.out.println("Nb d'incendies = " + nbIncendies);
            for (int i = 0; i < nbIncendies; i++) {
                lireIncendie(i);
            }

        } catch (NoSuchElementException e) {
            throw new DataFormatException("Format invalide. "
                    + "Attendu: nbIncendies");
        }
    }

    /**
     * Lit et affiche les donnees du i-eme incendie.
     * @param i
     */
    private void lireIncendie(int i) throws DataFormatException {
        ignorerCommentaires();
        System.out.print("Incendie " + i + ": ");

        try {
            int lig = scanner.nextInt();
            int col = scanner.nextInt();
            int intensite = scanner.nextInt();
            if (intensite <= 0) {
                throw new DataFormatException("incendie " + i
                        + "nb litres pour eteindre doit etre > 0");
            }
            verifieLigneTerminee();

            System.out.println("position = (" + lig + "," + col
                    + ");\t intensite = " + intensite);

        } catch (NoSuchElementException e) {
            throw new DataFormatException("format d'incendie invalide. "
                    + "Attendu: ligne colonne intensite");
        }
    }

    /**
     * Lit et affiche les donnees des robots.
     */
    private void lireRobots() throws DataFormatException {
        ignorerCommentaires();
        try {
            int nbRobots = scanner.nextInt();
            System.out.println("Nb de robots = " + nbRobots);
            for (int i = 0; i < nbRobots; i++) {
                lireRobot(i);
            }

        } catch (NoSuchElementException e) {
            throw new DataFormatException("Format invalide. "
                    + "Attendu: nbRobots");
        }
    }

    /**
     * Lit et affiche les donnees du i-eme robot.
     * @param i
     */
    private void lireRobot(int i) throws DataFormatException {
        ignorerCommentaires();
        System.out.print("Robot " + i + ": ");

        try {
            int lig = scanner.nextInt();
            int col = scanner.nextInt();
            System.out.print("position = (" + lig + "," + col + ");");
            String type = scanner.next();

            System.out.print("\t type = " + type);


            // lecture eventuelle d'une vitesse du robot (entier)
            System.out.print("; \t vitesse = ");
            String s = scanner.findInLine("(\\d+)");	// 1 or more digit(s) ?
            // pour lire un flottant:    ("(\\d+(\\.\\d+)?)");

            if (s == null) {
                System.out.print("valeur par defaut");
            } else {
                int vitesse = Integer.parseInt(s);
                System.out.print(vitesse);
            }
            verifieLigneTerminee();

            System.out.println();

        } catch (NoSuchElementException e) {
            throw new DataFormatException("format de robot invalide. "
                    + "Attendu: ligne colonne type [valeur_specifique]");
        }
    }


    /*******
     * LECTURE ET CREATION DES OBJETS
     */

    /**
     * Lit et construit un objet carte
     * @throws DataFormatException
     */
    private Carte creeCarte() throws DataFormatException {
    	ignorerCommentaires();
        try {
            int nbLignes = scanner.nextInt();
            int nbColonnes = scanner.nextInt();
            int tailleCases = scanner.nextInt();
            Carte carte = new Carte(tailleCases, nbLignes, nbColonnes);
            for (int lig = 0; lig < nbLignes; lig++) {
                for (int col = 0; col < nbColonnes; col++) {
                    carte.setCase(creeCase(lig, col, nbColonnes));
                }
            }
            return carte;

        } catch (NoSuchElementException e) {
            throw new DataFormatException("Format invalide. "
                    + "Attendu: nbLignes nbColonnes tailleCases");
        }
	}

    /**
     * Lit et construit un objet case
     * @throws DataFormatException
     */
    private Case creeCase(int lig, int col, int nbColonnes) throws DataFormatException {
        ignorerCommentaires();
        try {
            String chaineNature = scanner.next();
            verifieLigneTerminee();
            NatureTerrain nature = NatureTerrain.valueOf(chaineNature);
            int posAbs = lig * nbColonnes + col;
            Case cas = new Case(lig, col, nature, posAbs);
            return cas;
        } catch (NoSuchElementException e) {
            throw new DataFormatException("format de case invalide. "
                    + "Attendu: nature altitude [valeur_specifique]");
        }
    }

    /**
     * Lit et renvoie le nombre d'incendies
     * @throws DataFormatException
     */
    private int nbIncendies() throws DataFormatException {
    	ignorerCommentaires();
        try {
            int nbIncendies = scanner.nextInt();
            return nbIncendies;
        } catch (NoSuchElementException e) {
            throw new DataFormatException("Format invalide. "
                    + "Attendu: nbIncendies");
        }
    }

    /**
     * Lit et construit les objets Incendies
     * @throws DataFormatException
     */
    private Incendie[] creeIncendies(int nbIncendies, Carte carte) throws DataFormatException {
        ignorerCommentaires();
        try {
        	Incendie[] incendies = new Incendie[nbIncendies];
            for (int i = 0; i < nbIncendies; i++) {
                incendies[i] = creeIncendie(i, carte);
            }
            return incendies;

        } catch (NoSuchElementException e) {
            throw new DataFormatException("Format invalide. "
                    + "Attendu: nbIncendies");
        }
	}

    /**
     * Lit et construit le i-ème objet Incendie
     * @throws DataFormatException
     */
    private Incendie creeIncendie(int i, Carte carte) throws DataFormatException {
        ignorerCommentaires();
        try {
            int lig = scanner.nextInt();
            int col = scanner.nextInt();
            int intensite = scanner.nextInt();
            if (intensite <= 0) {
                throw new DataFormatException("incendie " + i
                        + "nb litres pour eteindre doit etre > 0");
            }
            verifieLigneTerminee();
            Incendie inc = new Incendie(carte.getCase(lig, col), intensite);
            return inc;

        } catch (NoSuchElementException e) {
            throw new DataFormatException("format d'incendie invalide. "
                    + "Attendu: ligne colonne intensite");
        }
    }

    /**
     * Lit et affiche les donnees des robots.
     */
    private int nbRobots() throws DataFormatException {
        ignorerCommentaires();
        try {
            int nbRobots = scanner.nextInt();
            return nbRobots;
        } catch (NoSuchElementException e) {
            throw new DataFormatException("Format invalide. "
                    + "Attendu: nbRobots");
        }
    }

    /**
     * Lit et construit les objets Robot
     * @throws DataFormatException
     */
    private Robot[] creeRobots(int nbRobots, Carte carte) throws DataFormatException {
        ignorerCommentaires();
        try {
        	Robot[] robots = new Robot[nbRobots];
            for (int i = 0; i < nbRobots; i++) {
                robots[i] = creeRobot(i, carte);
            }
            return robots;
        } catch (NoSuchElementException e) {
            throw new DataFormatException("Format invalide. "
                    + "Attendu: nbRobots");
        }
    }

    /**
     * Lit et construit le i-ème objet Robot
     * @throws DataFormatException
     */
    private Robot creeRobot(int i, Carte carte) throws DataFormatException {
        ignorerCommentaires();
        try {
            int lig = scanner.nextInt();
            int col = scanner.nextInt();
            String type = scanner.next();

            // lecture eventuelle d'une vitesse du robot (entier)
            String s = scanner.findInLine("(\\d+)");	// 1 or more digit(s) ?
            // pour lire un flottant:    ("(\\d+(\\.\\d+)?)");

            int vitesse;

            switch(type) {
            	case "DRONE":
                    Robot drone = new Drone(carte.getCase(lig, col));
                    if (s != null) {
                        vitesse = Integer.parseInt(s);
                        drone.setVitesse(vitesse);
                    };
                    verifieLigneTerminee();
                    return drone;
            	case "ROUES":
                    Robot roues = new Roues(carte.getCase(lig, col));
                    if (s != null) {
                        vitesse = Integer.parseInt(s);
                        roues.setVitesse(vitesse);
                    };
                    verifieLigneTerminee();
                    return roues;
            	case "PATTES":
                    Robot pattes = new Pattes(carte.getCase(lig, col));
                    if (s != null) {
                        vitesse = Integer.parseInt(s);
                        pattes.setVitesse(vitesse);
                    };
                    verifieLigneTerminee();
                    return pattes;
            	case "CHENILLES":
                    Robot che = new Chenilles(carte.getCase(lig, col));
                    if (s != null) {
                        vitesse = Integer.parseInt(s);
                        che.setVitesse(vitesse);
                    };
                    verifieLigneTerminee();
                    return che;
            }
            return null;
        } catch (NoSuchElementException e) {
            throw new DataFormatException("format de robot invalide. "
                    + "Attendu: ligne colonne type [valeur_specifique]");
        }
    }


    /** Ignore toute (fin de) ligne commencant par '#' */
    private void ignorerCommentaires() {
        while(scanner.hasNext("#.*")) {
            scanner.nextLine();
        }
    }

    /**
     * Verifie qu'il n'y a plus rien a lire sur cette ligne (int ou float).
     * @throws ExceptionFormatDonnees
     */
    private void verifieLigneTerminee() throws DataFormatException {
        if (scanner.findInLine("(\\d+)") != null) {
            throw new DataFormatException("format invalide, donnees en trop.");
        }
    }
}
