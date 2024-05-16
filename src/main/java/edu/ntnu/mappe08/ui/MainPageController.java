package edu.ntnu.mappe08.ui;

import edu.ntnu.mappe08.entity.Vector2D;
import edu.ntnu.mappe08.logic.AffineTransform2D;
import edu.ntnu.mappe08.logic.ChaosCanvas;
import edu.ntnu.mappe08.logic.ChaosGame;
import edu.ntnu.mappe08.logic.ChaosGameDescription;
import edu.ntnu.mappe08.logic.ChaosGameDescriptionFactory;
import edu.ntnu.mappe08.logic.ChaosGameFileHandler;
import edu.ntnu.mappe08.logic.ChaosGameObserver;
import edu.ntnu.mappe08.logic.IllegalTransformTypeException;
import edu.ntnu.mappe08.logic.Transform2D;
import edu.ntnu.mappe08.logic.TransformTypes;
import edu.ntnu.mappe08.logic.ValueParseException;
import java.io.File;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.geometry.Bounds;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.FileChooser;

/**
 * Represents the controller for the Chaos Game.
 * Should be interchangeable with GUI and CLI.
 */
public class MainPageController implements ChaosGameObserver {

  private MainPage mainPage;
  private ChaosGameFileHandler fileHandler;
  private ChaosGameDescriptionFactory descriptionFactory;
  private ChaosGameDescription currentDescription;
  private ChaosGame chaosGame;
  private Logger logger;
  
  private int iterations = 100000;

  private Vector2D oldMousePos = new Vector2D(0, 0);

  /**
   * Creates an instance of ChaosGameController.
   */
  public MainPageController(MainPage mainPage) {
    this.mainPage = mainPage;
    this.fileHandler = new ChaosGameFileHandler();
    this.descriptionFactory = new ChaosGameDescriptionFactory();
    this.logger = Logger.getLogger(MainPageController.class.getName());
    
  }

  /**
   * Creates an instance of ChaosGameController for use with CLI.
   */
  public MainPageController() {
    this.fileHandler = new ChaosGameFileHandler();
    this.descriptionFactory = new ChaosGameDescriptionFactory();
    // Legacy code for CLI to work
    this.chaosGame = new ChaosGame(descriptionFactory.createDescription(TransformTypes.SIERPINSKI), 
        1, 1);
    this.logger = Logger.getLogger(MainPageController.class.getName());
  }
  
