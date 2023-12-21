package controller.command;

import controller.ImageManipulationCommand;
import model.ImageManipulatorModel;

/**
 * Command to apply a blur effect to an image and save the result as a new image.
 */
public class Blur implements ImageManipulationCommand {

  private final String srcImageName; // Source image filename.
  private final String destImageName; // Destination image filename.

  /**
   * Constructs a Blur command with source and destination image filenames.
   *
   * @param srcImageName  The filename of the source image.
   * @param destImageName The filename for the destination (blurred) image.
   */
  public Blur(String srcImageName, String destImageName) {
    this.srcImageName = srcImageName;
    this.destImageName = destImageName;
  }

  /**
   * Executes the Blur command by applying a blur effect to,
   * the source image and saving the result as the destination image.
   *
   * @param m The ImageManipulatorModel that performs the image manipulation.
   */
  @Override
  public void execute(ImageManipulatorModel m) {
    m.blur(srcImageName, destImageName);
  }
}
