package controller.command;

import controller.AdvancedImageManipulationCommand;

import java.io.IOException;

import model.AdvancedImageManipulatorModel;

/**
 * A command to perform a split operation on the image based on a specified command
 * before the split and a split percentage.
 */
public class SplitOperation implements AdvancedImageManipulationCommand {

  /**
   * The command to execute before the split.
   */
  private final String commandBeforeSplit;

  /**
   * The percentage at which to split the image.
   */
  private final double splitPercentage;

  /**
   * Constructs a SplitOperation command with the specified parameters.
   *
   * @param commandBeforeSplit The command to execute before the split.
   * @param splitPercentage    The percentage at which to split the image.
   */
  public SplitOperation(String commandBeforeSplit, double splitPercentage) {
    this.commandBeforeSplit = commandBeforeSplit;
    this.splitPercentage = splitPercentage;
  }

  /**
   * Executes the split operation on the image.
   *
   * @param model The model on which to perform the split operation.
   * @throws IOException              If an I/O error occurs during the execution of the command.
   * @throws IllegalArgumentException If an illegal argument is provided for the split operation.
   */
  @Override
  public void execute(AdvancedImageManipulatorModel model) throws IOException {
    try {
      model.splitViewNew(commandBeforeSplit, splitPercentage);
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException(e);
    }
  }
}
