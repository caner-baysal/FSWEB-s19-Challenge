package com.workintech.s19challenge_twitter.service;

import com.workintech.s19challenge_twitter.dto.LikeRequest;
import com.workintech.s19challenge_twitter.entity.Like;
import com.workintech.s19challenge_twitter.entity.Tweet;
import com.workintech.s19challenge_twitter.entity.User;
import com.workintech.s19challenge_twitter.repository.LikeRepository;
import com.workintech.s19challenge_twitter.repository.TweetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService {

    private final LikeRepository likeRepository;

    private final TweetRepository tweetRepository;


    @Override
    public String toggleLike(LikeRequest likeRequest, User user) {
        Tweet tweet = tweetRepository.findById(likeRequest.getTweetId()).orElseThrow(() -> new RuntimeException("Tweet not found"));
        Optional<Like> existingLike = likeRepository.findByUserIdAndTweetId(user.getId(), tweet.getId());
        if(existingLike.isPresent()) {
            likeRepository.delete(existingLike.get());
            return "Like removed";
        } else {
            Like like = new Like();
            like.setUser(user);
            like.setTweet(tweet);
            like.setCreationDate(String.valueOf(LocalDateTime.now()));
            likeRepository.save(like);
            return "Liked";
        }
    }
}
