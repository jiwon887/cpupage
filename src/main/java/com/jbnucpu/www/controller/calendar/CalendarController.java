package com.jbnucpu.www.controller.calendar;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


/**
 * 메인화면 캘린더에 대한 기본적인 API
 * @author 김지훈
 *
 **/

@Controller
public class CalendarController {

    @GetMapping("/calendar")
    public String calendarAdminPage() {

        return "calendar";
    }
}