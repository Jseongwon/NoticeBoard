package com.noticeboard.comment;

import com.noticeboard.entity.Comment;
import com.noticeboard.entity.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CommentService {
    private final CommentRepository commentRepository;

    public void create(Post post, String content) {
        Comment comment = Comment.builder()
                .content(content)
                .post(post)
                .build();
        this.commentRepository.save(comment);
    }
}
