package com.github.eloyzone.eloyflashcards.model;

import com.github.eloyzone.eloyflashcards.util.DateUtil;
import com.github.eloyzone.eloyflashcards.util.Initializer;
import com.github.eloyzone.eloyflashcards.util.SavedObjectWriterReader;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.Month;
import java.time.Period;
import java.util.ArrayList;

public class Deck implements Serializable
{
    private static final long serialVersionUID = 4769203898308667983L;

    private final static int[] LEVEL_TWO_DAYS = {1, 3, 5, 7, 9, 11, 13, 15, 17, 19, 21, 23, 25, 27, 29, 31, 33, 35, 37, 39, 41, 43, 45, 47, 49, 51, 53, 55, 57, 59, 61, 63};
    private final static int[] LEVEL_THREE_DAYS = {2, 6, 10, 14, 18, 22, 26, 30, 38, 42, 46, 50, 54, 58, 62};
    private final static int[] LEVEL_FOUR_DAYS = {4, 13, 20, 29, 36, 45, 52, 61};
    private final static int[] LEVEL_FIVE_DAYS = {12, 28, 44, 60};
    private final static int[] LEVEL_SIX_DAYS = {24, 59};
    private final static int[] LEVEL_SEVEN_DAYS = {56};

    private String name;
    private String description;
    private ArrayList<Card> cards;

    private ArrayList<Card> levelOneCards;
    private ArrayList<Card> levelTwoCards;
    private ArrayList<Card> levelThreeCards;
    private ArrayList<Card> levelFourCards;
    private ArrayList<Card> levelFiveCards;
    private ArrayList<Card> levelSixCards;
    private ArrayList<Card> levelSevenCards;

    private LocalDate startingRoundLocalDate;
    private long totalCardsId;

    public Deck(String name, String description)
    {
        this.name = name;
        this.description = description;
        this.cards = new ArrayList<>();
        this.levelOneCards = new ArrayList<>();
        this.levelTwoCards = new ArrayList<>();
        this.levelThreeCards = new ArrayList<>();
        this.levelFourCards = new ArrayList<>();
        this.levelFiveCards = new ArrayList<>();
        this.levelSixCards = new ArrayList<>();
        this.levelSevenCards = new ArrayList<>();
        this.totalCardsId = 0;
    }

