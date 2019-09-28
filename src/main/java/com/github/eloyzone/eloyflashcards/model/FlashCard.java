package com.github.eloyzone.eloyflashcards.model;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

public class FlashCard implements Serializable
{
    private static final long serialVersionUID = 6791167886655376469L;

    public static transient final String UK_ACCENT = "_uk_pron.mp3";
    public static transient final String US_ACCENT = "_us_pron.mp3";

    private File resourceDirectory;
    private String desiredEnglishAccent = UK_ACCENT;

    private ArrayList<Deck> decks;

    public FlashCard(File resourceDirectory)
    {
        this.resourceDirectory = resourceDirectory;
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

    public String getDesiredEnglishAccent()
    {
        return desiredEnglishAccent;
    }

    public void setDesiredEnglishAccent(String desiredEnglishAccent)
    {
        this.desiredEnglishAccent = desiredEnglishAccent;
    }

    public File getResourceDirectory()
    {
        return resourceDirectory;
    }

    public void setResourceDirectory(File resourceDirectory)
    {
        this.resourceDirectory = resourceDirectory;
    }

    public File getResourceDirectoryEnglishVoices()
    {
        File file = new File(resourceDirectory.getPath() + "/voices/" + VoiceLanguage.ENGLISH);
        return file;
    }

    public File getResourceDirectoryGermanVoices()
    {
        File file = new File(resourceDirectory.getPath() + "/voices/" + VoiceLanguage.GERMAN);
        return file;
    }
}
