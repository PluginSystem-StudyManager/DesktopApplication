package KanBan.Gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CreateNoteController implements Initializable {
    private int numberOfSteps = 1;
    private String priority;
    
    @FXML
    private TextField textFieldSteps;
    
    @FXML
    private Button buttonCancel;
    
    @FXML
    private Button buttonOk;
    
    @FXML
    private ToggleGroup groupPriority;
    
    @FXML
    public void apply() {
        if (numberOfSteps > 0) {
            try {
                URL path = getClass().getResource("EditNote.fxml");
                FXMLLoader loader = new FXMLLoader(path);
                
                Stage stage = (Stage) buttonOk.getScene().getWindow();
                Scene scene = new Scene(loader.load());
                stage.setScene(scene);
                
                EditNoteController controller = loader.getController();
                controller.adjusting(numberOfSteps, priority);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Nur Zahlen zwischen 1 und unendlich sind möglich.");
            alert.showAndWait();
        }
    }
    
    @FXML
    public void cancel() {
        Stage stage = (Stage) buttonCancel.getScene().getWindow();
        stage.close();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        textFieldSteps.textProperty().addListener((observableValue, oldValue, newValue) -> {
            if (newValue.matches("\\d+")) {
                if (!newValue.equals(oldValue)) {
                    numberOfSteps = Integer.parseInt(newValue);
                }
            }
        });
        
        groupPriority.selectedToggleProperty().addListener((observableValue, oldToggle, newToggle) -> {
            if (newToggle != oldToggle) {
                priority = ((RadioButton) newToggle).getText();
            }
        });
    }
}
