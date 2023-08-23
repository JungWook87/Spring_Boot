package com.study.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
// 롬복에서 제공해주는 어노테이션, 로깅 추상화 라이브러리.
// 로깅 추상화 == 로깅을 직접하지 않고 로깅 구현체를 찾아 기능을 사용할 수 있게 해주는 것
public class LoggerInterceptor implements HandlerInterceptor {
// 인터셉터는 로그인, 권한, 인증과 같은 로직을 처리할 때 주로 사용된다.

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{
    // 컨트롤러의 메서드에 매핑된 특정 URI가 호출됐을 때 실행되는 메서드. 컨트롤러를 경유(접근)하기 직전에 실행되는 메서드.
    // 사용자가 어떤 기능을 수행했는지 파악하기 위해, 해당 메서드(기능)와 매핑된 URI 정보가 로그로 출력되도록 처리
        log.debug("=====================================");
        log.debug("=============== BEGIN ===============");
        log.debug("=====================================");
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception{
    // 컨트롤러를 경유 한 후, 즉 화면으로 결과를 전달하기 전에 실행되는 메서드
    // preHandle()과는 반대로 요청의 끝을 알리는 로그가 콘솔에 출력되도록 처리
        log.debug("=============== END ===============");
        log.debug("=====================================");
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }
}
