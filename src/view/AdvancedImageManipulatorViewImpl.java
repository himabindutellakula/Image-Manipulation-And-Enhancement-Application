package view;

import java.io.IOException;

/**
 * An advanced implementation of the ImageManipulatorView interface extending the
 * ImageManipulatorViewImpl to display messages to user.
 */
public class AdvancedImageManipulatorViewImpl extends ImageManipulatorViewImpl {

  @Override
  public void displayMenu(Appendable out) throws IOException {
    super.displayMenu(out);
    out.append("compress srcImageName destImageName percentage").append("\n");
    out.append("split operationName srcImageName "
        + "destImageName widthPercentage ").append("\n");
    out.append("histogram srcImageName destImageName").append("\n");
    out.append("color-correct srcImageName destImageName").append("\n");
    out.append("levels-adjust b m w srcImageName "
        + "destImageName").append("\n");
  }

}
