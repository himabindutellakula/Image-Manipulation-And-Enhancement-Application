package model;

/**
 * The abstract KernelFilter class provides a foundation for image filtering operations
 * based on a kernel (2D matrix). Subclasses can extend this class to implement specific
 * filtering algorithms.
 */
public abstract class KernelFilter implements ImageFilter {

  // The 2D matrix representing the filter kernel
  protected final double[][] kernel;

  /**
   * Constructs a KernelFilter with the specified kernel matrix. The kernel matrix should
   * have an odd dimension for filter operations.
   *
   * @param kernel A 2D matrix representing the kernel used for filtering.
   * @throws IllegalArgumentException if the provided kernel is not of odd dimension.
   */
  public KernelFilter(double[][] kernel) {
    if (checkKernelValidity(kernel)) {
      this.kernel = kernel;
    } else {
      throw new IllegalArgumentException("Please provide a valid filter of odd dimension.");
    }
  }

  /**
   * Validates the filter kernel. Kernels should be of odd dimension for filter operations.
   *
   * @param kernel A 2D matrix representing the kernel to be validated.
   * @return True if the kernel is valid (odd dimension), otherwise false.
   */
  private boolean checkKernelValidity(double[][] kernel) {
    int l = kernel.length;
    if (l % 2 == 1) {
      for (double[] doubles : kernel) {
        if (doubles.length != l) {
          return false;
        }
      }
      return true;
    }
    return false;
  }

  /**
   * Applies the filter to an input image and returns a new image with the filter effect applied.
   *
   * @param sourceImg The input image to which the filter is applied.
   * @return An Image object containing the filtered result.
   */
  @Override
  public Image applyFilterToImage(Image sourceImg) {
    Image imageWithAppliedFilter = new Image(sourceImg.getWidth(), sourceImg.getHeight());
    int limit = kernel.length / 2;
    for (int y = 0; y < sourceImg.getHeight(); y++) {
      for (int x = 0; x < sourceImg.getWidth(); x++) {
        double red = 0;
        double green = 0;
        double blue = 0;
        for (int i = -limit; i <= limit; i++) {
          for (int j = -limit; j <= limit; j++) {
            int neighborX = x + i;
            int neighborY = y + j;
            if (neighborX < 0 || neighborX >= sourceImg.getWidth() || neighborY < 0
                    || neighborY >= sourceImg.getHeight()) {
              continue;
            }
            double kernelValue = kernel[i + limit][j + limit];
            red += (kernelValue * sourceImg.getPixelRGB(neighborX, neighborY).getRed());
            green += (kernelValue * sourceImg.getPixelRGB(neighborX, neighborY).getGreen());
            blue += (kernelValue * sourceImg.getPixelRGB(neighborX, neighborY).getBlue());
          }
        }
        imageWithAppliedFilter.setPixelRGB(x, y,
                new Pixel(clampValue((int) red), clampValue((int) green), clampValue((int) blue)));
      }
    }
    return imageWithAppliedFilter;
  }

  /**
   * Clamps a value within the range [0, 255].
   *
   * @param value The value to be clamped.
   * @return The clamped value.
   */
  private static int clampValue(int value) {
    if (value < 0) {
      return 0;
    } else {
      return Math.min(value, 255);
    }
  }
}
