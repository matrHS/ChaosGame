package edu.ntnu.mappe08.logic;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test class for the ChaosGameFileHandler class.
 *
 * <list>
 *   <li>
 *     <p>
 *       Negative test:
 *       Test that the readFromFile method throws an IllegalArgumentException when the fileContent is null.
 *     </p>
 *   </li>
 *   <li>
 *     <p>
 *       Negative test:
 *       Test that the readFromFile method throws an IllegalArgumentException when the fileContent is empty.
 *     </p>
 *   </li>
 * </list>
 */
class ChaosGameFileHandlerTest {
  
  String testAffine;
  String testJulia;
  List<String> testAffineList;
  List<String> testJuliaList;
  
  @BeforeEach
  void initializeTestValues() {
    testAffine = "Affine2D                # Type of fractal \n" +
        "0, 0                    # Lower left \n" +
        "1, 1                    # Upper right \n" +
        ".5, 0, 0, .5, 0, 0      # 1st transform (a00, a01, a10, a11, b0, b1) \n" +
        ".5, 0, 0, .5, .25, .5   # 2nd transform \n" +
        ".5, 0, 0, .5, .5, 0     # 3rd transform";
    testJulia = "Julia           # Type of transform \n" +
        "-1.6, -1        # Lower left \n" +
        "1.6, 1          # Upper right \n" +
        "-.74543, .11301 # Real and imaginary parts of the constant c";
    
    testAffineList = testAffine.lines().toList();
    testJuliaList = testJulia.lines().toList();
  }

  

  

  /**
   * Test that the readFromFile method throws an IllegalArgumentException when the fileContent is null.
   */
  @Test
  void testNullFilepath() {
    ChaosGameFileHandler fileHandler = new ChaosGameFileHandler();
    assertThrows(IllegalArgumentException.class, () -> fileHandler.readFromFile(null));
  }

  /**
   * Test that the readFromFile method throws an IllegalArgumentException when the fileContent is empty.
   */
  @Test
  void testEmptyFilepath() {
    ChaosGameFileHandler fileHandler = new ChaosGameFileHandler();
    assertThrows(IllegalArgumentException.class, () -> fileHandler.readFromFile(""));
  }
  

}