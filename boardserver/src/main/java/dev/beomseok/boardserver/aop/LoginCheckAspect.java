package dev.beomseok.boardserver.aop;

import dev.beomseok.boardserver.utils.SessionUtil;
import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
@Aspect
@Log4j2
public class LoginCheckAspect {

    @Around("@annotation(dev.beomseok.boardserver.aop.LoginCheck) && @ annotation(loginCheck)")
    public Object adminLoginCheck(ProceedingJoinPoint proceedingJoinPoint, LoginCheck loginCheck) throws Throwable {
        HttpSession session = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest().getSession();
        String userId = null;

        String userType = loginCheck.type().toString();
        switch(userType) {
            case "ADMIN": {
                userId = SessionUtil.getLoginAdminId(session);
                break;
            }
            case "MEMBER": {
                userId = SessionUtil.getLoginMemberId(session);
                break;
            }
        }

        if(userId==null){
            log.error(proceedingJoinPoint.toString() + " accountName : " + userId);
            throw new IllegalStateException("로그인한 ID값을 확인해주세요");
        }

        Object[] args = proceedingJoinPoint.getArgs();
        args[0]=userId;

        return proceedingJoinPoint.proceed(args);
    }
}
