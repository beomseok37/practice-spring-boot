package dev.beomseok.boardserver.controller;

import dev.beomseok.boardserver.aop.LoginCheck;
import dev.beomseok.boardserver.dto.user.UserDTO;
import dev.beomseok.boardserver.dto.user.LoginInfoRequest;
import dev.beomseok.boardserver.dto.user.SignUpRequest;
import dev.beomseok.boardserver.dto.user.UpdatePasswordRequest;
import dev.beomseok.boardserver.dto.user.LoginResponse;
import dev.beomseok.boardserver.dto.common.ResponseBody;
import dev.beomseok.boardserver.service.user.UserService;
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
    public ResponseEntity signUp(@Valid @RequestBody SignUpRequest userSignUpRequest) {
        userService.register(userSignUpRequest);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @PostMapping("sign-in")
    public ResponseEntity<ResponseBody<UserDTO>> login(@Valid @RequestBody LoginInfoRequest request,
                                                       HttpSession session) {

        UserDTO userInfo = userService.login(request.getUserId(), request.getPassword());

        setLoginUserId(session, userInfo);
        return ResponseBody.createSuccessResponse(HttpStatus.OK, userInfo);
    }

    @GetMapping("my-info")
    @LoginCheck(type = LoginCheck.UserType.MEMBER)
    public ResponseEntity<ResponseBody<UserDTO>> memberInfo(String userId) {
        UserDTO userInfo = userService.getUserInfo(userId);
        return ResponseBody.createSuccessResponse(HttpStatus.OK, userInfo);
    }

    @PutMapping("logout")
    public ResponseEntity logout(HttpSession session) {
        clearLoginSession(session);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("password")
    @LoginCheck(type = LoginCheck.UserType.MEMBER)
    public ResponseEntity updateUserPassword(String userId, @Valid @RequestBody UpdatePasswordRequest updatePasswordRequest) {
        String beforePassword = updatePasswordRequest.getBeforePassword();
        String afterPassword = updatePasswordRequest.getAfterPassword();

        userService.updatePassword(userId, beforePassword, afterPassword);
        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping("delete")
    @LoginCheck(type = LoginCheck.UserType.MEMBER)
    public ResponseEntity delete(String userId, @Valid @RequestBody LoginInfoRequest loginInfoRequest, HttpSession session) {

        userService.delete(userId, loginInfoRequest.getPassword());
        clearLoginSession(session);

        return new ResponseEntity(HttpStatus.OK);
    }
}
