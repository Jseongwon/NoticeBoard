package com.noticeboard.comment;

import com.noticeboard.entity.Comment;
import com.noticeboard.entity.Post;
import com.noticeboard.entity.UserMeta;
import com.noticeboard.post.PostService;
import com.noticeboard.user.ApplicationUser;
import com.noticeboard.usermeta.UserMetaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.security.Principal;

@RequestMapping("/comment")
@RequiredArgsConstructor
@Controller
public class CommentController {

    private final PostService postService;
    private final CommentService commentService;
    private final UserMetaService userMetaService;

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create/{id}")
    public String createComment(Model model, @PathVariable Long id,
                                @Valid CommentForm commentForm, BindingResult bindingResult, Principal principal) {
        Post post = this.postService.getPost(id);
        if(bindingResult.hasErrors()) {
            model.addAttribute("post", post);
            return "post_detail";
        }
        ApplicationUser applicationUser = (ApplicationUser)((Authentication)principal).getPrincipal();

        UserMeta userMeta = this.userMetaService.getUserMeta(applicationUser.getId());
        Comment comment = this.commentService.create(post, commentForm.getContent(), userMeta);
        return String.format("redirect:/post/detail/%s#comment_%s", id, comment.getId());
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify/{id}")
    public String modifyComment(CommentForm commentForm, @PathVariable("id") Long id, Principal principal) {
        Comment comment = this.commentService.getComment(id);
        if(!comment.getAuthor().getUser().getName().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        commentForm.setContent(comment.getContent());
        return "comment_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify/{id}")
    public String modifyComment(@Valid CommentForm commentForm, BindingResult bindingResult,
                                @PathVariable("id") Long id, Principal principal) {
        if(bindingResult.hasErrors()) {
            return "comment_form";
        }
        Comment comment = this.commentService.getComment(id);
        if(!comment.getAuthor().getUser().getName().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        this.commentService.modify(comment, commentForm.getContent());
        return String.format("redirect:/post/detail/%s#comment_%s", comment.getPost().getId(), comment.getId());
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{id}")
    public String deleteComment(Principal principal, @PathVariable("id") Long id) {
        Comment comment = this.commentService.getComment(id);
        if(!comment.getAuthor().getUser().getName().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        this.commentService.delete(comment);
        return String.format("redirect:/post/detail/%s", comment.getPost().getId());
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/vote/{id}")
    public String commentVote(Principal principal, @PathVariable("id") Long id) {
        Comment comment = this.commentService.getComment(id);

        ApplicationUser applicationUser = (ApplicationUser)((Authentication)principal).getPrincipal();
        UserMeta userMeta = this.userMetaService.getUserMeta(applicationUser.getId());

        this.commentService.vote(comment, userMeta);

        return String.format("redirect:/post/detail/%s#comment_%s", comment.getPost().getId(), comment.getId());
    }
}
