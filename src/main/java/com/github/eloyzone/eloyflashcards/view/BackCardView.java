package com.github.eloyzone.eloyflashcards.view;

import com.github.eloyzone.eloyflashcards.model.Card;
import javafx.beans.property.BooleanProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Iterator;

public class BackCardView extends VBox
{
    private ArrayList<String> words;

    private VBox textEditorsVBox;
    private TextArea wordsLabel;
    private TextArea descriptionLabel;
    private BooleanProperty nextCardMustNotBeShown;
    private Card card;
    private boolean isTodayReview = false;

    HBox backCardDetailsHBox;


    public BackCardView(Card card, BooleanProperty nextCardMustNotBeShown, boolean isTodayReview)
    {
        getStylesheets().add(getClass().getResource("/styles/BackCardView.css").toExternalForm());
        this.card = card;
        this.nextCardMustNotBeShown = nextCardMustNotBeShown;
        this.words = card.getBackData();
        this.isTodayReview = isTodayReview;

        setAlignment(Pos.TOP_CENTER);

        HBox topHBox = new HBox();
        TextField textField = new TextField();
        textField.setText("Level " + card.getLevel());
        textField.setEditable(false);
        topHBox.getChildren().addAll(textField);
        HBox.setHgrow(textField, Priority.ALWAYS);
        VBox.setMargin(topHBox, new Insets(0, 0, 15, 0));
        topHBox.setStyle("-fx-background-color: black;" + "-fx-background-radius: 0 0 0 0;");
        textField.setStyle("-fx-background-color: orange; " + "-fx-background-radius: 8 8 0 0;" + "-fx-text-fill: white;");

        backCardDetailsHBox = new HBox();
        HBox.setHgrow(backCardDetailsHBox, Priority.ALWAYS);
        backCardDetailsHBox.setVisible(false);

        if (card.isHasTextFieldsOnBack())
        {
            createTextEditors();
            getChildren().addAll(topHBox, textEditorsVBox, backCardDetailsHBox);
        } else
        {
            VBox vBox = new VBox();
            ImageButton showBackImageButton = new ImageButton(new Image(getClass().getClassLoader().getResourceAsStream("images/icon_show-property.png")), 50, 50, ContentDisplay.TOP, "Show Back", "Show details of back card");
            HBox ImageButtonsHBox = new HBox();
            ImageButtonsHBox.setVisible(false);
            ImageButton knowImageButton = new ImageButton(new Image(getClass().getClassLoader().getResourceAsStream("images/icon_tick.png")), 50, 50, null, null, "Know");
            ImageButton unknownImageButton = new ImageButton(new Image(getClass().getClassLoader().getResourceAsStream("images/icon_forbidden.png")), 50, 50, null, null, "Don't Know");
            ImageButtonsHBox.getChildren().addAll(knowImageButton, unknownImageButton);
            ImageButtonsHBox.setAlignment(Pos.CENTER);
            ImageButtonsHBox.setSpacing(15);
            vBox.getChildren().addAll(showBackImageButton, ImageButtonsHBox);
            vBox.setAlignment(Pos.CENTER);
            vBox.setSpacing(15);

            showBackImageButton.setOnAction(event ->
            {
                knowImageButton.setOnAction(e ->
                {
                    knowImageButton.setDisable(true);
                    unknownImageButton.setDisable(true);
                    backCardDetailsHBox.requestFocus();
                    knowCard();
                });
                unknownImageButton.setOnAction(e ->
                {
                    knowImageButton.setDisable(true);
                    unknownImageButton.setDisable(true);
                    backCardDetailsHBox.requestFocus();
                    doNotKnowCard();
                });

                showBackImageButton.setDisable(true);
                ImageButtonsHBox.setVisible(true);
                showBackCardDetails();
            });

            getChildren().addAll(topHBox, vBox, backCardDetailsHBox);
        }
    }

    private void createWordsLabel(ArrayList<String> words)
    {
        VBox vBox = new VBox();
        HBox hBox = new HBox();

        TextField levelTextField = new TextField();
        levelTextField.setText("Back Card");
        levelTextField.setEditable(false);

        hBox.getChildren().addAll(levelTextField);
        HBox.setHgrow(levelTextField, Priority.ALWAYS);
        levelTextField.setStyle("-fx-background-color: #15a300; " + "-fx-text-fill: white;" + "-fx-background-radius: 8 8 0 0;");

        wordsLabel = new TextArea();
        wordsLabel.setId("wordsLabel");
        wordsLabel.setEditable(false);

        vBox.getChildren().addAll(hBox, wordsLabel);
        HBox.setHgrow(vBox, Priority.SOMETIMES);

        backCardDetailsHBox.getChildren().add(vBox);

        backCardDetailsHBox.setVisible(false);

        for (String word : words)
        {
            wordsLabel.setText(wordsLabel.getText() + word + "\n");
        }
    }

    private void createDescriptionScrollPane()
    {
        VBox vBox = new VBox();
        HBox hBox = new HBox();

        TextField levelTextField = new TextField();
        levelTextField.setText("Description");
        levelTextField.setEditable(false);
        hBox.getChildren().addAll(levelTextField);
        HBox.setHgrow(levelTextField, Priority.ALWAYS);
        levelTextField.setStyle("-fx-background-color: #a30000; " + "-fx-text-fill: white;" + "-fx-background-radius: 8 8 0 0;");

        descriptionLabel = new TextArea();
        descriptionLabel.setWrapText(true);
        descriptionLabel.setEditable(false);
        descriptionLabel.setText(card.getDescriptionBack());

        vBox.getChildren().addAll(hBox, descriptionLabel);
        HBox.setHgrow(vBox, Priority.ALWAYS);

        backCardDetailsHBox.getChildren().add(vBox);
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
            VBox.setMargin(evaluateButton, new Insets(10, 0, 0, 0));
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
                doNotKnowButton.setDisable(true);
                Iterator<Node> textEditorsVBoxIterator = textEditorsVBox.getChildren().iterator();
                while (textEditorsVBoxIterator.hasNext())
                {
                    Node textEditorsVBoxIteratorNextNode = textEditorsVBoxIterator.next();
                    if (textEditorsVBoxIteratorNextNode instanceof HBox)
                    {
                        HBox hBox = (HBox) textEditorsVBoxIteratorNextNode;
                        if (hBox.getChildren().get(0) instanceof TextField)
                        {
                            TextField textField = (TextField) hBox.getChildren().get(0);
                            textField.setDisable(true);
                        }
                    }

                }
                showBackCardDetails();
            });
        }
    }

    private void showBackCardDetails()
    {
        createWordsLabel(words);
        backCardDetailsHBox.setVisible(true);
        if (card.getDescriptionBack().length() > 0) createDescriptionScrollPane();
    }

    private void knowCard()
    {
        nextCardMustNotBeShown.set(false);
        if (isTodayReview)
        {
            this.card.setKnown(true);
        }
    }

    private void doNotKnowCard()
    {
        nextCardMustNotBeShown.set(false);
        if (isTodayReview)
        {
            this.card.setKnown(false);
        }
    }
}
