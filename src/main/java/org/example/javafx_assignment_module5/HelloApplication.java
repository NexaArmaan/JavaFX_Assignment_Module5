package org.example.javafx_assignment_module5;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));

        BorderPane root = new BorderPane();

        MenuBar menuBar = createMenuBar();
        VBox leftSide = createLeftSide();
        VBox rightSide = createRightSide;
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
        deleteItem.setOnAction(e-> deleteSelectedFields());
        defaultMenu.setOnAction(e-> infoDisplay("Theme","Default theme is applied"));
        aboutMenu.setOnAction(e-> infoDisplay("About","JavaFX is good"));

        menuBar.getMenus().addAll(fileMenu,editMenu,themeMenu,helpMenu);
        return menuBar;
    }

}
