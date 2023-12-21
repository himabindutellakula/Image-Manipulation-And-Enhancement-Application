package controller.command;

import controller.ImageManipulationCommand;
import model.ImageManipulatorModel;

/**
 * Command to flip an image horizontally and save the result as a new image.
 */
public class HorizontalFlip implements ImageManipulationCommand {

  private final String srcImageName;
  private final String destImageName;

  /**
   * Constructs a HorizontalFlip command with source and destination image filenames.
   *
   * @param srcImageName  The filename of the source image.
   * @param destImageName The filename for the destination (horizontally flipped) image.
   */
  public HorizontalFlip(String srcImageName, String destImageName) {
    this.srcImageName = srcImageName;
    this.destImageName = destImageName;
  }

  @Override
  public void execute(ImageManipulatorModel m) {
    m.horizontalFlip(srcImageName, destImageName);
  }
}
