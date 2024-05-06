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
  public ChaosCanvas(int height, int width, Vector2D minCoords, Vector2D maxCoords) {
    this.width = width;
    this.height = height;
    this.minCoords = minCoords;
    this.maxCoords = maxCoords;
    this.canvas = new int[height][width];
    this.transformCoordsToIndices = new AffineTransform2D(new Matrix2x2(
        0,
        (height - 1) / (minCoords.getX1() - maxCoords.getX1()),
        (width - 1) / (maxCoords.getX0() - minCoords.getX0()),
        0),

        new Vector2D(((height - 1) * maxCoords.getX1()) / (maxCoords.getX1() - minCoords.getX1()),
            ((width - 1) * minCoords.getX0()) / (minCoords.getX0() - maxCoords.getX0())));
  }


  /**
   * Returns pixel at a given point.
   *
   * @param point the point to get the pixel from.
   * @return the pixel at the given point.
   */
  public int getPixel(Vector2D point) {
    if (point == null) {
      throw new IllegalArgumentException("Passed vector can not be null");
    }
    if (point.getX0() < 0 
        || point.getX0() >= this.height 
        || point.getX1() < 0 
        || point.getX1() >= this.width) {
      throw new IllegalArgumentException("Point is outside the canvas");
    }
    
    int i = (int) Math.round(point.getX0());
    int j = (int) Math.round(point.getX1());
    
    return this.getCanvasArray()[i][j];
  }

  /**
   * Sets pixel at a given point.
   *
   * @param point the point to set the pixel at.
   */
  public void putPixel(Vector2D point) {
    if (point == null) {
      throw new IllegalArgumentException("Passed vector can not be null");
    }

    Vector2D test2 = transformCoordsToIndices.transform(point);
    // no checks for pixels outside the canvas to allow "jumping" within the fractal for zoom etc.
    int i = (int) Math.round(test2.getX0());
    int j = (int) Math.round(test2.getX1());
    if (i >= 0 && i < this.height && j >= 0 && j < this.width) {
      this.canvas[i][j] = 1;
    }
    
  }

  /**
   * Returns the canvas array.
   *
   * @return the canvas array.
   */
  public int[][] getCanvasArray() {
    return this.canvas;
  }

  /**
   * Clear the canvas.
   */
  public void clear() {
    for (int i = 0; i < this.width; i++) {
      for (int j = 0; j < this.height; j++) {
        this.canvas[i][j] = 0;
      }
    }
  }

  /**
   * Returns the width of the canvas.
   *
   * @return the width of the canvas
   */
  public int getWidth() {
    return this.width;
  }

  /**
   * Returns the height of the canvas.
   *
   * @return the height of the canvas
   */
  public int getHeight() {
    return this.height;
  }

  /**
   * Returns the minimum coordinates of the canvas.
   *
   * @return the minimum coordinates of the canvas
   */
  public Vector2D getMinCoords() {
    return this.minCoords;
  }

  /**
   * Returns the maximum coordinates of the canvas.
   *
   * @return the maximum coordinates of the canvas
   */
  public Vector2D getMaxCoords() {
    return this.maxCoords;
  }

}
