package edu.ntnu.mappe08.entity;

/**
 * Represents a complex number.
 */
public class Complex extends Vector2D {
  
  /**
   * Constructor for a complex number.
   *
   * @param realPart real part of the complex number
   * @param imaginaryPart imaginary part of the complex number
   * @throws ArithmeticException if realPart or imaginaryPart is NaN
   */
  public Complex(double realPart, double imaginaryPart) {
    super(realPart, imaginaryPart);
  }
  
  /**
   * Constructor for a complex number.
   *
   * @param vector vector to create the complex number from
   */
  public Complex(Vector2D vector) {
    super(vector.getX0(), vector.getX1());
  }

  /**
   * Returns the real part of the complex number.
   *
   * @return real part of the complex number
   */
  public double getRealPart() {
    return super.getX0();
  }
  
  /**
   * Returns the imaginary part of the complex number.
   *
   * @return imaginary part of the complex number
   */
  public double getImaginaryPart() {
    return super.getX1();
  }
  
  /**
   * Calculates and returns the square root of a complex number.
   *
   * @return square root of the complex number
   */
  public Complex sqrt() {
    double realPart = getRealPart();
    double imaginaryPart = getImaginaryPart();
    double length = Math.sqrt(realPart * realPart + imaginaryPart * imaginaryPart);
    double r = Math.sqrt((length + realPart) / 2);
    double imSign = Math.signum(imaginaryPart);
    // If the imaginary part is 0 we set it to 1 to avoid removing the imaginary part
    if (imSign == 0) {
      imSign = 1;
    }
    double i = imSign * Math.sqrt((length - realPart) / 2);
    
    return new Complex(r, i);
  }
  
}
