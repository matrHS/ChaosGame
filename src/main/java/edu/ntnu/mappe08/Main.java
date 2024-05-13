package edu.ntnu.mappe08;


import edu.ntnu.mappe08.ui.ChaosGameCli;
import edu.ntnu.mappe08.ui.MainPage;

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
//    ChaosGameCli app = new ChaosGameCli();
//    app.start();
    MainPage app = new MainPage();
    app.main(args);
  }

}
