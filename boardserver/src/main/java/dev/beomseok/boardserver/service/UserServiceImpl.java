package dev.beomseok.boardserver.service;

import dev.beomseok.boardserver.domain.User;
import dev.beomseok.boardserver.dto.user.UserDTO;
import dev.beomseok.boardserver.dto.user.SignUpRequest;
import dev.beomseok.boardserver.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static dev.beomseok.boardserver.utils.SHA256Util.encryptSHA245;

@Service
@RequiredArgsConstructor
@Log4j2
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    @Override
    @Transactional
    public void register(SignUpRequest userSignUpRequest) {
        if(isDuplicatedId(userSignUpRequest.getUserId())){
            throw new IllegalArgumentException("중복된 아이디입니다.");
        }

        userSignUpRequest.setPassword(encryptSHA245(userSignUpRequest.getPassword()));
        User user = User.createUser(userSignUpRequest);
        User savedUser = userRepository.save(user);

        if (savedUser == null){
            throw new RuntimeException("InsertUser ERROR! 회원가입 메서드를 확인해주세요\n"+"Param : "+ savedUser);
        }
    }

    @Override
    public boolean isDuplicatedId(String id) {
        return userRepository.countByUserId(id) == 1;
    }


    @Override
    public UserDTO login(String id, String password) {
        String cryptoPassword = encryptSHA245(password);
        UserDTO userInfo = userRepository.findByUserIdAndPassword(id, cryptoPassword);

        if(userInfo == null){
            throw new IllegalArgumentException("일치하는 유저 정보가 없습니다.");
        }

        return userInfo;
    }

    @Override
    public UserDTO getUserInfo(String userId) {
        return userRepository.findByUserId(userId);
    }


    @Override
    @Transactional
    public void updatePassword(String id, String beforePassword, String afterPassword) {
        String cryptoPassword = encryptSHA245(beforePassword);
        User memberInfo = userRepository.findAllInfoByUserIdAndPassword(id,cryptoPassword);

        if(memberInfo != null){
            memberInfo.setPassword(encryptSHA245(afterPassword));
        } else {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
    }

    @Override
    @Transactional
    public void delete(String id, String password) {
        String cryptoPassword = encryptSHA245(password);
        User memberInfo = userRepository.findAllInfoByUserIdAndPassword(id,cryptoPassword);

        if(memberInfo != null){
            userRepository.delete(memberInfo);
        } else {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
    }
}
