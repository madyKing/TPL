import gui.GUISimulator;
import java.awt.Color;

//import java.io.FileNotFoundException;
//import java.util.zip.DataFormatException;
import donnees.*;
import donnees.robot.Robot;
import simu.*;
//import src.donnees.*;


public class TestScenario0 {
    public static void main(String[] args) {
        // crée la fenêtre graphique dans laquelle dessiner
        GUISimulator gui = new GUISimulator(800, 600, Color.BLACK);
        // crée l'invader, en l'associant à la fenêtre graphique précédente

          Simulateur simms = new Simulateur(gui, args[0]);

          Robot rob = simms.getData().getRobot(0);

          simms.ajouteDeplacement(rob,Direction.NORD);
          simms.ajouteDeplacement(rob,Direction.NORD);
          simms.ajouteDeplacement(rob,Direction.NORD);
          simms.ajouteDeplacement(rob,Direction.NORD);
    }
}
