package com.noticeboard;

import com.noticeboard.comment.CommentService;
import com.noticeboard.entity.Comment;
import com.noticeboard.entity.Post;
import com.noticeboard.post.PostService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class NoticeboardApplicationTests {

    @Autowired
    private PostService postService;

    @Autowired
    private CommentService commentService;

//    @Transactional
    @Test
    void contextLoads() {

    }
}
// Post Test
// Insert post
// Before
//        Post post1 = Post.builder()
//                .title("첫 포스팅")
//                .content("포스팅이 잘 됩니다.")
//                .build();
//
//        this.postRepository.save(post1);
//
//        Post post2 = Post.builder()
//                .title("두번째 포스팅")
//                .content("두번째도 잘 됩니다.")
//                .build();
//
//        this.postRepository.save(post2);
// After
//this.postService.create("첫 포스팅", "포스팅이 잘 됩니다.");
//
//this.postService.create("두번째 포스팅", "두번째도 잘 됩니다.");

// Find all
//        List<Post> all = this.postRepository.findAll();
//        assertEquals(2, all.size());
//
//        Post post = all.get(0);
//        assertEquals("첫 포스팅", post.getTitle());

// Find by id
//        Optional<Post> op = this.postRepository.findById(1L);
//
//        if(op.isPresent()) {
//            Post post = op.get();
//            assertEquals("첫 포스팅", post.getTitle());
//        }

// Find by title
//        Post post = this.postRepository.findByTitle("첫 포스팅");
//        System.out.println(post.getId());
//        assertEquals(1, post.getId());

// Find by title and content
//        Post post = this.postRepository.findByTitleAndContent("첫 포스팅", "포스팅이 잘 됩니다.");
//        System.out.println(post.getId());
//        assertEquals(1, post.getId());

// Find by title like
        /*
        findedString%: findedString으로 시작하는 문자열
        %findedString: findedString으로 끝나는 문자열
        %findedString%: findedString을 포함하는 문자열
         */
//        List<Post> posts = this.postRepository.findByTitleLike("%포스팅%");
//        Post post = posts.get(0);
//        assertEquals(post.getId(), 1);

// Update title
//        Optional<Post> op = this.postRepository.findById(1L);
//        assertTrue(op.isPresent());
//        Post post = op.get();
//        post = Post.builder()
//                .id(post.getId())
//                .createAt(post.getCreateAt())
//                .title("수정된 포스팅")
//                .content(post.getContent())
//                .build();
//        this.postRepository.save(post);

// Delete post
//        assertEquals(this.postRepository.count(), 2);
//        Optional<Post> op = this.postRepository.findById(1l);
//        assertTrue(op.isPresent());
//        Post post = op.get();
//        this.postRepository.delete(post);
//        assertEquals(this.postRepository.count(), 1);

// Comment Test
// Insert comment
// Before
//        {
////            {
////                Post post1 = Post.builder()
////                        .title("첫 포스팅")
////                        .content("포스팅이 잘 됩니다.")
////                        .build();
////
////                this.postRepository.save(post1);
////
////                Post post2 = Post.builder()
////                        .title("두번째 포스팅")
////                        .content("두번째도 잘 됩니다.")
////                        .build();
////
////                this.postRepository.save(post2);
////            }
//            Optional<Post> op = this.postRepository.findById(1L);
//            assertTrue(op.isPresent());
//            Post post = op.get();
//
//            Comment comment = Comment.builder()
//                    .content("첫번째 코맨트")
//                    .post(post)
//                    .build();
//
//            this.commentRepository.save(comment);
//        }
// After
//Post post = this.postService.getPost(1L);
//this.commentService.create(post, "첫번째 코맨트");

// Select comment
//        Optional<Comment> oc = this.commentRepository.findById(1L);
//        assertTrue(oc.isPresent());
//        Comment comment = oc.get();
//        assertEquals(comment.getPost().getId(), 1L);

// Linked object lookup
//        {
//            Optional<Post> op = this.postRepository.findById(1L);
//            assertTrue(op.isPresent());
//            Post post = op.get();
//
//            System.out.println(post.getId());
//
//            List<Comment> commentList = post.getCommentList();
//            System.out.println(commentList.size());
//            System.out.println(commentList.get(0).getContent());
//            assertEquals(commentList.size(), 1);
//            assertEquals(commentList.get(0).getContent(), "첫번째 코맨트");
//        }

// Insert testing post data
//String title;
//String content;
//for(int i = 1; i <= 300; i++) {
//    title = String.format("테스트 데이터입니다. [%03d]", i);
//    content = "내용무";
//    this.postService.create(title, content);
//}