package com.workintech.s19challenge_twitter.service;

import com.workintech.s19challenge_twitter.dto.RetweetRequest;
import com.workintech.s19challenge_twitter.dto.TweetRequest;
import com.workintech.s19challenge_twitter.entity.Retweet;
import com.workintech.s19challenge_twitter.entity.Tweet;
import com.workintech.s19challenge_twitter.entity.User;
import com.workintech.s19challenge_twitter.exceptions.CustomException;
import com.workintech.s19challenge_twitter.repository.RetweetRepository;
import com.workintech.s19challenge_twitter.repository.TweetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RetweetServiceImpl implements RetweetService {

    private final RetweetRepository retweetRepository;

    private final TweetRepository tweetRepository;

    @Override
    public Retweet retweetTweet(RetweetRequest retweetRequest, User user) {
        Tweet tweet = tweetRepository.findById(retweetRequest.getTweetId()).orElseThrow(() -> new RuntimeException("Tweet not found"));
        Optional<Retweet> existingRetweet = retweetRepository.findByUserIdAndTweetId(user.getId(), tweet.getId());
        if(existingRetweet.isPresent()) {
            throw new CustomException("You have already retweeted this tweet ", HttpStatus.BAD_REQUEST);
        } else {
            Retweet retweet = new Retweet();
            retweet.setUser(user);
            retweet.setTweet(tweet);
            return retweetRepository.save(retweet);
        }

    }

    @Override
    public void deleteRetweet(Long retweetId, User user) {
        Retweet retweet = retweetRepository.findById(retweetId).orElseThrow(() -> new RuntimeException("Retweet not found"));
        if(!retweet.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("You are not allowed to delete this retweet");
        } else {
            retweetRepository.delete(retweet);
        }
    }
}
