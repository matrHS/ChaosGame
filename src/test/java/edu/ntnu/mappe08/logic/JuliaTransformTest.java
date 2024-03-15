package edu.ntnu.mappe08.logic;

import static org.junit.jupiter.api.Assertions.*;

import edu.ntnu.mappe08.entity.Complex;
import edu.ntnu.mappe08.entity.Vector2D;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class JuliaTransformTest {
  Complex testComplex;
  int sign;
  JuliaTransform testTransform;
  
  /**
   * Initializes the test values for each test.
   */
  @BeforeEach
  public void initializeTestValues() {
    this.testComplex = new Complex(0.4, 0.2);
  }
  
  /**
   * Positive test:
   * Test Julia transform with positive sign.
   */
  @Test
  public void testValidPosSignTransform() {
    sign = 1;
    testTransform = new JuliaTransform(testComplex,sign);
    Complex testPointZ = new Complex(0.3, 0.6);
    
    Complex result = (Complex) testTransform.transform(testPointZ);
    
    assertEquals(0.506, result.getRealPart(), 0.001);
    assertEquals(0.395, result.getImaginaryPart(), 0.001);
  }

  /**
   * Positive test:
   * Test Julia transform with negative sign.
   */
  @Test
  public void testValidNegSignTransform() {
    sign = -1;
    testTransform = new JuliaTransform(testComplex,sign);
    Complex testPointZ = new Complex(0.3, 0.6);

    Complex result = (Complex) testTransform.transform(testPointZ);

    assertEquals(-0.506, result.getRealPart(), 0.001);
    assertEquals(-0.395, result.getImaginaryPart(), 0.001);
  }
  
  /**
   * Negative test:
   * Test Julia transform with null point.
   */
  @Test
  public void testNullPointTransform() {
    sign = 1;
    testTransform = new JuliaTransform(testComplex,sign);
    Complex testPointZ = null;
    
    assertThrows(NullPointerException.class, () -> {
      testTransform.transform(testPointZ);
    });
  }
  
  /**
   * Negative test:
   * Test Julia transform with non-complex point.
   */
  @Test
  public void testNonComplexPointTransform() {
    sign = 1;
    testTransform = new JuliaTransform(testComplex,sign);
    Vector2D testPointZ = new Vector2D(2, 6);
    
    assertThrows(IllegalArgumentException.class, () -> {
      testTransform.transform(testPointZ);
    });
  }
  
  /**
   * Positive test:
   * Test getPointC method for JuliaTransform.
   */
  @Test
  public void testGetPointC() {
    sign = 1;
    testTransform = new JuliaTransform(testComplex,sign);
    assertEquals(this.testComplex, testTransform.getPointC());
  }
}