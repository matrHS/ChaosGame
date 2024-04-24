package edu.ntnu.mappe08.logic;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import org.junit.jupiter.api.Test;

/**
 * Test class for ChaosGameDescriptionFactory.
 */
class ChaosGameDescriptionFactoryTest {

  /**
   * Test that factory create a default sierpinski description with 3 transforms.
   */
  @Test
  void createDefaultSierpinskiDescription() {
    ChaosGameDescriptionFactory factory = new ChaosGameDescriptionFactory();
    ChaosGameDescription description = factory.createDescription("Sierpinski");
    assertEquals(3, description.getTransforms().size());
  }
  
  /**
   * Test that factory create a default barnsley description with 4 transforms.
   */
  @Test
  void createDefaultBarnsleyDescription() {
    ChaosGameDescriptionFactory factory = new ChaosGameDescriptionFactory();
    ChaosGameDescription description = factory.createDescription("Barnsley");
    assertEquals(4, description.getTransforms().size());
  }
  
  /**
   * Test that factory create a default julia description with 2 transforms.
   */
  @Test
  void createDefaultJuliaDescription() {
    ChaosGameDescriptionFactory factory = new ChaosGameDescriptionFactory();
    ChaosGameDescription description = factory.createDescription("Julia");
    assertEquals(2, description.getTransforms().size());
  }

  /**
   * TODO: Move to factory test class
   * Test that Exception is thrown if structure of file is incorrect leading to incorrect parsing.
   */
  @Test
  void testInvalidFileContent() {
    ChaosGameDescriptionFactory factory = new ChaosGameDescriptionFactory();

    List<String> invalidFileContent = List.of("Affine2D",
        "0, 0",
        "1, 1",
        ".5, 0, 0, .5, 0, 0",
        "",
        ".5, 0, 0, .5, .5, 0");
    assertThrows(IllegalArgumentException.class, () -> factory.buildChaosGameDescription(invalidFileContent));
  }

  /**
   * TODO: Move to factory test class
   * Test that buildChaosGameDescription throws an IllegalArgumentException when no valid transform type is set.
   */
  @Test
  void testInvalidTransformType() {
    ChaosGameDescriptionFactory factory = new ChaosGameDescriptionFactory();
    List<String> invalidFileContent = List.of("InvalidType",
        "0, 0",
        "1, 1",
        ".5, 0, 0, .5, 0, 0",
        ".5, 0, 0, .5, .25, .5",
        ".5, 0, 0, .5, .5, 0");
    assertThrows(IllegalArgumentException.class, () -> factory.buildChaosGameDescription(invalidFileContent));
  }

  
  
}