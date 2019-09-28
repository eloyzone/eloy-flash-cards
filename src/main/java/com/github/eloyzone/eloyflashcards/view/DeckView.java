package com.github.eloyzone.eloyflashcards.view;

import com.github.eloyzone.eloyflashcards.model.Card;
import com.github.eloyzone.eloyflashcards.model.Deck;
import com.github.eloyzone.eloyflashcards.util.Initializer;
import com.github.eloyzone.eloyflashcards.util.Transitioner;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Optional;

public class DeckView extends VBox
{
    private Iterator cardsIterator;
    private FaceCardView faceCardView;
    private BackCardView backCardVBox;
    private ImageButton startLearningButton;
    private StackPane parentStackPane;
    public BooleanProperty nextCardMustNotBeShown = new SimpleBooleanProperty(true);

    private boolean isTodayReview = false;

    private Deck deck;

    private int countOfTodaysCardsToReview = 0;
    private double countOfVisitedCard = 0;
    private int countOfCardsInARow = 0;

    private ScrollPane bottomScrollPane;
    private ScrollPane topScrollPane;

    private ProgressBar progressBar;
    private HBox hBoxProgressBar;
    private Label progressStatusLabel;
    private VBox topVBox;

    private final String RED_PROGRESS_BAR = "red-bar";
    private final String ORANGE_PROGRESS_BAR = "orange-bar";
    private final String GREEN_PROGRESS_BAR = "green-bar";
    private final String[] PROGRESS_BAR_STYLE_CLASSES = {RED_PROGRESS_BAR, ORANGE_PROGRESS_BAR, GREEN_PROGRESS_BAR};

    private DeckView()
    {
    }

    public DeckView(StackPane parentStackPane, Deck deck)
    {
        this.deck = deck;
        this.parentStackPane = parentStackPane;
        this.parentStackPane.getStylesheets().add(getClass().getResource("/styles/DeckView.css").toExternalForm());

        if (evaluateSoundPlayingIssue())
        {
            startLearningButton.setDisable(true);
        } else
        {
            ArrayList<Card> cards = new ArrayList<>(deck.getTodayCards());
            countOfTodaysCardsToReview = cards.size();
            Collections.shuffle(cards);
            cardsIterator = cards.iterator();
        }

        parentStackPane.getChildren().addAll(welcomingView());
        parentStackPane.getChildren().get(0).setVisible(false);

        FadeTransition fadeTransitionTop = Transitioner.getFade(600, parentStackPane);
        fadeTransitionTop.play();


    }

    private boolean evaluateSoundPlayingIssue()
    {
        // todo: handle both german and english
        boolean soundPlayingIssue = false;
//        if (Initializer.getFlashCard().getDirectoryOfEnglishSound() == null)
//        {
//            ArrayList<Card> cardsArrayList = this.deck.getCards();
//            for (Card card : cardsArrayList)
//            {
//                if (card.isHasVoiceOnFace())
//                {
//                    soundPlayingIssue = true;
//                    break;
//                }
//            }
//        }

        return soundPlayingIssue;
    }

    private Node welcomingView()
    {
        VBox vBox = new VBox();
        HBox hBoxTop = new HBox();
        HBox hBoxBottom = new HBox();
        VBox vBoxLeft = new VBox();
        VBox vBoxRight = new VBox();
        vBox.getChildren().addAll(hBoxTop, hBoxBottom);
        hBoxBottom.getChildren().addAll(deckGeneralInfo(), vBoxRight);

        vBox.setAlignment(Pos.TOP_CENTER);
        hBoxTop.setAlignment(Pos.CENTER);
        hBoxBottom.setAlignment(Pos.CENTER);
        vBoxLeft.setAlignment(Pos.CENTER_LEFT);
        vBoxRight.setAlignment(Pos.CENTER);
        vBox.setPadding(new Insets(15, 0, 0, 0));
        hBoxTop.setPadding(new Insets(0, 0, 50, 0));
        vBoxLeft.setPadding(new Insets(0, 50, 0, 0));
        vBoxRight.setPadding(new Insets(0, 0, 0, 50));

        Label deckNameLabel = new Label(deck.getName());
        hBoxTop.getChildren().addAll(deckNameLabel);
        hBoxTop.getStyleClass().add("general-welcome-info");

        ImageView imageView = new ImageView(new Image(getClass().getClassLoader().getResourceAsStream("images/icon_student.png")));
        imageView.setFitHeight(100);
        imageView.setFitWidth(100);
        imageView.setPreserveRatio(true);

        Button reviewAllCardsButton = new Button("Review All");
        Button reviewTodayCardButton = new Button("Review Today's");
        reviewAllCardsButton.setId("red-button");
        reviewTodayCardButton.setId("red-button");

        if (this.countOfTodaysCardsToReview == 0) reviewTodayCardButton.setDisable(true);

        reviewTodayCardButton.setOnAction(e ->
        {
            isTodayReview = true;
            countOfCardsInARow = this.countOfTodaysCardsToReview;
            if (cardsIterator.hasNext())
            {
                BorderPane borderPane = (BorderPane) initializeCardsView();
                parentStackPane.getChildren().add(borderPane);
                parentStackPane.getChildren().get(1).setVisible(false);

                borderPane.prefWidthProperty().bind(parentStackPane.widthProperty());
                borderPane.prefHeightProperty().bind(parentStackPane.heightProperty());
            }
        });

        reviewAllCardsButton.setOnAction(e ->
        {
            isTodayReview = false;
            ArrayList<Card> cards = new ArrayList<>(deck.getAllCards());
            countOfCardsInARow = cards.size();
            Collections.shuffle(cards);
            cardsIterator = cards.iterator();
            if (cardsIterator.hasNext())
            {
                BorderPane borderPane = (BorderPane) initializeCardsView();
                parentStackPane.getChildren().add(borderPane);
                parentStackPane.getChildren().get(1).setVisible(false);

                borderPane.prefWidthProperty().bind(parentStackPane.widthProperty());
                borderPane.prefHeightProperty().bind(parentStackPane.heightProperty());
            }
        });

        vBoxRight.getChildren().addAll(imageView, reviewTodayCardButton, reviewAllCardsButton);
        vBox.getStyleClass().add("welcoming-vbox-border");
        return vBox;
    }


