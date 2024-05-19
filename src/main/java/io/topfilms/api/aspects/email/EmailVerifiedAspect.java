package io.topfilms.api.aspects.email;

import io.topfilms.api.common.Constants;
import io.topfilms.api.common.TopFilmsUtil;
import io.topfilms.api.exceptions.TopFilmsException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.graphql.execution.ErrorType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class EmailVerifiedAspect {

    private static final Logger LOG = LoggerFactory.getLogger(EmailVerifiedAspect.class);

    @Pointcut("@annotation(io.topfilms.api.aspects.email.EmailVerified)")
    private void emailVerifiedAnnotation() {}

    @Around("io.topfilms.api.aspects.email.EmailVerifiedAspect.emailVerifiedAnnotation()")
    private Object checkEmailVerified(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Get jwt to check for authentication and log user ids
        Jwt jwt;
        try {
            jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (ClassCastException e) {
            LOG.info("User is not logged in");
            throw new TopFilmsException("User is not logged in", ErrorType.UNAUTHORIZED);
        }
        String userId = jwt.getSubject();

        // Check if admin or email verified
        if (TopFilmsUtil.containsGrantedAuthority(authentication, Constants.ROLE_ADMIN)) {
            LOG.info("User {} is admin, skipping email verified", userId);
            return proceedingJoinPoint.proceed();
        } else if (TopFilmsUtil.containsGrantedAuthority(authentication, Constants.ROLE_EMAIL_VERIFIED)) {
            LOG.info("User {} is email verified", userId);
            return proceedingJoinPoint.proceed();
        }

        LOG.warn("User {} is not email verified", userId);
        throw new TopFilmsException("User is not email verified", ErrorType.FORBIDDEN);
    }

}
