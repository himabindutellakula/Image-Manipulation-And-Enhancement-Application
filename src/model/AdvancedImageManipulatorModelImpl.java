package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of the AdvancedImageManipulatorModel interface. Extends the
 * ImageManipulatorModelImpl class to provide advanced image manipulation operations.
 */
public class AdvancedImageManipulatorModelImpl extends ImageManipulatorModelImpl
        implements AdvancedImageManipulatorModel {

  @Override
  public void compress(double percentage, String srcImageName, String destImageName)
          throws IllegalArgumentException {
    if (percentage < 0 || percentage > 100) {
      throw new IllegalArgumentException("Percentage must not be negative.");
    }
    Image srcImg = checkAndGetTheImage(srcImageName);
    if (srcImg == null) {
      return;
    }
    ImageCompression ic = new ImageCompressionImpl();
    this.addImageToHashMap(destImageName, ic.compress(srcImg, percentage));
  }

  @Override
  public void splitViewNew(String commandLine,
                           double widthPercentage) throws IllegalArgumentException {
    String[] tokens = commandLine.split("\\s+");
    String operation = tokens[0].toLowerCase();
    List<String> supportedOperations = new ArrayList<>(
            List.of("blur", "sharpen", "sepia", "red-component",
                    "green-component", "blue-component", "value-component", "luma-component",
                    "intensity-component", "color-correct", "levels-adjust"));
    if (widthPercentage < 0 || widthPercentage > 100 || !supportedOperations.contains(operation)) {
      throw new IllegalArgumentException("Percentage is invalid or operation is unsupported");
    }
    String srcImageName;
    String destImageName;
    if (operation.equals("levels-adjust")) {
      srcImageName = tokens[4];
      destImageName = tokens[5];
    } else {
      srcImageName = tokens[1];
      destImageName = tokens[2];
    }

    Image srcImg = checkAndGetTheImage(srcImageName);
    if (srcImg == null) {
      return;
    }

    int splitWidth = getCropWidth(widthPercentage, srcImg.getWidth());
    Image splittedImage = getCroppedImage(splitWidth, srcImg);
    switch (operation) {
      case "blur":
        KernelFilter blurFilter = new BlurKernelFilter();
        splittedImage = blurFilter.applyFilterToImage(splittedImage);
        break;
      case "sharpen":
        ImageFilter sharpenFilter = new SharpenKernelFilter();
        splittedImage = sharpenFilter.applyFilterToImage(splittedImage);
        break;
      case "sepia":
        ImageFilter sepiaFilter = new SepiaColorTransformationFilter();
        splittedImage = sepiaFilter.applyFilterToImage(splittedImage);
        break;
      case "red-component":
      case "green-component":
      case "blue-component":
      case "value-component":
      case "luma-component":
      case "intensity-component":
        ImageFilter greyScaleFilter = new GreyScaleFilter(operation);
        splittedImage = greyScaleFilter.applyFilterToImage(splittedImage);
        break;
      case "color-correct":
        colorCorrect(srcImageName, "partialSplit");
        splittedImage = checkAndGetTheImage("partialSplit");
        break;
      case "levels-adjust":
        adjustLevels(Integer.parseInt(tokens[1]), Integer.parseInt(tokens[2]),
                Integer.parseInt(tokens[3]), srcImageName, "partialSplit");
        splittedImage = checkAndGetTheImage("partialSplit");
        break;
      default:
        break;
    }
    addImageToHashMap(destImageName, getConcatenatedImage(splitWidth, splittedImage, srcImg));
  }

  /**
   * Calculates the width for cropping based on the provided width percentage and original width.
   *
   * @param widthPercentage The percentage of the original width to be cropped.
   * @param originalWidth   The original width of the image.
   * @return The calculated width for cropping.
   */
  private int getCropWidth(double widthPercentage, int originalWidth) {
    double v = (widthPercentage / 100) * originalWidth;
    return (int) v;
  }

  /**
   * Retrieves a cropped portion of the source image with the specified crop width.
   *
   * @param cropWidth The width of the cropped portion.
   * @param srcImage  The source image.
   * @return An Image object representing the cropped portion.
   */
  private Image getCroppedImage(int cropWidth, Image srcImage) {
    Image croppedImage = new Image(cropWidth, srcImage.getHeight());
    for (int y = 0; y < srcImage.getHeight(); y++) {
      for (int x = 0; x < cropWidth; x++) {
        croppedImage.setPixelRGB(x, y,
                new Pixel(srcImage.getPixelRGB(x, y).getRed(),
                        srcImage.getPixelRGB(x, y).getGreen(),
                        srcImage.getPixelRGB(x, y).getBlue()));
      }
    }
    return croppedImage;
  }


  /**
   * Concatenates a modified image with a source image based on the specified crop width.
   *
   * @param cropWidth   The width at which the images are concatenated.
   * @param modifiedImg The modified image to be concatenated.
   * @param srcImage    The source image.
   * @return An Image object representing the concatenated image.
   */
  private Image getConcatenatedImage(int cropWidth, Image modifiedImg, Image srcImage) {
    Image concatenatedImage = new Image(srcImage.getWidth(), srcImage.getHeight());
    for (int y = 0; y < srcImage.getHeight(); y++) {
      for (int x = 0; x < srcImage.getWidth(); x++) {
        if (x < cropWidth) {
          concatenatedImage.setPixelRGB(x, y, modifiedImg.getPixelRGB(x, y));
        } else {
          concatenatedImage.setPixelRGB(x, y, srcImage.getPixelRGB(x, y));
        }
      }
    }
    return concatenatedImage;
  }

  @Override
  public void generateHistogram(String srcImageName, String destImageName) {
    Image srcImg = checkAndGetTheImage(srcImageName);
    if (srcImg == null) {
      return;
    }
    SimpleHistogramVisualization histVis = new SimpleHistogramVisualization(srcImg);
    addRawImageToHashMap(destImageName, histVis.generateHistogram());
  }

  @Override
  public void colorCorrect(String srcImageName, String destImageName) {
    Image srcImg = checkAndGetTheImage(srcImageName);
    if (srcImg == null) {
      return;
    }
    AbstractImageHistogram colorCorrectCls = new ColorCorrectionWithHistogram(srcImg);
    addImageToHashMap(destImageName,
            ((ColorCorrectionWithHistogram) colorCorrectCls).colorCorrect(srcImg));
  }

  @Override
  public void adjustLevels(int b, int m, int w, String srcImageName, String destImageName)
          throws IllegalArgumentException {
    if ((b < 0 || b > 255) || (m < 0 || m > 255) || (w < 0 || w > 255) || !(b < m && m < w)) {
      throw new IllegalArgumentException("Invalid b m w values");
    }
    Image srcImg = checkAndGetTheImage(srcImageName);
    if (srcImg == null) {
      return;
    }
    Image adjustedLevelsImage = new Image(srcImg.getWidth(), srcImg.getHeight());
    double squareB = Math.pow(b, 2);
    double squareM = (int) Math.pow(m, 2);
    double squareW = (int) Math.pow(w, 2);

    double a = (squareB * (m - w)) - (b * (squareM - squareW)) + (w * squareM) - (m * squareW);
    double aA = (-b * (128 - 255)) + (128 * w) - (255 * m);
    double bA = (squareB * (128 - 255)) + (255 * squareM) - (128 * squareW);
    double cA = (squareB * ((255 * m) - (128 * w))) - (b * ((255 * squareM) - (128 * squareW)));
    double aCoefficient = (aA / a);
    double bCoefficient = (bA / a);
    double cCoefficient = (cA / a);

    for (int y = 0; y < srcImg.getHeight(); y++) {
      for (int x = 0; x < srcImg.getWidth(); x++) {
        Pixel currentPixel = srcImg.getPixelRGB(x, y);
        int red = currentPixel.getRed();
        int green = currentPixel.getGreen();
        int blue = currentPixel.getBlue();
        red = adjustLevel(red, aCoefficient, bCoefficient, cCoefficient);
        green = adjustLevel(green, aCoefficient, bCoefficient, cCoefficient);
        blue = adjustLevel(blue, aCoefficient, bCoefficient, cCoefficient);
        adjustedLevelsImage.setPixelRGB(x, y, new Pixel(red, green, blue));
      }
    }
    addImageToHashMap(destImageName, adjustedLevelsImage);
  }

  /**
   * Calculates the adjusted values by fitting them in the quadratic equation.
   *
   * @param channelValue current value of the channel, acts as x in quadratic equation
   * @param aCoefficient coefficient a of the quadratic equation
   * @param bCoefficient coefficient b of the quadratic equation
   * @param cCoefficient coefficient c of the quadratic equation
   * @return adjusted value for each pixel
   */
  private int adjustLevel(int channelValue, double aCoefficient,
                          double bCoefficient, double cCoefficient) {
    int adjustedValue = (int) ((aCoefficient * Math.pow(channelValue, 2)) + (bCoefficient
            * channelValue) + cCoefficient);
    return Math.max(0, Math.min(255, adjustedValue));
  }

}
