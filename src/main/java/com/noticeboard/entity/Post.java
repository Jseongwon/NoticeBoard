package com.noticeboard.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Getter
@Entity(name = "Post")
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseEntity {
    @Column(length = 256, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author", referencedColumnName = "id")
    private UserMeta author;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, mappedBy = "post")
    private List<Comment> commentList;

    @ManyToMany
    Set<UserMeta> voter;

    public Post(String title, String content, UserMeta author, List<Comment> commentList) {
        this.title = title;
        this.content = content;
        this.author = author;
        this.commentList = commentList;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
