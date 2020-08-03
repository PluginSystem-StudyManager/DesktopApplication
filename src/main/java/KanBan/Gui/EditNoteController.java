package KanBan.Gui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class EditNoteController implements Initializable {
    private int previousStep = 0;
    private int currentStep = 0;
    private String priority;
    private String[] descriptions;
    private List<String> files = new ArrayList<>();
    private ToggleGroup steps = new ToggleGroup();
    
    @FXML
    private TextField textFieldTitle;
    
    @FXML
    private TextArea textAreaDescription;
    
    @FXML
    private Button buttonAdd;
    
    @FXML
    private Button buttonFinish;
    
    @FXML
    private Button buttonDelete;
    
    @FXML
    private HBox boxSteps;
    
    @FXML
    public void createNote() {
        descriptions[currentStep] = textAreaDescription.getText();
        
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        
        if (writeXMLFile()) {
            alert.setContentText("Notiz wurde erstellt.");
            alert.showAndWait().filter(button -> button == ButtonType.OK).ifPresent(button -> {
                Stage stage = (Stage) buttonFinish.getScene().getWindow();
                stage.close();
            });
        } else {
            alert.setContentText("Notiz konnte nicht erstellt.");
            alert.showAndWait();
        }
    }
    
    @FXML
    public void addFile() {
        Stage stage = (Stage) buttonAdd.getScene().getWindow();
        
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        List<File> selectedFiles = fileChooser.showOpenMultipleDialog(stage);
        
        if (selectedFiles != null) {
            files.listIterator().add(selectedFiles.listIterator().next().getAbsolutePath());
        }
    }
    
    @FXML
    public void delete() {
        File file = new File("./" + textFieldTitle.getText() + ".xml");
        
        Alert alert = new Alert(null);
        if (file.exists()) {
            alert.setAlertType(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Möchten Sie diese Notiz permamnent löschen?");
            alert.showAndWait().filter(button -> button == ButtonType.OK).ifPresent(button -> {
                file.delete();
            });
            
            alert.setAlertType(Alert.AlertType.INFORMATION);
            alert.setContentText("Notiz wurde gelöscht.");
            alert.showAndWait().filter(button -> button == ButtonType.OK).ifPresent(button -> {
                Stage stage = (Stage) buttonDelete.getScene().getWindow();
                stage.close();
            });
        } else {
            alert.setAlertType(Alert.AlertType.INFORMATION);
            alert.setContentText("Notiz konnte nicht gefunden werden.");
            alert.showAndWait();
        }
    }
    
    public void adjusting(int n, String s) {
        descriptions = new String[n];
        priority = s;
        
        for (int i = 0; i < n; i++) {
            ToggleButton button = new ToggleButton("Step " + (i + 1));
            button.setId("button" + i);
            button.setToggleGroup(steps);
            steps.getToggles().get(0).setSelected(true);
            
            button.setOnAction(actionEvent -> {
                ToggleButton source = (ToggleButton) actionEvent.getSource();
                
                previousStep = currentStep;
                currentStep = Character.getNumericValue(source.getId().charAt(6));
                
                descriptions[previousStep] = textAreaDescription.getText();
                textAreaDescription.clear();
                if (descriptions[currentStep] != null) {
                    textAreaDescription.setText(descriptions[currentStep]);
                }
            });
            
            boxSteps.getChildren().add(button);
        }
    }
    
    // ToDo Auslagern
    private boolean writeXMLFile() {
        File file = new File("./" + textFieldTitle.getText() + ".xml");
        
        try {
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = builderFactory.newDocumentBuilder();
            Document document = builder.newDocument();
            
            Element root = document.createElement("note");
            document.appendChild(root);
            
            Element nodeTitle = document.createElement("title");
            nodeTitle.appendChild(document.createTextNode(textFieldTitle.getText()));
            root.appendChild(nodeTitle);
            
            Element nodePriority = document.createElement("priority");
            nodePriority.appendChild(document.createTextNode(priority));
            root.appendChild(nodePriority);
            
            Element nodeSteps = document.createElement("steps");
            nodeSteps.setAttribute("amount", Integer.toString(boxSteps.getChildren().size()));
            root.appendChild(nodeSteps);
            
            for (int i = 0; i < boxSteps.getChildren().size(); i++) {
                Element nodeStep = document.createElement("step");
                nodeStep.setAttribute("id", Integer.toString(i + 1));
                nodeSteps.appendChild(nodeStep);
                
                Element nodeDescription = document.createElement("description");
                nodeDescription.appendChild(document.createTextNode(descriptions[i]));
                nodeStep.appendChild(nodeDescription);
            }
            
            Element nodeFiles = document.createElement("files");
            nodeFiles.setAttribute("amount", Integer.toString(files.size()));
            root.appendChild(nodeFiles);
            
            for (int i = 0; i < files.size(); i++) {
                Element nodeFile = document.createElement("file");
                nodeFile.appendChild(document.createTextNode(files.get(i)));
                nodeFiles.appendChild(nodeFile);
            }
            
            TransformerFactory transformFactory = TransformerFactory.newInstance();
            Transformer transformer = transformFactory.newTransformer();
            DOMSource domSource = new DOMSource(document);
            StreamResult streamSource = new StreamResult(file);
            transformer.transform(domSource, streamSource);
        } catch (ParserConfigurationException | TransformerException e) {
            e.printStackTrace();
        }
        
        return file.exists();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    
    }
}
