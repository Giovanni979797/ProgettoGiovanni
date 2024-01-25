package com.electra.canbusdemo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApplication extends Application {
    public static Stage stage = null;
    @Override
    public void start(Stage stage) throws IOException {
        // Set an event handler for the close request to exit the application
        stage.setOnCloseRequest(event -> {
           System.exit(0);
        });


        // Store the main stage reference in the static variable for global access
        MainApplication.stage = stage;

        // Create a FXMLLoader to load the FXML file that defines the user interface
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("MainView.fxml"));

        // Load the FXML file to create the scene
        Scene scene = new Scene(fxmlLoader.load());

        // Set properties for the main stage
        stage.setTitle("CANbus GUI");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setMinHeight(768);
        stage.setMinWidth(1366);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
