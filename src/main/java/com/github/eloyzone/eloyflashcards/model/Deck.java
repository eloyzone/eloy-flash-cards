package com.github.eloyzone.eloyflashcards.model;

import com.github.eloyzone.eloyflashcards.util.Initializer;
import com.github.eloyzone.eloyflashcards.util.SavedObjectWriterReader;

import java.io.Serializable;
import java.util.ArrayList;

public class Deck implements Serializable
{
    private String name;
    private String description;
    private ArrayList<Card> cards;
    private int newCardCount;
    private int learnedCardCount;
    private int notLearnedCardCount;

    public Deck(String name, String description)
    {
        this.name = name;
        this.description = description;
        this.cards = new ArrayList<>();
        this.newCardCount = 0;
        this.learnedCardCount = 0;
        this.notLearnedCardCount = 0;
    }

    public void addNewCard(Card card)
    {
        this.cards.add(card);
        this.newCardCount++;
        new SavedObjectWriterReader().write(Initializer.getFlashCard());
    }

    public void refreshReport()
    {
        this.newCardCount = 0;
        this.learnedCardCount = 0;
        this.notLearnedCardCount = 0;

        for (Card card : cards)
        {
            if(card.isNewCard())
            {
                newCardCount++;
                continue;
            }

            if(card.isKnown())
            {
                learnedCardCount++;
                continue;
            }
            else
            {
                notLearnedCardCount++;
                continue;
            }
        }
        new SavedObjectWriterReader().write(Initializer.getFlashCard());
    }

    public int getTotalCardNumber()
    {
        return cards.size();
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public ArrayList<Card> getCards()
    {
        return cards;
    }

    public void setCards(ArrayList<Card> cards)
    {
        this.cards = cards;
    }

    public int getNewCardCount()
    {
        return newCardCount;
    }

    public void setNewCardCount(int newCardCount)
    {
        this.newCardCount = newCardCount;
    }

    public int getLearnedCardCount()
    {
        return learnedCardCount;
    }

    public void setLearnedCardCount(int learnedCardCount)
    {
        this.learnedCardCount = learnedCardCount;
    }

    public int getNotLearnedCardCount()
    {
        return notLearnedCardCount;
    }

    public void setNotLearnedCardCount(int notLearnedCardCount)
    {
        this.notLearnedCardCount = notLearnedCardCount;
    }
}
