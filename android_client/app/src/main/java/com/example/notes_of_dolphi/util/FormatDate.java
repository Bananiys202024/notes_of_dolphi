package com.example.notes_of_dolphi.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

public class FormatDate {
    public static String get_in_proper_format(String joining_date) throws ParseException {


        try
        {
            SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM d HH:mm:ss zzz yyyy", Locale.ENGLISH);

            Date date = sdf.parse(joining_date);

            SimpleDateFormat sdf_new = new SimpleDateFormat("dd-MMM yyyy");

            return sdf_new.format(date).replace("-", " ");
        }
        catch(ParseException e)
        {
            try
            {
                String month  = joining_date.substring(4,8);
                String number = joining_date.substring(8,10);
                String year   = joining_date.substring(24);

                return month +" "+ number + year;
            }
            catch(Exception ex)
            {
                System.out.println("Error----"+e);
            }
            System.out.println("Error----"+e);
        }
        return joining_date;
    }
}