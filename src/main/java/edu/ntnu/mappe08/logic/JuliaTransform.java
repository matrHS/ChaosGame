package edu.ntnu.mappe08.logic;

import edu.ntnu.mappe08.entity.Complex;
import edu.ntnu.mappe08.entity.Vector2D;

/**
 * Represents a 2D transformation using the Julia transformation.
 */
public class JuliaTransform implements Transform2D {
  private Complex pointC;
  private int sign;

  /**
   * Constructor for a Julia transformation.
   * 
   * @param point Complex number to use for transformation.
   * @param sign +1 or -1 for negative or positive transformation.
   */
  public JuliaTransform(Complex point, int sign) {
    this.pointC = point;
    this.sign = sign;
  }

  /**
   * Gets the point C of the Julia transformation.
   * 
   * @return The point C of the Julia transformation.
   */
  public Complex getPointC() {
    return this.pointC;
  }
  
  /**
   * Constructor for Julia transform.
   * Defaults sign to +1.
   * 
   * @param point Complex number to use for transformation.
   */
  public JuliaTransform(Complex point) {
    this(point,1);
  }

  /**
   * Transforms a 2D vector using the Julia transformation.
   * 
   * @param point Complex number to use for transformation.
   * @return The transformed complex number.
   */
  @Override
  public Vector2D transform(Vector2D point) {
    if (point == null) {
      throw new NullPointerException("Passed vector is null");
    }
    
    if (!(point instanceof Complex)) {
      throw new IllegalArgumentException("Passed vector is not a complex number");
    }

    Complex complexPoint = new Complex(point);
    
    
    Complex complexResult = new Complex(complexPoint.subtract(this.pointC));
    Complex complexSquare = complexResult.sqrt();

    Complex juliaResult = new Complex(complexSquare.multiply(this.sign));
    
    
    return juliaResult;
  }
}
