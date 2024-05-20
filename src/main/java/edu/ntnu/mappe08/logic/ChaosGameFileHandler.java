package edu.ntnu.mappe08.logic;

import edu.ntnu.mappe08.entity.Complex;
import edu.ntnu.mappe08.entity.Matrix2x2;
import edu.ntnu.mappe08.entity.Vector2D;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Handles reading and writing of chaos game descriptions to and from files.
 */
public class ChaosGameFileHandler {

  private Logger logger;

  public ChaosGameFileHandler() {
    this.logger = Logger.getLogger(ChaosGameFileHandler.class.getName());
  }

  
  
  
  /**
   * Reads a chaos game description from a file.
   *
   * @param filepath path to the file to read from
   * @return a chaos game description
   * @throws ValueParseException if the file could not be read
   */
  public List<String> readFromFile(String filepath) throws ValueParseException {
    if (filepath == null || filepath.isBlank()) {
      throw new IllegalArgumentException("filepath cannot be null or empty");
    }
    Path path = Path.of(filepath);
    List<String> fileContent = new ArrayList<>();
    
    try (BufferedReader reader = Files.newBufferedReader(path)) {
      fileContent = reader.lines().toList();
      
    } catch (IOException e) {
      logger.warning("Could not read from file");
      throw new ValueParseException("Could not read from file");
    }
    
    return fileContent;
  }

  

  /**
   * Writes chaos game description to a file.
   * Writes the file depending on type of transform.
   *
   * @param description the chaos game description to write to file
   * @param path the path to write the file to
   * @throws ValueParseException if the file could not be written to
   */
  public void writeToFile(ChaosGameDescription description, String path)
      throws ValueParseException {
    if (description == null || path == null || path.isBlank()) {
      throw new IllegalArgumentException("description and path cannot be null or empty");
    }
    try (BufferedWriter writer = Files.newBufferedWriter(Path.of(path))) {
      String transformType = "";
      if (description.getTransformType() == TransformTypes.AFFINE2D) {
        transformType = "Affine2D";
      } else if (description.getTransformType() == TransformTypes.JULIA) {
        transformType = "Julia";
      }
      writer.write(transformType);
      writer.write(" # Type of transform \n");
      writer.write(description.getMinCoords().getX0() + ", " + description.getMinCoords().getX1());
      writer.write(" # Lower left \n");
      writer.write(description.getMaxCoords().getX0() + ", " + description.getMaxCoords().getX1());
      writer.write(" # Upper Right \n");
      
      for (Transform2D transform : description.getTransforms()) {
        if (transform instanceof AffineTransform2D) {
          AffineTransform2D affineTransform = (AffineTransform2D) transform;
          writer.write(affineTransform.getMatrix().getA00() + ", " 
              + affineTransform.getMatrix().getA01() + ", " 
              + affineTransform.getMatrix().getA10() + ", " 
              + affineTransform.getMatrix().getA11() + ", " 
              + affineTransform.getVector().getX0() + ", " 
              + affineTransform.getVector().getX1());
          writer.write(" # Affine transform \n");
        } else if (transform instanceof JuliaTransform) {
          JuliaTransform juliaTransform = (JuliaTransform) transform;
          writer.write(juliaTransform.getPointC().getRealPart() 
              + ", " 
              + juliaTransform.getPointC().getImaginaryPart());
          writer.write(" # Julia transform \n");
        }
      }
      
      
    } catch (IOException e) {
      logger.warning("Could not write to file");
      throw new ValueParseException("Could not write to file");
    }
    
  }
}
