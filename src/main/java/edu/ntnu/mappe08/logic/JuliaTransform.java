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
    
    Complex complexPoint = (Complex) point;
    double realPartResult = pointC.getRealPart() - complexPoint.getRealPart();
    double imaginaryPartResult = pointC.getImaginaryPart() - complexPoint.getImaginaryPart();
    
    Complex complexResult = new Complex(realPartResult, imaginaryPartResult);
    Complex complexSquare = complexResult.sqrt();
    
    Vector2D juliaResult = new Complex(complexSquare.getRealPart() * sign, 
        complexSquare.getImaginaryPart() * sign);
    
    return juliaResult;
  }
}
