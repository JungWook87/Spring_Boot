package com.study.domain.post;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostResponse {
// 사용자에게 보여줄 데이터를 처리할 응답용 클래스
    private Long id;                       // PK
    private String title;                  // 제목
    private String content;                // 내용
    private String writer;                 // 작성자
    private int viewCnt;                   // 조회 수
    private Boolean noticeYn;              // 공지글 여부
    private Boolean deleteYn;              // 삭제 여부
    private LocalDateTime createdDate;     // 생성일시
    private LocalDateTime modifiedDate;    // 최종 수정일시

}