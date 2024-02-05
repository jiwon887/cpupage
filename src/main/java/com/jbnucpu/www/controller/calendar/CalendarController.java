package com.jbnucpu.www.controller.calendar;

import com.jbnucpu.www.dto.CalendarDTO;
import com.jbnucpu.www.service.CalendarService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


/**
 * 메인화면 캘린더에 대한 기본적인 API
 * @author 김지훈
 *
 **/

@Controller
public class CalendarController {

    private final CalendarService calendarService;

    public CalendarController(CalendarService calendarService) {

        this.calendarService = calendarService;
    }

    @GetMapping("/calendar")
    public String calendarAdminPage() {

        return "calendar";
    }

    @PostMapping("/calendar")
    public String calendarAddSchedule(CalendarDTO calendarDTO) {

        calendarService.addSchedule(calendarDTO);

        return "redirect:/";
    }
}