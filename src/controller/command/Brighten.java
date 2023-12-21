package controller.command;

import controller.ImageManipulationCommand;
import model.ImageManipulatorModel;

/**
 * Command to brighten an image by a specified increment,
 * and save the result as a new image.
 */
public class Brighten implements ImageManipulationCommand {

  private final int increment; // The brightness increment.
  private final String srcImageName; // Source image filename.
  private final String destImageName; // Destination image filename.

  /**
   * Constructs a Brighten command with brightness increment,
   * and source/destination image filenames.
   *
   * @param increment     The increment by which to brighten the image.
   * @param srcImageName  The filename of the source image.
   * @param destImageName The filename for the destination (brightened) image.
   */
  public Brighten(int increment, String srcImageName, String destImageName) {
    this.increment = increment;
    this.srcImageName = srcImageName;
    this.destImageName = destImageName;
  }

  @Override
  public void execute(ImageManipulatorModel m) {
    m.brighten(increment, srcImageName, destImageName);
  }
}
