package io.topfilms.api.common;

import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

public class TopFilmsUtil {

    /**
     * Checks is a role is present within granted authorities
     *
     * @param authentication spring security authentication
     * @param role check if this role exists
     * @return true if the role is there
     */
    public static boolean containsGrantedAuthority(Authentication authentication, String role) {
        for (GrantedAuthority authority : authentication.getAuthorities()) {
            if (authority.getAuthority().equals(role)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Parses expression in annotation to get dynamic values
     *
     * @param parameterNames signature params
     * @param args proceeding join point args
     * @param key value of the annotation
     * @return parsed value
     */
    public static Object parseAnnotationValue(String[] parameterNames, Object[] args, String key) {
        ExpressionParser parser = new SpelExpressionParser();
        StandardEvaluationContext context = new StandardEvaluationContext();

        for (int i = 0; i < parameterNames.length; i++) {
            context.setVariable(parameterNames[i], args[i]);
        }

        return parser.parseExpression(key).getValue(context, Object.class);
    }

}
