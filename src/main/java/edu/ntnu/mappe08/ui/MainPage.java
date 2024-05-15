package edu.ntnu.mappe08.ui;

import edu.ntnu.mappe08.entity.Vector2D;
import edu.ntnu.mappe08.logic.ChaosCanvas;
import edu.ntnu.mappe08.logic.TransformTypes;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Bounds;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * Represents the GUI for the Chaos Game.
 */
public class MainPage extends Application {
  
  private static final int MAX_ITERATIONS = 9999999;
  private boolean setupDone;
  Bounds centerCanvasBounds;
  MainPageController controller;
  BorderPane borderPane;
  StackPane centerBox;
  ImageView loadedImage;
  HBox transformControlsParent;
  GridPane transformControls;
  TransformControlsFactory transformControlsFactory;
  
  /**
   * Starts the javafx GUI.
   *
   * @param stage the stage to start.
   */
  @Override
  public void start(Stage stage) {
    this.controller = new MainPageController(this);
    this.transformControlsFactory = new TransformControlsFactory(this.controller);
    
    stage.setTitle("Chaos Game");
    
    borderPane = new BorderPane();
    transformControlsParent = new HBox();
    transformControls = new GridPane();
    
    transformControlsParent.getChildren().add(transformControls);
    transformControlsParent.setPadding(new javafx.geometry.Insets(4));
    borderPane.setLeft(transformControlsParent);
    

    
    MenuBar menuBar = createMenus();
    createMenus().getStyleClass().add("menu-bar");
    borderPane.setTop(menuBar);

    
    
    // Gets the screen size and sets the scene to be 200 pixels less than the screen size.
    Rectangle2D screenSize = Screen.getPrimary().getBounds();
    Scene scene = new Scene(borderPane,  screenSize.getWidth()-200, screenSize.getHeight()-200);
    
    centerBox = new StackPane();
    centerBox.setMinHeight(0);
    centerBox.setMinWidth(0);
    borderPane.setCenter(centerBox);

    loadedImage = new ImageView();
    centerBox.getChildren().add(loadedImage);
    loadedImage.fitWidthProperty().bind(centerBox.widthProperty());
    loadedImage.fitHeightProperty().bind(centerBox.heightProperty());
    
    
    loadedImage.setOnScroll(e -> {
      Vector2D mousePos = new Vector2D(e.getX(), e.getY());
      controller.doZoom(mousePos, e.getDeltaY());
    });
    
    loadedImage.setOnMouseDragged(e -> {
      Vector2D mousePos = new Vector2D(e.getX(), e.getY());
      controller.doDrag(mousePos);
    });
    
    
    
    
    ChangeListener<Number> stageSizeListener = (obs, oldVal, newVal) -> {

      // prevents null pointer on first run as the property listener for height and width triggers
      // an event once window opens
      if (setupDone) {
        updateBounds();
      }

    };
    centerBox.heightProperty().addListener(stageSizeListener);
    centerBox.widthProperty().addListener(stageSizeListener);



    scene.getStylesheets().add(getClass().getResource("/stylesheet.css").toExternalForm());
    
    stage.setScene(scene);
    stage.show();
    
    // Takes dimensions of visible center bounds to allow initialization of the controller
    centerCanvasBounds = borderPane.getCenter().getBoundsInLocal();
    controller.initialize();
    
    
    this.setupDone = true;
    
  }

  
  /**
   * Creates the bottom menu with options for all transformations.
   *
   * @return HBox with common options for all transformations.
   */
  private HBox createBottomOptions() {
    HBox bottomBar = new HBox();
    bottomBar.getStyleClass().add("HBox-bottom");
    Label iterationsLabel = new Label("Iterations");
    TextField iterations = new TextField();
    iterations.setText(controller.getIterations() + "");
    
    // Catch changes in text field and ensure that inputted value has to be an integer.
    iterations.textProperty().addListener((observable, oldValue, newValue) -> {
      try {
        if (!newValue.isEmpty() && Integer.parseInt(newValue) <= MAX_ITERATIONS) {
          controller.setIterations(Integer.parseInt(newValue));
//          controller.doRedrawImage(this.centerCanvasBounds);
        } else if(newValue.isEmpty()) {
          iterations.setText("");
        } else {
          iterations.setText(oldValue);
        }
      } catch (NumberFormatException e) {
        // Throws exception if inputted value is not an integer
        iterations.setText(oldValue);
      } 
    });
    
    bottomBar.setSpacing(4);
    bottomBar.getChildren().addAll(iterationsLabel, iterations);
    return bottomBar;
  }


  /**
   * Updates the image in the center of the screen.
   *
   * @param image image to update to.
   */
  public void updateImage(ImageView image) {
    loadedImage.setImage(image.getImage());
    
  }

