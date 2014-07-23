package com.exadel.borsch.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by srw on 8/8/13.
 */
public class DataUtil {
    private static DataUtil dataUtil;

    private Calendar calendar;
    private DateFormat dateFormat;

    private DataUtil() {
        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    }

    public static DataUtil getInstance() {
        if(dataUtil == null) {
            dataUtil = new DataUtil();
        }
        return dataUtil;
    }


    public String getMondayOfWeek(Integer weekDeviation) {
        calendar.add(Calendar.WEEK_OF_MONTH, weekDeviation);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        String formatedData = dateFormat.format(calendar.getTime());
        calendar.add(Calendar.WEEK_OF_MONTH, -weekDeviation);
        return formatedData;
    }


}
