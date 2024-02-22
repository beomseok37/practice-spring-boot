package dev.beomseok.boardserver.controller;

import dev.beomseok.boardserver.dto.UserDTO;
import dev.beomseok.boardserver.dto.request.UserDeleteRequest;
import dev.beomseok.boardserver.dto.request.UserLoginRequest;
import dev.beomseok.boardserver.dto.request.UserSignUpDto;
import dev.beomseok.boardserver.dto.request.UserUpdatePasswordRequest;
import dev.beomseok.boardserver.dto.response.LoginResponse;
import dev.beomseok.boardserver.service.UserService;
import dev.beomseok.boardserver.utils.SessionUtil;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static dev.beomseok.boardserver.domain.User.UserStatus;

@RestController
@RequestMapping("/users")
@Log4j2
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    static LoginResponse loginResponse = null;

    @PostMapping("sign-up")
    @ResponseStatus(HttpStatus.CREATED)
    public void signUp(@RequestBody UserSignUpDto userSignUpDto){
        if(UserSignUpDto.hasNullDataBeforeSignup(userSignUpDto)){
            throw new RuntimeException("회원가입 정보를 확인해주세요");
        }
        userService.register(userSignUpDto);
    }

    @PostMapping("sign-in")
    public ResponseEntity<LoginResponse> login(@RequestBody UserLoginRequest request,
                            HttpSession session){
        String id = request.getUserId();
        String password=  request.getPassword();
        UserDTO userInfo = userService.login(id,password);

        if(userInfo == null){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } else if(userInfo!=null){
            LoginResponse loginResponse = LoginResponse.success(userInfo);

            if(userInfo.getStatus() == UserStatus.ADMIN){
                SessionUtil.setLoginAdminId(session, id);
            } else {
                SessionUtil.setLoginMemberId(session, id);
            }

            return new ResponseEntity<>(loginResponse, HttpStatus.OK);
        } else {
            throw new RuntimeException("Login Error! 유저 정보가 없거나 지원되지 않는 유저입니다.");
        }
    }

    @GetMapping("my-info")
    public ResponseEntity<UserDTO> memberInfo(HttpSession session){
        String id = SessionUtil.getLoginMemberId(session);
        if (id == null){
            id = SessionUtil.getLoginAdminId(session);
        }

        if(id == null){
            return new ResponseEntity<>(null,HttpStatus.UNAUTHORIZED);
        }

        UserDTO memberInfo = userService.getUserInfo(id);
        return new ResponseEntity<>(memberInfo,HttpStatus.OK);
    }

    @PutMapping("logout")
    public void logout(HttpSession session){
        SessionUtil.clear(session);
    }

    @PatchMapping("password")
    public ResponseEntity updateUserPassword(@RequestBody UserUpdatePasswordRequest userUpdatePasswordRequest,HttpSession session){
        ResponseEntity<LoginResponse> responseEntity = null;
        String id = SessionUtil.getLoginMemberId(session);
        if (id == null){
            id = SessionUtil.getLoginAdminId(session);
        }

        if(id == null){
            return new ResponseEntity<>(null,HttpStatus.UNAUTHORIZED);
        }

        String beforePassword = userUpdatePasswordRequest.getBeforePassword();
        String afterPassword = userUpdatePasswordRequest.getAfterPassword();

        try{
            userService.updatePassword(id,beforePassword,afterPassword);
            responseEntity = new ResponseEntity<>(HttpStatus.OK);
        } catch (RuntimeException e){
            log.error("updatePassword 실패 ", e);
            responseEntity = new ResponseEntity<LoginResponse>(HttpStatus.BAD_REQUEST);
        }
        return responseEntity;
    }

    @DeleteMapping("delete")
    public ResponseEntity delete(@RequestBody UserDeleteRequest userDeleteId, HttpSession session){
        ResponseEntity<LoginResponse> responseEntity = null;

        String id = SessionUtil.getLoginMemberId(session);
        if (id == null){
            id = SessionUtil.getLoginAdminId(session);
        }

        if(id == null){
            return new ResponseEntity<>(null,HttpStatus.UNAUTHORIZED);
        }

        try{
            userService.delete(id, userDeleteId.getPassword());
            responseEntity = new ResponseEntity<>(HttpStatus.OK);
        } catch (RuntimeException e){
            log.error("deleteId 실패");
            responseEntity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return responseEntity;
    }
}
