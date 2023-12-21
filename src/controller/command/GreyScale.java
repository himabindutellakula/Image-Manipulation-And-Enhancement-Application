package controller.command;

import controller.ImageManipulationCommand;
import model.ImageManipulatorModel;

/**
 * Command to apply grayscale conversion to an image and save the result as a new image.
 */
public class GreyScale implements ImageManipulationCommand {

  private final String component; // The grayscale component to use.
  private final String srcImageName; // Source image filename.
  private final String destImageName; // Destination image filename.

  /**
   * Constructs a GreyScale command with grayscale component,
   * and source/destination image filenames.
   *
   * @param component     The grayscale component to use,
   *                     ("red-component", "green-component", "blue-component",
   *                      "value-component", "luma-component", or "intensity-component").
   * @param srcImageName  The filename of the source image.
   * @param destImageName The filename for the destination (grayscale) image.
   */
  public GreyScale(String component, String srcImageName, String destImageName) {
    this.component = component;
    this.srcImageName = srcImageName;
    this.destImageName = destImageName;
  }

  @Override
  public void execute(ImageManipulatorModel m) {
    m.greyscale(component, srcImageName, destImageName);
  }
}
