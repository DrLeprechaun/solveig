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
import com.leprechaun.solveig.json.UserListParser;
import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;
import com.leprechaun.solveig.entities.UserEntity;
import java.util.ArrayList;
import java.util.List;
import com.leprechaun.solveig.entities.UserEntity;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Separator;

public class FXMLController implements Initializable {

    //Main control tab
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
    private ListView databaseList;

    @FXML
    private Pane connectionSettingsPane;

    @FXML
    private Button createUserButton;
    @FXML
    private Button dropUserButton;
    @FXML
    private ListView userList;
    @FXML
    private TextField userName;
    @FXML
    private TextField userPassword;

    @FXML
    private TextField usernameField;
    @FXML
    private TextField passwordField;
    @FXML
    private Button saveConfigsButton;

    //
    @FXML
    private Pane queryPane;
    @FXML
    private TextField queryField;
    @FXML
    private Button executeButton;
    @FXML
    private ChoiceBox choiceDatabase;
    @FXML
    private ChoiceBox choiceQueryTemplate;

    //Data containers
    private ObservableList<UserEntity> usersDataList = FXCollections.observableArrayList();
    private ObservableList<String> databasesDataList = FXCollections.observableArrayList();

    //Button actions
    //Databases
    //Show databases
    @FXML
    private void showDatabasesRequest(ActionEvent event) {
        ResponseEntity responseEntity = DatabaseControl.showDatabases(hostField.getText(), portField.getText());
        outputConsole.appendText("SHOW DATABASES: " + responseEntity.getCode() + ": " + responseEntity.getBody() + "\n");
        if (responseEntity.getCode().contains("200")) {
            try {
                databasesDataList.clear();
                databaseList.getItems().clear();
                for (String str : DatabaseListParser.databaseStringList(responseEntity.getBody())) {
                    databasesDataList.add(str);
                }
                databaseList.setItems(databasesDataList);
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

    //Drop database
    @FXML
    private void dropDatabase(ActionEvent event) {

        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirm drop");
        alert.setHeaderText("Confirm drop");
        alert.setContentText("Drop database " + databaseList.getSelectionModel().getSelectedItem().toString() + "?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK) {
            ResponseEntity responseEntity = DatabaseControl.dropDatabase(hostField.getText(), portField.getText(), databaseList.getSelectionModel().getSelectedItem().toString());
            outputConsole.appendText("DROP DATABASE " + databaseList.getSelectionModel().getSelectedItem().toString() + ": " + responseEntity.getCode() + ": " + responseEntity.getBody() + "\n");
        }
    }

    //Users
    //Show users
    @FXML
    private void showUsersRequest(ActionEvent event) {
        ResponseEntity responseEntity = DatabaseControl.showUsers(hostField.getText(), portField.getText());
        outputConsole.appendText("SHOW USERS: " + responseEntity.getCode() + ": " + responseEntity.getBody() + "\n");
        if (responseEntity.getCode().contains("200")) {
            try {
                userList.getItems().clear();
                usersDataList.clear();

                for (UserEntity userEntity : UserListParser.userStringList(responseEntity.getBody())) {
                    usersDataList.add(new UserEntity(userEntity.getName(), userEntity.getAdminFlag()));
                }

                for (UserEntity ue : usersDataList) {
                    userList.getItems().add(ue.getName());
                }

            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    //Create user
    @FXML
    private void createUser(ActionEvent event) {
        ResponseEntity responseEntity = DatabaseControl.createUser(hostField.getText(), portField.getText(), userName.getText(), userPassword.getText());
        outputConsole.appendText("CREATE USER: " + responseEntity.getCode() + ": " + responseEntity.getBody() + "\n");
    }

    //Drop database
    @FXML
    private void dropUser(ActionEvent event) {

        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirm drop");
        alert.setHeaderText("Confirm drop");
        alert.setContentText("Drop user " + userList.getSelectionModel().getSelectedItem().toString() + "?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK) {
            ResponseEntity responseEntity = DatabaseControl.dropUser(hostField.getText(), portField.getText(), userList.getSelectionModel().getSelectedItem().toString());
            outputConsole.appendText("DROP USER " + userList.getSelectionModel().getSelectedItem().toString() + ": " + responseEntity.getCode() + ": " + responseEntity.getBody() + "\n");
        }
    }

    //Save connection configs
    @FXML
    private void saveConnectionConfigs(ActionEvent event) {
        try {
            ConfigManager configManager = new ConfigManager();
            configManager.writeConnectionProperties(hostField.getText(), portField.getText(), usernameField.getText(), passwordField.getText());

            outputConsole.appendText("Connection settings are succesfully saved \n");
        } catch (Exception e) {
            outputConsole.appendText("Error while saving connection settings \n");
        }
    }

    //TAB2
    //Choose database to use
    @FXML
    private void chooseDatabaseToUse() {
        /*
         ResponseEntity responseEntity = DatabaseControl.showDatabases(hostField.getText(), portField.getText());
         outputConsole.appendText("Get database list to drop: " + responseEntity.getCode() + ": " + "\n");
         if (responseEntity.getCode().contains("200")) {
         try {
         choiceDatabase.getItems().clear();
         for (String str : DatabaseListParser.databaseStringList(responseEntity.getBody())) {                 
         choiceDatabase.getItems().add(str);
         }
         } catch (Exception e) {
         System.out.println(e);
         }
         }
         */
        ResponseEntity responseEntity = DatabaseControl.showDatabases(hostField.getText(), portField.getText());
        outputConsole.appendText("SHOW DATABASES (to USE): " + responseEntity.getCode() + ": " + responseEntity.getBody() + "\n");
        if (responseEntity.getCode().contains("200")) {
            try {
                databasesDataList.clear();
                databaseList.getItems().clear();
                for (String str : DatabaseListParser.databaseStringList(responseEntity.getBody())) {
                    databasesDataList.add(str);
                }
                choiceDatabase.setItems(databasesDataList);
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    //Clear output console
    @FXML
    private void clearOutputConsole(ActionEvent event) {
        outputConsole.clear();
    }

    //Exit application
    @FXML
    private void exitApplication(ActionEvent event) {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }

    //Initialize
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        createDatabaseButton.setStyle("-fx-base: #d0d0e1;");
        dropDatabaseButton.setStyle("-fx-base: #ffccff;");

        createUserButton.setStyle("-fx-base: #d0d0e1;");
        dropUserButton.setStyle("-fx-base: #ffccff;");

        connectionSettingsPane.setStyle("-fx-background-color: #d0d0e1;");

        executeButton.setStyle("-fx-base: #d0d0e1;");
        queryPane.setStyle("-fx-background-color: #d0d0e1;");

        //Fill Query Template List
        Separator listSeparator = new Separator();
        choiceQueryTemplate.setItems(FXCollections.observableArrayList(
                "SHOW DATABASES",
                "CREATE DATABASE",
                "DROP DATABASE",
                listSeparator,
                "SHOW SERIES",
                "SHOW MEASUREMENTS",
                "SHOW TAG KEYS",
                "SHOW TAG VALUES",
                listSeparator,
                "SHOW RETENTIONS POLICIES",
                "CREATE RETENTION POLICY",
                "DROP RETENTION POLICY",
                listSeparator,
                "SHOW USERS",
                "CREATE USER",
                "CREATE ADMIN USER",
                "DROP USER"
        ));

        //Query template listener
        choiceQueryTemplate.getSelectionModel().selectedIndexProperty()
                .addListener(new ChangeListener<Number>() {
                    public void changed(ObservableValue ov, Number value, Number new_value) {
                        queryField.clear();
                        //queryField.setText("ololo");
                        switch (choiceQueryTemplate.getSelectionModel().getSelectedIndex()) {
                            case 0:
                                queryField.setText("SHOW DATABASES");
                                break;
                            case 1:
                                queryField.setText("CREATE DATABASE \"db_name\"");
                                break;
                            case 2:
                                queryField.setText("DROP DATABASE \"db_name\"");
                                break;
                            case 4:
                                queryField.setText("SHOW SERIES");
                                break;
                            case 5:
                                queryField.setText("SHOW MEASUREMENTS");
                                break;
                            case 6:
                                queryField.setText("SHOW TAG KEYS FROM \"measurement_name\"");
                                break;
                            case 7:
                                queryField.setText("SHOW TAG VALUES FROM \"measurement_name\" WITH KEY = \"tag_key\"");
                                break;
                            case 9:
                                queryField.setText("SHOW RETENTION POLICIES ON \"db_name\"");
                                break;
                            case 10:
                                queryField.setText("CREATE RETENTION POLICY \"rp_name\" ON \"db_name\" DURATION 30d REPLICATION 1 DEFAULT");
                                break;
                            case 11:
                                queryField.setText("DROP RETENTION POLICY \"rp_name\"");
                                break;
                            case 13:
                                queryField.setText("SHOW USERS");
                                break;
                            case 14:
                                queryField.setText("CREATE USER \"username\" WITH PASSWORD 'password'");
                                break;
                            case 15:
                                queryField.setText("CREATE USER \"username\" WITH PASSWORD 'password' WITH ALL PRIVILEGES");
                                break;
                            case 16:
                                queryField.setText("DROP USER \"username\"");
                                break;
                        }
                    }
                });

        //Load connection settings
        try {

            ConfigManager configManager = new ConfigManager();

            hostField.setText(configManager.readProperty("connection.properties", "host"));
            portField.setText(configManager.readProperty("connection.properties", "port"));
            usernameField.setText(configManager.readProperty("connection.properties", "username"));
            passwordField.setText(configManager.readProperty("connection.properties", "password"));

            outputConsole.appendText("Connection settings are succesfully loaded \n");
        } catch (Exception e) {
            outputConsole.appendText("Error while loading connection settings \n");
        }

        outputConsole.setEditable(false);
    }
}
