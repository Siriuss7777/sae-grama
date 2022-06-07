package app.launcher;

import app.main.gui.Window;
import app.main.map.Graph;
import app.main.nodes.LinkType;

import java.io.*;
import java.util.*;

public class main {

    static Graph graph = new Graph();

    Scanner input = new Scanner(System.in);

    public static void main(String[] args) throws IOException {

        String filename = null;
        filename = "src/resources/map.csv";
        graph.init(filename);
        System.out.println(graph);


        Window f1 = new Window(graph);
    }
}
