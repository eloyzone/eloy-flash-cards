package com.github.eloyzone.eloyflashcards.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Deck implements Serializable
{
    private String name;
    private String description;
    private ArrayList<Card> cards;

    public Deck(String name, String description)
    {
        this.name = name;
        this.description = description;
        this.cards = new ArrayList<>();
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
}
