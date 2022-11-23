package com.opensales.app.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import com.opensales.app.domain.model.Calendar;
import com.opensales.app.domain.model.CalendarHistory;
import com.opensales.app.domain.model.User;
import com.opensales.app.domain.model.UserProfile;
import com.opensales.app.repository.CalendarHistoryRepository;
import com.opensales.app.repository.CalendarRepository;
import com.opensales.app.repository.UserRepository;
import com.opensales.app.utils.DataTypeConverter;

@Service
public class CalendarServiceImpl implements CalendarService {
    private static final Logger logger = LoggerFactory.getLogger(CalendarServiceImpl.class);
   /* private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    
    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }*/

    @Autowired
    CalendarRepository calendarRepository;
    
    @Autowired
    CalendarHistoryRepository calendarHistoryRepository;

    @Autowired
    DataTypeConverter converter;

    @Autowired
    UserRepository userRepository;

    /**
     * @Method Name : getCalendarList
     * @작성일 : 2022. 6. 17.
     * @작성자 : tyKim
     * @변경이력 :
     * @Method 설명 :
     * @return
     */
    @Override
    public List<Map<String, Object>> getCalendarList(String monthDate) {

        List<Map<String, Object>> result = new ArrayList();
        logger.info("getCalendarList() -");

        return result;
    }

    /**
     * @Method Name : insertSchedule
     * @작성일 : 2022. 6. 17.
     * @작성자 : tykim
     * @변경이력 :
     * @Method 설명 :
     * @param params
     * @throws ParseException
     */
    @Override
    public Boolean insertSchedule(Map<String, Object> params) throws ParseException {
        Calendar schedule = new Calendar();
        User user = userRepository.findById(converter.converterStringToLong(params.get("userid").toString()));
        //UserId를 통해서 작성자명을 가져옵니다.
        String writerName =user.getUserName();
        UserProfile userProfile = new UserProfile();
        //예외처리 및 방어코드 넣어두기 
        userProfile.setUserProfileId(user);
        schedule.setContents(params.get("contents").toString()); // 스케줄 내용
        schedule.setScheduleTitle(params.get("title").toString()); // 스케줄 제목
        schedule.setScheduleMan(writerName); // 스케줄 작성자명
        schedule.setScheduleUserId(user); //스케줄 작성자 User Id
        //schedule.setScheduleUserId(userProfile.getUserProfileId().getId()); //스케줄 작성자 User Id
        schedule.setAttendence(params.get("attendence").toString()); //스케줄 참석자
        schedule.setStartDate(converter.converterStringtoDate(params.get("startDate"))); // 스케줄 시작일자
        schedule.setStartTime(params.get("startTime").toString());//스케줄 시작 시간
        schedule.setEndDate(converter.converterStringtoDate(params.get("endDate"))); // 스케줄 종료일자
        schedule.setEndTime(params.get("endTime").toString());// 스케줄 종료 시간 
        calendarRepository.save(schedule);
        //mkaeCalendarHistory 파라미터에 주말,공휴일 포함,제외도 넣기
        return makeCalendarHistory(schedule, writerName, params);
    }

    /**
     * @Method Name : updateSchedule
     * @작성일 : 2022. 6. 17.
     * @작성자 : tykim
     * @변경이력 :
     * @Method 설명 :
     * @param params
     * @throws ParseException 
     */
    @Override
    public Boolean updateSchedule(Map<String, Object> params) throws ParseException {
        //기존에 스케줄 히스토리에 들어갔던 값들을 삭제한다.
        Long scheduleId = Long.parseLong(params.get("id").toString());
        calendarRepository.delete(scheduleId);
        // Calendar 생성자를 새로 생성한다.
        Calendar schedule = new Calendar();
        UserProfile userProfile = new UserProfile();
        //예외처리 및 방어코드 넣어두기
        User user = userRepository.findById(converter.converterStringToLong(params.get("userid").toString()));
        //UserId를 통해서 작성자명을 가져옵니다.
        String writerName = user.getUserName();
        schedule.setContents(params.get("contents").toString()); // 스케줄 내용
        schedule.setScheduleTitle(params.get("title").toString()); // 스케줄 제목
        schedule.setAttendence(params.get("attendence").toString()); //스케줄 참석자
        schedule.setScheduleMan(writerName);                            //스케줄 작성자 
        schedule.setScheduleUserId(user); //스케줄 작성자 User Id
//        schedule.setScheduleUserId(userProfile.getUserProfileId().getId()); //스케줄 작성자 User Id
        schedule.setStartDate(converter.converterStringtoDate(params.get("startDate"))); // 스케줄 시작일자
        schedule.setStartTime(params.get("startTime").toString());//스케줄 시작 시간
        schedule.setEndDate(converter.converterStringtoDate(params.get("endDate"))); // 스케줄 종료일자
        schedule.setEndTime(params.get("endTime").toString());// 스케줄 종료 시간 

        logger.info("updateSchedule() - params : {}", params);
        
        //스케줄 저장
        calendarRepository.save(schedule);
        
        //스케줄 히스토리 생성
        return makeCalendarHistory(schedule, writerName, params);
    }

