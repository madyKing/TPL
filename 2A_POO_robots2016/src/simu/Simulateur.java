package simu;

import configs.Taille;
import io.LecteurDonnees;
import donnees.*;
import donnees.robot.*;
import java.awt.Color;


import gui.GUISimulator;
import gui.ImageElement;
import gui.Rectangle;
import gui.Simulable;
import gui.Oval;
import gui.Text;

import java.io.FileNotFoundException;
import java.util.*;
import java.util.zip.DataFormatException;


public class Simulateur implements Simulable {
    /**Interface graphique*/
    private GUISimulator gui;
    private String fichierDonnees;
    private DonneesSimulation data;
    private DonneesSimulation dataPourGarder;
    private List<Robot> robots;
    private List<Incendie> incendies;
    private int largeurCase;
    private int longueurCase;
    private long dateSimulation = 0;
    private LinkedList<Evenement> listeEvenements = new LinkedList<Evenement>();
    private LinkedList<Evenement> listeEvenementsPourGarder = new LinkedList<Evenement>();
    private ChefPompier mrBobLeChef = null;
    /**
     *Constructeur d'un simulateur
     *associe le simulateur à la Gui, lit les données à partir du fichier de donnees et affiche la carte ,les robots et les incendies
     *
     *@param GUISimulator,String
     */

    public Simulateur(GUISimulator gui, String fichier) {
	this.gui = gui;
	gui.setSimulable(this);				// association a la gui!
	this.fichierDonnees = fichier;
        lireData(fichierDonnees);
        robots = this.data.getRobots();
        incendies = this.data.getIncendies();

	largeurCase = Taille.WINDOW_WIDTH / data.getCarte().getNbColonnes();
	longueurCase = Taille.WINDOW_HEIGHT / data.getCarte().getNbLignes();

    update();
        update();
        update();
        update();
        update();
    }


    public long getDateSimulation() {
	return this.dateSimulation;
    }

    public DonneesSimulation getData() {
	return this.data;
    }
    /**
     *Initialisation du simulateur
     *Dans le cas ou on adopte une stratégie donnée que ce soit Elementaire ou Evoluée cette methode affecte un chef pompier au simulateur
     *@param ChefPompier le chef Pompier lié à ce simulateur
     */
    public void initSimulateur(ChefPompier leChef){
	this.mrBobLeChef = leChef;
    }

