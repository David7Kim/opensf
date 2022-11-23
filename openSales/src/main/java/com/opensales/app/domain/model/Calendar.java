package com.opensales.app.domain.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="open_sf_calendar")
public class Calendar {
    
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    
    private long id;
    
    //스케줄 제목
    @Column(name="schedule_title")
    private String scheduleTitle;
    
    //스케줄 작성자 user Id
    @ManyToOne
    @JoinColumn(name="user_profile_id")
    private User scheduleUserId;
    
   /* @OneToOne(mappedBy ="calendarUserProfile" , cascade= CascadeType.ALL , orphanRemoval= true)
    private UserProfile userProfile;*/
    
    //스케줄 작성자
    @Column(name="schedule_owner")
    private String scheduleMan;
    
    //스케줄 시작일
    @Column(name="start_date")
    private Date startDate;
    
    //스케줄 시작 시간
    @Column(name="start_time")
    private String startTime;
    
    //스케줄 종료일 
    @Column(name="end_date")
    private Date endDate;
    
    //스케줄 종료 시간
    @Column(name="end_time")
    private String endTime;
    
    //스케줄 내용
    @Column(name="contents")
    private String contents;
    
    //스케줄 참석자
    @Column(name="attendence")
    private String attendence;
    
    //스케줄 생성일
    @Temporal(value=TemporalType.TIMESTAMP)
    @Column(name="created_date" , updatable=false)
    private Date createdDate;

    //스케줄 수정일
    @Temporal(value=TemporalType.TIMESTAMP)
    @Column(name="updated_date")
    private Date updatedDate;
    
    @OneToMany(mappedBy ="schedule_calendarid" , cascade= CascadeType.ALL , orphanRemoval= true)
    private Set<CalendarHistory> calendarHistory = new HashSet<CalendarHistory>();
   
    @PrePersist
    protected void onCreate() {
        updatedDate = createdDate = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedDate = new Date();
    }

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

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public User getScheduleUserId() {
        return scheduleUserId;
    }

    public void setScheduleUserId(User scheduleUserId) {
        this.scheduleUserId = scheduleUserId;
    }
/*    public Long getScheduleUserId() {
        return scheduleUserId;
    }
    
    public void setScheduleUserId(Long scheduleUserId) {
        this.scheduleUserId = scheduleUserId;
    }
*/
    public String getAttendence() {
        return attendence;
    }

    public void setAttendence(String attendence) {
        this.attendence = attendence;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
    
}
