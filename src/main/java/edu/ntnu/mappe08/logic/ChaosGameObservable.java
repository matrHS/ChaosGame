package edu.ntnu.mappe08.logic;

public interface ChaosGameObservable {
  /**
   * Subscribes an observer
   *
   * @param observer Observer to add
   */
  void addObserver(ChaosGameObserver observer);
  /**
   * Unsubscribes an observer
   *
   * @param observer Observer to remove
   */
  void removeObserver(ChaosGameObserver observer);
  /**
   * Notifies all observers
   */
  void notifyObservers();
}
