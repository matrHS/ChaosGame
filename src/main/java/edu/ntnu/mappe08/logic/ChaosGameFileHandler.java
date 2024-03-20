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

/**
 * Handles reading and writing of chaos game descriptions to and from files.
 */
public class ChaosGameFileHandler {
  
  public ChaosGameFileHandler() {
  }

  /**
   * Builds a chaos game description from a file.
   *
   * @param filepath path to the file to read from
   * @return a chaos game description
   */
  public ChaosGameDescription buildChaosGameDescriptionFromFile(String filepath) {
    if (filepath == null || filepath.isBlank()) {
      throw new IllegalArgumentException("filepath cannot be null or empty");
    }
    return buildChaosGameDescription(readFromFile(filepath));
  }
  
  /**
   * Builds a chaos game description from a list of strings.
   * 
   * @param fileContent the list of strings to build the chaos game description from
   * @return a chaos game description
   */
  public ChaosGameDescription buildChaosGameDescription(List<String> fileContent) {
    if (fileContent == null || fileContent.isEmpty()) {
      throw new IllegalArgumentException("fileContent cannot be null or empty");
    }
    String transformType;
    Vector2D minCoords;
    Vector2D maxCoords;
    List<Transform2D> transforms = new ArrayList<>();
    
    List<String> formatedFileContent = new ArrayList<>();
    for (int i = 0; i < fileContent.size(); i++) {
      formatedFileContent.add(fileContent.get(i).split("#")[0].trim());
    }
    transformType = formatedFileContent.get(0);
    
    // TODO: Consider refactoring each transform into its own class to generalize filehandler
    if (transformType.contains("Affine2D")) {
      minCoords = parseCoords(formatedFileContent.get(1));
      maxCoords = parseCoords(formatedFileContent.get(2));

      for (int i = 3; i < formatedFileContent.size(); i++) {
        transforms.add(parseAffineTransform(formatedFileContent.get(i)));
      }
    } else if (transformType.contains("Julia")) {
      minCoords = parseCoords(formatedFileContent.get(1));
      maxCoords = parseCoords(formatedFileContent.get(2));
      Complex point = parseCoords(formatedFileContent.get(3));
      transforms.add(new JuliaTransform(point, 1));
      transforms.add(new JuliaTransform(point,-1));

    } else {
      throw new IllegalArgumentException("Invalid file content");
    }
    return new ChaosGameDescription(minCoords, maxCoords, transforms);
  }
  
  /**
   * Reads a chaos game description from a file.
   *
   * @param filepath path to the file to read from
   * @return a chaos game description
   */
  public List<String> readFromFile(String filepath) {
    if (filepath == null || filepath.isBlank()) {
      throw new IllegalArgumentException("filepath cannot be null or empty");
    }
    Path path = Path.of(filepath);
    List<String> fileContent = new ArrayList<>();
    
    try (BufferedReader reader = Files.newBufferedReader(path)) {
      fileContent = reader.lines().toList();
      
    } catch (IOException e) {
      // TODO: Replace with logger
      e.printStackTrace();
      // TODO: Replace with custom exception
      throw new IllegalArgumentException("Could not read from file");
    }
    
    return fileContent;
  }

  /**
   * Method for parsing the coordinates from a line in the file.
   *
   * @param line the line to parse containing 2 numerical numbers at start of line
   */
  private Complex parseCoords(String line) {
    if (line == null || line.isBlank()) {
      throw new IllegalArgumentException("line cannot be null or empty");
    }
    String[] splitLine = line.split(",");
    return new Complex(Double.parseDouble(splitLine[0].trim()), 
        Double.parseDouble(splitLine[1].trim()));
  }

  /**
   * Method for parsing an affine transformation from a line in the file.
   * 
   * @param line the line to parse containing a 2x2 matrix and a 2D vector
   * @return an affine transformation
   */
  private AffineTransform2D parseAffineTransform(String line) {
    if (line == null || line.isBlank()) {
      throw new IllegalArgumentException("line cannot be null or empty");
    }
    String[] splitLine = line.split(",");
    Matrix2x2 parsedMatrix = new Matrix2x2(Double.parseDouble(splitLine[0].trim()), 
        Double.parseDouble(splitLine[1].trim()), 
        Double.parseDouble(splitLine[2].trim()), 
        Double.parseDouble(splitLine[3].trim()));
    Vector2D parsedVector = new Vector2D(Double.parseDouble(splitLine[4].trim()), 
        Double.parseDouble(splitLine[5].trim()));
        
    return new AffineTransform2D(parsedMatrix, parsedVector);
  }

  /**
   * Writes a chaos game description to a file.
   * Writes the file depending on type of transform.
   * 
   * @param description the chaos game description to write to file
   * @param path the path to write the file to
   */
  public void writeToFile(ChaosGameDescription description, String path) {
    if (description == null || path == null || path.isBlank()) {
      throw new IllegalArgumentException("description and path cannot be null or empty");
    }
    try (BufferedWriter writer = Files.newBufferedWriter(Path.of(path))) {
      String transformType = "";
      if (description.getTransforms().get(0) instanceof AffineTransform2D) {
        transformType = "Affine2D";
      } else if (description.getTransforms().get(0) instanceof JuliaTransform) {
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
          writer.write(" # Affine tranWsform \n");
        } else if (transform instanceof JuliaTransform) {
          JuliaTransform juliaTransform = (JuliaTransform) transform;
          writer.write(juliaTransform.getPointC().getRealPart() + ", " + juliaTransform.getPointC().getImaginaryPart());
          writer.write(" # Julia transform \n");
        }
      }
      
      
    } catch (IOException e) {
      // TODO: Replace with logger
      e.printStackTrace();
      // TODO: Replace with custom exception
      throw new IllegalArgumentException("Could not read from file");
    }
    
  }
}
