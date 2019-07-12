package com.github.eloyzone.eloyflashcards.model;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

public class FlashCard implements Serializable
{
    public static final String UK_ACCENT = "_uk_pron.mp3";
    public static final String US_ACCENT = "_us_pron.mp3";

    private static final long serialVersionUID = 6791167886655376469L;
    private File directoryOfEnglishSound;
    private String desiredEnglishAccent = UK_ACCENT;

    private ArrayList<Deck> decks;

    public FlashCard(File directoryOfEnglishSound)
    {
        this.directoryOfEnglishSound = directoryOfEnglishSound;
        this.decks = new ArrayList<>();
    }


    private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException
    {
        in.defaultReadObject();
        if (desiredEnglishAccent == null)
        {
            desiredEnglishAccent = UK_ACCENT;
        }
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

    public String getDesiredEnglishAccent()
    {
        return desiredEnglishAccent;
    }

    public void setDesiredEnglishAccent(String desiredEnglishAccent)
    {
        this.desiredEnglishAccent = desiredEnglishAccent;
    }
}
