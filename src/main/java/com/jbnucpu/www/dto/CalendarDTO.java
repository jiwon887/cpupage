package com.jbnucpu.www.dto;

import com.jbnucpu.www.entity.CalendarEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CalendarDTO {

    private String start;
    private String end;
    private String title;

    public CalendarEntity toEntity() {

        CalendarEntity newSchedule = new CalendarEntity();

        newSchedule.setStart(this.start);
        newSchedule.setEnd(this.end);
        newSchedule.setTitle(this.title);

        return newSchedule;
    }
}
