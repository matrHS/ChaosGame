package edu.ntnu.mappe08.logic;

import edu.ntnu.mappe08.entity.Vector2D;
import java.util.List;

/**
 * Represents the chaos game and all saved transforms.
 */
public class ChaosGameDescription {
  private Vector2D minCoords;
  private Vector2D maxCoords;
  private List<Transform2D> transforms;
  
  ChaosGameDescription(Vector2D minCoords, Vector2D maxCoords, List<Transform2D> transforms) {
    setMinCoords(minCoords);
    setMaxCoords(maxCoords);
    setTransforms(transforms);
  }

  /**
   * Returns minimum coordinates of the chaos game.
   *
   * @return the minimum coordinates of the chaos game
   */
  public Vector2D getMinCoords() {
    return this.minCoords;
  }
  
  /**
   * Sets minimum coords of the chaos game.
   *
   * @param minCoords the minimum coords of the chaos game
   */
  public void setMinCoords(Vector2D minCoords) {
    if (minCoords == null) {
      throw new IllegalArgumentException("minCoords cannot be null");
    }
    this.minCoords = minCoords;
  }
  
  /**
   * Returns maximum coordinates of the chaos game.
   *
   * @return the maximum coordinates of the chaos game
   */
  public Vector2D getMaxCoords() {
    return this.maxCoords;
  }
  
  /**
   * Sets maximum coords of the chaos game.
   *
   * @param maxCoords the maximum coords of the chaos game
   */
  public void setMaxCoords(Vector2D maxCoords) {
    if (maxCoords == null) {
      throw new IllegalArgumentException("maxCoords cannot be null");
    }
    this.maxCoords = maxCoords;
  }

  /**
   * Returns the type of transform used in the chaos game.
   *
   * @return type of transform used in the chaos game, null if no transform
   */
  public TransformTypes getTransformType() {
    TransformTypes transformType = null;
    if (getTransforms().getFirst() instanceof AffineTransform2D) {
      transformType = TransformTypes.AFFINE2D;
    } else if (getTransforms().getFirst() instanceof JuliaTransform) {
      transformType = TransformTypes.JULIA;
    }
    return transformType;
  }
  
  /**
   * Returns transforms of the chaos game.
   *
   * @return the transforms of the chaos game
   */
  public List<Transform2D> getTransforms() {
    return this.transforms;
  }
  
  /**
   * Sets transforms of the chaos game.
   *
   * @param transforms the transforms of the chaos game
   */ 
  public void setTransforms(List<Transform2D> transforms) {
    if (transforms == null) {
      throw new IllegalArgumentException("transforms cannot be null");
    }
    this.transforms = transforms;
  }
}
