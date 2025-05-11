package com.fastcampus.board_server.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.fastcampus.board_server.utils.SessionUtil;

import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@Component
@Aspect
@Order(Ordered.LOWEST_PRECEDENCE)
@Log4j2
public class LoginCheckAspect {
    @Around("@annotation(com.fastcampus.board_server.aop.LoginCheck) && @annotation(currentLoginCheckAnnotation)")
    public Object adminLoginCheck(ProceedingJoinPoint proceedingJoinPoint, LoginCheck currentLoginCheckAnnotation) throws Throwable {
        HttpSession session = (HttpSession) ((ServletRequestAttributes) (RequestContextHolder
                .currentRequestAttributes())).getRequest().getSession();
        String id = null;
        int idIndex = 0;

        String userType = currentLoginCheckAnnotation.type().toString();
        switch (userType) {
            case "ADMIN": {
                id = SessionUtil.getLoginAdminId(session);
                break;
            }
            case "USER": {
                id = SessionUtil.getLoginMemberId(session);
                break;
            }
        }
        if (id == null) {
            log.debug(proceedingJoinPoint.toString() + "accountName :" + id);
            throw new HttpStatusCodeException(UNAUTHORIZED, "로그인한 id값을 확인해주세요.") {
            };
        }

        Object[] modifiedArgs = proceedingJoinPoint.getArgs();

        if (proceedingJoinPoint.getArgs() != null)
            modifiedArgs[idIndex] = id;

        return proceedingJoinPoint.proceed(modifiedArgs);
    }

}
