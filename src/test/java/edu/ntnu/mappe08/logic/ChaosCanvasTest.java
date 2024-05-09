package edu.ntnu.mappe08.logic;

import static org.junit.jupiter.api.Assertions.*;

import edu.ntnu.mappe08.entity.Vector2D;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Class for testing the ChaosCanvas class.
 *
 * <list>
 *   <li>
 *     <p>
 *       Positive test:
 *       Test the constructor of the ChaosCanvas class.
 *       This is done by initializing a ChaosCanvas object with the given parameters.
 *     </p>
 *   </li>
 *   <li>
 *     <p>
 *       Positive test:
 *       Test the getPixel and putPixel methods in the ChaosCanvas class.
 *       This is done by putting a pixel at a given point and then getting the pixel at transformed point
 *     </p>
 *   </li>
 *   <li>
 *     <p>
 *       Negative test:
 *       Test the getPixel and putPixel methods in the ChaosCanvas class.
 *     </p>
 *   </li>
 *   <li>
 *     <p>
 *       Positive test:
 *       Test the clear method in the ChaosCanvas class.
 *     </p>
 *   </li>
 * </list>
 */
class ChaosCanvasTest {

  private ChaosCanvas testCanvas;

  /**
   * Method to initialize test values.
   */
  @BeforeEach
  void initializeTestValues() {
    testCanvas = new ChaosCanvas(100, 100, new Vector2D(-2, -2), new Vector2D(2, 2));
  }

  /**
   * test the constructor of the ChaosCanvas class.
   */
  @Test
  void testConstructor() {
    ChaosCanvas testCanvasConstruct =
        new ChaosCanvas(200, 200, new Vector2D(-3, 5), new Vector2D(3, -5));
    Vector2D testMinCoords;
    Vector2D testMaxCoords;
    testMinCoords = testCanvasConstruct.getMinCoords();
    testMaxCoords = testCanvasConstruct.getMaxCoords();
    assertEquals(200, testCanvasConstruct.getWidth());
    assertEquals(200, testCanvasConstruct.getHeight());
    assertEquals(-3, testMinCoords.getX0());
    assertEquals(5, testMinCoords.getX1());
    assertEquals(3, testMaxCoords.getX0());
    assertEquals(-5, testMaxCoords.getX1());
  }

  /**
   * Get and put the pixel at a given point.
   */
  @Test
  void testValidGetPutPixel() {
    Vector2D point = new Vector2D(1, 1);
    Vector2D pointTransformed = new Vector2D(25, 74);
    testCanvas.putPixel(point);
    assertEquals(5, testCanvas.getPixel(pointTransformed));
  }

  /**
   * test to put and get the pixel at a given point.
   */
  @Test
  void testInvalidGetPutPixel() {
    assertThrows(IllegalArgumentException.class, () -> testCanvas.getPixel(new Vector2D(-1, 1)));
    assertThrows(IllegalArgumentException.class, () -> testCanvas.getPixel(null));
    assertThrows(IllegalArgumentException.class, () -> testCanvas.putPixel(null));
  }

  /**
   * test for the clear method.
   */
  @Test
  void testClear() {
    Vector2D point = new Vector2D(1, 1);
    Vector2D point2 = new Vector2D(2, 2);
    Vector2D pointTransformed = new Vector2D(25, 74);
    Vector2D point2Transformed = new Vector2D(0, 99);
    testCanvas.putPixel(point);
    testCanvas.putPixel(point2);
    assertEquals(5, testCanvas.getPixel(pointTransformed));
    assertEquals(5, testCanvas.getPixel(point2Transformed));
    testCanvas.clear();
    assertEquals(0, testCanvas.getPixel(pointTransformed));
    assertEquals(0, testCanvas.getPixel(point2Transformed));
  }

}