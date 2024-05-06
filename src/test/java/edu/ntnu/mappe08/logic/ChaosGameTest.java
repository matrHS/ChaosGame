package edu.ntnu.mappe08.logic;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * Test class for ChaosGame.
 *
 * <list>
 *   <li>
 *     <p>
 *       Positive test:
 *       Test that the constructor initializes the canvas, description and currentPoint.
 *     </p>
 *   </li>
 *   <li>
 *     <p>
 *       Negative test:
 *       Check if exception is thrown if description is null
 *     </p>
 *   </li>
 *   <li>
 *     <p>
 *       Negative test:
 *       Check that RunStep does not accept negative steps
 *     </p>
 *   </li>
 * </list>
 */
class ChaosGameTest {
  /**
   * Test that the constructor initializes the canvas, description and currentPoint.
   */
  @Test
void testConstructor() {
    ChaosGameDescriptionFactory factory = new ChaosGameDescriptionFactory();
    ChaosGameDescription description = factory.createDescription(TransformTypes.SIERPINSKI,
        null,
        null,
        null);
    ChaosGame chaosGame = new ChaosGame(description, 100, 100);
    assertNotNull(chaosGame.getCanvas());
  }

  /**
   * Check if exception is thrown if description is null
   */
  @Test
  void testConstructorNullDescription() {
    assertThrows(IllegalArgumentException.class, () -> new ChaosGame(null, 100, 100));
  }

  /**
   * Check that RunStep does not accept negative steps
   */
  @Test
  void testRunStepNegativeSteps() {
    ChaosGameDescriptionFactory factory = new ChaosGameDescriptionFactory();
    ChaosGameDescription description = factory.createDescription(TransformTypes.SIERPINSKI,
        null,
        null,
        null);
    ChaosGame chaosGame = new ChaosGame(description, 100, 100);
    
    assertThrows(IllegalArgumentException.class, () -> chaosGame.runSteps(-1));
  }
}