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

        mainBorderPane.setTop(new MenuBar(this));

        Scene scene = new Scene(mainBorderPane, 700, 700);
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
        primaryStage.getIcons().addAll(new Image(getClass().getClassLoader().getResourceAsStream("images/icon_eloy_flash_card_mini.png")));
        primaryStage.setTitle("Eloy Flash Cards");
        primaryStage.show();
    }
}
