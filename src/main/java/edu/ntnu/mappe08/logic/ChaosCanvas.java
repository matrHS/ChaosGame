package edu.ntnu.mappe08.logic;
import edu.ntnu.mappe08.entity.Matrix2x2;
import edu.ntnu.mappe08.entity.Vector2D;

/**
 * Represents the chaos canvas.
 */
public class ChaosCanvas {


  private  int[][] canvas;
  private  int width;
  private  int height;
  private Vector2D minCoords;
  private Vector2D maxCoords;

  private AffineTransform2D transformCoordsToIndices;

  /**
   * Constructor for the chaos canvas.
   *
   * @param width the width of the canvas
   * @param height the height of the canvas
   * @param minCoords the minimum coordinates of the canvas
   * @param maxCoords the maximum coordinates of the canvas
   */
  public ChaosCanvas(int width, int height, Vector2D minCoords, Vector2D maxCoords) {
    this.width = width;
    this.height = height;
    this.minCoords = minCoords;
    this.maxCoords = maxCoords;
    this.canvas = new int[width][height];
    this.transformCoordsToIndices = new AffineTransform2D(new Matrix2x2(
        0,
        (height - 1) / (minCoords.getX1() - maxCoords.getX1()),
        (width - 1) / (maxCoords.getX0() - minCoords.getX0()),
        0),

        new Vector2D(((height - 1) * maxCoords.getX1()) / (maxCoords.getX1() - minCoords.getX1()),
            ((width - 1) * minCoords.getX0()) / (minCoords.getX0() - maxCoords.getX0())));
  }


  /**
   * Method to get the pixel at a given point.
   *
   * @param point the point to get the pixel from.
   * @return the pixel at the given point.
   */
  public int getPixel(Vector2D point) {
    if (point == null) {
      throw new IllegalArgumentException("Passed vector can not be null");
    }
    if (point.getX0() < 0 || point.getX0() >= this.width || point.getX1() < 0 || point.getX1() >= this.height) {
      throw new IllegalArgumentException("Point is outside the canvas");
    }
    
    int i = (int) Math.round(point.getX0());
    int j = (int) Math.round(point.getX1());
    
    return this.getCanvasArray()[i][j];
  }

  /**
   * Method to set the pixel at a given point.
   *
   * @param point the point to set the pixel at.
   */
  public void putPixel(Vector2D point) {
    if (point == null) {
      throw new IllegalArgumentException("Passed vector can not be null");
    }
    

    Vector2D test2 = transformCoordsToIndices.transform(point);
    int i = (int) Math.round(test2.getX0());
    int j = (int) Math.round(test2.getX1());
    if (i >= 0 && i < this.width && j >= 0 && j < this.height) {
      this.canvas[i][j] = 1;
    }
    
  }

  /**
   * Get the canvas array.
   * @return the canvas array.
   */
  public int[][] getCanvasArray() {
    return this.canvas;
  }

  /**
   * Method to clear the canvas.
   */
  public void clear() {
    for (int i = 0; i < this.width; i++) {
      for (int j = 0; j < this.height; j++) {
        this.canvas[i][j] = 0;
      }
    }
  }

  /**
   * Method to get the width of the canvas.
   *
   * @return the width of the canvas
   */
  public int getWidth() {
    return this.width;
  }

  /**
   * Method to get the height of the canvas.
   *
   * @return the height of the canvas
   */
  public int getHeight() {
    return this.height;
  }

  /**
   * Method to get the minimum coordinates of the canvas.
   *
   * @return the minimum coordinates of the canvas
   */
  public Vector2D getMinCoords() {
    return this.minCoords;
  }

  /**
   * Method to get the maximum coordinates of the canvas.
   *
   * @return the maximum coordinates of the canvas
   */
  public Vector2D getMaxCoords() {
    return this.maxCoords;
  }

}
