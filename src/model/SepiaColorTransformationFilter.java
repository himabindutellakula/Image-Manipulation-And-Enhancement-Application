package model;

/**
 * The SepiaColorTransformationFilter class represents a specific color transformation filter
 * that converts an image to sepia tones. It inherits from the ColorTransformationFilter class.
 */
public class SepiaColorTransformationFilter extends ColorTransformationFilter {

  // The sepia color transformation matrix
  private static final double[][] sepiaCTFilter = {
          {0.393, 0.769, 0.189},
          {0.349, 0.686, 0.168},
          {0.272, 0.534, 0.131}
  };

  /**
   * Constructs a SepiaColorTransformationFilter using the predefined sepia transformation matrix.
   */
  public SepiaColorTransformationFilter() {
    super(sepiaCTFilter);
  }
}
