package com.jbnucpu.www.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpDTO {

    private String name;

    private String studentnumber;
    private String password;

    private String college; // 단과대학
    private String department; // 학과
    private Boolean isregistered = false; // 재학 상태 (True => 재학, False => 휴학)
    private String grade;

    private String phonenumber;
}
