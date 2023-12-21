package view;

import java.io.IOException;

/**
 * An implementation of the ImageManipulatorView interface that provides methods
 * to display messages and a menu for an image manipulation application.
 */
public class ImageManipulatorViewImpl implements ImageManipulatorView {

  @Override
  public void displayMenu(Appendable out) throws IOException {
    out.append("Supported image manipulation commands are: ").append("\n");
    out.append("load imagePath imageName").append("\n");
    out.append("save imagePath imageName").append("\n");
    out.append("rgb-split sourceImageName destinationRedImageName "
            + "destinationGreenImageName "
            + "destinationBlueImageName").append("\n");
    out.append(
            "rgb-combine destinationImageName sourceRedComponentImage sourceGreenComponentImage "
                    + "sourceBlueComponentImage").append("\n");
    out.append("brighten increment sourceImageName destinationImageName").append("\n");
    out.append("vertical-flip sourceImageName destinationImageName").append("\n");
    out.append("horizontal-flip sourceImageName destinationImageName").append("\n");
    out.append("greyscale-component-name sourceImageName destinationImageName").append("\n");
    out.append("blur sourceImageName destinationImageName").append("\n");
    out.append("sharpen sourceImageName destinationImageName").append("\n");
    out.append("sepia sourceImageName destinationImageName").append("\n");
  }

  @Override
  public void startMessageForAnApplication(Appendable out) throws IOException {
    out.append("-------------------------------------------------------------").append("\n");
    out.append("Welcome to the Image processing and manipulation application!").append("\n");
    out.append("-------------------------------------------------------------").append("\n");
    displayMenu(out);
  }

  @Override
  public void endMessageForAnApplication(Appendable out) throws IOException {
    out.append("---------------------------------").append("\n");
    out.append("Thank you for using this program!").append("\n");
    out.append("---------------------------------").append("\n");
  }

  @Override
  public void startMessageForScriptExecution(Appendable out) throws IOException {
    out.append("-------------------------").append("\n");
    out.append("Script execution started!").append("\n");
    out.append("-------------------------").append("\n");
  }

  @Override
  public void endMessageForScriptExecution(Appendable out) throws IOException {
    out.append("----------------------").append("\n");
    out.append("Script execution ended!").append("\n");
    out.append("-----------------------").append("\n");
    out.append("Enter commands or Type 'exit' to quit :").append("\n");
  }
}
