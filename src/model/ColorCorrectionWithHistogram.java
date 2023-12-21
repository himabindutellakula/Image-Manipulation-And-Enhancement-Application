package model;

/**
 * This class performs color correction on an image using histogram analysis. It adjusts the RGB
 * channels of each pixel to align their peaks with the average peak.
 */
public class ColorCorrectionWithHistogram extends AbstractImageHistogram {

  /**
   * Constructs a ColorCorrectionWithHistogram object with the given source image.
   *
   * @param srcImg The source image for color correction.
   */
  public ColorCorrectionWithHistogram(Image srcImg) {
    super(srcImg);
  }

  /**
   * Performs color correction on the input image based on histogram analysis.
   *
   * @param srcImg The source image to be color-corrected.
   * @return A new Image object representing the color-corrected image.
   */
  public Image colorCorrect(Image srcImg) {
    Image colorCorrectedImage = new Image(srcImg.getWidth(), srcImg.getHeight());
    int redPeak = calculateMeaningfulPeak(this.histogramValues[0]);
    int greenPeak = calculateMeaningfulPeak(this.histogramValues[1]);
    int bluePeak = calculateMeaningfulPeak(this.histogramValues[2]);

    int averagePeak = (redPeak + greenPeak + bluePeak) / 3;

    for (int y = 0; y < srcImg.getHeight(); y++) {
      for (int x = 0; x < srcImg.getWidth(); x++) {
        Pixel currentPixel = srcImg.getPixelRGB(x, y);
        int red = currentPixel.getRed();
        int green = currentPixel.getGreen();
        int blue = currentPixel.getBlue();

        int redOffset = averagePeak - redPeak;
        int greenOffset = averagePeak - greenPeak;
        int blueOffset = averagePeak - bluePeak;

        red = Math.max(0, Math.min(255, red + redOffset));
        green = Math.max(0, Math.min(255, green + greenOffset));
        blue = Math.max(0, Math.min(255, blue + blueOffset));

        colorCorrectedImage.setPixelRGB(x, y, new Pixel(red, green, blue));
      }
    }
    return colorCorrectedImage;
  }

  /**
   * Calculates the meaningful peaks of each channel.
   *
   * @param histogram Histogram of the particular channel of source Image.
   * @return peak of the particular channel of image.
   */
  private int calculateMeaningfulPeak(int[] histogram) {
    int meaningfulPeak = 0;
    int maxCount = 0;
    for (int i = 10; i < 245; i++) {
      if (histogram[i] > maxCount) {
        maxCount = histogram[i];
        meaningfulPeak = i;
      }
    }
    return meaningfulPeak;
  }
}
