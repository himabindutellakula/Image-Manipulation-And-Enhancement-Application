package model;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * A class for generating a simple histogram visualization from an input image.
 */
public class SimpleHistogramVisualization extends AbstractImageHistogram {

  /**
   * Constructs a SimpleHistogramVisualization object with the specified source image.
   *
   * @param srcImg The source image for which the histogram is generated.
   */
  public SimpleHistogramVisualization(Image srcImg) {
    super(srcImg);
  }

  /**
   * Generates a histogram and returns it as raw image data.
   *
   * @return A string array representing the raw image data of the generated histogram.
   */
  public String[] generateHistogram() {
    int[] redHistogram = this.histogramValues[0];
    int[] greenHistogram = this.histogramValues[1];
    int[] blueHistogram = this.histogramValues[2];

    // Find the maximum count in the histograms
    int maxCount = 0;
    for (int i = 0; i < 256; i++) {
      if (redHistogram[i] > maxCount) {
        maxCount = redHistogram[i];
      }
      if (greenHistogram[i] > maxCount) {
        maxCount = greenHistogram[i];
      }
      if (blueHistogram[i] > maxCount) {
        maxCount = blueHistogram[i];
      }
    }

    int imgWidth = 256;
    int imgHeight = 256;

    BufferedImage histogramImage = new BufferedImage(imgWidth, imgHeight,
            BufferedImage.TYPE_INT_RGB);
    Graphics2D graphics = histogramImage.createGraphics();

    graphics.setColor(java.awt.Color.WHITE);
    graphics.fillRect(0, 0, 256, 256);

    graphics.setColor(Color.LIGHT_GRAY);
    for (int i = 1; i < 16; i++) {
      int x = i * (imgWidth / 16);
      graphics.drawLine(x, 0, x, imgHeight);
      int y = i * (imgHeight / 16);
      graphics.drawLine(0, y, imgWidth, y);
    }

    for (int i = 0; i < 256; i++) {
      redHistogram[i] = (redHistogram[i] * imgHeight) / maxCount;
      greenHistogram[i] = (greenHistogram[i] * imgHeight) / maxCount;
      blueHistogram[i] = (blueHistogram[i] * imgHeight) / maxCount;
    }

    graphics.setColor(Color.RED);
    for (int i = 0; i < 255; i++) {
      graphics.drawLine(i, imgHeight - redHistogram[i], i + 1,
              imgHeight - redHistogram[i + 1]);
    }

    graphics.setColor(Color.GREEN);
    for (int i = 0; i < 255; i++) {
      graphics.drawLine(i, imgHeight - greenHistogram[i], i + 1,
              imgHeight - greenHistogram[i + 1]);
    }

    graphics.setColor(Color.BLUE);
    for (int i = 0; i < 255; i++) {
      graphics.drawLine(i, imgHeight - blueHistogram[i], i + 1,
              imgHeight - blueHistogram[i + 1]);
    }
    graphics.dispose();

    return convertBufferedImageIntoRawImage(histogramImage);
  }

  /**
   * Convert a BufferedImage into raw image data (string array).
   *
   * @param img The BufferedImage to convert.
   * @return A string array representing the raw image data.
   */
  private String[] convertBufferedImageIntoRawImage(BufferedImage img) {
    int width = img.getWidth();
    int height = img.getHeight();
    String[] rawImage = new String[height + 2];
    rawImage[0] = String.valueOf(width);
    rawImage[1] = String.valueOf(height);
    for (int y = 0; y < height; y++) {
      StringBuilder line = new StringBuilder();
      for (int x = 0; x < width; x++) {
        int rgb = img.getRGB(x, y);
        int red = (rgb >> 16) & 0xFF;
        int green = (rgb >> 8) & 0xFF;
        int blue = rgb & 0xFF;
        line.append(String.format("%03d%03d%03d", red, green, blue));
      }
      rawImage[y + 2] = line.toString();
    }
    return rawImage;
  }
}
