package com.workintech.s19challenge_twitter.service;

import com.workintech.s19challenge_twitter.dto.CommentRequest;
import com.workintech.s19challenge_twitter.entity.Comment;
import com.workintech.s19challenge_twitter.entity.Tweet;
import com.workintech.s19challenge_twitter.entity.User;
import com.workintech.s19challenge_twitter.repository.CommentRepository;
import com.workintech.s19challenge_twitter.repository.TweetRepository;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final TweetService tweetService;

    private final CommentRepository commentRepository;

    private final TweetRepository tweetRepository;


    @Override
    public Comment createComment(CommentRequest commentRequest, User user) {
        Tweet tweet = tweetRepository.findById(commentRequest.getTweetId()).orElseThrow(() -> new RuntimeException("Tweet not found"));
        Comment comment = new Comment();
        comment.setContent(commentRequest.getContent());
        comment.setUser(user);
        comment.setTweet(tweet);
        comment.setCreationDate(String.valueOf(LocalDateTime.now()));
        return commentRepository.save(comment);
    }

    @Override
    public Comment updateComment(Long commentId, CommentRequest commentRequest, User user) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new RuntimeException("Comment not found"));
        if(comment.getTweet() == null) {
            throw new RuntimeException("Comment is not relate to this tweet");

        } if(!comment.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Your are not allowed to change this comment");
        } else {
            comment.setContent(commentRequest.getContent());
            return commentRepository.save(comment);
        }
    }

    @Override
    public void deleteComment(Long id) {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new RuntimeException("Comment not found"));
        commentRepository.delete(comment);
    }
}
