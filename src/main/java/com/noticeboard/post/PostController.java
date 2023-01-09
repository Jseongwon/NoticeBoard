package com.noticeboard.post;

import com.noticeboard.comment.CommentForm;
import com.noticeboard.entity.Post;
import com.noticeboard.entity.User;
import com.noticeboard.entity.UserMeta;
import com.noticeboard.user.ApplicationUser;
import com.noticeboard.user.UserService;
import com.noticeboard.usermeta.UserMetaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Slf4j
@RequestMapping("/post")
@RequiredArgsConstructor
@Controller
public class PostController {

    private final PostService postService;
    private final UserMetaService userMetaService;

    @RequestMapping("/list")
    public String list(Model model, @RequestParam(value = "page", defaultValue = "0") int page,
                       @RequestParam(value = "keyword", defaultValue = "") String keyword) {
        log.info("page:{}, keyword:{}", page, keyword);
        Page<Post> paging = this.postService.getList(page, keyword);

        model.addAttribute("paging", paging);
        model.addAttribute("keyword", keyword);

        return "post_list";
    }

    @RequestMapping(value = "/detail/{id}")
    public String detail(Model model, @PathVariable Long id, CommentForm commentForm) {
        Post post = this.postService.getPost(id);
        model.addAttribute("post", post);
        return "post_detail";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/create")
    public String postCreate(PostForm postForm) {
        return "post_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create")
    public String postCreate(@Valid PostForm postForm, BindingResult bindingResult, Principal principal) {
        if(bindingResult.hasErrors()) {
            return "post_form";
        }
        ApplicationUser applicationUser = (ApplicationUser)((Authentication)principal).getPrincipal();

        UserMeta userMeta = this.userMetaService.getUserMeta(applicationUser.getId());
        this.postService.create(postForm.getTitle(), postForm.getContent(), userMeta);
        return "redirect:/post/list";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify/{id}")
    public String postModify(PostForm postForm, @PathVariable("id") Long id, Principal principal) {
        Post post = this.postService.getPost(id);
        if(!post.getAuthor().getUser().getName().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다");
        }
        postForm.setTitle(post.getTitle());
        postForm.setContent(post.getContent());
        return "post_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify/{id}")
    public String postModify(@Valid PostForm postForm, BindingResult bindingResult,
                             Principal principal, @PathVariable("id") Long id) {
        if(bindingResult.hasErrors()) {
            return "post_form";
        }
        Post post = this.postService.getPost(id);

        if(!post.getAuthor().getUser().getName().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        this.postService.modify(post, postForm.getTitle(), postForm.getContent());
        return String.format("redirect:/post/detail/%s", post.getId());
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{id}")
    public String postDelete(Principal principal, @PathVariable("id") Long id) {
        Post post = this.postService.getPost(id);
        if(!post.getAuthor().getUser().getName().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
        }
        this.postService.delete(post);
        return "redirect:/";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/vote/{id}")
    public String postVote(Principal principal, @PathVariable Long id) {
        Post post = this.postService.getPost(id);

        ApplicationUser applicationUser = (ApplicationUser)((Authentication)principal).getPrincipal();
        UserMeta userMeta = this.userMetaService.getUserMeta(applicationUser.getId());
        this.postService.vote(post, userMeta);
        return String.format("redirect:/post/detail/%s", id);
    }
}