    /**
     *Lecture des données
     *lit les données à partir des fichiers et les insère dans data de type DonneesSimulation , lève une exception si le fichier est introuvable ou invalide
     *@param String fichier: chemin du fichier de donnees (.map)
     */
    private void lireData(String fichier) {
        try {
	    this.data = LecteurDonnees.creeDonnees(fichier); //lecture des donnees
            this.dataPourGarder = LecteurDonnees.creeDonnees(fichier);
	} catch (FileNotFoundException e) {
	    System.out.println("fichier " + fichier + " inconnu ou illisible");
	} catch (DataFormatException e) {
	    System.out.println("\n\t**format du fichier " + fichier + " invalide: " + e.getMessage());
	}
    }
    /**
     *Methode DessineCarte
     *dessine toutes les cases de la carte
     */
    public void dessineCarte(){
	for(int i=0;i<data.getCarte().getNbLignes();i++) {
	    for(int j=0;j<data.getCarte().getNbColonnes();j++) {
		dessineCase(data.getCarte().getCase(i,j));
	    }
	}
    }
    /**
     *Methode dessineIncendies
     *dessine les incendies dans la zone de dessin
     */
    public void dessineIncendies(){
	for(Incendie inc : incendies){
	    if(inc.getIntensite()>0){
		gui.addGraphicalElement(new ImageElement(inc.getCase().getColonne()*largeurCase, inc.getCase().getLigne()*longueurCase, "cartes/incendie.jpg", longueurCase, longueurCase, null));
		gui.addGraphicalElement(new Text(inc.getCase().getColonne()*largeurCase + largeurCase/2, inc.getCase().getLigne()*longueurCase + longueurCase/2, Color.BLUE, "" + inc.getIntensite()));
	    }
        }
    }
    /**
     * Afficher la date simulation
     */
    public void dessineDateSimulation(){
        gui.addGraphicalElement(new Rectangle(700,300,Color.decode("0x000000"),Color.decode("0x303030"),200,600));
        gui.addGraphicalElement(new Text(700,50, Color.red,"Date Simulation (sec):"));
        gui.addGraphicalElement(new Text(700,100, Color.red,""+ this.dateSimulation));
    }
    /**
     *Methode dessineRobots
     *dessine les robots dans la zone de dessin
     */
    public void dessineRobots(){
	for (Robot rob: robots){
	    switch(rob.getClass().getSimpleName()) {
	    case "Chenilles":
                gui.addGraphicalElement(new ImageElement(rob.getPosition().getColonne()*largeurCase, rob.getPosition().getLigne()*longueurCase, "cartes/chenilles.jpg", longueurCase, longueurCase, null));
		gui.addGraphicalElement(new Text(rob.getPosition().getColonne()*largeurCase + largeurCase/2, rob.getPosition().getLigne()*longueurCase + longueurCase/2, Color.red, "" + rob.getVolumeEau()));

                break;
	    case "Drone":
                gui.addGraphicalElement(new ImageElement(rob.getPosition().getColonne()*largeurCase, rob.getPosition().getLigne()*longueurCase, "cartes/drone.jpg", longueurCase, longueurCase, null));
		gui.addGraphicalElement(new Text(rob.getPosition().getColonne()*largeurCase + largeurCase/2, rob.getPosition().getLigne()*longueurCase + longueurCase/2, Color.red, "" + rob.getVolumeEau()));

                break;
	    case "Pattes":
                gui.addGraphicalElement(new ImageElement(rob.getPosition().getColonne()*largeurCase, rob.getPosition().getLigne()*longueurCase, "cartes/pattes.jpg", longueurCase, longueurCase, null));
		gui.addGraphicalElement(new Text(rob.getPosition().getColonne()*largeurCase + largeurCase/2, rob.getPosition().getLigne()*longueurCase + longueurCase/2, Color.red, "INFINI"));

                break;
	    case "Roues":
                gui.addGraphicalElement(new ImageElement(rob.getPosition().getColonne()*largeurCase, rob.getPosition().getLigne()*longueurCase, "cartes/roues.jpg", longueurCase, longueurCase, null));
		gui.addGraphicalElement(new Text(rob.getPosition().getColonne()*largeurCase + largeurCase/2, rob.getPosition().getLigne()*longueurCase + longueurCase/2, Color.red, "" + rob.getVolumeEau()));

                break;
	    }
	}
    }
    /**
     *Methode dessineCase
     *dessine une case dans la zone de dessin selon sa nature : TERRAIN_LIBRE,EAU,FORET,ROCHE ou HABITAT
     */
    public void dessineCase(Case caseADessiner){
        switch (caseADessiner.getNature()){

        case EAU: // bleu
            gui.addGraphicalElement(new ImageElement(largeurCase*caseADessiner.getColonne(), longueurCase*caseADessiner.getLigne(), "cartes/eau.jpg", longueurCase, longueurCase, null));
            break;

        case FORET: //vert
            gui.addGraphicalElement(new ImageElement(largeurCase*caseADessiner.getColonne(), longueurCase*caseADessiner.getLigne(), "cartes/terrain.jpg", longueurCase, longueurCase, null));
            gui.addGraphicalElement(new ImageElement(largeurCase*caseADessiner.getColonne(), longueurCase*caseADessiner.getLigne(), "cartes/foret.jpg", longueurCase, longueurCase, null));
            break;

        case ROCHE: //gris
            gui.addGraphicalElement(new ImageElement(largeurCase*caseADessiner.getColonne(), longueurCase*caseADessiner.getLigne(), "cartes/roche1.jpg", longueurCase, longueurCase, null));
            gui.addGraphicalElement(new ImageElement(largeurCase*caseADessiner.getColonne(), longueurCase*caseADessiner.getLigne(), "cartes/roche2.jpg", longueurCase, longueurCase, null));
            break;

        case HABITAT: //marron
            gui.addGraphicalElement(new ImageElement(largeurCase*caseADessiner.getColonne(), longueurCase*caseADessiner.getLigne(), "cartes/roche1.jpg", longueurCase, longueurCase, null));
            gui.addGraphicalElement(new ImageElement(largeurCase*caseADessiner.getColonne(), longueurCase*caseADessiner.getLigne(), "cartes/habitat.jpg", longueurCase, longueurCase, null));
            break;

        case TERRAIN_LIBRE: //jaune
            gui.addGraphicalElement(new ImageElement(largeurCase*caseADessiner.getColonne(), longueurCase*caseADessiner.getLigne(), "cartes/terrain.jpg", longueurCase, longueurCase, null));

            break;
	}
    }

