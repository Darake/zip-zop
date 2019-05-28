
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

/**
 * The graphical user interface for the application.
 */
public class GUI extends Application {
    
    private Huffman huffman;
    
    @Override
    public void init() {
        this.huffman = new Huffman();
    }

    @Override
    public void start(Stage stage) throws Exception {
        Button selectButton  = new Button("Select file");
             
        Button compressButton = new Button("Compress");
        
        Button decompressButton = new Button("Decompress");
        
        HBox hbox = new HBox();
        hbox.setSpacing(10);
        hbox.getChildren().add(selectButton);
        hbox.getChildren().add(compressButton);
        hbox.getChildren().add(decompressButton);
        
        VBox vbox = new VBox();
        vbox.setPadding(new Insets(15, 15, 15, 15));
        vbox.setSpacing(10);
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().add(hbox);
        
        StringBuilder input = new StringBuilder();
        selectButton.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Select file");
            input.setLength(0);
            input.append(fileChooser.showOpenDialog(stage).getAbsolutePath());
        });
        
        StringBuilder output = new StringBuilder();
        compressButton.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save compressed file as");
            output.setLength(0);
            output.append(fileChooser.showSaveDialog(stage).getAbsolutePath());
            huffman.compress(input.toString(), output.toString());
        });
        decompressButton.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save decompressed file as");
            output.setLength(0);
            output.append(fileChooser.showSaveDialog(stage).getAbsolutePath());
            huffman.decompress(input.toString(), output.toString());
        });
        
        Scene scene = new Scene(vbox);
        
        stage.setScene(scene);
        stage.setTitle("Zip-Zop");
        stage.show();
    }
    
    public static void main(String[] args) {      
        launch(GUI.class);
    }
}
