package edu.ntnu.mappe08.logic;

import static org.junit.jupiter.api.Assertions.*;

import edu.ntnu.mappe08.entity.Matrix2x2;
import edu.ntnu.mappe08.entity.Vector2D;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

/**
 * Test class for the AffineTransform2D class.
 *
 * <list>
 *   <li>
 *     <p>
 *       Positive test:
 *       Test the transform method in the AffineTransform2D class.
 *     </p>
 *   </li>
 *   <li>
 *     <p>
 *       Negative test:
 *       Test Affine transform with null point.
 *     </p>
 *   </li>
 *   <li>
 *     <p>
 *       Positive test:
 *       Test getMatrix and getVector method for AffineTransform2D.
 *     </p>
 *   </li>
 * </list>
 */

class AffineTransform2DTest {

    private AffineTransform2D testTransform;
    private Matrix2x2 testMatrix;
    private Vector2D testVector;

    /**
    * Initializes the test values for each test
    */
    @BeforeEach
    public void initializeTestValues() {
      this.testMatrix = new Matrix2x2(1, 2, 3, 4);
      this.testVector = new Vector2D(1, 2);
      this.testTransform = new AffineTransform2D(this.testMatrix, this.testVector);
    }

    /**
    * Test the transform method in the AffineTransform2D class.
    */
    @Test
    public void testValidTransform() {
      Vector2D testPoint = new Vector2D(7, 6);
      Vector2D result = this.testTransform.transform(testPoint);
      assertEquals(20, result.getX0());
      assertEquals(47, result.getX1());
    }

  /**
   * Test Affine transform with null point.
   */
  @Test
  public void testNullPointTransform() {
    Vector2D testPoint = null;
    assertThrows(NullPointerException.class, () -> {
      this.testTransform.transform(testPoint);
    });
  }

  /**
   * Test getMatrix and getVector method for AffineTransform2D.
   */
  @Test
  public void testGetMatrixAndVector() {
    assertEquals(this.testMatrix, this.testTransform.getMatrix());
    assertEquals(this.testVector, this.testTransform.getVector());
  }


}