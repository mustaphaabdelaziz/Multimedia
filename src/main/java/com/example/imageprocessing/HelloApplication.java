package com.example.imageprocessing;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("ImageProcessing.fxml"));
            //Parent root = FXMLLoader.load(getClass().getResource("FXMLDocumentController.fxml"));
            Scene scene = new Scene(root, 950, 650);
            scene.getStylesheets().add(getClass().getResource("ImageProcessing.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.setTitle("Image Processing");
            primaryStage.getIcons().add(new Image(this.getClass().getResource("Gallery_65px.png").toString()));
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
//    public void start(Stage stage) throws IOException {
//        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
//        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
//        stage.setTitle("Hello!");
//        stage.setScene(scene);
//        stage.show();
//    }


    public static void main(String[] args) {
        launch();
    }
}