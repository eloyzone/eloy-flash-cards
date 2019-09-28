package com.github.eloyzone.eloyflashcards.util;

import com.github.eloyzone.eloyflashcards.model.FlashCard;

import java.io.File;
import java.util.ArrayList;
import java.util.TreeSet;

public class Initializer
{
    private static Initializer initializer = null;
    private static FlashCard flashCard = null;
    private static TreeSet<String> englishVoiceSoundNames;
    private static TreeSet<String> germanVoiceSoundNames;

    public static Initializer initialize()
    {
        if (initializer == null)
        {
            initializer = new Initializer();
            return initializer;
        } else
        {
            return initializer;
        }
    }


    private Initializer()
    {
        SavedObjectWriterReader savedObjectWriterReader = new SavedObjectWriterReader();
        flashCard = savedObjectWriterReader.read();
        if (flashCard == null)
        {
            File executedJarLocationFile = new File(Initializer.class.getProtectionDomain().getCodeSource().getLocation().getPath());
            File resourceDirectoryFile = new File(executedJarLocationFile.getParentFile().getPath() + "/resources");
            if (resourceDirectoryFile.exists()) flashCard = new FlashCard(resourceDirectoryFile);
            else flashCard = new FlashCard(null);

            savedObjectWriterReader.write(flashCard);
        }

        if (flashCard != null && flashCard.getResourceDirectory() == null)
        {
            File executedJarLocationFile = new File(Initializer.class.getProtectionDomain().getCodeSource().getLocation().getPath());
            File resourceDirectoryFile = new File(executedJarLocationFile.getParentFile().getPath() + "/resources");
            if (resourceDirectoryFile.exists())
            {
                flashCard.setResourceDirectory(resourceDirectoryFile);
                savedObjectWriterReader.write(flashCard);
                englishVoiceSoundNames = catchAllEnglishVoiceSoundNames(getFlashCard().getResourceDirectoryEnglishVoices());
                germanVoiceSoundNames = catchAllGermanVoiceSoundNames(getFlashCard().getResourceDirectoryGermanVoices());
            } else
            {
                flashCard.setResourceDirectory(null);
                savedObjectWriterReader.write(flashCard);
            }
        }

        if (flashCard != null && flashCard.getResourceDirectory() != null)
        {
            File file = getFlashCard().getResourceDirectory();
            if (file.exists())
            {
                englishVoiceSoundNames = catchAllEnglishVoiceSoundNames(getFlashCard().getResourceDirectoryEnglishVoices());
                germanVoiceSoundNames = catchAllGermanVoiceSoundNames(getFlashCard().getResourceDirectoryGermanVoices());

            } else
            {
                File executedJarLocationFile = new File(Initializer.class.getProtectionDomain().getCodeSource().getLocation().getPath());
                File resourceDirectoryFile = new File(executedJarLocationFile.getParentFile().getPath() + "/resources");
                if (resourceDirectoryFile.exists())
                {
                    flashCard.setResourceDirectory(resourceDirectoryFile);
                    savedObjectWriterReader.write(flashCard);
                    englishVoiceSoundNames = catchAllEnglishVoiceSoundNames(getFlashCard().getResourceDirectoryEnglishVoices());
                    germanVoiceSoundNames = catchAllGermanVoiceSoundNames(getFlashCard().getResourceDirectoryGermanVoices());

                } else
                {
                    flashCard.setResourceDirectory(null);
                    savedObjectWriterReader.write(flashCard);
                }
            }
        }
    }

    private static TreeSet<String> catchAllGermanVoiceSoundNames(File resourceDirectoryGermanVoices)
    {
        TreeSet<String> fileNames = new TreeSet<>();
        for (final File fileEntry : resourceDirectoryGermanVoices.listFiles())
        {
            if (fileEntry.isDirectory())
            {
                catchAllGermanVoiceSoundNames(fileEntry);
            } else
            {
                fileNames.add(fileEntry.getName().replace(".mp3", ""));
            }
        }
        return fileNames;
    }


    private static TreeSet<String> catchAllEnglishVoiceSoundNames(final File folder)
    {
        String desiredEnglishAccent = getFlashCard().getDesiredEnglishAccent();
        TreeSet<String> fileNames = new TreeSet<>();
        for (final File fileEntry : folder.listFiles())
        {
            if (fileEntry.isDirectory())
            {
                catchAllEnglishVoiceSoundNames(fileEntry);
            } else
            {
                if (fileEntry.getName().contains(desiredEnglishAccent))
                    fileNames.add(fileEntry.getName().replace(desiredEnglishAccent, ""));
            }
        }
        return fileNames;
    }

    public static void setResourceDirectoryFile(File resourceDirectoryFile)
    {
        flashCard.setResourceDirectory(resourceDirectoryFile);
        englishVoiceSoundNames = catchAllEnglishVoiceSoundNames(getFlashCard().getResourceDirectoryEnglishVoices());
        germanVoiceSoundNames = catchAllGermanVoiceSoundNames(getFlashCard().getResourceDirectoryGermanVoices());
        SavedObjectWriterReader savedObjectWriterReader = new SavedObjectWriterReader();
        savedObjectWriterReader.write(flashCard);
    }

    public static FlashCard getFlashCard()
    {
        return flashCard;
    }

    public static TreeSet<String> getEnglishVoiceSoundNames()
    {
        return englishVoiceSoundNames;
    }

    public static TreeSet<String> getGermanVoiceSoundNames()
    {
        return germanVoiceSoundNames;
    }
}
