package dev.beomseok.boardserver.utils;

import dev.beomseok.boardserver.domain.User;
import dev.beomseok.boardserver.dto.UserDTO;
import jakarta.servlet.http.HttpSession;

public class SessionUtil {
    private static final String LOGIN_MEMBER_ID = "LOGIN_MEMBER_ID";
    private static final String LOGIN_ADMIN_ID = "LOGIN_ADMIN_ID";

    public static String getLoginMemberId(HttpSession session) {
        return (String) session.getAttribute(LOGIN_MEMBER_ID);
    }
    private static void setLoginMemberId(HttpSession session, String id) {
        session.setAttribute(LOGIN_MEMBER_ID, id);
    }
    public static String getLoginAdminId(HttpSession session) {
        return (String) session.getAttribute(LOGIN_ADMIN_ID);
    }
    private static void setLoginAdminId(HttpSession session, String id) {
        session.setAttribute(LOGIN_ADMIN_ID, id);
    }
    public static void clearLoginSession(HttpSession session) {
        session.invalidate();
    }

    public static void setLoginUserId(HttpSession session, UserDTO userInfo){
        if (userInfo.getStatus() == User.UserStatus.ADMIN) {
            setLoginAdminId(session, userInfo.getUserId());
        } else {
            setLoginMemberId(session, userInfo.getUserId());
        }
    }
}