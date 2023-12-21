package controller;

import controller.command.AdjustLevels;
import controller.command.Blur;
import controller.command.ColorCorrect;
import controller.command.Compress;
import controller.command.GreyScale;
import controller.command.Histogram;
import controller.command.HorizontalFlip;
import controller.command.Load;
import controller.command.Save;
import controller.command.Sepia;
import controller.command.Sharpen;
import controller.command.SplitOperation;
import controller.command.VerticalFlip;
import java.io.IOException;
import model.AdvancedImageManipulatorModel;
import view.ImageManipulatorGUIView;

/**
 * Controller class that serves as a callback for GUI events.
 */
public class GUICallBackController implements GUIController {

  private final String currentImageName = "currentImg";
  private final String currentHistogram = "currentHistogram";
  private ImageManipulationCommand command;
  private AdvancedImageManipulationCommand advCommand;
  private final AdvancedImageManipulatorModel model;
  private final ImageManipulatorGUIView view;

  /**
   * Constructor for GUICallBackController.
   *
   * @param model The AdvancedImageManipulatorModel.
   * @param view  The ImageManipulatorGUIView.
   */
  public GUICallBackController(AdvancedImageManipulatorModel model, ImageManipulatorGUIView view) {
    this.model = model;
    this.view = view;
    if (view != null) {
      this.view.addFeatures(this);
    }
  }

  @Override
  public void loadImage(String imagePath) {
    command = new Load(imagePath, currentImageName);
    executeCommand(command);
    advCommand = new Histogram(currentImageName, currentHistogram);
    executeAdvancedCommand(advCommand);
    if (model.getRawImageFromHashMap(currentImageName) == null) {
      System.out.println("Img null");
    } else {
      System.out.println(model.getRawImageFromHashMap(currentImageName).length);
    }
    if (model.getRawImageFromHashMap(currentHistogram) == null) {
      System.out.println("Img null");
    } else {
      System.out.println(model.getRawImageFromHashMap(currentHistogram).length);
    }
    view.setImageInPanel(model.getRawImageFromHashMap(currentImageName));
    view.setHistogramInPanel(model.getRawImageFromHashMap(currentHistogram));
  }

  @Override
  public void saveImage(String imagePath, String imageName) {
    command = new Save(imagePath, imageName);
    executeCommand(command);
  }

  @Override
  public void preview(String operation, double widthPercentage) {
    String currentPreviewedImageName = "currentPreviewedImageName";
    String commandLine = operation + " " + currentImageName + " " + currentPreviewedImageName;
    advCommand = new SplitOperation(commandLine, widthPercentage);
    executeAdvancedCommand(advCommand);
    String currentPreviewedHistogram = "currentPreviewedHistogram";
    advCommand = new Histogram(currentPreviewedImageName, currentPreviewedHistogram);
    executeAdvancedCommand(advCommand);
    setImageAndHistogram(currentPreviewedImageName, currentPreviewedHistogram);
  }

  @Override
  public void revert() {
    setCurrentImageAndHistogram();
  }

  @Override
  public void horizontalFlip() {
    command = new HorizontalFlip(currentImageName, currentImageName);
    executeCommand(command);
    setCurrentImageAndHistogram();
  }

  @Override
  public void verticalFlip() {
    command = new VerticalFlip(currentImageName, currentImageName);
    executeCommand(command);
    setCurrentImageAndHistogram();
  }

  @Override
  public void visualizeComponent(String componentName) {
    command = new GreyScale(componentName, currentImageName, currentImageName);
    executeCommand(command);
    setCurrentImageAndHistogram();
  }

  @Override
  public void blur() {
    command = new Blur(currentImageName, currentImageName);
    executeCommand(command);
    setCurrentImageAndHistogram();
  }

  @Override
  public void sharpen() {
    command = new Sharpen(currentImageName, currentImageName);
    executeCommand(command);
    setCurrentImageAndHistogram();
  }

  @Override
  public void greyscale() {
    command = new GreyScale("luma-component", currentImageName, currentImageName);
    executeCommand(command);
    setCurrentImageAndHistogram();
  }

  @Override
  public void sepia() {
    command = new Sepia(currentImageName, currentImageName);
    executeCommand(command);
    setCurrentImageAndHistogram();
  }

  @Override
  public void compress(double compressPercentage) {
    advCommand = new Compress(compressPercentage, currentImageName, currentImageName);
    executeAdvancedCommand(advCommand);
    setCurrentImageAndHistogram();
  }

  @Override
  public void colorCorrect() {
    advCommand = new ColorCorrect(currentImageName, currentImageName);
    executeAdvancedCommand(advCommand);
    setCurrentImageAndHistogram();
  }

  @Override
  public void adjustLevels(int b, int m, int w) {
    advCommand = new AdjustLevels(b, m, w, currentImageName, currentImageName);
    executeAdvancedCommand(advCommand);
    setCurrentImageAndHistogram();
  }

  private void setImageAndHistogram(String imageName, String histogramName) {
    view.setImageInPanel(model.getRawImageFromHashMap(imageName));
    advCommand = new Histogram(currentImageName, currentHistogram);
    executeAdvancedCommand(advCommand);
    view.setHistogramInPanel(model.getRawImageFromHashMap(histogramName));
  }

  private void setCurrentImageAndHistogram() {
    view.setImageInPanel(model.getRawImageFromHashMap(currentImageName));
    advCommand = new Histogram(currentImageName, currentHistogram);
    executeAdvancedCommand(advCommand);
    view.setHistogramInPanel(model.getRawImageFromHashMap(currentHistogram));
  }


  /**
   * Executes the specified command and handles exceptions.
   *
   * @param command The ImageManipulationCommand to be executed.
   */
  private void executeCommand(ImageManipulationCommand command) {
    try {
      command.execute(model);
    } catch (IOException ex) {
      view.displayErrorMessage("An error occurred, please try another operation");
    } catch (IllegalArgumentException ex) {
      view.displayErrorMessage(ex.getMessage());
    }
  }

  /**
   * Executes the specified advanced command and handles exceptions.
   *
   * @param command The AdvancedImageManipulationCommand to be executed.
   */
  private void executeAdvancedCommand(AdvancedImageManipulationCommand command) {
    try {
      command.execute(model);
    } catch (IOException ex) {
      view.displayErrorMessage("An error occurred, please try another operation");
    } catch (IllegalArgumentException ex) {
      view.displayErrorMessage(ex.getMessage());
    }
  }
}
