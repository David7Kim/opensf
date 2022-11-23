package com.opensales.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.opensales.app.domain.model.CalendarHistory;

@Repository
public interface CalendarHistoryRepository extends JpaRepository<CalendarHistory,Long>  {
    List<CalendarHistory>findByScheduleDate(String scheduleDate);
}