    private Node deckGeneralInfo()
    {
        GridPane gridpane = new GridPane();
        gridpane.getStyleClass().add("general-welcome-info");

        gridpane.setPadding(new Insets(5));
        gridpane.setHgap(15);
        gridpane.setVgap(15);
        ColumnConstraints column1 = new ColumnConstraints(150);
        ColumnConstraints column2 = new ColumnConstraints(50, 150, 300);
        gridpane.getColumnConstraints().addAll(column1, column2);

        Label cardsLevelOneLabel = new Label("Level One");
        Label cardsLevelOneCountLabel = new Label("");
        Label cardsLevelTwoLabel = new Label("Level Two");
        Label cardsLevelTwoCountLabel = new Label("");
        Label cardsLevelThreeLabel = new Label("Level Three");
        Label cardsLevelThreeCountLabel = new Label("");
        Label cardsLevelFourLabel = new Label("Level Four");
        Label cardsLevelFourCountLabel = new Label("");
        Label cardsLevelFiveLabel = new Label("Level Five");
        Label cardsLevelFiveCountLabel = new Label("");
        Label cardsLevelSixLabel = new Label("Level Six");
        Label cardsLevelSixCountLabel = new Label("");
        Label cardsLevelSevenLabel = new Label("Level Seven");
        Label cardsLevelSevenCountLabel = new Label("");
        Label cardsToReviewLabel = new Label("Cards To Review");
        Label cardsToReviewCountLabel = new Label("");
        Label totalLabel = new Label("Total");
        Label totalCountLabel = new Label("");

        gridpane.add(cardsLevelOneLabel, 0, 0);
        gridpane.add(cardsLevelOneCountLabel, 1, 0);

        gridpane.add(cardsLevelTwoLabel, 0, 1);
        gridpane.add(cardsLevelTwoCountLabel, 1, 1);

        gridpane.add(cardsLevelThreeLabel, 0, 2);
        gridpane.add(cardsLevelThreeCountLabel, 1, 2);

        gridpane.add(cardsLevelFourLabel, 0, 3);
        gridpane.add(cardsLevelFourCountLabel, 1, 3);

        gridpane.add(cardsLevelFiveLabel, 0, 4);
        gridpane.add(cardsLevelFiveCountLabel, 1, 4);

        gridpane.add(cardsLevelSixLabel, 0, 5);
        gridpane.add(cardsLevelSixCountLabel, 1, 5);

        gridpane.add(cardsLevelSevenLabel, 0, 6);
        gridpane.add(cardsLevelSevenCountLabel, 1, 6);

        gridpane.add(cardsToReviewLabel, 0, 7);
        gridpane.add(cardsToReviewCountLabel, 1, 7);

        gridpane.add(totalLabel, 0, 8);
        gridpane.add(totalCountLabel, 1, 8);

        cardsLevelOneCountLabel.setText(String.valueOf(deck.getLevelOneCardsSize()));
        cardsLevelTwoCountLabel.setText(String.valueOf(deck.getLevelTwoCardsSize()));
        cardsLevelThreeCountLabel.setText(String.valueOf(deck.getLevelThreeCardsSize()));
        cardsLevelFourCountLabel.setText(String.valueOf(deck.getLevelFourCardsSize()));
        cardsLevelFiveCountLabel.setText(String.valueOf(deck.getLevelFiveCardsSize()));
        cardsLevelSixCountLabel.setText(String.valueOf(deck.getLevelSixCardsSize()));
        cardsLevelSevenCountLabel.setText(String.valueOf(deck.getLevelSevenCardsSize()));
        cardsToReviewCountLabel.setText(String.valueOf(this.countOfTodaysCardsToReview));
        totalCountLabel.setText(String.valueOf(deck.getCardsTotalSize()));

        if (this.countOfTodaysCardsToReview == 0)
        {
            cardsToReviewCountLabel.getStyleClass().addAll("green-text");
            cardsToReviewLabel.getStyleClass().addAll("green-text");
        } else
        {
            cardsToReviewCountLabel.getStyleClass().addAll("red-text");
            cardsToReviewLabel.getStyleClass().addAll("red-text");
        }

        return gridpane;
    }


