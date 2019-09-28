package com.github.eloyzone.eloyflashcards;

import com.github.eloyzone.eloyflashcards.util.Initializer;
import com.github.eloyzone.eloyflashcards.view.MainView;
import javafx.application.Application;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;

public class Main
{
    public static void main(String[] args)
    {
        Initializer.initialize();
        Application.launch(MainView.class, args);
    }
}
