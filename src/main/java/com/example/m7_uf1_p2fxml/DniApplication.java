package com.example.m7_uf1_p2fxml;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class DniApplication extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("main-view.fxml"));
        Parent root = loader.load();

        stage.setTitle("DNI generator");
        stage.setScene(new Scene(root, 400, 230));
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}