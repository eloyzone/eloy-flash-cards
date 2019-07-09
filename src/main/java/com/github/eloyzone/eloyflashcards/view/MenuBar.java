package com.github.eloyzone.eloyflashcards.view;

import javafx.application.Platform;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;

public class MenuBar extends javafx.scene.control.MenuBar
{
    public MenuBar()
    {
        Menu fileMenu = new Menu("File");
        MenuItem switchProfileMenuItem = new MenuItem("Switch Profile");
        MenuItem importMenuItem = new MenuItem("Import");
        MenuItem exportMenuItem = new MenuItem("Export");
        MenuItem exitMenuItem = new MenuItem("Exit");
        exitMenuItem.setOnAction(actionEvent -> Platform.exit());
        fileMenu.getItems().addAll(switchProfileMenuItem, new SeparatorMenuItem(), importMenuItem, exportMenuItem, new SeparatorMenuItem(), exitMenuItem);

        Menu helpMenu = new Menu("Help");
        MenuItem aboutMenuItem = new MenuItem("About");
        helpMenu.getItems().addAll(aboutMenuItem);

        getMenus().addAll(fileMenu, helpMenu);
    }
}
