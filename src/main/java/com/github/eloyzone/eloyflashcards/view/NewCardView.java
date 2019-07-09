package com.github.eloyzone.eloyflashcards.view;

import com.github.eloyzone.eloyflashcards.model.Card;
import com.github.eloyzone.eloyflashcards.model.Deck;
import com.github.eloyzone.eloyflashcards.util.Initializer;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import java.util.SortedSet;
import java.util.TreeSet;

public class NewCardView
{
    private Stage stage;
    private Scene scene;

    private VBox containerVBox;
    private HBox buttonHBox;
    private Button addButton;
    private Button closeButton;

    CheckBox needTextFieldInBackCheckBox;
    CheckBox hasVoiceCheckBox;
    CheckBox faceTextShownCheckBox;
    ComboBox<String> deckSelectorComboBox;

    VBox textFieldsBackVBox;

    TextField faceTextField;
    AutoCompleteTextField faceAutoCompleteTextField;

    private ArrayList<Deck> decks;


    public NewCardView(ArrayList<Deck> decks)
    {
        this.decks = decks;

        containerVBox = new VBox();
        containerVBox.setSpacing(20);
        containerVBox.setPadding(new Insets(20, 0, 20, 0));

        HBox deckSelectorHBox = new HBox();
        deckSelectorHBox.setSpacing(20);
        deckSelectorHBox.setAlignment(Pos.CENTER);
        Label deckSelectorLabel = new Label("Select your desired deck");
        deckSelectorComboBox = new ComboBox<String>();
        for (Deck deck : decks)
            deckSelectorComboBox.getItems().add(deck.getName());
        deckSelectorComboBox.setMinWidth(150);
        deckSelectorHBox.getChildren().addAll(deckSelectorLabel, deckSelectorComboBox);
        containerVBox.getChildren().add(deckSelectorHBox);

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
        textFieldsBackVBox = new VBox();
        textFieldsBackVBox.setSpacing(10);
        textFieldsBackScrollPane.setContent(textFieldsBackVBox);

        textFieldsBackVBox.setPadding(new Insets(10, 10, 10, 10));
        textFieldsBackVBox.prefWidthProperty().bind(textFieldsBackScrollPane.widthProperty());

        Label labelBack = new Label("Back:");
        Region region = new Region();
        HBox.setHgrow(region, Priority.ALWAYS);
        ImageButton newTextFieldImageButton = new ImageButton(new Image(getClass().getClassLoader().getResourceAsStream("images/icon_add.png")), 25, 25);

        HBox hBoxTextFieldsBackVBox = new HBox();
        TextField backTextField = new TextField();
        HBox.setHgrow(backTextField, Priority.ALWAYS);
        hBoxTextFieldsBackVBox.setSpacing(15);
        ImageButton removeTextField = new ImageButton(new Image(getClass().getClassLoader().getResourceAsStream("images/icon_remove.png")), 15, 15);
        hBoxTextFieldsBackVBox.setPadding(new Insets(0, 10, 0, 10));
        hBoxTextFieldsBackVBox.setAlignment(Pos.CENTER);
        hBoxTextFieldsBackVBox.getChildren().addAll(backTextField, removeTextField);

        textFieldsBackVBox.getChildren().addAll(hBoxTextFieldsBackVBox);

        newTextFieldImageButton.setOnAction(event ->
        {
            HBox newHBoxTextFieldsBackVBox = new HBox();
            TextField newBackTextField = new TextField();
            HBox.setHgrow(newBackTextField, Priority.ALWAYS);
            newHBoxTextFieldsBackVBox.setSpacing(15);
            ImageButton newRemoveTextField = new ImageButton(new Image(getClass().getClassLoader().getResourceAsStream("images/icon_remove.png")), 15, 15);
            newHBoxTextFieldsBackVBox.setPadding(new Insets(0, 10, 0, 10));
            newHBoxTextFieldsBackVBox.setAlignment(Pos.CENTER);
            newHBoxTextFieldsBackVBox.getChildren().addAll(newBackTextField, newRemoveTextField);
            textFieldsBackVBox.getChildren().addAll(newHBoxTextFieldsBackVBox);

            newRemoveTextField.setOnAction(event1 ->
            {
                textFieldsBackVBox.getChildren().remove(newHBoxTextFieldsBackVBox);
            });
        });

        HBox hBoxTextAreaDescriptionBack = new HBox();
        TextArea textAreaDescriptionBack = new TextArea();
        textAreaDescriptionBack.setMinHeight(150);
        hBoxTextAreaDescriptionBack.getChildren().add(textAreaDescriptionBack);
        hBoxTextAreaDescriptionBack.setPadding(new Insets(0, 10, 0, 10));


        HBox hBoxBackVBox = new HBox(labelBack, region, newTextFieldImageButton);
        hBoxBackVBox.setAlignment(Pos.CENTER);
        hBoxBackVBox.setPadding(new Insets(0, 10, 0, 10));

        backVBox.getChildren().addAll(hBoxBackVBox, textFieldsBackScrollPane, hBoxTextAreaDescriptionBack);

        addButton = new Button("Add");
        closeButton = new Button("Close");
        addButton.setMinWidth(100);
        closeButton.setMinWidth(100);
        buttonHBox = new HBox();
        buttonHBox.getChildren().addAll(addButton, closeButton);
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
                Iterator iterator = textFieldsBackVBox.getChildren().iterator();
                while (iterator.hasNext())
                {
                    HBox hBox = (HBox) iterator.next();
                    TextField textField = (TextField) hBox.getChildren().get(0);
                    if (textField.getText().length() > 0) backData.add(textField.getText());
                }
                Deck deck = Initializer.getFlashCard().getDecks().get(deckIndex);
                Card card = new Card(deck, faceCardText, backData, textAreaDescriptionBack.getText(), needTextFieldInBackCheckBox.isSelected(), faceTextShownCheckBox.isSelected(), hasVoiceCheckBox.isSelected());
                deck.addNewCard(card);
                stage.close();
            } else
            {
                showNotification();
            }


        });

        closeButton.setOnAction(e ->
        {
            stage.close();
        });


        containerVBox.getChildren().addAll(faceVBox, backVBox, buttonHBox);
        stage = new Stage();
        scene = new Scene(containerVBox);
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

    private void showNotification()
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Dialog");
        alert.setHeaderText("Card was not created");
        alert.setContentText("Required deckObservableList was not entered");
        alert.showAndWait();
    }
}
