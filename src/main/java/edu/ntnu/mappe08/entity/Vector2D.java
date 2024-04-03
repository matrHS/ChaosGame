package edu.ntnu.mappe08.entity;

/**
 * Represents a 2 Dimentional Vector with position x and y
 */
public class Vector2D {
  private double x0;
  private double x1;

  /**
   * Constructor for Vector2D
   * @param x0 first value of the vector
   * @param x1 second value of the vector
   * @throws ArithmeticException if x or y is NaN
   */
  public Vector2D(double x0, double x1){
    if (Double.isNaN(x0) || Double.isNaN(x1)) {
      throw new ArithmeticException("Vector cannot contain NaN values");
    }
    this.setX0(x0);
    this.setX1(x1);
  }

  /**
   * Returns the x value of the vector
   * @return the x value of the vector
   */
  public double getX0(){
    return this.x0;
  }
  
  /**
   * Returns the y value of the vector
   * @return the y value of the vector
   */
  public double getX1(){
    return this.x1;
  }

  /**
   * Sets the x value of the vector
   * @param x0 New x value of the vector
   */
  private void setX0(double x0) {
    this.x0 = x0;
  }
  
  /**
   * Sets the y value of the vector
   * @param x1 New y value of the vector
   */
  private void setX1(double x1) {
    this.x1 = x1;
  }
  
  /**
   * Returns new vector added to original vector
   * @param other Vector to add to the original
   * @return New vector
   */
  public Vector2D add(Vector2D other){
    if (other == null) {
      throw new NullPointerException("Passed vector is null");
    }
    double newX0 = this.getX0() + other.getX0();
    double newX1 = this.getX1() + other.getX1();
    
    return new Vector2D(newX0, newX1);
  }

  /**
   * Returns new vector subtracted from original vector
   * @param other Vector to subtract from the original
   * @return New vector
   */
  public Vector2D subtract(Vector2D other){
    if (other == null) {
      throw new NullPointerException("Passed vector is null");
    }
    double newX0 = this.getX0() - other.getX0();
    double newX1 = this.getX1() - other.getX1();
    
    return new Complex(newX0, newX1);
  }
  
  /**
   * Returns new vector multiplied by scalar.
   * 
   * @param scalar Scalar to multiply the vector with
   * @return New vector
   */
  public Vector2D multiply(double scalar){
    double newX0 = this.getX0() * scalar;
    double newX1 = this.getX1() * scalar;
    
    return new Vector2D(newX0, newX1);
  }
}
