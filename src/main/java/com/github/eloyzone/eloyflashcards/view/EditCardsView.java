package com.github.eloyzone.eloyflashcards.view;

import com.github.eloyzone.eloyflashcards.model.Card;
import com.github.eloyzone.eloyflashcards.model.Deck;
import com.github.eloyzone.eloyflashcards.model.VoiceLanguage;
import com.github.eloyzone.eloyflashcards.util.Initializer;
import com.github.eloyzone.eloyflashcards.util.SavedObjectWriterReader;
import com.github.eloyzone.eloyflashcards.util.Transitioner;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
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

import java.util.ArrayList;
import java.util.Iterator;

public class EditCardsView
{
    private ObservableList<Deck> dataDecksObservableList;
    private int indexOfDeck;

    private CardsSelectionTableView cardsSelectionTableView;
    private IntegerProperty indexOfSelectedCardIntegerProperty;

    private VBox editSelectedContainerVBox;

    private VBox primaryVBox;


    private VBox faceCardVBox;
    private HBox hBoxFaceCardVBox;
    private Label faceCardLabel;
    private Region regionHBoxFaceCardVBox;
    private CheckBox needTextFieldInBackCheckBox;
    private CheckBox hasVoiceCheckBox;
    private ComboBox<String> faceCardVoiceLanguageComboBox;
    private CheckBox faceTextShownCheckBox;
    private TextField faceTextField;
    private AutoCompleteTextField faceAutoCompleteTextField;
    private VBox backTextFieldVBox;

    private VBox backCardVBox;
    private HBox backCardHBoxVBox;
    private Label backCardLabel;
    private Region regionHBoxBackCardVBox;
    private ImageButton newTextFieldImageButton;
    private ScrollPane backTextFieldsScrollPane;
    private VBox backDescriptionTextAreaVBox;
    private Label backDescriptionLabelVBox;
    private TextArea textAreaDescriptionBack;

    private HBox buttonHBox;
    private Button updateButton;

    public EditCardsView(StackPane parentStackPane, ObservableList<Deck> dataDecksObservableList, int indexOfDeck)
    {
        this.indexOfDeck = indexOfDeck;
        this.dataDecksObservableList = dataDecksObservableList;
        this.indexOfSelectedCardIntegerProperty = new SimpleIntegerProperty(-1);

        parentStackPane.getChildren().addAll(inflateView());
        parentStackPane.getChildren().get(0).setVisible(false);
    }

    private Node inflateView()
    {
        primaryVBox = new VBox();

        ArrayList<Card> cardArrayList = dataDecksObservableList.get(indexOfDeck).getCards();
        ObservableList<Card> cardsObservableList = FXCollections.observableArrayList(dataDecksObservableList.get(indexOfDeck).getCards());

        cardsSelectionTableView = new CardsSelectionTableView(cardsObservableList, indexOfSelectedCardIntegerProperty);
        indexOfSelectedCardIntegerProperty.addListener((observable, oldValue, newValue) ->
        {
            if (primaryVBox.getChildren().size() == 2) primaryVBox.getChildren().remove(1);

            Node newNodeToBeAdded = editSelectedCard(cardArrayList.get(newValue.intValue()));
            FadeTransition fadeTransition = Transitioner.getFade(600, newNodeToBeAdded);
            primaryVBox.getChildren().add(newNodeToBeAdded);
            fadeTransition.play();
        });

        primaryVBox.getChildren().addAll(cardsSelectionTableView);
        primaryVBox.getStylesheets().add(getClass().getResource("/styles/EditCardsView.css").toExternalForm());
        primaryVBox.getStyleClass().add("node-border");
        return primaryVBox;
    }

    private Node editSelectedCard(Card selectedCard)
    {
        editSelectedContainerVBox = new VBox();
        editSelectedContainerVBox.setSpacing(20);
        editSelectedContainerVBox.setPadding(new Insets(20, 0, 20, 0));

        faceCardVBox = new VBox();
        faceCardVBox.setSpacing(10);
        faceCardVBox.setPadding(new Insets(10, 10, 0, 10));

        hBoxFaceCardVBox = new HBox();

        faceCardLabel = new Label("Front");
        faceCardLabel.getStyleClass().add("white-label");
        regionHBoxFaceCardVBox = new Region();
        HBox.setHgrow(regionHBoxFaceCardVBox, Priority.ALWAYS);
        hasVoiceCheckBox = new CheckBox("Has Voice");
        faceCardVoiceLanguageComboBox = new ComboBox<String>();
        faceCardVoiceLanguageComboBox.getItems().addAll(VoiceLanguage.getAllAvailableVoices());
        faceCardVoiceLanguageComboBox.setDisable(true);
        faceTextShownCheckBox = new CheckBox("Face Text Shown");
        faceTextShownCheckBox.setDisable(true);
        faceTextShownCheckBox.setSelected(true);
        needTextFieldInBackCheckBox = new CheckBox("Needs TextField In Back");
        hBoxFaceCardVBox.getChildren().addAll(faceCardLabel, regionHBoxFaceCardVBox, needTextFieldInBackCheckBox, hasVoiceCheckBox, faceCardVoiceLanguageComboBox, faceTextShownCheckBox);
        hBoxFaceCardVBox.setAlignment(Pos.CENTER);
        hBoxFaceCardVBox.setSpacing(10);
        needTextFieldInBackCheckBox.setPadding(new Insets(0, 10, 0, 0));
        hasVoiceCheckBox.setPadding(new Insets(0, 10, 0, 0));

        hasVoiceCheckBox.selectedProperty().addListener((observable, oldValue, newValue) ->
        {
            if (newValue)
            {
                faceTextShownCheckBox.setDisable(false);
                faceTextShownCheckBox.setSelected(false);
                faceCardVoiceLanguageComboBox.setDisable(false);
                faceCardVBox.getChildren().remove(faceTextField);
                faceCardVBox.getChildren().add(faceAutoCompleteTextField);
            } else
            {
                faceCardVBox.getChildren().remove(faceAutoCompleteTextField);
                faceCardVBox.getChildren().add(faceTextField);
                faceTextShownCheckBox.setDisable(true);
                faceTextShownCheckBox.setSelected(true);
                faceCardVoiceLanguageComboBox.setDisable(true);
            }
        });

        faceTextField = new TextField();
        faceTextField.setPromptText("Fill in the gap");

        faceCardVoiceLanguageComboBox.getSelectionModel().select(VoiceLanguage.ENGLISH);
        faceAutoCompleteTextField = new AutoCompleteTextField(Initializer.getEnglishVoiceSoundNames());

        if (selectedCard.isHasVoiceOnFace() && selectedCard.getVoiceLanguage().equals(VoiceLanguage.GERMAN))
        {
            faceCardVoiceLanguageComboBox.getSelectionModel().select(VoiceLanguage.GERMAN);
            faceAutoCompleteTextField.switchLanguage(Initializer.getGermanVoiceSoundNames());
        }

        faceCardVoiceLanguageComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->
        {
            if (newValue.equals(VoiceLanguage.ENGLISH))
            {
                faceAutoCompleteTextField.switchLanguage(Initializer.getEnglishVoiceSoundNames());
            } else if (newValue.equals(VoiceLanguage.GERMAN))
            {
                faceAutoCompleteTextField.switchLanguage(Initializer.getGermanVoiceSoundNames());
            }
        });

