package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * Implementation of the ImageCompression interface using Haar Wavelet Transform for compression.
 */
public class ImageCompressionImpl implements ImageCompression {

  @Override
  public Image compress(Image srcImg, Double compressionPercentage) {

    PaddedPixelData ppd = getPaddedPixelData(srcImg);
    double[][] redComponent = ppd.paddedRedImageArray;
    double[][] greenComponent = ppd.paddedGreenImageArray;
    double[][] blueComponent = ppd.paddedBlueImageArray;

    haarTransform(redComponent, redComponent.length);
    haarTransform(greenComponent, greenComponent.length);
    haarTransform(blueComponent, blueComponent.length);

    calculateThresholdAndApply(redComponent, greenComponent, blueComponent, compressionPercentage);

    inverseHaarTransform(redComponent, redComponent.length);
    inverseHaarTransform(greenComponent, greenComponent.length);
    inverseHaarTransform(blueComponent, blueComponent.length);

    Image compressedImage = new Image(srcImg.getWidth(), srcImg.getHeight());

    for (int y = 0; y < srcImg.getHeight(); y++) {
      for (int x = 0; x < srcImg.getWidth(); x++) {
        compressedImage.setPixelRGB(x, y,
            new Pixel(clampValue((int) redComponent[x][y]), clampValue((int) greenComponent[x][y]),
                clampValue((int) blueComponent[x][y])));
      }
    }
    return compressedImage;
  }

  /**
   * Ensures that the provided pixel value is within the range [0, 255].
   *
   * @param value The pixel value to clamp.
   * @return The clamped pixel value.
   */
  private int clampValue(int value) {
    return Math.max(0, Math.min(255, value));
  }

  /**
   * Gets the padded pixel data for the given image.
   *
   * @param srcImg The original image.
   * @return PaddedPixelData containing padded red, green, and blue image arrays.
   */
  private PaddedPixelData getPaddedPixelData(Image srcImg) {
    int originalWidth = srcImg.getWidth();
    int originalHeight = srcImg.getHeight();
    int maxSize = Math.max(originalWidth, originalHeight);
    int newSize = 1;
    while (newSize < maxSize) {
      newSize *= 2;
    }
    PaddedPixelData ppd = new PaddedPixelData();
    ppd.paddedRedImageArray = new double[newSize][newSize];
    ppd.paddedGreenImageArray = new double[newSize][newSize];
    ppd.paddedBlueImageArray = new double[newSize][newSize];
    for (int j = 0; j < newSize; j++) {
      for (int i = 0; i < newSize; i++) {
        if (j < originalHeight && i < originalWidth) {
          ppd.paddedRedImageArray[i][j] = srcImg.getPixelRGB(i, j).getRed();
          ppd.paddedGreenImageArray[i][j] = srcImg.getPixelRGB(i, j).getGreen();
          ppd.paddedBlueImageArray[i][j] = srcImg.getPixelRGB(i, j).getBlue();
        } else {
          ppd.paddedRedImageArray[i][j] = 0;
          ppd.paddedGreenImageArray[i][j] = 0;
          ppd.paddedBlueImageArray[i][j] = 0;
        }
      }
    }
    return ppd;
  }

  /**
   * Represents the padded pixel data structure.
   */
  private static class PaddedPixelData {

    public double[][] paddedRedImageArray;
    public double[][] paddedGreenImageArray;
    public double[][] paddedBlueImageArray;
  }


