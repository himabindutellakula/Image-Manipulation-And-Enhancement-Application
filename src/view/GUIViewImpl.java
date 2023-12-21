package view;

import controller.GUIController;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import utility.ImageUtility;

/**
 * Implementation of the GUI view for an image manipulation application. Extends JFrame and
 * implements the ImageManipulatorGUIView interface.
 */
public class GUIViewImpl extends JFrame implements ImageManipulatorGUIView {

  private final JMenuItem openOption;
  private final JMenuItem saveOption;
  private final FileNameExtensionFilter supportedFileExt;
  private final JPanel mainPanel;
  private final JPanel imagePanel;
  private final JLabel imageLabel;
  private final JLabel histogramLabel;
  private final JComboBox<String> splitOperationJComboBox;
  private final JSlider previewSlider;
  private final JButton previewButton;
  private final JButton revertButton;
  private final JButton retainButton;
  private JComboBox<Object> splitBlackPointDropDown;
  private JComboBox<Object> splitMidPointDropDown;
  private JComboBox<Object> splitWhitePointDropDown;
  private final JButton horizontalFlipButton;
  private final JButton verticalFlipButton;
  private final JComboBox<String> componentTypesJComboBox;
  private final JButton visualizeComponentButton;
  private final JButton blurButton;
  private final JButton sharpenButton;
  private final JButton greyscaleButton;
  private final JButton sepiaButton;
  private final JSlider compressSlider;
  private final JButton compressButton;
  private final JButton colorCorrectButton;
  private final JComboBox<Integer> blackPointDropDown;
  private final JComboBox<Integer> midPointDropDown;
  private final JComboBox<Integer> whitePointDropDown;
  private final JButton levelsAdjustButton;
  private boolean isImageLoaded;
  private final boolean[] isPreviewed = {false};

  /**
   * Constructor for GUIViewImpl.
   *
   * @param applicationName The name of the application.
   */
  public GUIViewImpl(String applicationName) {
    super(applicationName);
    setSize(900, 700);
    setLocation(150, 50);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    JMenuBar menuBar = new JMenuBar();
    JMenu fileMenu = new JMenu("Load/Save");
    menuBar.add(fileMenu);
    openOption = new JMenuItem("Load Image");
    saveOption = new JMenuItem("Save Image");
    supportedFileExt = new FileNameExtensionFilter(".JPG, .PNG, .BMP & .PPM",
        "jpg", "png", "bmp", "ppm");
    fileMenu.add(openOption);
    fileMenu.add(saveOption);
    setJMenuBar(menuBar);

    this.mainPanel = new JPanel(new GridBagLayout());

    GridBagConstraints mainPanelConstraints = new GridBagConstraints();
    mainPanelConstraints.gridx = 0;
    mainPanelConstraints.gridy = 0;
    mainPanelConstraints.weightx = 1.0;
    mainPanelConstraints.weighty = 0.7;
    mainPanelConstraints.fill = GridBagConstraints.BOTH;

    JPanel topScreen = new JPanel(new GridBagLayout());

    GridBagConstraints topScreenConstraints = new GridBagConstraints();
    topScreenConstraints.gridx = 0;
    topScreenConstraints.gridy = 0;
    topScreenConstraints.weightx = 0.8;
    topScreenConstraints.weighty = 1;
    topScreenConstraints.fill = GridBagConstraints.BOTH;

    imagePanel = new JPanel();
    imagePanel.setBorder(BorderFactory.createTitledBorder("Loaded Image"));

    imageLabel = new JLabel(
        "<html><br><br><br><br><br><br>Please load an image<br>Load/Save > Load Image</html>");
    imagePanel.add(imageLabel);

    JPanel histogramPanel = new JPanel();
    histogramPanel.setBorder(BorderFactory.createTitledBorder("Histogram Visualization"));

    histogramLabel = new JLabel("Live histogram will be displayed here");
    histogramLabel.setPreferredSize(new Dimension(256, 256));
    histogramPanel.add(histogramLabel);

    JScrollPane scroller = new JScrollPane(imagePanel);
    scroller.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
    scroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    topScreen.add(scroller, topScreenConstraints);
    topScreenConstraints.gridx = 1;
    topScreenConstraints.gridy = 0;
    topScreenConstraints.weightx = 0.2;
    topScreen.add(histogramPanel, topScreenConstraints);
    mainPanel.add(topScreen, mainPanelConstraints);

    JPanel previewPanel = new JPanel(new GridBagLayout());
    previewPanel.setBorder(BorderFactory.createTitledBorder(""));
    GridBagConstraints previewPanelConstraints = new GridBagConstraints();
    previewPanelConstraints.fill = GridBagConstraints.HORIZONTAL;
    previewPanelConstraints.weightx = 1.0;
    previewPanelConstraints.insets = new Insets(0, 5, 0, 5);

    JLabel operationSelectLabel = new JLabel("Select Operation to Preview");
    splitOperationJComboBox = new JComboBox<>(
        new String[]{"None", "Blur", "Sharpen", "Sepia", "Luma-component",
            "Color-Correct", "Levels-Adjust"});

    previewPanelConstraints.gridx = 0;
    previewPanelConstraints.gridy = 0;
    previewPanel.add(operationSelectLabel, previewPanelConstraints);

    previewPanelConstraints.gridx = 0;
    previewPanelConstraints.gridy = 1;
    previewPanel.add(splitOperationJComboBox, previewPanelConstraints);

    JLabel previewPercentLabel = new JLabel("Select Preview Width");
    previewSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, 0);
    previewSlider.setMajorTickSpacing(20);
    previewSlider.setMinorTickSpacing(5);
    previewSlider.setPaintTicks(true);
    previewSlider.setPaintLabels(true);

