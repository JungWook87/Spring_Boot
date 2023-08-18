package com.study.domain.post;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
// 해당 클래스가 컨트롤러 클래스임을 명시
@RequiredArgsConstructor
// 상수로 만들어진 멤버에 대한 생성자 생성. 자동으로 빈 주입
public class PostController {

    private final PostService postService;

    // 게시글 작성 페이지
    @GetMapping("/post/write.do")
    public String openPostWrite(@RequestParam(value="id", required=false) final Long id, Model model){
    // RequestParam : 화면(HTML)에서 보낸 파라미터를 전달받는데 사용.
    // 예를 들어, 신규 게시글을 등록하는 경우에는 게시글 번호(id)가 null로 전송된다. 하지만, 기존 게시글을 수정하는 경우에는 수정할 게시글 번호(id)가
    // openPostWrite()의 파라미터로 전송되고, 전달받은 게시글 번호(id)를 이용해 게시글 상세정보를 조회한 후 화면으로 전달한다

    // 신규 게시글 등록에는 게시글 번호(id)가 필요하지 않기 때문에 required 속성을 false로 지정한다.
    // 필수(required) 속성은 default 값이 true이며, required 속성을 false로 지정하지 않으면, id가 파라미터로 넘어오지 않았을 때 예외가 발생한다
        if(id != null){
            PostResponse post = postService.findPostById(id);
            model.addAttribute("post", post);
        }

        return "post/write";
    }

    //신규 글 작성
    @PostMapping("/post/save.do")
    public String postSava(final PostRequest params){
        postService.savePost(params);
        return "redirect:/post/list.do";
    }

    // 게시글 리스트 조회
    @GetMapping("/post/list.do")
    public String openPostList(Model model){
        List<PostResponse> posts = postService.findAllPost();
        model.addAttribute("posts", posts);
        return "post/list";
    }

    // 게시글 상세 조회
    @GetMapping("/post/view.do")
    public String openPostView(@RequestParam final long id, Model model){
        PostResponse post = postService.findPostById(id);
        model.addAttribute("post", post);
        return "post/view";
    }

    // 기존 게시글 수정
    @PostMapping("/post/update.do")
    public String updatePost(final PostRequest params){
        postService.updatePost(params);
        return "redirect:/post/list.do";
    }

    // 게시글 삭제
    @PostMapping("/post/delete.do")
    public String deletePost(@RequestParam final long id){
        postService.deletePost(id);
        return "redirect:/post/list.do";
    }

}
