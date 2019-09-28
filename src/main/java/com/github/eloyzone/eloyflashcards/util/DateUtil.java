package com.github.eloyzone.eloyflashcards.util;

import java.time.LocalDate;
import java.time.Month;

public class DateUtil
{
    private static LocalDate localDate;

    public static LocalDate getTodayDate()
    {
        if (localDate == null)
        {
//            localDate = LocalDate.now();
            localDate = LocalDate.of(2019, Month.OCTOBER, 6);

        }
        return localDate;
    }
}
