package controller.command;

import controller.ImageManipulationCommand;
import model.ImageManipulatorModel;

/**
 * Command to split an image into separate red, green,
 * and blue channel images and save them as new images.
 */
public class RGBSplit implements ImageManipulationCommand {

  private final String srcImageName; // Source image name.
  private final String destRedImageName; // Destination image name for the red channel.
  private final String destGreenImageName; // Destination image name for the green channel.
  private final String destBlueImageName; // Destination image name for the blue channel.

  /**
   * Constructs an RGBSplit command with a source image name and destination,
   * image names for the red, green, and blue channels.
   *
   * @param srcImageName       The name of the source image to split.
   * @param destRedImageName   The name for the destination red channel image.
   * @param destGreenImageName The name for the destination green channel image.
   * @param destBlueImageName  The name for the destination blue channel image.
   */
  public RGBSplit(String srcImageName, String destRedImageName,
                  String destGreenImageName, String destBlueImageName) {
    this.srcImageName = srcImageName;
    this.destRedImageName = destRedImageName;
    this.destGreenImageName = destGreenImageName;
    this.destBlueImageName = destBlueImageName;
  }

  @Override
  public void execute(ImageManipulatorModel m) {
    m.rgbSplit(srcImageName, destRedImageName, destGreenImageName, destBlueImageName);
  }
}