    previewPanelConstraints.gridx = 1;
    previewPanelConstraints.gridy = 0;
    previewPanel.add(previewPercentLabel, previewPanelConstraints);

    previewPanelConstraints.gridx = 1;
    previewPanelConstraints.gridy = 1;
    previewPanel.add(previewSlider, previewPanelConstraints);

    previewPanelConstraints.gridx = 2;
    previewPanelConstraints.gridy = 1;
    previewButton = new JButton("Preview Operation");
    previewPanel.add(previewButton, previewPanelConstraints);

    previewPanelConstraints.gridx = 3;
    previewPanelConstraints.gridy = 1;
    retainButton = new JButton("Retain Operation");
    previewPanel.add(retainButton, previewPanelConstraints);

    previewPanelConstraints.gridx = 4;
    previewPanelConstraints.gridy = 1;
    revertButton = new JButton("Revert to Original");
    previewPanel.add(revertButton, previewPanelConstraints);

    splitBlackPointDropDown = new JComboBox<>(
        new CustomComboBoxModel("Select Black Point"));
    splitMidPointDropDown = new JComboBox<>(
        new CustomComboBoxModel("Select Mid Point"));
    splitWhitePointDropDown = new JComboBox<>(
        new CustomComboBoxModel("Select White Point"));

    previewPanelConstraints.gridx = 0;
    previewPanelConstraints.gridy = 2;
    previewPanel.add(splitBlackPointDropDown, previewPanelConstraints);
    previewPanelConstraints.gridx = 1;
    previewPanelConstraints.gridy = 2;
    previewPanel.add(splitMidPointDropDown, previewPanelConstraints);
    previewPanelConstraints.gridx = 2;
    previewPanelConstraints.gridy = 2;
    previewPanel.add(splitWhitePointDropDown, previewPanelConstraints);

    hideBMWPicker();

    mainPanelConstraints.gridy = 1;
    mainPanelConstraints.weighty = 0.1;
    mainPanel.add(previewPanel, mainPanelConstraints);

    JPanel bottomScreen = new JPanel(new GridLayout(2, 1));
    JPanel basicAndFilers = new JPanel(new GridLayout(1, 2));

    JPanel basicOpsPanel = new JPanel();
    basicOpsPanel.setLayout(new GridBagLayout());
    basicOpsPanel.setBorder(BorderFactory.createTitledBorder("Basic Operations"));

    GridBagConstraints basicControlsPanelConstraints = new GridBagConstraints();
    basicControlsPanelConstraints.fill = GridBagConstraints.HORIZONTAL;
    basicControlsPanelConstraints.weightx = 1.0;
    basicControlsPanelConstraints.insets = new Insets(0, 5, 1, 5);

    basicControlsPanelConstraints.gridx = 0;
    basicControlsPanelConstraints.gridy = 0;
    horizontalFlipButton = new JButton("Horizontal Flip");
    basicOpsPanel.add(horizontalFlipButton, basicControlsPanelConstraints);

