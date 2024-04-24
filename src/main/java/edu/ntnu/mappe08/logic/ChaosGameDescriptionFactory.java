package edu.ntnu.mappe08.logic;


import edu.ntnu.mappe08.entity.Complex;
import edu.ntnu.mappe08.entity.Matrix2x2;
import edu.ntnu.mappe08.entity.Vector2D;
import java.util.ArrayList;
import java.util.List;

/**
 * Factory for creating ChaosGameDescriptions.
 */
public class ChaosGameDescriptionFactory {


  // TODO: Refactor to use enum instead of string.

  public ChaosGameDescription createDescription(String type) {
    return createDescription(type, null, null, null);
  }
  
  /**
   * Creates a ChaosGameDescription based on the type.
   *
   * @param type the type of chaos game description to create
   * @return a ChaosGameDescription
   */
  public ChaosGameDescription createDescription(String type, 
                                                 Vector2D minCoords, 
                                                 Vector2D maxCoords, 
                                                 List<Transform2D> transforms) {
    ChaosGameDescription description = null;

    switch (type) {

      case "Sierpinski":
        description = sierpinskiDescription();
        break;
      case "Affine2D":
        description = affineDescription(minCoords, maxCoords, transforms);
        break;
      case "Barnsley":
        description = barnsleyDescription();
        break;

      case "Julia":
        description = juliaDescription(minCoords, maxCoords, transforms);
        break;

      default:
        return null;
    }
    return description;
  }


  /**
   * Creates ChaosGameDescription based on Julia transforms.
   *
   * @return a ChaosGameDescription
   */
  private ChaosGameDescription juliaDescription(Vector2D minCoords,
                                                Vector2D maxCoords,
                                                List<Transform2D> transforms) {

    if (minCoords == null) {
      minCoords = new Vector2D(-1.6, -1);
    }
    if (maxCoords == null) {
      maxCoords = new Vector2D(1.6, 1);
    }
    if (transforms == null) {
      transforms = new ArrayList<>();
      Complex point = new Complex(-.74543, .11301);
      transforms.add(new JuliaTransform(point, 1));
      transforms.add(new JuliaTransform(point, -1));
    } 

    return new ChaosGameDescription(minCoords, maxCoords, transforms);
  }


  /**
   * Creates default Barnsley Fern ChaosGameDescription.
   *
   * @return ChaosGameDescription
   */
  private ChaosGameDescription barnsleyDescription() {
    
    List<Transform2D> transforms = new ArrayList<>();
    transforms.add(new AffineTransform2D(new Matrix2x2(0, 0, 0, 0.16), 
        new Vector2D(0, 0)));
    transforms.add(new AffineTransform2D(new Matrix2x2(0.85, 0.04, -0.04, 0.85), 
        new Vector2D(0, 1.6)));
    transforms.add(new AffineTransform2D(new Matrix2x2(0.2, -0.26, 0.23, 0.22), 
        new Vector2D(0, 1.6)));
    transforms.add(new AffineTransform2D(new Matrix2x2(-0.15, 0.28, 0.26, 0.24), 
        new Vector2D(0, 0.44)));

    Vector2D minCoords = new Vector2D(-2.65, 0);
    Vector2D maxCoords = new Vector2D(2.65, 10);
    return new ChaosGameDescription(minCoords, maxCoords, transforms);
  }
  

  /**
   * Creates ChaosGameDescription based on Affine2D transform.
   *
   * @return ChaosGameDescription
   */
  private ChaosGameDescription affineDescription(Vector2D minCoords,
                                                 Vector2D maxCoords,
                                                 List<Transform2D> transforms) {
    return new ChaosGameDescription(minCoords, maxCoords, transforms);
  }

  /**
   * Creates a default Sierpinski Triangle ChaosGameDescription.
   *
   * @return a ChaosGameDescription
   */
  private ChaosGameDescription sierpinskiDescription() {
    

    List<Transform2D> transforms = new ArrayList<>();
    transforms.add(new AffineTransform2D(new Matrix2x2(0.5, 0, 0, 0.5), 
        new Vector2D(0, 0)));
    transforms.add(new AffineTransform2D(new Matrix2x2(0.5, 0, 0, 0.5), 
        new Vector2D(0.5, 0)));
    transforms.add(new AffineTransform2D(new Matrix2x2(0.5, 0, 0, 0.5), 
        new Vector2D(0.25, 0.5)));

    Vector2D minCoords = new Vector2D(0, 0);
    Vector2D maxCoords = new Vector2D(1, 1);
    return new ChaosGameDescription(minCoords, maxCoords, transforms);
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
      transformType = "Sierpinski";
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
      transforms.add(new JuliaTransform(point, -1));

    } else {
      // TODO: Custom exception
      throw new IllegalArgumentException("Invalid file content");
    }
    
    return createDescription(transformType, minCoords, maxCoords, transforms);
  }

  /**
   * Parses coordinates from a line in the file.
   * // TODO: Move parse methods into a separate builder class.
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
   * Parses an affine transformation from a line in the file.
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
}
