package edu.ntnu.mappe08.logic;

/**
 * Exception class for value or parsing errors.
 */
public class ValueParseException extends Exception {

  /**
   * Constructor for the exception.
   *
   * @param message message to be displayed
   */
  public ValueParseException(String message) {
    super(message);
  }
}
