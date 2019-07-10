package com.github.eloyzone.eloyflashcards.view;

import com.github.eloyzone.eloyflashcards.model.Deck;
import com.github.eloyzone.eloyflashcards.util.Initializer;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainView extends Application
{
    private DecksTableView decksTableView;
    private ObservableList<Deck> deckObservableList;
    private StackPane mainStackPane;

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        deckObservableList = FXCollections.observableArrayList(Initializer.getFlashCard().getDecks());

        BorderPane mainBorderPane = new BorderPane();

        HBox topControllerHBox = createTopController();
        mainStackPane = new StackPane();
        VBox centerVBox = new VBox();
        centerVBox.getChildren().addAll(topControllerHBox, mainStackPane);

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);
        scrollPane.getStyleClass().add("node-border");

        mainStackPane.getChildren().addAll(scrollPane);

        decksTableView = new DecksTableView(deckObservableList, mainStackPane);
        scrollPane.setContent(decksTableView);

        mainBorderPane.setTop(new MenuBar(this));
        mainBorderPane.setCenter(centerVBox);

        Scene scene = new Scene(mainBorderPane, 700, 700);
        scene.getStylesheets().add(getClass().getResource("/styles/MainView.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
        primaryStage.getIcons().addAll(new Image(getClass().getClassLoader().getResourceAsStream("images/icon_eloy_flash_card_mini.png")));
        primaryStage.setTitle("Eloy Flash Cards");
        primaryStage.show();

        mainStackPane.prefWidthProperty().bind(scene.widthProperty());
        mainStackPane.prefHeightProperty().bind(scene.heightProperty());

        if (Initializer.getFlashCard().getDirectoryOfEnglishSound() == null)
        {
            new SelectResourceView();
        }

    }

    private HBox createTopController()
    {
        HBox hBox = new HBox();
        hBox.setSpacing(5);
        hBox.setAlignment(Pos.CENTER);
        hBox.getStyleClass().add("node-border");
        hBox.setPadding(new Insets(5, 0, 5, 0));

        Button showDecksButton = new Button("Decks");
        Button addDeckButton = new Button("Add Deck");
        Button addCardButton = new Button("Add Card");
        showDecksButton.setId("dark-button");
        addDeckButton.setId("dark-button");
        addCardButton.setId("dark-button");

        showDecksButton.setOnAction(event ->
        {
            int sizeOfMainStackPane = mainStackPane.getChildren().size();
            for (; sizeOfMainStackPane > 1; sizeOfMainStackPane--)
                mainStackPane.getChildren().remove(sizeOfMainStackPane - 1);
            mainStackPane.getChildren().get(0).setVisible(true);
        });

        addDeckButton.setOnAction(e ->
        {
            new NewDeckView(deckObservableList);
            decksTableView.refresh();
        });

        addCardButton.setOnAction(e ->
        {
            new NewCardView(Initializer.getFlashCard().getDecks());
            decksTableView.refresh();
        });

        hBox.getChildren().addAll(showDecksButton, addDeckButton, addCardButton);

        return hBox;
    }
}
