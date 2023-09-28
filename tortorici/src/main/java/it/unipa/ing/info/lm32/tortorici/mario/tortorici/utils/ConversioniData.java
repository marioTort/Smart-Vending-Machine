package it.unipa.ing.info.lm32.tortorici.mario.tortorici.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class ConversioniData {

    public java.sql.Date fromStringToSqlDate(String utilDate) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        try {

            java.util.Date date = simpleDateFormat.parse(utilDate);
            return new java.sql.Date(date.getTime());

        } catch (ParseException parseException) {
            parseException.printStackTrace();
        }

        return null;

    }

    public java.util.Date fromSqlToUtilDate(java.sql.Date sqlDate) {
        return new java.util.Date(sqlDate.getTime());
    }

}
