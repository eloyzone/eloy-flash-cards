package com.github.eloyzone.eloyflashcards.model;

import com.github.eloyzone.eloyflashcards.util.DateUtil;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

public class Card implements Serializable
{
    private static final long serialVersionUID = 935977773390644018L;
    private long id;
    private boolean hasTextFieldsOnBack;
    private boolean hasVoiceOnFace;
    private boolean faceDataShown;
    private String faceData;
    private ArrayList<String> backData;
    private String descriptionBack;
    private Deck deck;
    private String voiceLanguage;
    private int level;
    private LocalDate lastReviewDate;

    public Card(Deck deck, String faceData, ArrayList<String> backData, String descriptionBack, boolean hasTextFieldsOnBack,
                boolean faceDataShown, boolean hasVoiceOnFace, String voiceLanguage)
    {
        this.deck = deck;
        this.faceData = faceData;
        this.backData = backData;
        this.descriptionBack = descriptionBack;
        this.faceDataShown = faceDataShown;
        this.hasTextFieldsOnBack = hasTextFieldsOnBack;
        this.hasVoiceOnFace = hasVoiceOnFace;
        this.voiceLanguage = voiceLanguage;
        this.level = 1;
        this.lastReviewDate = DateUtil.getTodayDate();
    }


    public void setKnown(boolean known)
    {
        this.lastReviewDate = DateUtil.getTodayDate();
        this.deck.refreshCardLevel(this, known);
    }

    private void readObject(ObjectInputStream input) throws IOException, ClassNotFoundException
    {
        // deserialize the non-transient data members first;
        input.defaultReadObject();

        if (this.hasVoiceOnFace && this.getVoiceLanguage() == null)
        {
            this.setVoiceLanguage(VoiceLanguage.ENGLISH);
        }
    }

    public String getFaceData()
    {
        return faceData;
    }

    public void setFaceData(String faceData)
    {
        this.faceData = faceData;
    }

    public ArrayList<String> getBackData()
    {
        return backData;
    }

    public void setBackData(ArrayList<String> backData)
    {
        this.backData = backData;
    }

    public String getDescriptionBack()
    {
        return descriptionBack;
    }

    public void setDescriptionBack(String descriptionBack)
    {
        this.descriptionBack = descriptionBack;
    }

    public boolean isHasTextFieldsOnBack()
    {
        return hasTextFieldsOnBack;
    }

    public void setHasTextFieldsOnBack(boolean hasTextFieldsOnBack)
    {
        this.hasTextFieldsOnBack = hasTextFieldsOnBack;
    }

    public boolean isHasVoiceOnFace()
    {
        return hasVoiceOnFace;
    }

    public void setHasVoiceOnFace(boolean hasVoiceOnFace)
    {
        this.hasVoiceOnFace = hasVoiceOnFace;
    }

    public Deck getDeck()
    {
        return deck;
    }

    public void setDeck(Deck deck)
    {
        this.deck = deck;
    }

    public boolean isFaceDataShown()
    {
        return faceDataShown;
    }

    public void setFaceDataShown(boolean faceDataShown)
    {
        this.faceDataShown = faceDataShown;
    }

    public String getVoiceLanguage()
    {
        return voiceLanguage;
    }

    public void setVoiceLanguage(String voiceLanguage)
    {
        this.voiceLanguage = voiceLanguage;
    }

    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public int getLevel()
    {
        return level;
    }

    public void setLevel(int level)
    {
        this.level = level;
    }

    public LocalDate getLastReviewDate()
    {
        return lastReviewDate;
    }

    public void setLastReviewDate(LocalDate lastReviewDate)
    {
        this.lastReviewDate = lastReviewDate;
    }
}
