package dev.beomseok.boardserver.service;

import dev.beomseok.boardserver.dto.UserDTO;
import dev.beomseok.boardserver.exception.DuplicatedException;
import dev.beomseok.boardserver.mapper.UserProfileMapper;
import dev.beomseok.boardserver.utils.SHA256Util;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Date;

import static dev.beomseok.boardserver.utils.SHA256Util.*;

@Service
@RequiredArgsConstructor
@Log4j2
public class UserServiceImpl implements UserService{

    private final UserProfileMapper userProfileMapper;

    @Override
    public void register(UserDTO userProfile) {
        boolean dupIdResult = isDuplicatedId(userProfile.getUserId());
        if(dupIdResult){
            throw new DuplicatedException("중복된 아이디입니다.");
        }

        userProfile.setCreateTime(new Date());
        userProfile.setPassword(encryptSHA245(userProfile.getPassword()));
        int insertCount = userProfileMapper.register(userProfile);

        if (insertCount != 1){
            log.error("insertMember ERROR! {}", userProfile);
            throw new RuntimeException("insertUser ERROR! 회원가입 메서드를 확인해주세요\n"+"Param : "+ userProfile);
        }
    }

    @Override
    public UserDTO login(String id, String password) {
        String cryptoPassword = encryptSHA245(password);
        UserDTO memberInfo = userProfileMapper.findByIdAndPassword(id,cryptoPassword);
        return memberInfo;
    }

    @Override
    public boolean isDuplicatedId(String id) {
        return userProfileMapper.idCheck(id) == 1;
    }

    @Override
    public UserDTO getUserInfo(String userId) {
        return null;
    }

    @Override
    public void updatePassword(String id, String beforePassword, String afterPassword) {
        String cryptoPassword = encryptSHA245(beforePassword);
        UserDTO memberInfo = userProfileMapper.findByIdAndPassword(id,cryptoPassword);

        if(memberInfo != null){
            memberInfo.setPassword(encryptSHA245(afterPassword));
            int insertCount = userProfileMapper.updatePassword(memberInfo);
        } else {
            log.error("updatePassword ERROR! {}", memberInfo);
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }
    }

    @Override
    public void deleteId(String id, String password) {
        String cryptoPassword = encryptSHA245(password);
        UserDTO memberInfo = userProfileMapper.findByIdAndPassword(id,cryptoPassword);

        if(memberInfo != null){
            int deleteCount = userProfileMapper.deleteUserProfile(id);
        } else {
            log.error("updatePassword ERROR! {}", memberInfo);
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }
    }
}
