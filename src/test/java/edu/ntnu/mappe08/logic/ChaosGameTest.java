package edu.ntnu.mappe08.logic;

import static org.junit.jupiter.api.Assertions.*;

import edu.ntnu.mappe08.entity.Matrix2x2;
import edu.ntnu.mappe08.entity.Vector2D;
import java.util.ArrayList;
import java.util.List;
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
 *   <li>
 *     <p>
 *       Positive test:
 *       Check that setMincoords updates the minCoords and notifies observers
 *     </p>
 *   </li>
 *   <li>
 *     <p>
 *       Positive test:
 *       Check that setMaxCoords updates the maxCoords and notifies observers
 *     </p>
 *   </li>
 *   <li>
 *     <p>
 *       Negative test:
 *       Check that setMinCoords does not accept null coordinates
 *     </p>
 *   </li>
 *   <li>
 *     <p>
 *       Negative test:
 *       Check that setMaxCoords does not accept null coordinates
 *     </p>
 *   </li>
 *   <li>
 *     <p>
 *       Positive test:
 *       Check that setTransforms updates the transforms and notifies observers
 *     </p>
 *   </li>
 *   <li>
 *     <p>
 *       Negative test:
 *       Check that setTransforms does not accept null transforms
 *     </p>
 *   </li>
 *   <li>
 *     <p>
 *       Positive test:
 *       Check that getTransformType returns the correct transform type depending on description
 *     </p>
 *   </li>
 *   <li>
 *     <p>
 *       Positive test:
 *       Check that reconfigureCanvas updates the description and canvas
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
  
  /**
   * Check that setMincoords updates the minCoords and notifies observers
   */
  @Test
  void testSetMinCoords() {
    ChaosGameDescriptionFactory factory = new ChaosGameDescriptionFactory();
    ChaosGameDescription description = factory.createDescription(TransformTypes.SIERPINSKI,
        null,
        null,
        null);
    ChaosGame chaosGame = new ChaosGame(description, 100, 100);
    Vector2D minCoords = new Vector2D(0, 0);
    chaosGame.setMinCoords(minCoords);
    assertEquals(minCoords, chaosGame.getDescription().getMinCoords());
  }
  
  /**
   * Check that setMaxCoords updates the maxCoords and notifies observers
   */
  @Test
  void testSetMaxCoords() {
    ChaosGameDescriptionFactory factory = new ChaosGameDescriptionFactory();
    ChaosGameDescription description = factory.createDescription(TransformTypes.SIERPINSKI,
        null,
        null,
        null);
    ChaosGame chaosGame = new ChaosGame(description, 100, 100);
    Vector2D maxCoords = new Vector2D(100, 100);
    chaosGame.setMaxCoords(maxCoords);
    assertEquals(maxCoords, chaosGame.getDescription().getMaxCoords());
  }
  
  /**
   * Check that setMinCoords does not accept null coordinates
   */
  @Test
  void testSetMinCoordsNull() {
    ChaosGameDescriptionFactory factory = new ChaosGameDescriptionFactory();
    ChaosGameDescription description = factory.createDescription(TransformTypes.SIERPINSKI,
        null,
        null,
        null);
    ChaosGame chaosGame = new ChaosGame(description, 100, 100);
    assertThrows(IllegalArgumentException.class, () -> chaosGame.setMinCoords(null));
  }
  
  /**
   * Check that setMaxCoords does not accept null coordinates
   */
  @Test
  void testSetMaxCoordsNull() {
    ChaosGameDescriptionFactory factory = new ChaosGameDescriptionFactory();
    ChaosGameDescription description = factory.createDescription(TransformTypes.SIERPINSKI,
        null,
        null,
        null);
    ChaosGame chaosGame = new ChaosGame(description, 100, 100);
    assertThrows(IllegalArgumentException.class, () -> chaosGame.setMaxCoords(null));
  }
  
  /**
   * Check that setTransforms updates the transforms and notifies observers
   */
  @Test
  void testSetTransforms() {
    ChaosGameDescriptionFactory factory = new ChaosGameDescriptionFactory();
    ChaosGameDescription description = factory.createDescription(TransformTypes.SIERPINSKI,
        null,
        null,
        null);
    ChaosGame chaosGame = new ChaosGame(description, 100, 100);
    List<Transform2D> transforms = new ArrayList<>();
    Matrix2x2 matrix = new Matrix2x2(0.5, 0.5, 0.5, 0.5);
    Vector2D vector = new Vector2D(0, 0);
    transforms.add(new AffineTransform2D(matrix, vector));
    chaosGame.setTransforms(transforms);
    assertEquals(transforms, chaosGame.getDescription().getTransforms());
  }
  
  /**
   * Check that setTransforms does not accept null transforms
   */
  @Test
  void testSetTransformsNull() {
    ChaosGameDescriptionFactory factory = new ChaosGameDescriptionFactory();
    ChaosGameDescription description = factory.createDescription(TransformTypes.SIERPINSKI,
        null,
        null,
        null);
    ChaosGame chaosGame = new ChaosGame(description, 100, 100);
    assertThrows(IllegalArgumentException.class, () -> chaosGame.setTransforms(null));
  }
  
  /**
   * Check that getTransformType returns the correct transform type depending on description
   */
  @Test
  void testGetTransformType() {
    ChaosGameDescriptionFactory factory = new ChaosGameDescriptionFactory();
    ChaosGameDescription description = factory.createDescription(TransformTypes.SIERPINSKI,
        null,
        null,
        null);
    ChaosGame chaosGame = new ChaosGame(description, 100, 100);
    assertEquals(TransformTypes.AFFINE2D, chaosGame.getTransformType());
  }
  
  /**
   * Check that reconfigureCanvas updates the description and canvas
   */
  @Test
  void testReconfigureCanvas() {
    ChaosGameDescriptionFactory factory = new ChaosGameDescriptionFactory();
    ChaosGameDescription description = factory.createDescription(TransformTypes.SIERPINSKI,
        null,
        null,
        null);
    ChaosGame chaosGame = new ChaosGame(description, 100, 100);
    Vector2D minCoords = new Vector2D(0, 0);
    Vector2D maxCoords = new Vector2D(100, 100);
    description.setMinCoords(minCoords);
    description.setMaxCoords(maxCoords);
    chaosGame.reconfigureChaosGame(description, 150, 150);
    assertEquals(minCoords, chaosGame.getDescription().getMinCoords());
    assertEquals(maxCoords, chaosGame.getDescription().getMaxCoords());
    assertEquals(150, chaosGame.getCanvas().getWidth());
    assertEquals(150, chaosGame.getCanvas().getHeight());
  }
  
}