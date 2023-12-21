import controller.AdvancedImageManipulatorControllerImpl;
import model.AdvancedImageManipulatorModel;
import model.AdvancedImageManipulatorModelImpl;
import model.Image;
import model.Pixel;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import controller.ImageManipulatorController;
import controller.ImageManipulatorControllerImpl;
import view.ImageManipulatorView;
import view.ImageManipulatorViewImpl;

import static org.junit.Assert.assertEquals;

/**
 * This class tests the program implementation as integration of model and controller.
 */
public class IntegrationTest {
  /**
   * Helper method to create an image directly with the values.
   *
   * @param imageData the data of the image.
   * @return the image created with image data.
   */
  public Image createImage(String imageData) {
    Scanner sc = new Scanner(imageData);
    int width = sc.nextInt();
    int height = sc.nextInt();
    int max = sc.nextInt();
    Image newImage = new Image(width, height);
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        newImage.setPixelRGB(i, j, new Pixel(sc.nextInt(), sc.nextInt(), sc.nextInt()));
      }
    }
    return newImage;
  }


  /**
   * Reads a file, an image file of .ppm and returns the image data as string.
   * This contains width and height in its first line as separate integers.
   * Max value as next integer. And remaining are pixel data.
   *
   * @param filename is the name of the file to be read
   * @return the image data
   * @throws IOException if hit a block while reading an image.
   */
  public String readImage(String filename) throws IOException {
    File file = new File(filename);
    FileInputStream fis = new FileInputStream(file);
    Scanner sc = new Scanner(fis);
    StringBuilder builder = new StringBuilder();

    // read the file line by line, and populate a string. This will throw away any comment lines
    while (sc.hasNextLine()) {
      String s = sc.nextLine();
      if (s.charAt(0) != '#') {
        builder.append(s).append(System.lineSeparator());
      }
    }

    sc = new Scanner(builder.toString());

    String token;
    token = sc.next();
    if (!token.equals("P3")) {
      throw new IOException("Invalid token found in the file " + filename);
    }

    int width = sc.nextInt();
    int height = sc.nextInt();
    int maxValue = sc.nextInt();

    if (width < 0 || height < 0 || maxValue < 0) {
      throw new InvalidObjectException("Invalid values found in the file - " + filename);
    }

    StringBuilder sb = new StringBuilder();

    sb.append(width).append(" ").append(height).append(System.lineSeparator());
    sb.append(maxValue).append(System.lineSeparator());

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        int r = sc.nextInt();
        int g = sc.nextInt();
        int b = sc.nextInt();
        if (r < 0 || g < 0 || b < 0 || r > maxValue || g > maxValue || b > maxValue) {
          throw new InvalidObjectException("Invalid values found in the file - " + filename);
        }
        sb.append(r).append(System.lineSeparator());
        sb.append(g).append(System.lineSeparator());
        sb.append(b).append(System.lineSeparator());
      }
    }

    return sb.toString();
  }


  @Test(expected = IllegalArgumentException.class)
  public void testLoadInvalidFiles() throws IOException {
    AdvancedImageManipulatorModel testIme;
    ImageManipulatorController testController;
    ImageManipulatorView testview;

    testIme = new AdvancedImageManipulatorModelImpl();
    testview = new ImageManipulatorViewImpl();
    List<String> invalidPPMFilePaths = new ArrayList<>();
    for (int i = 0; i < 6; i++) {
      invalidPPMFilePaths.add("test/res/invalid" + i + ".ppm");
    }
    for (String invalidFilePath : invalidPPMFilePaths) {
      StringBuilder sb = new StringBuilder();
      testController = new AdvancedImageManipulatorControllerImpl(testIme, testview);
      testController.executeApplication(new StringReader("load " + invalidFilePath
          + " invalidTest"), sb);
      assertEquals(sb.toString(), "Please provide a valid file");
    }
  }

  @Test(expected = IllegalArgumentException.class)
  public void tesInvalidSaveFiles() throws IOException {
    AdvancedImageManipulatorModel testIme;
    ImageManipulatorController testController;
    ImageManipulatorController testController2;
    ImageManipulatorController testController3;
    ImageManipulatorView testview = new ImageManipulatorViewImpl();

    StringBuilder sb = new StringBuilder();

    testIme = new AdvancedImageManipulatorModelImpl();
    testController = new ImageManipulatorControllerImpl(testIme, testview);

    testController.executeApplication(new StringReader("load res/test.ppm test"), sb);

    StringBuilder sb2 = new StringBuilder();

    testController2 = new ImageManipulatorControllerImpl(testIme, testview);
    testController2.executeApplication(new StringReader("save res/test.ppm test1"), sb2);

    assertEquals(sb2.toString(), "Image test1 not found Please try again with valid image.");

    StringBuilder sb3 = new StringBuilder();

    testController3 = new ImageManipulatorControllerImpl(testIme, testview);
    testController3.executeApplication(new StringReader("save res/test.ppm test2"), sb3);

    assertEquals(sb3.toString(), "Source image not found.");
  }

}