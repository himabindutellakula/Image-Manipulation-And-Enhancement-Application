import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import model.Image;
import model.ImageManipulatorModelImpl;
import model.Pixel;
import org.junit.Before;
import org.junit.Test;

/**
 * Test class to test ImageManipulatorModel class.
 */
public class ImageManipulatorModelTest {

  private ImageManipulatorModelImpl imageManipulatorModel;
  private Image testImage;

  @Before
  public void setUp() {
    imageManipulatorModel = new ImageManipulatorModelImpl();
    testImage = createTestImage();
    imageManipulatorModel.addImageToHashMap("testImage", testImage);
  }

  private Image createTestImage() {
    Image testImage = new Image(5, 5);
    for (int y = 0; y < 5; y++) {
      for (int x = 0; x < 5; x++) {
        testImage.setPixelRGB(x, y, new Pixel(x * 25, y * 25, (x + y) * 25));
      }
    }
    return testImage;
  }

  @Test
  public void testRgbSplit() {
    imageManipulatorModel.rgbSplit("testImage", "redImage",
        "greenImage", "blueImage");

    Image redImage = imageManipulatorModel.checkAndGetTheImage("redImage");
    Image greenImage = imageManipulatorModel.checkAndGetTheImage("greenImage");
    Image blueImage = imageManipulatorModel.checkAndGetTheImage("blueImage");

    for (int y = 0; y < 5; y++) {
      for (int x = 0; x < 5; x++) {
        Pixel originalPixel = testImage.getPixelRGB(x, y);
        Pixel redPixel = redImage.getPixelRGB(x, y);
        Pixel greenPixel = greenImage.getPixelRGB(x, y);
        Pixel bluePixel = blueImage.getPixelRGB(x, y);

        assertEquals(originalPixel.getRed(), redPixel.getRed());
        assertEquals(0, redPixel.getGreen());
        assertEquals(0, redPixel.getBlue());

        assertEquals(0, greenPixel.getRed());
        assertEquals(originalPixel.getGreen(), greenPixel.getGreen());
        assertEquals(0, greenPixel.getBlue());

        assertEquals(0, bluePixel.getRed());
        assertEquals(0, bluePixel.getGreen());
        assertEquals(originalPixel.getBlue(), bluePixel.getBlue());
      }
    }
  }

  @Test
  public void testRgbCombine() {
    imageManipulatorModel.rgbSplit("testImage", "redImage",
        "greenImage", "blueImage");
    imageManipulatorModel.rgbCombine("redImage", "greenImage",
        "blueImage", "combinedImage");

    Image combinedImage = imageManipulatorModel.checkAndGetTheImage("combinedImage");

    for (int y = 0; y < 5; y++) {
      for (int x = 0; x < 5; x++) {
        Pixel originalPixel = testImage.getPixelRGB(x, y);
        Pixel combinedPixel = combinedImage.getPixelRGB(x, y);

        assertEquals(originalPixel.getRed(), combinedPixel.getRed());
        assertEquals(originalPixel.getGreen(), combinedPixel.getGreen());
        assertEquals(originalPixel.getBlue(), combinedPixel.getBlue());
      }
    }
  }

  @Test
  public void testHorizontalFlip() {
    imageManipulatorModel.horizontalFlip("testImage", "flippedImage");

    Image flippedImage = imageManipulatorModel.checkAndGetTheImage("flippedImage");

    for (int y = 0; y < 5; y++) {
      for (int x = 0; x < 5; x++) {
        Pixel originalPixel = testImage.getPixelRGB(x, y);
        Pixel flippedPixel = flippedImage.getPixelRGB(4 - x, y);

        assertEquals(originalPixel.getRed(), flippedPixel.getRed());
        assertEquals(originalPixel.getGreen(), flippedPixel.getGreen());
        assertEquals(originalPixel.getBlue(), flippedPixel.getBlue());
      }
    }
  }

