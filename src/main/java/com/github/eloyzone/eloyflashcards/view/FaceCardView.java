package com.github.eloyzone.eloyflashcards.view;

import com.github.eloyzone.eloyflashcards.model.Card;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;

public class FaceCardView extends VBox
{
    private ImageButton playImageButton;

    public FaceCardView(Card card)
    {
        setAlignment(Pos.TOP_CENTER);
        setSpacing(10);
        setPadding(new Insets(10, 0, 10, 0));

        if (card.isHasVoiceOnFace())
        {
            playImageButton = new ImageButton(new Image(getClass().getClassLoader().getResourceAsStream("images/icon_play.png")), 45, 45);
            getChildren().addAll(playImageButton);
        }

        if (card.isFaceDataShown())
        {
            TextField copyableTextField = new TextField(card.getFaceData());
            copyableTextField.setStyle("-fx-font-size: 2em; -fx-background-color: transparent ;");
            copyableTextField.setEditable(false);
            copyableTextField.setAlignment(Pos.CENTER);
            getChildren().addAll(copyableTextField);
        }
    }
}
