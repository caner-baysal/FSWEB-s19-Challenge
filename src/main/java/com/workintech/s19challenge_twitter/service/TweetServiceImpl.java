package com.workintech.s19challenge_twitter.service;

import com.workintech.s19challenge_twitter.dto.TweetRequest;
import com.workintech.s19challenge_twitter.entity.Tweet;
import com.workintech.s19challenge_twitter.entity.User;
import com.workintech.s19challenge_twitter.repository.TweetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TweetServiceImpl implements TweetService {

    private final TweetRepository tweetRepository;

    @Override
    public Tweet createTweet(TweetRequest tweetRequest, User user) {
        Tweet tweet = new Tweet();
        tweet.setContent(tweetRequest.getContent());
        tweet.setUser(user);
        tweet.setCreationDate(LocalDate.now());
        return tweetRepository.save(tweet);
    }

    @Override
    public List<Tweet> tweetsByUserId(Long userId) {
        return tweetRepository.findByUserId(userId);

    }

    @Override
    public Tweet getTweetById(Long tweetId) {
        return tweetRepository.findById(tweetId).orElseThrow(() -> new RuntimeException("Tweet not found with id: " + tweetId));
    }

    @Override
    public Tweet updateTweet(Long tweetId, TweetRequest tweetRequest, User user) {
        Tweet tweet = getTweetById(tweetId);
        if(!tweet.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("You are not authorized to update this tweet");
        }
        tweet.setContent(tweetRequest.getContent());
        return tweetRepository.save(tweet);
    }

    @Override
    public void deleteTweet(Long tweetId, User user) {
        Tweet tweet = getTweetById(tweetId);
        if(tweet == null) {
            throw new RuntimeException("Tweet not found with id: " + tweetId);
        } else if(!tweet.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("You are not authorized to delete this tweet");
        } else {
            tweetRepository.delete(tweet);
        }
    }
}
