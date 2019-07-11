package com.github.eloyzone.eloyflashcards.view;

import com.github.eloyzone.eloyflashcards.model.Card;
import com.github.eloyzone.eloyflashcards.model.Deck;
import com.github.eloyzone.eloyflashcards.util.Initializer;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
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

    private Deck deck;

    private DeckView()
    {
    }


    public DeckView(StackPane parentStackPane, Deck deck)
    {
        this.deck = deck;
        this.parentStackPane = parentStackPane;
        this.parentStackPane.getStylesheets().add(getClass().getResource("/styles/DeckView.css").toExternalForm());

        parentStackPane.getChildren().addAll(welcomingView());
        parentStackPane.getChildren().get(0).setVisible(false);

        if(evaluateSoundPlayingIssue())
        {
            startLearningButton.setDisable(true);
        }
        else
        {
            ArrayList<Card> cards = new ArrayList<>(deck.getCards());
            Collections.shuffle(cards);
            cardsIterator = cards.iterator();
        }
    }

    private boolean evaluateSoundPlayingIssue()
    {
        boolean soundPlayingIssue = false;
        if (Initializer.getFlashCard().getDirectoryOfEnglishSound() == null)
        {
            ArrayList<Card> cardsArrayList = this.deck.getCards();
            for (Card card : cardsArrayList)
            {
                if (card.isHasVoiceOnFace())
                {
                    soundPlayingIssue = true;
                    break;
                }
            }
        }

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
        vBoxRight.setAlignment(Pos.CENTER_RIGHT);
        vBox.setPadding(new Insets(15, 0, 0, 0));
        hBoxTop.setPadding(new Insets(0, 0, 50, 0));
        vBoxLeft.setPadding(new Insets(0, 50, 0, 0));
        vBoxRight.setPadding(new Insets(0, 0, 0, 50));


        Label deckNameLabel = new Label("Pronunciation");
        hBoxTop.getChildren().addAll(deckNameLabel);

        startLearningButton = new ImageButton(new Image(getClass().getClassLoader().getResourceAsStream("images/icon_student.png")), 100, 100, ContentDisplay.TOP, "Study Now!", null);
        startLearningButton.setOnAction(e ->
        {
            if (cardsIterator.hasNext())
            {
                BorderPane borderPane = (BorderPane) initializeCardsView();
                parentStackPane.getChildren().add(borderPane);
                parentStackPane.getChildren().get(1).setVisible(false);

                borderPane.prefWidthProperty().bind(parentStackPane.widthProperty());
                borderPane.prefHeightProperty().bind(parentStackPane.heightProperty());
            }
        });
        vBoxRight.getChildren().addAll(startLearningButton);

        vBox.getStyleClass().add("node-border");
        return vBox;
    }


    private Node deckGeneralInfo()
    {
        GridPane gridpane = new GridPane();
        gridpane.setPadding(new Insets(5));
        gridpane.setHgap(15);
        gridpane.setVgap(15);
        ColumnConstraints column1 = new ColumnConstraints(100);
        ColumnConstraints column2 = new ColumnConstraints(50, 150, 300);
        gridpane.getColumnConstraints().addAll(column1, column2);

        Label newLabel = new Label("New");
        Label newValueLabel = new Label("");
        Label learnedLabel = new Label("Learned");
        Label learnedValueLabel = new Label("");
        Label notLearnedLabel = new Label("Not Learned");
        Label notLearnedValueLabel = new Label("");
        Label totalLabel = new Label("Total");
        Label totalValueLabel = new Label("");

        gridpane.add(newLabel, 0, 0);
        gridpane.add(newValueLabel, 1, 0);

        gridpane.add(learnedLabel, 0, 1);
        gridpane.add(learnedValueLabel, 1, 1);

        gridpane.add(notLearnedLabel, 0, 2);
        gridpane.add(notLearnedValueLabel, 1, 2);

        gridpane.add(totalLabel, 0, 3);
        gridpane.add(totalValueLabel, 1, 3);

        newValueLabel.setText(String.valueOf(deck.getNewCardCount()));
        learnedValueLabel.setText(String.valueOf(deck.getLearnedCardCount()));
        notLearnedValueLabel.setText(String.valueOf(deck.getNotLearnedCardCount()));
        totalValueLabel.setText(String.valueOf(deck.getTotalCardNumber()));

        return gridpane;
    }


    private Node initializeCardsView()
    {
        BorderPane borderPane = new BorderPane();

        ScrollPane topScrollPane = new ScrollPane();
        topScrollPane.setFitToWidth(true);
        topScrollPane.setFitToHeight(true);
        topScrollPane.prefWidthProperty().bind(borderPane.widthProperty().divide(2));
        topScrollPane.prefHeightProperty().bind(borderPane.heightProperty().divide(2));
        topScrollPane.getStyleClass().add("node-border");

        ScrollPane bottomScrollPane = new ScrollPane();
        bottomScrollPane.setFitToWidth(true);
        bottomScrollPane.setFitToHeight(true);
        bottomScrollPane.prefWidthProperty().bind(borderPane.widthProperty().divide(2));
        bottomScrollPane.prefHeightProperty().bind(borderPane.heightProperty().divide(2));
        bottomScrollPane.getStyleClass().add("node-border");

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

                final FadeTransition transition = new FadeTransition(Duration.millis(600), faceCardView);
                transition.setFromValue(0);
                transition.setToValue(1);
                transition.setInterpolator(Interpolator.EASE_IN);
                topScrollPane.setContent(faceCardView);
                transition.play();

                final FadeTransition transition2 = new FadeTransition(Duration.millis(600), backCardVBox);
                transition2.setFromValue(0);
                transition2.setToValue(1);
                transition2.setInterpolator(Interpolator.EASE_IN);
                bottomScrollPane.setContent(backCardVBox);
                transition2.play();
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


        topScrollPane.setContent(faceCardView);
        bottomScrollPane.setContent(backCardVBox);

        borderPane.setTop(topScrollPane);
        borderPane.setCenter(bottomScrollPane);
        borderPane.setBottom(nextCardImageButtonHBox);
        return borderPane;
    }

    private void inflateCards(Card nextCard)
    {
        faceCardView = new FaceCardView(nextCard);
        backCardVBox = new BackCardView(nextCard, nextCardMustNotBeShown);
    }
}
