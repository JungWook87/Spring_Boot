package com.study;

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
}
