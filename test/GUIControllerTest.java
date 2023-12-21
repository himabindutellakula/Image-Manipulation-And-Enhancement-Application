import static org.junit.Assert.assertEquals;

import controller.GUICallBackController;
import controller.GUIController;
import java.io.IOException;
import model.AdvancedImageManipulatorModel;
import org.junit.Test;
import view.ImageManipulatorGUIView;

/**
 * A test class to test the GUIController functionality by mocking the model.
 */
public class GUIControllerTest {

  StringBuilder log = new StringBuilder();
  StringBuilder out = new StringBuilder();
  AdvancedImageManipulatorModel mockModel = new MockModel(log);
  ImageManipulatorGUIView mockView = new MockView(out);
  GUIController testController = new GUICallBackController(mockModel, mockView);

  @Test
  public void testCompress() throws IOException {
    testController.compress(50);
    assertEquals("compressgetRawImageFromHashMapgenerateHistogramgetRawImageFromHashMap",
        log.toString());
    assertEquals("addFeaturessetImageInPanelsetHistogramInPanel",out.toString());
  }


  @Test
  public void testSplitView() throws IOException {
    testController.preview("blur", 50);
    assertEquals(
        "splitViewgenerateHistogramgetRawImageFromHashMapgenerateHistogramgetRawImageFromHashMap",
        log.toString());
    assertEquals("addFeaturessetImageInPanelsetHistogramInPanel",out.toString());
  }

  @Test
  public void testColorCorrect() throws IOException {
    testController.colorCorrect();
    assertEquals("colorCorrectgetRawImageFromHashMapgenerateHistogramgetRawImageFromHashMap",
        log.toString());
    assertEquals("addFeaturessetImageInPanelsetHistogramInPanel",out.toString());
  }

  @Test
  public void testAdjustLevels() throws IOException {
    testController.adjustLevels(10, 120, 230);
    assertEquals("adjustLevelsgetRawImageFromHashMapgenerateHistogramgetRawImageFromHashMap",
        log.toString());
    assertEquals("addFeaturessetImageInPanelsetHistogramInPanel",out.toString());
  }


  @Test
  public void horizontalFlip() throws IOException {
    testController.horizontalFlip();
    assertEquals("horizontalFlipgetRawImageFromHashMapgenerateHistogramgetRawImageFromHashMap",
        log.toString());
    assertEquals("addFeaturessetImageInPanelsetHistogramInPanel",out.toString());
  }

  @Test
  public void verticalFlip() throws IOException {
    testController.verticalFlip();
    assertEquals("verticalFlipgetRawImageFromHashMapgenerateHistogramgetRawImageFromHashMap",
        log.toString());
    assertEquals("addFeaturessetImageInPanelsetHistogramInPanel",out.toString());
  }

  @Test
  public void testBlur() throws IOException {
    testController.blur();
    assertEquals("blurgetRawImageFromHashMapgenerateHistogramgetRawImageFromHashMap",
        log.toString());
    assertEquals("addFeaturessetImageInPanelsetHistogramInPanel",out.toString());
  }

  @Test
  public void testSharpen() throws IOException {
    testController.sharpen();
    assertEquals("sharpengetRawImageFromHashMapgenerateHistogramgetRawImageFromHashMap",
        log.toString());
    assertEquals("addFeaturessetImageInPanelsetHistogramInPanel",out.toString());
  }

  @Test
  public void testSepia() throws IOException {
    testController.sepia();
    assertEquals("sepiagetRawImageFromHashMapgenerateHistogramgetRawImageFromHashMap",
        log.toString());
    assertEquals("addFeaturessetImageInPanelsetHistogramInPanel",out.toString());
  }

  @Test
  public void testGreyScale() throws IOException {
    testController.greyscale();
    assertEquals("greyscalegetRawImageFromHashMapgenerateHistogramgetRawImageFromHashMap",
        log.toString());
    assertEquals("addFeaturessetImageInPanelsetHistogramInPanel",out.toString());
  }

}
