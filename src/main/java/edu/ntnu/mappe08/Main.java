package edu.ntnu.mappe08;


import edu.ntnu.mappe08.Entity.Matrix2x2;

public class Main {
  public static void main(String[] args){
    System.out.println("Main");
    Matrix2x2 matrix = new Matrix2x2(1, 2, 3, 4);
    System.out.println(matrix.getA00());
  }
}
