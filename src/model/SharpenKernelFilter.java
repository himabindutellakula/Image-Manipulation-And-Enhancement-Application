package model;

/**
 * The SharpenKernelFilter class represents a specific kernel filter used for image sharpening.
 * It inherits from the KernelFilter class.
 */
public class SharpenKernelFilter extends KernelFilter {

  // The sharpening kernel matrix
  private static final double[][] sharpenKernel = {
          {-1.0 / 8, -1.0 / 8, -1.0 / 8, -1.0 / 8, -1.0 / 8},
          {-1.0 / 8, 1.0 / 4, 1.0 / 4, 1.0 / 4, -1.0 / 8},
          {-1.0 / 8, 1.0 / 4, 1.0, 1.0 / 4, -1.0 / 8},
          {-1.0 / 8, 1.0 / 4, 1.0 / 4, 1.0 / 4, -1.0 / 8},
          {-1.0 / 8, -1.0 / 8, -1.0 / 8, -1.0 / 8, -1.0 / 8}
  };

  /**
   * Constructs a SharpenKernelFilter using the predefined sharpening kernel matrix.
   */
  public SharpenKernelFilter() {
    super(sharpenKernel);
  }
}
