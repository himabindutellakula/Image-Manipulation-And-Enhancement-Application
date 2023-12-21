import controller.GUIController;
import view.ImageManipulatorGUIView;

/**
 * A mock class for ImageManipulatorGUIView used for GUIController testing.
 */
public class MockView implements ImageManipulatorGUIView {

  private final StringBuilder log;

  public MockView(StringBuilder out) {
    log = out;
  }

  @Override
  public void addFeatures(GUIController features) {
    log.append("addFeatures");
  }

  @Override
  public void setImageInPanel(String[] image) {
    log.append("setImageInPanel");
  }

  @Override
  public void setHistogramInPanel(String[] image) {
    log.append("setHistogramInPanel");
  }

  @Override
  public void displayErrorMessage(String errorMessage) {
    log.append("displayErrorMessage");
  }
}