    /**
      * @Method Name : getCalendarScheduleList
      * @작성일 : 2022. 10. 26.
      * @작성자 : tykim
      * @변경이력 : 
      * @Method 설명 :
      * @param sheduleDate
      * @return
      */
    @Override
    public List<CalendarHistory> getCalendarScheduleList(String sheduleDate) {
        
        List<CalendarHistory> calendar = calendarHistoryRepository.findByScheduleDate(sheduleDate);

        return calendar;
    }

    /**
      * @Method Name : getCalendar
      * @작성일 : 2022. 10. 26.
      * @작성자 : tykim
      * @변경이력 : 
      * @Method 설명 :
      * @param scheduleId
      * @return
      */
    @Override
    public Calendar getCalendar(String scheduleId) {
        Calendar calendar = new Calendar();
        calendar =calendarRepository.findOne(Long.parseLong(scheduleId));
        return calendar;
    }

    /**
      * @Method Name : deleteSchedule
      * @작성일 : 2022. 10. 26.
      * @작성자 : tykim
      * @변경이력 : 
      * @Method 설명 :
      * @param scheduleId
      */
    @Override
    public void deleteSchedule(Long scheduleId){
        //스케줄 삭제
        logger.info("deleteSchedule() - scheduleId : {}", scheduleId);
        calendarRepository.delete(scheduleId);
    }
    
    /**
      * @Method Name : makeCalendarHistory
      * @작성일 : 2022. 10. 26.
      * @작성자 : tykim
      * @변경이력 : 
      * @Method 설명 : startDate ~ endDate 기간만큼 CalendarHistory 테이블에 insert
      * @param schedule
      * @param writerName
      * @param params
      * @throws ParseException
      */
    private Boolean makeCalendarHistory(Calendar schedule,String writerName,Map<String, Object> params) throws ParseException {
        //startDate와 endDate간의 size를 구하여서 startDate를 size만큼 Date 증가 시킨다.
        String startDate = params.get("startDate").toString();
        String endDate = params.get("endDate").toString();
        //startDate와 endDate간의 일수를 구한다.
        Long diffDays = converter.betweenStartAndEnd(startDate, endDate, 4);
        
        java.util.Calendar cal = java.util.Calendar.getInstance();
        DateFormat date = new SimpleDateFormat("yyyy-MM-dd");
        Boolean msg = false;
        
        //calendar table , calendar_history table insert
        try {
            //반복문을 돌려서 scheduleDate를 넣어준다. 
            for (int i= 0; i<=diffDays; i++) {
                CalendarHistory history = new CalendarHistory();
                
                cal.setTime(converter.converterStringtoDate(params.get("startDate")));
                cal.add(java.util.Calendar.DATE, i);
                history.setStartDate(converter.converterStringtoDate(params.get("startDate")));
                history.setEndDate(converter.converterStringtoDate(params.get("endDate")));
                history.setScheduleMan(writerName);
                history.setScheduleUserId(converter.converterStringToLong(params.get("userid").toString()));
                history.setScheduleTitle(params.get("title").toString());
                history.setSchedule_calendarid(schedule);
                history.setSchduleDate(date.format(cal.getTime()));
                calendarHistoryRepository.save(history);
            }
            logger.info("makeCalendarHistory() - params : {} , case : {}", params);
            msg = true;
        } catch (Exception e) {
            logger.info("makeCalendarHistory Exception() ");
        }
        return msg ;
    }
}
