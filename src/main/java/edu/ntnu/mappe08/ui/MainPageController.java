package edu.ntnu.mappe08.ui;

import edu.ntnu.mappe08.logic.ChaosCanvas;
import edu.ntnu.mappe08.logic.ChaosGame;
import edu.ntnu.mappe08.logic.ChaosGameDescription;
import edu.ntnu.mappe08.logic.ChaosGameDescriptionFactory;
import edu.ntnu.mappe08.logic.ChaosGameFileHandler;
import java.io.File;
import java.util.List;
import javafx.application.Platform;
import javafx.geometry.Bounds;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

/**
 * Represents the controller for the Chaos Game.
 * Should be interchangeable with GUI and CLI.
 */
public class MainPageController {

  private MainPage mainPage;
  private ChaosGameFileHandler fileHandler;
  private ChaosGameDescriptionFactory descriptionFactory;
  private ChaosGameDescription currentDescription;
  
  private int iterations = 100000;

  /**
   * Creates an instance of ChaosGameController.
   */
  public MainPageController(MainPage mainPage) {
    this.mainPage = mainPage;
    this.fileHandler = new ChaosGameFileHandler();
    this.descriptionFactory = new ChaosGameDescriptionFactory();
  }

  /**
   * Creates an instance of ChaosGameController for use with CLI.
   */
  public MainPageController() {
    this.fileHandler = new ChaosGameFileHandler();
    this.descriptionFactory = new ChaosGameDescriptionFactory();
  }

  /**
   * Builds and simulates ChaosCanvas for any transformation type.
   *
   * @param height height of canvas.
   * @param width width of canvas.
   * @param iterations number of iterations.
   * @param transformType type of transformation.
   * @return ChaosCanvas.
   */
  private ChaosCanvas getChaosCanvas(int height, int width, int iterations, String transformType) {
    currentDescription = this.descriptionFactory.createDescription(transformType);
    ChaosGame chaosGame = new ChaosGame(currentDescription, height, width);
    chaosGame.runSteps(iterations);
    return chaosGame.getCanvas();
  }

  /**
   * Creates a canvas based on file.
   *
   * @param height Height of canvas in pixels.
   * @param width Width of canvas in pixels.
   * @param iterations Number of iterations.
   * @param filePath Filepath for transform.
   * @return Simulated ChaosCanvas.
   */
  public ChaosCanvas getCustomCanvas(int height, int width, int iterations,String filePath) {
    List<String> strings = fileHandler.readFromFile(filePath);
    this.currentDescription = descriptionFactory.buildChaosGameDescription(strings);
    ChaosGame chaosGame = new ChaosGame(currentDescription, height, width);
    chaosGame.runSteps(iterations);
    return chaosGame.getCanvas();
  }

  /**
   * Returns a ChaosCanvas based on the current loaded description.
   *
   * @param height height of canvas.
   * @param width width of canvas.
   * @param iterations number of iterations.
   * @return ChaosCanvas with new dimensions.
   */
  private ChaosCanvas getRedrawnCanvas(int height, int width, int iterations) {
    ChaosGame chaosGame = new ChaosGame(currentDescription, height, width);
    chaosGame.runSteps(iterations);
    return chaosGame.getCanvas();
  }

  /**
   * Returns a Sierpinski ChaosCanvas.
   *
   * @param height height of canvas.
   * @param width width of canvas.
   * @return ChaosCanvas.
   */
  public ChaosCanvas getSierpinski(int height, int width) {
    return getChaosCanvas(height, width, iterations, "Sierpinski");
  }

  /**
   * Returns a Barnsley ChaosCanvas.
   *
   * @param height height of canvas.
   * @param width width of canvas.
   * @return ChaosCanvas.
   */
  public ChaosCanvas getBarnsley(int height, int width) {
    return getChaosCanvas(height, width, iterations, "Barnsley");
  }

  /**
   * Returns a Julia ChaosCanvas.
   *
   * @param height height of canvas.
   * @param width width of canvas.
   * @return ChaosCanvas.
   */
  public ChaosCanvas getJulia(int height, int width) {
    return getChaosCanvas(height, width, iterations, "Julia");
  }

  /**
   * Saves the current transformation to a file using file chooser dialog.
   */
  public void saveTransform() {
    FileChooser fileChooser = new FileChooser();
    
    fileChooser.setTitle("Save Transformation");
    fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
    fileChooser.setInitialFileName("transformation.csv");
    File selectedFile = fileChooser.showSaveDialog(null);
    if (selectedFile != null) {
      fileHandler.writeToFile(this.descriptionFactory.createDescription("Julia"),
          selectedFile.getAbsolutePath());
    }
  }

  /**
   * Opens a transformation from a file using file chooser dialog.
   */
  public void openTransform() {
    // TODO: Consider refactoring file chooser to a separate method for both save and open.
    FileChooser fileChooser = new FileChooser();

    fileChooser.setTitle("Open Transformation");
    fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
    File selectedFile = fileChooser.showOpenDialog(null);
    if (selectedFile != null) {
      doChangeImage(this.getCustomCanvas((int) mainPage.centerCanvasBounds.getWidth(),
          (int) mainPage.centerCanvasBounds.getHeight(),
          iterations, 
          selectedFile.getAbsolutePath()));
    }
  }

  /**
   * Exits the application.
   */ 
  public void exitApp() {
    // TODO: Fill later with dialog to confirm exit.
    Platform.exit();
  }

  /**
   * Changes the image on the main page.
   *
   * @param newCanvas new canvas to draw.
   */
  public void doChangeImage(ChaosCanvas newCanvas) {
    this.mainPage.updateImage(this.mainPage.drawImageFromChaosCanvas(newCanvas));
  }

  /**
   * Redraws the image on the main page using passed bounds.
   * 
   * @param newBounds new bounds to redraw image.
   */
  public void doRedrawImage(Bounds newBounds) {
    ChaosCanvas canvas = getRedrawnCanvas((int) newBounds.getHeight(), (int) newBounds.getWidth(), iterations);
    this.doChangeImage(canvas);
  }
  
  /**
   * Sets the number of iterations used for calculations.
   *
   * @param iterations number of iterations.
   */
  public void setIterations(int iterations) {
    this.iterations = iterations;
  }
  
  /**
   * Gets the number of iterations used for calculations.
   *
   * @return number of iterations.
   */
  public int getIterations() {
    return iterations;
  }
  
}
