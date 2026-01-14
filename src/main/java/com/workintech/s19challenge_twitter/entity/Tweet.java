package com.workintech.s19challenge_twitter.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "tweets", schema = "twitter_clone")
public class Tweet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "content")
    private String content;

    @Column(name = "creation_date")
    private LocalDate creationDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany
    @JoinColumn(name = "comment_id")
    private List<Comment> comments;

    @OneToMany
    @JoinColumn(name = "like_id")
    private List<Like> likes;

    @OneToMany
    @JoinColumn(name = "retweet_id")
    private List<Retweet> retweets;
}
