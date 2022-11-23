package com.opensales.app.domain.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="open_sf_calendar_history")
public class CalendarHistory {
    
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;
    
    //스케줄 제목
    @Column(name="schedule_title")
    private String scheduleTitle;
    
    //스케줄 작성자 user Id
    @Column(name="schedule_userid")
    private Long scheduleUserId;
    
    //캘린더 Id
    @ManyToOne
    @JoinColumn(name="calendar_id")
    private Calendar schedule_calendarid;
    
    //스케줄 작성자
    @Column(name="schedule_owner")
    private String scheduleMan;
    
    //스케줄 시작일
    @Column(name="start_date")
    private Date startDate;
    
    //스케줄 종료일 
    @Column(name="end_date")
    private Date endDate;

    //스케줄 Date 
    @Column(name="schedule_date")
    private String scheduleDate;
    
    
    //스케줄 히스토리 생성일
    @Temporal(value=TemporalType.TIMESTAMP)
    @Column(name="created_date" , updatable=false)
    private Date createdDate;
    
    //스케줄 히스토리 수정일
    @Temporal(value=TemporalType.TIMESTAMP)
    @Column(name="updated_date")
    private Date updatedDate;


    public long getId() {
        return id;
    }


    public void setId(long id) {
        this.id = id;
    }


    public String getScheduleTitle() {
        return scheduleTitle;
    }


    public void setScheduleTitle(String scheduleTitle) {
        this.scheduleTitle = scheduleTitle;
    }


    public Long getScheduleUserId() {
        return scheduleUserId;
    }


    public void setScheduleUserId(Long scheduleUserId) {
        this.scheduleUserId = scheduleUserId;
    }


 /*   public Long getSchedule_calendarid() {
        return schedule_calendarid;
    }


    public void setSchedule_calendarid(Long schedule_calendarid) {
        this.schedule_calendarid = schedule_calendarid;
    }*/


    public String getScheduleMan() {
        return scheduleMan;
    }


    public void setScheduleMan(String scheduleMan) {
        this.scheduleMan = scheduleMan;
    }


    public Date getStartDate() {
        return startDate;
    }


    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }


    public Date getEndDate() {
        return endDate;
    }


    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }


    public String getSchduleDate() {
        return scheduleDate;
    }


    public void setSchduleDate(String schduleDate) {
        this.scheduleDate = schduleDate;
    }


    public Date getCreatedDate() {
        return createdDate;
    }


    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }


    public Calendar getSchedule_calendarid() {
        return schedule_calendarid;
    }


    public void setSchedule_calendarid(Calendar schedule_calendarid) {
        this.schedule_calendarid = schedule_calendarid;
//        schedule_calendarid.getId().add(this);
    }


    public Date getUpdatedDate() {
        return updatedDate;
    }


    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

}