    basicControlsPanelConstraints.gridx = 1;
    basicControlsPanelConstraints.gridy = 0;
    verticalFlipButton = new JButton("Vertical Flip");
    basicOpsPanel.add(verticalFlipButton, basicControlsPanelConstraints);

    basicControlsPanelConstraints.gridx = 0;
    basicControlsPanelConstraints.gridy = 1;
    JLabel componentTypesLabel = new JLabel("Select Component");
    basicOpsPanel.add(componentTypesLabel, basicControlsPanelConstraints);

    basicControlsPanelConstraints.gridx = 0;
    basicControlsPanelConstraints.gridy = 2;
    componentTypesJComboBox = new JComboBox<>(
        new String[]{"None", "Red", "Green", "Blue"});
    basicOpsPanel.add(componentTypesJComboBox, basicControlsPanelConstraints);

    basicControlsPanelConstraints.gridx = 1;
    basicControlsPanelConstraints.gridy = 2;
    visualizeComponentButton = new JButton("Visualize Component");
    basicOpsPanel.add(visualizeComponentButton, basicControlsPanelConstraints);
    basicAndFilers.add(basicOpsPanel);

    JPanel imageFiltersPanel = new JPanel();
    imageFiltersPanel.setLayout(new GridBagLayout());
    imageFiltersPanel.setBorder(BorderFactory.createTitledBorder("Image Filters"));

    GridBagConstraints imageFiltersPanelConstraints = new GridBagConstraints();
    imageFiltersPanelConstraints.fill = GridBagConstraints.HORIZONTAL;
    imageFiltersPanelConstraints.weightx = 1.0;
    imageFiltersPanelConstraints.insets = new Insets(5, 5, 5, 5);

    imageFiltersPanelConstraints.gridx = 0;
    imageFiltersPanelConstraints.gridy = 0;
    blurButton = new JButton("Blur");
    imageFiltersPanel.add(blurButton, imageFiltersPanelConstraints);

    imageFiltersPanelConstraints.gridx = 1;
    imageFiltersPanelConstraints.gridy = 0;
    sharpenButton = new JButton("Sharpen");
    imageFiltersPanel.add(sharpenButton, imageFiltersPanelConstraints);

    imageFiltersPanelConstraints.gridx = 0;
    imageFiltersPanelConstraints.gridy = 1;
    greyscaleButton = new JButton("Greyscale");
    imageFiltersPanel.add(greyscaleButton, imageFiltersPanelConstraints);

    imageFiltersPanelConstraints.gridx = 1;
    imageFiltersPanelConstraints.gridy = 1;
    sepiaButton = new JButton("Sepia");
    imageFiltersPanel.add(sepiaButton, imageFiltersPanelConstraints);
    basicAndFilers.add(imageFiltersPanel);

    bottomScreen.add(basicAndFilers);

    JPanel advancedOpsPanel = new JPanel();
    advancedOpsPanel.setLayout(new GridBagLayout());
    advancedOpsPanel.setBorder(BorderFactory.createTitledBorder("Advanced Operations"));

    GridBagConstraints advancedControlsPanelConstraints = new GridBagConstraints();
    advancedControlsPanelConstraints.fill = GridBagConstraints.HORIZONTAL;
    advancedControlsPanelConstraints.weightx = 1.0;
    advancedControlsPanelConstraints.insets = new Insets(0, 5, 1, 5);

    advancedControlsPanelConstraints.gridx = 0;
    advancedControlsPanelConstraints.gridy = 0;
    JLabel compressLabel = new JLabel("Select Compression %");
    advancedOpsPanel.add(compressLabel, advancedControlsPanelConstraints);

