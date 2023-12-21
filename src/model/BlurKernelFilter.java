package model;

/**
 * A class representing a blur filter using a specific kernel for image convolution.
 */
public class BlurKernelFilter extends KernelFilter {

  /**
   * The Gaussian blur filter kernel represented as a 3x3 matrix.
   */
  private static final double[][] gaussianFilter = {
          {1.0 / 16, 1.0 / 8, 1.0 / 16},
          {1.0 / 8, 1.0 / 4, 1.0 / 8},
          {1.0 / 16, 1.0 / 8, 1.0 / 16}
  };

  /**
   * Constructs a `BlurKernelFilter` with the Gaussian blur filter kernel.
   */
  public BlurKernelFilter() {
    super(gaussianFilter);
  }
}
