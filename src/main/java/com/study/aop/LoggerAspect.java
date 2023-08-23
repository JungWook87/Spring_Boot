package com.study.aop;

/* AOP는 관점 지향 프로그래밍이다. AOP는 자바와 같은 객체 지향 프로그래밍(OOP)을 더욱 OOP 답게 사용할 수 있도록 도와준다
   AOP는 여러 개의 핵심 비즈니스 로직 외에 공통으로 처리되어야 하는 로그 출력, 보안 처리, 예외 처리와 같은 코드를 별도로 분리해서
   하나의 단위로 묶는 모듈화 개념으로 생각할 수 있다

   AOP 관점은 핵심적인 관점과 부가적인 관점으로 나눌 수 있다. 핵심적인 관점은 핵심 비즈니스 로직을 의미하고, 부가적인 관점은 앞에서 말한
   공통으로 처리되어야 하는 코드를 의미한다

            Controller      Service         DAO
    고객  -> 권한 -> 로깅 -> 로깅 -> 트랜잭션 -> 로깅 --->
    상품  -> 권한 -> 로깅 -> 로깅 -> 트랜잭션 -> 로깅 --->
    배송  -> 권한 -> 로깅 -> 로깅 -> 트랜잭션 -> 로깅 --->

    각 화살표는 하나의 기능을 구현하는데 필요한 작업을 의미하는데, MVC 패턴의 특성상, 컨트롤러 -> 서비스 -> 맵퍼 순으로 작동
    만약, 필수적으로 처리되어야 하는 로그, 보안, 트랜잭션, 예외 처리와 같은 부작적인 기능들이, 규모가 큰 시스템에서
    각각의 기능마다 추가되면 코드가 길어지게 된다

    AOP는 이러한 문제를 관점이라는 개념을 통해 해결했다. 부가적인 관점에서는 핵심 비즈니스 로직이 어떤 기능을 수행하는지에 대해
    전혀 알 필요가 없었다. 단지, 핵심 비즈니스 로직 안에서 필요한 시점에 부가적인 관점이 포함되기만 하면 된다

                Controller      Service      DAO
    고객    -> | | -> | | -> | | -> |    | -> | | --->
    상품    -> | | -> | | -> | | -> |    | -> | | --->
    배송    -> | | -> | | -> | | -> |    | -> | | --->
              권한    로깅    로깅    트랜잭션    로깅

   부가적인 관점이 핵심 비즈니스 로직의 바깥에 포함되어 있다.
   AOP를 적용하면 로그, 보안, 트랜잭션, 예외 처리와 같은 부가적인 기능들을 비즈니스 로직마다 일일이 추가하지 않는다
*/

/*
    AOP 용어
    관점(Aspect) :  공통적으로 적용될 기능을 의미. 부가적인 기능을 정의한 코드인 어드바이스와 어드바이스를 어느 곳에 적용할지 결정하는 포인트컷의 조합
    어드바이스(Advice) : 실제로 부가적인 기능을 구현한 객체를 의미
    조인 포인트(Join Point) : 어드바이스를 적요할 위치를 의미. 예를 들어, PostService에서 CRUD를 처리하는 메서드 중 원하는 메서드를 골라서 어드바이스를 적용할 수 있다.
                            이때 PostService의 모든 메서드는 조인 포인트가 된다.
    포인트컷(Pointcut) : 어드바이스를 적용할 조인 포인트를 선별하는 과정이나 그 기능을 정의한 모듈을 의미. 정규 표현식이나 AspectJ 문법을 이용하여 어떤 조인 포인트를 사용할지 결정
    타겟(Target) : 실제로 비즈니스 로직을 수행하는 객체를 의미. 어드바이스를 적용하 대상.
    프록시(Proxy) : 어드바이스가 적용되었을 때 생성되는 객체를 의미
    인트로덕션(Introduction) : 타겟에는 없는 새로운 메서드나 멤버(인스턴스) 변수를 추가하는 기능
    위빙(Weaving) : 포인트컷에 의해서 결정된 타겟의 조인 포인트에 어드바이스를 적용하는 것을 의미
* */

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.thymeleaf.util.StringUtils;

@Slf4j
@Aspect
// AOP 기능을 하는 클래스의 클래스 레벨에 선언하는 어노테이션
@Component
// 스프링 컨테이너에 빈(Bean)으로 등록하기 위한 어노테이션. @Bean은 개발자가 제어할 수 없는 외부 라이브러리를 빈으로 등록할 때 사용하고,
// @Component는 개발자가 직접 정의한 클래스를 빈으로 등록할 때 사용
public class LoggerAspect {

    @Around("execution(* com.study.domain..*Controller.*(..)) || execution(* com.study.domain..*Service.*(..)) || execution(* com.study.domain..*Mapper.*(..))")
    // 어드바이스의 종류 중 한 가지로 어드바이스는 모두 다섯 가지의 타입이 있다. 다섯 가지 중 어라운드는 메서드의 호출 자체를 제어할 수 잇기 때문에 어드바이스 중 가장 강력한 기능이다.
    /* 다섯개의 어드바이스
       1. Before Advice / @Before / Target 메서드 호출 이전에 적용할 어드바이스 정의
       2. After Returning / @AfterReturning / Target 메서드가 성공적으로 실행되고, 결과값을 반환한 뒤에 적용
       3. After Throwing / @AfterThrowing / Target 메서드에서 예외 발생 이후에 적용(try/catch의 catch와 유사)
       4. After / @After / Target 메서드에서 예외 발생에 관계없이 적용(try/catch의 finally와 유사)
       5. Around / @Around / Target 메서드 호출 이전과 이후 모두 적용(가장 광범위하게 사용됨)
    */

