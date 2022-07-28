package com.spring.ribborn.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
//@Tartget을 통해 이 어노테이션이 어떤 종류의 녀석들을 위한 것인지(메소드를 위한 녀석인지.. 패키지를 위한 녀석 인지..) 설정해줍니다.
// ElementType이 METHOD로 메소드를 위한 어노테이션임을 나타내구요!!
@Retention(RetentionPolicy.RUNTIME)
//@Retention은 Retention(보유, 유지력)이라는 말 그대로 이 어노테이션이 언제까지 유효한지 설정해주는데
// 런타임시에도(프로그램이 동작하고 있을 때에) 작동해야하므로 RetentionPolicy를 RUNTIME으로 설정해줍니다.
public @interface LogInCheck {
    //            LogExecutionTime  이름의 어노테이션이 완성
}