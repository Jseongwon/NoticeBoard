package com.noticeboard.post;

import com.noticeboard.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    Post findByTitle(String title);
    Post findByTitleAndContent(String title, String content);
    List<Post> findByTitleLike(String title);
    Page<Post> findAll(Pageable pageable);
    Page<Post> findAll(Specification<Post> specification, Pageable pageable);

//    @Query("select "
//            + "    distinct p "
//            + "    from "
//            + "        post p "
//            + "    left outer join "
//            + "        comment c "
//            + "            on c.post=p "
//            + "    where "
//            + "        p.title like %:keyword% "
//            + "        or p.content like %:keyword% "
//            + "        or c.content like %:keyword% ")
//    Page<Post> findAllByKeyword(@Param("keyword") String keyword, Pageable pageable);
}
