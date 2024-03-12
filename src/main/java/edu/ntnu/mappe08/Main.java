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
    ChaosGameDescription chaosGameDescription = chaosGameFileHandler.buildChaosGameDescriptionFromFile("data/testBarnsley.csv");
    ChaosGame chaosGame = new ChaosGame(chaosGameDescription, 100, 100);
    chaosGame.runSteps(1000000);
    ChaosCanvas canvas = chaosGame.getCanvas();
    
    printCanvas(canvas);
  }
  
  public static void printCanvas(ChaosCanvas c){
    int[][] canvasArray = c.getCanvasArray();
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < c.getHeight(); i++) {
      for (int j = 0; j < c.getWidth(); j++) {
        if (c.getPixel(new Vector2D(j, i)) == 0) {
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
