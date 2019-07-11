package com.github.eloyzone.eloyflashcards.view;

import com.github.eloyzone.eloyflashcards.util.Initializer;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
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

        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        Label directoryLabel = new Label("Directory:");
        directoryLabel.setPadding(new Insets(0, 10, 0, 0));
        Label resourceDirectoryLabel = new Label("No Path Founded");
        resourceDirectoryLabel.setMaxWidth(350);
        resourceDirectoryLabel.setPadding(new Insets(0, 10, 0, 0));
        Button browseNewDirectoryButton = new Button("Browse");
        browseNewDirectoryButton.setId("red-button");
        hBox.getChildren().addAll(directoryLabel, resourceDirectoryLabel, browseNewDirectoryButton);

        if (Initializer.getFlashCard().getDirectoryOfEnglishSound() != null)
        {
            resourceDirectoryLabel.setText(Initializer.getFlashCard().getDirectoryOfEnglishSound().getPath());
            resourceDirectoryLabel.setTooltip(new Tooltip(resourceDirectoryLabel.getText()));
        }

        browseNewDirectoryButton.setOnAction(event ->
        {
            DirectoryChooser directoryChooser = new DirectoryChooser();
            File selectedDirectory = directoryChooser.showDialog(stage);
            if (selectedDirectory != null)
            {
                Initializer.setResourceDirectoryFile(selectedDirectory);
                resourceDirectoryLabel.setText(Initializer.getFlashCard().getDirectoryOfEnglishSound().getPath());
                resourceDirectoryLabel.setTooltip(new Tooltip(resourceDirectoryLabel.getText()));
            }
        });

        vBox.getChildren().addAll(hBox);
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
