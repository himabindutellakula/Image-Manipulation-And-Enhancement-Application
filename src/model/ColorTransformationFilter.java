package model;

/**
 * An abstract class representing a color transformation filter for image processing. This filter
 * applies a specified color transformation matrix to each pixel in an image.
 */
public abstract class ColorTransformationFilter implements ImageFilter {

  /**
   * The color transformation matrix used by this filter to transform pixel colors.
   */
  private final double[][] colorTransformation;

  /**
   * Constructs a color transformation filter with the given color transformation matrix.
   *
   * @param colorTransformation The 3x3 color transformation matrix to apply to each pixel.
   */
  public ColorTransformationFilter(double[][] colorTransformation) {
    this.colorTransformation = colorTransformation;
  }

  /**
   * Applies the color transformation filter to the input image.
   *
   * @param srcImg The source image to which the filter will be applied.
   * @return A new image with the filter applied.
   */
  @Override
  public Image applyFilterToImage(Image srcImg) {
    Image imageWithAppliedFilter = new Image(srcImg.getWidth(), srcImg.getHeight());
    for (int y = 0; y < srcImg.getHeight(); y++) {
      for (int x = 0; x < srcImg.getWidth(); x++) {
        Pixel currentPixel = srcImg.getPixelRGB(x, y);
        int currentRed = currentPixel.getRed();
        int currentGreen = currentPixel.getGreen();
        int currentBlue = currentPixel.getBlue();
        int newRed = (int) (colorTransformation[0][0] * currentRed
            + colorTransformation[0][1] * currentGreen
            + colorTransformation[0][2] * currentBlue);
        int newGreen = (int) (colorTransformation[1][0] * currentRed
            + colorTransformation[1][1] * currentGreen
            + colorTransformation[1][2] * currentBlue);
        int newBlue = (int) (colorTransformation[2][0] * currentRed
            + colorTransformation[2][1] * currentGreen
            + colorTransformation[2][2] * currentBlue);
        imageWithAppliedFilter.setPixelRGB(x, y,
            new Pixel(clampValue(newRed), clampValue(newGreen), clampValue(newBlue)));
      }
    }
    return imageWithAppliedFilter;
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
}
