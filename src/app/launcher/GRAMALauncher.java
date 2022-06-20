package app.launcher;

import app.main.gui.Window;
import app.main.map.Graph;

import java.io.*;
import java.util.*;

public class GRAMALauncher {

    static Graph graph = new Graph();

    static Scanner input = new Scanner(System.in);

    public static void main(String[] args) throws IOException {

        String filename = null;
        // Read the file name from the user
        System.out.println("          ____    _    _____       ____ ____      _    __  __    _\n" +
                "         / ___|  / \\  | ____|     / ___|  _ \\    / \\  |  \\/  |  / \\\n" +
                "         \\___ \\ / _ \\ |  _| _____| |  _| |_) |  / _ \\ | |\\/| | / _ \\\n" +
                "          ___) / ___ \\| |__|_____| |_| |  _ <  / ___ \\| |  | |/ ___ \\\n" +
                "         |____/_/   \\_\\_____|     \\____|_| \\_\\/_/   \\_\\_|  |_/_/   \\_\\");
        System.out.println("Bienvenue dans GRAMA !");
        System.out.println("Ce système de chargement de fichier est temporaire.");
        System.out.println("Pour ouvrir le graphe que nous avons créé, saisissez \"map\". Pour la map de test, saisissez \"test\".");
        System.out.println("Entrez le nom du fichier à ouvrir : ");
        filename = input.nextLine();
        if(filename.toLowerCase(Locale.ROOT).equals("map")) {
            filename = "src/resources/map.csv";
        }
        else if(filename.toLowerCase(Locale.ROOT).equals("test")) {
            filename = "src/resources/test.csv";
        }

        // Start a timer for the map loading
        long startTime = System.currentTimeMillis();

        System.out.println("Chargement du fichier " + filename + "...");

        graph.init(filename);
        Window f1 = new Window(graph);

        long endTime = System.currentTimeMillis();

        System.out.println("Fichier chargé ! ("+ (endTime - startTime) + "ms)");
    }
}
