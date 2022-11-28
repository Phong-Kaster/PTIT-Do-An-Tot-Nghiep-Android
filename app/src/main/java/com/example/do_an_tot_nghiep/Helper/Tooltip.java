package com.example.do_an_tot_nghiep.Helper;

import android.annotation.SuppressLint;
import android.content.Context;

import com.example.do_an_tot_nghiep.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

/**
 * @since 23-11-2022
 * this class is written to wrap some useful functions
 */
public class Tooltip {

    /**
     * @since 23-11-2022
     * @return String today with format yyyy-MM-dd.
     * For example, 2022-05-01
     */
    public static String getToday()
    {
        TimeZone timeZone = TimeZone.getTimeZone("Asia/Ho_Chi_Minh");
        Calendar calendar = Calendar.getInstance(timeZone);

        int date = calendar.get(Calendar.DATE);
        int month = calendar.get(Calendar.MONTH) + 1;
        int year = calendar.get(Calendar.YEAR);

        return year + "-" + month + "-" + date;
    }

    /**
     * @since 24-11-2022
     * @return String beautifier datetime
     * For instance, 2022-11-24 09:57:53 => 09:57 T5, 24-11-2022
     */
    @SuppressLint("SimpleDateFormat")
    public static String beautifierDatetime(Context context, String input)
    {
        if( input.length() != 19)
        {
            return "Tooltip - beautifierDatetime - error: value is not valid " + input.length();
        }
        String output = "";

        String dateInput = input.substring(0,10);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        String dateOutput = "";
        DateFormat dateFormat = new SimpleDateFormat("EE, dd-MM-yyyy");
        try
        {
            Date dateFormatted = formatter.parse(dateInput);
            assert dateFormatted != null;
            dateOutput = dateFormat.format(dateFormatted);
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }

        String timeOutput = input.substring(11, 16);

        output = dateOutput + " " +context.getString(R.string.at) + " " + timeOutput;

        return output;
    }

    /**
     * Get a diff between two dates
     * @param date1 the oldest date
     * @param date2 the newest date
     * @param timeUnit the unit in which you want the diff
     * @return the diff value, in the provided unit
     */
    public static long getDateDifference(Date date1, Date date2, TimeUnit timeUnit) {
        long diffInMillies = date2.getTime() - date1.getTime();
        return timeUnit.convert(diffInMillies,TimeUnit.MILLISECONDS);
    }
}
