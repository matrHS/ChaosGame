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
        (width - 1) / (minCoords.getY() - maxCoords.getY()),
        0,
        (height - 1) / (maxCoords.getX() - minCoords.getX())),

        new Vector2D(((width - 1) * maxCoords.getY()) / (maxCoords.getY() - minCoords.getY()),
            ((height - 1) * minCoords.getX()) / (minCoords.getX() - maxCoords.getX())));
  }


  /**
   * Method to get the pixel at a given point.
   *
   * @param point the point to get the pixel from.
   */
  private int getPixel(Vector2D point) {
    return this.canvas[(int) point.getX()][(int) point.getY()];
    // TODO: Implement method.
  }

  /**
   * Method to set the pixel at a given point.
   *
   * @param point the point to set the pixel at
   */
  private void setPixel(Vector2D point) {
    this.canvas[(int) point.getX()][(int) point.getY()] = 1;
    // TODO: Implement method.
  }

  /**
   * Get the canvas array.
   * @return the canvas array.
   */
  private int[][] getCanvasArray() {
    return this.canvas;
  }

}
