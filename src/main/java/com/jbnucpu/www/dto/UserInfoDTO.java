package com.jbnucpu.www.dto;

public class UserInfoDTO {
    private String name;
    private String studentNumber;
    private String role;

    public UserInfoDTO(String name, String studentNumber, String role){
        this.name = name;
        this.studentNumber = studentNumber;
        this.role = role;
    }
}
