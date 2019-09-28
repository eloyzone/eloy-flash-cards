package com.github.eloyzone.eloyflashcards.view;

import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ImageButton extends Button
{
    private final String STYLE_NORMAL = "-fx-background-color: transparent; -fx-padding: 2, 2, 2, 2;";
    private final String STYLE_PRESSED = "-fx-background-color: transparent; -fx-padding: 3 1 1 3;";
    private final String HOVER_MOUSE_CURSOR = "-fx-cursor: hand;" + STYLE_NORMAL;

    private ImageView imageView;
    public ImageButton(Image originalImage, double height, double width)
    {
        imageView = new ImageView(originalImage);
        imageView.setFitHeight(height);
        imageView.setFitWidth(width);
        imageView.setPreserveRatio(true);
        setGraphic(imageView);
        setStyle(STYLE_NORMAL);

        setOnMousePressed(event -> setStyle(STYLE_PRESSED));
        setOnMouseReleased(event -> setStyle(STYLE_NORMAL));
        setOnMouseEntered(event -> setStyle(HOVER_MOUSE_CURSOR));
        setOnMouseEntered(event -> setStyle(HOVER_MOUSE_CURSOR));
    }

    public ImageButton(Image originalImage, double height, double width, ContentDisplay contentDisplay, String buttonText, String tooltip)
    {
        this(originalImage, height, width);

        if (tooltip != null && tooltip.length() > 0)
            setTooltip(new Tooltip(tooltip));

        if (buttonText != null && buttonText.length() > 0)
            setText(buttonText);

        if (contentDisplay != null)
            setContentDisplay(contentDisplay);
    }
}