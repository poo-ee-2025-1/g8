package com.ricardo.utils;

import java.time.DayOfWeek;
import java.time.LocalDate;

public class DateUtils {
    public static LocalDate getComecoDaSemana(LocalDate date) {
        return date.minusDays(date.getDayOfWeek().getValue() - DayOfWeek.MONDAY.getValue());
    }

    public static LocalDate[] getSemanaAtual(LocalDate date) {
        LocalDate comeco = getComecoDaSemana(date);
        LocalDate[] semana = new LocalDate[7];

        for(int i = 0; i < 7; i++) {
            semana[i] = comeco.plusDays(i);
        }

        return semana;
    }
}
