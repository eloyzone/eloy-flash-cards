package com.github.eloyzone.eloyflashcards.view;

import javafx.application.Platform;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;

public class MenuBar extends javafx.scene.control.MenuBar
{
    public MenuBar(MainView mainView)
    {
        Menu fileMenu = new Menu("File");
        MenuItem switchProfileMenuItem = new MenuItem("Switch Profile");
        MenuItem settingsMenuItem = new MenuItem("Settings");
        MenuItem importMenuItem = new MenuItem("Import");
        MenuItem exportMenuItem = new MenuItem("Export");
        MenuItem exitMenuItem = new MenuItem("Exit");
        fileMenu.getItems().addAll(switchProfileMenuItem, settingsMenuItem, new SeparatorMenuItem(), importMenuItem, exportMenuItem, new SeparatorMenuItem(), exitMenuItem);

        exitMenuItem.setOnAction(actionEvent -> Platform.exit());
        settingsMenuItem.setOnAction(actionEvent -> new SettingsView());

        Menu helpMenu = new Menu("Help");
        MenuItem aboutMenuItem = new MenuItem("About");
        aboutMenuItem.setOnAction(e -> new AboutView(mainView));
        helpMenu.getItems().addAll(aboutMenuItem);

        getMenus().addAll(fileMenu, helpMenu);
    }
}
