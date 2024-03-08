package edu.ntnu.mappe08.logic;

import static org.junit.jupiter.api.Assertions.*;

import edu.ntnu.mappe08.entity.Vector2D;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Class for testing the ChaosCanvas class.
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
   * Method to test the constructor of the ChaosCanvas class.
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
    assertEquals(-3, testMinCoords.getX());
    assertEquals(5, testMinCoords.getY());
    assertEquals(3, testMaxCoords.getX());
    assertEquals(-5, testMaxCoords.getY());
  }

  /**
   * Positive test to get and put the pixel at a given point.
   */
  @Test
  void testValidGetPutPixel() {
    Vector2D point = new Vector2D(1, 1);
    testCanvas.putPixel(point);
    assertEquals(1, testCanvas.getPixel(point));
  }

  /**
   * Negative test to put and get the pixel at a given point.
   */
  @Test
  void testInvalidGetPutPixel() {
    assertThrows(IllegalArgumentException.class, () -> testCanvas.getPixel(new Vector2D(-1, 1)));
    assertThrows(IllegalArgumentException.class, () -> testCanvas.getPixel(null));
    assertThrows(IllegalArgumentException.class, () -> testCanvas.putPixel(new Vector2D(-1, 1)));
    assertThrows(IllegalArgumentException.class, () -> testCanvas.putPixel(null));
  }

  /**
   * Positive test for the clear method.
   */
  @Test
  void testClear() {
    Vector2D point = new Vector2D(1, 1);
    Vector2D point2 = new Vector2D(2, 2);
    testCanvas.putPixel(point);
    testCanvas.putPixel(point2);
    assertEquals(1, testCanvas.getPixel(point));
    assertEquals(1, testCanvas.getPixel(point2));
    testCanvas.clear();
    assertEquals(0, testCanvas.getPixel(point));
    assertEquals(0, testCanvas.getPixel(point2));
  }

}