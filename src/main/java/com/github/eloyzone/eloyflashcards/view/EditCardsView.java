package com.github.eloyzone.eloyflashcards.view;

import com.github.eloyzone.eloyflashcards.model.Card;
import com.github.eloyzone.eloyflashcards.model.Deck;
import com.github.eloyzone.eloyflashcards.util.Initializer;
import com.github.eloyzone.eloyflashcards.util.SavedObjectWriterReader;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

public class EditCardsView
{
    private VBox primaryVBox;
    private StackPane parentStackPane;
    private ObservableList<Deck> data;
    private int indexOfDeck;
    private CardsTableView cardsTableView;

    IntegerProperty simpleIntegerProperty = new SimpleIntegerProperty(-1);


    public EditCardsView(StackPane parentStackPane, ObservableList<Deck> data, int index)
    {
        this.parentStackPane = parentStackPane;
        this.indexOfDeck = index;
        this.data = data;

        parentStackPane.getChildren().addAll(inflateView());
        parentStackPane.getChildren().get(0).setVisible(false);
    }

    private Node inflateView()
    {
        primaryVBox = new VBox();
        primaryVBox.getStylesheets().add(getClass().getResource("/styles/EditCardsView.css").toExternalForm());

        ArrayList<Card> cards = data.get(indexOfDeck).getCards();
        ObservableList<Card> cardsObservableList = FXCollections.observableArrayList(data.get(indexOfDeck).getCards());

        simpleIntegerProperty.addListener(new ChangeListener<Number>()
        {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue)
            {
                if (primaryVBox.getChildren().size() == 2) primaryVBox.getChildren().remove(1);

                Node newNodeToBeAdded = editSection(cards.get(newValue.intValue()));
                final FadeTransition transition = new FadeTransition(Duration.millis(600), newNodeToBeAdded);
                transition.setFromValue(0);
                transition.setToValue(1);
                transition.setInterpolator(Interpolator.EASE_IN);
                primaryVBox.getChildren().add(newNodeToBeAdded);
                transition.play();
            }
        });
        cardsTableView = new CardsTableView(cardsObservableList, simpleIntegerProperty);
        primaryVBox.getChildren().addAll(cardsTableView);
        primaryVBox.getStyleClass().add("node-border");
        return primaryVBox;
    }


    VBox containerVBox;
    HBox buttonHBox;
    Button updateButton;


    CheckBox needTextFieldInBackCheckBox;
    CheckBox hasVoiceCheckBox;
    CheckBox faceTextShownCheckBox;

    VBox textFieldsBackVBox;

    TextField faceTextField;
    AutoCompleteTextField faceAutoCompleteTextField;

    private Node editSection(Card selectedCard)
    {
        containerVBox = new VBox();
        containerVBox.setSpacing(20);
        containerVBox.setPadding(new Insets(20, 0, 20, 0));

        VBox faceVBox = new VBox();
        faceVBox.setSpacing(10);
        faceVBox.setPadding(new Insets(10, 10, 0, 10));

        HBox hBoxFaceVBox = new HBox();

        Label faceLabel = new Label("Front");
        Region regionHBoxFaceVBox = new Region();
        HBox.setHgrow(regionHBoxFaceVBox, Priority.ALWAYS);
        hasVoiceCheckBox = new CheckBox("Has Voice");
        faceTextShownCheckBox = new CheckBox("Face Text Shown");
        faceTextShownCheckBox.setDisable(true);
        faceTextShownCheckBox.setSelected(true);
        needTextFieldInBackCheckBox = new CheckBox("Needs TextField In Back");
        hBoxFaceVBox.getChildren().addAll(faceLabel, regionHBoxFaceVBox, needTextFieldInBackCheckBox, hasVoiceCheckBox, faceTextShownCheckBox);
        needTextFieldInBackCheckBox.setPadding(new Insets(0, 10, 0, 0));
        hasVoiceCheckBox.setPadding(new Insets(0, 10, 0, 0));

        hasVoiceCheckBox.selectedProperty().addListener(new ChangeListener<Boolean>()
        {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue)
            {
                if (newValue)
                {
                    faceTextShownCheckBox.setDisable(false);
                    faceTextShownCheckBox.setSelected(false);
                    faceVBox.getChildren().remove(faceTextField);
                    faceVBox.getChildren().add(faceAutoCompleteTextField);
                } else
                {
                    faceVBox.getChildren().remove(faceAutoCompleteTextField);
                    faceVBox.getChildren().add(faceTextField);
                    faceTextShownCheckBox.setDisable(true);
                    faceTextShownCheckBox.setSelected(true);
                }
            }
        });

        faceTextField = new TextField();
        SortedSet<String> entries = new TreeSet<>();
        entries.addAll(Initializer.getEnglishVoiceSoundNames());
        faceAutoCompleteTextField = new AutoCompleteTextField(entries);

        faceVBox.getChildren().addAll(hBoxFaceVBox, faceTextField);

        VBox backVBox = new VBox();
        backVBox.setSpacing(10);

        ScrollPane textFieldsBackScrollPane = new ScrollPane();
        textFieldsBackScrollPane.setFitToWidth(true);
        textFieldsBackScrollPane.setMinHeight(75);
        textFieldsBackScrollPane.setMaxHeight(75);
        textFieldsBackVBox = new VBox();
        textFieldsBackVBox.setSpacing(10);
        textFieldsBackScrollPane.setContent(textFieldsBackVBox);

        textFieldsBackVBox.setPadding(new Insets(10, 10, 10, 10));
        textFieldsBackVBox.prefWidthProperty().bind(textFieldsBackScrollPane.widthProperty());

        Label labelBack = new Label("Back:");
        Region region = new Region();
        HBox.setHgrow(region, Priority.ALWAYS);
        ImageButton newTextFieldImageButton = new ImageButton(new Image(getClass().getClassLoader().getResourceAsStream("images/icon_add.png")), 25, 25);

        if (selectedCard == null) createTextField(null);

        newTextFieldImageButton.setOnAction(event ->
        {
            createTextField(null);
        });

        HBox hBoxTextAreaDescriptionBack = new HBox();
        TextArea textAreaDescriptionBack = new TextArea();
        textAreaDescriptionBack.setMaxHeight(75);
        hBoxTextAreaDescriptionBack.setPadding(new Insets(0, 10, 0, 10));
        hBoxTextAreaDescriptionBack.getChildren().add(textAreaDescriptionBack);

        HBox.setHgrow(textAreaDescriptionBack, Priority.ALWAYS);

        HBox hBoxBackVBox = new HBox(labelBack, region, newTextFieldImageButton);
        hBoxBackVBox.setAlignment(Pos.CENTER);
        hBoxBackVBox.setPadding(new Insets(0, 10, 0, 10));


        backVBox.getChildren().addAll(hBoxBackVBox, textFieldsBackScrollPane, hBoxTextAreaDescriptionBack);


        updateButton = new Button("Update");
        updateButton.setId("green-button");
        updateButton.setMinWidth(100);
        buttonHBox = new HBox();
        buttonHBox.getChildren().addAll(updateButton);
        buttonHBox.setAlignment(Pos.BOTTOM_RIGHT);
        buttonHBox.setPadding(new Insets(0, 10, 0, 0));


        updateButton.setOnAction(e ->
        {
            String faceCardText;

            if (isValidCard())
            {
                if (hasVoiceCheckBox.isSelected()) faceCardText = faceAutoCompleteTextField.getText();
                else faceCardText = faceTextField.getText();

                ArrayList<String> backData = new ArrayList<>();
                Iterator iterator = textFieldsBackVBox.getChildren().iterator();
                while (iterator.hasNext())
                {
                    HBox hBox = (HBox) iterator.next();
                    TextField textField = (TextField) hBox.getChildren().get(0);
                    if (textField.getText().length() > 0) backData.add(textField.getText());
                }
                selectedCard.setFaceData(faceCardText);
                selectedCard.setBackData(backData);
                selectedCard.setDescriptionBack(textAreaDescriptionBack.getText());
                selectedCard.setHasTextFieldsOnBack(needTextFieldInBackCheckBox.isSelected());
                selectedCard.setFaceDataShown(faceTextShownCheckBox.isSelected());
                selectedCard.setHasVoiceOnFace(hasVoiceCheckBox.isSelected());

                new SavedObjectWriterReader().write(Initializer.getFlashCard());
                cardsTableView.refresh();

                Node nodeToBeRemoved = primaryVBox.getChildren().get(1);
                final FadeTransition transition = new FadeTransition(Duration.millis(250), nodeToBeRemoved);
                transition.setFromValue(nodeToBeRemoved.getOpacity());
                transition.setToValue(0);
                transition.setInterpolator(Interpolator.EASE_BOTH);
                transition.setOnFinished(finishHim ->
                {
                    primaryVBox.getChildren().remove(nodeToBeRemoved);
                });
                transition.play();


            } else
            {
                showNotification();
            }


        });

        containerVBox.getChildren().addAll(faceVBox, backVBox, buttonHBox);

        if (selectedCard.isHasTextFieldsOnBack()) needTextFieldInBackCheckBox.setSelected(true);

        if (selectedCard.isHasVoiceOnFace())
        {
            faceTextShownCheckBox.setDisable(false);
            hasVoiceCheckBox.setSelected(true);
            faceAutoCompleteTextField.appendText(selectedCard.getFaceData().replace(".mp3", ""));
        } else
        {
            hasVoiceCheckBox.setSelected(false);
            faceTextField.setText(selectedCard.getFaceData());
        }
        for (String cardBackData : selectedCard.getBackData())
        {
            createTextField(cardBackData);
        }

        faceTextShownCheckBox.setSelected(selectedCard.isFaceDataShown());

        textAreaDescriptionBack.setText(selectedCard.getDescriptionBack());


        return containerVBox;
    }

    private void createTextField(String textFieldString)
    {
        HBox newHBoxTextFieldsBackVBox = new HBox();
        TextField newBackTextField = new TextField();
        if (textFieldString != null) newBackTextField.setText(textFieldString);
        HBox.setHgrow(newBackTextField, Priority.ALWAYS);
        newHBoxTextFieldsBackVBox.setSpacing(15);
        ImageButton newRemoveTextField = new ImageButton(new Image(getClass().getClassLoader().getResourceAsStream("images/icon_remove.png")), 15, 15);
        newHBoxTextFieldsBackVBox.setPadding(new Insets(0, 10, 0, 10));
        newHBoxTextFieldsBackVBox.setAlignment(Pos.CENTER);
        newHBoxTextFieldsBackVBox.getChildren().addAll(newBackTextField, newRemoveTextField);
        textFieldsBackVBox.getChildren().addAll(newHBoxTextFieldsBackVBox);

        newRemoveTextField.setOnAction(event1 ->
        {
            if (textFieldsBackVBox.getChildren().size() > 1)
                textFieldsBackVBox.getChildren().remove(newHBoxTextFieldsBackVBox);
        });
    }

    private void showNotification()
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Dialog");
        alert.setHeaderText("Card was not created");
        alert.setContentText("Required deckObservableList was not entered");
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.initStyle(StageStyle.UTILITY);
        Stage stageAlert = (Stage) alert.getDialogPane().getScene().getWindow();
        stageAlert.getIcons().addAll(new Image(getClass().getClassLoader().getResourceAsStream("images/icon_eloy_flash_card_mini.png")));
        alert.showAndWait();
    }

    private boolean isValidCard()
    {
        if (hasVoiceCheckBox.isSelected())
        {
            if (!faceAutoCompleteTextField.isAcceptable()) return false;
        } else
        {
            if (!(faceTextField.getText().length() > 0)) return false;
        }

        int backSideCardNum = 0;
        ArrayList<String> backData = new ArrayList<>();
        Iterator iterator = textFieldsBackVBox.getChildren().iterator();
        while (iterator.hasNext())
        {
            HBox hBox = (HBox) iterator.next();
            TextField textField = (TextField) hBox.getChildren().get(0);
            if (textField.getText().length() > 0) backSideCardNum++;
        }

        if (backSideCardNum == 0) return false;

        return true;
    }
}
