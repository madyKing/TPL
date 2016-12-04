import gui.GUISimulator;
import java.awt.Color;

//import java.io.FileNotFoundException;
//import java.util.zip.DataFormatException;
import donnees.*;
import donnees.robot.Robot;
import simu.*;
//import src.donnees.*;


public class TestScenario1 {
    public static void main(String[] args) {
        // crée la fenêtre graphique dans laquelle dessiner
        GUISimulator gui = new GUISimulator(800, 600, Color.BLACK);
        // crée l'invader, en l'associant à la fenêtre graphique précédente

          Simulateur simms = new Simulateur(gui, args[0]);
          Carte car = simms.getData().getCarte();
          Robot rob = simms.getData().getRobot(1);
          simms.ajouteDeplacement(rob,Direction.NORD);
          simms.ajouteDeversement(rob,5000,simms.getData().getIncendie(4));
          simms.ajouteDeplacement(rob,Direction.OUEST);
          simms.ajouteDeplacement(rob,Direction.OUEST);
          simms.ajouteRemplissage(rob,car.getVoisin(rob.getPosition(),Direction.OUEST));
          simms.ajouteDeplacement(rob,Direction.EST);
          simms.ajouteDeplacement(rob,Direction.EST);
          simms.ajouteDeversement(rob,3000,simms.getData().getIncendie(4));
          simms.ajouteDeplacement(rob,Direction.SUD);


    }
}
