package com.study.config;

import com.study.interceptor.LoggerInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
// WebMvcConfigurer : 해당 인터페이스를 구현하면 @EnableWebMvc의 자동 설정을 베이스로 가져가며, 개발자가 원하는 설정까지 추가할 수 있다.

    @Override
    public void addInterceptors(InterceptorRegistry registry){
    // 애플리케이션 내에 인터셉터를 등록. 이 과정에서 excludePathPatterns()를 이용하면, 메서드의 인자로 전달하는
    // 주소(URI)와 경로(Path)는 인터셉터 호출에서 제외시켜준다. 여기서 해당 메서드는 resources의 모든 정적(statci) 파일을 무시하겠다는 의미이다.
    // 반대로 addPathPatterns()가 있다.

        registry.addInterceptor(new LoggerInterceptor()).excludePathPatterns("/css/**", "/images/**", "/js/**");
    }
}
