package com.noticeboard.comment;

import com.noticeboard.entity.Comment;
import com.noticeboard.entity.Post;
import com.noticeboard.entity.UserMeta;
import com.noticeboard.exception.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CommentService {
    private final CommentRepository commentRepository;

    public Comment create(Post post, String content, UserMeta author) {
        Comment comment = Comment.builder()
                .content(content)
                .author(author)
                .post(post)
                .build();
        this.commentRepository.save(comment);
        return comment;
    }

    public Comment getComment(Long id) {
        Optional<Comment> optionalComment = this.commentRepository.findById(id);
        if(optionalComment.isPresent()) {
            return optionalComment.get();
        }
        else {
            throw new DataNotFoundException("comment not found");
        }
    }

    public void modify(Comment comment, String content) {
        comment.update(content);
        this.commentRepository.save(comment);
    }

    public void delete(Comment comment) {
        this.commentRepository.delete(comment);
    }

    public void vote(Comment comment, UserMeta voter) {
        comment.getVoter().add(voter);
        this.commentRepository.save(comment);
    }
}
