package com.jbnucpu.www.service;

import com.jbnucpu.www.entity.StudyEntity;
import com.jbnucpu.www.repository.StudyRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StudyEnrollService {

    private final StudyRepository studyRepository;

    public StudyEnrollService(StudyRepository studyRepository){
        this.studyRepository = studyRepository;
    }

    public Optional<StudyEntity> processStudy(Long no){

        return studyRepository.findById(no);
    }
}
