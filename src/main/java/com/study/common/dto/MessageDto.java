package com.study.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

@Getter
@AllArgsConstructor
// PostService의 @RequiredArgsConstructor와 마찬가지로 롬복 라이브러리에서 제공해주는 기능.
// 해당 어노테이션이 선언된 클래스에는 전체 멤버 변수를 필요로 하는 생성자가 생성된다.
public class MessageDto {

    private String message;             // 사용자에게 전달할 메시지
    private String redirectUri;         // 리다이렉트 URI
    private RequestMethod method;       // HTTP 요청 메서드. spring-web 라이브러리에 포함된 enum(상수 처리용) 클래스
    private Map<String, Object> data;   // 화면(view)로 전달할 데이터(파라미터)
}
