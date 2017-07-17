package com.taoxue.event;

import com.prolificinteractive.materialcalendarview.CalendarDay;

/**
 * Created by Administrator on 2017/1/4.
 */

public class DateEvent {
    private CalendarDay calendarDay;

    public DateEvent(CalendarDay calendarDay) {
        this.calendarDay = calendarDay;
    }

    public CalendarDay getCalendarDay() {
        return calendarDay;
    }

    public void setCalendarDay(CalendarDay calendarDay) {
        this.calendarDay = calendarDay;
    }
}
