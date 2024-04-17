package edu.ntnu.mappe08.entity;

/**
 * Class for a 2x2 matrix.
 */
public class Matrix2x2 {

  private double a00;
  private double a01;
  private double a10;
  private double a11;

  /**
   * Constructor for a 2x2 matrix.
   *
   * @param a00 first value of the matrix
   * @param a01 second value of the matrix
   * @param a10 third value of the matrix
   * @param a11 fourth value of the matrix
   */
  public Matrix2x2(double a00, double a01, double a10, double a11) {
    setA00(a00);
    setA01(a01);
    setA10(a10);
    setA11(a11);
  }

  /**
   * Returns first value of the matrix.
   *
   * @return first value of the matrix
   */
  public double getA00() {
    return this.a00;
  }

  /**
   * Returns second value of the matrix.
   *
   * @return second value of the matrix
   */
  public double getA01() {
    return this.a01;
  }

  /**
   * Returns third value of the matrix.
   *
   * @return third value of the matrix
   */
  public double getA10() {
    return this.a10;
  }

  /**
   * Returns fourth value of the matrix.
   *
   * @return fourth value of the matrix
   */
  public double getA11() {
    return this.a11;
  }

  /**
   * Sets first value of the matrix.
   *
   * @param a00 New first value of the matrix
   */
  private void setA00(double a00) {
    this.a00 = a00;
  }
  
  /**
   * Sets second value of the matrix.
   *
   * @param a01 New second value of the matrix
   */
  private void setA01(double a01) {
    this.a01 = a01;
  }
  
  /**
   * Sets third value of the matrix.
   *
   * @param a10 New third value of the matrix
   */
  private void setA10(double a10) {
    this.a10 = a10;
  }
  
  /**
   * Sets fourth value of the matrix.
   *
   * @param a11 New fourth value of the matrix
   */
  private void setA11(double a11) {
    this.a11 = a11;
  }

  /**
   * Returns matrix multiplied by a vector.
   *
   * @param vector Vector to multiply with the matrix.
   * @return New vector.
   */
  public Vector2D multiply(Vector2D vector) {
    if (vector == null) {
      throw new NullPointerException("Passed vector is null");
    }

    double newX = this.a00 * vector.getX0() + this.a01 * vector.getX1();
    double newY = this.a10 * vector.getX0() + this.a11 * vector.getX1();

    return new Vector2D(newX, newY);
  }
}

