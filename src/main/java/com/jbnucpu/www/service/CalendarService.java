package com.jbnucpu.www.service;

import com.jbnucpu.www.dto.CalendarDTO;
import com.jbnucpu.www.entity.CalendarEntity;
import com.jbnucpu.www.repository.CalendarRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CalendarService {

    private final CalendarRepository calendarRepository;

    public CalendarService(CalendarRepository calendarRepository) {

        this.calendarRepository = calendarRepository;
    }

    //create new schedule logic
    public void addSchedule(CalendarDTO calendarDTO) {

        CalendarEntity newSchedule = calendarDTO.toEntity();

        calendarRepository.save(newSchedule);
    }

    //read all schedule
    public List<CalendarEntity> findAllSchedule() {

        return calendarRepository.findAll();
    }
}
