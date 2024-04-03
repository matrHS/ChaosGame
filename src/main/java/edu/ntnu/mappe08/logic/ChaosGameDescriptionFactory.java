package edu.ntnu.mappe08.logic;


import edu.ntnu.mappe08.entity.Complex;
import edu.ntnu.mappe08.entity.Matrix2x2;
import edu.ntnu.mappe08.entity.Vector2D;
import java.util.ArrayList;
import java.util.List;

/**
 * Factory for creating ChaosGameDescriptions.
 * // TODO: Refactor to not be hardcoded.
 */
public class ChaosGameDescriptionFactory {


  // TODO: Refactor to use enum instead of string.

  /**
   * Creates a ChaosGameDescription based on the type.
   *
   * @param type the type of chaos game description to create
   * @return a ChaosGameDescription
   */
  public ChaosGameDescription createDescription (String type) {
    ChaosGameDescription description = null;

    switch (type) {

      case "Sierpinski":
        description = SierpinskiDescription();
        break;

      case "Barnsley":
        description = BarnsleyDescription();
        break;

      case "Julia":
        description = JuliaDescription();
        break;

      default:
        return null;
    }
    return description;
  }


  /**
   * Creates a Julia set ChaosGameDescription.
   * @return a ChaosGameDescription
   */
  private ChaosGameDescription JuliaDescription() {

    Vector2D minCoords = new Vector2D(-1.6, -1);
    Vector2D maxCoords = new Vector2D(1.6, 1);
    List<Transform2D> transforms = new ArrayList<>();
    Complex point = new Complex(-.74543, .11301);
    transforms.add(new JuliaTransform(point, 1));
    transforms.add(new JuliaTransform(point, -1));

    return new ChaosGameDescription(minCoords, maxCoords, transforms);
  }


  /**
   * Creates a Barnsley Fern ChaosGameDescription.
   * @return a ChaosGameDescription
   */
  private ChaosGameDescription BarnsleyDescription() {

    Vector2D minCoords = new Vector2D(-2.65 , 0);
    Vector2D maxCoords = new Vector2D(2.65, 10);
    List<Transform2D> transforms = new ArrayList<>();
    transforms.add(new AffineTransform2D(new Matrix2x2(0, 0, 0, 0.16), new Vector2D(0, 0)));
    transforms.add(new AffineTransform2D(new Matrix2x2(0.85, 0.04, -0.04, 0.85), new Vector2D(0, 1.6)));
    transforms.add(new AffineTransform2D(new Matrix2x2(0.2, -0.26, 0.23, 0.22), new Vector2D(0, 1.6)));
    transforms.add(new AffineTransform2D(new Matrix2x2(-0.15, 0.28, 0.26, 0.24), new Vector2D(0, 0.44)));

    return new ChaosGameDescription(minCoords, maxCoords, transforms);
  }
  

  /**
   * Creates a Sierpinski Triangle ChaosGameDescription.
   * @return a ChaosGameDescription
   */
  private ChaosGameDescription SierpinskiDescription() {

    Vector2D minCoords = new Vector2D(0, 0);
    Vector2D maxCoords = new Vector2D(1, 1);
    List<Transform2D> transforms = new ArrayList<>();
    transforms.add(new AffineTransform2D(new Matrix2x2(0.5, 0, 0, 0.5), new Vector2D(0, 0)));
    transforms.add(new AffineTransform2D(new Matrix2x2(0.5, 0, 0, 0.5), new Vector2D(0.25, 0.5)));
    transforms.add(new AffineTransform2D(new Matrix2x2(0.5, 0, 0, 0.5), new Vector2D(0.5, 0)));

    return new ChaosGameDescription(minCoords, maxCoords, transforms);
  }
}
