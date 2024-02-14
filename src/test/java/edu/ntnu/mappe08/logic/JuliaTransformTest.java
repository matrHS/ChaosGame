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
    this.testComplex = new Complex(11, 46);
  }
  
  /**
   * Positive test:
   * Test Julia transform with positive sign.
   */
  @Test
  public void testValidPosSignTransform() {
    sign = 1;
    testTransform = new JuliaTransform(testComplex,sign);
    Complex testPointZ = new Complex(2, 6);
    
    Complex result = (Complex) testTransform.transform(testPointZ);
    
    assertEquals(5, result.getRealPart());
    assertEquals(4, result.getImaginaryPart());
  }

  /**
   * Positive test:
   * Test Julia transform with negative sign.
   */
  @Test
  public void testValidNegSignTransform() {
    sign = -1;
    testTransform = new JuliaTransform(testComplex,sign);
    Complex testPointZ = new Complex(2, 6);

    Complex result = (Complex) testTransform.transform(testPointZ);

    assertEquals(-5, result.getRealPart());
    assertEquals(-4, result.getImaginaryPart());
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
}