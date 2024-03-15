package edu.ntnu.mappe08;


import edu.ntnu.mappe08.entity.Matrix2x2;
import edu.ntnu.mappe08.entity.Vector2D;
import edu.ntnu.mappe08.logic.ChaosCanvas;
import edu.ntnu.mappe08.logic.ChaosGame;
import edu.ntnu.mappe08.logic.ChaosGameDescription;
import edu.ntnu.mappe08.logic.ChaosGameFileHandler;


public class Main {
  
  public static void main(String[] args){
    ChaosGameFileHandler chaosGameFileHandler = new ChaosGameFileHandler();
    ChaosGameDescription chaosGameDescription = chaosGameFileHandler.buildChaosGameDescriptionFromFile("data/testAffine.csv");
    ChaosGame chaosGame = new ChaosGame(chaosGameDescription, 60, 60);
    chaosGame.runSteps(100000);
    ChaosCanvas canvas = chaosGame.getCanvas();
    
    printCanvas(canvas);
  }
  
  public static void printCanvas(ChaosCanvas c){
    int[][] canvasArray = c.getCanvasArray();
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < c.getHeight(); i++) {
      for (int j = 0; j < c.getWidth(); j++) {
        if (c.getPixel(new Vector2D(i, j)) == 0) {
          sb.append(" ");
        } else {
          sb.append("*");
        }
      }
      sb.append("\n");
    }
    System.out.println(sb.toString());
  }
}
