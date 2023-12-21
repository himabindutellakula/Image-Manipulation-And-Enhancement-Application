package controller.command;

import controller.ImageManipulationCommand;
import model.ImageManipulatorModel;

/**
 * Command to flip an image vertically and save the result as a new image.
 */
public class VerticalFlip implements ImageManipulationCommand {

  private final String srcImageName; // Source image filename.
  private final String destImageName; // Destination image filename.

  /**
   * Constructs a VerticalFlip command with source and destination image filenames.
   *
   * @param srcImageName  The filename of the source image.
   * @param destImageName The filename for the destination (vertically flipped) image.
   */
  public VerticalFlip(String srcImageName, String destImageName) {
    this.srcImageName = srcImageName;
    this.destImageName = destImageName;
  }

  @Override
  public void execute(ImageManipulatorModel m) {
    m.verticalFlip(srcImageName, destImageName);
  }
}
