package com.example.dollarchecker.utility;

import java.util.Calendar;

public class CalendarManipulation {
    public static Calendar getStart(Calendar calendar) {
        Calendar calendarStart = (Calendar) calendar.clone();
        calendarStart.add(Calendar.MONTH, -1);
        return calendarStart;
    }

    public static Calendar getStart2(Calendar calendar) {
        Calendar calendarStart = (Calendar) calendar.clone();
        calendarStart.add(Calendar.MONTH, -2);
        return calendarStart;
    }
}
