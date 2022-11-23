package com.opensales.app.service;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import com.opensales.app.domain.model.Calendar;
import com.opensales.app.domain.model.CalendarHistory;

public interface CalendarService {

    public List<Map<String,Object>> getCalendarList(String monthDate);
    public List<CalendarHistory> getCalendarScheduleList(String sheduleDate);
    public Calendar getCalendar(String scheduleId);
    public Boolean insertSchedule(Map<String,Object> params) throws ParseException;
    public Boolean updateSchedule(Map<String,Object> params) throws ParseException;
    public void deleteSchedule(Long scheduleId);
}
