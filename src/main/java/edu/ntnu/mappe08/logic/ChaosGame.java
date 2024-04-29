package edu.ntnu.mappe08.logic;

import edu.ntnu.mappe08.entity.Complex;
import edu.ntnu.mappe08.entity.Vector2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * Represents the chaos game.
 */
public class ChaosGame {

  private ChaosCanvas canvas;
  private ChaosGameDescription description;
  private Vector2D currentPoint;
  private Random random;
  private ChaosGameObserver notifier;
  private List<ChaosGameObserver> listeners = new ArrayList<>();


  /**
   * Constructor for the chaos game.
   *
   * @param description the description of the chaos game
   * @param width the width of the canvas
   * @param height the height of the canvas
   */
  public ChaosGame(ChaosGameDescription description, int height, int width) {
    if (description == null) {
      throw new IllegalArgumentException("description cannot be null");
    }
    random = new Random();
    this.description = description;
    this.canvas = new ChaosCanvas(height, 
        width, 
        description.getMinCoords(), 
        description.getMaxCoords());
    this.currentPoint = new Complex(0, 0);
    
  }
  
  public void reconfigureChaosGame(ChaosGameDescription description, int height, int width) {
    if (description == null) {
      throw new IllegalArgumentException("description cannot be null");
    }
    this.description = description;
    this.canvas = new ChaosCanvas(height, 
        width, 
        description.getMinCoords(), 
        description.getMaxCoords());
    this.currentPoint = new Complex(0, 0);
//    notifyListeners();
  }

  /**
   * Returns the description of the chaos game.
   *
   * @return the description of the chaos game
   */
  public ChaosGameDescription getDescription() {
    return description;
  }
  
  /**
   * Sets the description of the chaos game.
   *
   * @param description the description of the chaos game
   */
  public void setDescription(ChaosGameDescription description) {
    if (description == null) {
      throw new IllegalArgumentException("description cannot be null");
    }
    this.description = description;
    notifyListeners();
  }
  
  /**
   * Returns the canvas of the chaos game.
   *
   * @return the canvas of the chaos game
   */
  public ChaosCanvas getCanvas() {
    return this.canvas;
  }

  /**
   * Runs a number of steps in the chaos game.
   *
   * @param steps number of steps to run
   */
  public void runSteps(int steps) {
    if (steps < 0) {
      throw new IllegalArgumentException("steps cannot be negative");
    }
    for (int i = 0; i < steps; i++) {
      this.runStep();
    }
  }

  /**
   * Runs a single step in the chaos game.
   */
  private void runStep() {
    int randomVal = random.nextInt(description.getTransforms().size());
    Vector2D newPoint = description.getTransforms().get(randomVal).transform(currentPoint);
    currentPoint = newPoint;
    
    canvas.putPixel(currentPoint);
  }
  
  public void subscribe(ChaosGameObserver listener) {
    listeners.add(listener);
  }
  
  private void notifyListeners() {
    for (ChaosGameObserver listener : listeners) {
      listener.update();
    }
  }

}
