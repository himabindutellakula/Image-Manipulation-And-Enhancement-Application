package view;

import java.io.IOException;

/**
 * The ImageManipulatorView interface defines methods for displaying messages and menus in an image
 * manipulation application's user interface.
 */
public interface ImageManipulatorView {

  /**
   * Display the menu of supported image manipulation commands.
   *
   * @param out an appendable out to capture messages
   * @throws IOException if any error occurs during writing
   */
  void displayMenu(Appendable out) throws IOException;

  /**
   * Display the start message for the image manipulation application.
   *
   * @param out an appendable out to capture messages
   * @throws IOException if any error occurs during writing
   */
  void startMessageForAnApplication(Appendable out) throws IOException;

  /**
   * Display the end message for the image manipulation application.
   *
   * @param out an appendable out to capture messages
   * @throws IOException if any error occurs during writing
   */
  void endMessageForAnApplication(Appendable out) throws IOException;

  /**
   * Display the start message for script execution.
   *
   * @param out an appendable out to capture messages
   * @throws IOException if any error occurs during writing
   */
  void startMessageForScriptExecution(Appendable out) throws IOException;

  /**
   * Display the end message for script execution.
   *
   * @param out an appendable out to capture messages
   * @throws IOException if any error occurs during writing
   */
  void endMessageForScriptExecution(Appendable out) throws IOException;
}
