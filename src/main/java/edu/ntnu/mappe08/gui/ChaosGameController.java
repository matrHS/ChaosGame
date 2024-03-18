package edu.ntnu.mappe08.gui;

import edu.ntnu.mappe08.logic.ChaosCanvas;
import edu.ntnu.mappe08.logic.ChaosGame;
import edu.ntnu.mappe08.logic.ChaosGameDescription;
import edu.ntnu.mappe08.logic.ChaosGameFileHandler;

/**
 * Represents the controller for the Chaos Game.
 * Should be interchangeable with GUI and CLI.
 */

public class ChaosGameController {

  private ChaosGameFileHandler fileHandler;

  /**
   * Creates an instance of ChaosGameController.
   */
  public ChaosGameController() {
    this.fileHandler = new ChaosGameFileHandler();
  }


  /**
   * Builds and simulates ChaosCanvas for any transformation type.
   * @param height height of canvas.
   * @param width width of canvas.
   * @param iterations number of iterations.
   * @param path path to file.
   * @return ChaosCanvas.
   */
  private ChaosCanvas getChaosCanvas(int height, int width, int iterations, String path) {
    ChaosGameDescription chaosGameDescription = fileHandler.buildChaosGameDescriptionFromFile(path);
    ChaosGame chaosGame = new ChaosGame(chaosGameDescription, height, width);
    chaosGame.runSteps(iterations);
    return chaosGame.getCanvas();
  }


  /**
   * Returns a Sierpinski ChaosCanvas.
   * @param height height of canvas.
   * @param width width of canvas.
   * @param iterations number of iterations.
   * @return ChaosCanvas.
   */
  public ChaosCanvas getSierpinski(int height, int width, int iterations) {
    return getChaosCanvas(height, width, iterations,"data/testAffine.csv");
  }

  /**
   * Returns a Barnsley ChaosCanvas.
   * @param height height of canvas.
   * @param width width of canvas.
   * @param iterations number of iterations.
   * @return ChaosCanvas.
   */
  public ChaosCanvas getBarnsley(int height, int width, int iterations) {
    return getChaosCanvas(height, width, iterations,"data/testBarnsley.csv");
  }

  public ChaosCanvas getJulia(int height, int width, int iterations) {

    return getChaosCanvas(height, width, iterations,"data/testJulia2.csv");
  }
}