  /**
   * Initializes the ChaosGameController.
   */
  public void initialize() {
    this.chaosGame = new ChaosGame(descriptionFactory.createDescription(TransformTypes.SIERPINSKI), 
        (int) mainPage.centerCanvasBounds.getHeight(), 
        (int) mainPage.centerCanvasBounds.getWidth());
    this.chaosGame.addObserver(this);
    this.chaosGame.addObserver(mainPage.transformControlsFactory);
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
  private ChaosCanvas getChaosCanvas(int height, int width, int iterations, 
                                     TransformTypes transformType) {
    currentDescription = this.descriptionFactory.createDescription(transformType);
    chaosGame.reconfigureChaosGame(currentDescription, height, width);
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
  public ChaosCanvas getCustomCanvas(int height, int width, int iterations, String filePath) {
    try {
      List<String> strings = fileHandler.readFromFile(filePath);
      this.currentDescription = descriptionFactory.buildChaosGameDescription(strings);
      chaosGame.reconfigureChaosGame(currentDescription, height, width);
      chaosGame.runSteps(iterations);
    } catch (ValueParseException e) {
      Alert alert = new Alert(Alert.AlertType.ERROR);
      alert.setTitle("Error");
      alert.setContentText("Could not read transformation from file.");
      alert.showAndWait();
      logger.warning("Could not read transformation from file.");
    }
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
    chaosGame.reconfigureChaosGame(currentDescription, height, width);
    chaosGame.runSteps(iterations);
    return chaosGame.getCanvas();
  }

  /**
   * Returns the current ChaosGame.
   *
   * @return ChaosGame.
   */
  public ChaosGame getChaosGame() {
    return chaosGame;
  }

  /**
   * Returns a Sierpinski ChaosCanvas.
   *
   * @param height height of canvas.
   * @param width width of canvas.
   * @return ChaosCanvas.
   */
  public ChaosCanvas getSierpinski(int height, int width) {
    return getChaosCanvas(height, width, iterations, TransformTypes.SIERPINSKI);
  }

  /**
   * Returns Affine ChaosCanvas with 1 empty transform.
   *
   * @param height height of canvas.
   * @param width width of canvas.
   * @return ChaosCanvas.
   */
  public ChaosCanvas getEmptyAffine(int height, int width) {
    return getChaosCanvas(height, width, iterations, TransformTypes.NONE);
  }

  /**
   * Returns a Barnsley ChaosCanvas.
   *
   * @param height height of canvas.
   * @param width width of canvas.
   * @return ChaosCanvas.
   */
  public ChaosCanvas getBarnsley(int height, int width) {
    return getChaosCanvas(height, width, iterations, TransformTypes.BARNSLEY);
  }

  public ChaosCanvas getSnowflake(int height, int width) {
    return getChaosCanvas(height, width, iterations, TransformTypes.SNOWFLAKE);
  }

  /**
   * Returns a Julia ChaosCanvas.
   *
   * @param height height of canvas.
   * @param width width of canvas.
   * @return ChaosCanvas.
   */
  public ChaosCanvas getJulia(int height, int width) {
    return getChaosCanvas(height, width, iterations, TransformTypes.JULIA);
  }

  /**
   * Saves the current transformation to a file using file chooser dialog.
   */
  public void saveTransform() {
    FileChooser fileChooser = new FileChooser();
    
    fileChooser.setTitle("Save Transformation");
    fileChooser.getExtensionFilters()
        .add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
    fileChooser.setInitialFileName("transformation.csv");
    File selectedFile = fileChooser.showSaveDialog(null);
    try {
      if (selectedFile != null) {
        fileHandler.writeToFile(chaosGame.getDescription(),
            selectedFile.getAbsolutePath());
      }
    } catch (ValueParseException e) {
      Alert alert = new Alert(Alert.AlertType.ERROR);
      alert.setTitle("Error");
      alert.setContentText("Could not save transformation to file.");
      alert.showAndWait();
      logger.warning("Could not save transformation to file.");
    }
  }

  /**
   * Opens a transformation from a file using file chooser dialog.
   */
  public void openTransform() {
    FileChooser fileChooser = new FileChooser();

    fileChooser.setTitle("Open Transformation");
    fileChooser.getExtensionFilters()
        .add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
    File selectedFile = fileChooser.showOpenDialog(null);
    if (selectedFile != null) {
      doChangeImage(this.getCustomCanvas((int) mainPage.centerCanvasBounds.getWidth(),
          (int) mainPage.centerCanvasBounds.getHeight(),
          iterations, 
          selectedFile.getAbsolutePath()));
      mainPage.setTransformControls(mainPage.transformControlsFactory
          .getTransformControls(chaosGame.getTransformType()));
    }
  }

  /**
   * Exits the application.
   */ 
  public void exitApp() {
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle("Exit");
    alert.setContentText("Are you sure you want to exit?");
    alert.showAndWait().ifPresent(response -> {
      if (response == ButtonType.OK) {
        Platform.exit();
      }
    });
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
    ChaosCanvas canvas = getRedrawnCanvas((int) newBounds.getHeight(), 
        (int) newBounds.getWidth(), 
        iterations);
    this.doChangeImage(canvas);
  }
  
  /**
   * Sets the number of iterations used for calculations.
   *
   * @param iterations number of iterations.
   */
  public void setIterations(int iterations) {
    this.iterations = iterations;
    update();
  }

  /**
   * Edits an affine transformation.
   *
   * @param transform the transformation to edit.
   */
  public void doEditAffineTransform(AffineTransform2D transform) {
    if (this.getChaosGame().getTransformType() != TransformTypes.AFFINE2D) {
      throw new IllegalTransformTypeException(
          "Cannot edit affine transformation in non affine chaos game");
    }
    AffineTransformDialog dialog = new AffineTransformDialog(transform);
    dialog.setTitle("Edit Affine Transformation");
    Optional<AffineTransform2D> result = dialog.showAndWait();
    result.ifPresent(newTransform -> {
      List<Transform2D> transforms = this.getCurrentDescription().getTransforms();
      if (transforms.getFirst() instanceof AffineTransform2D) {
        transforms.set(transforms.indexOf(transform), newTransform);
        this.getChaosGame().setTransforms(transforms);
      }
    });
    
  }

  /**
   * Adds an affine transformation.
   */
  public void doAddAffineTransform() {
    if (this.getChaosGame().getTransformType() != TransformTypes.AFFINE2D 
        && this.getChaosGame().getTransformType() != TransformTypes.NONE) {
      throw new IllegalTransformTypeException(
          "Cannot add affine transformation to non affine chaos game");
    }
    AffineTransformDialog dialog = new AffineTransformDialog();
    dialog.setTitle("Add Affine Transformation");
    Optional<AffineTransform2D> result = dialog.showAndWait();
    result.ifPresent(transform -> {
      List<Transform2D> transforms = this.getCurrentDescription().getTransforms();
      transforms.add(transform);
      this.getChaosGame().setTransforms(transforms);
    });
  }
  
  /**
   * Removes an affine transformation.
   *
   * @param index index of transformation to remove.
   */
  public void doRemoveAffineTransform(int index) {
    if (this.getChaosGame().getTransformType() != TransformTypes.AFFINE2D) {
      throw new IllegalTransformTypeException(
          "Cannot remove affine transformation from non affine chaos game");
    }
    
    if (index >= 0) {
      List<Transform2D> transforms = this.getCurrentDescription().getTransforms();
      transforms.remove(index);
      this.getChaosGame().setTransforms(transforms);
    } else {
      Alert alert = new Alert(Alert.AlertType.WARNING);
      alert.setTitle("No row selected");
      alert.setContentText("Please select a row to remove.");
      alert.showAndWait();
    }
  }


  /**
   * Pans the canvas based on mouse position.
   * Uses old mouse position to calculate delta for pan.
   *
   * @param mousePos current mouse position.
   */
  public void doDrag(Vector2D mousePos) {
    int maxWidth = (int) mainPage.centerCanvasBounds.getWidth();
    int maxHeight = (int) mainPage.centerCanvasBounds.getHeight();

    // Normalize cursor position in relation to canvas dimensions
    double normX = 1 - mousePos.getX0() / maxWidth;
    double normY = (mousePos.getX1() / maxHeight);
    
    // Calculate the new min and max coordinates in relation to position and zoom level
    Vector2D currentMin = chaosGame.getDescription().getMinCoords();
    Vector2D currentMax = chaosGame.getDescription().getMaxCoords();

    double panXfactor = (currentMax.getX0() - currentMin.getX0());
    double panYfactor = (currentMax.getX1() - currentMin.getX1());
    double newMinX = currentMin.getX0() + (normX - oldMousePos.getX0()) * (panXfactor);
    double newMaxX = currentMax.getX0() + (normX - oldMousePos.getX0()) * (panXfactor);
    double newMinY = currentMin.getX1() + (normY - oldMousePos.getX1()) * (panYfactor);
    double newMaxY = currentMax.getX1() + (normY - oldMousePos.getX1()) * (panYfactor);

    // Updates the min and max coordinates
    chaosGame.setMinCoords(new Vector2D(newMinX, newMinY));
    chaosGame.setMaxCoords(new Vector2D(newMaxX, newMaxY));

    // Sets the old normalized mouse position so that there is a difference between mouse positions.
    // for next cpu cycle, this gives us 2 points to calculate the new min max coords from.
    oldMousePos = new Vector2D(normX, normY);
  }

  /**
   * Sets the old mouse position for dragging purposes.
   * Activated when mouse is pressed on canvas to prevent jagged panning.
   *
   * @param mousePos mouse position.
   */
  public void doSetOldMousePos(Vector2D mousePos) {
    int maxWidth = (int) mainPage.centerCanvasBounds.getWidth();
    int maxHeight = (int) mainPage.centerCanvasBounds.getHeight();

    // Normalize cursor position in relation to canvas dimensions
    double normX = 1 - mousePos.getX0() / maxWidth;
    double normY = (mousePos.getX1() / maxHeight);
    
    this.oldMousePos = new Vector2D(normX, normY);
  }

  /**
   * Zooms in or out on the canvas based on mouse position.
   *
   * @param mousePos mouse position.
   * @param scrollDirection scroll direction.
   */
  public void doZoom(Vector2D mousePos, double scrollDirection) {
    // Gets the canvas width and height
    int maxWidth = (int) mainPage.centerCanvasBounds.getWidth();
    int maxHeight = (int) mainPage.centerCanvasBounds.getHeight();
    
    // Normalize cursor position in relation to canvas dimensions
    double normX = mousePos.getX0() / maxWidth;
    double normY = 1 - (mousePos.getX1() / maxHeight);

    // Calculate new zoom level
    double zoomDirection = Math.signum(scrollDirection) * -1;
    double zoomFactor = 1.1; // Zoom speed
    double newZoomLevel = Math.pow(zoomFactor, zoomDirection);

    // Calculate the new min and max coordinates in relation to position and zoom level
    Vector2D currentMin = chaosGame.getDescription().getMinCoords();
    Vector2D currentMax = chaosGame.getDescription().getMaxCoords();
    double newMinX = currentMin.getX0() + normX 
        * (currentMax.getX0() - currentMin.getX0()) * (1 - newZoomLevel);
    double newMaxX = currentMax.getX0() - (1 - normX) 
        * (currentMax.getX0() - currentMin.getX0()) * (1 - newZoomLevel);
    double newMinY = currentMin.getX1() + normY 
        * (currentMax.getX1() - currentMin.getX1()) * (1 - newZoomLevel);
    double newMaxY = currentMax.getX1() - (1 - normY) 
        * (currentMax.getX1() - currentMin.getX1()) * (1 - newZoomLevel);

    // Updates the min and max coordinates
    chaosGame.setMinCoords(new Vector2D(newMinX, newMinY));
    chaosGame.setMaxCoords(new Vector2D(newMaxX, newMaxY));
  }
  
  /**
   * Gets the number of iterations used for calculations.
   *
   * @return number of iterations.
   */
  public int getIterations() {
    return iterations;
  }

  /**
   * Gets the current description.
   *
   * @return current description.
   */
  public ChaosGameDescription getCurrentDescription() {
    return chaosGame.getDescription();
  }

  @Override
  public void update() {
    doRedrawImage(mainPage.centerCanvasBounds);
  }


  /**
   * Shows a help dialog with instructions.
   */
  public void showHelp() {
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle("Help");
    alert.setHeaderText("Chaos Game Help");
    alert.setContentText("""
            Default fractals can be loaded from the "fractals" menu.
            Custom fractals can be loaded from the "file" menu.
            Loaded fractals can be saved to a file using the "file" menu.
            Depending on the loaded fractal, the controls on the left will change.
            The number of iterations can be changed in the bottom left corner.
            The canvas can be zoomed in and out using the scroll wheel.
            The canvas can be panned by clicking anywhere on the image and dragging.
            """);
    alert.showAndWait();
  }

  /**
   * Shows about dialog with information about the application.
   */
  public void showAbout() {
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle("About");
    alert.setHeaderText("Chaos Game");
    alert.setContentText("""
        Chaos Game is an application developed by Matthew Hunt and Thea Pernille
        as part of the course IDATA2003-2024 at NTNU.
        There are sample fractals included in the "sampleFractals" folder 
        if the user needs more inspiration.
        """);
    alert.showAndWait();
  }

  
}
