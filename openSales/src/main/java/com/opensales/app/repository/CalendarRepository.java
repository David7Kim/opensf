package com.opensales.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.opensales.app.domain.model.Calendar;
@Repository
public interface CalendarRepository extends JpaRepository<Calendar, Long> {
    
//    List<Calendar>findByCreatedDateBetwenn(String startDate , String endDate);
//    List<Calendar>findByStartDate
}
