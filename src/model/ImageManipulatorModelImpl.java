package model;

import java.util.HashMap;
import java.util.Map;

/**
 * The ImageManipulatorModelImpl class implements the ImageManipulatorModel interface
 * and provides various image manipulation operations.
 */
public class ImageManipulatorModelImpl implements ImageManipulatorModel {

  // Stores raw image data with keys.
  protected Map<String, String[]> rawImagesAlbum = new HashMap<>();
  // Stores processed images with keys.
  protected Map<String, Image> imagesAlbum = new HashMap<>();


  @Override
  public void rgbSplit(String srcImageName, String destRedImageName, String destGreenImageName,
                       String destBlueImageName) {
    // Retrieve the source image.
    Image srcImg = checkAndGetTheImage(srcImageName);
    if (srcImg == null) {
      return;
    }

    int width = srcImg.getWidth();
    int height = srcImg.getHeight();

    // Create images to store split channels.
    Image destRedImg = new Image(width, height);
    Image destGreenImg = new Image(width, height);
    Image destBlueImg = new Image(width, height);

    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        int srcImageRed = srcImg.getPixelRGB(x, y).getRed();
        int srcImageGreen = srcImg.getPixelRGB(x, y).getGreen();
        int srcImageBlue = srcImg.getPixelRGB(x, y).getBlue();

        // Split the RGB channels.
        destRedImg.setPixelRGB(x, y, new Pixel(srcImageRed, 0, 0));
        destGreenImg.setPixelRGB(x, y, new Pixel(0, srcImageGreen, 0));
        destBlueImg.setPixelRGB(x, y, new Pixel(0, 0, srcImageBlue));
      }
    }

    // Store the split channels in the HashMap.
    addImageToHashMap(destRedImageName, destRedImg);
    addImageToHashMap(destGreenImageName, destGreenImg);
    addImageToHashMap(destBlueImageName, destBlueImg);
  }

  @Override
  public void rgbCombine(String srcRedImageName, String srcGreenImageName, String srcBlueImageName,
                         String destImageName) {
    // Retrieve the source images for red, green, and blue channels.
    Image redImg = imagesAlbum.get(srcRedImageName);
    Image greenImg = imagesAlbum.get(srcGreenImageName);
    Image blueImg = imagesAlbum.get(srcBlueImageName);

    if (redImg == null || greenImg == null || blueImg == null) {
      System.err.println("One or more source images not found.");
      return;
    }

    int width = redImg.getWidth();
    int height = redImg.getHeight();

    // Create an image to combine the channels.
    Image destImg = new Image(width, height);

    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        int red = redImg.getPixelRGB(x, y).getRed();
        int green = greenImg.getPixelRGB(x, y).getGreen();
        int blue = blueImg.getPixelRGB(x, y).getBlue();

        // Combine the RGB channels.
        destImg.setPixelRGB(x, y, new Pixel(red, green, blue));
      }
    }

    // Store the combined image in the HashMap.
    addImageToHashMap(destImageName, destImg);
  }


  @Override
  public void horizontalFlip(String srcImageName, String destImageName) {
    // Retrieve the source image.
    Image srcImg = checkAndGetTheImage(srcImageName);
    if (srcImg == null) {
      return;
    }

    int width = srcImg.getWidth();
    int height = srcImg.getHeight();

    // Create an image for the horizontal flip.
    Image destImg = new Image(width, height);

    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        // Perform the horizontal flip.
        destImg.setPixelRGB(width - x - 1, y, srcImg.getPixelRGB(x, y));
      }
    }

    // Store the flipped image in the HashMap.
    addImageToHashMap(destImageName, destImg);
  }


  @Override
  public void verticalFlip(String srcImageName, String destImageName) {
    // Retrieve the source image.
    Image srcImg = checkAndGetTheImage(srcImageName);
    if (srcImg == null) {
      return;
    }

    int width = srcImg.getWidth();
    int height = srcImg.getHeight();

    // Create an image for the vertical flip.
    Image destImg = new Image(width, height);

    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        // Perform the vertical flip.
        destImg.setPixelRGB(x, height - y - 1, srcImg.getPixelRGB(x, y));
      }
    }

    // Store the flipped image in the HashMap.
    addImageToHashMap(destImageName, destImg);
  }


  @Override
  public void brighten(int increment, String srcImageName, String destImageName) {
    // Retrieve the source image.
    Image srcImg = checkAndGetTheImage(srcImageName);
    if (srcImg == null) {
      return;
    }

    int width = srcImg.getWidth();
    int height = srcImg.getHeight();

    // Create an image for the brightened result.
    Image destImg = new Image(width, height);

    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        int redValue = clampValue(srcImg.getPixelRGB(x, y).getRed() + increment);
        int greenValue = clampValue(srcImg.getPixelRGB(x, y).getGreen() + increment);
        int blueValue = clampValue(srcImg.getPixelRGB(x, y).getBlue() + increment);

        // Adjust the pixel values to brighten the image.
        destImg.setPixelRGB(x, y, new Pixel(redValue, greenValue, blueValue));
      }
    }

    // Store the brightened image in the HashMap.
    addImageToHashMap(destImageName, destImg);
  }


  @Override
  public void blur(String srcImageName, String destImageName) {
    // Retrieve the source image.
    Image srcImg = checkAndGetTheImage(srcImageName);
    if (srcImg == null) {
      return;
    }

    // Apply a blur filter to the source image and store the result.
    KernelFilter blurFilter = new BlurKernelFilter();
    addImageToHashMap(destImageName, blurFilter.applyFilterToImage(srcImg));
  }

  @Override
  public void sharpen(String srcImageName, String destImageName) {
    // Retrieve the source image.
    Image srcImg = checkAndGetTheImage(srcImageName);
    if (srcImg == null) {
      return;
    }

    // Apply a sharpen filter to the source image and store the result.
    KernelFilter sharpenFilter = new SharpenKernelFilter();
    addImageToHashMap(destImageName, sharpenFilter.applyFilterToImage(srcImg));
  }


  @Override
  public void greyscale(String component, String srcImageName, String destImageName) {
    Image srcImg = checkAndGetTheImage(srcImageName);
    if (srcImg == null) {
      return;
    }
    ImageFilter greyScaleFilter = new GreyScaleFilter(component);
    addImageToHashMap(destImageName, greyScaleFilter.applyFilterToImage(srcImg));
  }

  @Override
  public void sepia(String srcImageName, String destImageName) {
    // Retrieve the source image.
    Image srcImg = checkAndGetTheImage(srcImageName);
    if (srcImg == null) {
      return;
    }

    // Apply a sepia color transformation to the source image and store the result.
    ColorTransformationFilter sepiaFilter = new SepiaColorTransformationFilter();
    addImageToHashMap(destImageName, sepiaFilter.applyFilterToImage(srcImg));
  }


  @Override
  public void addRawImageToHashMap(String key, String[] rawImage) {
    // Store raw image data in the HashMap.
    rawImagesAlbum.put(key, rawImage);
    imagesAlbum.put(key, createFromRawImage(rawImage));
  }

  @Override
  public String[] getRawImageFromHashMap(String key) {
    // Retrieve raw image data from the HashMap.
    return rawImagesAlbum.get(key);
  }


  /**
   * Stores the processed image in the imagesAlbum HashMap.
   *
   * @param key   The key for the processed image.
   * @param value The processed Image object.
   */
  public void addImageToHashMap(String key, Image value) {
    imagesAlbum.put(key, value);
    rawImagesAlbum.put(key, convertIntoRawImage(value));
  }

  /**
   * Creates an Image object from raw image data.
   *
   * @param rawImage The raw image data.
   * @return An Image object.
   */
  private static Image createFromRawImage(String[] rawImage) {
    if (rawImage == null || rawImage.length < 2) {
      throw new IllegalArgumentException("Invalid raw image data.");
    }
    int width = Integer.parseInt(rawImage[0]);
    int height = Integer.parseInt(rawImage[1]);
    Image image = new Image(width, height);
    if (rawImage.length != height + 2) {
      throw new IllegalArgumentException("Invalid raw image data.");
    }
    for (int y = 0; y < height; y++) {
      String line = rawImage[y + 2];
      if (line.length() != width * 9) { // 3 digits for each color channel (RGB)
        throw new IllegalArgumentException("Invalid raw image data.");
      }
      for (int x = 0; x < width; x++) {
        int startIndex = x * 9;
        int red = Integer.parseInt(line.substring(startIndex, startIndex + 3));
        int green = Integer.parseInt(line.substring(startIndex + 3, startIndex + 6));
        int blue = Integer.parseInt(line.substring(startIndex + 6, startIndex + 9));
        image.setPixelRGB(x, y, new Pixel(red, green, blue));
      }
    }
    return image;
  }

  /**
   * Converts an Image object into raw image data.
   *
   * @param img The Image object.
   * @return Raw image data as a String array.
   */
  private String[] convertIntoRawImage(Image img) {
    int height = img.getHeight();
    int width = img.getWidth();
    String[] rawImage = new String[height + 2];
    rawImage[0] = String.valueOf(width);
    rawImage[1] = String.valueOf(height);

    for (int y = 0; y < height; y++) {
      StringBuilder line = new StringBuilder();
      for (int x = 0; x < width; x++) {
        Pixel pixel = img.getPixelRGB(x, y);
        line.append(
                String.format("%03d%03d%03d", pixel.getRed(), pixel.getGreen(), pixel.getBlue()));
      }
      rawImage[y + 2] = line.toString();
    }
    return rawImage;
  }

  /**
   * Retrieves an image from the imagesAlbum HashMap.
   *
   * @param imgName The key of the image to retrieve.
   * @return The Image object or null if not found.
   */
  public Image checkAndGetTheImage(String imgName) {
    Image srcImg = imagesAlbum.get(imgName);
    if (srcImg == null) {
      System.err.println("Source image not found: " + imgName);
      return null;
    }
    return srcImg;
  }

  /**
   * Ensures that the provided pixel value is within the range [0, 255].
   *
   * @param value The pixel value to clamp.
   * @return The clamped pixel value.
   */
  protected int clampValue(int value) {
    return Math.max(0, Math.min(255, value));
  }

}
