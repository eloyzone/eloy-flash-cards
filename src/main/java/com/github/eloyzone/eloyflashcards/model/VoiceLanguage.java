package com.github.eloyzone.eloyflashcards.model;

import com.github.eloyzone.eloyflashcards.util.Initializer;

import java.util.ArrayList;

public class VoiceLanguage
{
    public static String ENGLISH = "en";
    public static String GERMAN = "de";


    public static ArrayList<String> getAllAvailableVoices()
    {
        ArrayList<String> arrayList =  new ArrayList<>();
        arrayList.add(ENGLISH);
        arrayList.add(GERMAN);
        return arrayList;
    }

    public static String getVoicePath(String voiceLanguage, String soundName)
    {
        FlashCard flashCard = Initializer.getFlashCard();

        String soundFilePath = "";
        if (voiceLanguage.toLowerCase().equals(ENGLISH))
            soundFilePath = flashCard.getResourceDirectory().getPath() + "/voices/" + VoiceLanguage.ENGLISH + "/" + soundName + flashCard.getDesiredEnglishAccent();
        if (voiceLanguage.toLowerCase().equals(GERMAN))
            soundFilePath = flashCard.getResourceDirectory().getPath() + "/voices/" + VoiceLanguage.GERMAN + "/" + soundName + ".mp3";

        return soundFilePath;
    }
}
