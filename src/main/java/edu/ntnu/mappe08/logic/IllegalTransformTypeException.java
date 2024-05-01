package edu.ntnu.mappe08.logic;

/**
 * Exception class for illegal transform types.
 */
public class IllegalTransformTypeException extends RuntimeException {
  
    /**
    * Constructor for the exception.
    *
    * @param message message to be displayed
    */
    public IllegalTransformTypeException(String message) {
      super(message);
    }
}