        faceCardVBox.getChildren().addAll(hBoxFaceCardVBox, faceTextField);


        backCardVBox = new VBox();
        backCardVBox.setSpacing(10);

        backCardLabel = new Label("Back");
        backCardLabel.getStyleClass().add("white-label");
        regionHBoxBackCardVBox = new Region();
        HBox.setHgrow(regionHBoxBackCardVBox, Priority.ALWAYS);
        newTextFieldImageButton = new ImageButton(new Image(getClass().getClassLoader().getResourceAsStream("images/icon_add.png")), 25, 25);

        backCardHBoxVBox = new HBox(backCardLabel, regionHBoxBackCardVBox, newTextFieldImageButton);
        backCardHBoxVBox.setAlignment(Pos.CENTER);
        backCardHBoxVBox.setPadding(new Insets(0, 10, 0, 10));

        backTextFieldsScrollPane = new ScrollPane();
        backTextFieldsScrollPane.setFitToWidth(true);
        backTextFieldsScrollPane.setMaxHeight(80);

        backTextFieldVBox = new VBox();
        backTextFieldVBox.setStyle("-fx-background-color: black;");
        backTextFieldVBox.setSpacing(5);
        backTextFieldsScrollPane.setContent(backTextFieldVBox);

        backTextFieldVBox.setPadding(new Insets(10, 10, 10, 10));
        backTextFieldVBox.prefWidthProperty().bind(backTextFieldsScrollPane.widthProperty());

        if (selectedCard == null) createTextField(null);

        newTextFieldImageButton.setOnAction(event -> createTextField(null));

        backDescriptionTextAreaVBox = new VBox();
        backDescriptionTextAreaVBox.setSpacing(5);
        backDescriptionLabelVBox = new Label("Description");
        backDescriptionLabelVBox.getStyleClass().add("white-label");

        textAreaDescriptionBack = new TextArea();
        textAreaDescriptionBack.setMaxHeight(75);
        textAreaDescriptionBack.setWrapText(true);
        backDescriptionTextAreaVBox.setPadding(new Insets(0, 10, 0, 10));
        backDescriptionTextAreaVBox.getChildren().addAll(backDescriptionLabelVBox, textAreaDescriptionBack);

        HBox.setHgrow(textAreaDescriptionBack, Priority.ALWAYS);

        backCardVBox.getChildren().addAll(backCardHBoxVBox, backTextFieldsScrollPane, backDescriptionTextAreaVBox);

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
                Iterator iterator = backTextFieldVBox.getChildren().iterator();
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
                selectedCard.setVoiceLanguage(faceCardVoiceLanguageComboBox.getSelectionModel().getSelectedItem());

                new SavedObjectWriterReader().write(Initializer.getFlashCard());
                cardsSelectionTableView.refresh();

                Node nodeToBeRemoved = primaryVBox.getChildren().get(1);
                final FadeTransition transition = Transitioner.getFade(250, nodeToBeRemoved, Interpolator.EASE_BOTH);
                transition.setOnFinished(finishHim -> primaryVBox.getChildren().remove(nodeToBeRemoved));
                transition.play();
            } else
            {
                showNotification();
            }
        });

        editSelectedContainerVBox.getChildren().addAll(faceCardVBox, backCardVBox, buttonHBox);

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


        return editSelectedContainerVBox;
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
        backTextFieldVBox.getChildren().addAll(newHBoxTextFieldsBackVBox);
        newBackTextField.requestFocus();


        newRemoveTextField.setOnAction(event1 ->
        {
            if (backTextFieldVBox.getChildren().size() > 1)
                backTextFieldVBox.getChildren().remove(newHBoxTextFieldsBackVBox);
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
        Iterator iterator = backTextFieldVBox.getChildren().iterator();
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
