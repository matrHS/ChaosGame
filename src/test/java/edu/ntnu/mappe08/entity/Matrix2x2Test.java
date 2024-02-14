package edu.ntnu.mappe08.entity;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class Matrix2x2Test {

  private Vector2D testVector;
  private Matrix2x2 testMatrix;

  /**
   * Initializes the test values for each test
   */
  @BeforeEach
  public void initializeTestValues() {
    this.testMatrix = new Matrix2x2(1, 2, 3, 4);
    this.testVector = new Vector2D(1, 2);
  }


  /**
   * Positive test:
   * Test the constructor in the Matrix2x2 class.
   */
  @Test
  public void testValidMatrixConstructor() {
    Matrix2x2 testConstructorMatrix = new Matrix2x2(2, 3, 4, 5);
    assertEquals(2, testConstructorMatrix.getA00());
    assertEquals(3, testConstructorMatrix.getA01());
    assertEquals(4, testConstructorMatrix.getA10());
    assertEquals(5, testConstructorMatrix.getA11());
  }
  
  /**
   * Positive test:
   * Test the multiply method in the Matrix2x2 class.
   */
  @Test
  public void testValidMultiply() {
    Vector2D result = this.testMatrix.multiply(this.testVector);
    assertEquals(5, result.getX());
    assertEquals(11, result.getY());
  }
  
  /**
   * Negative test:
   * Test Matrix2x2 multiply with null vector.
   */
  @Test
  public void testNullVectorMultiply() {
    Vector2D testVector = null;
    assertThrows(NullPointerException.class, () -> {
      this.testMatrix.multiply(testVector);
    });
  }

}