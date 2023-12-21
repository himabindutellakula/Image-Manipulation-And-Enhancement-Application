import model.AdvancedImageManipulatorModel;

/**
 * A mock class for AdvancedImageManipulatorModel used for controller testing.
 */
public class MockModel implements AdvancedImageManipulatorModel {

  private StringBuilder log;

  public MockModel(StringBuilder log) {
    this.log = log;
  }

  @Override
  public void compress(double percentage, String srcImageName, String destImageName)
      throws IllegalArgumentException {
    log.append("compress");
  }

  @Override
  public void splitViewNew(String commandLine, double widthPercentage)
      throws IllegalArgumentException {
    log.append("splitView");
  }

  @Override
  public void generateHistogram(String srcImageName, String destImageName) {
    log.append("generateHistogram");
  }

  @Override
  public void colorCorrect(String srcImageName, String destImageName) {
    log.append("colorCorrect");
  }

  @Override
  public void adjustLevels(int b, int m, int w, String srcImageName, String destImageName)
      throws IllegalArgumentException {
    log.append("adjustLevels");
  }

  @Override
  public void addRawImageToHashMap(String key, String[] rawImage) {
    log.append("addRawImageToHashMap");
  }

  @Override
  public String[] getRawImageFromHashMap(String imageName) {
    log.append("getRawImageFromHashMap");
    return new String[0];
  }

  @Override
  public void rgbSplit(String srcImageName, String destRedImageName, String destGreenImageName,
      String destBlueImageName) {
    log.append("rgbSplit");
  }

  @Override
  public void rgbCombine(String srcRedImageName, String srcGreenImageName, String srcBlueImageName,
      String destImageName) {
    log.append("rgbCombine");
  }

  @Override
  public void horizontalFlip(String srcImageName, String destImageName) {
    log.append("horizontalFlip");
  }

  @Override
  public void verticalFlip(String srcImageName, String destImageName) {
    log.append("verticalFlip");
  }

  @Override
  public void brighten(int increment, String srcImageName, String destImageName) {
    log.append("brighten");
  }

  @Override
  public void greyscale(String component, String srcImageName, String destImageName) {
    log.append("greyscale");
  }

  @Override
  public void blur(String srcImageName, String destImageName) {
    log.append("blur");
  }

  @Override
  public void sharpen(String srcImageName, String destImageName) {
    log.append("sharpen");
  }

  @Override
  public void sepia(String srcImageName, String destImageName) {
    log.append("sepia");
  }

}
