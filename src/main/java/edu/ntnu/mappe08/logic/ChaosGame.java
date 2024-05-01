package edu.ntnu.mappe08.logic;

import edu.ntnu.mappe08.entity.Complex;
import edu.ntnu.mappe08.entity.Vector2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * Represents the chaos game.
 */
public class ChaosGame implements ChaosGameObservable {

  private ChaosCanvas canvas;
  private ChaosGameDescription description;
  private Vector2D currentPoint;
  private Random random;
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

  /**
   * Reconfigures the chaos game with a new description and canvas size.
   *
   * @param description description of the chaos game
   * @param height height of the canvas
   * @param width width of the canvas
   */
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
   * Returns the current point of the chaos game.
   */
  public void setMinCoords(Vector2D minCoords) {
    if (minCoords == null) {
      throw new IllegalArgumentException("minCoords cannot be null");
    }
    this.description.setMinCoords(minCoords);
    this.notifyObservers();
  }
  
  /**
   * Returns the current point of the chaos game.
   */
  public void setMaxCoords(Vector2D maxCoords) {
    if (maxCoords == null) {
      throw new IllegalArgumentException("maxCoords cannot be null");
    }
    this.description.setMaxCoords(maxCoords);
    this.notifyObservers();
  }

  /**
   * Sets transforms for the chaos game description.
   *
   * @param transforms the transforms to set
   */
  public void setTransforms(List<Transform2D> transforms) {
    if (transforms == null) {
      throw new IllegalArgumentException("transforms cannot be null");
    }
    this.description.setTransforms(transforms);
    this.notifyObservers();
  }
  
  /**
   * Returns transform type of the chaos game description
   *
   * @return transform type of the chaos game description
   */
  public TransformTypes getTransformType() {
    return description.getTransformType();
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

  @Override
  public void addObserver(ChaosGameObserver observer) {
    if (observer == null) {
      throw new IllegalArgumentException("observer cannot be null");
    }
    listeners.add(observer);
  }

  @Override
  public void removeObserver(ChaosGameObserver observer) {
    if (observer == null) {
      throw new IllegalArgumentException("observer cannot be null");
    }
    listeners.remove(observer);
  }

  @Override
  public void notifyObservers() {
    for (ChaosGameObserver listener : listeners) {
      listener.update();
    }
  }
}
