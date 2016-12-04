package io;

import donnees.*;
import donnees.robot.*;
import java.io.*;
import java.util.*;
import java.util.zip.DataFormatException;




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
     * Cree et retourne un instance of DonneesSimulation en lisant un
     * fichier donnee
     * @param fichierDonnees nom du fichier à lire
     */
    public static DonneesSimulation creeDonnees(String fichierDonnees)
	throws FileNotFoundException, DataFormatException {
	LecteurDonnees lecteur = new LecteurDonnees(fichierDonnees);
	Carte carte = lecteur.creeCarte();
	List<Incendie> incendies = lecteur.creeIncendies(carte); 
	List<Robot> robots = lecteur.creeRobots(carte);
        return new DonneesSimulation(carte, incendies, robots);
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
        // une ExceptionFormat levee depuis lireCase est remontee telle quelle
    }
    
    /**
     * Cree une carte basé sur les donneés dans le fichier
     * @throws ExceptionFormatDonnees
     */
    private Carte creeCarte() throws DataFormatException {
	ignorerCommentaires();
	try {
	    int nbLignes = scanner.nextInt();
	    int nbColonnes = scanner.nextInt();
	    int tailleCases = scanner.nextInt();
	    
	    Carte carte = new Carte(nbLignes, nbColonnes, tailleCases);
	    for (int lig = 0; lig < nbLignes; lig++) {
		for (int col = 0; col < nbColonnes; col++) {
		    carte.setCase(lig, col, creeCase(lig, col));
		}
	    }
	    return carte;
	} catch (NoSuchElementException e) {
	    throw new DataFormatException("format de carte invalide. "
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
        //		NatureTerrain nature;

        try {
            chaineNature = scanner.next();
            // si NatureTerrain est un Enum, vous pouvez recuperer la valeur
            // de l'enum a partir d'une String avec:
            //			NatureTerrain nature = NatureTerrain.valueOf(chaineNature);

            verifieLigneTerminee();

            System.out.print("nature = " + chaineNature);

        } catch (NoSuchElementException e) {
            throw new DataFormatException("format de case invalide. "
                    + "Attendu: nature altitude [valeur_specifique]");
        }

        System.out.println();
    }
    
    /**
     * Cree case depuis donneés
     */
    private Case creeCase(int lig, int col) throws DataFormatException {
	ignorerCommentaires();
	try {
	    String chaineNature = scanner.next();
	    NatureTerrain nature = NatureTerrain.valueOf(chaineNature);
	    verifieLigneTerminee();
	    return new Case(lig, col, nature);
	} catch (NoSuchElementException e) {
	    throw new DataFormatException("format de case invalide. "
		    + "Attendu: nature altitude [valeur_specifique]");
	}
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
     * Initialiser des incendies
     */
    private List<Incendie> creeIncendies(Carte carte) throws DataFormatException {
	ignorerCommentaires();
	try {
	    int nbIncendies = scanner.nextInt();
	    List<Incendie> incendies = new LinkedList<Incendie>();

	    for (int i = 0; i < nbIncendies; i++) {
		incendies.add(creeIncendie(i, carte));
	    }
	    return incendies;
	} catch (NoSuchElementException e) {
	    throw new DataFormatException("format invalide. " 
					  + "Attendu: nbIncendies");
	}
    }
   
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
	    return new Incendie(carte.getCase(lig, col), intensite);
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
     * Cree des robots depuis donnees
     * @param carte sur laquelle les robots sont distribués 
     */
    private List<Robot> creeRobots(Carte carte) throws DataFormatException {
	ignorerCommentaires();
	try {
	    int nbRobots = scanner.nextInt();
	    List<Robot> robots = new LinkedList<Robot>();
	    
	    for (int i = 0; i < nbRobots; i++) {
		robots.add(creeRobot(i, carte));
	    }
	    return robots;
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

    /**
     * Cree un robot et le place dans la carte
     *
     */
    private Robot creeRobot(int i, Carte carte) throws DataFormatException {
	ignorerCommentaires();
	try {
	    int lig = scanner.nextInt();
	    int col = scanner.nextInt();
	    String chaineType = scanner.next();
	    TypeRobot type = TypeRobot.valueOf(chaineType);
	    Robot robot = null;
	    Case c = carte.getCase(lig, col);
	    String s = scanner.findInLine("\\d+");
	    
	    if (s == null) {
		switch (type) {
                    case DRONE:
                        robot = new Drone(c);
                        break;
                    case ROUES:
                        robot = new Roues(c);
                        break;
                    case CHENILLES:
                        robot = new Chenilles(c);
                        break;
                    case PATTES:
                        robot = new Pattes(c);
                        break;
                }
	    } else {
		int vitesse = Integer.parseInt(s);
		switch (type) {
                    case DRONE:
                        robot = new Drone(c, vitesse);
                        break;
                    case ROUES:
                        robot = new Roues(c, vitesse);
                        break;
                    case CHENILLES:
                        robot = new Chenilles(c, vitesse);
                        break;
                    case PATTES:
                        robot = new Pattes(c);
                        break;
                }
	    }
	    return robot;
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
