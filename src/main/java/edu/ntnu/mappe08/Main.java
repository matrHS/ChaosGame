package edu.ntnu.mappe08;


import edu.ntnu.mappe08.entity.Matrix2x2;
import edu.ntnu.mappe08.logic.ChaosGameDescription;
import edu.ntnu.mappe08.logic.ChaosGameFileHandler;

public class Main {
  public static void main(String[] args){
    String testFilePath = "testAffine.csv";
    ChaosGameFileHandler fileHandler = new ChaosGameFileHandler();
    ChaosGameDescription chaosGameDescription = fileHandler.readFromFile(testFilePath);
  }
}
