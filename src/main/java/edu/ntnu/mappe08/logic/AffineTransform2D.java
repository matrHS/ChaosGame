package edu.ntnu.mappe08.logic;

import edu.ntnu.mappe08.entity.Matrix2x2;
import edu.ntnu.mappe08.entity.Vector2D;

/**
 * Class for a 2D affine transformation.
 */
public class AffineTransform2D implements Transform2D{

  private Matrix2x2 matrix;
  private Vector2D vector;

  /**
   * Constructor for a 2D affine transformation.
   *
   * @param matrix the matrix to use for the transformation
   * @param vector the vector to use for the transformation
   */
  public AffineTransform2D(Matrix2x2 matrix, Vector2D vector) {
    this.matrix = matrix;
    this.vector = vector;
  }

  /**
   * Method to transform a 2D vector.
   *
   * @param point the vector to transform
   * @return the transformed vector
   */
  @Override
  public Vector2D transform(Vector2D point) {

    if (point == null) {
      throw new NullPointerException("Passed vector is null");
    }

    Vector2D tempVector = this.matrix.multiply(point);
    Vector2D newVector = tempVector.add(this.vector);

    return newVector;
  }
}
