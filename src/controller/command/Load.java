package controller.command;

import controller.ImageManipulationCommand;
import utility.ImageUtility;
import java.io.IOException;
import model.ImageManipulatorModel;

/**
 * Command to load an image from a file and add it to the ImageManipulatorModel.
 */
public class Load implements ImageManipulationCommand {

  private final String imagePath; // The path to the image file.
  private final String imageName; // The name to use when adding the image to the model.

  /**
   * Constructs a Load command with an image file path and an image name.
   *
   * @param imagePath The path to the image file to load.
   * @param imageName The name to assign to the loaded image in the model.
   */
  public Load(String imagePath, String imageName) {
    this.imagePath = imagePath;
    this.imageName = imageName;
  }

  @Override
  public void execute(ImageManipulatorModel m) throws IOException {
    String extension = imagePath.substring(
            imagePath.lastIndexOf(".") + 1).toLowerCase();
    if (extension.equals("ppm")) {
      m.addRawImageToHashMap(imageName, ImageUtility.loadPPMImage(imagePath));
    } else {
      m.addRawImageToHashMap(imageName, ImageUtility.loadImage(imagePath));
    }
  }
}
