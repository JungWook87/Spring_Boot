package com.study.domain.post;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PostMapper {
    // 레거시에서는 DAO 클래스에 @Repository 어노테이션을 선언하여 해당 클래스가 DB와 연동됨을 명시
    // Mybatis는 Mapper와 XML Mapper(SQL 쿼리작성하는 파일)를 통해 DB와 통신

    // Mapper는 XML Mapper에 선언된 SQL 중에 메서드명과 동일한 id를 가진 쿼리를 찾아서 실행
    // 예) 메서드명이 savePost()라고 하면 SQL id는 "savePost"가 되어야 한다

    // Mapper에는 @Mapper 어노테이션을 필수로 선언
    // Mapper와 XML Mapper의 namespace라는 속성을 통해 연결

    /**
     * 게시글 저장
     * @param params - 게시글 정보 
     */
    void save(PostRequest params);
    // 게시글을 생성하는 INSERT 쿼리를 호출
    // 파라미터로 전달받는 params는 요청(PostRequest) 클래스의 객체이며,
    // params에는 저장할 게시글 정보가 담기게 된다

    /**
     * 게시글 상세정보 조회
     * @param id - PK
     * @return 게시글 상세정보
     */
    PostResponse findById(Long id);
    // 특정 게시글을 조회하는 SELECT 쿼리를 호출
    // 파라미터로 id(PK)를 전달받아 SQL 쿼리의 WHERE 조건으로 사용하며,
    // 쿼리가 실행되면 메서드의 리턴 타입인 응답(PostResponse) 클래스 객체의 각 멤버 변수에 결과값이 맵핑
    // 레거시에서 Result Map과 같은 효과인듯..


    /**
     * 게시글 수정
     * @param params - 게시글 정보 
     */
    void update(PostRequest params);
    // 게시글 정보를 수정하는 UPDATE 쿼리 호출
    // save()와 같다.
    // save()와의 차이는 UPDATE 쿼리의 WHERE 조건으로 사용되는 id(PK)에도 값이 담긴다는 것

    /**
     * 게시글 삭제
     * @param id - PK 
     */
    void deleteById(Long id);
    // 게시글 삭제 처리하는 UPDATE 쿼리 호출
    // id값을 받아 delete_yn 칼럼의 값을 0(false)에서 1(true)로 업데이트
    // 실무에서는 데이ㅓ가 delete(물리적인 삭제)가 되어버리면 리스크(손실)이 크기 때문에 논리적인 삭제 방식을 사용

    /**
     * 게시글 리스트 조회
     * @return 
     */
    List<PostResponse> findAll();
    // 게시글 목록을 조회하는 SELECT 쿼리를 호출
    // findById()는 id를 기준으로 하나의 게시글을 조회
    // findAll()은 여러 개의 게시글을 리스트에 담아 리턴

    /**
     * 게시글 수 카운팅
     * @return 게시글 수
     */
    int count();
    // 전체 게시글 수를 조회하는 SELECT 쿼리를 호출
}
