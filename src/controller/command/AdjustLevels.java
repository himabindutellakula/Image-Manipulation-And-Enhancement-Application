package controller.command;

import controller.AdvancedImageManipulationCommand;
import model.AdvancedImageManipulatorModel;

/**
 * A command class for adjusting levels in an image. Implements the ImageManipulationCommand
 * interface to execute the adjustment operation.
 */
public class AdjustLevels implements AdvancedImageManipulationCommand {

  private final String srcImageName; // Source image filename.
  private final String destImageName; // Destination image filename.
  private final int b; // The black point.
  private final int m; // The midpoint.
  private final int w; // The white point.

  /**
   * Constructs an AdjustLevels command with source and destination image filenames, along with
   * parameters for black point, midpoint, and white point.
   *
   * @param b             The black point.
   * @param m             The midpoint.
   * @param w             The white point.
   * @param srcImageName  The filename of the source image.
   * @param destImageName The filename for the destination image after level adjustment.
   */
  public AdjustLevels(int b, int m, int w, String srcImageName, String destImageName) {
    this.b = b;
    this.m = m;
    this.w = w;
    this.srcImageName = srcImageName;
    this.destImageName = destImageName;
  }

  /**
   * Execute the image manipulation command on the given AdvancedImageManipulatorModel.
   *
   * @param model The AdvancedImageManipulatorModel on which the image manipulation, operation will
   *              be performed.
   * @throws IllegalArgumentException when b, m, w are not in between 0 & 255 and values should be b
   *                                  < m < w.
   */
  @Override
  public void execute(AdvancedImageManipulatorModel model) throws IllegalArgumentException {
    try {
      model.adjustLevels(b, m, w, srcImageName, destImageName);
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException(e);
    }
  }
}
