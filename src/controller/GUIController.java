package controller;

/**
 * Interface for a GUI controller that handles image manipulation operations.
 */
public interface GUIController {

  /**
   * Loads an image from the specified file path.
   *
   * @param imagePath The file path of the image to be loaded.
   */
  void loadImage(String imagePath);

  /**
   * Saves the current image to the specified file path with the given image name.
   *
   * @param imagePath The file path where the image will be saved.
   * @param imageName The name of the image file.
   */
  void saveImage(String imagePath, String imageName);

  /**
   * Previews a specific image manipulation operation with the given parameters.
   *
   * @param operation       The type of image manipulation operation to preview.
   * @param widthPercentage The width percentage for split view operations.
   */
  void preview(String operation, double widthPercentage);

  /**
   * Reverts the current image to its original state.
   */
  void revert();

  /**
   * Flips the current image horizontally.
   */
  void horizontalFlip();

  /**
   * Flips the current image vertically.
   */
  void verticalFlip();

  /**
   * Visualizes a specific component of the current image.
   *
   * @param componentName The name of the component to visualize.
   */
  void visualizeComponent(String componentName);

  /**
   * Applies a blur effect to the current image.
   */
  void blur();

  /**
   * Applies a sharpening effect to the current image.
   */
  void sharpen();

  /**
   * Converts the current image to grayscale.
   */
  void greyscale();

  /**
   * Applies a sepia tone effect to the current image.
   */
  void sepia();

  /**
   * Compresses the current image with the specified compression percentage.
   *
   * @param compressPercentage The percentage by which to compress the image.
   */
  void compress(double compressPercentage);

  /**
   * Performs color correction on the current image.
   */
  void colorCorrect();

  /**
   * Adjusts the levels of the current image.
   *
   * @param b The black point level.
   * @param m The midpoint level.
   * @param w The white point level.
   */
  void adjustLevels(int b, int m, int w);
}
