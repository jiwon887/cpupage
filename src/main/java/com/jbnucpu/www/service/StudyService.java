package com.jbnucpu.www.service;

import com.jbnucpu.www.dto.StudyDTO;
import com.jbnucpu.www.entity.StudyEnrollEntity;
import com.jbnucpu.www.entity.StudyEntity;
import com.jbnucpu.www.entity.UserEntity;
import com.jbnucpu.www.repository.StudyEnrollRepository;
import com.jbnucpu.www.repository.StudyRepository;
import com.jbnucpu.www.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

/**
 * 스터디 관련 서비스
 * @author     joonlife0901@gmail.com
 * @version    0.2
 */

@Service
public class StudyService {
    private final StudyRepository studyRepository;
    private final StudyEnrollRepository studyEnrollRepository;
    private final AuthService authService;
    private final UserRepository userRepository;
    public StudyService(StudyRepository studyRepository,StudyEnrollRepository studyEnrollRepository,AuthService authService,UserRepository userRepository){
        this.studyRepository = studyRepository;
        this.studyEnrollRepository=studyEnrollRepository;
        this.authService=authService;
        this.userRepository=userRepository;
    }

    /**
     * 모든 스터디엔티티들 리스트로
     * @return this.studyRepository.findAll()
     */
    public List<StudyEntity> getList(){
        System.out.println("studyRepository.findAll()실행----->");
        return this.studyRepository.findAll();
    }
    /**
     * 특정 번호의 엔티티값
     * @return this.studyRepository.findAll()
     */
    public StudyEntity findStudy(Long no){
        return studyRepository.findById(no)
                .orElseThrow(()->new NoSuchElementException("해당 id에 대한 스터디를 찾을 수 없습니다"));
    }
    /**
     * 'studyopen.mustache' 로부터 학번을 제외한 DTO를 받아 저장,
     *  빠진 학번은 authService.getUsername()로 따로 불러옴
     *  이때 authService.getUsername()의 리턴값은 name이 아니라 studentnumber임
     *
     * @param studyDTO 학번을 제외한 DTO객체
     * @return this.studyRepository.findAll()
     */
    public Boolean processOpenStudy(StudyDTO studyDTO){
        if(!authService.isAuthenticated()){
            System.out.println("신청 실패: 로그인 한 사용자만 신청가능");
            return false;
        }
        studyDTO.toEntity_ExceptStudentNumber();
        studyDTO.setStudentNumber(authService.getUsername());
        StudyEntity studyEntity = studyDTO.toEntity();
        studyRepository.save(studyEntity);
        return true;
    }

    /**
     *  특정 스터디에 해당하는 EnrollEntity객체들을 반환
     *  EnrollEntity는 Study-User 간 다대다 관계를 위한 연결테이블임
     * @param no EnrollEntity값들을 가져올 study의 id
     * @return studyEnrollEntities
     */
    public Set<StudyEnrollEntity> findEnrollEntities(Long no){
        return findStudy(no).getStudyEnrollEntities();
    }

    /**
     * 특정 스터디 삭제
     * @param no 삭제할 study의 id
     * @return 삭제성공하면 true 실패하면 false
     */
    public Boolean deleteStudy(Long no){
        if(!authService.isAuthenticated()){
            System.out.println("삭제 실패: 로그인 한 사용자 본인만 삭제 가능");
            return false;
        }
        StudyEntity studyEntity=findStudy(no);
        if(!authService.getUsername().equals(studyEntity.getStudentNumber())){
            System.out.println("삭제 실패: 스터디 개설자 본인만 삭제 가능");
            return false;
        }
        this.studyRepository.delete(studyEntity);
        return true;
    }

