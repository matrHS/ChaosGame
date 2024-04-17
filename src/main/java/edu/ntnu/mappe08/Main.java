package edu.ntnu.mappe08;


import edu.ntnu.mappe08.gui.ChaosGameMainPage;

/**
 * Main class for the application. 
 * Used to launch the application
 */
public class Main {
  /**
   * Main method for the application.
   * Used to start the application with either CLI or GUI.
   *
   * @param args Arguments
   */
  public static void main(String[] args) {
//    ChaosGameCLI app = new ChaosGameCLI();
//    app.start();
    ChaosGameMainPage app = new ChaosGameMainPage();
    app.main(args);
  }

}