  @Test
  public void testVerticalFlip() {
    imageManipulatorModel.verticalFlip("testImage", "flippedImage");

    Image flippedImage = imageManipulatorModel.checkAndGetTheImage("flippedImage");

    for (int y = 0; y < 5; y++) {
      for (int x = 0; x < 5; x++) {
        Pixel originalPixel = testImage.getPixelRGB(x, y);
        Pixel flippedPixel = flippedImage.getPixelRGB(x, 4 - y);

        assertEquals(originalPixel.getRed(), flippedPixel.getRed());
        assertEquals(originalPixel.getGreen(), flippedPixel.getGreen());
        assertEquals(originalPixel.getBlue(), flippedPixel.getBlue());
      }
    }
  }

  @Test
  public void testBrighten() {
    int increment = 50;
    imageManipulatorModel.brighten(increment, "testImage",
        "brightenedImage");

    Image brightenedImage = imageManipulatorModel.checkAndGetTheImage("brightenedImage");

    for (int y = 0; y < 5; y++) {
      for (int x = 0; x < 5; x++) {
        Pixel originalPixel = testImage.getPixelRGB(x, y);
        Pixel brightenedPixel = brightenedImage.getPixelRGB(x, y);

        int expectedRed = Math.min(originalPixel.getRed() + increment, 255);
        int expectedGreen = Math.min(originalPixel.getGreen() + increment, 255);
        int expectedBlue = Math.min(originalPixel.getBlue() + increment, 255);

        assertEquals(expectedRed, brightenedPixel.getRed());
        assertEquals(expectedGreen, brightenedPixel.getGreen());
        assertEquals(expectedBlue, brightenedPixel.getBlue());
      }
    }
  }


  @Test
  public void testSepia() {
    Image srcImg = createTestImage();
    imageManipulatorModel.sepia("testImage", "sepiaImage");

    Image filteredImage = imageManipulatorModel.checkAndGetTheImage("sepiaImage");

    for (int y = 0; y < 5; y++) {
      for (int x = 0; x < 5; x++) {
        Pixel originalPixel = srcImg.getPixelRGB(x, y);
        Pixel filteredPixel = filteredImage.getPixelRGB(x, y);

        int expectedRed = (int) ((0.393 * originalPixel.getRed()) + (0.769
            * originalPixel.getGreen()) + (0.189 * originalPixel.getBlue()));
        int expectedGreen = (int) ((0.349 * originalPixel.getRed()) + (0.686
            * originalPixel.getGreen()) + (0.168 * originalPixel.getBlue()));
        int expectedBlue = (int) ((0.272 * originalPixel.getRed()) + (0.534
            * originalPixel.getGreen()) + (0.131 * originalPixel.getBlue()));

        assertEquals(expectedRed, filteredPixel.getRed());
        assertEquals(expectedGreen, filteredPixel.getGreen());
        assertEquals(expectedBlue, filteredPixel.getBlue());
      }
    }
  }


  @Test
  public void testBlur() {
    Image srcImg = createTestImage();
    imageManipulatorModel.blur("testImage", "blurImage");

    Image filteredImage = imageManipulatorModel.checkAndGetTheImage("blurImage");

    for (int y = 0; y < 5; y++) {
      for (int x = 0; x < 5; x++) {
        Pixel originalPixel = srcImg.getPixelRGB(x, y);
        Pixel filteredPixel = filteredImage.getPixelRGB(x, y);

        // Calculate the expected Gaussian values for the original pixel
        int expectedRed = calculateGaussianValue(srcImg, x, y, "red");
        int expectedGreen = calculateGaussianValue(srcImg, x, y, "green");
        int expectedBlue = calculateGaussianValue(srcImg, x, y, "blue");

        // Compare the calculated expected values with the filtered pixel values
        assertEquals(expectedRed, filteredPixel.getRed());
        assertEquals(expectedGreen, filteredPixel.getGreen());
        assertEquals(expectedBlue, filteredPixel.getBlue());
      }
    }
  }

  private static final double[][] gaussianFilter = {
      {1.0 / 16, 1.0 / 8, 1.0 / 16},
      {1.0 / 8, 1.0 / 4, 1.0 / 8},
      {1.0 / 16, 1.0 / 8, 1.0 / 16}
  };


