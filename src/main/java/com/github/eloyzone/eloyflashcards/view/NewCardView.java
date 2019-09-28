package com.github.eloyzone.eloyflashcards.view;

import com.github.eloyzone.eloyflashcards.model.Card;
import com.github.eloyzone.eloyflashcards.model.Deck;
import com.github.eloyzone.eloyflashcards.model.VoiceLanguage;
import com.github.eloyzone.eloyflashcards.util.Initializer;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.ArrayList;
import java.util.Iterator;

public class NewCardView
{
    private Stage stage;
    private Scene scene;

    private VBox primaryVBox;

    private HBox deckSelectorHBox;
    private Label deckSelectorLabel;
    private ComboBox<String> deckSelectorComboBox;

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
    private Button addButton;
    private Button cancelButton;

    public NewCardView(ArrayList<Deck> decks)
    {
        primaryVBox = new VBox();
        primaryVBox.setSpacing(20);
        primaryVBox.setPadding(new Insets(20, 0, 20, 0));

        deckSelectorHBox = new HBox();
        deckSelectorHBox.setSpacing(20);
        deckSelectorHBox.setAlignment(Pos.CENTER);
        deckSelectorLabel = new Label("Select your desired deck");
        deckSelectorComboBox = new ComboBox<>();
        for (Deck deck : decks)
            deckSelectorComboBox.getItems().add(deck.getName());
        deckSelectorComboBox.setMinWidth(150);
        deckSelectorHBox.getChildren().addAll(deckSelectorLabel, deckSelectorComboBox);


        primaryVBox.getChildren().add(deckSelectorHBox);

        faceCardVBox = new VBox();
        faceCardVBox.setSpacing(10);
        faceCardVBox.setPadding(new Insets(10, 10, 0, 10));

        hBoxFaceCardVBox = new HBox();

        faceCardLabel = new Label("Front");
        regionHBoxFaceCardVBox = new Region();
        HBox.setHgrow(regionHBoxFaceCardVBox, Priority.ALWAYS);
        hasVoiceCheckBox = new CheckBox("Has Voice");
        faceCardVoiceLanguageComboBox = new ComboBox<>();
        faceCardVoiceLanguageComboBox.getItems().addAll(VoiceLanguage.getAllAvailableVoices());
        faceCardVoiceLanguageComboBox.setDisable(true);
        faceTextShownCheckBox = new CheckBox("Face Text Shown");
        faceTextShownCheckBox.setDisable(true);
        faceTextShownCheckBox.setSelected(true);
        needTextFieldInBackCheckBox = new CheckBox("Needs TextField In Back");
        hBoxFaceCardVBox.getChildren().addAll(faceCardLabel, regionHBoxFaceCardVBox, needTextFieldInBackCheckBox, hasVoiceCheckBox, faceCardVoiceLanguageComboBox, faceTextShownCheckBox);
        hBoxFaceCardVBox.setAlignment(Pos.CENTER);
        hBoxFaceCardVBox.setSpacing(10);
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
        faceCardVoiceLanguageComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->
        {
            if (newValue.toLowerCase().equals(VoiceLanguage.ENGLISH))
            {
                faceAutoCompleteTextField.switchLanguage(Initializer.getEnglishVoiceSoundNames());
            } else if (newValue.toLowerCase().equals(VoiceLanguage.GERMAN))
            {
                faceAutoCompleteTextField.switchLanguage(Initializer.getGermanVoiceSoundNames());
            }
        });


        faceAutoCompleteTextField = new AutoCompleteTextField(Initializer.getEnglishVoiceSoundNames());

        faceCardVBox.getChildren().addAll(hBoxFaceCardVBox, faceTextField);

        backCardVBox = new VBox();
        backCardVBox.setSpacing(10);

        backCardLabel = new Label("Back");
        regionHBoxBackCardVBox = new Region();
        HBox.setHgrow(regionHBoxBackCardVBox, Priority.ALWAYS);
        newTextFieldImageButton = new ImageButton(new Image(getClass().getClassLoader().getResourceAsStream("images/icon_add.png")), 25, 25);


