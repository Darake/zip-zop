package zipzop.ui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import zipzop.huffman.Huffman;

/** The graphical user interface for the application. */
public class UserInterface extends Application {

  private Huffman huffman;
  private StringBuilder input;
  private StringBuilder output;

  @Override
  public void init() {
    this.huffman = new Huffman();
    this.input = new StringBuilder();
    this.output = new StringBuilder();
  }
  
  private Button createInputSelectionButton(Stage stage) {
    Button selectButton = new Button("Select file");
    selectButton.setOnAction(e -> {
      FileChooser fileChooser = new FileChooser();
      fileChooser.setTitle("Select file");
      input.setLength(0);
      input.append(fileChooser.showOpenDialog(stage).getAbsolutePath());
    });
    return selectButton;
  }
  
  private Button createCompressionButton(Stage stage) {
    Button compressButton = new Button("Compress");
    compressButton.setOnAction(e -> {
      FileChooser fileChooser = new FileChooser();
      fileChooser.setTitle("Save compressed file as");
      output.setLength(0);
      output.append(fileChooser.showSaveDialog(stage).getAbsolutePath());
      huffman.compress(input.toString(), output.toString());
    });
    return compressButton;
  }
  
  private Button createDecompressionButton(Stage stage) {
    Button decompressButton = new Button("Decompress");
    decompressButton.setOnAction(e -> {
      FileChooser fileChooser = new FileChooser();
      fileChooser.setTitle("Save decompressed file as");
      output.setLength(0);
      output.append(fileChooser.showSaveDialog(stage).getAbsolutePath());
      huffman.decompress(input.toString(), output.toString());
    });
    return decompressButton;
  }
  
  private Scene buildScene(Stage stage) {
    Button selectButton = createInputSelectionButton(stage);

    Button compressButton = createCompressionButton(stage);

    Button decompressButton = createDecompressionButton(stage);

    HBox hbox = new HBox();
    hbox.setSpacing(10);
    hbox.getChildren().addAll(selectButton, compressButton, decompressButton);

    VBox vbox = new VBox();
    vbox.setPadding(new Insets(15, 15, 15, 15));
    vbox.setSpacing(10);
    vbox.setAlignment(Pos.CENTER);
    vbox.getChildren().add(hbox);
    
    return new Scene(vbox);
  }
  
  @Override
  public void start(Stage stage) throws Exception {
    Scene scene = buildScene(stage);

    stage.setScene(scene);
    stage.setTitle("Zip-Zop");
    stage.show();
  }
  
  /** Launches the graphical user interface. */
  public static void main(String[] args) {
    launch(args);
  }
}