  private int calculateGaussianValue(Image srcImg, int x, int y, String color) {
    int limit = gaussianFilter.length / 2;
    double result = 0.0;
    for (int i = -limit; i <= limit; i++) {
      for (int j = -limit; j <= limit; j++) {
        int neighborX = x + i;
        int neighborY = y + j;
        if (neighborX < 0 || neighborX >= srcImg.getWidth() || neighborY < 0
            || neighborY >= srcImg.getHeight()) {
          continue;
        }
        double kernelValue = gaussianFilter[i + limit][j + limit];
        Pixel neighborPixel = srcImg.getPixelRGB(neighborX, neighborY);
        if (color.equals("red")) {
          result += (kernelValue * neighborPixel.getRed());
        } else if (color.equals("green")) {
          result += (kernelValue * neighborPixel.getGreen());
        } else if (color.equals("blue")) {
          result += (kernelValue * neighborPixel.getBlue());
        }
      }
    }
    return (int) result;
  }

  private static final double[][] sharpenKernel = {
      {-1.0 / 8, -1.0 / 8, -1.0 / 8, -1.0 / 8, -1.0 / 8},
      {-1.0 / 8, 1.0 / 4, 1.0 / 4, 1.0 / 4, -1.0 / 8},
      {-1.0 / 8, 1.0 / 4, 1.0, 1.0 / 4, -1.0 / 8},
      {-1.0 / 8, 1.0 / 4, 1.0 / 4, 1.0 / 4, -1.0 / 8},
      {-1.0 / 8, -1.0 / 8, -1.0 / 8, -1.0 / 8, -1.0 / 8}
  };

  private int calculateSharpenValue(Image srcImg, int x, int y, String color) {
    int limit = sharpenKernel.length / 2;
    double result = 0.0;
    for (int i = -limit; i <= limit; i++) {
      for (int j = -limit; j <= limit; j++) {
        int neighborX = x + i;
        int neighborY = y + j;
        if (neighborX < 0 || neighborX >= srcImg.getWidth() || neighborY < 0
            || neighborY >= srcImg.getHeight()) {
          continue;
        }
        double kernelValue = sharpenKernel[i + limit][j + limit];
        Pixel neighborPixel = srcImg.getPixelRGB(neighborX, neighborY);
        if (color.equals("red")) {
          result += (kernelValue * neighborPixel.getRed());
        } else if (color.equals("green")) {
          result += (kernelValue * neighborPixel.getGreen());
        } else if (color.equals("blue")) {
          result += (kernelValue * neighborPixel.getBlue());
        }
      }
    }
    return clampValue((int) result, 0, 255);
  }


  @Test
  public void testSharpen() {
    int[][] testImagePixels = {
        {100, 150, 200, 50, 75},
        {25, 175, 225, 125, 100},
        {75, 125, 250, 200, 150},
        {200, 100, 50, 175, 225},
        {150, 50, 175, 225, 125}
    };

    Image testImage = createTestImageWithArray(testImagePixels);

    imageManipulatorModel.addImageToHashMap("testImage", testImage);
    imageManipulatorModel.sharpen("testImage", "sharpenImage");

    Image filteredImage = imageManipulatorModel.checkAndGetTheImage("sharpenImage");

    int[][] sharpenedImagePixels = {
        {78, 228, 243, 100, 15},
        {53, 255, 255, 255, 109},
        {62, 200, 255, 255, 184},
        {212, 225, 178, 255, 255},
        {153, 87, 137, 255, 178}
    };

    for (int y = 0; y < 5; y++) {
      for (int x = 0; x < 5; x++) {
        Pixel pixel = filteredImage.getPixelRGB(x, y);
        int expectedRed = sharpenedImagePixels[y][x];
        int expectedGreen = sharpenedImagePixels[y][x];
        int expectedBlue = sharpenedImagePixels[y][x];

        assertEquals("Red value mismatch at (" + x + ", " + y + ")",
            expectedRed, pixel.getRed());
        assertEquals("Green value mismatch at (" + x + ", " + y + ")",
            expectedGreen,
            pixel.getGreen());
        assertEquals("Blue value mismatch at (" + x + ", " + y + ")",
            expectedBlue,
            pixel.getBlue());
      }
    }
  }

  private int clampValue(int value, int min, int max) {
    if (value < min) {
      return min;
    } else if (value > max) {
      return max;
    } else {
      return value;
    }
  }

