package controller.command;

import controller.ImageManipulationCommand;
import utility.ImageUtility;
import java.io.IOException;
import model.ImageManipulatorModel;

/**
 * Command to save an image from the ImageManipulatorModel,
 * to a file with the specified format.
 */
public class Save implements ImageManipulationCommand {

  private final String imagePath; // The path to save the image file.
  private final String imageName; // The name of the image in the model to save.

  /**
   * Constructs a Save command with an image file path and the name of the image to save.
   *
   * @param imagePath The path to save the image file.
   * @param imageName The name of the image in the model to save.
   */
  public Save(String imagePath, String imageName) {
    this.imagePath = imagePath;
    this.imageName = imageName;
  }

  @Override
  public void execute(ImageManipulatorModel m) throws IOException {
    String extension = imagePath.substring(
            imagePath.lastIndexOf(".") + 1).toLowerCase();
    String[] rawImage = m.getRawImageFromHashMap(imageName);
    if (rawImage != null) {
      switch (extension) {
        case "ppm":
          ImageUtility.saveImageAsPPM(rawImage, imagePath);
          break;
        case "jpg":
        case "jpeg":
        case "bmp":
        case "png":
          ImageUtility.saveImageWithFormat(rawImage, imagePath, extension.toUpperCase());
          break;
        default:
          throw new IllegalArgumentException("Unsupported file format");
      }
    } else {
      System.err.println("Image not found: " + imageName);
    }
  }
}
