package edu.ntnu.mappe08.logic;

import edu.ntnu.mappe08.entity.Complex;
import edu.ntnu.mappe08.entity.Matrix2x2;
import edu.ntnu.mappe08.entity.Vector2D;
import java.io.BufferedReader;
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
   * Reads a chaos game description from a file.
   *
   * @param filepath path to the file to read from
   * @return a chaos game description
   */
  public ChaosGameDescription readFromFile(String filepath) {
    Path path = Path.of(filepath);
    String transformType = "";
    Vector2D minCoords = new Vector2D(0, 0);
    Vector2D maxCoords = new Vector2D(0, 0);
    List<Transform2D> transforms = new ArrayList<>();

    try (BufferedReader reader = Files.newBufferedReader(path)) {
      List<String> fileContent = reader.lines().toList();
      List<String> formatedFileContent = new ArrayList<>();
      for (int i = 0; i < fileContent.size(); i++) {
        formatedFileContent.add(fileContent.get(i).split("#")[0].trim());
      }
      
      // TODO: Consider refactoring each transform into its own class to generalize filehandler
      if (formatedFileContent.get(0).contains("Affine2D")) {
        minCoords = parseCoords(formatedFileContent.get(1));
        maxCoords = parseCoords(formatedFileContent.get(2));
        
        for (int i = 3; i < formatedFileContent.size(); i++) {
          transforms.add(parseAffineTransform(formatedFileContent.get(i)));
        }
      } else if (formatedFileContent.get(0).contains("Julia")) {
        Complex point = parseCoords(formatedFileContent.get(1));
        transforms.add(new JuliaTransform(point));
        
      }
    } catch (IOException e) {
      // TODO: Replace with logger
      e.printStackTrace();
      // TODO: Replace with custom exception
      throw new IllegalArgumentException("Could not read from file");
    }
    
    return new ChaosGameDescription(minCoords, maxCoords, transforms);
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
  
  public void writeToFile(ChaosGameDescription description, String path) {
  }
}
