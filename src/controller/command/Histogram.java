package controller.command;

import controller.AdvancedImageManipulationCommand;
import java.io.IOException;
import model.AdvancedImageManipulatorModel;

/**
 * A command class for generating a histogram of an image.
 * Implements the ImageManipulationCommand interface to execute the histogram generation operation.
 */
public class Histogram implements AdvancedImageManipulationCommand {

  private final String srcImageName; // Source image filename.
  private final String destImageName; // Destination image filename.

  /**
   * Constructs a Histogram command with source and destination image filenames.
   *
   * @param srcImageName  The filename of the source image.
   * @param destImageName The filename for the destination image where the histogram will be saved.
   */
  public Histogram(String srcImageName, String destImageName) {
    this.srcImageName = srcImageName;
    this.destImageName = destImageName;
  }

  @Override
  public void execute(AdvancedImageManipulatorModel model) throws IOException {
    model.generateHistogram(srcImageName, destImageName);
  }
}
