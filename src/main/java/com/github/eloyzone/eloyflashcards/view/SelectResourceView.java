package com.github.eloyzone.eloyflashcards.view;

import com.github.eloyzone.eloyflashcards.util.Initializer;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.DirectoryChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;

public class SelectResourceView extends VBox
{

    public SelectResourceView()
    {
        Stage stage = new Stage();
        VBox vBox = new VBox();

        Image image = new Image(getClass().getClassLoader().getResourceAsStream("images/eloy_flash_card_mini.png"));
        ImageView imageView = new ImageView(image);


        VBox textsVBox = new VBox();

        HBox descriptionTextHBox = new HBox();
        Text descriptionText = new Text("No resources file have been founded.\n" + "Please select the appropriate directory. " + "If you don't select the resource directory, You won't be able to use voices.");
        descriptionText.setTextAlignment(TextAlignment.JUSTIFY);
        descriptionText.setFont(new Font("FreeSerif", 18));
        descriptionText.wrappingWidthProperty().set(image.getWidth());
        descriptionTextHBox.getChildren().add(descriptionText);
        descriptionTextHBox.setPadding(new Insets(15, 0, 0, 4));


        HBox buttonHBox = new HBox();
        Button browseButton = new Button("Browse");
        Button cancelButton = new Button("Cancel");
        buttonHBox.setPadding(new Insets(15, 0, 0, 0));
        cancelButton.setMinWidth(100);
        browseButton.setMinWidth(100);
        buttonHBox.getChildren().addAll(browseButton, cancelButton);
        buttonHBox.setAlignment(Pos.BOTTOM_RIGHT);

        browseButton.setOnAction(event ->
        {
            DirectoryChooser directoryChooser = new DirectoryChooser();
            File selectedDirectory = directoryChooser.showDialog(stage);
            if (selectedDirectory != null)
            {
                Initializer.setResourceDirectoryFile(selectedDirectory);
                stage.close();
            }
        });

        cancelButton.setOnAction(event-> stage.close());


        textsVBox.getChildren().addAll(descriptionTextHBox);
        vBox.getChildren().addAll(imageView, textsVBox, buttonHBox);
        vBox.setAlignment(Pos.TOP_CENTER);
        vBox.setPadding(new Insets(15, 15, 15, 15));


        Scene scene = new Scene(vBox);
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("Resource Selecting View");
        stage.getIcons().add(new Image(getClass().getClassLoader().getResourceAsStream("images/icon_eloy_flash_card_mini.png")));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UTILITY);
        stage.setOnCloseRequest(event -> event.consume()); // disable the closing event
        stage.show();
        stage.setResizable(false);
    }


}
