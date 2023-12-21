package controller.command;

import controller.ImageManipulationCommand;
import model.ImageManipulatorModel;

/**
 * Command to apply a sepia effect to an image and save the result as a new image.
 */
public class Sepia implements ImageManipulationCommand {

  private final String srcImageName; // Source image filename.
  private final String destImageName; // Destination image filename.

  /**
   * Constructs a Sepia command with source and destination image filenames.
   *
   * @param srcImageName  The filename of the source image.
   * @param destImageName The filename for the destination (sepia) image.
   */
  public Sepia(String srcImageName, String destImageName) {
    this.srcImageName = srcImageName;
    this.destImageName = destImageName;
  }

  @Override
  public void execute(ImageManipulatorModel m) {
    m.sepia(srcImageName, destImageName);
  }
}
