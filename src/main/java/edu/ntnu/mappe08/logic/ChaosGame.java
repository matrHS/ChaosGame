package edu.ntnu.mappe08.logic;

import edu.ntnu.mappe08.entity.Complex;
import edu.ntnu.mappe08.entity.Vector2D;
import java.util.Random;


/**
 * Represents the chaos game.
 */
public class ChaosGame {

  private ChaosCanvas canvas;
  private ChaosGameDescription description;
  private Vector2D currentPoint;
  private Random random;


  /**
   * Constructor for the chaos game.
   *
   * @param description the description of the chaos game
   * @param width the width of the canvas
   * @param height the height of the canvas
   */
  public ChaosGame(ChaosGameDescription description, int width, int height) {
    random = new Random();
    this.description = description;
    this.canvas = new ChaosCanvas(width, height, description.getMinCoords(), description.getMaxCoords());
    this.currentPoint = new Complex(0, 0);
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
    int randomVal = random.nextInt(description.getTransforms().size());
    Vector2D newPoint = description.getTransforms().get(randomVal).transform(currentPoint);
    currentPoint = newPoint;
    
    canvas.putPixel(currentPoint);
  }

}
