package edu.ntnu.mappe08.entity;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ComplexTest {
  
    private Complex testComplex;
  
    /**
    * Initializes the test values for each test
    */
    @BeforeEach
    public void initializeTestValues() {
      this.testComplex = new Complex(9, 40);
    }
  
    /**
    * Test the constructor in the Complex class.
    */
    @Test
    public void testValidComplexConstructor() {
      Complex testConstructorComplex = new Complex(2, 3);

      assertEquals(2, testConstructorComplex.getRealPart());
      assertEquals(3, testConstructorComplex.getImaginaryPart());
    }
  
    /**
    * Test the sqrt method in the Complex class.
    */
    @Test
    public void testValidSqrt() {
      Complex result = this.testComplex.sqrt();
      
      assertEquals(5, result.getX0());
      assertEquals(4, result.getX1());
    }

  /**
   * Checks that the sqrt method returns the correct value when the imaginary part is zero
   */
  @Test
    public void textImZero() {
      Complex testImZero = new Complex(5, 0);
      Complex result = testImZero.sqrt();
      
      assertEquals(2.23, result.getX0(), 0.01);
      assertEquals(0, result.getX1());
    }

  /**
   * Try initializing a complex with zero point division to get a NaN
   */
  @Test
  public void TestZeroPointConstructor(){
    assertThrows(ArithmeticException.class, () -> {
      Complex zeroVector = new Complex(5/0,3/0);
    });
  }
}