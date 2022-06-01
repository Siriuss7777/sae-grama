package app.launcher;

import app.main.gui.Window;
import app.main.map.Graph;

import java.io.*;
import java.util.*;

public class main {

    static Graph map = new Graph();

    Scanner input = new Scanner(System.in);

    public static void main(String[] args) throws IOException {

        String filename = null;
        filename = "src/resources/map.csv";
        map.init(filename);


        Window window = new Window(map);

    }
}