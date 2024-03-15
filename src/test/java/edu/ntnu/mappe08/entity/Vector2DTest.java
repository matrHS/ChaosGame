package edu.ntnu.mappe08.entity;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * 
 */
class Vector2DTest {

  private Vector2D testVector;
  private Vector2D otherTestVector;

  /**
   * Initializes the test vectors
   */
  @BeforeEach
  public void intializeTestValues(){
    testVector = new Vector2D(5,2);
    otherTestVector = new Vector2D(1,3);
  }
  
  
  /**
   * Positive test:
   * Check to see if vector successfully gets constructed
   */
  @Test
  public void TestValidConstructorForVector2D(){
    Vector2D testConstctorVector = new Vector2D(5,2);
    double expectedX = 5;
    double expectedY = 2;
    
    assertEquals(expectedX,testConstctorVector.getX0());
    assertEquals(expectedY,testConstctorVector.getX1());
  }

  /**
   * Positive test:
   * Check if add function correctly adds the vectors
   */
  @Test
  public void TestValidAdd(){
    Vector2D vectorResult = testVector.add(otherTestVector);
    double expectedX = 6;
    double expectedY = 5;
    
    
    assertEquals(expectedX,vectorResult.getX0());
    assertEquals(expectedY,vectorResult.getX1());
  }

  /**
   * Positive test:
   * Check if subtraction function correctly subtracts the vectors
   */
  @Test
  public void TestValidSubtract(){
    Vector2D vectorResult = testVector.subtract(otherTestVector);
    double expectedX = 4;
    double expectedY = -1;


    assertEquals(expectedX,vectorResult.getX0());
    assertEquals(expectedY,vectorResult.getX1());
  }
  
  /**
   * Negative test:
   * Check if add function throws exception when null vector is passed
   */
  @Test
  public void TestNullAdd(){
    Vector2D nullVector = null;
    assertThrows(NullPointerException.class, () -> {
      testVector.add(nullVector);
    });
  }
  
  /**
   * Negative test:
   * Check if subtract function throws exception when null vector is passed
   */
  @Test
  public void TestNullSubtract(){
    Vector2D nullVector = null;
    assertThrows(NullPointerException.class, () -> {
      testVector.subtract(nullVector);
    });
  }

  /**
   * Negative test:
   * Try initializing a vector with zero point division to get a NaN
   */
  @Test
  public void TestZeroPointConstructor(){
    assertThrows(ArithmeticException.class, () -> {
      Vector2D zeroVector = new Vector2D(Math.sqrt(-5),Math.sqrt(-7));
    });
  }

}