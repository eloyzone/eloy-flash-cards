package com.github.eloyzone.eloyflashcards.view;

import com.github.eloyzone.eloyflashcards.model.Deck;
import com.github.eloyzone.eloyflashcards.util.Initializer;
import com.github.eloyzone.eloyflashcards.util.Transitioner;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
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
    private Scene scene;
    private BorderPane mainBorderPane;
    private HBox topControllerHBox;
    private Button showDecksButton;
    private Button addDeckButton;
    private Button addCardButton;
    private StackPane mainStackPane;
    private VBox centerVBox;

    private DecksTableView decksTableView;
    private ObservableList<Deck> deckObservableList;

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        deckObservableList = FXCollections.observableArrayList(Initializer.getFlashCard().getDecks());

        mainBorderPane = new BorderPane();
        topControllerHBox = createTopController();
        mainStackPane = new StackPane();
        centerVBox = new VBox();
        centerVBox.getChildren().addAll(topControllerHBox, mainStackPane);

        decksTableView = new DecksTableView(deckObservableList, mainStackPane);
        decksTableView.setId("decks-table-view");

        mainStackPane.getChildren().addAll(decksTableView);

        mainBorderPane.setTop(new MenuBar(this));
        mainBorderPane.setCenter(centerVBox);

        scene = new Scene(mainBorderPane, 700, 700);
        scene.getStylesheets().add(getClass().getResource("/styles/MainView.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
        primaryStage.getIcons().addAll(new Image(getClass().getClassLoader().getResourceAsStream("images/icon_eloy_flash_card_mini.png")));
        primaryStage.setTitle("Eloy Flash Cards");
        primaryStage.show();

        mainStackPane.prefWidthProperty().bind(scene.widthProperty());
        mainStackPane.prefHeightProperty().bind(scene.heightProperty());

        if (Initializer.getFlashCard().getResourceDirectory() == null)
        {
            new SelectResourceView();
        }

    }

    private HBox createTopController()
    {
        topControllerHBox = new HBox();
        topControllerHBox.setSpacing(5);
        topControllerHBox.setAlignment(Pos.CENTER);
        topControllerHBox.getStyleClass().add("top-controller-hbox");
        topControllerHBox.setPadding(new Insets(5, 0, 5, 0));

        showDecksButton = new Button("Decks");
        addDeckButton = new Button("Add Deck");
        addCardButton = new Button("Add Card");
        showDecksButton.setId("dark-button");
        addDeckButton.setId("dark-button");
        addCardButton.setId("dark-button");

        showDecksButton.setOnAction(event ->
        {
            int sizeOfMainStackPane = mainStackPane.getChildren().size();
            for (; sizeOfMainStackPane > 1; sizeOfMainStackPane--)
                mainStackPane.getChildren().remove(sizeOfMainStackPane - 1);
            mainStackPane.getChildren().get(0).setVisible(true);

            FadeTransition fadeTransitionTop = Transitioner.getFade(600, mainStackPane);
            fadeTransitionTop.play();

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

        topControllerHBox.getChildren().addAll(showDecksButton, addDeckButton, addCardButton);

        return topControllerHBox;
    }
}
