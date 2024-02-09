package com.jbnucpu.www.service;


import com.jbnucpu.www.dto.StudyDTO;
import com.jbnucpu.www.dto.UserInfoDTO;
import com.jbnucpu.www.entity.StudyEntity;
import com.jbnucpu.www.entity.UserEntity;
import com.jbnucpu.www.repository.StudyRepository;
import com.jbnucpu.www.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdminService {

    private final UserRepository userRepository;

    private final StudyRepository studyRepository;

    @Autowired
    public AdminService(UserRepository userRepository, StudyRepository studyRepository){

        this.userRepository = userRepository;
        this.studyRepository = studyRepository;
    }



    public List<UserInfoDTO> getAllMembers(){
        List<UserEntity> userEntities = userRepository.findAll();
        List<UserInfoDTO> userInfo = new ArrayList<>();

        for(UserEntity userEntity : userEntities){
            UserInfoDTO userInfoDTO = userEntity.getUserInfo();
            userInfo.add(userInfoDTO);
        }

        return userInfo;
    }


    public List<StudyDTO> getAllStudy(){

        List<StudyEntity> studyEntites = studyRepository.findAll();
        List<StudyDTO> studyDTOS = new ArrayList<>();

        for(StudyEntity studyEntity : studyEntites){
            StudyDTO studyDTO = studyEntity.getStudyInfo();
            studyDTOS.add(studyDTO);
        }

        return studyDTOS;
        }




}
