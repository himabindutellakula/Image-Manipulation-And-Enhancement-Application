package controller;

import controller.command.AdjustLevels;
import controller.command.ColorCorrect;
import controller.command.Compress;
import controller.command.Histogram;
import controller.command.SplitOperation;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import model.AdvancedImageManipulatorModel;
import view.ImageManipulatorView;

/**
 * Advanced ImageManipulatorController extending the old ImageManipulatorControllerImpl to handle th
 * new commands.
 */
public class AdvancedImageManipulatorControllerImpl extends ImageManipulatorControllerImpl {

  /**
   * Constructs an NewAdvancedImageManipulationControllerImpl with a model and a view.
   *
   * @param model The ImageManipulatorModel for image manipulation operations.
   * @param view  The view for displaying messages and results.
   */
  public AdvancedImageManipulatorControllerImpl(AdvancedImageManipulatorModel model,
      ImageManipulatorView view) {
    super(model, view);
  }

  @Override
  protected boolean processCommand(String commandLine, Appendable out) throws IOException {
    Map<String, Function<String[], AdvancedImageManipulationCommand>> commands = new HashMap<>();
    commands.put("compress",
        tokens -> new Compress(Double.parseDouble(tokens[1]), tokens[2], tokens[3]));
    commands.put("histogram", tokens -> new Histogram(tokens[1], tokens[2]));
    commands.put("color-correct", tokens -> new ColorCorrect(tokens[1], tokens[2]));
    commands.put("levels-adjust", tokens -> new AdjustLevels(Integer.parseInt(tokens[1]),
        Integer.parseInt(tokens[2]), Integer.parseInt(tokens[3]), tokens[4], tokens[5]));

    String[] tokens = commandLine.split("\\s+");
    String command = tokens[0].toLowerCase();
    int indexOfSplit = commandLine.indexOf(" split ");
    if (indexOfSplit != -1) {
      String commandBeforeSplit = commandLine.substring(0, indexOfSplit);
      double splitPercentage = Double.parseDouble(tokens[tokens.length - 1]);
      AdvancedImageManipulationCommand cmd = new SplitOperation(commandBeforeSplit,
          splitPercentage);
      cmd.execute((AdvancedImageManipulatorModel) model);
      out.append("Operation performed successfully: ").append("split").append("\n");
      return true;
    } else {
      if (super.processCommand(commandLine, out)) {
        return true;
      }
      Function<String[], AdvancedImageManipulationCommand> cmdFunction = commands.get(command);
      if (cmdFunction != null) {
        AdvancedImageManipulationCommand cmd = cmdFunction.apply(tokens);
        if (cmd != null) {
          try {
            cmd.execute((AdvancedImageManipulatorModel) model);
            out.append("Operation performed successfully: ").append(command).append("\n");
            return true;
          } catch (IllegalArgumentException e) {
            handleError(out, "Invalid Arguments " + e.getMessage());
          }
        }
      } else if (!command.equals("run")) {
        handleError(out, "Unknown command: " + command);
      }
    }
    return false;
  }
}
