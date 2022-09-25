package org.zerock.aop;

import lombok.ToString;
import lombok.extern.log4j.Log4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.test.context.TestConstructor;

import java.util.Arrays;

/*
    로그를 기록하는 일은 반복적이면서 핵심 로직도 아니고, 필요하기는 한 기능이므로 관심사로 간주할 수 있음
    AOP의 개념에서 Advice는 관심사를 실제로 구현한 코드

    Advice와 관련된 어노테이션들은 내부적으로 Pointcut을 지정

    @Aspect : 해당 클래스가 Aspect로 구현한 것임을 나타냄
    @Component : 스프링 빈으로 등록
 */
@Aspect
@Log4j
@Component
public class LogAdvice {


    // @Before : BeforeAdvice를 구현한 메소드
    // execution 뒤에 문자열은 AspectJ의 표현식
    @Before("execution(* org.zerock.service.SampleService*.*(..))") // 어떤 위치에 Advice를 적용할 것인지를 결정하는 Pointcut
    public void logBefore() {

        log.info("======================");
    }

    // && args : 간단히 파라미터를 찾아서 기록할 때에는 유용하지만 파라미터가 다른 여러 종류의 메서드에 적용하는 데에는 간단하지 않음
    @Before("execution(* org.zerock.service.SampleService*.doAdd(String, String)) && args(str1, str2)")
    public void logBeforeWithParam(String str1, String str2) {

        log.info("str1 : " + str1);
        log.info("str2 : " + str2);
    }

    // @AfterThrowing : 지정된 대상이 예외를 발생한 후에 동작하면서 문제를 찾을 수 있도록 도와줄 수 있음
    // pointcut과 throwing 속성을 지정하고 변수 이름을 exception으로 지정
    @AfterThrowing(pointcut = "execution(* org.zerock.service.SampleService*.*(..))", throwing = "exception")
    public void logException(Exception exception) {

        log.info("Exception..........!!");
        log.info("exception : " + exception);

    }

    // AOP를 이용해서 좀 더 구체적인 처리를 하고 싶다면 @Around와 ProceedingJoinPoint를 이용
    // @Around : 직접 대상 메서드를 실행할 수 있는 권한을 가지고 있고, 메서드의 실행 전과 실행 후에 처리가 가능
    @Around("execution(* org.zerock.service.SampleService*.*(..))")
    public Object logTime(ProceedingJoinPoint pjp) {

        long start = System.currentTimeMillis();

        log.info("Target : " + pjp.getTarget());
        log.info("Param : " + Arrays.toString(pjp.getArgs()));

        // invoke method
        Object result = null;

        try {
            result = pjp.proceed();
        } catch(Throwable e) {
            e.printStackTrace();
        }

        long end = System.currentTimeMillis();

        log.info("TIME : " + (end - start));

        return result;
    }

}
