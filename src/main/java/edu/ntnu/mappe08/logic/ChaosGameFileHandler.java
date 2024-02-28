package edu.ntnu.mappe08.logic;

import edu.ntnu.mappe08.entity.Matrix2x2;
import edu.ntnu.mappe08.entity.Vector2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * Handles reading and writing of chaos game descriptions to and from files.
 */
public class ChaosGameFileHandler {
  
  public ChaosGameFileHandler() {
  }
  
  public ChaosGameDescription readFromFile(String filepath) {
    Path path = Path.of(filepath);
    
    String transformType = "";
    Vector2D minCoords = new Vector2D(0, 0);
    Vector2D maxCoords = new Vector2D(0, 0);
    List<Transform2D> transforms = List.of();

    try (BufferedReader reader = Files.newBufferedReader(path)) {
      List<String> fileContent = reader.lines().toList();
      if (fileContent.get(0).contains("Affine2D")) {
        minCoords = parseCoords(fileContent.get(1));
        maxCoords = parseCoords(fileContent.get(2));
        
        for (String line : fileContent) {
          transforms.add(parseAffineTransform(line));
        }
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
  private Vector2D parseCoords(String line) {
    if (line == null || line.isBlank()) {
      throw new IllegalArgumentException("line cannot be null or empty");
    }
    String[] splitLine = line.replace(",", "").split(" ");
    return new Vector2D(Double.parseDouble(splitLine[0]), Double.parseDouble(splitLine[1]));
  }
  
  private AffineTransform2D parseAffineTransform(String line) {
    if (line == null || line.isBlank()) {
      throw new IllegalArgumentException("line cannot be null or empty");
    }
    String[] splitLine = line.replace(",", "").split(" ");
    Matrix2x2 parsedMatrix = new Matrix2x2(Double.parseDouble(splitLine[0]), 
        Double.parseDouble(splitLine[1]), 
        Double.parseDouble(splitLine[2]), 
        Double.parseDouble(splitLine[3]));
    Vector2D parsedVector = new Vector2D(Double.parseDouble(splitLine[4]), 
        Double.parseDouble(splitLine[5]));
        
    return new AffineTransform2D(parsedMatrix, parsedVector);
  }
  
  public void writeToFile(ChaosGameDescription description, String path) {
  }
}
