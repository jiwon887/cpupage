package com.jbnucpu.www.service;

import com.jbnucpu.www.dto.CalendarDTO;
import com.jbnucpu.www.entity.CalendarEntity;
import com.jbnucpu.www.repository.CalendarRepository;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class CalendarService {

    private final CalendarRepository calendarRepository;

    public CalendarService(CalendarRepository calendarRepository) {

        this.calendarRepository = calendarRepository;
    }

    //add one day logic
    private String addOneDayForEnd(String end) {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = format.parse(end);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, 1);

        date = new Date(calendar.getTimeInMillis());


        return format.format(date);
    }

    //create new schedule logic
    public void addSchedule(CalendarDTO calendarDTO) {

        String calculateEndDate = addOneDayForEnd(calendarDTO.getEnd());
        calendarDTO.setEnd(calculateEndDate);
        CalendarEntity newSchedule = calendarDTO.toEntity();

        calendarRepository.save(newSchedule);
    }

    //read all schedule
    public List<CalendarEntity> findAllSchedule() {

        return calendarRepository.findAll();
    }
}
