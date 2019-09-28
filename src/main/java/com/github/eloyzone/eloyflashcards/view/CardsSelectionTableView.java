package com.github.eloyzone.eloyflashcards.view;

import com.github.eloyzone.eloyflashcards.model.Card;
import javafx.beans.property.IntegerProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class CardsSelectionTableView extends TableView<Card>
{
    private ObservableList<Card> dataObservableList;
    private TableColumn cardFaceDataTableColumn;
    private TableColumn cardLevelTableColumn;

    public CardsSelectionTableView(ObservableList<Card> dataObservableList, IntegerProperty indexOfSelectedCardIntegerProperty)
    {
        setMaxHeight(250);

        this.dataObservableList = dataObservableList;
        setItems(this.dataObservableList);

        this.getStylesheets().add(getClass().getResource("/styles/CardsTableView.css").toExternalForm());

        Label placeHolderLabel = new Label("No cards are added.");
        placeHolderLabel.getStyleClass().add("place-holder-label");
        setPlaceholder(placeHolderLabel);

        cardFaceDataTableColumn= new TableColumn("Face Text");
        cardFaceDataTableColumn.setMinWidth(150);
        cardFaceDataTableColumn.setPrefWidth(150);
        cardFaceDataTableColumn.setCellValueFactory(new PropertyValueFactory<>("faceData"));
        cardFaceDataTableColumn.setSortable(false);

        cardLevelTableColumn = new TableColumn("Level");
        cardLevelTableColumn.setMinWidth(50);
        cardLevelTableColumn.setMaxWidth(50);
        cardLevelTableColumn.setPrefWidth(50);
        cardLevelTableColumn.setCellValueFactory(new PropertyValueFactory<>("level"));
        cardLevelTableColumn.setSortable(false);

        getColumns().addAll(cardFaceDataTableColumn, cardLevelTableColumn);
        setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        this.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> indexOfSelectedCardIntegerProperty.setValue(newValue));
    }
}
