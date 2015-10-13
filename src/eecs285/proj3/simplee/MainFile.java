package eecs285.proj3.simplee;

import eecs285.proj3.simplee.FedexSimulatorGUI;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Colin on 10/19/2014.
 * <p/>
 * This file is the main file that starts the GUI.
 */

public class MainFile
{
  public static FedexSimulatorGUI win;

  public static void main( String[] args )
  {
    win = new FedexSimulatorGUI();
    win.setResizable(false);
    win.pack();
    win.setVisible(true);
    win.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
  }
}