  /**
   * Creates the menu bar at the top of the screen.
   * Connects events to menu items.
   *
   * @return Fully constructed menu bar.
   */
  public MenuBar createMenus() {

    // File menu
    MenuItem saveTransform = new MenuItem("Save");
    saveTransform.setOnAction(e -> {
      controller.saveTransform();
    });
    MenuItem openTransform = new MenuItem("Open");
    openTransform.setOnAction(e -> {
      controller.openTransform();
    });
    MenuItem exitApp = new MenuItem("Exit");
    exitApp.setOnAction(e -> {
      controller.exitApp();
    });

    Menu fileMenu = new Menu("File");
    fileMenu.getItems().addAll(openTransform, saveTransform);
    fileMenu.getItems().add(new SeparatorMenuItem());
    fileMenu.getItems().add(exitApp);
    
    // Fractals menu
    MenuItem juliaDefault = new MenuItem("Julia");
    juliaDefault.setOnAction(e -> {
      controller.doChangeImage(controller.getJulia((int) this.centerCanvasBounds.getHeight(),
          (int) this.centerCanvasBounds.getWidth()));
      setTransformControls(transformControlsFactory.getTransformControls(TransformTypes.JULIA));
    });
    MenuItem barnsleyDefault = new MenuItem("Barnsley");
    barnsleyDefault.setOnAction(e -> {
      controller.doChangeImage(controller.getBarnsley((int) this.centerCanvasBounds.getHeight(),
          (int) this.centerCanvasBounds.getWidth()));
      setTransformControls(transformControlsFactory.getTransformControls(TransformTypes.AFFINE2D));
    });
    MenuItem sierpinskiDefault = new MenuItem("Sierpinski");
    sierpinskiDefault.setOnAction(e -> {
      controller.doChangeImage(controller.getSierpinski((int) this.centerCanvasBounds.getHeight(),
          (int) this.centerCanvasBounds.getWidth()));
      setTransformControls(transformControlsFactory.getTransformControls(TransformTypes.AFFINE2D));
    });
    MenuItem snowflakeDefault = new MenuItem("Snowflake");
    snowflakeDefault.setOnAction(e -> {
      controller.doChangeImage(controller.getSnowflake((int) this.centerCanvasBounds.getHeight(),
          (int) this.centerCanvasBounds.getWidth()));
      setTransformControls(transformControlsFactory.getTransformControls(TransformTypes.AFFINE2D));
    });
    MenuItem emptyAffine = new MenuItem("Custom Affine");
    emptyAffine.setOnAction(e -> {
      controller.doChangeImage(controller.getEmptyAffine((int) this.centerCanvasBounds.getHeight(),
          (int) this.centerCanvasBounds.getWidth()));
      setTransformControls(transformControlsFactory.getTransformControls(TransformTypes.AFFINE2D));
    });
    Menu fractalsMenu = new Menu("Fractals");
    fractalsMenu.getItems().addAll(juliaDefault, barnsleyDefault, sierpinskiDefault, snowflakeDefault);
    fractalsMenu.getItems().add(new SeparatorMenuItem());
    fractalsMenu.getItems().addAll(emptyAffine);
    
    MenuBar menuBar = new MenuBar();
    menuBar.getMenus().addAll(fileMenu);
    menuBar.getMenus().add(fractalsMenu);
    
    return menuBar;
  }
  
  /**
   * Sets the transform controls for the page.
   *
   * @param controls controls to set.
   */
  public void setTransformControls(GridPane controls) {
    borderPane.setBottom(createBottomOptions());
    transformControlsParent.getChildren().remove(transformControls);
    this.transformControls = controls;
    transformControlsParent.getChildren().add(transformControls);
  }


  /**
   * Updates the bounds of the center canvas.
   */
  public void updateBounds() {
    this.centerCanvasBounds = borderPane.getCenter().getBoundsInLocal();
    // Temporarily removed redraw to get observer working first with values. will be added back.
    controller.doRedrawImage(this.centerCanvasBounds);
  }
  
  
  
  /**
   * Creates and gets an image based on a chaos canvas.
   *
   * @param canvas ChaosCanvas to generate image from.
   * @return Generated image.
   */
  public ImageView drawImageFromChaosCanvas(ChaosCanvas canvas) {
    WritableImage chaosCanvas = new WritableImage(canvas.getWidth(), canvas.getHeight());
    PixelWriter pixelWriter = chaosCanvas.getPixelWriter();
    int[][] drawnCanvas = canvas.getCanvasArray();
    for (int i = 0; i < canvas.getHeight(); i++) {
      for (int j = 0; j < canvas.getWidth(); j++) {
        if (canvas.getPixel(new Vector2D(i, j)) == 0) {
          drawnCanvas[i][j] = 0;
          pixelWriter.setColor(j, i, Color.WHITE);
        } else {
          int color = (0xFF << 24);
          color += (drawnCanvas[i][j] << 16);
          color += (0x00 << 8);
          color += (0x00 << 0);
              
          pixelWriter.setArgb(j, i, color);
        }
      }
    }
    
    ImageView imageView = new ImageView(chaosCanvas);


    return imageView;
  }
  
  
  
  /**
   * Method for starting the javafx GUI without using maven directly.
   *
   * @param args command line arguments.
   */
  public static void main(String[] args) {
    launch(args);
  }
}
