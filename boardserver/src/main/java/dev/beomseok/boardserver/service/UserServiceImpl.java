package dev.beomseok.boardserver.service;

import dev.beomseok.boardserver.domain.User;
import dev.beomseok.boardserver.dto.UserDTO;
import dev.beomseok.boardserver.dto.request.UserSignUpDto;
import dev.beomseok.boardserver.exception.DuplicatedException;
import dev.beomseok.boardserver.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static dev.beomseok.boardserver.utils.SHA256Util.encryptSHA245;

@Service
@RequiredArgsConstructor
@Log4j2
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    @Override
    @Transactional
    public void register(UserSignUpDto userSignUpDto) {
        if(isDuplicatedId(userSignUpDto.getUserId())){
            throw new DuplicatedException("중복된 아이디입니다.");
        }

        userSignUpDto.setPassword(encryptSHA245(userSignUpDto.getPassword()));
        User user = User.createUser(userSignUpDto);
        User savedUser = userRepository.save(user);

        if (savedUser == null){
            log.error("insertMember ERROR! {}", savedUser);
            throw new RuntimeException("insertUser ERROR! 회원가입 메서드를 확인해주세요\n"+"Param : "+ savedUser);
        }
    }

    @Override
    public boolean isDuplicatedId(String id) {
        return userRepository.countByUserId(id) == 1;
    }


    @Override
    public UserDTO login(String id, String password) {
        String cryptoPassword = encryptSHA245(password);
        return userRepository.findByUserIdAndPassword(id, cryptoPassword);
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
            log.error("updatePassword ERROR! {}", memberInfo);
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
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
            log.error("updatePassword ERROR! {}", memberInfo);
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }
    }
}