  private Image createTestImageWithArray(int[][] pixels) {
    int width = pixels[0].length;
    int height = pixels.length;
    Image testImage = new Image(width, height);

    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        int red = pixels[y][x];
        int green = pixels[y][x];
        int blue = pixels[y][x];
        testImage.setPixelRGB(x, y, new Pixel(red, green, blue));
      }
    }

    return testImage;
  }

  @Test
  public void testGreyscale() {
    Image srcImage = new Image(2, 2);
    srcImage.setPixelRGB(0, 0, new Pixel(100, 150, 200));
    srcImage.setPixelRGB(1, 0, new Pixel(50, 75, 100));
    srcImage.setPixelRGB(0, 1, new Pixel(25, 38, 51));
    srcImage.setPixelRGB(1, 1, new Pixel(200, 0, 100));
    imageManipulatorModel.addImageToHashMap("srcImage", srcImage);

    imageManipulatorModel.greyscale("red-component", "srcImage",
        "redGreyscaleImage");
    imageManipulatorModel.greyscale("green-component", "srcImage",
        "greenGreyscaleImage");
    imageManipulatorModel.greyscale("blue-component", "srcImage",
        "blueGreyscaleImage");
    imageManipulatorModel.greyscale("value-component", "srcImage",
        "valueGreyscaleImage");
    imageManipulatorModel.greyscale("luma-component", "srcImage",
        "lumaGreyscaleImage");
    imageManipulatorModel.greyscale("intensity-component", "srcImage",
        "intensityGreyscaleImage");

    Image redGreyscaleImage =
        imageManipulatorModel.checkAndGetTheImage("redGreyscaleImage");
    Image greenGreyscaleImage =
        imageManipulatorModel.checkAndGetTheImage("greenGreyscaleImage");
    Image blueGreyscaleImage =
        imageManipulatorModel.checkAndGetTheImage("blueGreyscaleImage");
    Image valueGreyscaleImage =
        imageManipulatorModel.checkAndGetTheImage("valueGreyscaleImage");
    Image lumaGreyscaleImage =
        imageManipulatorModel.checkAndGetTheImage("lumaGreyscaleImage");
    Image intensityGreyscaleImage =
        imageManipulatorModel.checkAndGetTheImage(
            "intensityGreyscaleImage");

    assertNotNull(redGreyscaleImage);
    assertNotNull(greenGreyscaleImage);
    assertNotNull(blueGreyscaleImage);
    assertNotNull(valueGreyscaleImage);
    assertNotNull(lumaGreyscaleImage);
    assertNotNull(intensityGreyscaleImage);

    assertEquals(100, redGreyscaleImage.getPixelRGB(0, 0).getRed());
    assertEquals(0, redGreyscaleImage.getPixelRGB(0, 0).getGreen());
    assertEquals(0, redGreyscaleImage.getPixelRGB(0, 0).getBlue());
    assertEquals(0, greenGreyscaleImage.getPixelRGB(0, 0).getRed());
    assertEquals(150, greenGreyscaleImage.getPixelRGB(0, 0).getGreen());
    assertEquals(0, greenGreyscaleImage.getPixelRGB(0, 0).getBlue());
    assertEquals(0, blueGreyscaleImage.getPixelRGB(0, 0).getRed());
    assertEquals(0, blueGreyscaleImage.getPixelRGB(0, 0).getGreen());
    assertEquals(200, blueGreyscaleImage.getPixelRGB(0, 0).getBlue());
    assertEquals(200, valueGreyscaleImage.getPixelRGB(0, 0).getRed());
    assertEquals(200, valueGreyscaleImage.getPixelRGB(0, 0).getGreen());
    assertEquals(200, valueGreyscaleImage.getPixelRGB(0, 0).getBlue());
    assertEquals(142, lumaGreyscaleImage.getPixelRGB(0, 0).getRed());
    assertEquals(142, lumaGreyscaleImage.getPixelRGB(0, 0).getGreen());
    assertEquals(142, lumaGreyscaleImage.getPixelRGB(0, 0).getBlue());
    assertEquals(150, intensityGreyscaleImage.getPixelRGB(0, 0).getRed());
    assertEquals(150, intensityGreyscaleImage.getPixelRGB(0, 0).getGreen());
    assertEquals(150, intensityGreyscaleImage.getPixelRGB(0, 0).getBlue());
  }

}
