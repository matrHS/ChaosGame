package edu.ntnu.mappe08.entity;

/**
 * Represents a 2 Dimentional Vector with position x and y
 */
public class Vector2D {
  private double x;
  private double y;

  /**
   * Constructor for Vector2D
   * @param x first value of the vector
   * @param y second value of the vector
   * @throws ArithmeticException if x or y is NaN
   */
  public Vector2D(double x, double y){
    if (Double.isNaN(x) || Double.isNaN(y)) {
      throw new ArithmeticException("Vector cannot contain NaN values");
    }
    this.setX(x);
    this.setY(y);
  }

  /**
   * Method to get the x value of the vector
   * @return the x value of the vector
   */
  public double getX(){
    return this.x;
  }
  
  /**
   * Method to get the y value of the vector
   * @return the y value of the vector
   */
  public double getY(){
    return this.y;
  }

  /**
   * Method to set the x value of the vector
   * @param x New x value of the vector
   */
  private void setX(double x) {
    this.x = x;
  }
  
  /**
   * Method to set the y value of the vector
   * @param y New y value of the vector
   */
  private void setY(double y) {
    this.y = y;
  }
  
  /**
   * Adds vector to original vector
   * @param other Vector to add to the original
   * @return New vector
   */
  public Vector2D add(Vector2D other){
    if (other == null) {
      throw new NullPointerException("Passed vector is null");
    }
    double newX = this.getX() + other.getX();
    double newY = this.getY() + other.getY();
    
    return new Vector2D(newX, newY);
  }

  /**
   * Subtracts vector from original vector
   * @param other Vector to subtract from the original
   * @return New vector
   */
  public Vector2D subtract(Vector2D other){
    if (other == null) {
      throw new NullPointerException("Passed vector is null");
    }
    double newX = this.getX() - other.getX();
    double newY = this.getY() - other.getY();
    
    return new Vector2D(newX, newY);
  }
}
