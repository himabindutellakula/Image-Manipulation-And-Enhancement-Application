package model;

/**
 * A class representing an image with pixel data.
 */
public class Image {

  /**
   * The width of the image.
   */
  private final int width;

  /**
   * The height of the image.
   */
  private final int height;

  /**
   * A 2D array containing pixel data in RGB format.
   */
  private final Pixel[][] pixelRGBData;

  /**
   * Constructs an image with the specified width and height.
   *
   * @param width  The width of the image.
   * @param height The height of the image.
   */
  public Image(int width, int height) {
    this.width = width;
    this.height = height;
    this.pixelRGBData = new Pixel[width][height];
  }

  /**
   * Get the width of the image.
   *
   * @return The width of the image.
   */
  public int getWidth() {
    return width;
  }

  /**
   * Get the height of the image.
   *
   * @return The height of the image.
   */
  public int getHeight() {
    return height;
  }

  /**
   * Get the pixel at the specified coordinates in RGB format.
   *
   * @param x The X-coordinate of the pixel.
   * @param y The Y-coordinate of the pixel.
   * @return The pixel at the specified coordinates.
   */
  public Pixel getPixelRGB(int x, int y) {
    return pixelRGBData[x][y];
  }

  /**
   * Set the pixel at the specified coordinates to the given pixel in RGB format.
   *
   * @param x         The X-coordinate of the pixel.
   * @param y         The Y-coordinate of the pixel.
   * @param tempPixel The pixel to set at the specified coordinates.
   */
  public void setPixelRGB(int x, int y, Pixel tempPixel) {
    pixelRGBData[x][y] = tempPixel;
  }
}
