package edu.ntnu.mappe08.Entity;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
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
    
    assertEquals(expectedX,testConstctorVector.getX());
    assertEquals(expectedY,testConstctorVector.getY());
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
    
    
    assertEquals(expectedX,vectorResult.getX());
    assertEquals(expectedY,vectorResult.getY());
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


    assertEquals(expectedX,vectorResult.getX());
    assertEquals(expectedY,vectorResult.getY());
  }

}