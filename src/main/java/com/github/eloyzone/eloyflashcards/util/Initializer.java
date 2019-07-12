package com.github.eloyzone.eloyflashcards.util;

import com.github.eloyzone.eloyflashcards.model.FlashCard;

import java.io.File;
import java.util.ArrayList;

public class Initializer
{
    private static Initializer initializer = null;
    private static FlashCard flashCard = null;
    private static ArrayList<String> englishVoiceSoundNames;

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

        if (flashCard != null && flashCard.getDirectoryOfEnglishSound() == null)
        {
            File executedJarLocationFile = new File(Initializer.class.getProtectionDomain().getCodeSource().getLocation().getPath());
            File resourceDirectoryFile = new File(executedJarLocationFile.getParentFile().getPath() + "/resources");
            if (resourceDirectoryFile.exists())
            {
                flashCard.setDirectoryOfEnglishSound(resourceDirectoryFile);
                savedObjectWriterReader.write(flashCard);
                englishVoiceSoundNames = catchAllEnglishVoiceSoundNames(getFlashCard().getDirectoryOfEnglishSound());
            } else
            {
                flashCard.setDirectoryOfEnglishSound(null);
                savedObjectWriterReader.write(flashCard);
            }
        }

        if (flashCard != null && flashCard.getDirectoryOfEnglishSound() != null)
        {
            File file = getFlashCard().getDirectoryOfEnglishSound();
            if (file.exists())
            {
                englishVoiceSoundNames = catchAllEnglishVoiceSoundNames(getFlashCard().getDirectoryOfEnglishSound());
            } else
            {
                File executedJarLocationFile = new File(Initializer.class.getProtectionDomain().getCodeSource().getLocation().getPath());
                File resourceDirectoryFile = new File(executedJarLocationFile.getParentFile().getPath() + "/resources");
                if (resourceDirectoryFile.exists())
                {
                    flashCard.setDirectoryOfEnglishSound(resourceDirectoryFile);
                    savedObjectWriterReader.write(flashCard);
                    englishVoiceSoundNames = catchAllEnglishVoiceSoundNames(getFlashCard().getDirectoryOfEnglishSound());
                } else
                {
                    flashCard.setDirectoryOfEnglishSound(null);
                    savedObjectWriterReader.write(flashCard);
                }
            }
        }
    }


    private static ArrayList<String> catchAllEnglishVoiceSoundNames(final File folder)
    {
        String desiredEnglishAccent = getFlashCard().getDesiredEnglishAccent();
        ArrayList<String> fileNames = new ArrayList<>();
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
        flashCard.setDirectoryOfEnglishSound(resourceDirectoryFile);
        englishVoiceSoundNames = catchAllEnglishVoiceSoundNames(getFlashCard().getDirectoryOfEnglishSound());
        SavedObjectWriterReader savedObjectWriterReader = new SavedObjectWriterReader();
        savedObjectWriterReader.write(flashCard);
    }

    public static FlashCard getFlashCard()
    {
        return flashCard;
    }

    public static ArrayList<String> getEnglishVoiceSoundNames()
    {
        return englishVoiceSoundNames;
    }
}
