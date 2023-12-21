package model;

/**
 * An interface for image filters that can be applied to an input image.
 */
public interface ImageFilter {

  /**
   * Applies the filter to the input image and returns the result as a new image.
   *
   * @param sourceImg The input image to which the filter is applied.
   * @return A new image with the filter applied.
   */
  Image applyFilterToImage(Image sourceImg);
}
