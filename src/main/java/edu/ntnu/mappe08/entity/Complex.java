package edu.ntnu.mappe08.entity;

public class Complex extends Vector2D{
  
  /**
   * Constructor for a complex number.
   * 
   * @param realPart the real part of the complex number
   * @param imaginaryPart the imaginary part of the complex number
   */
  public Complex(double realPart, double imaginaryPart) {
    super(realPart, imaginaryPart);
  }

  /**
   * Method to get the real part of the complex number.
   * 
   * @return the real part of the complex number
   */
  public double getRealPart() {
    return super.getX();
  }
  
  /**
   * Method to get the imaginary part of the complex number.
   * 
   * @return the imaginary part of the complex number
   */
  public double getImaginaryPart() {
    return super.getY();
  }
  
  /**
   * Method to calculate the square root of a complex number.
   * 
   * @return the square root of the complex number
   */
  public Complex sqrt() {
    double realPart = getRealPart();
    double imaginaryPart = getImaginaryPart();
    double length = Math.sqrt(realPart * realPart + imaginaryPart * imaginaryPart);
    double r = Math.sqrt((length + realPart) / 2);
    double i = Math.sqrt((length - realPart) / 2);
    
    return new Complex(r, i);
  }
  
}
