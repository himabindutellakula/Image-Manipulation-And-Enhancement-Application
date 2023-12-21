package controller.command;

import controller.AdvancedImageManipulationCommand;
import java.io.IOException;
import model.AdvancedImageManipulatorModel;

/**
 * A command class for performing color correction on an image.
 * Implements the ImageManipulationCommand interface to execute the color correction operation.
 */
public class ColorCorrect implements AdvancedImageManipulationCommand {

  private final String srcImageName; // Source image filename.
  private final String destImageName; // Destination image filename.

  /**
   * Constructs a ColorCorrect command with source and destination image filenames.
   *
   * @param srcImageName  The filename of the source image.
   * @param destImageName The filename for the destination (color-corrected) image.
   */
  public ColorCorrect(String srcImageName, String destImageName) {
    this.srcImageName = srcImageName;
    this.destImageName = destImageName;
  }

  @Override
  public void execute(AdvancedImageManipulatorModel model) throws IOException {
    model.colorCorrect(srcImageName, destImageName);
  }
}
