package app.launcher;

import app.main.*;
import app.main.Map;

import java.io.*;
import java.util.*;

public class main {

    static Map map = new Map();

    Scanner input = new Scanner(System.in);

    public static void main(String[] args) throws IOException {

        String filename = null;
        filename = "src/resources/map.csv";
        map.init(filename);

        System.out.println(map);

        System.out.println(map.getMatrix());

        System.out.println(map.getNodeFromString("V,Lyon").getId());
        System.out.println(map.getMatrix().getLine(map.getNodeFromString("V,Lyon").getId()));

    }
}
