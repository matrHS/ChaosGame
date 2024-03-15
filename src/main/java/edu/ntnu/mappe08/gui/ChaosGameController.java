package edu.ntnu.mappe08.gui;

import edu.ntnu.mappe08.logic.ChaosCanvas;
import edu.ntnu.mappe08.logic.ChaosGame;
import edu.ntnu.mappe08.logic.ChaosGameDescription;
import edu.ntnu.mappe08.logic.ChaosGameFileHandler;

public class ChaosGameController {

  private ChaosGameFileHandler fileHandler;
  
  public ChaosGameController() {
    this.fileHandler = new ChaosGameFileHandler();
  }

  private ChaosCanvas getChaosCanvas(int height, int width, int iterations, String path) {
    ChaosGameDescription chaosGameDescription = fileHandler.buildChaosGameDescriptionFromFile(path);
    ChaosGame chaosGame = new ChaosGame(chaosGameDescription, height, width);
    chaosGame.runSteps(iterations);
    return chaosGame.getCanvas();
  }


  public ChaosCanvas getSierpinski(int height, int width, int iterations) {
    return getChaosCanvas(height, width, iterations,"data/testAffine.csv");
  }


  public ChaosCanvas getBarnsley(int height, int width, int iterations) {
    return getChaosCanvas(height, width, iterations,"data/testBarnsley.csv");
  }
  
}
