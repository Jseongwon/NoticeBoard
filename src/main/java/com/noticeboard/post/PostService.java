package com.noticeboard.post;

import com.noticeboard.entity.Post;
import com.noticeboard.exception.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;

    public Page<Post> getList(int page) {
        int pageSize = 10;

        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createAt"));
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by(sorts));
        return this.postRepository.findAll(pageable);
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

    public void create(String title, String content) {
        Post post = Post.builder()
                .title(title)
                .content(content)
                .build();
        this.postRepository.save(post);
    }
}
