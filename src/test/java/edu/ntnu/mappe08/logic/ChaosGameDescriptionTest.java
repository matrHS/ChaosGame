package edu.ntnu.mappe08.logic;

import static org.junit.jupiter.api.Assertions.*;

import edu.ntnu.mappe08.entity.Vector2D;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ChaosGameDescriptionTest {

  Vector2D testMinCoords;
  Vector2D testMaxCoords;
  
  @BeforeEach
  void initializeTests() {
    testMinCoords = new Vector2D(0, 0);
    testMaxCoords = new Vector2D(1, 1);
  }
  
  /**
   * Positive test:
   * Test that the constructor sets the minCoords, maxCoords and transforms correctly.
   */
  @Test
  void testValidConstructor() {
    List<Transform2D> transforms = new ArrayList<>();
    ChaosGameDescription description = new ChaosGameDescription(testMinCoords, testMaxCoords, transforms);
    assertEquals(testMinCoords, description.getMinCoords());
    assertEquals(testMaxCoords, description.getMaxCoords());
    assertEquals(transforms, description.getTransforms());
  }
  
  /**
   * Negative test:
   * Test that the constructor throws an IllegalArgumentException when any parameter is null.
   */
  @Test
  void testNullParamConstructor() {
    List<Transform2D> transforms = new ArrayList<>();
    assertThrows(IllegalArgumentException.class, () -> new ChaosGameDescription(null, testMaxCoords, transforms));
    assertThrows(IllegalArgumentException.class, () -> new ChaosGameDescription(testMinCoords, null, transforms));
    assertThrows(IllegalArgumentException.class, () -> new ChaosGameDescription(testMinCoords, testMaxCoords, null));
  }
  

}