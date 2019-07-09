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
        if(initializer == null)
        {
            initializer = new Initializer();
            return initializer;
        }
        else
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
            File jarFileLocationFile = new File(Initializer.class.getProtectionDomain().getCodeSource().getLocation().getPath());
            File resourceDirectoryFile = new File(jarFileLocationFile.getParentFile().getPath() + "/resources");
            if (resourceDirectoryFile.exists()) flashCard = new FlashCard(resourceDirectoryFile);
            else flashCard = new FlashCard(null);

            savedObjectWriterReader.write(flashCard);
        }

        if (flashCard.getDirectoryOfEnglishSound() != null)
            englishVoiceSoundNames = catchAllEnglishVoiceSoundNames(getFlashCard().getDirectoryOfEnglishSound());
    }



    private static ArrayList<String> catchAllEnglishVoiceSoundNames(final File folder)
    {
        ArrayList<String> fileNames = new ArrayList<>();
        for (final File fileEntry : folder.listFiles())
        {
            if (fileEntry.isDirectory())
            {
                catchAllEnglishVoiceSoundNames(fileEntry);
            } else
            {
                if (!fileEntry.getName().contains("_us_pron.mp3"))
                    fileNames.add(fileEntry.getName().replace("_uk_pron.mp3", ""));
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
