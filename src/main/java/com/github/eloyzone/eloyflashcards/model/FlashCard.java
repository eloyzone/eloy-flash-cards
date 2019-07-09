package com.github.eloyzone.eloyflashcards.model;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;

public class FlashCard implements Serializable
{
    private File directoryOfEnglishSound;

    private ArrayList<Deck> decks;

    public FlashCard(File directoryOfEnglishSound)
    {
        this.directoryOfEnglishSound = directoryOfEnglishSound;
        this.decks = new ArrayList<>();
    }

    public ArrayList<Deck> getDecks()
    {
        return decks;
    }

    public void setDecks(ArrayList<Deck> decks)
    {
        this.decks = decks;
    }

    public File getDirectoryOfEnglishSound()
    {
        return directoryOfEnglishSound;
    }

    public void setDirectoryOfEnglishSound(File directoryOfEnglishSound)
    {
        this.directoryOfEnglishSound = directoryOfEnglishSound;
    }
}
