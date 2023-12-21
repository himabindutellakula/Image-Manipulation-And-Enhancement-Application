import controller.AdvancedImageManipulatorControllerImpl;
import controller.GUICallBackController;
import controller.ImageManipulatorController;
import java.io.IOException;
import java.io.InputStreamReader;
import model.AdvancedImageManipulatorModel;
import model.AdvancedImageManipulatorModelImpl;
import view.AdvancedImageManipulatorViewImpl;
import view.GUIViewImpl;
import view.ImageManipulatorGUIView;
import view.ImageManipulatorView;

/**
 * Main class that initializes and runs the Image Manipulator application.
 */
public class Main {

  /**
   * The main method that serves as the entry point for the Image Manipulator application.
   *
   * @param args Command-line arguments passed to the application.
   */
  public static void main(String[] args) throws IOException {
    AdvancedImageManipulatorModel model = new AdvancedImageManipulatorModelImpl();
    ImageManipulatorView view = new AdvancedImageManipulatorViewImpl();
    ImageManipulatorController controller = new AdvancedImageManipulatorControllerImpl(model, view);

    if (args.length > 0 && args[0].equals("-file")) {
      if (args.length > 1) {
        String fileName = args[1];
        controller.runCommandsFromFile(fileName, System.out);
        System.exit(0);
      } else {
        System.out.println("Error: File name not provided after -file option.");
        System.exit(1);
      }
    } else if (args.length > 0 && args[0].equals("-text")) {
      controller.executeApplication(new InputStreamReader(System.in), System.out);
    } else if (args.length == 0) {
      ImageManipulatorGUIView guiView = new GUIViewImpl(
          "Image Manipulation & Enhancement Application");
      AdvancedImageManipulatorModel guiModel = new AdvancedImageManipulatorModelImpl();
      new GUICallBackController(guiModel,guiView);
    }
  }
}
