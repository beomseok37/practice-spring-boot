package dev.beomseok.boardserver.utils;

import dev.beomseok.boardserver.domain.User;
import dev.beomseok.boardserver.dto.UserDTO;
import dev.beomseok.boardserver.exception.SessionNotExistException;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class SessionUtil {
    private static final String LOGIN_MEMBER_ID = "LOGIN_MEMBER_ID";
    private static final String LOGIN_ADMIN_ID = "LOGIN_ADMIN_ID";

    private static String getLoginDefaultId(HttpSession session) {
        return (String) session.getAttribute(LOGIN_MEMBER_ID);
    }
    public static void setLoginMemberId(HttpSession session, String id) {
        session.setAttribute(LOGIN_MEMBER_ID, id);
    }
    private static String getLoginAdminId(HttpSession session) {
        return (String) session.getAttribute(LOGIN_ADMIN_ID);
    }
    public static void setLoginAdminId(HttpSession session, String id) {
        session.setAttribute(LOGIN_ADMIN_ID, id);
    }
    public static void clearLoginSession(HttpSession session) {
        session.invalidate();
    }

    public static String getLoginUserId(HttpSession session){
        String userId = getLoginDefaultId(session);
        if (userId == null){
            userId = getLoginAdminId(session);
        }

        if (userId == null){
            throw new SessionNotExistException("세션이 존재하지 않습니다.");
        }

        return userId;
    }

    public static void setLoginUserId(HttpSession session, UserDTO userInfo){
        if (userInfo.getStatus() == User.UserStatus.ADMIN) {
            setLoginAdminId(session, userInfo.getUserId());
        } else {
            setLoginMemberId(session, userInfo.getUserId());
        }
    }
}