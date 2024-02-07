package edu.ntnu.mappe08.Entity;

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
   */
  public Vector2D(double x, double y){
    this.x = x;
    this.y = y;
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
   * Adds vector to original vector
   * @param other Vector to add to the original
   * @return New vector
   */
  public Vector2D add(Vector2D other){
    if (other == null) {
      throw new NullPointerException("Passed vector is null");
    }

    return new Vector2D(this.getX() + other.getX(), this.getY() + other.getY());
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

    return new Vector2D(this.getX() - other.getX(), this.getY() - other.getY());
  }
}
