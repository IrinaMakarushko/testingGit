package com.exadel.borsch.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;

@Service
public class CalendarService {

    public String getDataFirstDayOfWeek(String date){
        Calendar  calendar = getCalenrarFromStr(date);
        calendar.add(Calendar.DATE,(calendar.get(Calendar.DAY_OF_WEEK)!=1)?
                Calendar.MONDAY-calendar.get(Calendar.DAY_OF_WEEK):
                -6);
        String result = calendar.get(Calendar.YEAR)+"-"+(calendar.get(Calendar.MONTH)+1)+"-"
                +calendar.get(Calendar.DATE);
        return result;
    }
    private Calendar getCalenrarFromStr(String date){
        String [] dateElemenst = date.split("-");
        Calendar  calendar = Calendar.getInstance();
        calendar.set(Integer.parseInt(dateElemenst[0]),
                Integer.parseInt(dateElemenst[1])-1,
                Integer.parseInt(dateElemenst[2]));
        return calendar;
    }
    public String getNextWeek(String date){
        Calendar  calendar = getCalenrarFromStr(date);
        calendar.add(Calendar.DATE, 7);
        String result = calendar.get(Calendar.YEAR)+"-"+(calendar.get(Calendar.MONTH)+1)+"-"
                +calendar.get(Calendar.DATE);
        return result;
    }
    public String getPrevWeek(String date){
        Calendar  calendar = getCalenrarFromStr(date);
        calendar.add(Calendar.DATE, -7);
        String result = calendar.get(Calendar.YEAR)+"-"+(calendar.get(Calendar.MONTH)+1)+"-"
                +calendar.get(Calendar.DATE);
        return result;
    }
}