    /**
     * 특정 스터디 수정
     * StudentNumber는 수정하지 않음 변경시 삭제 후 재가입
     * @param no 수정할 study의 id
     * @param studyDTO 수정한 studyDTO 데이터
     */
    public void editStudy(StudyDTO studyDTO,Long no){
        StudyEntity s=findStudy(no);
        s.setTitle(studyDTO.getTitle());
        s.setName(studyDTO.getName());
        s.setGrade(studyDTO.getGrade());
        s.setCollege(studyDTO.getCollege());
        s.setDepartment(studyDTO.getDepartment());
        s.setPhoneNumber(studyDTO.getPhoneNumber());
        s.setStudySubject(studyDTO.getStudySubject());
        s.setStudyGoal(studyDTO.getStudyGoal());
        s.setStudyMethod(studyDTO.getStudyMethod());
        s.setStudyReference(studyDTO.getStudyReference());
        s.setStudyTime(studyDTO.getStudyTime());
        s.setStudyPlace(studyDTO.getStudyPlace());
        s.setManagerExperience(studyDTO.getManagerExperience());
        studyRepository.save(s);
    }

    /**
     * 특정 스터디에 가입
     * 중복신청 판단 방식:
     * 현재 로그인된 유저의 학번에 해당하는 userEntity의 id가 EnrollEntity의 UserEntity 컬럼에 존재하는지 확인해서
     * 있으면(isPresent) 중복신청으로 판단
     *
     * 가입시 Study와 User 연결테이블 StudyEnroll 생성하고
     * 맵핑하는 과정을 거침
     * @param no 가입할 스터디 id
     * @return 가입성공하면 true 실패하면 false
     * @todo 맵핑 정상적으로 되었는지 DB로 확인하기
     */
    public Boolean applyStudy(Long no){
        if(!authService.isAuthenticated()){
            System.out.println("신청실패: 로그인 안 한 상태로 신청");
            return false;
        }

        UserEntity userEntity_AvoidDuplicate = userRepository.findByStudentnumber(authService.getUsername());
        Optional<StudyEnrollEntity> userOptional = studyEnrollRepository.findByUserEntityId(userEntity_AvoidDuplicate.getId());
        if(userOptional.isPresent()){
            System.out.println("신청실패: 이미 신청된 사용자");
            return false;
        }

        //이하 주석은 모든 코드 정상동작 확인 시점에서 삭제하겠음
        //1.새로운 엔티티 생성
        StudyEnrollEntity enrollEntity = new StudyEnrollEntity();
        //2.enrollEntityEntity에 userEntity 맵핑
        UserEntity userEntity = userRepository.findByStudentnumber(authService.getUsername());//주의 getUsername()해당메소드 학번반환함 이름 반환안함
        enrollEntity.setUserEntity(userEntity);
        //3.enrollEntityEntity에 studyEntity 맵핑
        enrollEntity.setStudyEntity(findStudy(no));
        studyEnrollRepository.save(enrollEntity);
        //4.UserEnitity에 StudyMemberEntity 맵핑
        /*User는 여러개의 enrollEntity를 가질 수 있어서 add로 추가함, 이게 정석인진 모르겠음*/
        Set<StudyEnrollEntity> tempEnrollEntities_User=userEntity.getStudyEnrollEntities();
        tempEnrollEntities_User.add(enrollEntity);
        userEntity.setStudyEnrollEntities(tempEnrollEntities_User);

        userRepository.save(userEntity);
        return true;
    }

    /**
     * 스터디 탈퇴
     * 삭제방식:
     * 로그인된 현재 유저가 studyEnrollEntity에 있는지 찾아서
     * 해당 studyEnrollEntity삭제하면 study-user간 연결 끊어짐
     * @return 탈퇴성공하면 ture 실패하면 false
     */
    public boolean quitStudy(){
        if(!authService.isAuthenticated()){
            System.out.println("탈퇴실패: 로그인 안 한 상태로 탈퇴");
            return false;
        }
        UserEntity userEntity = userRepository.findByStudentnumber(authService.getUsername());
        Optional<StudyEnrollEntity> userOptional = studyEnrollRepository.findByUserEntityId(userEntity.getId());
        if(userOptional.isEmpty()){
            System.out.println("삭제실패: 삭제할 사용자를 찾을 수 없음");
            return false;
        }

        this.studyEnrollRepository.delete(userOptional.get());
        return true;
    }
}
