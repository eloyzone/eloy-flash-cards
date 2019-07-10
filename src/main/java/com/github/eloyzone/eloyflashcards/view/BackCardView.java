package com.github.eloyzone.eloyflashcards.view;

import com.github.eloyzone.eloyflashcards.model.Card;
import javafx.beans.property.BooleanProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class BackCardView extends VBox
{
    private ArrayList<String> words;

    private VBox textEditorsVBox;
    private TextField wordsLabel;
    private TextArea descriptionLabel;
    private BooleanProperty nextCardMustNotBeShown;
    private Card card;


    public BackCardView(Card card, BooleanProperty nextCardMustNotBeShown)
    {
        this.card = card;
        this.nextCardMustNotBeShown = nextCardMustNotBeShown;
        this.words = card.getBackData();

        setAlignment(Pos.TOP_CENTER);
        setSpacing(10);
        setPadding(new Insets(10, 0, 10, 0));

        createWordsLabel(words);
        if (card.isHasTextFieldsOnBack())
        {
            createTextEditors();
            getChildren().addAll(textEditorsVBox, wordsLabel);
        } else
        {
            VBox vBox = new VBox();
            ImageButton showWordsButton = new ImageButton(new Image(getClass().getClassLoader().getResourceAsStream("images/icon_show-property.png")), 50, 50, ContentDisplay.TOP, "Show Back", "Show details of back card");
            HBox hBox = new HBox();
            hBox.setVisible(false);
            ImageButton knowButton = new ImageButton(new Image(getClass().getClassLoader().getResourceAsStream("images/icon_tick.png")), 50, 50, null, null, "Know");
            ImageButton unknownButton = new ImageButton(new Image(getClass().getClassLoader().getResourceAsStream("images/icon_forbidden.png")), 50, 50, null, null, "Don't Know");
            hBox.getChildren().addAll(knowButton, unknownButton);
            hBox.setAlignment(Pos.CENTER);
            hBox.setSpacing(15);
            vBox.getChildren().addAll(showWordsButton, hBox);
            vBox.setAlignment(Pos.CENTER);
            vBox.setSpacing(15);

            showWordsButton.setOnAction(event ->
            {
                hBox.setVisible(true);
                showBackCardDetails();
            });

            knowButton.setOnAction(e -> knowCard());
            unknownButton.setOnAction(e -> doNotKnowCard());

            getChildren().addAll(vBox, wordsLabel);
        }
    }

    private void createWordsLabel(ArrayList<String> words)
    {
        wordsLabel = new TextField();
        wordsLabel.setEditable(false);
        wordsLabel.setText(words.toString());
        wordsLabel.setAlignment(Pos.CENTER);
        wordsLabel.setStyle("-fx-font-size: 2em; -fx-background-color: transparent ;");
        wordsLabel.setVisible(false);
    }

    private void createDescriptionScrollPane()
    {
        descriptionLabel = new TextArea();
        descriptionLabel.setMinHeight(150);
        descriptionLabel.setPrefRowCount(10);
        descriptionLabel.setPrefColumnCount(100);
        descriptionLabel.setWrapText(true);
        descriptionLabel.setPrefWidth(150);
        descriptionLabel.setStyle("-fx-background-color:transparent;");
        descriptionLabel.setEditable(false);
        descriptionLabel.setText(card.getDescriptionBack());
        this.getChildren().add(descriptionLabel);
    }


    private void createTextEditors()
    {
        int numberOfTextEditor = words.size();
        textEditorsVBox = new VBox();
        textEditorsVBox.setAlignment(Pos.TOP_CENTER);
        textEditorsVBox.setSpacing(5);

        if (numberOfTextEditor > 0)
        {
            while (numberOfTextEditor > 0)
            {
                HBox hBox = new HBox();
                hBox.setAlignment(Pos.CENTER);
                hBox.setSpacing(10);
                TextField textField = new TextField();
                textField.setPromptText("Fill in the gap");
                hBox.getChildren().add(textField);
                textEditorsVBox.getChildren().addAll(hBox);
                numberOfTextEditor--;
            }

            Button evaluateButton = new Button("Evaluate >>");
            Button doNotKnowButton = new Button("Don't know");
            evaluateButton.setId("blue-button");
            doNotKnowButton.setId("red-button");
            Label labelCheckingStatus = new Label();
            labelCheckingStatus.setVisible(false);
            textEditorsVBox.getChildren().addAll(evaluateButton, doNotKnowButton, labelCheckingStatus);

            evaluateButton.setOnAction(e ->
            {
                ArrayList<String> enteredEditTextArrayList = new ArrayList<>();
                for (int i = 0; i < words.size(); i++)
                {
                    HBox hBox = (HBox) textEditorsVBox.getChildren().get(i);
                    TextField textField = (TextField) hBox.getChildren().get(0);
                    enteredEditTextArrayList.add(textField.getText().trim().toLowerCase());
                }
                if (enteredEditTextArrayList.containsAll(words))
                {
                    labelCheckingStatus.setVisible(true);
                    labelCheckingStatus.setText("Correct");
                    labelCheckingStatus.setTextFill(Color.GREEN);
                    doNotKnowButton.setDisable(true);
                    evaluateButton.setDisable(true);
                    nextCardMustNotBeShown.set(false);
                    showBackCardDetails();
                    knowCard();
                } else
                {
                    labelCheckingStatus.setVisible(true);
                    labelCheckingStatus.setText("Wrong");
                    labelCheckingStatus.setTextFill(Color.RED);
                }
            });

            doNotKnowButton.setOnAction(e ->
            {
                doNotKnowCard();
                evaluateButton.setDisable(true);
                showBackCardDetails();
            });
        }
    }

    private void showBackCardDetails()
    {
        wordsLabel.setVisible(true);
        if (card.getDescriptionBack().length() > 0)
            createDescriptionScrollPane();
    }

    private void knowCard()
    {
        nextCardMustNotBeShown.set(false);
        this.card.setKnown(true);
    }

    private void doNotKnowCard()
    {
        nextCardMustNotBeShown.set(false);
        this.card.setKnown(false);
    }
}
