package com.github.eloyzone.eloyflashcards.view;

import com.github.eloyzone.eloyflashcards.model.Card;
import javafx.beans.property.IntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class CardsTableView extends TableView<Card>
{
    ObservableList<Card> data;
    IntegerProperty simpleIntegerProperty;

    public CardsTableView(ObservableList<Card> data, IntegerProperty simpleIntegerProperty)
    {
        setMaxHeight(250);

        this.data = data;
        this.simpleIntegerProperty  = simpleIntegerProperty;

        this.getStylesheets().add(getClass().getResource("/styles/CardsTableView.css").toExternalForm());

        Label placeHolderLabel = new Label("No cards are added.");
        placeHolderLabel.getStyleClass().add("place-holder-label");
        setPlaceholder(placeHolderLabel);

        TableColumn faceDataTableColumn = new TableColumn("Face Text");
        faceDataTableColumn.setMinWidth(150);
        faceDataTableColumn.setPrefWidth(150);
        faceDataTableColumn.setCellValueFactory(new PropertyValueFactory<>("faceData"));
        faceDataTableColumn.setSortable(false);

        TableColumn hasTextFieldTableColumn = new TableColumn("Has TextField");
        hasTextFieldTableColumn.setMinWidth(50);
        hasTextFieldTableColumn.setMaxWidth(50);
        hasTextFieldTableColumn.setPrefWidth(50);
        hasTextFieldTableColumn.setCellValueFactory(new PropertyValueFactory<>("needTextFieldInBackCheckBox"));
        hasTextFieldTableColumn.setSortable(false);

        setItems(this.data);
        getColumns().addAll(faceDataTableColumn, hasTextFieldTableColumn);
        setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);


        this.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>()
        {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue)
            {
                simpleIntegerProperty.setValue(newValue);
            }
        });
    }
}
