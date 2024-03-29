package com.github.eloyzone.eloyflashcards.view;

import com.github.eloyzone.eloyflashcards.model.Deck;
import com.github.eloyzone.eloyflashcards.util.Initializer;
import com.github.eloyzone.eloyflashcards.util.SavedObjectWriterReader;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.util.Callback;

public class DecksTableView extends TableView<Deck>
{
    private ObservableList<Deck> deckObservableList;

    public DecksTableView(ObservableList<Deck> deckObservableList, StackPane mainStackPane)
    {
        this.deckObservableList = deckObservableList;
        this.getStylesheets().add(getClass().getResource("/styles/DecksTableView.css").toExternalForm());

        Label placeHolderLabel = new Label("No decks are created.");
        placeHolderLabel.getStyleClass().add("place-holder-label");
        setPlaceholder(placeHolderLabel);

        TableColumn deckNameTableColumn = new TableColumn("Deck");
        deckNameTableColumn.setId("deckNameTableColumn");
        deckNameTableColumn.setMinWidth(150);
        deckNameTableColumn.setPrefWidth(150);
        deckNameTableColumn.setSortable(false);
        deckNameTableColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn totalCardsTableColumn = new TableColumn("Total");
        totalCardsTableColumn.setMinWidth(50);
        totalCardsTableColumn.setMaxWidth(50);
        totalCardsTableColumn.setPrefWidth(50);
        totalCardsTableColumn.setSortable(false);
        totalCardsTableColumn.setCellValueFactory(new PropertyValueFactory<>("cardsTotalSize"));

        TableColumn toReviewTableColumn = new TableColumn("To Review");
        toReviewTableColumn.setMinWidth(100);
        toReviewTableColumn.setMaxWidth(100);
        toReviewTableColumn.setPrefWidth(100);
        toReviewTableColumn.setSortable(false);
        toReviewTableColumn.setCellValueFactory(new PropertyValueFactory<>("todayCardsCount"));

        TableColumn deckOptionsTableColumn = new TableColumn("");
        deckOptionsTableColumn.setSortable(false);
        deckOptionsTableColumn.setMinWidth(60);
        deckOptionsTableColumn.setPrefWidth(60);
        deckOptionsTableColumn.setMaxWidth(60);

        Callback<TableColumn<Deck, String>, TableCell<Deck, String>> deckOptionsTableColumnCellFactory = new Callback<TableColumn<Deck, String>, TableCell<Deck, String>>()
        {
            @Override
            public TableCell call(final TableColumn<Deck, String> param)
            {
                final TableCell<Deck, String> cell = new TableCell<Deck, String>()
                {
                    final MenuItem renameMenuItem = new MenuItem("Rename");
                    final MenuItem editCardsMenuItem = new MenuItem("Edit Cards");
                    final MenuItem deleteMenuItem = new MenuItem("Delete");

                    final Image settingsImage = new Image(getClass().getClassLoader().getResourceAsStream("images/icon_settings.png"), 15, 15, false, false);
                    final ImageView settingsImageView = new ImageView(settingsImage);
                    final MenuButton settingsMenuButton = new MenuButton("", settingsImageView, renameMenuItem, editCardsMenuItem, deleteMenuItem);
                    final HBox settingsHBox = new HBox(settingsMenuButton);

                    @Override
                    public void updateItem(String item, boolean empty)
                    {
                        super.updateItem(item, empty);
                        if (empty)
                        {
                            setGraphic(null);
                            setText(null);
                        } else
                        {
                            renameMenuItem.setOnAction(event ->
                            {
                                new NewDeckView(deckObservableList, getIndex());
                                refresh();
                            });

                            editCardsMenuItem.setOnAction(event ->
                            {
                                new EditCardsView(mainStackPane, deckObservableList, getIndex());
                                refresh();
                            });

                            deleteMenuItem.setOnAction(event ->
                            {
                                deckObservableList.remove(getIndex());
                                Initializer.getFlashCard().getDecks().remove(getIndex());
                                new SavedObjectWriterReader().write(Initializer.getFlashCard());
                            });
                            setGraphic(settingsHBox);
                            setText(null);
                        }
                    }
                };
                return cell;
            }
        };

        // row's double click event handler
        setRowFactory(tv ->
        {
            TableRow<Deck> row = new TableRow<>();
            row.setOnMouseClicked(new EventHandler<MouseEvent>()
            {
                @Override
                public void handle(MouseEvent event)
                {
                    if (event.getClickCount() == 2 && (!row.isEmpty()))
                    {
                        Deck deck = row.getItem();
                        new DeckView(mainStackPane, deck);
                    }
                }
            });
            return row;
        });

        deckOptionsTableColumn.setCellFactory(deckOptionsTableColumnCellFactory);

        setItems(this.deckObservableList);
        getColumns().addAll(deckNameTableColumn, totalCardsTableColumn, toReviewTableColumn, deckOptionsTableColumn);
        setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }
}
