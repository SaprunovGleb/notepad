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

public class ControllerSaveMenu {
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
        }
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

    @FXML
    TextField name;

    @FXML
    private void createDir(){
        String str = name.getText();
        StringBuilder sb = new StringBuilder(str);
        if (sb.length() > 0 & (sb.lastIndexOf(".") == -1)) {
            File dir = new File(path.getText() + "/" + sb);
            if (dir.mkdir() == true) {
                File file = new File(path.getText());
                files = FXCollections.observableArrayList(file.listFiles());
                filesView.setItems(files);
            };
        } else {
            message("Имя папки не должно содержать точку или быть пустым", "Ошибка");
        }
    }

    @FXML
    private void saveFile(){
        String str = name.getText();
        StringBuilder sb = new StringBuilder(str);
        if (sb.length() > 4 & (sb.indexOf(".txt", sb.length()-4) != -1 || sb.indexOf(".ini", sb.length()-4) != -1)) {
            File saveFile = new File(path.getText() + "/" + sb);
            try {
                FileWriter writer = new FileWriter(saveFile);
                writer.write(controller.getTextArea().getText());
                writer.flush();
                writer.close();
            } catch (IOException e) {
                message("Exception: " + e.getMessage(), "Exception");
                e.printStackTrace();
            }
            File file = new File(path.getText());
            files = FXCollections.observableArrayList(file.listFiles());
            filesView.setItems(files);
        } else {
            message("Имя файла должно заканчиваться на .txt или .ini", "Ошибка");
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



    public void setController(Controller controller) {
        this.controller = controller;
    }


}