  /**
   * Removes the smallest values from the 2D array based on the specified percentage.
   *
   * @param rArray             The red 2D array.
   * @param bArray             The blue 2D array.
   * @param gArray             The green 2D array.
   * @param percentageToRemove The percentage of smallest values to be removed.
   */
  private void calculateThresholdAndApply(double[][] rArray, double[][] bArray, double[][] gArray,
      double percentageToRemove) {
    Set<Double> uniqueValues = new TreeSet<>();
    getUniqueValues(rArray, uniqueValues);
    getUniqueValues(bArray, uniqueValues);
    getUniqueValues(gArray, uniqueValues);

    List<Double> uniqueValuesList = new ArrayList<>(uniqueValues);
    int elementsToRemove = (int) (uniqueValues.size() * percentageToRemove / 100.0);

    double threshold = uniqueValuesList.get(elementsToRemove);

    for (int i = 0; i < rArray.length; i++) {
      for (int j = 0; j < rArray[i].length; j++) {
        if (Math.abs(rArray[i][j]) <= threshold) {
          rArray[i][j] = 0.0;
        }
        if (Math.abs(gArray[i][j]) <= threshold) {
          gArray[i][j] = 0.0;
        }
        if (Math.abs(bArray[i][j]) <= threshold) {
          bArray[i][j] = 0.0;
        }
      }
    }
  }

  /**
   * Gets unique non-zero values from the 2D array.
   *
   * @param array        The 2D array of each channel.
   * @param uniqueValues Set to store the unique values.
   */
  private void getUniqueValues(double[][] array, Set<Double> uniqueValues) {
    for (double[] row : array) {
      for (double value : row) {
        if (value != 0) {
          uniqueValues.add(Math.abs(value));
        }
      }
    }
  }

  /**
   * Performs a 1D Haar Wavelet Transform on the given array.
   *
   * @param s The array to be transformed.
   * @param c The size of the array.
   */
  private void transform1D(double[] s, int c) {
    int n = s.length;
    double[] avg = new double[n / 2];
    double[] diff = new double[n / 2];

    for (int i = 0, j = 0; i < n; i += 2, j++) {
      double a = s[i];
      double b = s[i + 1];
      avg[j] = (a + b) / Math.sqrt(2);
      diff[j] = (a - b) / Math.sqrt(2);
    }

    System.arraycopy(avg, 0, s, 0, n / 2);
    System.arraycopy(diff, 0, s, n / 2, n / 2);
  }

  /**
   * Performs a 2D Haar Wavelet Transform on the given array.
   *
   * @param x    The 2D array to be transformed.
   * @param size The size of the array.
   */
  private void haarTransform(double[][] x, int size) {
    int c = size;

    while (c > 1) {
      // Transform along rows
      for (int i = 0; i < c; i++) {
        transform1D(x[i], c);
      }

      // Transform along columns
      for (int j = 0; j < c; j++) {
        double[] column = new double[size];
        for (int i = 0; i < size; i++) {
          column[i] = x[i][j];
        }
        transform1D(column, c);
        for (int i = 0; i < size; i++) {
          x[i][j] = column[i];
        }
      }

      c = c / 2;
    }
  }

  /**
   * Performs an inverse 1D Haar Wavelet Transform on the given array.
   *
   * @param s The array to be transformed.
   * @param c The size of the array.
   */
  private void inverseTransform1D(double[] s, int c) {
    int n = s.length;
    double[] avg = new double[n / 2];
    double[] diff = new double[n / 2];

    System.arraycopy(s, 0, avg, 0, n / 2);
    System.arraycopy(s, n / 2, diff, 0, n / 2);

    for (int i = 0, j = 0; i < n; i += 2, j++) {
      double a = (avg[j] + diff[j]) / Math.sqrt(2);
      double b = (avg[j] - diff[j]) / Math.sqrt(2);
      s[i] = a;
      s[i + 1] = b;
    }
  }

  /**
   * Performs an inverse 2D Haar Wavelet Transform on the given array.
   *
   * @param x    The 2D array to be transformed.
   * @param size The size of the array.
   */
  private void inverseHaarTransform(double[][] x, int size) {
    int c = 2;
    while (c <= size) {
      // Inverse transform along columns
      for (int j = 0; j < c; j++) {
        double[] column = new double[size];
        for (int i = 0; i < size; i++) {
          column[i] = x[i][j];
        }
        inverseTransform1D(column, c);
        for (int i = 0; i < size; i++) {
          x[i][j] = column[i];
        }
      }
      // Inverse transform along rows
      for (int i = 0; i < c; i++) {
        inverseTransform1D(x[i], c);
      }
      c = c * 2;
    }
  }

}
