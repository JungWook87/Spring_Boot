package com.study;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.study.domain.post.PostMapper;
import com.study.domain.post.PostRequest;
import com.study.domain.post.PostResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class PostMapperTest {

    @Autowired
    PostMapper postMapper;

    @Test
    void save(){
        PostRequest params = new PostRequest();
        params.setTitle("1번 게시글 제목");
        params.setContent("1번 게시글 내용");
        params.setWriter("테스터");
        params.setNoticeYn(false);
        postMapper.save(params);

        List<PostResponse> posts = postMapper.findAll();
        System.out.printf("전체 게시글 개수는 : " + posts.size() + "개 입니다.");

        // PostRequest 객체 생성하고, 값 set하고, save()를 호출
        // Mapper.xml에서 save 쿼리가 실행. #{변수명}을 통해 객체의 멤버 변수에 접근
        // id의 경우 auto_imcrement에 의해 자동 증가

        // 오라클 등 auto_increment 기능이 없는 DB의 경우 시퀀스(Sequence)와 <selectKey> 태그를 이용하여 처리
    }

    @Test
    void findById(){
        PostResponse post = postMapper.findById(1L);
        try{
            String postJson = new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(post);
            System.out.printf(postJson);
        } catch(JsonProcessingException e){
            e.printStackTrace();
        }
        /*
        * post 객체 : 앞에서 생성된 게시글인 PK인 1을 인자로 전달하여 게시글 상세정보 조회
        * postJson : 스프링 부트에 기본으로 내장되어 있는 Jackson 라이브러리를 이용,
        * 조회한 1번 게시글의 응답 객체를 JSON 문자열로 변환한 결과.
        * 객체는 디버깅을 해보지 않는 이상 확인이 까다롭기 때문에 JSON 문자열로 변경해서 콘솔에 출력해본다.
        *
        * JSON은 key-value 쌍으로 이루어진 데이터 포맷.
        */

        /* new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(post);
        * registerModule(new JavaTimeModule())가 없으면 날짜가 "year" : 2023, "month":"OCTOBER", "monthvalue":8 ....
        * 이런 형태로 발생한다. 있으면 2023,8,11,16,25,38 이런 형태로 발생 년,월,일,시,분,초
        * writeValueAsString(post)는 객체를 String으로 변환
        * */
    }

    @Test
    void update(){
        // 1. 게시글 수정
        PostRequest params = new PostRequest();
        params.setId(1L);
        params.setTitle("1번 게시글 제목 수정합니다.");
        params.setContent("1번 게시글 내용 수정합니다");
        params.setWriter("도뎡이");
        params.setNoticeYn(true);
        postMapper.update(params);

        // 2. 게시글 상세정보 조회
        PostResponse post = postMapper.findById(1L);
        try{
            String postJson = new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(post);
            System.out.println(postJson);
        } catch (JsonProcessingException e){
            throw new RuntimeException(e);
        }
    }

    @Test
    void delete(){
        System.out.println("삭제 이전의 전체 게시글 개수는 : " + postMapper.findAll().size() + "개 입니다");
        postMapper.deleteById(1L);
        System.out.println("삭제 이후의 전체 게시글 개수는 : " + postMapper.findAll().size() + "개 입니다.");
    }
}
