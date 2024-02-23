package dev.beomseok.boardserver.controller;

import dev.beomseok.boardserver.dto.UserDTO;
import dev.beomseok.boardserver.dto.request.UserLoginInfoRequest;
import dev.beomseok.boardserver.dto.request.UserSignUpRequest;
import dev.beomseok.boardserver.dto.request.UserUpdatePasswordRequest;
import dev.beomseok.boardserver.dto.response.LoginResponse;
import dev.beomseok.boardserver.dto.response.ResponseBody;
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
    public ResponseEntity signUp(@Valid @RequestBody UserSignUpRequest userSignUpRequest) {
        userService.register(userSignUpRequest);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @PostMapping("sign-in")
    public ResponseEntity<ResponseBody<UserDTO>> login(@Valid @RequestBody UserLoginInfoRequest request,
                                                       HttpSession session) {

        UserDTO userInfo = userService.login(request.getUserId(), request.getPassword());

        setLoginUserId(session, userInfo);
        return ResponseBody.createSuccessResponse(HttpStatus.OK, userInfo);
    }

    @GetMapping("my-info")
    public ResponseEntity<ResponseBody<UserDTO>> memberInfo(HttpSession session) {
        String userId = getLoginUserId(session);

        UserDTO userInfo = userService.getUserInfo(userId);
        return ResponseBody.createSuccessResponse(HttpStatus.OK, userInfo);
    }

    @PutMapping("logout")
    public ResponseEntity logout(HttpSession session) {
        clearLoginSession(session);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("password")
    public ResponseEntity updateUserPassword(@Valid @RequestBody UserUpdatePasswordRequest userUpdatePasswordRequest,
                                             HttpSession session) {
        String userId = getLoginUserId(session);

        String beforePassword = userUpdatePasswordRequest.getBeforePassword();
        String afterPassword = userUpdatePasswordRequest.getAfterPassword();

        userService.updatePassword(userId, beforePassword, afterPassword);
        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping("delete")
    public ResponseEntity delete(@Valid @RequestBody UserLoginInfoRequest userLoginInfoRequest,
                                 HttpSession session) {
        String userId = getLoginUserId(session);

        userService.delete(userId, userLoginInfoRequest.getPassword());
        clearLoginSession(session);

        return new ResponseEntity(HttpStatus.OK);
    }
}
