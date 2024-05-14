package edu.ntnu.mappe08.ui;

import edu.ntnu.mappe08.entity.Matrix2x2;
import edu.ntnu.mappe08.entity.Vector2D;
import edu.ntnu.mappe08.logic.AffineTransform2D;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class AffineTransformDialog extends Dialog<AffineTransform2D> {

  /**
   * Mode of the dialog.
   */
  public enum Mode {
    NEW, EDIT
  }
  
  private AffineTransform2D existingAffineTransform = null;
  private final Mode mode;
  
  /**
   * Create instance of the AffineTransform dialog.
   */
  public AffineTransformDialog() {
    super();
    this.mode = Mode.NEW;
    createContent();
  }
  
  /**
   * Create instance of the AffineTransform dialog.
   *
   * @param affineTransform2D AffineTransform2D instance to edit
   */
  public AffineTransformDialog(AffineTransform2D affineTransform2D) {
    super();
    this.mode = Mode.EDIT;
    this.existingAffineTransform = affineTransform2D;
    createContent();
  }
  
  /**
   * Create content for the dialog.
   */
  private void createContent() {
    GridPane gridPane = new GridPane();

    // Adds stylesheet and icon to the dialog.
    this.getDialogPane().getStylesheets().add(getClass().getResource(
        "/stylesheet.css").toExternalForm());

    if (this.getDialogPane().getScene().getWindow() instanceof Stage) {
      Stage stage = (Stage) this.getDialogPane().getScene().getWindow();
      stage.getIcons().add(new Image(
          this.getClass().getResource("/images/icon64x64.png").toExternalForm()));
    }




    getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
    getDialogPane().setContent(gridPane);Label matrixValues = new Label("Matrix");
    gridPane.addRow(0, matrixValues);
    
    Label matrixA00Label = new Label("a00");
    TextField matrixA00 = new TextField();
    addNumericListener(matrixA00);

    Label matrixA01Label = new Label("a01");
    TextField matrixA01 = new TextField();
    addNumericListener(matrixA01);
    gridPane.addRow(1, matrixA00Label, matrixA00, matrixA01Label, matrixA01);
    
    Label matrixA10Label = new Label("a10");
    TextField matrixA10 = new TextField();
    addNumericListener(matrixA10);

    Label matrixA11Label = new Label("a11");
    TextField matrixA11 = new TextField();
    addNumericListener(matrixA11);
    gridPane.addRow(2, matrixA10Label, matrixA10, matrixA11Label, matrixA11);
    
    Label vectorValues = new Label("Vector");
    gridPane.addRow(3, vectorValues);
    
    Label vectorX0Label = new Label("x0");
    TextField vectorX0 = new TextField();
    addNumericListener(vectorX0);

    Label vectorX1Label = new Label("x1");
    TextField vectorX1 = new TextField();
    addNumericListener(vectorX1);
    gridPane.addRow(4, vectorX0Label, vectorX0, vectorX1Label, vectorX1);

    if (mode == Mode.EDIT) {
      matrixA00.setText(existingAffineTransform.getMatrix().getA00() + "");
      matrixA01.setText(existingAffineTransform.getMatrix().getA01() + "");
      matrixA10.setText(existingAffineTransform.getMatrix().getA10() + "");
      matrixA11.setText(existingAffineTransform.getMatrix().getA11() + "");
      vectorX0.setText(existingAffineTransform.getVector().getX0() + "");
      vectorX1.setText(existingAffineTransform.getVector().getX1() + "");
    }
    
    setResultConverter(dialogButton -> {
      AffineTransform2D affineTransform2D = null;
      if (dialogButton == ButtonType.OK) {
        try {
          affineTransform2D = new AffineTransform2D(new Matrix2x2(
              Double.parseDouble(matrixA00.getText()),
              Double.parseDouble(matrixA01.getText()),
              Double.parseDouble(matrixA10.getText()),
              Double.parseDouble(matrixA11.getText())),
              new Vector2D(Double.parseDouble(vectorX0.getText()),
                  Double.parseDouble(vectorX1.getText())));
        } catch (NumberFormatException e) {
          Alert alert = new Alert(Alert.AlertType.ERROR);
          alert.setTitle("Error");
          alert.setHeaderText("Invalid input");
          alert.setContentText("Please enter valid numbers for all fields");
          alert.showAndWait();
        }
      }
      return affineTransform2D;
      }
      );

  }

  private static void addNumericListener(TextField textField) {
    textField.textProperty().addListener((observable, oldValue, newValue) -> {
      try {
        if (!newValue.isEmpty()) {
          Double.parseDouble(newValue);
        } else if(newValue.isEmpty()) {
          textField.setText("");
        } else {
          textField.setText(oldValue);
        }
      } catch (NumberFormatException e) {
        // Throws exception if inputted value is not valid
        textField.setText(oldValue);
      }
      });
  }

}
