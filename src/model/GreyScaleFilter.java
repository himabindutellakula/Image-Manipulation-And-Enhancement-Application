package model;

/**
 * The GreyScaleFilter class implements the ImageFilter interface to create a greyscale version of
 * an image based on a specified color component.
 */
public class GreyScaleFilter implements ImageFilter {

  private final String component;

  /**
   * Constructs a new GreyScaleFilter with the specified color component.
   *
   * @param componentName The name of the color component to be used for creating the greyscale
   *                      image.
   */
  public GreyScaleFilter(String componentName) {
    this.component = componentName;
  }

  @Override
  public Image applyFilterToImage(Image srcImg) {
    Image resultImg = new Image(srcImg.getWidth(), srcImg.getHeight());

    for (int y = 0; y < srcImg.getHeight(); y++) {
      for (int x = 0; x < srcImg.getWidth(); x++) {
        int grey;
        Pixel currentPixel = srcImg.getPixelRGB(x, y);

        // Convert the pixel to greyscale based on the selected component.
        switch (this.component) {
          case "red-component":
            resultImg.setPixelRGB(x, y, new Pixel(currentPixel.getRed(), 0, 0));
            break;

          case "green-component":
            resultImg.setPixelRGB(x, y, new Pixel(0, currentPixel.getGreen(), 0));
            break;

          case "blue-component":
            resultImg.setPixelRGB(x, y, new Pixel(0, 0, currentPixel.getBlue()));
            break;

          case "value-component":
            grey = currentPixel.getValueComponent();
            resultImg.setPixelRGB(x, y, new Pixel(grey, grey, grey));
            break;

          case "luma-component":
            grey = currentPixel.getLumaComponent();
            resultImg.setPixelRGB(x, y, new Pixel(grey, grey, grey));
            break;

          case "intensity-component":
            grey = currentPixel.getIntensityComponent();
            resultImg.setPixelRGB(x, y, new Pixel(grey, grey, grey));
            break;

          default:
            throw new IllegalArgumentException("Invalid Component Type"
                + " for creating a greyscale image");
        }
      }
    }
    return resultImg;
  }
}
