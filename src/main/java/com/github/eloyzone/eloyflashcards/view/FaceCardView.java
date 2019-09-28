package com.github.eloyzone.eloyflashcards.view;

import com.github.eloyzone.eloyflashcards.model.VoiceLanguage;
import com.github.eloyzone.eloyflashcards.model.Card;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.io.File;

public class FaceCardView extends VBox
{
    private HBox levelHBox;
    private TextField levelTextField;

    private HBox playImageButtonHBox;
    private ImageButton playImageButton;
    private MediaPlayer mediaPlayer;
    private Media media;

    private TextField faceCardCopyableTextField;


    public FaceCardView(Card card)
    {
        setSpacing(5);
        setPadding(new Insets(0, 0, 10, 0));
        levelHBox =new HBox();
        levelTextField = new TextField();
        levelTextField.setText("Level " + card.getLevel());
        levelHBox.getChildren().addAll(levelTextField);
        HBox.setHgrow(levelTextField, Priority.ALWAYS);
        levelHBox.setId("level-hbox");
        levelTextField.setId("level-text-field");

        getChildren().addAll(levelHBox);

        if (card.isHasVoiceOnFace())
        {
            playImageButtonHBox = new HBox();
            playImageButton = new ImageButton(new Image(getClass().getClassLoader().getResourceAsStream("images/icon_play.png")), 45, 45);
            playImageButton.setOnAction(event -> playMp3File(VoiceLanguage.getVoicePath(card.getVoiceLanguage(), card.getFaceData())));
            playImageButtonHBox.getChildren().add(playImageButton);
            playImageButtonHBox.setAlignment(Pos.CENTER);
            getChildren().addAll(playImageButtonHBox);
        }

        if (card.isFaceDataShown())
        {
            faceCardCopyableTextField = new TextField(card.getFaceData());
            faceCardCopyableTextField.setId("face-card-text-field");
            faceCardCopyableTextField.setEditable(false);
            getChildren().addAll(faceCardCopyableTextField);
        }

        if (card.isHasVoiceOnFace())
            playMp3File(VoiceLanguage.getVoicePath(card.getVoiceLanguage(), card.getFaceData()));
    }

    private void playMp3File(String soundFilePath)
    {
        media = new Media(new File(soundFilePath).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.stop();
        mediaPlayer.setStartTime(new Duration(0)); // on OS-X, we need to set it.
        mediaPlayer.play();
    }
}
