package edu.ntnu.mappe08.logic;

import edu.ntnu.mappe08.entity.Vector2D;

/**
 * Represents a 2D transformation.
 */
public interface Transform2D {
  public Vector2D transform(Vector2D point);
}
