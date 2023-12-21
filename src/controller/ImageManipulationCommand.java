package controller;

import java.io.IOException;
import model.ImageManipulatorModel;

/**
 * Interface for image manipulation commands. Classes implementing this interface
 * can define image manipulation operations and execute them on an ImageManipulatorModel.
 */
public interface ImageManipulationCommand {

  /**
   * Execute the image manipulation command on the given ImageManipulatorModel.
   *
   * @param m The AdvancedImageManipulatorModel on which the image manipulation,
   *         operation will be performed.
   * @throws IOException If an I/O error occurs during the execution of the command.
   */
  void execute(ImageManipulatorModel m) throws IOException;

}
