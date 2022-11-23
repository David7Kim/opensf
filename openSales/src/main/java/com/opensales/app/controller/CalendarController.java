package com.opensales.app.controller;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.opensales.app.domain.model.Calendar;
import com.opensales.app.domain.model.CalendarHistory;
import com.opensales.app.service.CalendarService;
import com.opensales.app.utils.DataTypeConverter;

@Controller
public class CalendarController {
    @Autowired
    CalendarService calendarService;
    @Autowired
    DataTypeConverter converter;
    private static final Logger logger = LoggerFactory.getLogger(CalendarController.class);
    
    /**
      * @Method Name : calendarList
      * @작성일 : 2022. 7. 13.
      * @작성자 : tykim
      * @변경이력 : 
      * @Method 설명 :
      * @param monthDate
      * @return
      */
    @ResponseBody
    @RequestMapping(value = "/calendar/list", method = RequestMethod.GET)
    public List<Map<String,Object>> calendarList(@RequestBody String monthDate) {
        
        List<Map<String,Object>> monthDateList = calendarService.getCalendarList(monthDate);
        
        return monthDateList;
    }
    
    /**
      * @Method Name : calendarScheduleList
      * @작성일 : 2022. 7. 13.
      * @작성자 : tykim
      * @변경이력 : 
      * @Method 설명 :
      * @param session
      * @param scheduleDate
      * @return
      */
    @ResponseBody
    @RequestMapping(value="/calendar/list/{date}" , method =RequestMethod.GET)
    public List<CalendarHistory> calendarScheduleList ( @PathVariable("date") String scheduleDate){
        logger.info("calendarScheduleList()  scheduleDate : {} ", scheduleDate);
        
        List<CalendarHistory> scheduleList = calendarService.getCalendarScheduleList(scheduleDate);
        
        logger.info("calendarScheduleList()  scheduleList : {} ", scheduleList.toArray());
        
        return scheduleList;
        
    }
    
    @ResponseBody 
    @RequestMapping(value ="/calendar/add/schedule" , method = RequestMethod.POST)
    public String addSchedule(HttpServletRequest session , @RequestBody HashMap<String,Object> params) throws ParseException {
        
        if (calendarService.insertSchedule(params)) {
            return "SUCCESS";
        }else {
            return "FAIL";
        }
    }
    /**
      * @Method Name : getScheduleOne
      * @작성일 : 2022. 7. 22.
      * @작성자 : tykim
      * @변경이력 : 
      * @Method 설명 :
      * @param scheduleId
      * @return
      * @throws ParseException
      */
    @ResponseBody 
    @RequestMapping(value ="/calendar/modify/schedule/{scheduleid}" , method = RequestMethod.GET)
    public Calendar getScheduleOne(@PathVariable("scheduleid") String scheduleId) throws ParseException {
       
       return calendarService.getCalendar(scheduleId);
    }
    /**
      * @Method Name : updateSchedule
      * @작성일 : 2022. 7. 22.
      * @작성자 : tykim
      * @변경이력 : 
      * @Method 설명 :
      * @param session
      * @param params
      * @throws ParseException
      */
    @ResponseBody 
    @RequestMapping(value ="/calendar/modify/schedule" , method = RequestMethod.POST)
    public void updateSchedule(HttpServletRequest session , @RequestBody HashMap<String,Object> params) throws ParseException {
        
        calendarService.updateSchedule(params);
    }
    /**
      * @Method Name : deleteSchedule
      * @작성일 : 2022. 10. 27.
      * @작성자 : tykim
      * @변경이력 : 
      * @Method 설명 :
      * @param params
      * @throws ParseException
      */
    @ResponseBody
    @RequestMapping(value="/calendar/delete" , method = RequestMethod.DELETE)
    public void deleteSchedule(@RequestBody Map<String, Object> params)throws ParseException{
        calendarService.deleteSchedule(Long.parseLong(params.get("scheduleId").toString()));
    }
}
