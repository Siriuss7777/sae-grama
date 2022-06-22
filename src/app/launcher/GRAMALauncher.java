package app.launcher;

import app.main.gui.Window;
import app.main.map.Graph;
import com.formdev.flatlaf.FlatDarculaLaf;
import com.mxgraph.util.mxResources;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.*;

/**
 * @author Matéo Guenot, Bastien Le Gall
 */

public class GRAMALauncher {

    static {
        try {
            UIManager.setLookAndFeel(new FlatDarculaLaf());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {

        Window window = new Window();

    }
}
