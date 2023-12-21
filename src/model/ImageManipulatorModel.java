package model;

/**
 * Interface for an image manipulator application model. Allows operations such as greyscale
 * conversion, flipping, brightening, RGB splitting, combining, sepia effect, blur, and sharpening
 * on images.
 */
public interface ImageManipulatorModel {

  /**
   * Adds a raw image to the internal HashMap with the given key.
   *
   * @param key      The key to associate with the raw image.
   * @param rawImage An array representing the raw image data.
   */
  void addRawImageToHashMap(String key, String[] rawImage);

  /**
   * Retrieves the raw image associated with the given image name from the internal HashMap.
   *
   * @param imageName The name of the image.
   * @return The raw image data as an array, or null if the image is not found.
   */
  String[] getRawImageFromHashMap(String imageName);

  /**
   * Splits the source image into its red, green, and blue components.
   *
   * @param srcImageName       The name of the source image.
   * @param destRedImageName   The name to associate with the resulting red component image.
   * @param destGreenImageName The name to associate with the resulting green component image.
   * @param destBlueImageName  The name to associate with the resulting blue component image.
   */
  void rgbSplit(String srcImageName, String destRedImageName, String destGreenImageName,
      String destBlueImageName);

  /**
   * Combines the red, green, and blue component images into a single image.
   *
   * @param srcRedImageName   The name of the red component image.
   * @param srcGreenImageName The name of the green component image.
   * @param srcBlueImageName  The name of the blue component image.
   * @param destImageName     The name to associate with the resulting combined image.
   */
  void rgbCombine(String srcRedImageName, String srcGreenImageName, String srcBlueImageName,
      String destImageName);

  /**
   * Flips the source image horizontally and associates the result, with the given destination
   * image.
   *
   * @param srcImageName  The name of the source image.
   * @param destImageName The name to associate with the resulting flipped image.
   */
  void horizontalFlip(String srcImageName, String destImageName);

  /**
   * Flips the source image vertically and associates the result, with the given destination image
   * .
   *
   * @param srcImageName  The name of the source image.
   * @param destImageName The name to associate with the resulting flipped image.
   */
  void verticalFlip(String srcImageName, String destImageName);

  /**
   * Brightens the source image by the specified increment and associates, the result with the
   * image.
   *
   * @param increment     The brightness increment.
   * @param srcImageName  The name of the source image.
   * @param destImageName The name to associate with the resulting brightened image.
   */
  void brighten(int increment, String srcImageName, String destImageName);

  /**
   * Converts the source image to a greyscale image using the specified component.
   *
   * @param component     The component to use for greyscale conversion.
   * @param srcImageName  The name of the source image.
   * @param destImageName The name to associate with the resulting greyscale image.
   */
  void greyscale(String component, String srcImageName, String destImageName);

  /**
   * Applies a blur effect to the source image and associates the result with the given destination
   * image.
   *
   * @param srcImageName  The name of the source image.
   * @param destImageName The name to associate with the resulting blurred image.
   */
  void blur(String srcImageName, String destImageName);

  /**
   * Applies a sharpening effect to the source image and associates the result with the given
   * destination image.
   *
   * @param srcImageName  The name of the source image.
   * @param destImageName The name to associate with the resulting sharpened image.
   */
  void sharpen(String srcImageName, String destImageName);

  /**
   * Applies a sepia effect to the source image and associates the result with the given destination
   * image.
   *
   * @param srcImageName  The name of the source image.
   * @param destImageName The name to associate with the resulting sepia image.
   */
  void sepia(String srcImageName, String destImageName);
}
