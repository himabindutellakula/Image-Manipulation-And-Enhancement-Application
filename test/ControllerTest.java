import static org.junit.Assert.assertEquals;

import controller.AdvancedImageManipulatorControllerImpl;
import controller.ImageManipulatorController;
import java.io.IOException;
import java.io.StringReader;
import model.AdvancedImageManipulatorModel;
import org.junit.Test;
import view.AdvancedImageManipulatorViewImpl;
import view.ImageManipulatorView;

/**
 * A test class to test the controller functionality by mocking the model.
 */
public class ControllerTest {

  StringBuilder log = new StringBuilder();
  StringBuilder out = new StringBuilder();
  AdvancedImageManipulatorModel mockModel = new MockModel(log);
  ImageManipulatorView testView = new AdvancedImageManipulatorViewImpl();


  @Test
  public void testCompress() throws IOException {
    Readable in = new StringReader("compress 70 m c\nexit");
    ImageManipulatorController controller = new AdvancedImageManipulatorControllerImpl(mockModel,
        testView);
    controller.executeApplication(in, out);
    assertEquals("compress", log.toString());
  }

  @Test
  public void testHistogram() throws IOException {
    Readable in = new StringReader("histogram m c\nexit");
    ImageManipulatorController controller = new AdvancedImageManipulatorControllerImpl(mockModel,
        testView);
    controller.executeApplication(in, out);
    assertEquals("generateHistogram", log.toString());
  }

  @Test
  public void testSplitView() throws IOException {
    Readable in = new StringReader("blur m c split 90\nexit");
    ImageManipulatorController controller = new AdvancedImageManipulatorControllerImpl(mockModel,
        testView);
    controller.executeApplication(in, out);
    assertEquals("splitView", log.toString());
  }

  @Test
  public void testColorCorrect() throws IOException {
    Readable in = new StringReader("color-correct m c\nexit");
    ImageManipulatorController controller = new AdvancedImageManipulatorControllerImpl(mockModel,
        testView);
    controller.executeApplication(in, out);
    assertEquals("colorCorrect", log.toString());
  }

  @Test
  public void testAdjustLevels() throws IOException {
    Readable in = new StringReader("levels-adjust 10 90 120 m c\nexit");
    ImageManipulatorController controller = new AdvancedImageManipulatorControllerImpl(mockModel,
        testView);
    controller.executeApplication(in, out);
    assertEquals("adjustLevels", log.toString());
  }

  @Test
  public void testRGBSplit() throws IOException {
    Readable in = new StringReader("rgb-split r m c p\nexit");
    ImageManipulatorController controller = new AdvancedImageManipulatorControllerImpl(mockModel,
        testView);
    controller.executeApplication(in, out);
    assertEquals("rgbSplit", log.toString());
  }

  @Test
  public void testRGBCombine() throws IOException {
    Readable in = new StringReader("rgb-combine r m c p\nexit");
    ImageManipulatorController controller = new AdvancedImageManipulatorControllerImpl(mockModel,
        testView);
    controller.executeApplication(in, out);
    assertEquals("rgbCombine", log.toString());
  }

  @Test
  public void horizontalFlip() throws IOException {
    Readable in = new StringReader("horizontal-flip m c\nexit");
    ImageManipulatorController controller = new AdvancedImageManipulatorControllerImpl(mockModel,
        testView);
    controller.executeApplication(in, out);
    assertEquals("horizontalFlip", log.toString());
  }

  @Test
  public void verticalFlip() throws IOException {
    Readable in = new StringReader("vertical-flip m c\nexit");
    ImageManipulatorController controller = new AdvancedImageManipulatorControllerImpl(mockModel,
        testView);
    controller.executeApplication(in, out);
    assertEquals("verticalFlip", log.toString());
  }

  @Test
  public void testBrighten() throws IOException {
    Readable in = new StringReader("brighten 100 m c\nexit");
    ImageManipulatorController controller = new AdvancedImageManipulatorControllerImpl(mockModel,
        testView);
    controller.executeApplication(in, out);
    assertEquals("brighten", log.toString());
  }

  @Test
  public void testBlur() throws IOException {
    Readable in = new StringReader("blur m c\nexit");
    ImageManipulatorController controller = new AdvancedImageManipulatorControllerImpl(mockModel,
        testView);
    controller.executeApplication(in, out);
    assertEquals("blur", log.toString());
  }

  @Test
  public void testSharpen() throws IOException {
    Readable in = new StringReader("sharpen m c\nexit");
    ImageManipulatorController controller = new AdvancedImageManipulatorControllerImpl(mockModel,
        testView);
    controller.executeApplication(in, out);
    assertEquals("sharpen", log.toString());
  }

  @Test
  public void testSepia() throws IOException {
    Readable in = new StringReader("sepia m c\nexit");
    ImageManipulatorController controller = new AdvancedImageManipulatorControllerImpl(mockModel,
        testView);
    controller.executeApplication(in, out);
    assertEquals("sepia", log.toString());
  }

  @Test
  public void testGreyScale() throws IOException {
    Readable in = new StringReader("red-component m c\nexit");
    ImageManipulatorController controller = new AdvancedImageManipulatorControllerImpl(mockModel,
        testView);
    controller.executeApplication(in, out);
    assertEquals("greyscale", log.toString());
  }
}
