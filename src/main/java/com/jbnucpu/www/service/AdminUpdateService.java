package com.jbnucpu.www.service;


import com.jbnucpu.www.entity.UserEntity;
import com.jbnucpu.www.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class AdminUpdateService {

    static public class ExistNickNameError extends RuntimeException {

        public ExistNickNameError(String message) {
            super(message);
        }
    }

    private final UserRepository userRepository;


    @Autowired
    public AdminUpdateService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public String updateUserRole(String studentNumber, String newRole){
        UserEntity user = userRepository.findByStudentnumber(studentNumber);
        if(user == null){
            throw  new UsernameNotFoundException("해당 사용자를 찾을 수 엄슴");
        }
        user.setRole(newRole);
        userRepository.save(user);

        return "/admin_main";
    }



    @Transactional
    public String adminUpdateMember(String studentNumber, String newPassword, String newName, String newPhoneNumber,
                                    String newNickName){

        UserEntity user = userRepository.findByStudentnumber(studentNumber);


        if (user == null) {
            throw new UsernameNotFoundException("해당 사용자를 찾을 수 없습니다.");
        }

        // 수정하려는 정보가 null이 아니면 업데이트
        if (newPassword != null) {
            user.setPassword(newPassword);
        }
        if (newName != null) {
            user.setName(newName);
        }
        if (newPhoneNumber != null) {
            user.setPhonenumber(newPhoneNumber);
        }
        if (newNickName != null) {
            //닉네임 중복 검사
            if(userRepository.existsByNickname(newNickName)){
                throw new ExistNickNameError("이미 사용 중인 닉네임입니다. 창의력을 발휘하세요.");
            }
            user.setNickname(newNickName);
        }


        userRepository.save(user);

        return ("/admin_main");
    }
}