        backCardHBoxVBox = new HBox(backCardLabel, regionHBoxBackCardVBox, newTextFieldImageButton);
        backCardHBoxVBox.setAlignment(Pos.CENTER);
        backCardHBoxVBox.setPadding(new Insets(0, 10, 0, 10));

        backTextFieldsScrollPane = new ScrollPane();
        backTextFieldsScrollPane.setFitToWidth(true);
        backTextFieldsScrollPane.setMinHeight(75);

        backTextFieldVBox = new VBox();
        backTextFieldVBox.setSpacing(10);
        backTextFieldsScrollPane.setContent(backTextFieldVBox);

        backTextFieldVBox.setPadding(new Insets(10, 10, 10, 10));
        backTextFieldVBox.prefWidthProperty().bind(backTextFieldsScrollPane.widthProperty());

        createTextField(null);
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

        addButton = new Button("Add");
        cancelButton = new Button("Cancel");
        addButton.setId("green-button");
        cancelButton.setId("red-button");
        addButton.setMinWidth(100);
        cancelButton.setMinWidth(100);
        buttonHBox = new HBox();
        buttonHBox.getChildren().addAll(addButton, cancelButton);
        buttonHBox.setAlignment(Pos.BOTTOM_RIGHT);
        buttonHBox.setPadding(new Insets(0, 10, 0, 0));

        addButton.setOnAction(e ->
        {
            String faceCardText;

            if (isValidCard())
            {
                if (hasVoiceCheckBox.isSelected()) faceCardText = faceAutoCompleteTextField.getText();
                else faceCardText = faceTextField.getText();

                int deckIndex = deckSelectorComboBox.getSelectionModel().getSelectedIndex();
                ArrayList<String> backData = new ArrayList<>();
                Iterator iterator = backTextFieldVBox.getChildren().iterator();
                while (iterator.hasNext())
                {
                    HBox hBox = (HBox) iterator.next();
                    TextField textField = (TextField) hBox.getChildren().get(0);
                    if (textField.getText().length() > 0) backData.add(textField.getText().trim().toLowerCase());
                }
                Deck deck = Initializer.getFlashCard().getDecks().get(deckIndex);
                Card card = new Card(deck, faceCardText.trim().toLowerCase(), backData, textAreaDescriptionBack.getText(), needTextFieldInBackCheckBox.isSelected(), faceTextShownCheckBox.isSelected(), hasVoiceCheckBox.isSelected(), faceCardVoiceLanguageComboBox.getSelectionModel().getSelectedItem());
                deck.addNewCard(card);
                stage.close();
            } else
            {
                showNotification();
            }


        });

        cancelButton.setOnAction(e -> stage.close());

        primaryVBox.getChildren().addAll(faceCardVBox, backCardVBox, buttonHBox);
        stage = new Stage();
        scene = new Scene(primaryVBox);
        scene.getStylesheets().add(getClass().getResource("/styles/NewCardView.css").toExternalForm());
        stage.setResizable(false);
        stage.setScene(scene);
        stage.getIcons().add(new Image(getClass().getClassLoader().getResourceAsStream("images/icon_eloy_flash_card_mini.png")));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UTILITY);
        stage.setTitle("New Card");
        stage.show();

        double maxWidth = stage.getWidth();
        double maxHeight = stage.getHeight();
        stage.setMaxWidth(maxWidth);
        stage.setMaxHeight(maxHeight);
    }


    private void createTextField(String textFieldString)
    {
        HBox newHBoxTextFieldsBackVBox = new HBox();
        TextField newBackTextField = new TextField();
        newBackTextField.setPromptText("Fill in the gap");
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

    private boolean isValidCard()
    {
        if (hasVoiceCheckBox.isSelected())
        {
            if (!faceAutoCompleteTextField.isAcceptable()) return false;
        } else
        {
            if (!(faceTextField.getText().length() > 0)) return false;
        }

        int deckIndex = deckSelectorComboBox.getSelectionModel().getSelectedIndex();
        if (deckIndex == -1) return false;

        int backSideCardNum = 0;
        ArrayList<String> backData = new ArrayList<>();
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
}
