package model;

/**
 * This interface extends the ImageManipulatorModel and provides additional advanced image
 * manipulation functionalities like compress, split-view of filters, histogram visualizations,
 * color corrections and level adjustments.
 */
public interface AdvancedImageManipulatorModel extends ImageManipulatorModel {

  /**
   * Compresses the specified image by the given percentage and saves the result to the destination
   * image.
   *
   * @param percentage    The compression percentage (between 0 and 100).
   * @param srcImageName  The name of the source image.
   * @param destImageName The name of the destination image after compression.
   * @throws IllegalArgumentException when Percentage is negative
   */
  public void compress(double percentage, String srcImageName, String destImageName)
      throws IllegalArgumentException;

  /**
   * Generates a split view of the specified image based on the given operation and width
   * percentage. The resulting image is saved to the destination image. The operation can be
   * blurred, sharpen, sepia, greyscale, color correction, or levels adjustment.
   *
   * @param commandLine     The command that needs a split view
   * @param widthPercentage The percentage of the width for placing the splitting line.
   * @throws IllegalArgumentException when widthPercentage is negative
   */
  void splitViewNew(String commandLine,
      double widthPercentage) throws IllegalArgumentException;

  /**
   * Generates an image representing the histogram of the given image. The histogram image will have
   * a size of 256x256 and display the histograms for the red, green, and blue channels as line
   * graphs.
   *
   * @param srcImageName  The name of the source image.
   * @param destImageName The name of the destination image representing the histogram.
   */
  public void generateHistogram(String srcImageName, String destImageName);

  /**
   * Color-corrects the specified image by aligning the meaningful peaks of its histogram and saves
   * the result to the destination image.
   *
   * @param srcImageName  The name of the source image.
   * @param destImageName The name of the destination image after color correction.
   */
  public void colorCorrect(String srcImageName, String destImageName);

  /**
   * Adjusts the levels of the specified image based on the provided black, mid, and white values.
   * The resulting image is saved to the destination image.
   *
   * @param b             The black value for level adjustment (0 to 255).
   * @param m             The mid-value for level adjustment (0 to 255).
   * @param w             The white value for level adjustment (0 to 255).
   * @param srcImageName  The name of the source image.
   * @param destImageName The name of the destination image after level adjustment.
   * @throws IllegalArgumentException when b, m and w are not in between 0 and 255 and values should
   *                                  be b < m < w.
   */
  public void adjustLevels(int b, int m, int w, String srcImageName, String destImageName)
      throws IllegalArgumentException;

}

