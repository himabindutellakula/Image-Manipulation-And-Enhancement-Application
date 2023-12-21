package controller.command;

import controller.AdvancedImageManipulationCommand;
import model.AdvancedImageManipulatorModel;

/**
 * A command class for compressing an image. Implements the ImageManipulationCommand interface to
 * execute the compression operation.
 */
public class Compress implements AdvancedImageManipulationCommand {

  private final String srcImageName; // Source image filename.
  private final String destImageName; // Destination image filename.
  private final double percentage; // Percentage of compression

  /**
   * Constructs a Compress command with source and destination image filenames, along with a
   * percentage value for compression.
   *
   * @param percentage    The percentage of compression to be applied.
   * @param srcImageName  The filename of the source image.
   * @param destImageName The filename for the destination image after compression.
   */
  public Compress(double percentage, String srcImageName, String destImageName) {
    this.srcImageName = srcImageName;
    this.destImageName = destImageName;
    this.percentage = percentage;
  }

  @Override
  public void execute(AdvancedImageManipulatorModel model) throws IllegalArgumentException {
    try {
      model.compress(percentage, srcImageName, destImageName);
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException(e);
    }
  }
}
