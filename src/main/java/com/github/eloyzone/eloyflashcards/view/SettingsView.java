package com.github.eloyzone.eloyflashcards.view;

import com.github.eloyzone.eloyflashcards.model.FlashCard;
import com.github.eloyzone.eloyflashcards.util.Initializer;
import com.github.eloyzone.eloyflashcards.util.SavedObjectWriterReader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;

public class SettingsView
{
    public SettingsView()
    {
        Stage stage = new Stage();
        VBox vBox = new VBox();
        vBox.setPadding(new Insets(15, 15, 15, 15));

        HBox resourceDirectoryHBox = new HBox();
        resourceDirectoryHBox.setAlignment(Pos.CENTER);
        Label directoryLabel = new Label("Directory:");
        directoryLabel.setPadding(new Insets(0, 10, 0, 0));
        Label resourceDirectoryLabel = new Label("No Path Founded");
        resourceDirectoryLabel.setMaxWidth(350);
        resourceDirectoryLabel.setPadding(new Insets(0, 10, 0, 0));
        Button browseNewDirectoryButton = new Button("Browse");
        browseNewDirectoryButton.setId("red-button");
        resourceDirectoryHBox.getChildren().addAll(directoryLabel, resourceDirectoryLabel, browseNewDirectoryButton);

        if (Initializer.getFlashCard().getResourceDirectory() != null)
        {
            resourceDirectoryLabel.setText(Initializer.getFlashCard().getResourceDirectory().getPath());
            resourceDirectoryLabel.setTooltip(new Tooltip(resourceDirectoryLabel.getText()));
        }

        browseNewDirectoryButton.setOnAction(event ->
        {
            DirectoryChooser directoryChooser = new DirectoryChooser();
            File selectedDirectory = directoryChooser.showDialog(stage);
            if (selectedDirectory != null)
            {
                Initializer.setResourceDirectoryFile(selectedDirectory);
                resourceDirectoryLabel.setText(Initializer.getFlashCard().getResourceDirectory().getPath());
                resourceDirectoryLabel.setTooltip(new Tooltip(resourceDirectoryLabel.getText()));
            }
        });


        HBox voiceAccentHBox = new HBox();
        voiceAccentHBox.setSpacing(10);
        voiceAccentHBox.setPadding(new Insets(20, 0, 0, 0));
        voiceAccentHBox.setAlignment(Pos.CENTER_LEFT);
        Label voiceAccentLabel = new Label("Select your desired accent:\t");
        ToggleGroup voiceAccentToggleGroup = new ToggleGroup();
        RadioButton britishRadioButton = new RadioButton("British");
        britishRadioButton.setUserData(FlashCard.UK_ACCENT);
        britishRadioButton.setToggleGroup(voiceAccentToggleGroup);
        RadioButton americanRadioButton = new RadioButton("American");
        americanRadioButton.setUserData(FlashCard.US_ACCENT);
        americanRadioButton.setToggleGroup(voiceAccentToggleGroup);
        voiceAccentHBox.getChildren().addAll(voiceAccentLabel, britishRadioButton, americanRadioButton);

        if(Initializer.getFlashCard().getDesiredEnglishAccent().equals(FlashCard.UK_ACCENT))
            britishRadioButton.setSelected(true);
        else
            americanRadioButton.setSelected(true);

        voiceAccentToggleGroup.selectedToggleProperty().addListener(event ->
        {
            RadioButton selectedRadioButton = (RadioButton) voiceAccentToggleGroup.getSelectedToggle();
            Initializer.getFlashCard().setDesiredEnglishAccent((String) selectedRadioButton.getUserData());
            new SavedObjectWriterReader().write(Initializer.getFlashCard());
        });

        vBox.getChildren().addAll(resourceDirectoryHBox, voiceAccentHBox);
        Scene scene = new Scene(vBox);
        scene.getStylesheets().add(getClass().getResource("/styles/SettingsView.css").toExternalForm());
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("Settings");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.getIcons().add(new Image(getClass().getClassLoader().getResourceAsStream("images/icon_eloy_flash_card_mini.png")));
        stage.initStyle(StageStyle.UTILITY);
        stage.show();
        stage.setResizable(false);
    }
}
