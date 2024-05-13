package edu.ntnu.mappe08.ui;


import edu.ntnu.mappe08.entity.Complex;
import edu.ntnu.mappe08.entity.Matrix2x2;
import edu.ntnu.mappe08.entity.Vector2D;
import edu.ntnu.mappe08.logic.AffineTransform2D;
import edu.ntnu.mappe08.logic.ChaosGameObserver;
import edu.ntnu.mappe08.logic.JuliaTransform;
import edu.ntnu.mappe08.logic.Transform2D;
import edu.ntnu.mappe08.logic.TransformTypes;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

/**
 * Factory class for creating TransformControls.
 */
public class TransformControlsFactory implements ChaosGameObserver {

  MainPageController controller;
  private ObservableList<Transform2D> transformListWrapper;
  private TextField a10;
  private TextField a11;
  private TextField a00;
  private TextField a01;
  private Vector2D maxCoords;


  /**
   * Constructor for TransformControlsFactory.
   *
   * @param controller MainPageController
   */
  public TransformControlsFactory(MainPageController controller) {
    this.controller = controller;
  }



  /**
   * Creates GridPane with controls for current transformation type.
   *
   * @return GridPane with controls for current transformation type
   */
  public GridPane getTransformControls(TransformTypes transformType) {
    GridPane controls = new GridPane();

    switch (transformType) {
      case AFFINE2D:
        controls = createAffineControls();
        break;
      case JULIA:
        controls = createJuliaControls();
        break;
      default:
        break;
    }
    controls.setHgap(4);
    controls.setVgap(4);
    controls.maxWidth(200);
    controls.minWidth(200);
    return controls;
  }


