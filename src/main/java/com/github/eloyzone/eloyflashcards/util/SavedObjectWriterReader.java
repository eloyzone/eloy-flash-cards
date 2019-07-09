package com.github.eloyzone.eloyflashcards.util;

import com.github.eloyzone.eloyflashcards.model.FlashCard;

import java.io.*;

public class SavedObjectWriterReader
{
    private final static String savedObject = "saved.ezfc";

    public void write(FlashCard flashCard)
    {
        FileOutputStream fileOutputStream = null;
        ObjectOutputStream objectOutputStream = null;

        try
        {
            fileOutputStream = new FileOutputStream(new File(savedObject));
            objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(flashCard);
        } catch (FileNotFoundException e)
        {
            System.out.println("File not found");
        } catch (IOException e)
        {
            System.out.println("Error initializing stream");
        } finally
        {
            if (fileOutputStream != null)
            {
                try
                {
                    fileOutputStream.close();
                } catch (IOException e)
                {
                    e.printStackTrace();
                }
            }

            if (objectOutputStream != null)
            {
                try
                {
                    objectOutputStream.close();
                } catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }

    public FlashCard read()
    {
        FileInputStream fileInputStream = null;
        ObjectInputStream objectInputStream = null;
        FlashCard flashCard = null;

        try
        {
            fileInputStream = new FileInputStream(new File(savedObject));
            objectInputStream = new ObjectInputStream(fileInputStream);

            flashCard = (FlashCard) objectInputStream.readObject();

        } catch (FileNotFoundException e)
        {
            System.out.println("File not found");
        } catch (IOException e)
        {
            System.out.println("Error initializing stream");
        } catch (ClassNotFoundException e)
        {
            System.out.println("class has not found exception");
        } finally
        {
            if (fileInputStream != null)
            {
                try
                {
                    fileInputStream.close();
                } catch (IOException e)
                {
                    e.printStackTrace();
                }
            }

            if (objectInputStream != null)
            {
                try
                {
                    objectInputStream.close();
                } catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
        return flashCard;
    }
}
