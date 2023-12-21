import static org.junit.Assert.assertEquals;

import model.AbstractImageHistogram;
import model.AdvancedImageManipulatorModelImpl;
import model.ColorCorrectionWithHistogram;
import model.Image;
import model.Pixel;
import model.SimpleHistogramVisualization;
import org.junit.Test;

/**
 * Test class for testing the advanced image manipulation techniques implemented in
 * AdvancedImageManipulatorModel.
 */
public class AdvancedImageManipulatorModelTest {

  @Test
  public void testImageCompression() {
    int[][] testImagePixels = {
        {100, 150, 200, 50, 75},
        {25, 175, 225, 125, 100},
        {75, 125, 250, 200, 150},
        {200, 100, 50, 175, 225},
        {150, 50, 175, 225, 125}
    };

    AdvancedImageManipulatorModelImpl model = new AdvancedImageManipulatorModelImpl();
    Image testImage = createTestImage(testImagePixels);
    model.addImageToHashMap("testImage", testImage);

    model.compress(50, "testImage", "compressedImage");
    Image compressedImage = model.checkAndGetTheImage("compressedImage");

    int[][] compressedImagePixels = {
        {66, 178, 172, 60, 59},
        {13, 125, 253, 141, 57},
        {85, 135, 250, 200, 181},
        {194, 94, 63, 188, 179},
        {112, 112, 170, 170, 39}
    };

    assertEquals(testImage.getWidth(), compressedImage.getWidth());
    assertEquals(testImage.getHeight(), compressedImage.getHeight());
    assertImageRGBValues(compressedImagePixels, compressedImage);
  }

  @Test
  public void testImageCompression0Percent() {
    int[][] testImagePixels = {
        {100, 150, 200, 50, 75},
        {25, 175, 225, 125, 100},
        {75, 125, 250, 200, 150},
        {200, 100, 50, 175, 225},
        {150, 50, 175, 225, 125}
    };

    AdvancedImageManipulatorModelImpl model = new AdvancedImageManipulatorModelImpl();
    Image testImage = createTestImage(testImagePixels);
    model.addImageToHashMap("testImage", testImage);

    model.compress(0, "testImage", "compressedImage");
    Image compressedImage = model.checkAndGetTheImage("compressedImage");

    int[][] compressedImagePixels = {
        {103, 153, 199, 49, 74},
        {21, 171, 224, 124, 99},
        {71, 121, 249, 199, 149},
        {203, 103, 49, 174, 224},
        {146, 46, 174, 224, 124}
    };
    assertEquals(testImage.getWidth(), compressedImage.getWidth());
    assertEquals(testImage.getHeight(), compressedImage.getHeight());
    assertImageRGBValues(compressedImagePixels, compressedImage);
  }

