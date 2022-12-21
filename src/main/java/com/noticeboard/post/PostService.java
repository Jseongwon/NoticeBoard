package com.noticeboard.post;

import com.noticeboard.entity.Comment;
import com.noticeboard.entity.Post;
import com.noticeboard.entity.UserMeta;
import com.noticeboard.exception.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;

    private Specification search(String keyword) {
        return new Specification<Post>() {
            private static final long serialVersionUID = 1L;
            @Override
            public Predicate toPredicate(Root<Post> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                query.distinct(true);  // 중복을 제거
                Join<Post, UserMeta> postUserMetaJoin = root.join("author", JoinType.LEFT);
                Join<Post, Comment> postCommentJoin = root.join("commentList", JoinType.LEFT);
                Join<Comment, UserMeta> commentUserMetaJoin = postCommentJoin.join("author", JoinType.LEFT);
                return criteriaBuilder.or(criteriaBuilder.like(root.get("title"), "%" + keyword + "%"), // 제목
                        criteriaBuilder.like(root.get("content"), "%" + keyword + "%"),      // 내용
                        criteriaBuilder.like(postUserMetaJoin.get("name"), "%" + keyword + "%"),    // 질문 작성자
                        criteriaBuilder.like(postCommentJoin.get("content"), "%" + keyword + "%"),      // 답변 내용
                        criteriaBuilder.like(commentUserMetaJoin.get("name"), "%" + keyword + "%"));   // 답변 작성자
            }
        };
    }

    public Page<Post> getList(int page, String keyword) {
        int pageSize = 10;

        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createAt"));
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by(sorts));
        Specification<Post> specification = search(keyword);
        return this.postRepository.findAll(specification, pageable);
    }

    public Post getPost(Long id) {
        Optional<Post> optionalPost = this.postRepository.findById(id);
        if(optionalPost.isPresent()) {
            return optionalPost.get();
        }
        else {
            throw new DataNotFoundException("Post not found");
        }
    }

    public void create(String title, String content, UserMeta userMeta) {
        Post post = Post.builder()
                .title(title)
                .content(content)
                .author(userMeta)
                .build();
        this.postRepository.save(post);
    }

    public void modify(Post post, String title, String content) {
        post.update(title, content);
        this.postRepository.save(post);
    }

    public void delete(Post post) {
        this.postRepository.delete(post);
    }

    public void vote(Post post, UserMeta voter) {
        post.getVoter().add(voter);
        this.postRepository.save(post);
    }
}
