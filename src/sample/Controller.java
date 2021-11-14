package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Controller {
    @FXML
    private MenuItem open;

    public String basePath = null;
    private Stage dialogStage;

    @FXML
    private void openNewFile() throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("openMenu.fxml"));
        Parent root = loader.load();
        ControllerOpenMenu controller = loader.getController();
        controller.setController(this);
        dialogStage = new Stage();
        dialogStage.setTitle("Меню открытия");
        dialogStage.setScene(new Scene(root, 500, 500));
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.show();
    }

    @FXML
    private void saveThisFile() throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("saveMenu.fxml"));
        Parent root = loader.load();
        ControllerSaveMenu controller = loader.getController();
        controller.setController(this);
        dialogStage = new Stage();
        dialogStage.setTitle("Меню сохранения");
        dialogStage.setScene(new Scene(root, 500, 500));
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.show();
    }

    @FXML
    private MenuItem save;

    @FXML
    private TextArea textArea;

    public TextArea getTextArea() {
        return textArea;
    }

    private Main mainApp;

    public Controller() {

    }

    @FXML
    private void initialize() {

    }

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }

}