    public void ajouteEvenement(Evenement e) {
        this.listeEvenements.add(e);
        if (!this.listeEvenementsPourGarder.contains(e)) {
            this.listeEvenementsPourGarder.add(e);
        }
    }

    /**
     *methode incrementeDate incremente la valeur de dateSimulation par le pas de simulation et exécute tout les evenements presents dans la listeEvenements dont la date d'exécution est passée
     */
    private void incrementeDate() {
        this.dateSimulation+=Taille.SIMULATION_STEP;
        Evenement e;
        LinkedList<Evenement> toRemove = new LinkedList<Evenement>();

        for (int i = 0; i < this.listeEvenements.size(); i++) {
            e = this.listeEvenements.get(i);
            if (e.getDate() <= this.dateSimulation) {
                e.execute();
                update();
                toRemove.add(e);
            }
        }

        for (Evenement eve: toRemove) {
            this.listeEvenements.remove(eve);
        }

    }

    /**
     *verifie si la simulation est terminée ou pas
     *@return true si la simulation est terminée
     */
    private boolean simulationTerminee() {
        Iterator<Evenement> it = this.listeEvenements.iterator();
        while (it.hasNext()) {
            Evenement e = it.next();
            if (e.getDate() <= this.dateSimulation) {
                return false;
            }
        }
        return true;
    }

    /**
     *Méthode update dessine la carte avec les incendies et les robots
     *
     */
    private void update() {
        gui.reset();
        dessineCarte();
        dessineIncendies();
        dessineRobots();
        dessineDateSimulation();
        
        }

    /**
     *methode next invoqué lors d'un click sur suivant sur l'interface graphique ou chaque pas de simulation
     *appelle la methode inceremente date et deploier du chef
     */
    @Override
    public void next() {
        incrementeDate();
        if(mrBobLeChef != null)
	    mrBobLeChef.deploier();
    }

    /**
     * Rendre la simulation à l'état initial
     */
    @Override
    public void restart() {
        this.dateSimulation = 0;
        List<Robot> robotsGarder = this.dataPourGarder.getRobots();

        for (int i = 0; i < this.robots.size(); i++) {
            this.robots.get(i).setPosition(robotsGarder.get(i).getPosition());
            this.robots.get(i).setVolumeEau((robotsGarder.get(i).getVolumeEau()));
        }

        List<Incendie> incendiesGarder = this.dataPourGarder.getIncendies();

        for (int i = 0; i < this.incendies.size(); i++) {
            this.incendies.get(i).setPosition(incendiesGarder.get(i).getCase());
            this.incendies.get(i).setIntensite((incendiesGarder.get(i).getIntensite()));
        }

        this.listeEvenements.clear();
        System.out.println(listeEvenements);
        for (Evenement e: this.listeEvenementsPourGarder) {
            this.listeEvenements.add(e);
        }

        System.out.println(listeEvenements);
        update();
    }

    /**
     * Ajouter le déplacement d'un robot dans la liste d'événement
     * @param robotADeplacer
     * @param directionDeplacement
     */
    public void ajouteDeplacement(Robot robotADeplacer,Direction directionDeplacement){
	long dateCourante = dateSimulation;
	Case caseCourante = robotADeplacer.getPosition();
	Case caseSuivante = data.getCarte().getVoisin(caseCourante,directionDeplacement); // IF A RAJOUTER

	Iterator<Evenement> itElements = this.listeEvenements.iterator();
	while (itElements.hasNext()) {
	    Evenement e = itElements.next();
	    if (e.getRobot() == robotADeplacer) { // get robot a rajouter
		dateCourante = e.getDate();
	    }
	}

	dateCourante += robotADeplacer.tempsDeplacement(caseCourante,caseSuivante,data.getCarte());
	System.out.println(dateCourante);
	ajouteEvenement(new EvenementDeplacerSurDirection(dateCourante,robotADeplacer,directionDeplacement,data.getCarte()));
    }

