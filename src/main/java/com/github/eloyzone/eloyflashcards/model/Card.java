package com.github.eloyzone.eloyflashcards.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Card implements Serializable
{
    private static final long serialVersionUID = 935977773390644018L;
    private boolean known;
    private boolean newCard;
    private boolean hasTextFieldsOnBack;
    private boolean hasVoiceOnFace;
    private boolean faceDataShown;
    private String faceData;
    private ArrayList<String> backData;
    private String descriptionBack;
    private Deck deck;

    public Card(Deck deck, String faceData, ArrayList<String> backData, String descriptionBack, boolean hasTextFieldsOnBack, boolean faceDataShown, boolean hasVoiceOnFace)
    {
        this.deck = deck;
        this.faceData = faceData;
        this.backData = backData;
        this.descriptionBack = descriptionBack;
        this.faceDataShown = faceDataShown;
        this.hasTextFieldsOnBack = hasTextFieldsOnBack;
        this.hasVoiceOnFace = hasVoiceOnFace;
        this.known = false;
        this.newCard = true;
    }

    public boolean isKnown()
    {
        return known;
    }

    public void setKnown(boolean known)
    {
        this.known = known;
        this.newCard = false;
        this.deck.refreshReport();
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

    public boolean isNewCard()
    {
        return newCard;
    }

    public boolean isFaceDataShown()
    {
        return faceDataShown;
    }

    public void setFaceDataShown(boolean faceDataShown)
    {
        this.faceDataShown = faceDataShown;
    }
}
