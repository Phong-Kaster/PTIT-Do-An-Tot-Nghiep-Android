package com.example.do_an_tot_nghiep.Helper;

import java.util.Calendar;
import java.util.TimeZone;

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
}