  /**
   * Creates GridPane with controls for Julia transformation.
   *
   * @return GridPane with controls for Julia transformation
   */
  private GridPane createJuliaControls() {
    GridPane parentControls = new GridPane();
    
    
    parentControls.addRow(0, createMinMaxCoordsControls());
    parentControls.addRow(1, new Label("Julia Controls"));

    
    
    Slider realSlider = new Slider(-2, 2, 0);
    realSlider.setShowTickLabels(true);
    realSlider.setShowTickMarks(true);
    realSlider.setMajorTickUnit(1);
    
    Slider imaginarySlider = new Slider(-2, 2, 0);
    imaginarySlider.setShowTickLabels(true);
    imaginarySlider.setShowTickMarks(true);
    imaginarySlider.setMajorTickUnit(1);

    TextField realField = new TextField();
    realField.setText(realSlider.getValue() + "");
    realField.setOnAction(e -> {
      realSlider.setValue(Double.parseDouble(realField.getText()));
    });

    TextField imaginaryField = new TextField();
    imaginaryField.setText(imaginarySlider.getValue() + "");
    imaginaryField.setOnAction(e -> {
      imaginarySlider.setValue(Double.parseDouble(imaginaryField.getText()));
    });
    
    realSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
      if (controller.getChaosGame().getTransformType().equals(TransformTypes.JULIA)) {
        updateJuliaParams(realSlider, imaginarySlider);
        realField.setText(String.format("%.2f",newValue));
      }
    });
    
    imaginarySlider.valueProperty().addListener((observable, oldValue, newValue) -> {
      if (controller.getChaosGame().getTransformType().equals(TransformTypes.JULIA)) {
        updateJuliaParams(realSlider, imaginarySlider);
        imaginaryField.setText(String.format("%.2f",newValue));
      }
    });

    Transform2D loadedTransform = controller.getChaosGame().getDescription()
        .getTransforms().getFirst();
    if (loadedTransform instanceof JuliaTransform) {
      JuliaTransform juliaTransform = (JuliaTransform) loadedTransform;
      realSlider.setValue(juliaTransform.getPointC().getRealPart());
      imaginarySlider.setValue(juliaTransform.getPointC().getImaginaryPart());
    }
    
    Label emptyLabel = new Label("");
    Label pointcLabel = new Label("Point C");

    GridPane juliaControls = new GridPane();
    
    juliaControls.addRow(0, pointcLabel, realSlider, imaginarySlider);
    juliaControls.addRow(1, emptyLabel, realField, imaginaryField);
    
    parentControls.addRow(2, juliaControls);

    return parentControls;
  }

  /**
   * Updates Julia transform parameters.
   *
   * @param realSlider Slider for real part
   * @param imaginarySlider Slider for imaginary part
   */
  private void updateJuliaParams(Slider realSlider, Slider imaginarySlider) {
    List<Transform2D> transforms = new ArrayList<>();
    transforms.add(new JuliaTransform(new Complex(realSlider.getValue(), 
        imaginarySlider.getValue()), 1));
    transforms.add(new JuliaTransform(new Complex(realSlider.getValue(), 
        imaginarySlider.getValue()), -1));
    controller.getChaosGame().setTransforms(transforms);
  }

  /**
   * Creates GridPane with controls for min and max coordinates.
   *
   * @return GridPane with controls for min and max coordinates
   */
  private GridPane createMinMaxCoordsControls() {
    
    
    a10 = new TextField();
    a10.setText(controller.getCurrentDescription().getMaxCoords().getX0() + "");

    a11 = new TextField();
    a11.setText(controller.getCurrentDescription().getMaxCoords().getX1() + "");

    a10.setOnAction(e -> {
      controller.getChaosGame().setMaxCoords(new Vector2D(Double.parseDouble(a10.getText()),
          Double.parseDouble(a11.getText())));
    });
    a11.setOnAction(e -> {
      controller.getChaosGame().setMaxCoords(new Vector2D(Double.parseDouble(a10.getText()),
          Double.parseDouble(a11.getText())));
    });
    
    Label maxCoords = new Label("Upper Right");
    GridPane controls = new GridPane();
    controls.addRow(0, maxCoords, a10, a11);

    
    a00 = new TextField();
    a00.setText(controller.getCurrentDescription().getMinCoords().getX0() + "");
    a01 = new TextField();
    a01.setText(controller.getCurrentDescription().getMinCoords().getX1() + "");

    a00.setOnAction(e -> {
      controller.getChaosGame().setMinCoords(new Vector2D(Double.parseDouble(a00.getText()),
          Double.parseDouble(a01.getText())));
    });
    a01.setOnAction(e -> {
      controller.getChaosGame().setMinCoords(new Vector2D(Double.parseDouble(a00.getText()),
          Double.parseDouble(a01.getText())));
    });

    Label a0 = new Label("Lower Left");
    controls.addRow(1, a0, a00, a01);
    
    return controls;
  }
  
  /**
   * Creates GridPane with controls for affine transformation.
   *
   * @return GridPane with controls for affine transformation
   */
  private GridPane createAffineControls() {
    GridPane parentControls = new GridPane();
    GridPane transformControls = new GridPane();
    

    transformControls.setHgap(4);
    transformControls.setVgap(4);
    parentControls.addRow(0, createMinMaxCoordsControls());
    
    Label affineLabel = new Label("Affine Controls");
    parentControls.addRow(1, affineLabel);
    
    // Help from copilot choosing the SimpleStingProperty datatype for the return type.
    TableColumn<AffineTransform2D, String> a00Col = new TableColumn<>("a00");
    a00Col.setMinWidth(25);
    a00Col.setCellValueFactory(cellData -> {
      AffineTransform2D transform = cellData.getValue();
      Matrix2x2 matrix = transform.getMatrix();
      return new SimpleStringProperty(matrix.getA00() + "");
    });

    TableColumn<AffineTransform2D, String> a01Col = new TableColumn<>("a01");
    a01Col.setMinWidth(25);
    a01Col.setCellValueFactory(cellData -> {
      AffineTransform2D transform = cellData.getValue();
      Matrix2x2 matrix = transform.getMatrix();
      return new SimpleStringProperty(matrix.getA01() + "");
    });

    TableColumn<AffineTransform2D, String> a10Col = new TableColumn<>("a10");
    a10Col.setMinWidth(25);
    a10Col.setCellValueFactory(cellData -> {
      AffineTransform2D transform = cellData.getValue();
      Matrix2x2 matrix = transform.getMatrix();
      return new SimpleStringProperty(matrix.getA10() + "");
    });

    TableColumn<AffineTransform2D, String> a11Col = new TableColumn<>("a11");
    a11Col.setMinWidth(25);
    a11Col.setCellValueFactory(cellData -> {
      AffineTransform2D transform = cellData.getValue();
      Matrix2x2 matrix = transform.getMatrix();
      return new SimpleStringProperty(matrix.getA11() + "");
    });

    TableColumn<AffineTransform2D, String> x0Col = new TableColumn<>("x0");
    x0Col.setMinWidth(25);
    x0Col.setCellValueFactory(cellData -> {
      AffineTransform2D transform = cellData.getValue();
      Vector2D vector = transform.getVector();
      return new SimpleStringProperty(vector.getX0() + "");
    });

    TableColumn<AffineTransform2D, String> x1Col = new TableColumn<>("x1");
    x1Col.setMinWidth(25);
    x1Col.setCellValueFactory(cellData -> {
      AffineTransform2D transform = cellData.getValue();
      Vector2D vector = transform.getVector();
      return new SimpleStringProperty(vector.getX1() + "");
    });

    TableView transformTable = new TableView();
    transformTable.setItems(this.getTransformListWrapper(controller.getCurrentDescription()
        .getTransforms()));
    transformTable.getColumns().addAll(a00Col, a01Col, a10Col, a11Col, x0Col, x1Col);
    transformTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);

    // Add listener for double-click on row
    transformTable.setOnMousePressed(mouseEvent -> {
      if (mouseEvent.isPrimaryButtonDown() && (mouseEvent.getClickCount() == 2)) {
        int selectedIndex = transformTable.getSelectionModel().getSelectedIndex();
        AffineTransform2D selectedLiterature = (AffineTransform2D) transformTable.getItems()
            .get(selectedIndex);
        controller.doEditAffineTransform(selectedLiterature);
        transformTable.setItems(this.getTransformListWrapper(this.controller.getCurrentDescription()
            .getTransforms()));
      }
    });
    
    parentControls.addRow(2, transformTable);

    Button addTransform = new Button("Add Transform");
    addTransform.setOnAction(e -> {
      controller.doAddAffineTransform();
      transformTable.setItems(this.getTransformListWrapper(this.controller.getCurrentDescription()
          .getTransforms()));
    });
    
    Button removeTransform = new Button("Remove Transform");
    removeTransform.setOnAction(e -> {
      int selectedIndex = transformTable.getSelectionModel().getSelectedIndex();
      controller.doRemoveAffineTransform(selectedIndex);
      transformTable.setItems(this.getTransformListWrapper(this.controller.getCurrentDescription()
          .getTransforms()));
    });

    GridPane buttonControls = new GridPane();
    buttonControls.addRow(0, addTransform, removeTransform);
    parentControls.addRow(3, buttonControls);

    return parentControls;
  }


  /**
   * Returns an ObservableList for table of Transform2D.
   *
   * @param transforms List of Transform2D.
   * @return ObservableList of Transform2D.
   */
  private ObservableList<Transform2D> getTransformListWrapper(List<Transform2D> transforms) {
    transformListWrapper
        = FXCollections.observableArrayList(transforms);
    return transformListWrapper;
  }

  @Override
  public void update() {
    a00.setText(String.format("%.2f", controller.getCurrentDescription().getMinCoords().getX0()));
    a01.setText(String.format("%.2f", controller.getCurrentDescription().getMinCoords().getX1()));
    a10.setText(String.format("%.2f", controller.getCurrentDescription().getMaxCoords().getX0()));
    a11.setText(String.format("%.2f", controller.getCurrentDescription().getMaxCoords().getX1()));
  }
}
