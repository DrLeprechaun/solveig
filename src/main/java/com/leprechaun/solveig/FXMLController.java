package com.leprechaun.solveig;

import com.leprechaun.solveig.entities.ResponseEntity;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ScrollPane;
import com.leprechaun.solveig.http.DatabaseControl;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import com.leprechaun.solveig.json.DatabaseListParser;
import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;


public class FXMLController implements Initializable {
    
    @FXML
    private TextArea outputConsole;
    @FXML
    private TextField hostField;
    @FXML
    private TextField portField;
    @FXML
    private TextField createDatabaseName;
    @FXML
    private Button showDatabasesButton;
    @FXML
    private Button createDatabaseButton;
    @FXML
    private Button dropDatabaseButton;
    @FXML
    private Button exitButton;
    @FXML
    private ChoiceBox dropDatabaseList;
    @FXML
    private ListView databaseList;
    
    //Button actions
    
    //Show databases
    @FXML
    private void showDatabasesRequest(ActionEvent event) {
        ResponseEntity responseEntity = DatabaseControl.showDatabases(hostField.getText(), portField.getText());
        outputConsole.appendText("SHOW DATABASES: " + responseEntity.getCode() + ": " + responseEntity.getBody() + "\n");
        if (responseEntity.getCode().contains("200")) {
            try {
                databaseList.getItems().clear();
                dropDatabaseList.getItems().clear();
                for (String str : DatabaseListParser.databaseStringList(responseEntity.getBody())) {
                    databaseList.getItems().add(str);
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }
    
    //Create database
    @FXML
    private void createDatabase(ActionEvent event) {
        ResponseEntity responseEntity = DatabaseControl.createDatabase(hostField.getText(), portField.getText(), createDatabaseName.getText());
        outputConsole.appendText("CREATE DATABASE: " + responseEntity.getCode() + ": " + responseEntity.getBody() + "\n");
    }
    
    //Choose database to drop
    @FXML
    private void chooseDatabasesToDrop() {
        ResponseEntity responseEntity = DatabaseControl.showDatabases(hostField.getText(), portField.getText());
        outputConsole.appendText("Get database list to drop: " + responseEntity.getCode() + ": " + "\n");
        if (responseEntity.getCode().contains("200")) {
            try {
                dropDatabaseList.getItems().clear();
                for (String str : DatabaseListParser.databaseStringList(responseEntity.getBody())) {                 
                    dropDatabaseList.getItems().add(str);
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }
    
    //Drop database
    @FXML
    private void dropDatabase (ActionEvent event) {
        
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirm drop");
        alert.setHeaderText("Confirm drop");
        alert.setContentText("Drop database " + dropDatabaseList.getValue().toString() + "?");
        Optional<ButtonType> result = alert.showAndWait();
        
        if (result.get() == ButtonType.OK){
            ResponseEntity responseEntity = DatabaseControl.dropDatabase(hostField.getText(), portField.getText(), dropDatabaseList.getValue().toString());
            outputConsole.appendText("DROP DATABASE " + dropDatabaseList.getValue().toString() + ": " + responseEntity.getCode() + ": " + responseEntity.getBody() + "\n");
        }
    }
    
    //Clear output console
    @FXML
    private void clearOutputConsole (ActionEvent event) {
        outputConsole.clear();
    }
    
    //Exit application
    @FXML
    private void exitApplication (ActionEvent event) {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }
    
    //Initialize
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        createDatabaseButton.setStyle("-fx-base: #b3ffcc;");
        dropDatabaseButton.setStyle("-fx-base: #ffb3b3;");
        outputConsole.setEditable(false);
    }    
}
