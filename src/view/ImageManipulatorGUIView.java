package view;

import controller.GUIController;

/**
 * Interface defining the contract for an Image Manipulator GUI view.
 */
public interface ImageManipulatorGUIView {

  /**
   * Adds features to the GUI view using the provided GUIController.
   *
   * @param features The GUIController instance providing the features.
   */
  void addFeatures(GUIController features);

  /**
   * Sets the image in the main panel of the GUI.
   *
   * @param image An array representing the image.
   */
  void setImageInPanel(String[] image);

  /**
   * Sets the histogram in the histogram panel of the GUI.
   *
   * @param image An array representing the histogram.
   */
  void setHistogramInPanel(String[] image);

  /**
   * Displays an error message to the user in the GUI.
   *
   * @param errorMessage The error message to be displayed.
   */
  void displayErrorMessage(String errorMessage);

  // Example method that was commented out
  //void setImageAndHistogram(String imageName);
}

