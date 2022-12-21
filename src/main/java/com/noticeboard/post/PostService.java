package com.noticeboard.post;

import com.noticeboard.entity.Comment;
import com.noticeboard.entity.Post;
import com.noticeboard.entity.User;
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

    private Specification searchByTitleAndContent(String keyword) {
        return new Specification<Post>() {
            private static final long serialVersionUID = 1L;
            @Override
            public Predicate toPredicate(Root<Post> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                query.distinct(true);
                Join<Post, Comment> postCommentJoin = root.join("commentList", JoinType.LEFT);
                System.out.println("Keyword : " + keyword);
                return criteriaBuilder.or(criteriaBuilder.like(root.get("title"), "%" + keyword + "%"),
                        criteriaBuilder.like(root.get("content"), "%" + keyword + "%"),      // 내용
                        criteriaBuilder.like(postCommentJoin.get("content"), "%" + keyword + "%"));
            }
        };
    }

    private Specification searchByAuthor(String keyword) {
        return new Specification<Post>() {
            private static final long serialVersionUID = 1L;
            @Override
            public Predicate toPredicate(Root<Post> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                query.distinct(true);  // 중복을 제거
                Join<Post, UserMeta> postUserMetaJoin = root.join("author", JoinType.LEFT);
                Join<Post, Comment> postCommentJoin = root.join("commentList", JoinType.LEFT);
                Join<Comment, UserMeta> commentUserMetaJoin = postCommentJoin.join("author", JoinType.LEFT);

                return criteriaBuilder.or(
                        criteriaBuilder.like(postUserMetaJoin.get("user").get("name"), "%" + keyword + "%"),
                        criteriaBuilder.like(commentUserMetaJoin.get("user").get("name"), "%" + keyword + "%"));
            }
        };
    }

    public Page<Post> getList(int page, String keyword) {
        int pageSize = 10;

        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createAt"));
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by(sorts));
        //return this.postRepository.findAllByKeyword(keyword, pageable);
        Specification<Post> specification = searchByTitleAndContent(keyword);
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
