package model;

/**
 * The ImageCompression interface defines methods for compressing images using various techniques.
 */
public interface ImageCompression {

  /**
   * Compresses the given image using the Haar Wavelet Transform.
   *
   * @param srcImg                The original image to be compressed.
   * @param compressionPercentage Percentage of smallest values to be removed during compression.
   * @return Compressed image after applying the Haar Wavelet Transform.
   */
  Image compress(Image srcImg, Double compressionPercentage);
}
