package edu.ntnu.mappe08.Entity;

/**
 * Class for a 2x2 matrix
 */
public class Matrix2x2 {

  private double a00;
  private double a01;
  private double a10;
  private double a11;

  /**
   * Constructor for a 2x2 matrix
   * @param a00 first value of the matrix
   * @param a01 second value of the matrix
   * @param a10 third value of the matrix
   * @param a11 fourth value of the matrix
   */
  public Matrix2x2(double a00, double a01, double a10, double a11) {
    this.a00 = a00;
    this.a01 = a01;
    this.a10 = a10;
    this.a11 = a11;
  }

  /**
   * Method to get the first value of the matrix
   * @return the first value of the matrix
   */
  public double getA00() {
    return this.a00;
  }

  /**
   * Method to get the second value of the matrix
   * @return the second value of the matrix
   */
  public double getA01() {
    return this.a01;
  }

  /**
   * Method to get the third value of the matrix
   * @return the third value of the matrix
   */
  public double getA10() {
    return this.a10;
  }

  /**
   * Method to get the fourth value of the matrix
   * @return the fourth value of the matrix
   */
  public double getA11() {
    return this.a11;
  }


  /**
   * Method to multiply the matrix with a vector.
   * @param vector Vector to multiply with the matrix.
   * @return New vector.
   */
  public Vector2D multiply(Vector2D vector) {
    if (vector == null) {
      throw new NullPointerException("Passed vector is null");
    }

    double x = this.a00 * vector.getX() + this.a01 * vector.getY();
    double y = this.a10 * vector.getX() + this.a11 * vector.getY();

    return new Vector2D(x, y);
  }
}

