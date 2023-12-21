package controller;

import java.io.IOException;

/**
 * Interface for controlling image manipulation operations and interacting with users.
 */
public interface ImageManipulatorController {

  /**
   * Start the image manipulation controller and handle user input and output.
   *
   * @param in  The Readable source for user input.
   * @param out The Appendable destination for displaying messages and results.
   */
  void executeApplication(Readable in, Appendable out) throws IOException;

  /**
   * Reads commands from a file, processes them, and outputs the results.
   *
   * @param fileName The name of the file containing commands to be executed.
   */
  void runCommandsFromFile(String fileName, Appendable out) throws IOException;

}
