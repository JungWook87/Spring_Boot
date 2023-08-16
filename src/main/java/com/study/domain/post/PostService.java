package com.study.domain.post;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
// PostMapper 인터페이스의 @Mapper와 유사하며, 해당 클래스가 비즈니스 로직을 담당하는 Service Layer의 클래스임을 의미
@RequiredArgsConstructor
// 레거시에는 일반적으로 @Autowired, @Inject, @Resource 등을 이용해서 빈을 주입하였다
// 스프링은 생성자로 빈을 주입하는 방식을 권장한다

//해당 어노테이션은 롬복(Lombok)에서 제공해주는 기능, 클래스 내에 final로 선언된 모든 멤버에 대한 생성자를 만들어주는 역할
// 예) public PostService(PostMapper postMapper){
//      this.postMapper = postMapper; }
public class PostService {

    private final PostMapper postMapper;

    /** 게시글 저장
     * @param params - 게시글 정보
     * @return Generated PK
     */
    @Transactional
    // 스프링에서 제공해 주는 트랜잭션처리 방법 중 하나로, 선언적 트랜잭션으로 불리는 기능이다.
    // 호출된 메서드에 해당 어노테이션이 선언되어 있으면 메서드의 실행과 동시에 트랜잭션이 시작되고,
    // 메서드의 정상 종료 여부에 따라 Commit 또는 Rollback 된다
    public Long savePost(final PostRequest params){
        postMapper.save(params);
        return params.getId();
    }

    /** 게시글 상세정보 조회
     * @param id - PK
     * @return 게시글 상세정보
     */
    public PostResponse findPostById(final Long id){
        return postMapper.findById(id);
    }

    /** 게시글 수정
     * @param params - 게시글 정보
     * @return PK
     */
    @Transactional
    public Long updatePost(final PostRequest params){
        postMapper.update(params);
        return params.getId();
    }

    /** 게시글 삭제
     * @param id - PK
     * @return PK
     */
    public Long deletePost(final Long id){
        postMapper.deleteById(id);
        return id;
    }

    /** 게시글 리스트 조회
     * @return 게시글 리스트
     */
    public List<PostResponse> findAllPost(){
        return postMapper.findAll();
    }
}
