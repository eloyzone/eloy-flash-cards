package com.github.eloyzone.eloyflashcards.view;

import com.github.eloyzone.eloyflashcards.model.Deck;
import com.github.eloyzone.eloyflashcards.util.Initializer;
import com.github.eloyzone.eloyflashcards.util.SavedObjectWriterReader;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class NewDeckView
{
    private Stage stage;
    private Scene scene;
    private Label deckNameLabel;
    private TextField deckNameTextField;
    private Label deckDescriptionLabel;
    private TextArea deckDescriptionTextArea;
    private HBox buttonHBox;
    private Button okButton;
    private Button cancelButton;
    private ObservableList<Deck> data;
    private int indexOfDeckForEdit = -1;

    public NewDeckView(ObservableList<Deck> data)
    {
        this.data = data;
        createButtons();
        createOtherView();
        stage.showAndWait();
    }

    public NewDeckView(ObservableList<Deck> data, int index)
    {
        this.data = data;
        this.indexOfDeckForEdit = index;
        createButtons();
        createOtherView();
        deckNameTextField.setText(data.get(index).getName());
        deckDescriptionTextArea.setText(data.get(index).getDescription());
        stage.showAndWait();
    }

    private void createOtherView()
    {
        VBox vBox = new VBox();
        deckNameLabel = new Label("Name for deck:");
        deckDescriptionLabel = new Label("Description for deck:");
        deckDescriptionTextArea = new TextArea();
        deckNameTextField = new TextField();
        deckDescriptionTextArea = new TextArea();

        vBox.getChildren().addAll(deckNameLabel, deckNameTextField, deckDescriptionLabel, deckDescriptionTextArea, buttonHBox);
        vBox.setPadding(new Insets(10, 10, 10, 10));
        vBox.setSpacing(10);

        stage = new Stage();
        scene = new Scene(vBox, 400, 250);
        stage.setResizable(false);
        stage.setTitle("New Deck");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.getIcons().add(new Image(getClass().getClassLoader().getResourceAsStream("images/icon_eloy_flash_card_mini.png")));
        stage.initStyle(StageStyle.UTILITY);
    }



    private void createButtons()
    {
        buttonHBox = new HBox();
        cancelButton = new Button("Cancel");
        okButton = new Button("OK");
        cancelButton.setMinWidth(100);
        okButton.setMinWidth(100);
        buttonHBox.getChildren().addAll(cancelButton, okButton);
        buttonHBox.setAlignment(Pos.BOTTOM_RIGHT);


        cancelButton.setOnAction(event -> stage.close());

        okButton.setOnAction(event ->
        {
            if(indexOfDeckForEdit > -1)
            {
                data.get(indexOfDeckForEdit).setName(deckNameTextField.getText());
                data.get(indexOfDeckForEdit).setDescription(deckDescriptionTextArea.getText());
                Initializer.getFlashCard().getDecks().get(indexOfDeckForEdit).setName(deckNameTextField.getText());
                Initializer.getFlashCard().getDecks().get(indexOfDeckForEdit).setName(deckNameTextField.getText());
                new SavedObjectWriterReader().write(Initializer.getFlashCard());
            }
            else
            {
                Deck deck = new Deck(deckNameTextField.getText(), deckDescriptionTextArea.getText());
                Initializer.getFlashCard().getDecks().add(deck);
                new SavedObjectWriterReader().write(Initializer.getFlashCard());
                data.add(deck);
            }
            stage.close();
        });

    }
}
