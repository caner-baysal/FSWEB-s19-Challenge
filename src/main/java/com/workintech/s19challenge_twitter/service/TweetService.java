package com.workintech.s19challenge_twitter.service;

import com.workintech.s19challenge_twitter.dto.TweetRequest;
import com.workintech.s19challenge_twitter.entity.Tweet;
import com.workintech.s19challenge_twitter.entity.User;

import java.util.List;

public interface TweetService {
    Tweet createTweet(TweetRequest tweetRequest, User user);
    List<Tweet> getAllTweets();
    List<Tweet> tweetsByUserId(Long userId);
    Tweet getTweetById(Long tweetId);
    Tweet updateTweet(Long tweetId, TweetRequest tweetRequest, User user);
    void deleteTweet(Long tweetId, User user);
}
