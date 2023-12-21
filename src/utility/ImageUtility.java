package utility;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.util.Scanner;

/**
 * Utility class for working with images, including loading, saving, and conversion between
 * formats.
 */
public class ImageUtility {

  /**
   * Load a PPM image from a file and convert it into a raw image format.
   *
   * @param filename The path to the PPM image file to load.
   * @return A string array representing the raw image.
   */
  public static String[] loadPPMImage(String filename) {
    Scanner sc;
    try {
      sc = new Scanner(new FileInputStream(filename));
    } catch (FileNotFoundException e) {
      System.err.println("File " + filename + " not found!");
      return null;
    }
    StringBuilder builder = new StringBuilder();
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
      System.err.println("Invalid PPM file: plain RAW file should begin with P3");
    }
    int width = sc.nextInt();
    int height = sc.nextInt();
    String[] rawImage = new String[height + 2];
    rawImage[0] = String.valueOf(width);
    rawImage[1] = String.valueOf(height);
    int maxValue = sc.nextInt();
    for (int y = 0; y < height; y++) {
      StringBuilder line = new StringBuilder();
      for (int x = 0; x < width; x++) {
        int r = sc.nextInt();
        int g = sc.nextInt();
        int b = sc.nextInt();
        line.append(String.format("%03d%03d%03d", r, g, b));
      }
      rawImage[y + 2] = line.toString();
    }
    return rawImage;
  }

  /**
   * Load an image from a file and convert it into a raw image format.
   *
   * @param imagePath The path to the image file to load.
   * @return A string array representing the raw image.
   * @throws IOException If there's an issue reading the image file.
   */
  public static String[] loadImage(String imagePath) throws IOException {
    return convertBufferedImageIntoRawImage(ImageIO.read(new File(imagePath)));
  }

  /**
   * Save a raw image as a PPM image to a specified file path.
   *
   * @param rawImage  The string array representing the raw image.
   * @param imagePath The path to save the PPM image.
   * @throws IOException If there's an issue writing the image file.
   */
  public static void saveImageAsPPM(String[] rawImage, String imagePath) throws IOException {
    int height = Integer.parseInt(rawImage[1]);
    // Each pixel is represented as "RRRGGGBBB" (9 characters)
    int width = Integer.parseInt(rawImage[0]);
    int maxColorValue = 255;

    try (BufferedWriter writer = new BufferedWriter(new FileWriter(imagePath))) {
      writer.write("P3\n");
      writer.write(width + " " + height + "\n");
      writer.write(maxColorValue + "\n");

      for (int y = 0; y < height; y++) {
        for (int x = 0; x < width; x++) {
          String pixelData = rawImage[y + 2].substring(x * 9, (x + 1) * 9);
          int red = Integer.parseInt(pixelData.substring(0, 3));
          int green = Integer.parseInt(pixelData.substring(3, 6));
          int blue = Integer.parseInt(pixelData.substring(6, 9));
          writer.write(red + " " + green + " " + blue + " ");
        }
        writer.write("\n");
      }
    }
  }

  /**
   * Save a raw image as an image with the specified format.
   *
   * @param rawImage  The string array representing the raw image.
   * @param imagePath The path to save the image.
   * @param format    The image format to save (e.g., "jpg," "jpeg," "bmp," "png").
   * @throws IOException              If there's an issue writing the image file.
   * @throws IllegalArgumentException If the specified format is unsupported.
   */
  public static void saveImageWithFormat(String[] rawImage, String imagePath, String format)
      throws IOException {
    ImageUtility util = new ImageUtility();
    BufferedImage bImage = util.convertRawImageToBufferedImage(rawImage);
    if (!ImageIO.write(bImage, format, new File(imagePath))) {
      throw new IllegalArgumentException("Unsupported file format");
    }
  }

  /**
   * Convert a raw image (string array) into a BufferedImage.
   *
   * @param rawImage The string array representing the raw image.
   * @return A BufferedImage representation of the raw image.
   */
  public BufferedImage convertRawImageToBufferedImage(String[] rawImage) {
    if (rawImage == null || rawImage.length == 0) {
      return null;
    }

    int height = Integer.parseInt(rawImage[1]);
    int width = Integer.parseInt(rawImage[0]);

    BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

    int[] pixels = ((DataBufferInt) bufferedImage.getRaster().getDataBuffer()).getData();

    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        String pixelData = rawImage[y + 2].substring(x * 9, (x + 1) * 9);
        int red = Integer.parseInt(
            pixelData.substring(0, 3).replaceAll("[^\\d]", ""));
        int green = Integer.parseInt(
            pixelData.substring(3, 6).replaceAll("[^\\d]", ""));
        int blue = Integer.parseInt(
            pixelData.substring(6, 9).replaceAll("[^\\d]", ""));
        int rgb = (red << 16) | (green << 8) | blue;
        pixels[y * width + x] = rgb;
      }
    }

    return bufferedImage;
  }

  /**
   * Convert a BufferedImage into a raw image (string array).
   *
   * @param img The BufferedImage to convert.
   * @return A string array representing the raw image.
   */
  public static String[] convertBufferedImageIntoRawImage(BufferedImage img) {
    int width = img.getWidth();
    int height = img.getHeight();
    String[] rawImage = new String[height + 2];
    rawImage[0] = String.valueOf(width);
    rawImage[1] = String.valueOf(height);
    for (int y = 0; y < height; y++) {
      StringBuilder line = new StringBuilder();
      for (int x = 0; x < width; x++) {
        int rgb = img.getRGB(x, y);
        int red = (rgb >> 16) & 0xFF;
        int green = (rgb >> 8) & 0xFF;
        int blue = rgb & 0xFF;
        line.append(String.format("%03d%03d%03d", red, green, blue));
      }
      rawImage[y + 2] = line.toString();
    }
    return rawImage;
  }
}
