package com.workintech.s19challenge_twitter.controller;

import com.workintech.s19challenge_twitter.dto.TweetRequest;
import com.workintech.s19challenge_twitter.entity.Tweet;
import com.workintech.s19challenge_twitter.entity.User;
import com.workintech.s19challenge_twitter.repository.TweetRepository;
import com.workintech.s19challenge_twitter.service.TweetService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/tweet")
public class TweetController {
    private final TweetService tweetService;
    private final TweetRepository tweetRepository;

    @PostMapping
    public ResponseEntity<?> createTweet(@Valid @RequestBody TweetRequest tweetRequest, @AuthenticationPrincipal User user) {
        try {
            Tweet tweet = tweetService.createTweet(tweetRequest, user);
            return ResponseEntity.status(HttpStatus.CREATED).body(tweet);
        } catch(RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @GetMapping("/all")
    public ResponseEntity<List<Tweet>> getAllTweets() {
        List<Tweet> tweets = tweetRepository.findAll();
        tweets.forEach(tweet -> {
            if(tweet.getUser() != null) {
                tweet.getUser().getUsername();
            }
        });
        return ResponseEntity.ok(tweets);
    }

    @GetMapping("/findByUserId")
    public ResponseEntity<?> tweetsByUserId(@RequestParam Long userId) {
    System.out.println("=== DEBUG: Finding tweets for userId: " + userId);
    List<Tweet> tweets = tweetService.tweetsByUserId(userId);
    System.out.println("Found " + tweets.size() + " tweets");
    if (!tweets.isEmpty()) {
        System.out.println("First tweet user: " +
                (tweets.get(0).getUser() != null ? tweets.get(0).getUser().getUsername() : "NULL"));
    }
    return ResponseEntity.ok(tweets);
}

    @GetMapping("/findById")
    public ResponseEntity<?> getTweetById(@RequestParam Long tweetId) {
        try {
            Tweet tweet = tweetService.getTweetById(tweetId);
            return ResponseEntity.ok(tweet);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{tweetId}")
    public ResponseEntity<?> updateTweet(@PathVariable Long tweetId, @RequestBody TweetRequest tweetRequest, @AuthenticationPrincipal User user) {
        try {
            Tweet tweet = tweetService.updateTweet(tweetId, tweetRequest, user);
            return ResponseEntity.ok(tweet);
        } catch(RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{tweetId}")
    public ResponseEntity<?> deleteTweet(@PathVariable Long tweetId, @AuthenticationPrincipal User user) {
        try {
            tweetService.deleteTweet(tweetId, user);
            return ResponseEntity.noContent().build();
        } catch(RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
