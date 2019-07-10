package com.github.eloyzone.eloyflashcards.view;

import com.github.eloyzone.eloyflashcards.model.Card;
import com.github.eloyzone.eloyflashcards.util.Initializer;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.File;

public class FaceCardView extends VBox
{
    private ImageButton playImageButton;
    private MediaPlayer mediaPlayer;

    public FaceCardView(Card card)
    {
        setAlignment(Pos.TOP_CENTER);
        setSpacing(10);
        setPadding(new Insets(10, 0, 10, 0));

        if (card.isHasVoiceOnFace())
        {
            playImageButton = new ImageButton(new Image(getClass().getClassLoader().getResourceAsStream("images/icon_play.png")), 45, 45);
            getChildren().addAll(playImageButton);
            playImageButton.setOnAction(event -> playMp3File(card.getFaceData()));
        }

        if (card.isFaceDataShown())
        {
            TextField copyableTextField = new TextField(card.getFaceData());
            copyableTextField.setStyle("-fx-font-size: 2em; -fx-background-color: transparent ;");
            copyableTextField.setEditable(false);
            copyableTextField.setAlignment(Pos.CENTER);
            getChildren().addAll(copyableTextField);
        }

        if(card.isHasVoiceOnFace())
            playMp3File(card.getFaceData());
    }

    private void playMp3File(String soundName)
    {
        String soundFilePath = Initializer.getFlashCard().getDirectoryOfEnglishSound().getPath() +"/" + soundName + "_uk_pron.mp3";
        Media media = new Media(new File(soundFilePath).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.stop();
        mediaPlayer.setStartTime(new Duration(0)); // on OS-X, we need to set it.
        mediaPlayer.play();
    }
}