    /**
     * Ajouter le déplacement d'un robot sur la 'caseSuivante'
     * dans la liste d'événement
     * @param robotADeplacer
     * @param caseSuivante
     */
    public void ajouteDeplacementCase(Robot robotADeplacer,Case caseSuivante){
	long dateCourante = dateSimulation;
	Case caseCourante = robotADeplacer.getPosition();

	Iterator<Evenement> itElements = this.listeEvenements.iterator();
	while (itElements.hasNext()) {
	    Evenement e = itElements.next();
	    if (e.getRobot() == robotADeplacer) { // get robot a rajouter
		dateCourante = e.getDate();
	    }
	}
	dateCourante += robotADeplacer.tempsDeplacement(caseCourante,caseSuivante,data.getCarte());
	/*System.out.println("Robot: " + robotADeplacer.getClass().getSimpleName());

	System.out.println("Date a deplacer: " + dateCourante);
    */
	ajouteEvenement(new EvenementDeplacerElementaire(dateCourante,robotADeplacer,caseSuivante));
    }
    /**
     * Ajouter le déversement d'un robot sur le 'incendie' dans la liste
     * d'événement
     * @param robotDeverseur
     * @param volumeADerverser
     * @param incendieAEteindre
     */
    public void ajouteDeversement(Robot robotDeverseur,int volumeADerverser,Incendie incendieAEteindre){
	long dateCourante = dateSimulation;

	Iterator<Evenement> itElements = this.listeEvenements.iterator();
	while (itElements.hasNext()) {
	    Evenement e = itElements.next();
	    if (e.getRobot() == robotDeverseur) { //get robot a rajouter
		dateCourante = e.getDate();
	    }
	}

	dateCourante += robotDeverseur.tempsDeversement(volumeADerverser);
    /*
    System.out.println("Robot: " + robotDeverseur.getClass().getSimpleName());

	System.out.println("Date a deverser: " + dateCourante);
    */
	ajouteEvenement(new EvenementDeverser(dateCourante,robotDeverseur,data.getCarte(),incendieAEteindre,volumeADerverser));
    }

    /**
     * Ajouter le remplissage d'un robot sur la liste d'événement
     * @param robotARemplir
     * @param caseEau
     */
    public void ajouteRemplissage(Robot robotARemplir,Case caseEau){
	long dateCourante = dateSimulation;
	Iterator<Evenement> itElements = this.listeEvenements.iterator();
	while (itElements.hasNext()) {
	    Evenement e = itElements.next();
	    if (e.getRobot() == robotARemplir) { //get robot a rajouter
		dateCourante = e.getDate();
	    }
	}
	dateCourante += robotARemplir.tempsRemplissage();
	/*System.out.println("Robot: " + robotARemplir.getClass().getSimpleName());

	System.out.println("Date a remplir: " + dateCourante);
    */
	ajouteEvenement(new EvenementRemplir(dateCourante,robotARemplir,data.getCarte(),caseEau));
    }

    /**
     * Ajouter l'événement libérer un robot après il finit une tache
     * @param robotALiberer
     */
    public void ajouteLibererRobot(Robot robotALiberer){
	long dateCourante = dateSimulation;
	Iterator<Evenement> itElements = this.listeEvenements.iterator();
	while (itElements.hasNext()) {
	    Evenement e = itElements.next();
	    if (e.getRobot() == robotALiberer) { //get robot a rajouter
		dateCourante = e.getDate();
	    }
	}
    /*
	System.out.println("Robot: " + robotALiberer.getClass().getSimpleName());

	System.out.println("Date a Liberer: " + dateCourante);
    */

	ajouteEvenement(new EvenementLibererRobot(dateCourante,robotALiberer));
    }

}
