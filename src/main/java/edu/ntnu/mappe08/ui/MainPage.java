package edu.ntnu.mappe08.ui;

import edu.ntnu.mappe08.entity.Matrix2x2;
import edu.ntnu.mappe08.entity.Vector2D;
import edu.ntnu.mappe08.logic.AffineTransform2D;
import edu.ntnu.mappe08.logic.ChaosCanvas;
import edu.ntnu.mappe08.logic.Transform2D;
import edu.ntnu.mappe08.logic.TransformTypes;
import java.util.List;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Bounds;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
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
  
  private static final int MAX_ITERATIONS = 9999999;
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


    scene.getStylesheets().add(getClass().getResource("/stylesheet.css").toExternalForm());
    stage.setScene(scene);
    stage.show();

    controller.initialize();
    
    
    
    
    //centerCanvasBounds = borderPane.getCenter().getBoundsInLocal();
    
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
      transformControlsParent.getChildren().remove(transformControls);
      transformControls = transformControlsFactory.getTransformControls(TransformTypes.AFFINE2D);
      transformControlsParent.getChildren().add(transformControls);
    });
    MenuItem juliaDefault = new MenuItem("Julia");
    juliaDefault.setOnAction(e -> {
      controller.doChangeImage(controller.getJulia((int) this.centerCanvasBounds.getHeight(),
          (int) this.centerCanvasBounds.getWidth()));
      transformControlsParent.getChildren().remove(transformControls);
      transformControls = transformControlsFactory.getTransformControls(TransformTypes.JULIA);
      transformControlsParent.getChildren().add(transformControls);
    });
    MenuItem sierpinskiDefault = new MenuItem("Sierpinski");
    sierpinskiDefault.setOnAction(e -> {
      controller.doChangeImage(controller.getSierpinski((int) this.centerCanvasBounds.getHeight(),
          (int) this.centerCanvasBounds.getWidth()));
      transformControlsParent.getChildren().remove(transformControls);
      transformControls = transformControlsFactory.getTransformControls(TransformTypes.AFFINE2D);
      transformControlsParent.getChildren().add(transformControls);
    });
    Menu fractalsMenu = new Menu("Fractals");
    fractalsMenu.getItems().addAll(barnsleyDefault, juliaDefault, sierpinskiDefault);
    
    MenuBar menuBar = new MenuBar();
    menuBar.getMenus().addAll(fileMenu);
    menuBar.getMenus().add(fractalsMenu);
    
    return menuBar;
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
