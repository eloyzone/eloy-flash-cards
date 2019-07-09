package com.github.eloyzone.eloyflashcards.view;

import com.sun.deploy.uitoolkit.impl.fx.HostServicesFactory;
import com.sun.javafx.application.HostServicesDelegate;
import javafx.scene.control.Hyperlink;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.StageStyle;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AboutView
{
    public AboutView(MainView mainView)
    {
        HostServicesDelegate hostServices = HostServicesFactory.getInstance(mainView);

        VBox mainVBox = new VBox();
        mainVBox.setId("mainVBox");

        Image image = new Image(getClass().getClassLoader().getResourceAsStream("images/eloy_flash_card_mini.png"));
        ImageView imageView = new ImageView(image);

        VBox textsVBox = new VBox();

        HBox descriptionTextHBox =  new HBox();
        descriptionTextHBox.getStyleClass().add("description-text-hbox");
        Text descriptionText = new Text("This is a program which helps you to remember and learn new English words.");
        descriptionText.wrappingWidthProperty().set(image.getWidth());
        descriptionTextHBox.getChildren().add(descriptionText);

        Hyperlink eloyZoneHyperlink = new Hyperlink("http://eloy.zone");
        Hyperlink githubHyperlink = new Hyperlink("https://github.com/eloyzone/eloy-flash-cards");

        eloyZoneHyperlink.setOnAction(event -> hostServices.showDocument(eloyZoneHyperlink.getText()));
        githubHyperlink.setOnAction(event -> hostServices.showDocument(githubHyperlink.getText()));

        textsVBox.getChildren().addAll(descriptionTextHBox, eloyZoneHyperlink, githubHyperlink);
        mainVBox.getChildren().addAll(imageView, textsVBox);

        Scene scene = new Scene(mainVBox);
        scene.getStylesheets().add(getClass().getResource("/styles/AboutView.css").toExternalForm());
        Stage stage = new Stage();
        stage.setResizable(false);
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("About");

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.getIcons().add(new Image(getClass().getClassLoader().getResourceAsStream("images/icon_eloy_flash_card_mini.png")));
        stage.initStyle(StageStyle.UTILITY);
        stage.show();
    }
}