    /* @Around 안에서 execution으로 시작하는 구문은 포인트컷을 지정하는 문법으로, 다른 명시자로는 within과 bean이 있다.
       세 개 중 가장 많이 사용되는 명시자는 execution으로, 접근제어자, 리턴타입, 타입패턴, 메서드, 파라미터 타입, 예외 타입 등을 조합하여 정교한 포인트컷을 만들 수 있다

       모든 예시는 execution()으로 감싸져 있다는 가정하에 작성
       PostResponse select*(..) : 리턴 타입이 PostResponse 타입이고, 메서드의 이름이 select로 시작하며, 파라미터가 0개 이상인 모든 메서드가 호출될 때
                                  (0개 이상은 패키지, 메서드, 파라미터 등 모든 것을 의미)
      * com.study.controller.*() : 해당 패키지 내의 파라미터가 없는 모든 메서드가 호출될 때
      * com.study.controller.*(..) : 해당 패키지 내의 파라미터가 0개 이상인 모든 메서드가 호출될 때
      * con.study..select(*) : com.study의 모든 하위 패키지에 존재하는 select로 시작하고, 파라미터가 한 개인 모든 메서드가 호출될 때
      * com.study..select(*,*) : com.study의 모든 하위 패키지에 존재하는 select로 시작하고, 파라미터가 두 개인 모든 메서드가 호출될 때
    */

    /* 우리가 작성한 LoggerAspect 클래스의 포인트컷을 보면 "or" 표현식이 있다. 포인트컷 표현식은 and(&&) or(||)를 조합해서 사용할 수 있다.
       execution 포인트컷은 AOP에서 정말 주용한 개념이니 꼭 기억하자.

       * com.study.domain..*Cotroller.*(..) : com.study.domain의 모든 하위 패키지 중 xxxController와 같은 패턴의 이름을 가진 클래스에서 파라미터가 0개 이상인 메서드를 의미
    */
    public Object printLog(ProceedingJoinPoint joinPoint) throws Throwable {
    // ProceedingJoinPoint 타입의 객체인 jointPoint 객체 안의 signature 겍체는 클래스와 메서드에 대한 정보를 담고 있는 객체이다.
        String name = joinPoint.getSignature().getDeclaringTypeName();
        // 대상 파일의 경로(패키지 + 파일명)가 저장되며, 결과적으로 printLog()는 signature 객체가 가진 정보를 이용해서, 어떤 클래스의 어떤 메서드가 호출되었는지를 로그로 출력한다
        // 컨트롤러 : com.study.domain.post.PostController
        // 서비스 : com.study.domain.post.PostService
        // 매퍼 : com.study.domain.post.PostMapper

        /* 추가적으로 ProceedingJoinPoint 인터페이스는 JoinPoint 인터페이스를 상속 받는다. JoinPoint는 다음의 메서드들을 포함하고 있다.
           Object[] getArgs() : 전달되는 모든 파라미터들을 Object 타입의 배열로 가져온다.
           String[] getKind() : 해당 어드바이스(Advice)의 타입을 가지고 온다
           Signature getSignature() : 실행되는 대상 객체의 메서드에 대한 정보를 가지고 온다
           Object getTarget() : 타겟(Target) 객체를 가지고 온다
           Object getThis() : 어드바이스(Advice)를 행하는 객체를 가지고 온다
        * */

        String type =
                StringUtils.contains(name, "Controller")? "Controller ===> " :
                StringUtils.contains(name, "Service")? "Service ===> " :
                StringUtils.contains(name, "Mapper")? "Mapper ===> " :
                "";

        log.debug(type + name + "." + joinPoint.getSignature().getName() + "()");
        return joinPoint.proceed();
    }

    /* 트랜잭션의 기본 원칙
       ACID에서 트랜잭션의 성격을 가장 잘 표현한 것은 원자성(Atomicity)이다.
       원자성이란? 트랜잭션의 여러 가지 작업들 중 하나라도 실패 처리되었다면, 앞에서 성공했던 모든 작업들도 원래 상태로 돌아가야함을 의미
       Atomicity(원자성) : 하나의 트랜잭션은 모두 하나의 작업 단위로 처리되어야 한다. 트랜잭션이 A, B, C로 구성된다며느 A, B, C의 처리 결과는 모두
                          동일해야 한다. 하나라도 실패한 경우 세 가지 모두 원상태로 돌아간다
       Consistency(일관성) : 트랜잭션이 성공했다면 데이터베이스의 모든 데이터는 일관성을 유지해야 한다
       Isolation(고립성) : 트랜잭션은 독립적으로 처리되며, 처리되는 중간에 외부에서의 간섭은 없어야 한다
       Durability(지속성) : 트랜잭션은 독립적으로 처리되며, 그 결과는 지속성으로 유지되어야 한다
    */

    /* 트랜잭션 적용
       코드 퀄리티 개선 작업 이전에는 애플리케이션 전역에서 DB Transactional이 작동하도록 설정하였는데,
       PostService의 일부 메서드에 적용되어 있는 선언적 트랜잭션(@Transactional)이 퍼포먼스상 이득일 거라는 판단하에
       전역 트랜잭션 설정은 스킵하고, 선언적 트랜잭션을 이용해서 남은 기능을 구현하려고 함.
    * */

}
