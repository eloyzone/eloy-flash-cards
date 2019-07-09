package com.github.eloyzone.eloyflashcards.view;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainView extends Application
{
    @Override
    public void start(Stage primaryStage) throws Exception
    {
        BorderPane mainBorderPane = new BorderPane();

        HBox topControllerHBox = createTopController();
        VBox centerVBox = new VBox();
        centerVBox.getChildren().addAll(topControllerHBox);

        mainBorderPane.setTop(new MenuBar(this));
        mainBorderPane.setCenter(centerVBox);

        Scene scene = new Scene(mainBorderPane, 700, 700);
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
        primaryStage.getIcons().addAll(new Image(getClass().getClassLoader().getResourceAsStream("images/icon_eloy_flash_card_mini.png")));
        primaryStage.setTitle("Eloy Flash Cards");
        primaryStage.show();
    }

    private HBox createTopController()
    {
        HBox hBox = new HBox();

        hBox.setAlignment(Pos.CENTER);
        hBox.getStyleClass().add("node-border");
        hBox.setPadding(new Insets(5, 0, 5, 0));

        Button showDecksButton = new Button("Decks");
        Button addDeckButton = new Button("Add Deck");
        Button addCardButton = new Button("Add Card");

        hBox.getChildren().addAll(showDecksButton, addDeckButton, addCardButton);

        return hBox;
    }
}
