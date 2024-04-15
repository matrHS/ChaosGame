package edu.ntnu.mappe08.logic;

import static org.junit.jupiter.api.Assertions.*;

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
  
}