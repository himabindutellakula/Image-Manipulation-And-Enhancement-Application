package model;

/**
 * The Pixel class represents a color pixel with red, green, and blue components. It provides
 * methods for creating, accessing, and manipulating pixel values.
 */
public class Pixel {

  private int red;
  private int green;
  private int blue;

  /**
   * Constructs a Pixel object with specified red, green, and blue components.
   *
   * @param red   The red component value (0-255).
   * @param green The green component value (0-255).
   * @param blue  The blue component value (0-255).
   */
  public Pixel(int red, int green, int blue) {
    this.red = red;
    this.green = green;
    this.blue = blue;
  }

  /**
   * Returns the red component of the pixel.
   *
   * @return The red component value (0-255).
   */
  public int getRed() {
    return red;
  }

  /**
   * Returns the green component of the pixel.
   *
   * @return The green component value (0-255).
   */
  public int getGreen() {
    return green;
  }

  /**
   * Returns the blue component of the pixel.
   *
   * @return The blue component value (0-255).
   */
  public int getBlue() {
    return blue;
  }

  /**
   * Sets the red component of the pixel.
   *
   * @param red The new red component value (0-255).
   */
  public void setRed(int red) {
    this.red = red;
  }

  /**
   * Sets the green component of the pixel.
   *
   * @param green The new green component value (0-255).
   */
  public void setGreen(int green) {
    this.green = green;
  }

  /**
   * Sets the blue component of the pixel.
   *
   * @param blue The new blue component value (0-255).
   */
  public void setBlue(int blue) {
    this.blue = blue;
  }

  /**
   * Computes the value component, which is the maximum of the red, green, and blue components.
   *
   * @return The value component value (0-255).
   */
  public int getValueComponent() {
    return Math.max(Math.max(red, green), blue);
  }

  /**
   * Computes the luma component using the Y'UV color space conversion formula.
   *
   * @return The luma component value (0-255).
   */
  public int getLumaComponent() {
    return (int) (0.2126 * red + 0.7152 * green + 0.0722 * blue);
  }

  /**
   * Computes the intensity component, which is the average of the red, green, and blue components.
   *
   * @return The intensity component value (0-255).
   */
  public int getIntensityComponent() {
    return (red + green + blue) / 3;
  }

  @Override
  public String toString() {
    return this.red + ' ' + this.getGreen() + " " + this.getBlue();
  }
}
