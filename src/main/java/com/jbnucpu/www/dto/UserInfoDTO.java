package com.jbnucpu.www.dto;

public class UserInfoDTO {
    private String name;
    private String studentNumber;
    private String enroll;

    public UserInfoDTO(String name, String studentNumber, String enroll){
        this.name = name;
        this.studentNumber = studentNumber;
        this.enroll = enroll;
    }
}
