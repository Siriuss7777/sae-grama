package app.launcher;

import app.main.gui.Window;
import app.main.map.Map;
import app.main.nodes.LinkType;

import java.io.*;
import java.util.*;

public class main {

    static Map map = new Map();

    Scanner input = new Scanner(System.in);

    public static void main(String[] args) throws IOException {

        String filename = null;
        filename = "src/resources/map.csv";
        map.init(filename);


        Window f1 = new Window();
    }
}
