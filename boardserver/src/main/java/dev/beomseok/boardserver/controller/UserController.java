package dev.beomseok.boardserver.controller;

import dev.beomseok.boardserver.dto.UserDTO;
import dev.beomseok.boardserver.dto.request.UserDeleteRequest;
import dev.beomseok.boardserver.dto.request.UserLoginRequest;
import dev.beomseok.boardserver.dto.request.UserSignUpDto;
import dev.beomseok.boardserver.dto.request.UserUpdatePasswordRequest;
import dev.beomseok.boardserver.dto.response.LoginResponse;
import dev.beomseok.boardserver.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static dev.beomseok.boardserver.utils.SessionUtil.*;

@RestController
@RequestMapping("/users")
@Log4j2
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    static LoginResponse loginResponse = null;

    @PostMapping("sign-up")
    public ResponseEntity signUp(@Valid @RequestBody UserSignUpDto userSignUpDto) {
        userService.register(userSignUpDto);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @PostMapping("sign-in")
    public ResponseEntity<LoginResponse> login(@RequestBody UserLoginRequest request,
                                               HttpSession session) {

        UserDTO userInfo = userService.login(request.getUserId(), request.getPassword());

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
        String userId = getLoginUserId(session);

        String beforePassword = userUpdatePasswordRequest.getBeforePassword();
        String afterPassword = userUpdatePasswordRequest.getAfterPassword();

        userService.updatePassword(userId, beforePassword, afterPassword);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("delete")
    public ResponseEntity delete(@RequestBody UserDeleteRequest userDeleteId, HttpSession session) {
        String userId = getLoginUserId(session);

        userService.delete(userId, userDeleteId.getPassword());
        clearLoginSession(session);

        return new ResponseEntity(HttpStatus.OK);
    }
}
