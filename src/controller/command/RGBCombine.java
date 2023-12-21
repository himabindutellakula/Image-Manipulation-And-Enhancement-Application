package controller.command;

import controller.ImageManipulationCommand;
import model.ImageManipulatorModel;

/**
 * Command to combine separate red, green, and blue channels into a,
 * single image and save the result as a new image.
 */
public class RGBCombine implements ImageManipulationCommand {

  private final String srcRedImageName; // Source red channel image name.
  private final String srcGreenImageName; // Source green channel image name.
  private final String srcBlueImageName; // Source blue channel image name.
  private final String destImageName; // Destination combined image name.

  /**
   * Constructs an RGBCombine command with source image names for red,
   * green, blue channels, and a destination image name.
   *
   * @param srcRedImageName   The name of the source image for the red channel.
   * @param srcGreenImageName The name of the source image for the green channel.
   * @param srcBlueImageName  The name of the source image for the blue channel.
   * @param destImageName     The name of the destination combined image.
   */
  public RGBCombine(String srcRedImageName, String srcGreenImageName,
                    String srcBlueImageName, String destImageName) {
    this.srcRedImageName = srcRedImageName;
    this.srcGreenImageName = srcGreenImageName;
    this.srcBlueImageName = srcBlueImageName;
    this.destImageName = destImageName;
  }

  @Override
  public void execute(ImageManipulatorModel m) {
    m.rgbCombine(srcRedImageName, srcGreenImageName, srcBlueImageName, destImageName);
  }
}
