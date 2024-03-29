package com.github.eloyzone.eloyflashcards.view;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Side;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.util.LinkedList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;


public class AutoCompleteTextField extends TextField
{
    private SortedSet<String> autoCompleteEntries;
    private ContextMenu autoCompletePopupContextMenu;
    private ObservableList<String> defaultTextFieldStyle;
    private boolean acceptable = false;


    public AutoCompleteTextField(TreeSet<String> autoCompleteEntries)
    {
        super();
        this.autoCompleteEntries = new TreeSet<>(autoCompleteEntries);
        this.autoCompletePopupContextMenu = new ContextMenu();
        this.defaultTextFieldStyle = FXCollections.observableArrayList(getStyleClass());
        onChangeListenerAction();

    }

    private void onChangeListenerAction()
    {
        textProperty().addListener(new ChangeListener<String>()
        {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue)
            {
                newValue = newValue.toLowerCase().trim();
                validate(newValue);

                if (newValue.length() == 0)
                {
                    autoCompletePopupContextMenu.hide();
                } else
                {
                    LinkedList<String> searchResult = new LinkedList<>();
                    searchResult.addAll(autoCompleteEntries.subSet(newValue, newValue + Character.MAX_VALUE));
                    if (autoCompleteEntries.size() > 1)
                    {
                        populatePopup(searchResult);

                        if (!autoCompletePopupContextMenu.isShowing() && isFocused())
                        {
                            autoCompletePopupContextMenu.show(AutoCompleteTextField.this, Side.BOTTOM, 0, 0);
                        }
                    } else
                    {
                        autoCompletePopupContextMenu.hide();
                    }
                }
            }
        });

        focusedProperty().addListener(new ChangeListener<Boolean>()
        {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean aBoolean2)
            {
                autoCompletePopupContextMenu.hide();
            }
        });
    }

    public boolean isAcceptable()
    {
        return acceptable;
    }


    public void switchLanguage(SortedSet<String> autoCompleteEntries)
    {
        this.autoCompleteEntries = new TreeSet<>(autoCompleteEntries);
        this.autoCompletePopupContextMenu = new ContextMenu();
        validate(getText());
    }

    private void populatePopup(List<String> searchResult)
    {
        List<CustomMenuItem> menuItems = new LinkedList<>();
        int maxEntries = 10;
        int count = Math.min(searchResult.size(), maxEntries);
        for (int i = 0; i < count; i++)
        {
            final String searchResultItem = searchResult.get(i);
            Label entryLabel = new Label(searchResultItem);
            CustomMenuItem item = new CustomMenuItem(entryLabel, true);
            item.setOnAction(new EventHandler<ActionEvent>()
            {
                @Override
                public void handle(ActionEvent actionEvent)
                {
                    // add selected result to TextField
                    setText(searchResultItem);
                    positionCaret(getText().length());
                    autoCompletePopupContextMenu.hide();
                }
            });
            menuItems.add(item);
        }
        autoCompletePopupContextMenu.getItems().clear();
        autoCompletePopupContextMenu.getItems().addAll(menuItems);
    }

    private void validate(String input)
    {
        if (autoCompleteEntries.contains(input))
        {
            getStyleClass().clear();
            getStyleClass().addAll(defaultTextFieldStyle);
            acceptable = true;
        } else
        {
            getStyleClass().add("validation-error");
            acceptable = false;
        }
    }
}
