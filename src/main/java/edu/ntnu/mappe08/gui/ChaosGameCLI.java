package edu.ntnu.mappe08.gui;


import edu.ntnu.mappe08.entity.Vector2D;
import edu.ntnu.mappe08.logic.ChaosCanvas;
import java.util.Scanner;


/**
 * Represents the Chaos Game CLI.
 */
public class ChaosGameCLI {


  private ChaosGameController controller;

  private static final int SIERPINSKI = 1;
  private static final int BARNSLEY = 2;
  private static final int EXIT = 9;
  private static final int MAX_MENU_CHOICE = 9;

  /**
   * Creates an instance of ChaosGameCLI.
   */
  public ChaosGameCLI() {
    this.controller = new ChaosGameController();
  }

  /**
   * Starts the Chaos Game CLI.
   */
  public void start() {

    boolean finished = false;
    while (!finished) {
      printWelcomeScreen();
      displayMenu();
      int selectedMenu = getUsersMenuChoice();
      if (!executeMenuChoice(selectedMenu)) {
        finished = true;
      }
    }
    System.out.println("Thank you for using this application!");
  }

  /**
   * Prints the welcome screen.
   */
  private void printWelcomeScreen() {
    System.out.println("Welcome to the Chaos Game CLI");
  }

  /**
   * Displays the menu.
   */
  private void displayMenu() {
    System.out.println("1. Sierpinski Triangle");
    System.out.println("2. Barnsley Fern");
    System.out.println("9. Exit");
  }

  /**
   * Gets the users menu choice.
   * @return the users menu choice.
   */
  private int getUsersMenuChoice() {
    int selectedMenu;
    Scanner userInput = new Scanner(System.in);
    System.out.print("Please enter your a menu choice between 1 and " + MAX_MENU_CHOICE + ": ");

    if (userInput.hasNextInt()) {
      selectedMenu = userInput.nextInt();
    } else {
      selectedMenu = -1;
    }
    return selectedMenu;
  }

  /**
   * Executes the menu choice.
   * @param selectedMenu to execute.
   * @return {@code true} if the application should continue running, {@code false} otherwise.
   */
  private boolean executeMenuChoice(int selectedMenu) {
    boolean running = true;
    int[] parameters;
    switch (selectedMenu) {

      case SIERPINSKI:
        parameters = setParameters();
        printCanvas(controller.getSierpinski(parameters[0], parameters[1], parameters[2]));
        break;

      case BARNSLEY:
        parameters = setParameters();
        printCanvas(controller.getBarnsley(parameters[0], parameters[1], parameters[2]));
        break;

      case EXIT:
        running = false;
        break;

      default:
        System.out.println("Invalid menu choice");
    }
    return running;
  }

  /**
   * Prints the canvas.
   * @param canvas to print.
   */
  private void printCanvas(ChaosCanvas canvas) {
    int[][] canvasArray = canvas.getCanvasArray();
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < canvas.getHeight(); i++) {
      for (int j = 0; j < canvas.getWidth(); j++) {
        if (canvas.getPixel(new Vector2D(i, j)) == 0) {
          sb.append(" ");
        } else {
          sb.append("*");
        }
      }
      sb.append("\n");
    }
    System.out.println(sb.toString());
  }


  /**
   * Sets the parameters for the canvas.
   * @return the parameters for the canvas.
   */
  public int[] setParameters() {
    Scanner userInput = new Scanner(System.in);
    System.out.print("Please enter the height of the canvas: ");
    int height = Integer.parseInt(userInput.nextLine());
    System.out.print("Please enter the width of the canvas: ");
    int width = Integer.parseInt(userInput.nextLine());
    System.out.print("Please enter the number of iterations: ");
    int iterations = Integer.parseInt(userInput.nextLine());

    return new int[]{height, width, iterations};
  }
}