  @Test
  public void testImageCompression90Percent() {
    int[][] testImagePixels = {
        {100, 150, 200, 50, 75},
        {200, 100, 50, 175, 225},
        {150, 50, 175, 225, 125}
    };

    AdvancedImageManipulatorModelImpl model = new AdvancedImageManipulatorModelImpl();
    Image testImage = createTestImage(testImagePixels);
    model.addImageToHashMap("testImage", testImage);

    model.compress(90, "testImage", "compressedImage");
    Image compressedImage = model.checkAndGetTheImage("compressedImage");

    int[][] compressedImagePixels = {
        {82, 82, 82, 82, 32},
        {82, 82, 82, 82, 32},
        {82, 82, 82, 82, 32}
    };
    assertEquals(testImage.getWidth(), compressedImage.getWidth());
    assertEquals(testImage.getHeight(), compressedImage.getHeight());
    assertImageRGBValues(compressedImagePixels, compressedImage);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCompressionIllegalPercentageLess() {
    int[][] testImagePixels = {
        {100, 150, 200, 50, 75}
    };
    AdvancedImageManipulatorModelImpl model = new AdvancedImageManipulatorModelImpl();
    Image testImage = createTestImage(testImagePixels);
    model.addImageToHashMap("testImage", testImage);
    model.compress(-10, "testImage",
        "resultImage");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCompressionIllegalPercentageMore() {
    int[][] testImagePixels = {
        {100, 150, 200, 50, 75}
    };
    AdvancedImageManipulatorModelImpl model = new AdvancedImageManipulatorModelImpl();
    Image testImage = createTestImage(testImagePixels);
    model.addImageToHashMap("testImage", testImage);
    model.compress(110, "testImage",
        "resultImage");
  }

  @Test
  public void testSplitViewWithGreyScale() {
    // Create a 5x5 test image with known RGB values
    int[][] testImagePixels = {
        {100, 150, 200, 50, 75},
        {25, 175, 225, 125, 100},
        {75, 125, 250, 200, 150},
        {200, 100, 50, 175, 225},
        {150, 50, 175, 225, 125}
    };

    AdvancedImageManipulatorModelImpl model = new AdvancedImageManipulatorModelImpl();
    Image testImage = createTestImage(testImagePixels);
    model.addImageToHashMap("testImage", testImage);

    // Apply splitView operation with blur filter
    model.splitViewNew("red-component testImage resultImage",
        50);

    // Define expected RGB values after applying blur filter to the split view
    int[][] rExpectedPixels = {
        {100, 150, 200, 50, 75},
        {25, 175, 225, 125, 100},
        {75, 125, 250, 200, 150},
        {200, 100, 50, 175, 225},
        {150, 50, 175, 225, 125}
    };

    int[][] gExpectedPixels = {
        {0, 0, 200, 50, 75},
        {0, 0, 225, 125, 100},
        {0, 0, 250, 200, 150},
        {0, 0, 50, 175, 225},
        {0, 0, 175, 225, 125}
    };

    int[][] bExpectedPixels = {
        {0, 0, 200, 50, 75},
        {0, 0, 225, 125, 100},
        {0, 0, 250, 200, 150},
        {0, 0, 50, 175, 225},
        {0, 0, 175, 225, 125}
    };

    assertImageRGBValuesSeparately(rExpectedPixels, gExpectedPixels, bExpectedPixels,
        model.checkAndGetTheImage("resultImage"));
  }

  @Test
  public void testSplitViewWithBlur() {
    // Create a 5x5 test image with known RGB values
    int[][] testImagePixels = {
        {100, 150, 200, 50, 75},
        {25, 175, 225, 125, 100},
        {75, 125, 250, 200, 150},
        {200, 100, 50, 175, 225},
        {150, 50, 175, 225, 125}
    };

    AdvancedImageManipulatorModelImpl model = new AdvancedImageManipulatorModelImpl();
    Image testImage = createTestImage(testImagePixels);
    model.addImageToHashMap("testImage", testImage);

    // Apply splitView operation with blur filter
    model.splitViewNew("blur  testImage resultImage", 50);

    // Define expected RGB values after applying blur filter to the split view
    int[][] expectedPixels = {
        {57, 73, 200, 50, 75},
        {67, 92, 225, 125, 100},
        {79, 89, 250, 200, 150},
        {101, 85, 50, 175, 225},
        {75, 56, 175, 225, 125}
    };

    Image splittedImage = model.checkAndGetTheImage("resultImage");
    // Check if the resulting image has the expected RGB values
    assertImageRGBValues(expectedPixels, model.checkAndGetTheImage("resultImage"));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSplitViewIllegalPercentageLess() {
    int[][] testImagePixels = {
        {100, 150, 200, 50, 75}
    };
    AdvancedImageManipulatorModelImpl model = new AdvancedImageManipulatorModelImpl();
    Image testImage = createTestImage(testImagePixels);
    model.addImageToHashMap("testImage", testImage);
    model.splitViewNew("red-component testImage resultImage", -10);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSplitViewIllegalPercentageMore() {
    int[][] testImagePixels = {
        {100, 150, 200, 50, 75}
    };
    AdvancedImageManipulatorModelImpl model = new AdvancedImageManipulatorModelImpl();
    Image testImage = createTestImage(testImagePixels);
    model.addImageToHashMap("testImage", testImage);
    model.splitViewNew("red-component testImage resultImage", 150);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSplitViewIllegalOperation() {
    int[][] testImagePixels = {
        {100, 150, 200, 50, 75}
    };
    AdvancedImageManipulatorModelImpl model = new AdvancedImageManipulatorModelImpl();
    Image testImage = createTestImage(testImagePixels);
    model.addImageToHashMap("testImage", testImage);
    model.splitViewNew("illegal testImage resultImage", -10);
    model.splitViewNew("brighten testImage resultImage", -10);
  }

  @Test
  public void testSplitViewWithSepia() {
    // Create a 5x5 test image with known RGB values
    int[][] testImagePixels = {
        {100, 150, 200, 50, 75},
        {25, 175, 225, 125, 100},
        {75, 125, 250, 200, 150},
        {200, 100, 50, 175, 225},
        {150, 50, 175, 225, 125}
    };

    AdvancedImageManipulatorModelImpl model = new AdvancedImageManipulatorModelImpl();
    Image testImage = createTestImage(testImagePixels);
    model.addImageToHashMap("testImage", testImage);

    // Apply splitView operation with blur filter
    model.splitViewNew("sepia  testImage resultImage", 50);

    // Define expected RGB values after applying blur filter to the split view
    int[][] rExpectedPixels = {
        {135, 202, 200, 50, 75},
        {33, 236, 225, 125, 100},
        {101, 168, 250, 200, 150},
        {255, 135, 50, 175, 225},
        {202, 67, 175, 225, 125}
    };

    int[][] gExpectedPixels = {
        {120, 180, 200, 50, 75},
        {30, 210, 225, 125, 100},
        {90, 150, 250, 200, 150},
        {240, 120, 50, 175, 225},
        {180, 60, 175, 225, 125}
    };

    int[][] bExpectedPixels = {
        {93, 140, 200, 50, 75},
        {23, 163, 225, 125, 100},
        {70, 117, 250, 200, 150},
        {187, 93, 50, 175, 225},
        {140, 46, 175, 225, 125}
    };

    Image splittedImage = model.checkAndGetTheImage("resultImage");
    // Check if the resulting image has the expected RGB values
    assertImageRGBValuesSeparately(rExpectedPixels, gExpectedPixels, bExpectedPixels,
        splittedImage);
  }

  @Test
  public void testColorCorrection() {
    // Create a 5x5 test image with known RGB values
    int[][] redTestImagePixels = {
        {100, 150, 200, 50, 75},
        {25, 175, 225, 125, 100},
        {75, 125, 250, 200, 150}
    };

    int[][] greenTestImagePixels = {
        {124, 201, 45, 98, 175},
        {33, 150, 92, 200, 42},
        {177, 64, 22, 198, 123}
    };

    int[][] blueTestImagePixels = {
        {14, 189, 67, 200, 32},
        {123, 54, 88, 176, 99},
        {33, 155, 89, 210, 44}
    };

    Image testImage = createTestImageWith3Channels(redTestImagePixels, greenTestImagePixels,
        blueTestImagePixels);

    // Create ColorCorrectionWithHistogram object
    ColorCorrectionWithHistogram colorCorrector = new ColorCorrectionWithHistogram(testImage);

    // Apply color correction
    Image correctedImage = colorCorrector.colorCorrect(testImage);

    int[][] rExpectedPixels = {
        {62, 112, 162, 12, 37},
        {0, 137, 187, 87, 62},
        {37, 87, 212, 162, 112}
    };

    int[][] gExpectedPixels = {
        {139, 216, 60, 113, 190},
        {48, 165, 107, 215, 57},
        {192, 79, 37, 213, 138}
    };

    int[][] bExpectedPixels = {
        {37, 212, 90, 223, 55},
        {146, 77, 111, 199, 122},
        {56, 178, 112, 233, 67}
    };

    // Check if the corrected image has the expected RGB values
    assertImageRGBValuesSeparately(rExpectedPixels, gExpectedPixels, bExpectedPixels,
        correctedImage);

  }

  @Test
  public void testAdjustLevels() {
    int[][] testRedImagePixels = {
        {14, 189, 67, 200, 32},
        {123, 54, 88, 176, 99}
    };
    int[][] testGreenImagePixels = {
        {85, 10, 190, 67, 143},
        {56, 123, 88, 33, 210}
    };
    int[][] testBlueImagePixels = {
        {124, 201, 45, 98, 175},
        {33, 150, 92, 200, 42},
    };
    // Create a 5x2 test image
    Image testImage = createTestImageWith3Channels(testRedImagePixels, testGreenImagePixels,
        testBlueImagePixels);

    AdvancedImageManipulatorModelImpl model = new AdvancedImageManipulatorModelImpl();
    model.addImageToHashMap("testImage", testImage);

    // Define levels adjustment parameters
    int b = 30; // black point
    int m = 150; // mid point
    int w = 220; // white point

    // Adjust levels
    model.adjustLevels(b, m, w, "testImage", "adjustedImage");
    // Retrieve the adjusted levels image
    Image adjustedImage = model.checkAndGetTheImage("adjustedImage");

    int[][] adjustedRedImagePixels = {
        {0, 193, 27, 214, 1},
        {89, 16, 47, 170, 59}
    };
    int[][] adjustedGreenImagePixels = {
        {44, 0, 195, 27, 117},
        {18, 89, 47, 1, 234}
    };
    int[][] adjustedBlueImagePixels = {
        {90, 216, 9, 58, 168},
        {1, 128, 51, 214, 7},
    };

    assertImageRGBValuesSeparately(adjustedRedImagePixels, adjustedGreenImagePixels,
        adjustedBlueImagePixels,
        adjustedImage);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAdjustLevelsInvalidBMW() {
    // Create a 5x5 test image with known RGB values
    int[][] testImagePixels = {
        {100, 150, 200, 50, 75},
        {25, 175, 225, 125, 100},
        {75, 125, 250, 200, 150},
        {200, 100, 50, 175, 225},
        {150, 50, 175, 225, 125}
    };

    AdvancedImageManipulatorModelImpl model = new AdvancedImageManipulatorModelImpl();
    Image testImage = createTestImage(testImagePixels);
    model.addImageToHashMap("testImage", testImage);

    // Apply splitView operation with blur filter
    model.adjustLevels(240, 100, 200, "testImage", "resultImage");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAdjustLevelsInvalidBMW3() {
    // Create a 5x5 test image with known RGB values
    int[][] testImagePixels = {
        {100, 150, 200, 50, 75},
        {25, 175, 225, 125, 100},
        {75, 125, 250, 200, 150},
        {200, 100, 50, 175, 225},
        {150, 50, 175, 225, 125}
    };

    AdvancedImageManipulatorModelImpl model = new AdvancedImageManipulatorModelImpl();
    Image testImage = createTestImage(testImagePixels);
    model.addImageToHashMap("testImage", testImage);

    // Apply splitView operation with blur filter
    model.adjustLevels(40, 100, 40, "testImage", "resultImage");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAdjustLevelsInvalidBMWNegative() {
    // Create a 5x5 test image with known RGB values
    int[][] testImagePixels = {
        {200, 100, 50, 175, 225},
        {150, 50, 175, 225, 125}
    };

    AdvancedImageManipulatorModelImpl model = new AdvancedImageManipulatorModelImpl();
    Image testImage = createTestImage(testImagePixels);
    model.addImageToHashMap("testImage", testImage);

    // Apply splitView operation with blur filter
    model.adjustLevels(-90, 109, 250, "testImage",
        "resultImage");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAdjustLevelsInvalidBMWPositive() {
    // Create a 5x5 test image with known RGB values
    int[][] testImagePixels = {
        {200, 100, 50, 175, 225},
        {150, 50, 175, 225, 125}
    };

    AdvancedImageManipulatorModelImpl model = new AdvancedImageManipulatorModelImpl();
    Image testImage = createTestImage(testImagePixels);
    model.addImageToHashMap("testImage", testImage);

    // Apply splitView operation with blur filter
    model.adjustLevels(270, 899, 960, "testImage",
        "resultImage");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAdjustLevelsInvalidBMW2() {
    // Create a 5x5 test image with known RGB values
    int[][] testImagePixels = {
        {100, 150, 200, 50, 75},
        {25, 175, 225, 125, 100},
        {75, 125, 250, 200, 150},
        {200, 100, 50, 175, 225},
        {150, 50, 175, 225, 125}
    };

    AdvancedImageManipulatorModelImpl model = new AdvancedImageManipulatorModelImpl();
    Image testImage = createTestImage(testImagePixels);
    model.addImageToHashMap("testImage", testImage);

    // Apply splitView operation with blur filter
    model.adjustLevels(900, 80, 98, "testImage", "resultImage");
  }

  @Test
  public void testGenerateHistogram() {

    int[][] redImagePixels = {
        {46, 72, 89, 15, 37, 68, 94, 21, 52, 80},
        {33, 95, 10, 57, 74, 98, 26, 63, 40, 87},
        {61, 18, 83, 48, 5, 92, 39, 70, 27, 54},
        {12, 69, 42, 77, 23, 58, 35, 81, 96, 19},
        {60, 84, 49, 16, 3, 76, 11, 28, 45, 72},
        {98, 34, 71, 84, 20, 56, 92, 17, 63, 38},
        {25, 51, 79, 14, 46, 70, 93, 39, 68, 11},
        {37, 88, 15, 62, 29, 77, 44, 91, 26, 52},
        {73, 28, 56, 82, 19, 45, 67, 94, 13, 59},
        {65, 22, 48, 75, 10, 34, 57, 88, 16, 41}
    };

    int[][] greenImagePixels = {
        {83, 12, 47, 75, 23, 58, 92, 39, 65, 16},
        {29, 94, 61, 18, 53, 86, 42, 79, 7, 34},
        {55, 6, 39, 75, 11, 48, 84, 22, 57, 93},
        {16, 72, 38, 85, 27, 64, 91, 19, 54, 80},
        {40, 85, 21, 57, 93, 29, 66, 12, 49, 74},
        {7, 53, 89, 26, 61, 97, 34, 71, 18, 45},
        {31, 68, 16, 51, 78, 14, 49, 85, 23, 60},
        {76, 23, 58, 94, 32, 67, 3, 40, 76, 10},
        {22, 59, 95, 42, 79, 15, 52, 89, 26, 62},
        {48, 5, 31, 67, 13, 48, 74, 20, 57, 93}
    };

    int[][] blueImagePixels = {
        {53, 87, 21, 54, 80, 17, 45, 72, 9, 36},
        {76, 12, 47, 83, 29, 64, 91, 38, 75, 20},
        {23, 58, 94, 31, 67, 3, 40, 76, 13, 49},
        {69, 16, 42, 79, 15, 52, 89, 26, 62, 7},
        {34, 70, 7, 43, 77, 14, 50, 85, 22, 58},
        {80, 27, 63, 9, 46, 72, 98, 35, 61, 17},
        {27, 64, 90, 38, 74, 20, 57, 93, 29, 66},
        {13, 49, 76, 22, 59, 95, 42, 79, 6, 32},
        {60, 17, 53, 88, 24, 61, 97, 33, 70, 15},
        {6, 42, 79, 15, 52, 88, 25, 61, 98, 35}
    };

    // Create a 5x5 test image with known pixel values
    Image testImage = createTestImageWith3Channels(redImagePixels, greenImagePixels,
        blueImagePixels);

    AdvancedImageManipulatorModelImpl model = new AdvancedImageManipulatorModelImpl();
    model.addImageToHashMap("testImage", testImage);

    AbstractImageHistogram hist = new SimpleHistogramVisualization(testImage);
    int[][] histogramValues = hist.getHistogramValues();
    model.generateHistogram("testImage", "histogram");

    int[][] expectedHistogramValues = new int[3][256];
    for (int y = 0; y < testImage.getHeight(); y++) {
      for (int x = 0; x < testImage.getWidth(); x++) {
        Pixel currentPixel = testImage.getPixelRGB(x, y);
        expectedHistogramValues[0][currentPixel.getRed()]++;
        expectedHistogramValues[1][currentPixel.getGreen()]++;
        expectedHistogramValues[2][currentPixel.getBlue()]++;
      }
    }

    for (int channel = 0; channel < 3; channel++) {
      for (int intensity = 0; intensity < 256; intensity++) {
        assertEquals(expectedHistogramValues[channel][intensity],
            histogramValues[channel][intensity]);
      }
    }
  }

  private void assertImageRGBValuesSeparately(int[][] expectedRedPixels,
      int[][] expectedGreenPixels,
      int[][] expectedBluePixels, Image image) {
    int width = expectedRedPixels[0].length;
    int height = expectedRedPixels.length;

    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        Pixel pixel = image.getPixelRGB(x, y);
        int expectedRed = expectedRedPixels[y][x];
        int expectedGreen = expectedGreenPixels[y][x];
        int expectedBlue = expectedBluePixels[y][x];

        assertEquals("Red value mismatch at (" + x + ", " + y + ")", expectedRed,
            pixel.getRed());
        assertEquals("Green value mismatch at (" + x + ", " + y + ")", expectedGreen,
            pixel.getGreen());
        assertEquals("Blue value mismatch at (" + x + ", " + y + ")", expectedBlue,
            pixel.getBlue());
      }
    }
  }

  private void assertImageRGBValues(int[][] expectedPixels, Image image) {
    int width = expectedPixels[0].length;
    int height = expectedPixels.length;

    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        Pixel pixel = image.getPixelRGB(x, y);
        int expectedRed = expectedPixels[y][x];
        int expectedGreen = expectedPixels[y][x];
        int expectedBlue = expectedPixels[y][x];

        assertEquals("Red value mismatch at (" + x + ", " + y + ")", expectedRed, pixel.getRed());
        assertEquals("Green value mismatch at (" + x + ", " + y + ")", expectedGreen,
            pixel.getGreen());
        assertEquals("Blue value mismatch at (" + x + ", " + y + ")", expectedBlue,
            pixel.getBlue());
      }
    }
  }

  private Image createTestImage(int[][] pixels) {
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

  private Image createTestImageWith3Channels(int[][] redPixels, int[][] greenPixels,
      int[][] bluePixels) {
    int width = redPixels[0].length;
    int height = redPixels.length;
    Image testImage = new Image(width, height);

    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        int red = redPixels[y][x];
        int green = greenPixels[y][x];
        int blue = bluePixels[y][x];
        testImage.setPixelRGB(x, y, new Pixel(red, green, blue));
      }
    }
    return testImage;
  }


}
