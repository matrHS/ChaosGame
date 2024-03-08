package edu.ntnu.mappe08.logic;

import edu.ntnu.mappe08.entity.Vector2D;


/**
 * Represents the chaos game.
 */
public class ChaosGame {

  private ChaosCanvas canvas;
  private ChaosGameDescription description;
  private Vector2D currentPoint;


  /**
   * Constructor for the chaos game.
   *
   * @param description the description of the chaos game
   * @param width the width of the canvas
   * @param height the height of the canvas
   */
  public ChaosGame(ChaosGameDescription description, int width, int height) {
    this.description = description;
    this.canvas = new ChaosCanvas(width, height, description.getMinCoords(), description.getMaxCoords());
    this.currentPoint = new Vector2D(0, 0);
  }

  /**
   * Method to get the canvas of the chaos game.
   *
   * @return the canvas of the chaos game
   */
  public ChaosCanvas getCanvas() {
    return this.canvas;
  }

  public void runSteps(int steps) {
    for (int i = 0; i < steps; i++) {
      this.runStep();
    }
  }

  private void runStep() {
    
  }

}
