package com.github.eloyzone.eloyflashcards.view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainView extends Application
{
    @Override
    public void start(Stage primaryStage) throws Exception
    {
        BorderPane mainBorderPane = new BorderPane();

        mainBorderPane.setTop(new MenuBar());

        Scene scene = new Scene(mainBorderPane, 700, 700);
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
        primaryStage.setTitle("Eloy Flash Cards");
        primaryStage.show();
    }
}
