package edu.ntnu.mappe08.gui;

import edu.ntnu.mappe08.entity.Vector2D;
import edu.ntnu.mappe08.logic.ChaosCanvas;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * Represents the GUI for the Chaos Game.
 */
public class ChaosGameMainPage extends Application {
  
  ChaosGameController controller;
  BorderPane borderPane;
  
  /**
   * Starts the javafx GUI.
   * 
   * @param stage the stage to start.
   */
  @Override
  public void start(Stage stage) {
    this.controller = new ChaosGameController(this);
    
    stage.setTitle("My Application");
    borderPane = new BorderPane();
    ChaosCanvas canvas = controller.getBarnsley(1000, 800, 1000000);

    
    MenuBar menuBar = createMenus();
    borderPane.setTop(menuBar);
    
    updateImage(getTransformImage(canvas));
    
    
    Scene scene = new Scene(borderPane, 1200, 600);
    stage.setScene(scene);
    stage.show();
  }

  public void updateImage(ImageView image) {
    // TODO: Refactor into a more sophisticated solution for swapping images.
    borderPane.setCenter(image);
  }
  
  public MenuBar createMenus() {

    // File menu
    Menu fileMenu = new Menu("File");

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
    
    fileMenu.getItems().addAll(openTransform, saveTransform);
    fileMenu.getItems().add(new SeparatorMenuItem());
    fileMenu.getItems().add(exitApp);
    
    
    MenuBar menuBar = new MenuBar();
    menuBar.getMenus().addAll(fileMenu);
    return menuBar;
  }
  
  /**
   * Creates and gets an image based on a chaos canvas.
   * @param canvas
   * @return
   */
  public ImageView getTransformImage(ChaosCanvas canvas) {
    WritableImage chaosCanvas = new WritableImage(canvas.getWidth(), canvas.getHeight());
    PixelWriter pixelWriter = chaosCanvas.getPixelWriter();
    for (int i = 0; i < canvas.getHeight(); i++) {
      for (int j = 0; j < canvas.getWidth(); j++) {
        if (canvas.getPixel(new Vector2D(i, j)) == 0) {
          pixelWriter.setColor(j, i, Color.BLACK);
        } else {
          pixelWriter.setColor(j, i, Color.RED);
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