    advancedControlsPanelConstraints.gridx = 1;
    advancedControlsPanelConstraints.gridy = 0;
    compressSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, 0);
    compressSlider.setMajorTickSpacing(20);
    compressSlider.setMinorTickSpacing(5);
    compressSlider.setPaintTicks(true);
    compressSlider.setPaintLabels(true);
    advancedOpsPanel.add(compressSlider, advancedControlsPanelConstraints);

    advancedControlsPanelConstraints.gridx = 2;
    advancedControlsPanelConstraints.gridy = 0;
    compressButton = new JButton("Compress");
    advancedOpsPanel.add(compressButton, advancedControlsPanelConstraints);

    advancedControlsPanelConstraints.gridx = 3;
    advancedControlsPanelConstraints.gridy = 0;
    colorCorrectButton = new JButton("Color Correct");
    advancedOpsPanel.add(colorCorrectButton, advancedControlsPanelConstraints);

    JLabel blackPointL = new JLabel("Select Black Point");
    JLabel midPointL = new JLabel("Select Mid Point");
    JLabel whitePointL = new JLabel("Select White Point");

    Integer[] values = new Integer[256];
    for (int i = 0; i <= 255; i++) {
      values[i] = i;
    }

    advancedControlsPanelConstraints.gridx = 0;
    advancedControlsPanelConstraints.gridy = 1;
    advancedOpsPanel.add(blackPointL, advancedControlsPanelConstraints);

    advancedControlsPanelConstraints.gridx = 1;
    advancedControlsPanelConstraints.gridy = 1;
    advancedOpsPanel.add(midPointL, advancedControlsPanelConstraints);

    advancedControlsPanelConstraints.gridx = 2;
    advancedControlsPanelConstraints.gridy = 1;
    advancedOpsPanel.add(whitePointL, advancedControlsPanelConstraints);

    advancedControlsPanelConstraints.gridx = 0;
    advancedControlsPanelConstraints.gridy = 2;
    blackPointDropDown = new JComboBox<>(values);
    advancedOpsPanel.add(blackPointDropDown, advancedControlsPanelConstraints);

    advancedControlsPanelConstraints.gridx = 1;
    advancedControlsPanelConstraints.gridy = 2;
    midPointDropDown = new JComboBox<>(values);
    advancedOpsPanel.add(midPointDropDown, advancedControlsPanelConstraints);

    advancedControlsPanelConstraints.gridx = 2;
    advancedControlsPanelConstraints.gridy = 2;
    whitePointDropDown = new JComboBox<>(values);
    advancedOpsPanel.add(whitePointDropDown, advancedControlsPanelConstraints);

    advancedControlsPanelConstraints.gridx = 3;
    advancedControlsPanelConstraints.gridy = 2;
    levelsAdjustButton = new JButton("Adjust Levels");
    advancedOpsPanel.add(levelsAdjustButton, advancedControlsPanelConstraints);

    bottomScreen.add(advancedOpsPanel);

    mainPanelConstraints.gridy = 2;
    mainPanelConstraints.weighty = 0.2;
    mainPanel.add(bottomScreen, mainPanelConstraints);
    add(mainPanel);
    setVisible(true);
    isImageLoaded = false;
    disableOrEnableEverything(false);
    disableOrEnableRevertRetain(false);
  }

  @Override
  public void addFeatures(GUIController features) {
    openOption.addActionListener(evt -> handleLoadImage(features));
    saveOption.addActionListener(evt -> handleSaveImage(features));
    splitOperationJComboBox.addActionListener(evt -> {
      int selectedIndex = splitOperationJComboBox.getSelectedIndex();
      if (selectedIndex == 6) {
        displayBMWPicker();
      } else {
        hideBMWPicker();
      }
    });
    previewButton.addActionListener(evt -> handlePreviewButton(features));
    retainButton.addActionListener(evt -> handleRetainButton(features));
    revertButton.addActionListener(evt -> handleRevertButton(features));
    horizontalFlipButton.addActionListener(evt -> features.horizontalFlip());
    verticalFlipButton.addActionListener(evt -> features.verticalFlip());
    visualizeComponentButton.addActionListener(evt -> handleVisualiseComponentsButton(features));
    blurButton.addActionListener(evt -> features.blur());
    sepiaButton.addActionListener(evt -> features.sepia());
    sharpenButton.addActionListener(evt -> features.sharpen());
    greyscaleButton.addActionListener(evt -> features.greyscale());
    compressButton.addActionListener(evt -> {
      if (compressSlider.getValue() != 0) {
        features.compress(compressSlider.getValue());
      } else {
        this.displayErrorMessage(
            "Please select compression percentage to compress image.");
      }
    });
    colorCorrectButton.addActionListener(evt -> features.colorCorrect());
    levelsAdjustButton.addActionListener(evt -> handleLevelsAdjustButton(features));
  }

  /**
   * Displays the BMW picker components.
   */
  private void displayBMWPicker() {
    splitBlackPointDropDown.setVisible(true);
    splitMidPointDropDown.setVisible(true);
    splitWhitePointDropDown.setVisible(true);
  }

  /**
   * Hides the BMW picker components.
   */
  private void hideBMWPicker() {
    splitBlackPointDropDown.setVisible(false);
    splitMidPointDropDown.setVisible(false);
    splitWhitePointDropDown.setVisible(false);
  }

  /**
   * resets all the components in the view.
   */
  private void resetEverything() {
    splitBlackPointDropDown.setSelectedIndex(0);
    splitMidPointDropDown.setSelectedIndex(0);
    splitWhitePointDropDown.setSelectedIndex(0);
    blackPointDropDown.setSelectedIndex(0);
    midPointDropDown.setSelectedIndex(0);
    whitePointDropDown.setSelectedIndex(0);
    splitOperationJComboBox.setSelectedIndex(0);
    componentTypesJComboBox.setSelectedIndex(0);
    compressSlider.setValue(0);
    previewSlider.setValue(0);
    imageLabel.setIcon(null);
    histogramLabel.setIcon(null);
  }

  /**
   * To enable and disable all the operations of the application. If enable is true, enables all
   * operations else if enable is false disables all the operations.
   */
  private void disableOrEnableEverything(boolean enable) {
    saveOption.setEnabled(enable);
    splitOperationJComboBox.setEnabled(enable);
    previewSlider.setEnabled(enable);
    previewButton.setEnabled(enable);
    splitBlackPointDropDown.setEnabled(enable);
    splitMidPointDropDown.setEnabled(enable);
    splitWhitePointDropDown.setEnabled(enable);
    horizontalFlipButton.setEnabled(enable);
    verticalFlipButton.setEnabled(enable);
    componentTypesJComboBox.setEnabled(enable);
    visualizeComponentButton.setEnabled(enable);
    blurButton.setEnabled(enable);
    sharpenButton.setEnabled(enable);
    greyscaleButton.setEnabled(enable);
    sepiaButton.setEnabled(enable);
    compressSlider.setEnabled(enable);
    compressButton.setEnabled(enable);
    colorCorrectButton.setEnabled(enable);
    blackPointDropDown.setEnabled(enable);
    midPointDropDown.setEnabled(enable);
    whitePointDropDown.setEnabled(enable);
    levelsAdjustButton.setEnabled(enable);
  }

  /**
   * To enable and disable revert and retain buttons. If enable is true, enables all operations else
   * if enable is false disables all the operations.
   */
  private void disableOrEnableRevertRetain(boolean enable) {
    revertButton.setEnabled(enable);
    retainButton.setEnabled(enable);
  }

  /**
   * To handle the action to be performed when user chooses load menu option.
   *
   * @param features The GUIController instance to handle the image loading.
   */
  private void handleLoadImage(GUIController features) {
    if (isImageLoaded) {
      int response = displayPopup(
          "Any unsaved changes will not be retained. Do you like to continue?",
          "Unsaved changes");
      if (response == 0) {
        resetEverything();
        isImageLoaded = false;
      }
    }
    if (!isImageLoaded) {
      final JFileChooser fChooser = new JFileChooser();
      fChooser.setFileFilter(supportedFileExt);
      int retValue = fChooser.showOpenDialog(this);
      if (retValue == JFileChooser.APPROVE_OPTION) {
        File f = fChooser.getSelectedFile();
        features.loadImage(f.getPath());
        imagePanel.setBorder(BorderFactory.createTitledBorder(f.getPath()));
        isImageLoaded = true;
        disableOrEnableEverything(true);
      }
    }
  }

  /**
   * To handle the action to be performed when user chooses save menu option.
   *
   * @param features The GUIController instance to handle the image saving.
   */
  private void handleSaveImage(GUIController features) {
    final JFileChooser fChooser = new JFileChooser();
    FileFilter pngFilter = new FileNameExtensionFilter("PNG File", ".png");
    FileFilter jpgFilter = new FileNameExtensionFilter("JPG File", ".jpg");
    FileFilter ppmFilter = new FileNameExtensionFilter("PPM File", ".ppm");
    FileFilter bpmFilter = new FileNameExtensionFilter("BMP File", ".bmp");
    fChooser.setFileFilter(pngFilter);
    fChooser.addChoosableFileFilter(jpgFilter);
    fChooser.addChoosableFileFilter(ppmFilter);
    fChooser.addChoosableFileFilter(bpmFilter);
    int retValue = fChooser.showSaveDialog(this);
    if (retValue == JFileChooser.APPROVE_OPTION) {
      File f = fChooser.getSelectedFile();
      FileFilter selectedFilter = fChooser.getFileFilter();
      String extension = " ";
      if (selectedFilter == pngFilter) {
        extension = ".png";
      } else if (selectedFilter == jpgFilter) {
        extension = ".jpg";
      } else if (selectedFilter == ppmFilter) {
        extension = ".ppm";
      } else if (selectedFilter == bpmFilter) {
        extension = ".bmp";
      }
      try {
        String path = f.getAbsolutePath() + extension;
        String[] parts = path.split("\\.");
        String format = parts[parts.length - 1];
        List<String> validExtensions = Arrays.asList("png", "jpg", "ppm", "bmp");
        if (validExtensions.contains(format)) {
          features.saveImage(path, "currentImg");
          displayInformativeMessage(mainPanel, "Successfully saved the image.");
        } else {
          displayErrorMessage("Please provide a valid file path");
        }
      } catch (InputMismatchException e) {
        displayErrorMessage("Could not save the image. Please provide a valid path and try "
            + "again");
      }
    }
  }

  /**
   * To handle the action to be performed when user clicks Preview Operation button.
   *
   * @param features the GUI call back controller
   */
  private void handlePreviewButton(GUIController features) {
    int selectedIndex = splitOperationJComboBox.getSelectedIndex();
    String selectedValue = (String) splitOperationJComboBox.getSelectedItem();
    if (previewSlider.getValue() != 0 && selectedIndex != 0 && selectedValue != null) {
      isPreviewed[0] = true;
      if (selectedIndex == 6) {
        if (splitBlackPointDropDown.getSelectedItem() != null
            && splitMidPointDropDown.getSelectedItem() != null
            && splitWhitePointDropDown.getSelectedItem() != null
            && splitBlackPointDropDown.getSelectedIndex() != 0
            && splitMidPointDropDown.getSelectedIndex() != 0
            && splitWhitePointDropDown.getSelectedIndex() != 0) {
          int b = (int) splitBlackPointDropDown.getSelectedItem();
          int m = (int) splitMidPointDropDown.getSelectedItem();
          int w = (int) splitWhitePointDropDown.getSelectedItem();
          String commandLineForAdjustLevels =
              selectedValue.toLowerCase() + " " + b + " " + m + " " + w;
          features.preview(commandLineForAdjustLevels, previewSlider.getValue());
          hideBMWPicker();
        }
      } else {
        features.preview(selectedValue.toLowerCase(), previewSlider.getValue());
      }
      disableOrEnableRevertRetain(true);
    } else {
      this.displayErrorMessage(
          "Please select preview width to preview the image.");
    }
  }

  /**
   * To handle the action to be performed when user clicks Retain Operation button.
   *
   * @param features the GUI call back controller
   */
  private void handleRetainButton(GUIController features) {
    if (!isPreviewed[0]) {
      this.displayErrorMessage(
          "First preview the image to revert back to original image");
    } else {
      int selectedIndex = splitOperationJComboBox.getSelectedIndex();
      switch (selectedIndex) {
        case 1:
          features.blur();
          break;
        case 2:
          features.sharpen();
          break;
        case 3:
          features.sepia();
          break;
        case 4:
          features.greyscale();
          break;
        case 5:
          features.colorCorrect();
          break;
        case 6:
          int b = (int) splitBlackPointDropDown.getSelectedItem();
          int m = (int) splitMidPointDropDown.getSelectedItem();
          int w = (int) splitWhitePointDropDown.getSelectedItem();
          String selectedValue = (String) splitOperationJComboBox.getSelectedItem();
          String commandLineForAdjustLevels =
              selectedValue.toLowerCase() + " " + b + " " + m + " " + w;
          features.preview(commandLineForAdjustLevels, previewSlider.getValue());
          break;
        default:
          break;
      }
      splitOperationJComboBox.setSelectedIndex(0);
      previewSlider.setValue(0);
      disableOrEnableRevertRetain(false);
    }
    hideBMWPicker();
  }

  /**
   * To handle the action to be performed when user clicks Revert To Original button.
   *
   * @param features the GUI call back controller
   */
  private void handleRevertButton(GUIController features) {
    if (!isPreviewed[0]) {
      this.displayErrorMessage(
          "First preview the image to revert back to original image");
    } else {
      features.revert();
      disableOrEnableRevertRetain(false);
      splitOperationJComboBox.setSelectedIndex(0);
      previewSlider.setValue(0);
    }
    hideBMWPicker();
  }


  /**
   * To handle the action to be performed when user clicks levels adjust button.
   *
   * @param features the GUI call back controller
   */
  private void handleVisualiseComponentsButton(GUIController features) {
    if (componentTypesJComboBox.getSelectedItem() != null
        && componentTypesJComboBox.getSelectedIndex() != 0) {
      int selectedIndex = componentTypesJComboBox.getSelectedIndex();
      String componentName = null;
      if (selectedIndex == 1) {
        componentName = "red-component";
      } else if (selectedIndex == 2) {
        componentName = "green-component";
      } else if (selectedIndex == 3) {
        componentName = "blue-component";
      }
      features.visualizeComponent(componentName);
    } else if (componentTypesJComboBox.getSelectedIndex() == 0) {
      this.displayErrorMessage("Please select the component type to visualize image.");
    }
  }

  /**
   * To handle the action to be performed when user clicks levels adjust button.
   *
   * @param features the GUI call back controller
   */
  private void handleLevelsAdjustButton(GUIController features) {
    if (blackPointDropDown.getSelectedItem() != null &&
        midPointDropDown.getSelectedItem() != null
        && whitePointDropDown.getSelectedItem() != null
        && blackPointDropDown.getSelectedIndex() != 0 &&
        midPointDropDown.getSelectedIndex() != 0
        && whitePointDropDown.getSelectedIndex() != 0) {
      int b = (int) blackPointDropDown.getSelectedItem();
      int m = (int) midPointDropDown.getSelectedItem();
      int w = (int) whitePointDropDown.getSelectedItem();
      features.adjustLevels(b, m, w);
    } else {
      this.displayErrorMessage(
          "Please select black, mid and white points to adjust levels of image.");
    }
  }

  @Override
  public void setImageInPanel(String[] image) {
    ImageUtility util = new ImageUtility();
    BufferedImage bufferedImage = util.convertRawImageToBufferedImage(image);
    imageLabel.setText("");
    if (bufferedImage != null) {
      imageLabel.setIcon(new ImageIcon(bufferedImage));
    }
  }

  @Override
  public void setHistogramInPanel(String[] image) {
    ImageUtility util = new ImageUtility();
    BufferedImage bufferedImage = util.convertRawImageToBufferedImage(image);
    histogramLabel.setText("");
    if (bufferedImage != null) {
      histogramLabel.setIcon(new ImageIcon(bufferedImage));
    }
  }

  @Override
  public void displayErrorMessage(String errorMessage) {
    JOptionPane.showMessageDialog(mainPanel, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
  }

  /**
   * Displays an informative message to the user.
   *
   * @param parentFrame   The parent component for the message dialog.
   * @param informMessage The informative message to be displayed.
   */
  private void displayInformativeMessage(Component parentFrame, String informMessage) {
    JOptionPane.showMessageDialog(parentFrame, informMessage, "Information",
        JOptionPane.INFORMATION_MESSAGE);
  }

  /**
   * To display a prompt message to the user.
   *
   * @param displayMsg a message to put in the prompt
   * @param title      a title to the prompt
   * @return 0 0r 1 based on user response yes is 1 and no is 0
   */
  private int displayPopup(String displayMsg, String title) {
    return JOptionPane.showConfirmDialog(mainPanel, displayMsg, title,
        JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
  }

  /**
   * Custom ComboBoxModel class for handling ComboBox initialization.
   */
  private static class CustomComboBoxModel extends DefaultComboBoxModel<Object> {

    /**
     * Constructor for CustomComboBoxModel.
     *
     * @param initialString The initial string to be added to the ComboBox.
     */
    public CustomComboBoxModel(String initialString) {
      addElement(initialString);
      for (int i = 0; i <= 255; i++) {
        addElement(i);
      }
    }
  }

}