    private Node initializeCardsView()
    {
        BorderPane borderPane = new BorderPane();

        topScrollPane = new ScrollPane();
        topScrollPane.setFitToWidth(true);
        topScrollPane.setFitToHeight(true);
        topScrollPane.prefWidthProperty().bind(borderPane.widthProperty().divide(2));
        topScrollPane.prefHeightProperty().bind(borderPane.heightProperty().divide(2).subtract(100));
        topScrollPane.getStyleClass().add("node-border2");

        bottomScrollPane = new ScrollPane();
        bottomScrollPane.setFitToWidth(true);
        bottomScrollPane.setFitToHeight(true);
        bottomScrollPane.prefWidthProperty().bind(borderPane.widthProperty().divide(2));
        bottomScrollPane.prefHeightProperty().bind(borderPane.heightProperty().divide(2).add(100));
        bottomScrollPane.getStyleClass().add("edge-to-edge");
        bottomScrollPane.getStyleClass().add("node-border2");


        progressBar = new ProgressBar();
        progressBar.setMaxWidth(Double.MAX_VALUE);

        hBoxProgressBar = new HBox();
        hBoxProgressBar.setHgrow(progressBar, Priority.ALWAYS);
        hBoxProgressBar.setPadding(new Insets(0, 15, 10, 15));
        hBoxProgressBar.setSpacing(10);
        progressStatusLabel = new Label();
        progressStatusLabel.setTextFill(Color.WHITE);
        hBoxProgressBar.getChildren().addAll(progressStatusLabel, progressBar);
        hBoxProgressBar.setStyle("-fx-background-color: black;");

        topVBox = new VBox();
        topVBox.getChildren().addAll(hBoxProgressBar);

        inflateCards((Card) cardsIterator.next());

        ImageButton nextCardImageButton = new ImageButton(new Image(getClass().getClassLoader().getResourceAsStream("images/icon_next.png")), 30, 30);
        HBox nextCardImageButtonHBox = new HBox(nextCardImageButton);
        nextCardImageButtonHBox.setAlignment(Pos.CENTER);
        nextCardImageButtonHBox.setPadding(new Insets(0, 0, 10, 0));
        nextCardImageButton.setDisable(true);
        nextCardImageButton.disableProperty().bindBidirectional(nextCardMustNotBeShown);

        nextCardImageButton.setOnAction(event ->
        {
            if (cardsIterator.hasNext())
            {
                nextCardMustNotBeShown.set(true);
                inflateCards((Card) cardsIterator.next());
            } else
            {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Finish");
                alert.setHeaderText("No more cards left");
                alert.setContentText("Do you want to open decks?");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK)
                {
                    parentStackPane.getChildren().remove(parentStackPane.getChildren().size() - 1);
                    parentStackPane.getChildren().remove(parentStackPane.getChildren().size() - 1);
                    parentStackPane.getChildren().get(0).setVisible(true);
                }
            }

        });

        topScrollPane.setContent(topVBox);
        borderPane.setTop(topScrollPane);
        borderPane.setCenter(bottomScrollPane);
        borderPane.setBottom(nextCardImageButtonHBox);
        return borderPane;
    }

    private void inflateCards(Card nextCard)
    {
        backCardVBox = new BackCardView(nextCard, nextCardMustNotBeShown, this.isTodayReview);
        faceCardView = new FaceCardView(nextCard);
        VBox.setVgrow(faceCardView, Priority.ALWAYS);

        FadeTransition fadeTransitionTop = Transitioner.getFade(600, faceCardView);
        FadeTransition fadeTransitionBottom = Transitioner.getFade(600, bottomScrollPane);

        bottomScrollPane.setContent(backCardVBox);
        bottomScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        countOfVisitedCard++;
        double progressValue = countOfVisitedCard / countOfCardsInARow;
        progressBar.setProgress(progressValue);
        progressStatusLabel.setText((int) countOfVisitedCard + "/" + countOfCardsInARow);

        if (progressValue < 0.2)
        {
            progressBar.getStyleClass().removeAll(PROGRESS_BAR_STYLE_CLASSES);
            progressBar.getStyleClass().add(RED_PROGRESS_BAR);
        } else if (progressValue <= 0.99)
        {
            progressBar.getStyleClass().removeAll(PROGRESS_BAR_STYLE_CLASSES);
            progressBar.getStyleClass().add(ORANGE_PROGRESS_BAR);
        } else
        {
            progressBar.getStyleClass().removeAll(PROGRESS_BAR_STYLE_CLASSES);
            progressBar.getStyleClass().add(GREEN_PROGRESS_BAR);
        }


        if (topVBox.getChildren().size() == 2)
        {
            topVBox.getChildren().remove(1);
            topVBox.getChildren().addAll(faceCardView);
        } else
        {
            topVBox.getChildren().addAll(faceCardView);
        }

        fadeTransitionTop.play();
        fadeTransitionBottom.play();
    }
}
