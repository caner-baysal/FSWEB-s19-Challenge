package com.workintech.s19challenge_twitter.controller;

import com.workintech.s19challenge_twitter.dto.LikeRequest;
import com.workintech.s19challenge_twitter.entity.Like;
import com.workintech.s19challenge_twitter.entity.User;
import com.workintech.s19challenge_twitter.repository.UserRepository;
import com.workintech.s19challenge_twitter.service.LikeService;
import lombok.AllArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/like")
public class LikeController {
    private final LikeService likeService;
    private final UserRepository userRepository;

    @PostMapping
    public ResponseEntity<?> toggleLike(@RequestBody LikeRequest likeRequest,
                                        @AuthenticationPrincipal org.springframework.security.core.userdetails.User principal) {

        System.out.println("=== LIKE CONTROLLER DEBUG ===");
        System.out.println("Principal: " + (principal != null ? principal.getUsername() : "NULL"));
        System.out.println("Tweet ID: " + likeRequest.getTweetId());

        if(principal == null) {
            System.out.println("ERROR: Principal is NULL!");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authenticated");
        }

        try {
            // User'ı al
            User user = userRepository.findByUsername(principal.getUsername());
            System.out.println("Found user in DB: " + (user != null ? user.getUsername() : "NULL"));

            if(user == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not found");
            }

            String result = likeService.toggleLike(likeRequest, user);
            return ResponseEntity.ok(result);

        } catch (RuntimeException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
