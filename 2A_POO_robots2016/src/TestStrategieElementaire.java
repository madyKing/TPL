import gui.GUISimulator;
import java.awt.Color;

//import java.io.FileNotFoundException;
//import java.util.zip.DataFormatException;
import donnees.*;
import donnees.robot.Robot;
import simu.*;
//import src.donnees.*;


public class TestStrategieElementaire {
    public static void main(String[] args) {
        // crée la fenêtre graphique dans laquelle dessiner
        GUISimulator gui = new GUISimulator(800, 600, Color.BLACK);
        // crée l'invader, en l'associant à la fenêtre graphique précédente
        Simulateur simms = new Simulateur(gui, args[0]);


        ChefPompier strat = new StrategieElementaire(simms);

        simms.initSimulateur(strat);
    }
}
