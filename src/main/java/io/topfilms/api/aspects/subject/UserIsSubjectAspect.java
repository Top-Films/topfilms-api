package io.topfilms.api.aspects.subject;

import io.topfilms.api.common.Constants;
import io.topfilms.api.common.TopFilmsUtil;
import io.topfilms.api.exceptions.TopFilmsException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.graphql.execution.ErrorType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class UserIsSubjectAspect {

    private static final Logger LOG = LoggerFactory.getLogger(io.topfilms.api.aspects.email.EmailVerifiedAspect.class);

    @Pointcut("@annotation(io.topfilms.api.aspects.subject.UserIsSubject)")
    private void userIsSubjectAnnotation() {}

    @Around("io.topfilms.api.aspects.subject.UserIsSubjectAspect.userIsSubjectAnnotation()")
    private Object checkUserIsSubject(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        // Get annotation
        MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
        UserIsSubject annotation = signature.getMethod().getAnnotation(UserIsSubject.class);

        // User helper function to get the value provided as param
        String providedUserId = TopFilmsUtil.parseAnnotationValue(
                signature.getParameterNames(),
                proceedingJoinPoint.getArgs(),
                annotation.id()
        ).toString();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Get jwt to check for authentication and log user ids
        Jwt jwt;
        try {
            jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (ClassCastException e) {
            LOG.info("User is not logged in");
            throw new TopFilmsException("User is not logged in", ErrorType.UNAUTHORIZED);
        }
        String jwtUserId = jwt.getSubject();

        // Check if admin or email verified
        if (TopFilmsUtil.containsGrantedAuthority(authentication, Constants.ROLE_ADMIN)) {
            LOG.info("User {} is admin, skipping user is subject check", jwtUserId);
            return proceedingJoinPoint.proceed();
        } else if (jwtUserId.equals(providedUserId)) {
            LOG.info("Provided user id {} matches jwt user id {}", providedUserId, jwtUserId);
            return proceedingJoinPoint.proceed();
        }

        LOG.warn("Provided user id {} does not match jwt user id {}", providedUserId, jwtUserId);
        throw new TopFilmsException("User is not subject", ErrorType.FORBIDDEN);
    }

}