    public void addNewCard(Card card)
    {
        this.totalCardsId++;
        card.setId(totalCardsId);
        this.cards.add(card);
        this.levelOneCards.add(card);
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

    public void refreshCardLevel(Card card, boolean cardKnown)
    {
        switch (card.getLevel())
        {
            case 1:
                if (cardKnown)
                {
                    levelOneCards.remove(card);
                    card.setLevel(2);
                    levelTwoCards.add(card);
                }
                break;
            case 2:
                levelTwoCards.remove(card);
                if (cardKnown)
                {
                    card.setLevel(3);
                    levelThreeCards.add(card);
                } else
                {
                    addCardToLevelOne(card);
                }
                break;
            case 3:
                levelThreeCards.remove(card);
                if (cardKnown)
                {
                    card.setLevel(4);
                    levelFourCards.add(card);
                } else
                {
                    addCardToLevelOne(card);
                }
                break;
            case 4:
                levelFourCards.remove(card);
                if (cardKnown)
                {
                    card.setLevel(5);
                    levelFiveCards.add(card);
                } else
                {
                    addCardToLevelOne(card);
                }
                break;
            case 5:
                levelFiveCards.remove(card);
                if (cardKnown)
                {
                    card.setLevel(6);
                    levelSixCards.add(card);
                } else
                {
                    addCardToLevelOne(card);
                }
                break;
            case 6:
                levelSixCards.remove(card);
                if (cardKnown)
                {
                    card.setLevel(7);
                    levelSevenCards.add(card);
                } else
                {
                    addCardToLevelOne(card);
                }
                break;
            case 7:
                if (!cardKnown)
                {
                    addCardToLevelOne(card);
                }
                break;
        }
        new SavedObjectWriterReader().write(Initializer.getFlashCard());
    }

    private void addCardToLevelOne(Card card)
    {
        card.setLevel(1);
        levelOneCards.add(card);
    }


    public ArrayList<Card> getAllCards()
    {
        ArrayList<Card> cards = new ArrayList<>();
        cards.addAll(levelOneCards);
        cards.addAll(levelTwoCards);
        cards.addAll(levelThreeCards);
        cards.addAll(levelFourCards);
        cards.addAll(levelFiveCards);
        cards.addAll(levelSixCards);
        cards.addAll(levelSevenCards);
        return cards;
    }

    public ArrayList<Card> getTodayCards()
    {
        ArrayList<Card> cardsToReviewArrayList = new ArrayList<>();
        if (startingRoundLocalDate == null)
        {
            startingRoundLocalDate = DateUtil.getTodayDate();
        }

        int daysDuration = Period.between(startingRoundLocalDate, DateUtil.getTodayDate()).getDays() + 1;
        cardsToReviewArrayList.addAll(getTodayNotReviewedCards(levelOneCards));

        if (daysDuration < 64)
        {
            if (isInThisLevel(LEVEL_TWO_DAYS, daysDuration))
                cardsToReviewArrayList.addAll(getTodayNotReviewedCards(levelTwoCards));
            if (isInThisLevel(LEVEL_THREE_DAYS, daysDuration))
                cardsToReviewArrayList.addAll(getTodayNotReviewedCards(levelThreeCards));
            if (isInThisLevel(LEVEL_FOUR_DAYS, daysDuration))
                cardsToReviewArrayList.addAll(getTodayNotReviewedCards(levelFourCards));
            if (isInThisLevel(LEVEL_FIVE_DAYS, daysDuration))
                cardsToReviewArrayList.addAll(getTodayNotReviewedCards(levelFiveCards));
            if (isInThisLevel(LEVEL_SIX_DAYS, daysDuration))
                cardsToReviewArrayList.addAll(getTodayNotReviewedCards(levelSixCards));
            if (isInThisLevel(LEVEL_SEVEN_DAYS, daysDuration))
                cardsToReviewArrayList.addAll(getTodayNotReviewedCards(levelSevenCards));
        } else
        {
            startingRoundLocalDate = DateUtil.getTodayDate();
        }

        return cardsToReviewArrayList;
    }

    private boolean isInThisLevel(int dayLevels[], int passedDurationDay)
    {
        for (int dayLevel : dayLevels)
        {
            if (dayLevel == passedDurationDay) return true;
        }
        return false;
    }

    private ArrayList<Card> getTodayNotReviewedCards(ArrayList<Card> cards)
    {
        LocalDate todayLocalDate = DateUtil.getTodayDate();

        ArrayList<Card> toReviewCardsArrayList = new ArrayList<>();
        for (Card card : cards)
        {
            if (card.getLastReviewDate() != null && !todayLocalDate.isEqual(card.getLastReviewDate()))
                toReviewCardsArrayList.add(card);
        }
        return toReviewCardsArrayList;
    }

    public int getLevelOneCardsSize()
    {
        return levelOneCards.size();
    }

    public int getLevelTwoCardsSize()
    {
        return levelTwoCards.size();
    }

    public int getLevelThreeCardsSize()
    {
        return levelThreeCards.size();
    }

    public int getLevelFourCardsSize()
    {
        return levelFourCards.size();
    }

    public int getLevelFiveCardsSize()
    {
        return levelFiveCards.size();
    }

    public int getLevelSixCardsSize()
    {
        return levelSixCards.size();
    }

    public int getLevelSevenCardsSize()
    {
        return levelSevenCards.size();
    }

    public int getCardsTotalSize()
    {
        return  levelOneCards.size() + levelTwoCards.size() + levelThreeCards.size() + levelFourCards.size() + levelFiveCards.size() + levelSixCards.size() + levelSevenCards.size();
    }

    public int getTodayCardsCount()
    {
        return getTodayCards().size();
    }

}
