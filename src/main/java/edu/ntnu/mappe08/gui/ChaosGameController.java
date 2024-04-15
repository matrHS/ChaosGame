package edu.ntnu.mappe08.gui;

import edu.ntnu.mappe08.logic.ChaosCanvas;
import edu.ntnu.mappe08.logic.ChaosGame;
import edu.ntnu.mappe08.logic.ChaosGameDescription;
import edu.ntnu.mappe08.logic.ChaosGameDescriptionFactory;
import edu.ntnu.mappe08.logic.ChaosGameFileHandler;
import java.util.List;

/**
 * Represents the controller for the Chaos Game.
 * Should be interchangeable with GUI and CLI.
 */
public class ChaosGameController {

  private ChaosGameMainPage chaosGameMainPage;
  private ChaosGameFileHandler fileHandler;
  private ChaosGameDescriptionFactory descriptionFactory;

  /**
   * Creates an instance of ChaosGameController.
   */
  public ChaosGameController(ChaosGameMainPage chaosGameMainPage) {
    this.chaosGameMainPage = chaosGameMainPage;
    this.fileHandler = new ChaosGameFileHandler();
    this.descriptionFactory = new ChaosGameDescriptionFactory();
  }

  /**
   * Creates an instance of ChaosGameController for use with CLI.
   */
  public ChaosGameController() {
    this.fileHandler = new ChaosGameFileHandler();
    this.descriptionFactory = new ChaosGameDescriptionFactory();
  }

  /**
   * Builds and simulates ChaosCanvas for any transformation type.
   * @param height height of canvas.
   * @param width width of canvas.
   * @param iterations number of iterations.
   * @param transformType type of transformation.
   * @return ChaosCanvas.
   */
  private ChaosCanvas getChaosCanvas(int height, int width, int iterations, String transformType) {
    // TODO: Improve parameterization
    ChaosGameDescription chaosGameDescription = this.descriptionFactory.createDescription(transformType);
    ChaosGame chaosGame = new ChaosGame(chaosGameDescription, height, width);
    chaosGame.runSteps(iterations);
    return chaosGame.getCanvas();
  }

  public ChaosCanvas getCustom(int height, int width, int iterations, String filePath) {
    List<String> strings = fileHandler.readFromFile(filePath);
    ChaosGame chaosGame = new ChaosGame(descriptionFactory.buildChaosGameDescription(strings), height, width);
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
    //TODO: Implement Variable path
    return getChaosCanvas(height, width, iterations,"Sierpinski");
  }

  /**
   * Returns a Barnsley ChaosCanvas.
   * @param height height of canvas.
   * @param width width of canvas.
   * @param iterations number of iterations.
   * @return ChaosCanvas.
   */
  public ChaosCanvas getBarnsley(int height, int width, int iterations) {
    //TODO: Implement Variable path
    return getChaosCanvas(height, width, iterations,"Barnsley");
  }

  /**
   * Returns a Julia ChaosCanvas.
   * 
   * @param height height of canvas.
   * @param width width of canvas.
   * @param iterations number of iterations.
   * @return ChaosCanvas.
   */
  public ChaosCanvas getJulia(int height, int width, int iterations) {
    //TODO: Implement Variable path
    return getChaosCanvas(height, width, iterations,"Julia");
  }
  
  
}
