package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.*;

public class ControllerOpenMenu {
    @FXML
    private TextField path;

    @FXML
    private ListView<File> filesView;

    private ObservableList<File> files;
    private Controller controller;
    private MultipleSelectionModel<File> langsSelectionModel;
    private File selection;

    @FXML
    private void initialize(){
        File[] roots = File.listRoots();
        files = FXCollections.observableArrayList();
        filesView.setItems(files);
        for (File file : roots) {
            files.add(file);
        }
        path.setText("Корень");
        langsSelectionModel = filesView.getSelectionModel();
    }

    @FXML
    private Button open;

    @FXML
    private void open(){
        selection = langsSelectionModel.getSelectedItem();
        if (selection.isDirectory()) {
            files = FXCollections.observableArrayList(selection.listFiles());
            filesView.setItems(files);
            path.setText(selection.getAbsolutePath());
        } else {
            String line;
            String str = selection.getName();
            StringBuilder sb = new StringBuilder(str);
            if (sb.indexOf(".txt", sb.length()-4) != -1 || sb.indexOf(".ini", sb.length()-4) != -1) {
                try {
                    BufferedReader reader = new BufferedReader(new FileReader(selection));
                    controller.getTextArea().setText("");
                    while ((line = reader.readLine()) != null) {
                        controller.getTextArea().appendText(line + "\n");
                    }
                    reader.close();
                    Stage stage = (Stage) open.getScene().getWindow();
                    stage.close();
                } catch (Exception e) {
                    message("Exception: " + e.getMessage(), "Exception");
                    e.printStackTrace();
                }
            } else {
                message("Имя файла должно заканчиваться на .txt или .ini", "Ошибка");
            }
        }
    }

    private void message(String message, String title) {
        Stage primaryStage = (Stage) open.getScene().getWindow();
        Label label = new Label(message);
        StackPane layout = new StackPane();
        layout.getChildren().add(label);

        Scene scene = new Scene(layout, 230, 100);

        // New window (Stage)
        Stage newWindow = new Stage();
        newWindow.setTitle(title);
        newWindow.setScene(scene);

        // Specifies the modality for new window.
        newWindow.initModality(Modality.WINDOW_MODAL);

        // Specifies the owner Window (parent) for new window
        newWindow.initOwner(primaryStage);

        // Set position of second window, related to primary window.
        newWindow.setX(primaryStage.getX() + 200);
        newWindow.setY(primaryStage.getY() + 100);

        newWindow.show();


    }

    @FXML
    private void up(){
        File file = new File(path.getText());
        if (file.getParentFile() == null) {
            files = FXCollections.observableArrayList(File.listRoots());
            path.setText("Корень");
        } else {
            files = FXCollections.observableArrayList(file.getParentFile().listFiles());
            path.setText(file.getParentFile().toString());
        }
        filesView.setItems(files);
    }


    public void setController(Controller controller) {
        this.controller = controller;
    }


}
