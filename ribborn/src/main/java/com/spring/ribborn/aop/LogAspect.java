package com.spring.ribborn.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import java.lang.reflect.Method;
import java.util.Arrays;

@Component
@Aspect
public class LogAspect {

    Logger log = LoggerFactory.getLogger(LogAspect.class);

    @Around("execution(* com.spring.ribborn.controller.*Controller.UserController.login*(..)) "
            + "or execution(* com.spring.ribborn.service.UserService.login*(..)) ")
    public Object LogInCheck(ProceedingJoinPoint joinPoint) throws Throwable {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        // @LogExecutionTime 애노테이션이 붙어있는 타겟 메소드를 실행
        log.info("Before Execute Method!!! ( 메소드 실행 전 ");
        Object proceed = joinPoint.proceed();
//        로그 앞뒤로 찍기전에 joinPoint.proceed(); 요거 기억!!

        log.info("-------------------------------------------------------!-" );
        log.info("After Execute Method!!!!( 메소드 실행 후 )");
        stopWatch.stop();
        log.info(stopWatch.prettyPrint());

        return proceed; // 결과 리턴
    }

    // 클라이언트에서 보내주는 값 로깅
    @Around("@annotation(Logging)")
    public Object logPrint(ProceedingJoinPoint proceedingJoinPoint) throws  Throwable {

        log.info("-------------------------------------");

        long start = System.currentTimeMillis();

        Object result = proceedingJoinPoint.proceed();
        Signature sig = proceedingJoinPoint.getSignature();

        long end = System.currentTimeMillis();
        Object [] objects = proceedingJoinPoint.getArgs();
        for (Object obj : objects) {
            log.info("!!!!!!!!!!!!!!!!!!!!");
            log.info("Args: {}", obj);
        }

        log.info("Parameter   :" + Arrays.toString(Arrays.stream(proceedingJoinPoint.getArgs()).toArray()));
        log.info("들어옴?");
        //메소드 이름
        log.info(sig.getName());
        // 메소드가 들어있는 컨트롤러
        log.info(proceedingJoinPoint.getTarget().getClass().getSimpleName());
        // 메소드 파라미터
        log.info(Arrays.toString(proceedingJoinPoint.getArgs()));

        log.info("Running Time :" + (end-start));
        log.info("-------------------------------------");
        return result;
    }


    @Pointcut("execution(* com.spring.ribborn.controller.*Controller.UserController.login*(..)) "
            + "or execution(* com.spring.ribborn.service.UserService.login*(..)) ")
    private void cut() { }

    @Before("cut()")
    public void before( JoinPoint joinPoint ) {
        MethodSignature methodSignature = ( MethodSignature ) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        log.info("함수명: {}", method.getName() );

        Object [] objects = joinPoint.getArgs();
        for (Object obj : objects) {
            log.info("!!!!!!!!!!!!!!!!!!!!");
            log.info("Args: {}", obj);
        }
    }


}