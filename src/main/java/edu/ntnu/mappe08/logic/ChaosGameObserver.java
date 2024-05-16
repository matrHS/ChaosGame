package edu.ntnu.mappe08.logic;

/**
 * Represents the chaos game observer.
 * Should be implemented by any "subscriber" classes
 */
public interface ChaosGameObserver {
  
  /**
   * Updates the observer.
   */
  void update();
}
