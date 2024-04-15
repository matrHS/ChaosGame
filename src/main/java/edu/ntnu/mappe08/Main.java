package edu.ntnu.mappe08;


import edu.ntnu.mappe08.entity.Matrix2x2;
import edu.ntnu.mappe08.entity.Vector2D;
import edu.ntnu.mappe08.gui.ChaosGameCLI;
import edu.ntnu.mappe08.gui.ChaosGameMainPage;
import edu.ntnu.mappe08.logic.ChaosCanvas;
import edu.ntnu.mappe08.logic.ChaosGame;
import edu.ntnu.mappe08.logic.ChaosGameDescription;
import edu.ntnu.mappe08.logic.ChaosGameFileHandler;


public class Main {
  
  public static void main(String[] args){
//    ChaosGameCLI app = new ChaosGameCLI();
//    app.start();
    ChaosGameMainPage app = new ChaosGameMainPage();
    app.main(args);
  }

}
