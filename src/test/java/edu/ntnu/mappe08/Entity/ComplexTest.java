package edu.ntnu.mappe08.Entity;

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
    * Positive test:
    * Test the constructor in the Complex class.
    */
    @Test
    public void testValidComplexConstructor() {
      Complex testConstructorComplex = new Complex(2, 3);

      assertEquals(2, testConstructorComplex.getRealPart());
      assertEquals(3, testConstructorComplex.getImaginaryPart());
    }
  
    /**
    * Positive test:
    * Test the sqrt method in the Complex class.
    */
    @Test
    public void testValidSqrt() {
      Complex result = this.testComplex.sqrt();
      
      assertEquals(5, result.getX());
      assertEquals(4, result.getY());
    }

}