package ZTI.projekt.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * The LoggingAspect class handles logging of method invocations in the application.
 * It logs the start and end of method execution for controllers and repository classes.
 */
@Aspect
@Component
public class LoggingAspect {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    /**
     * Pointcut for methods in the controller package.
     */
    @Pointcut("execution(* ZTI.projekt.controller.*.*(..))")
    public void controllerMethods() {
    }

    /**
     * Logs method entry.
     * @param joinPoint provides information about the method being invoked.
     */
    @Before("controllerMethods()")
    public void logBefore(JoinPoint joinPoint) {
        log.info("Entering Method: {} ", joinPoint.getSignature().getName());
    }

    /**
     * Logs method exit.
     * @param joinPoint provides information about the method being invoked.
     */
    @After("controllerMethods()")
    public void logAfter(JoinPoint joinPoint) {
        log.info("Exiting Method: {} ", joinPoint.getSignature().getName());
    }
}
