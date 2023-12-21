package model;

/**
 * An abstract class providing a basic implementation of image histogram generation.
 */
public abstract class AbstractImageHistogram {

  /**
   * Two-dimensional array to store histogram values for each color channel (red, green, blue).
   * The first index represents the color channel (0 for red, 1 for green, 2 for blue),
   * and the second index represents the intensity level (0 to 255).
   */
  protected int[][] histogramValues;

  /**
   * Constructs an AbstractImageHistogram object and generates the histogram values based on the
   * RGB values of the pixels in the provided source image.
   *
   * @param srcImg The source image for which the histogram is generated.
   */
  protected AbstractImageHistogram(Image srcImg) {
    // Initialize the histogramValues array with three color channels and 256 intensity levels.
    this.histogramValues = new int[3][256];

    // Loop through each pixel in the source image to populate the histogramValues array.
    for (int y = 0; y < srcImg.getHeight(); y++) {
      for (int x = 0; x < srcImg.getWidth(); x++) {
        Pixel currentPixel = srcImg.getPixelRGB(x, y);

        // Increment the histogram count for the red, green, and blue channels.
        histogramValues[0][currentPixel.getRed()]++;
        histogramValues[1][currentPixel.getGreen()]++;
        histogramValues[2][currentPixel.getBlue()]++;
      }
    }
  }

  /**
   * To get the histogram values of the image.
   *
   * @return 2D array of histogram values
   */
  public int[][] getHistogramValues() {
    return histogramValues;
  }
}
