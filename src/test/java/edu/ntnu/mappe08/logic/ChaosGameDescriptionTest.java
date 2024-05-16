package edu.ntnu.mappe08.logic;

import static org.junit.jupiter.api.Assertions.*;

import edu.ntnu.mappe08.entity.Complex;
import edu.ntnu.mappe08.entity.Matrix2x2;
import edu.ntnu.mappe08.entity.Vector2D;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test class for the ChaosGameDescription class.
 *
 * <list>
 *   <li>
 *     <p>
 *       Positive test:
 *       Test that the constructor sets the minCoords, maxCoords and transforms correctly.
 *     </p>
 *   </li>
 *   <li>
 *     <p>
 *       Negative test:
 *       Test that the constructor throws an IllegalArgumentException when any parameter is null.
 *     </p>
 *   </li>
 *   <li>
 *     <p>
 *       Positive test:
 *       Test that getTransformType returns the correct transform type for Affine2D, Julia and None.
 *     </p>
 *   </li>
 * </list>
 */
class ChaosGameDescriptionTest {

  Vector2D testMinCoords;
  Vector2D testMaxCoords;
  
  @BeforeEach
  void initializeTests() {
    testMinCoords = new Vector2D(0, 0);
    testMaxCoords = new Vector2D(1, 1);
  }
  
  /**
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
   * Test that the constructor throws an IllegalArgumentException when any parameter is null.
   */
  @Test
  void testNullParamConstructor() {
    List<Transform2D> transforms = new ArrayList<>();
    assertThrows(IllegalArgumentException.class, () -> new ChaosGameDescription(null, testMaxCoords, transforms));
    assertThrows(IllegalArgumentException.class, () -> new ChaosGameDescription(testMinCoords, null, transforms));
    assertThrows(IllegalArgumentException.class, () -> new ChaosGameDescription(testMinCoords, testMaxCoords, null));
  }
  
  /**
   * Test that getTransformType returns the correct transform type for all 3 types.
   */
  @Test
  void testGetTransformType() {
    List<Transform2D> transforms = new ArrayList<>();
    Matrix2x2 matrix = new Matrix2x2(0.5, 0, 0, 0.5);
    Vector2D vector = new Vector2D(0.5, 0.5);
    AffineTransform2D affine = new AffineTransform2D(matrix, vector);

    Complex c = new Complex(0.5, 0.5);
    JuliaTransform julia = new JuliaTransform(c);
    
    transforms.add(affine);
    ChaosGameDescription description = new ChaosGameDescription(testMinCoords, testMaxCoords, transforms);
    assertEquals(TransformTypes.AFFINE2D, description.getTransformType());
    
    transforms.clear();
    transforms.add(julia);
    description = new ChaosGameDescription(testMinCoords, testMaxCoords, transforms);
    assertEquals(TransformTypes.JULIA, description.getTransformType());
    
    transforms.clear();
    description = new ChaosGameDescription(testMinCoords, testMaxCoords, transforms);
    assertEquals(TransformTypes.NONE, description.getTransformType());
  }
  

}