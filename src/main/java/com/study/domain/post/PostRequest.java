package com.study.domain.post;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostRequest {
// 게시글 생성과 수정에 사용할 요청(Request) 클래스
    private Long id;             // PK
    private String title;        // 제목
    private String content;      // 내용
    private String writer;       // 작성자
    private Boolean noticeYn;    // 공지글 여부

}