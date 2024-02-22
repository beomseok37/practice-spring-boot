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
import static dev.beomseok.boardserver.utils.SessionUtil.*;

@RestController
@RequestMapping("/users")
@Log4j2
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    static LoginResponse loginResponse = null;

    @PostMapping("sign-up")
    public ResponseEntity signUp(@RequestBody UserSignUpDto userSignUpDto) {
        if (UserSignUpDto.hasNullDataBeforeSignup(userSignUpDto)) {
            throw new RuntimeException("회원가입 정보를 확인해주세요");
        }
        userService.register(userSignUpDto);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @PostMapping("sign-in")
    public ResponseEntity<LoginResponse> login(@RequestBody UserLoginRequest request,
                                               HttpSession session) {
        String id = request.getUserId();
        String password = request.getPassword();

        UserDTO userInfo = userService.login(id, password);
        if (userInfo == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        setLoginUserId(session,userInfo);
        LoginResponse loginResponse = LoginResponse.success(userInfo);
        return new ResponseEntity(loginResponse, HttpStatus.OK);
    }

    @GetMapping("my-info")
    public ResponseEntity<UserDTO> memberInfo(HttpSession session) {
        String userId = getLoginUserId(session);

        UserDTO memberInfo = userService.getUserInfo(userId);
        return new ResponseEntity(memberInfo, HttpStatus.OK);
    }

    @PutMapping("logout")
    public void logout(HttpSession session) {
        clearLoginSession(session);
    }

    @PatchMapping("password")
    public ResponseEntity updateUserPassword(@RequestBody UserUpdatePasswordRequest userUpdatePasswordRequest, HttpSession session) {
        ResponseEntity<LoginResponse> responseEntity = null;
        String userId = getLoginUserId(session);

        String beforePassword = userUpdatePasswordRequest.getBeforePassword();
        String afterPassword = userUpdatePasswordRequest.getAfterPassword();

        try {
            userService.updatePassword(userId, beforePassword, afterPassword);
            responseEntity = new ResponseEntity<>(HttpStatus.OK);
        } catch (RuntimeException e) {
            log.error("updatePassword 실패 ", e);
            responseEntity = new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return responseEntity;
    }

    @DeleteMapping("delete")
    public ResponseEntity delete(@RequestBody UserDeleteRequest userDeleteId, HttpSession session) {
        ResponseEntity<LoginResponse> responseEntity = null;

        String userId = getLoginUserId(session);

        try {
            userService.delete(userId, userDeleteId.getPassword());
            clearLoginSession(session);
            responseEntity = new ResponseEntity(HttpStatus.OK);
        } catch (RuntimeException e) {
            log.error("deleteId 실패");
            responseEntity = new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        return responseEntity;
    }
}
