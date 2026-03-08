package org.example.javafx_assignment_module5;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {

    private final TableView<Student> tableView = new TableView<>();
    private final ObservableList<Student> studentList = FXCollections.observableArrayList();

    private TextField idNum;
    private TextField firstName;
    private TextField lastName;
    private TextField department;
    private TextField major;
    private TextField email;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));

        BorderPane root = new BorderPane();

        MenuBar menuBar = createMenuBar();
        VBox leftSide = createLeftSide();
        VBox rightSide = createRightSide();
        StackPane centerPanel = createCenterPanel();
        Pane bottomBar = createBottomBar();

        root.setTop(menuBar);
        root.setLeft(leftSide);
        root.setRight(rightSide);
        root.setCenter(centerPanel);
        root.setBottom(bottomBar);

        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("JavaFX_Assignment");
        stage.setScene(scene);
        stage.show();
    }

    private MenuBar createMenuBar(){
        MenuBar menuBar = new MenuBar();

        Menu fileMenu = new Menu("File");
        Menu newItem = new Menu("New");
        Menu exitMenu = new Menu("Exit");
        fileMenu.getItems().addAll(newItem,new SeparatorMenuItem(), exitMenu);

        Menu editMenu = new Menu("Edit");
        Menu clearMenu = new Menu("Clear Fields");
        Menu deleteItem = new Menu("Delete Selected Items");
        editMenu.getItems().addAll(clearMenu,deleteItem);

        Menu themeMenu = new Menu("Theme");
        Menu defaultMenu = new Menu("Default Theme");
        Menu colorMenu = new Menu("Colored Theme");
        themeMenu.getItems().addAll(defaultMenu,colorMenu);

        Menu helpMenu = new Menu("Help");
        Menu aboutMenu = new Menu("About");
        helpMenu.getItems().addAll(aboutMenu);

        newItem.setOnAction(e -> clearFields());
        clearMenu.setOnAction(e -> clearFields());
        exitMenu.setOnAction(e -> ((Stage) menuBar.getScene().getWindow()).close());
        deleteItem.setOnAction(e-> deleteSelectedStudent());
        defaultMenu.setOnAction(e-> infoDisplay("Theme","Default theme is applied"));
        aboutMenu.setOnAction(e-> infoDisplay("About","JavaFX is good"));

        menuBar.getMenus().addAll(fileMenu,editMenu,themeMenu,helpMenu);
        return menuBar;
    }

    private VBox createLeftSide(){
        VBox leftSide = new VBox();
        Image image = new Image("C:\\Users\\Armaan Arora\\Desktop\\JavaFX_Assignment_Module5\\src\\main\\resources\\org\\example\\profile-icon-login-head-icon-vector.jpg");

        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(100);
        imageView.setFitHeight(100);
        imageView.setPreserveRatio(true);
        imageView.getStyleClass().add("login-image");

        leftSide.getChildren().add(imageView);
        return leftSide;
    }

    private VBox createRightSide(){
        VBox rightSide = new VBox();
        rightSide.getStyleClass().add("right-side");

        idNum = new TextField();
        idNum.setPromptText("ID");

        firstName = new TextField();
        firstName.setPromptText("First Name");

        lastName = new TextField();
        lastName.setPromptText("Last Name");

        department = new TextField();
        department.setPromptText("Department");

        major = new TextField();
        major.setPromptText("Major");

        email = new TextField();
        email.setPromptText("Email");

        Button clearButton = new Button("Clear");
        Button addButton = new Button("Add");
        Button deleteButton = new Button("Delete");
        Button editButton = new Button("Edit");

        clearButton.setOnAction(e->clearFields());
        addButton.setOnAction(e->addStudent());
        deleteButton.setOnAction(e->deleteSelectedStudent());
        editButton.setOnAction(e->editSelectedStudent());

        clearButton.getStyleClass().add("action-button");
        addButton.getStyleClass().add("action-button");
        deleteButton.getStyleClass().add("action-button");
        editButton.getStyleClass().add("action-button");

        rightSide.getChildren().addAll(idNum,firstName,lastName,department,major,email,clearButton,addButton,deleteButton,editButton);

        return rightSide;
    }

    private StackPane createCenterPanel(){
        StackPane centerPanel = new StackPane();
        centerPanel.getStyleClass().add("center-panel");

        TableColumn<Student, String> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Student, String> firstNameCol = new TableColumn<>("First Name");
        firstNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));

        TableColumn<Student, String> lastNameCol = new TableColumn<>("LastName");
        lastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));

        TableColumn<Student, String> departmentCol = new TableColumn<>("Department");
        departmentCol.setCellValueFactory(new PropertyValueFactory<>("department"));

        TableColumn<Student, String> majorCol = new TableColumn<>("Major");
        majorCol.setCellValueFactory(new PropertyValueFactory<>("major"));

        TableColumn<Student, String> emailCol = new TableColumn<>("Email");
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));

        tableView.getColumns().addAll(idCol,firstNameCol,lastNameCol,departmentCol,majorCol,emailCol);
        tableView.setItems(studentList);
        tableView.getStyleClass().add("student-table");

        tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                loadStudentIntoFields(newSelection);
            }});

        centerPanel.getChildren().add(tableView);
        return centerPanel;
    }

    private Pane createBottomBar(){
        Pane bottomBar = new Pane();
        bottomBar.getStyleClass().add("bottom-bar");
        return bottomBar;
    }

    private void clearFields(){
        idNum.clear();
        firstName.clear();
        lastName.clear();
        department.clear();
        major.clear();
        email.clear();
        tableView.getSelectionModel().clearSelection();
    }

    private void deleteSelectedStudent(){
        Student select = tableView.getSelectionModel().getSelectedItem();

        if (select == null){
            infoDisplay("No selection","Select a field to be deleted");
            return;
        }

        studentList.remove(select);
        clearFields();
    }

    private void addStudent(){
        if (idNum.getText().trim().isEmpty() || firstName.getText().trim().isEmpty() || lastName.getText().trim().isEmpty() || department.getText().trim().isEmpty() || major.getText().trim().isEmpty() || email.getText().trim().isEmpty()){
            infoDisplay("Missing Info","Fill all the information before adding");
            return;
        }

        Student student = new Student(idNum.getText(),firstName.getText(),lastName.getText(),department.getText(),major.getText(),email.getText());
        studentList.add(student);
        clearFields();
    }

    private void editSelectedStudent(){
        Student selected = tableView.getSelectionModel().getSelectedItem();

        if (selected == null){
            infoDisplay("No Selection","Select the student to be edited");
        }

        selected.setID(idNum.getText());
        selected.setFirstName(firstName.getText());
        selected.setLastName(lastName.getText());
        selected.setDepartment(department.getText());
        selected.setMajor(major.getText());
        selected.setEmail(email.getText());

        tableView.refresh();
        clearFields();
    }

    private void infoDisplay(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void loadStudentIntoFields(Student student) {
        idNum.setText(student.getId());
        firstName.setText(student.getFirstName());
        lastName.setText(student.getLastName());
        department.setText(student.getDepartment());
        major.setText(student.getMajor());
        email.setText(student.getEmail());
    }

    public static void main(String[] args) {
        launch();
    }
}
