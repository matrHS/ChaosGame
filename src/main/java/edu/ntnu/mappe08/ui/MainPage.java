package edu.ntnu.mappe08.ui;

import edu.ntnu.mappe08.entity.Vector2D;
import edu.ntnu.mappe08.logic.ChaosCanvas;
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
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
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
  
  Bounds centerCanvasBounds;
  MainPageController controller;
  BorderPane borderPane;
  StackPane centerBox;
  ImageView loadedImage;
  
  /**
   * Starts the javafx GUI.
   *
   * @param stage the stage to start.
   */
  @Override
  public void start(Stage stage) {
    this.controller = new MainPageController(this);
    
    
    
    stage.setTitle("My Application");
    
    borderPane = new BorderPane();
    GridPane transformControls = getTransformControls("Julia");
    
    borderPane.setLeft(transformControls);
    
    MenuBar menuBar = createMenus();
    borderPane.setTop(menuBar);

    HBox bottomBarOptions = createBottomOptions();
    borderPane.setBottom(bottomBarOptions);
    
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

    
    ChangeListener<Number> stageSizeListener = (obs, oldVal, newVal) -> {
      
      updateBounds();
      
    };
    centerBox.heightProperty().addListener(stageSizeListener);
    centerBox.widthProperty().addListener(stageSizeListener);
    
    
    stage.setScene(scene);
    stage.show();

    //centerCanvasBounds = borderPane.getCenter().getBoundsInLocal();
    
    
    
    
  }

  
  /**
   * Creates the bottom menu with options for all transformations.
   *
   * @return HBox with common options for all transformations.
   */
  private HBox createBottomOptions() {
    HBox bottomBar = new HBox();
    Label iterationsLabel = new Label("Iterations");
    TextField iterations = new TextField();
    iterations.setText(controller.getIterations() + "");
    iterations.setOnAction(e -> {
      controller.setIterations(Integer.parseInt(iterations.getText()));
      controller.doRedrawImage(this.centerCanvasBounds);
    });
    bottomBar.setSpacing(4);
    bottomBar.getChildren().addAll(iterationsLabel, iterations);
    return bottomBar;
  }

  /**
   * Creates GridPane with controls for current transformation type.
   *
   * @return GridPane with controls for current transformation type
   */
  private GridPane getTransformControls(String transformType) {
    // TODO: Refactor into factory.
    GridPane controls = new GridPane();
    
    switch (transformType) {
      case "Affine2D":
        controls = createAffineControls();
        break;
      case "Julia":
        controls = createJuliaControls();
        break;
    }
    controls.setHgap(4);
    controls.setVgap(4);
    controls.maxWidth(200);
    controls.minWidth(200);
    return controls;
  }

  private GridPane createJuliaControls() {
    GridPane controls = new GridPane();
    Label c1 = new Label("Lower Left");
    TextField c1Real = new TextField();
    TextField c1Imaginary = new TextField();
    
    controls.addRow(0, c1, c1Real, c1Imaginary);
    
    Label c2 = new Label("Upper Right");
    TextField c2Real = new TextField();
    TextField c2Imaginary = new TextField();
    
    controls.addRow(1, c2, c2Real, c2Imaginary);
    
    return controls;
  }

  private GridPane createAffineControls() {
    return null;
  }

  public void updateImage(ImageView image) {
    // TODO: Refactor into a more sophisticated solution for swapping images.
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
    MenuItem barnsleyDefault = new MenuItem("Barnsley");
    barnsleyDefault.setOnAction(e -> {
      controller.doChangeImage(controller.getBarnsley((int) this.centerCanvasBounds.getHeight(),
          (int) this.centerCanvasBounds.getWidth()));
    });
    MenuItem juliaDefault = new MenuItem("Julia");
    juliaDefault.setOnAction(e -> {
      controller.doChangeImage(controller.getJulia((int) this.centerCanvasBounds.getHeight(),
          (int) this.centerCanvasBounds.getWidth()));
    });
    MenuItem sierpinskiDefault = new MenuItem("Sierpinski");
    sierpinskiDefault.setOnAction(e -> {
      controller.doChangeImage(controller.getSierpinski((int) this.centerCanvasBounds.getHeight(),
          (int) this.centerCanvasBounds.getWidth()));
    });
    Menu fractalsMenu = new Menu("Fractals");
    fractalsMenu.getItems().addAll(barnsleyDefault, juliaDefault, sierpinskiDefault);
    
    MenuBar menuBar = new MenuBar();
    menuBar.getMenus().addAll(fileMenu);
    menuBar.getMenus().add(fractalsMenu);
    
    return menuBar;
  }
  
  public void updateBounds() {
    this.centerCanvasBounds = borderPane.getCenter().getBoundsInLocal();
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
          drawnCanvas[i][j] += 1;
          int color = (0xFF << 24);
          color += (drawnCanvas[i][j] << 16);
          color += (0x30 << 8);
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
