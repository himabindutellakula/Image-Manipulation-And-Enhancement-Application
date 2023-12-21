package controller;

import controller.command.Blur;
import controller.command.Brighten;
import controller.command.GreyScale;
import controller.command.HorizontalFlip;
import controller.command.Load;
import controller.command.RGBCombine;
import controller.command.RGBSplit;
import controller.command.Save;
import controller.command.Sepia;
import controller.command.Sharpen;
import controller.command.VerticalFlip;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import model.ImageManipulatorModel;
import view.ImageManipulatorView;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

import static java.lang.System.out;

/**
 * Implementation of the ImageManipulatorController interface, for controlling image manipulation
 * operations.
 */
public class ImageManipulatorControllerImpl implements ImageManipulatorController {

  // The ImageManipulatorModel to perform image manipulations.
  protected final ImageManipulatorModel model;
  // The view for displaying messages and results.
  protected final ImageManipulatorView view;
  protected final Map<String, Function<String[], ImageManipulationCommand>> knownCommands;


  /**
   * Constructs an ImageManipulatorControllerImpl with a model and a view.
   *
   * @param model The ImageManipulatorModel for image manipulation operations.
   * @param view  The view for displaying messages and results.
   */
  public ImageManipulatorControllerImpl(ImageManipulatorModel model,
      ImageManipulatorView view) {
    this.model = model;
    this.view = view;
    this.knownCommands = initializeCommands();
  }

  /**
   * Initializes the known commands map with mappings from command strings to corresponding
   * functions.
   *
   * @return The map containing command mappings.
   */
  protected Map<String, Function<String[], ImageManipulationCommand>> initializeCommands() {
    Map<String, Function<String[], ImageManipulationCommand>> commands = new HashMap<>();
    commands.put("load", tokens -> new Load(tokens[1], tokens[2]));
    commands.put("save", tokens -> new Save(tokens[1], tokens[2]));
    commands.put("rgb-split", tokens -> new RGBSplit(tokens[1], tokens[2], tokens[3], tokens[4]));
    commands.put("rgb-combine",
        tokens -> new RGBCombine(tokens[1], tokens[2], tokens[3], tokens[4]));
    commands.put("horizontal-flip", tokens -> new HorizontalFlip(tokens[1], tokens[2]));
    commands.put("vertical-flip", tokens -> new VerticalFlip(tokens[1], tokens[2]));
    commands.put("brighten",
        tokens -> new Brighten(Integer.parseInt(tokens[1]), tokens[2], tokens[3]));
    commands.put("blur", tokens -> new Blur(tokens[1], tokens[2]));
    commands.put("sharpen", tokens -> new Sharpen(tokens[1], tokens[2]));
    commands.put("sepia", tokens -> new Sepia(tokens[1], tokens[2]));
    commands.put("red-component", tokens -> new GreyScale(tokens[0], tokens[1], tokens[2]));
    commands.put("green-component", tokens -> new GreyScale(tokens[0], tokens[1], tokens[2]));
    commands.put("blue-component", tokens -> new GreyScale(tokens[0], tokens[1], tokens[2]));
    commands.put("value-component", tokens -> new GreyScale(tokens[0], tokens[1], tokens[2]));
    commands.put("luma-component", tokens -> new GreyScale(tokens[0], tokens[1], tokens[2]));
    commands.put("intensity-component", tokens -> new GreyScale(tokens[0], tokens[1], tokens[2]));
    commands.put("run", tokens -> {
      if (tokens.length < 2) {
        throw new IllegalArgumentException("Invalid command format: " + tokens[0]);
      } else {
        try {
          runCommandsFromFile(tokens[1], out);
        } catch (IOException e) {
          throw new RuntimeException(e);
        }
      }
      return null;
    });
    return commands;
  }


  /**
   * Starts the image manipulation controller and handles user input and output.
   *
   * @param in  The Readable source for user input.
   * @param out The Appendable destination for displaying messages and results.
   */
  @Override
  public void executeApplication(Readable in, Appendable out) throws IOException {
    view.startMessageForAnApplication(out);
    try (BufferedReader reader = new BufferedReader((Reader) in)) {
      out.append("Enter commands (Type 'exit' to quit):").append(System.lineSeparator());
      String command;
      while (!(command = reader.readLine()).equalsIgnoreCase("exit")) {
        this.processCommand(command, out);
      }
    } catch (IOException er) {
      try {
        out.append("Image does not exist at the given location.").append("\n");
      } catch (IOException e) {
        System.err.println("Error displaying the message.");
      }
    }
    view.endMessageForAnApplication(out);
  }

  /**
   * Processes a command line for image manipulation and executes the corresponding
   * ImageManipulationCommand. Supported commands include load, save, rgb-split, rgb-combine,
   * horizontal-flip, vertical-flip, brighten, blur, sharpen, sepia, red-component, green-component,
   * blue-component, value-component, luma-component, intensity-component, run, compress, split,
   * histogram, color-correct, and levels-adjust.
   *
   * @param commandLine The input command line to be processed.
   * @param out         The Appendable object where output messages are appended.
   * @throws IOException If an I/O error occurs while processing the command.
   */
  protected boolean processCommand(String commandLine, Appendable out) throws IOException {
    String[] tokens = commandLine.split("\\s+");
    String command = tokens[0].toLowerCase();
    Function<String[], ImageManipulationCommand> cmdFunction = knownCommands.get(command);
    if (cmdFunction != null) {
      ImageManipulationCommand cmd = cmdFunction.apply(tokens);
      if (cmd != null) {
        cmd.execute(model);
        out.append("Operation performed successfully: ").append(command).append("\n");
        return true;
      }
    }
    return false;
  }

  /**
   * Handles errors by appending the error message to the Appendable object.
   *
   * @param out          The Appendable object where the error message is appended.
   * @param errorMessage The error message to be appended.
   */
  protected void handleError(Appendable out, String errorMessage) {
    try {
      out.append(errorMessage).append("\n");
    } catch (IOException e) {
      System.err.println("Error displaying the message.");
    }
  }

  @Override
  public void runCommandsFromFile(String fileName, Appendable out) throws IOException {
    view.startMessageForScriptExecution(out);
    try {
      List<String> lines = Files.readAllLines(Paths.get(fileName));
      for (String line : lines) {
        if (!line.trim().startsWith("#") && !line.trim().isEmpty()) {
          processCommand(line, out);
        }
      }
    } catch (IOException e) {
      out.append("Error reading commands from file: ").append(e.getMessage()).append("\n");
    }
    view.endMessageForScriptExecution(out);
  }
}
