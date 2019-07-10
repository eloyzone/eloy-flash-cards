package com.github.eloyzone.eloyflashcards.view;

import com.github.eloyzone.eloyflashcards.model.Deck;
import com.github.eloyzone.eloyflashcards.util.Initializer;
import com.github.eloyzone.eloyflashcards.util.SavedObjectWriterReader;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
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
    private Button addButton;
    private Button cancelButton;
    private ObservableList<Deck> data;
    private int indexOfDeckForEdit = -1;

    public NewDeckView(ObservableList<Deck> data)
    {
        this.data = data;
        createButtons();
        createOtherView();
        cancelButton.requestFocus();
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
        cancelButton.requestFocus();;
        stage.showAndWait();
    }

    private void createOtherView()
    {
        VBox vBox = new VBox();
        deckNameLabel = new Label("Deck Name:");
        deckDescriptionLabel = new Label("Deck Description:");
        deckNameTextField = new TextField();
        deckDescriptionTextArea = new TextArea();
        deckNameTextField.setPromptText("Fill in the gap");

        vBox.getChildren().addAll(deckNameLabel, deckNameTextField, deckDescriptionLabel, deckDescriptionTextArea, buttonHBox);
        vBox.setPadding(new Insets(10, 10, 10, 10));
        vBox.setSpacing(10);

        stage = new Stage();
        scene = new Scene(vBox, 400, 250);
        scene.getStylesheets().add(getClass().getClassLoader().getResource("styles/NewDeckView.css").toExternalForm());
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
        addButton = new Button("Add");
        addButton.setId("green-button");
        cancelButton.setId("red-button");
        buttonHBox.getChildren().addAll(cancelButton, addButton);
        buttonHBox.setAlignment(Pos.BOTTOM_RIGHT);


        cancelButton.setOnAction(event -> stage.close());

        addButton.setOnAction(event ->
        {


            if(deckNameTextField.getText().equals(""))
            {
                showNotification();
            }
            else
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
            }
        });
    }

    private void showNotification()
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Dialog");
        alert.setHeaderText("Deck was not created");
        alert.setContentText("Required data was not entered\nEnter the name of Deck.");
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.initStyle(StageStyle.UTILITY);
        Stage stageAlert = (Stage) alert.getDialogPane().getScene().getWindow();
        stageAlert.getIcons().addAll(new Image(getClass().getClassLoader().getResourceAsStream("images/icon_eloy_flash_card_mini.png")));
        alert.showAndWait();
    }
}
