package edu.ntnu.mappe08.logic;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
   * TODO: Move to factory test class
   * Test that the buildChaosGameDescription method returns a ChaosGameDescription object with the correct values.
   */
  @Test
  void testValidBuildChaosGameDescription() {
    ChaosGameFileHandler fileHandler = new ChaosGameFileHandler();
    assertEquals(3, fileHandler.buildChaosGameDescription(testAffineList).getTransforms().size());
    assertEquals(2, fileHandler.buildChaosGameDescription(testJuliaList).getTransforms().size());
  }
  
  /**
   * TODO: Move to factory test class
   * Test that the buildChaosGameDescription method throws an IllegalArgumentException when the fileContent is null.
   */
  @Test
  void testNullFileContent() {
    ChaosGameFileHandler fileHandler = new ChaosGameFileHandler();
    assertThrows(IllegalArgumentException.class, () -> fileHandler.buildChaosGameDescription(null));
  }

  /**
   * TODO: Move to factory test class
   * Test that Exception is thrown if structure of file is incorrect leading to incorrect parsing.
   */
  @Test
  void testInvalidFileContent() {
    ChaosGameFileHandler fileHandler = new ChaosGameFileHandler();
    
    List<String> invalidFileContent = List.of("Affine2D", 
        "0, 0", 
        "1, 1", 
        ".5, 0, 0, .5, 0, 0", 
        "", 
        ".5, 0, 0, .5, .5, 0");
    assertThrows(IllegalArgumentException.class, () -> fileHandler.buildChaosGameDescription(invalidFileContent));
  }

  /**
   * TODO: Move to factory test class
   * Test that buildChaosGameDescription throws an IllegalArgumentException when no valid transform type is set.
   */
  @Test
  void testInvalidTransformType() {
    ChaosGameFileHandler fileHandler = new ChaosGameFileHandler();
    List<String> invalidFileContent = List.of("InvalidType", 
        "0, 0", 
        "1, 1", 
        ".5, 0, 0, .5, 0, 0", 
        ".5, 0, 0, .5, .25, .5", 
        ".5, 0, 0, .5, .5, 0");
    assertThrows(IllegalArgumentException.class, () -> fileHandler.buildChaosGameDescription(invalidFileContent));
  }

  /**
   * Negative test:
   * Test that the readFromFile method throws an IllegalArgumentException when the fileContent is null.
   */
  @Test
  void testNullFilepath() {
    ChaosGameFileHandler fileHandler = new ChaosGameFileHandler();
    assertThrows(IllegalArgumentException.class, () -> fileHandler.readFromFile(null));
  }

  /**
   * Negative test:
   * Test that the readFromFile method throws an IllegalArgumentException when the fileContent is empty.
   */
  @Test
  void testEmptyFilepath() {
    ChaosGameFileHandler fileHandler = new ChaosGameFileHandler();
    assertThrows(IllegalArgumentException.class, () -> fileHandler.readFromFile(""));
  }
  
  /**
   * TODO: Move to factory test class
   * Test that the buildChaosGameDescriptionFromFile method throws an IllegalArgumentException when the filepath is null.
   */
  @Test
  void testNullFilepathBuildChaosGameDescriptionFromFile() {
    ChaosGameFileHandler fileHandler = new ChaosGameFileHandler();
    assertThrows(IllegalArgumentException.class, () -> fileHandler.buildChaosGameDescriptionFromFile(null));
  }


}